package org.openyu.mix.wuxing.service.impl;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.Notice;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.NoticeImpl;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.wuxing.service.WuxingMachine;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.WuxingCollector;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.service.RoleService.SpendResult;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.util.AssertHelper;
import org.openyu.commons.util.CalendarHelper;
import org.openyu.commons.util.CollectionHelper;
import org.openyu.commons.util.DateHelper;
import org.openyu.socklet.message.vo.Message;

public class WuxingServiceImpl extends AppServiceSupporter implements WuxingService {

	private static final long serialVersionUID = 1449172726555774863L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(WuxingServiceImpl.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	@Autowired
	@Qualifier("wuxingMachine")
	protected transient WuxingMachine wuxingMachine;

	private transient WuxingCollector wuxingCollector = WuxingCollector.getInstance();

	private transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 已開出的結果,人氣榜,最大訊息數量
	 */
	public int MAX_NOTICE_SIZE = 10;

	/**
	 * 已開出的結果,人氣榜
	 */
	private Queue<Notice> board = new ConcurrentLinkedQueue<Notice>();

	public WuxingServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.wuxingMachine, "The WuxingMachine is required");
	}

	/**
	 * 連線
	 * 
	 * @param roleId
	 * @param attatch
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 重置每日已玩的次數
		reset(false, result);

		// 發送連線
		sendRoleConnect(result, attatch);

		// 已連線
		result.getWuxingPen().setConnected(true);
		//
		return result;
	}

	/**
	 * 發送角色連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleConnect(Role role, T attatch) {
		// 發送初始化
		Message message = sendInitialize(role);

		// 發送公告區
		sendBoard(role);

		return message;
	}

	protected Message sendInitialize(Role role) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_INITIALIZE_RESPONSE, role.getId());

		// 五行欄位
		fillWuxingPen(message, role.getWuxingPen());
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 角色斷線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleDisconnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 發送斷線
		sendRoleDisconnect(result, attatch);
		//
		return result;
	}

	/**
	 * 發送角色斷線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleDisconnect(Role role, T attatch) {
		return null;
	}

	/**
	 * 發送五行欄位
	 * 
	 * @param role
	 * @param bagPen
	 * @return
	 */
	public Message sendWuxingPen(Role role, WuxingPen wuxingPen) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_PEN_RESPONSE, role.getId());

		fillWuxingPen(message, wuxingPen);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充五行欄位
	 * 
	 * @param message
	 * @param wuxingPen
	 * @return
	 */
	public boolean fillWuxingPen(Message message, WuxingPen wuxingPen) {
		boolean result = false;
		// 玩的時間
		message.addLong(wuxingPen.getPlayTime());
		// 每日已玩的次數
		message.addInt(wuxingPen.getDailyTimes());

		// 五行結果
		Outcome outcome = wuxingPen.getOutcome();
		message.addString((outcome != null ? safeGet(outcome.getId()) : ""));
		message.addString((outcome != null ? safeGet(outcome.getBankerId()) : ""));
		message.addString((outcome != null ? safeGet(outcome.getPlayerId()) : ""));

		// 填充獎勵
		fillAwards(message, wuxingPen.getAwards());

		result = true;
		return result;
	}

	/**
	 * 填充獎勵
	 * 
	 * @param message
	 * @param awards
	 * @return
	 */
	public boolean fillAwards(Message message, Map<String, Integer> awards) {
		boolean result = false;
		message.addInt(awards.size());// 獎勵總數
		for (Map.Entry<String, Integer> entry : awards.entrySet()) {
			message.addString(entry.getKey());// 獎勵道具id
			message.addInt(entry.getValue());// 獎勵道具數量
		}

		result = true;
		return result;
	}

	/**
	 * 建構開出的結果
	 * 
	 * @return
	 */
	public Outcome createOutcome() {
		return wuxingMachine.createOutcome();
	}

	/**
	 * 建構開出的結果
	 * 
	 * @param outcomeId
	 *            結果id
	 * @param bankerId
	 * @param playerId
	 * @return
	 */
	public Outcome createOutcome(String outcomeId, String bankerId, String playerId) {
		return wuxingMachine.createOutcome(outcomeId, bankerId, playerId);
	}

	public static class PlayResultImpl extends AppResultSupporter implements PlayResult {

		private static final long serialVersionUID = -4323504616451837011L;

		/**
		 * 玩的類別
		 */
		private PlayType playType;

		/**
		 * 玩的時間
		 */
		private long playTime;

		/**
		 * 每日已玩的次數
		 */
		private int dailyTimes;

		/**
		 * 最後玩的結果
		 */
		private Outcome outcome;

		/**
		 * 所有結果
		 */
		private List<Outcome> outcomes = new LinkedList<Outcome>();

		/**
		 * 真正成功扣道具及儲值幣的次數
		 */
		private int totalTimes;

		/**
		 * 消耗的金幣
		 */
		private long spendGold;

		/**
		 * 消耗的道具
		 */
		private List<Item> spendItems = new LinkedList<Item>();

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		public PlayResultImpl(PlayType playType, long playTime, int dailyTimes, Outcome outcome, int totalTimes,
				long spendGold, List<Item> spendItems, int spendCoin) {
			this.playType = playType;
			this.playTime = playTime;
			this.dailyTimes = dailyTimes;
			// 最後玩的結果
			this.outcome = outcome;
			// 所有結果
			this.outcomes.add(outcome);
			//
			this.totalTimes = totalTimes;
			this.spendGold = spendGold;
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		/**
		 * 用金幣玩
		 * 
		 * @param playType
		 * @param playTime
		 * @param dailyTimes
		 * @param outcome
		 * @param spendGold
		 */
		public PlayResultImpl(PlayType playType, long playTime, int dailyTimes, Outcome outcome, int totalTimes,
				long spendGold) {
			this(playType, playTime, dailyTimes, outcome, totalTimes, spendGold, new LinkedList<Item>(), 0);
		}

		/**
		 * 用道具或儲值幣玩
		 * 
		 * @param playType
		 * @param playTime
		 * @param outcome
		 * @param totalTimes
		 * @param spendItems
		 * @param spendCoin
		 */
		public PlayResultImpl(PlayType playType, long playTime, List<Outcome> outcomes, int totalTimes,
				List<Item> spendItems, int spendCoin) {
			this.playType = playType;
			this.playTime = playTime;
			// 所有結果
			this.outcomes = outcomes;
			int size = outcomes.size();
			if (size > 0) {
				// 最後玩的結果
				this.outcome = outcomes.get(size - 1);
			}
			//
			this.totalTimes = totalTimes;
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		public PlayResultImpl() {
			this(null, 0, 0, null, 0, 0L, new LinkedList<Item>(), 0);
		}

		public PlayType getPlayType() {
			return playType;
		}

		public void setPlayType(PlayType playType) {
			this.playType = playType;
		}

		public long getPlayTime() {
			return playTime;
		}

		public void setPlayTime(long playTime) {
			this.playTime = playTime;
		}

		public int getDailyTimes() {
			return dailyTimes;
		}

		public void setDailyTimes(int dailyTimes) {
			this.dailyTimes = dailyTimes;
		}

		public Outcome getOutcome() {
			return outcome;
		}

		public void setOutcome(Outcome outcome) {
			this.outcome = outcome;
		}

		public List<Outcome> getOutcomes() {
			return outcomes;
		}

		public void setOutcomes(List<Outcome> outcomes) {
			this.outcomes = outcomes;
		}

		public int getTotalTimes() {
			return totalTimes;
		}

		public void setTotalTimes(int totalTimes) {
			this.totalTimes = totalTimes;
		}

		public long getSpendGold() {
			return spendGold;
		}

		public void setSpendGold(long spendGold) {
			this.spendGold = spendGold;
		}

		public List<Item> getSpendItems() {
			return spendItems;
		}

		public void setSpendItems(List<Item> spendItems) {
			this.spendItems = spendItems;
		}

		public int getSpendCoin() {
			return spendCoin;
		}

		public void setSpendCoin(int spendCoin) {
			this.spendCoin = spendCoin;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("playType", playType);
			builder.append("playTime", playTime);
			builder.append("dailyTimes", dailyTimes);
			builder.append("outcome", (outcome != null
					? outcome.getId() + "," + outcome.getBankerId() + "," + outcome.getPlayerId() : null));
			//
			builder.append("totalTimes", totalTimes);
			builder.append("spendGold", spendGold);
			builder.append("spendItems", spendItems);
			builder.append("spendCoin", spendCoin);
			return builder.toString();
		}

		public Object clone() {
			PlayResultImpl copy = null;
			copy = (PlayResultImpl) super.clone();
			copy.spendItems = clone(spendItems);
			return copy;
		}
	}

	/**
	 * 玩五行
	 * 
	 * @param sendable
	 * @param role
	 * @param playValue
	 *            玩的類別 @see PlayType
	 * @return
	 */
	public PlayResult play(boolean sendable, Role role, int playValue) {
		PlayResult result = null;
		// 玩的類別
		PlayType playType = EnumHelper.valueOf(PlayType.class, playValue);
		if (playType == null) {
			return result;
		}
		//
		switch (playType) {
		case BRONZE: {
			// 用金幣玩
			result = goldPlay(sendable, role);
			break;
		}
			// 用道具或儲值幣玩
		case GALACTIC:
		case GOLDEN:
		case BLACK: {
			result = itemCoinPlay(sendable, role, playType);
			break;
		}
		default: {
			if (sendable) {
				// 玩的類別不存在
				sendPlay(ErrorType.PLAY_TYPE_NOT_EXIST, role, result);
			}
			break;
		}
		}
		//
		return result;
	}

	/**
	 * 用金幣玩
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public PlayResult goldPlay(boolean sendable, Role role) {
		PlayResult result = null;
		WuxingPen wuxingPen = null;
		//
		List<Notice> notices = new LinkedList<Notice>();// 已玩的通知
		List<Prize> prizes = new LinkedList<Prize>();// 已玩的獎勵
		// 檢查條件
		ErrorType errorType = checkGoldPlay(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 五行
			wuxingPen = role.getWuxingPen();
			// 先玩,為了檢查獎勵區空間是否足夠
			Outcome outcome = wuxingMachine.play();
			// 沒結果,xml可能沒設定,壞掉了
			if (outcome == null) {
				errorType = ErrorType.OUTCOME_NOT_EXIST;
			} else {
				// 檢查中獎區
				errorType = checkAwards(wuxingPen, outcome);
			}
			//
			if (errorType == ErrorType.NO_ERROR) {
				// 花費的金幣
				long spendGold = wuxingCollector.getPlayGold();
				;
				// 扣金幣
				long decreaseGold = roleService.decreaseGold(true, role, spendGold, GoldType.WUXING_PLAY);
				// 成功
				if (decreaseGold != 0) {
					long now = System.currentTimeMillis();
					wuxingPen.setPlayTime(now);// 玩的時間
					wuxingPen.addDailyTimes(1);// 每日已玩的次數

					// clone玩的結果
					Outcome cloneOutcome = clone(outcome);
					wuxingPen.setOutcome(cloneOutcome);// 最後的結果

					// 結果,放獎勵
					prizes = cloneOutcome.getPrizes();
					// 獎勵區加入道具,並累計道具數量
					for (Prize prize : prizes) {
						wuxingPen.addAwards(prize.getAwards());
						// 加到公告通知公告區
						Notice notice = addNotice(role, prize);
						if (notice != null) {
							notices.add(notice);
						}
					}

					// 結果
					result = new PlayResultImpl(PlayType.BRONZE, now, wuxingPen.getDailyTimes(), cloneOutcome, 1,
							spendGold);
				} else {
					errorType = ErrorType.GOLD_NOT_ENOUGH;
				}
			}
		}

		// 發訊息
		if (sendable) {
			// 發送五行欄位
			if (errorType == ErrorType.NO_ERROR && wuxingPen != null) {
				sendWuxingPen(role, wuxingPen);
			}
			// 玩的訊息
			sendPlay(errorType, role, result);
			//
			if (result != null) {
				// 多個公告通知
				sendNotices(notices);
				// 多個成名
				sendFamousPlays(role, prizes);
			}
		}
		//
		return result;
	}

	/**
	 * 檢查用金幣玩
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkGoldPlay(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < wuxingCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		WuxingPen wuxingPen = role.getWuxingPen();

		// 超過每日次數
		if (wuxingPen.getDailyTimes() >= wuxingCollector.getDailyTimes()) {
			errorType = ErrorType.OVER_PLAY_DAILY_TIMES;
			return errorType;
		}

		// 金幣不足
		long spendGold = wuxingCollector.getPlayGold();
		boolean checkDecreaseGold = roleService.checkDecreaseGold(role, spendGold);
		if (!checkDecreaseGold) {
			errorType = ErrorType.GOLD_NOT_ENOUGH;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 用道具或儲值幣玩
	 * 
	 * @param sendable
	 * @param role
	 * @param playType
	 * @see PlayType
	 * @return
	 */
	public PlayResult itemCoinPlay(boolean sendable, Role role, PlayType playType) {
		PlayResult result = null;
		WuxingPen wuxingPen = null;
		int playTimes = playType.playTimes();// 玩的次數
		//
		List<Notice> notices = new LinkedList<Notice>();// 已玩的通知
		List<Prize> prizes = new LinkedList<Prize>();// 已玩的獎勵
		// 檢查條件
		ErrorType errorType = checkItemCoinPlay(role, playTimes);
		if (errorType == ErrorType.NO_ERROR) {
			// 五行
			wuxingPen = role.getWuxingPen();
			// 先玩,為了檢查獎勵區空間是否足夠
			List<Outcome> outcomes = wuxingMachine.play(playTimes);
			// 沒結果,xml可能沒設定,壞掉了
			if (outcomes.size() == 0) {
				errorType = ErrorType.OUTCOME_NOT_EXIST;
			} else {
				// 檢查中獎區
				errorType = checkAwards(wuxingPen, outcomes);
			}
			//
			if (errorType == ErrorType.NO_ERROR) {
				// 消耗道具或儲值幣
				SpendResult spendResult = roleService.spendByItemCoin(sendable, role, playTimes,
						wuxingCollector.getPlayItem(), 1, wuxingCollector.getPlayCoin(), CoinType.WUXING_PLAY,
						vipCollector.getWuxingCoinVipType());
				//
				RoleService.ErrorType spendError = spendResult.getErrorType();
				// System.out.println("spendError: " + spendError);

				// 扣成功
				if (spendError == RoleService.ErrorType.NO_ERROR) {
					long now = System.currentTimeMillis();
					wuxingPen.setPlayTime(now);// 玩的時間

					// clone玩的結果
					List<Outcome> cloneOutcomes = clone(outcomes);
					// 拿最後一個結果
					Outcome cloneOutcome = cloneOutcomes.get(cloneOutcomes.size() - 1);
					wuxingPen.setOutcome(cloneOutcome);// 最後的結果

					// 累計每日已玩的次數,不需每日重置
					wuxingPen.addAccuTimes(spendResult.getTotalTimes());

					// 所有的結果,放獎勵
					for (Outcome outcome : outcomes) {
						List<Prize> buffPrizes = outcome.getPrizes();
						for (Prize prize : buffPrizes) {
							// 獎勵區加入道具,並累計道具數量
							wuxingPen.addAwards(prize.getAwards());

							// 加到公告通知公告區
							Notice notice = addNotice(role, prize);
							if (notice != null) {
								notices.add(notice);
							}

							// 多個成名
							if (prize.isFamous()) {
								prizes.add(prize);
							}
						}
					}

					// 玩的結果
					result = new PlayResultImpl(playType, now, cloneOutcomes, spendResult.getTotalTimes(),
							spendResult.getItems(), spendResult.getCoin());
				} else {
					errorType = roleErrorToWuxingError(spendError);
				}
			}
		}

		// 發訊息
		if (sendable) {
			// 發送五行欄位
			if (errorType == ErrorType.NO_ERROR && wuxingPen != null) {
				sendWuxingPen(role, wuxingPen);
			}
			// 玩的訊息
			sendPlay(errorType, role, result);
			//
			if (result != null) {
				// 多個公告通知
				sendNotices(notices);
				// 多個成名
				sendFamousPlays(role, prizes);
			}
		}
		//
		return result;
	}

	/**
	 * role的errorType轉換成wuxing的errorType
	 * 
	 * @param roleError
	 * @return
	 */
	protected ErrorType roleErrorToWuxingError(RoleService.ErrorType roleError) {
		ErrorType errorType = ErrorType.NO_ERROR;
		switch (roleError) {
		case ROLE_NOT_EXIST: {
			errorType = ErrorType.ROLE_NOT_EXIST;
			break;
		}
		case VIP_NOT_ENOUGH: {
			errorType = ErrorType.VIP_NOT_ENOUGH;
			break;
		}
		case COIN_NOT_ENOUGH: {
			errorType = ErrorType.COIN_NOT_ENOUGH;
			break;
		}
		case CAN_NOT_DECREASE_ITEM: {
			errorType = ErrorType.CAN_NOT_DECREASE_ITEM;
			break;
		}
		default: {
			break;
		}
		}
		return errorType;
	}

	/**
	 * 檢查用道具或儲值幣玩
	 * 
	 * @param role
	 * @param times
	 *            玩的次數
	 * @return
	 */
	public ErrorType checkItemCoinPlay(Role role, int playTimes) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < wuxingCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 玩的次數為0
		if (playTimes == 0) {
			errorType = ErrorType.PLAY_TIMES_IS_ZERO;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 檢查中獎區
	 * 
	 * @param wuxingPen
	 * @param outcome
	 * @return
	 */
	protected ErrorType checkAwards(WuxingPen wuxingPen, Outcome outcome) {

		List<Outcome> outcomes = new LinkedList<Outcome>();
		outcomes.add(outcome);
		return checkAwards(wuxingPen, outcomes);
	}

	/**
	 * 檢查中獎區
	 * 
	 * @param roleId
	 * @param wuxingPen
	 * @param outcomes
	 * @return
	 */
	protected ErrorType checkAwards(WuxingPen wuxingPen, List<Outcome> outcomes) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 檢查獎勵區空間是否足夠
		// 中獎區,clone一個出來
		Map<String, Integer> cloneAwards = clone(wuxingPen.getAwards());
		int cloneAwardsSize = cloneAwards.size();
		//
		for (Outcome outcome : outcomes) {
			List<Prize> prizes = outcome.getPrizes();
			for (Prize prize : prizes) {
				Map<String, Integer> prizeAwards = prize.getAwards();
				for (Map.Entry<String, Integer> entry : prizeAwards.entrySet()) {
					String itemId = entry.getKey();
					Integer amount = entry.getValue();
					boolean contains = cloneAwards.containsKey(itemId);
					if (!contains) {
						cloneAwardsSize += 1;
						// 最大中獎區道具種類
						if (cloneAwardsSize > WuxingPen.MAX_AWARDS_SIZE) {
							// 中獎區滿了
							errorType = ErrorType.AWARDS_FULL;
							break;
						} else {
							cloneAwards.put(itemId, amount);
						}
					}
				}
				//
				if (errorType == ErrorType.AWARDS_FULL) {
					break;
				}
			}
		}
		//
		return errorType;
	}

	/**
	 * 發送玩
	 * 
	 * @param errorType
	 *            錯誤類別
	 * @param role
	 * @param playResult
	 */
	public Message sendPlay(ErrorType errorType, Role role, PlayResult playResult) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_PLAY_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addInt(playResult.getPlayType());// 玩的類別
			break;
		}
		default: {
			break;
		}
		}
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 加到人氣榜
	 * 
	 * @param role
	 * @param treasure
	 * @param item
	 * @return
	 */
	protected Notice addNotice(Role role, Prize prize) {
		Notice result = null;
		// 有成名的獎勵,才加到人氣榜
		// if (prize != null && prize.isFamous())

		// 改為只要有結果就加到人氣榜,2013/04/03
		if (prize != null) {
			if (board.size() >= MAX_NOTICE_SIZE) {
				board.poll();
			}
			//
			result = new NoticeImpl();
			result.setRoleName(role.getName());
			// 取道具名稱
			Map<String, Integer> nameAwards = new LinkedHashMap<String, Integer>();
			Map<String, Integer> awards = prize.getAwards();
			for (Map.Entry<String, Integer> entry : awards.entrySet()) {
				Item item = itemService.createItem(entry.getKey());
				nameAwards.put(item.getName(), entry.getValue());
			}
			result.setAwards(nameAwards);
			//
			boolean offer = board.offer(result);
			if (!offer) {
				result = null;
			}
		}
		return result;
	}

	/**
	 * 發送通知
	 * 
	 * @param receivers
	 * @param notice
	 */
	protected void sendNotice(Notice notice) {
		if (notice == null) {
			return;
		}
		// 取所有角色id,只限本地
		List<String> receivers = roleSetService.getRoleIds(false);
		//
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_NOTICE_RESPONSE, receivers);

		fillNotice(message, notice);
		//
		messageService.addMessage(message);
	}

	/**
	 * 發送多個通知
	 * 
	 * @param receivers
	 * @param notice
	 */
	protected void sendNotices(List<Notice> notices) {
		if (CollectionHelper.isEmpty(notices)) {
			return;
		}
		// 取所有角色id,只限本地
		List<String> receivers = roleSetService.getRoleIds(false);
		//
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_NOTICES_RESPONSE, receivers);

		message.addInt(notices.size());
		for (Notice notice : notices) {
			fillNotice(message, notice);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充通知
	 * 
	 * @param message
	 * @param notice
	 */
	protected void fillNotice(Message message, Notice notice) {
		message.addString(notice.getRoleName());
		// 已經是道具名稱,因addNotice已處理
		Map<String, Integer> awards = notice.getAwards();
		message.addInt(awards.size());
		for (Map.Entry<String, Integer> entry : awards.entrySet()) {
			message.addString(entry.getKey());
			message.addInt(entry.getValue());
		}
	}

	/**
	 * 發送人氣榜
	 * 
	 * @param role
	 */
	protected void sendBoard(Role role) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_BOARD_RESPONSE, role.getId());

		message.addInt(board.size());
		for (Notice notice : board) {
			fillNotice(message, notice);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 發送成名玩
	 * 
	 * @param receivers
	 * @param roleName
	 * @param level
	 */
	protected void sendFamousPlay(Role role, Prize prize) {
		if (prize == null || !prize.isFamous()) {
			return;
		}
		// 取所有角色id
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_FAMOUS_PLAY_RESPONSE, receivers);

		fillFamousPlay(message, role, prize);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充成名玩
	 * 
	 * @param message
	 * @param role
	 * @param prize
	 */
	protected void fillFamousPlay(Message message, Role role, Prize prize) {
		message.addString(role.getName());// 角色名稱
		message.addInt(prize.getLevel());// 獎勵等級
	}

	protected void sendFamousPlays(Role role, List<Prize> prizes) {
		if (CollectionHelper.isEmpty(prizes)) {
			return;
		}
		// 取所有角色id
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_FAMOUS_PLAYS_RESPONSE, receivers);

		message.addInt(prizes.size());
		for (Prize prize : prizes) {
			fillFamousPlay(message, role, prize);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 發送中獎區
	 * 
	 * @param role
	 * @param bagPen
	 */
	public Message sendAwards(Role role, Map<String, Integer> awards) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_AWARDS_RESPONSE, role.getId());

		fillAwards(message, awards);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 放入包包的結果
	 */
	public static class PutResultImpl extends AppResultSupporter implements PutResult {
		private static final long serialVersionUID = -5914943285973752523L;

		private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

		public PutResultImpl(String itemId, int amount) {
			awards.put(itemId, amount);
		}

		public PutResultImpl() {
		}

		public Map<String, Integer> getAwards() {
			return awards;
		}

		public void setAwards(Map<String, Integer> awards) {
			this.awards = awards;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("awards", awards);
			return builder.toString();
		}

		public Object clone() {
			PutResultImpl copy = null;
			copy = (PutResultImpl) super.clone();
			copy.awards = clone(awards);
			return copy;
		}
	}

	/**
	 * 單擊獎勵放入包包
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 */
	public PutResult putOne(boolean sendable, Role role, String itemId, int amount) {
		PutResult result = null;
		// 檢查條件
		ErrorType errorType = checkPutOne(role, itemId, amount);
		if (errorType == ErrorType.NO_ERROR) {
			// 五行
			WuxingPen wuxingPen = role.getWuxingPen();

			// 再判斷放入包包是否成功
			List<IncreaseItemResult> increaseResults = itemService.increaseItemWithItemId(true, role, itemId, amount);
			if (increaseResults.size() >= 0) {
				wuxingPen.removeAward(itemId);
				// 結果
				result = new PutResultImpl(itemId, amount);
			} else {
				errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
			}
		}

		// 發訊息
		if (sendable) {
			sendPutOne(errorType, role, itemId, amount);
		}
		//
		return result;
	}

	/**
	 * 檢查單擊獎勵放入包包
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public ErrorType checkPutOne(Role role, String itemId, int amount) {
		ErrorType errorType = ErrorType.NO_ERROR;
		// 道具不存在
		Item item = itemService.createItem(itemId, amount);
		if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 中獎區道具不存在
		WuxingPen wuxingPen = role.getWuxingPen();
		// 獎勵道具數量
		Integer awardAmount = wuxingPen.getAwards().get(itemId);
		if (awardAmount == null || awardAmount < amount) {
			errorType = ErrorType.AWARD_NOT_EXIST;
			return errorType;
		}

		// 檢查包包增加道具
		BagPen.ErrorType bagError = itemService.checkIncreaseItem(role, item);
		if (bagError != BagPen.ErrorType.NO_ERROR) {
			errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送單擊獎勵放入包包
	 * 
	 * @param errorType
	 * @param role
	 * @param itemId
	 * @param amount
	 */
	public Message sendPutOne(ErrorType errorType, Role role, String itemId, int amount) {

		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_PUT_ONE_RESPONSE, role.getId());

		message.addInt(errorType);// 0,錯誤類別
		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addString(itemId);
			message.addInt(amount);
			break;
		}
		default: {
			break;
		}
		}
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * @param sendable
	 * @param role
	 */
	public PutResult putAll(boolean sendable, Role role) {
		PutResult result = null;
		// 檢查條件
		ErrorType errorType = ErrorType.NO_ERROR;
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
		} else {
			// 五行
			WuxingPen wuxingPen = role.getWuxingPen();
			Map<String, Integer> awards = wuxingPen.getAwards();
			for (Map.Entry<String, Integer> entry : awards.entrySet()) {
				String itemId = entry.getKey();
				int amount = entry.getValue();
				// 檢查獎勵放入包包
				ErrorType checkError = checkPutOne(role, itemId, amount);
				// 有1個放不進去就不放了
				if (checkError != ErrorType.NO_ERROR) {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
					break;
				}
				// 再判斷放入包包是否成功
				List<IncreaseItemResult> increaseResults = itemService.increaseItemWithItemId(true, role, itemId, amount);
				if (increaseResults.size() >= 0) {
					// 結果
					if (result == null) {
						result = new PutResultImpl();
					}
					result.getAwards().put(itemId, amount);
				}
			}
			// 有放到包包成功,從中獎區移除已放入的道具
			if (result != null) {
				for (String itemId : result.getAwards().keySet()) {
					wuxingPen.removeAward(itemId);
				}
			}

		}

		// 發訊息
		if (sendable) {
			sendPutAll(errorType, role, result);
		}
		//
		return result;
	}

	/**
	 * 發送所有中獎區獎勵放入包包
	 * 
	 * @param errorType
	 * @param role
	 * @param awards
	 */
	public Message sendPutAll(ErrorType errorType, Role role, PutResult putResult) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_PUT_ALL_RESPONSE, role.getId());

		message.addInt(errorType);// 0,錯誤類別
		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			// 填充獎勵
			fillAwards(message, putResult.getAwards());
			break;
		}
		default: {
			break;
		}
		}
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 重置每日已玩的次數
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public boolean reset(boolean sendable, Role role) {
		boolean result = false;
		WuxingPen wuxingPen = null;
		// 檢查條件
		ErrorType errorType = checkReset(role);
		// 超過重置時間
		if (errorType == ErrorType.OVER_RESET_TIME) {
			// 訓練
			wuxingPen = role.getWuxingPen();
			// 重置
			result = wuxingPen.reset();
		}

		// 發訊息
		if (sendable && result) {
			sendReset(role, wuxingPen);
		}
		//
		return result;
	}

	/**
	 * 檢查重置
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkReset(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}
		//
		WuxingPen wuxingPen = role.getWuxingPen();
		// 今日早上0點
		Calendar today = CalendarHelper.today(0, 0, 0);
		// 明日早上0點
		Calendar tomorrow = CalendarHelper.tomorrow(0, 0, 0);

		// 玩的時間
		long playTime = wuxingPen.getPlayTime();
		boolean overTime = DateHelper.isOverTime(playTime, System.currentTimeMillis(), today.getTimeInMillis(),
				tomorrow.getTimeInMillis());
		// 超過重置時間
		if (overTime) {
			errorType = ErrorType.OVER_RESET_TIME;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送重置
	 * 
	 * @param role
	 * @param trainPen
	 */
	public Message sendReset(Role role, WuxingPen wuxingPen) {
		Message message = messageService.createMessage(CoreModuleType.WUXING, CoreModuleType.CLIENT,
				CoreMessageType.WUXING_RESET_RESPONSE, role.getId());

		fillWuxingPen(message, wuxingPen);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 重置每日已玩的次數,所有線上玩家
	 * 
	 * 排程用
	 * 
	 * @param sendable
	 * @return
	 */
	public int reset(boolean sendable) {
		int result = 0;
		// false=只有本地
		for (Role role : roleSetService.getRoles(false).values()) {
			try {
				// 是否已連線
				if (!role.isConnected() || !role.getWuxingPen().isConnected()) {
					continue;
				}
				//
				String roleId = role.getId();
				// 重置
				boolean reset = reset(sendable, role);
				result += (reset ? 1 : 0);// 重置成功的角色數量
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		//
		return result;
	}
}
