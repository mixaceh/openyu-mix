package org.openyu.mix.treasure.service.impl;

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
import org.openyu.mix.app.vo.impl.NoticeImpl;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasureCollector;
import org.openyu.mix.treasure.vo.TreasurePen;
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
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.socklet.message.vo.Message;

/**
 * 秘寶服務
 */
public class TreasureServiceImpl extends AppServiceSupporter implements TreasureService {

	private static final long serialVersionUID = -6406783662997385343L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TreasureServiceImpl.class);

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

	private transient TreasureCollector treasureCollector = TreasureCollector.getInstance();

	private transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 已購買的成名祕寶,人氣榜,最大訊息數量
	 */
	int MAX_NOTICE_SIZE = 15;

	/**
	 * 已購買的成名祕寶,人氣榜
	 */
	private Queue<Notice> board = new ConcurrentLinkedQueue<Notice>();

	/**
	 * 監聽毫秒
	 */
	private long LISTEN_MILLS = 10 * 1000L;

	/**
	 * 監聽
	 */
	private transient TreasureListenRunner treasureListenRunner;

	public TreasureServiceImpl() {
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.treasureListenRunner = new TreasureListenRunner(threadService);
		this.treasureListenRunner.start();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.treasureListenRunner.shutdown();
	}

	/**
	 * 監聽
	 */
	protected class TreasureListenRunner extends BaseRunnableSupporter {

		public TreasureListenRunner(ThreadService threadService) {
			super(threadService);
		}

		@Override
		protected void doRun() throws Exception {
			while (true) {
				try {
					listen();
					ThreadHelper.sleep(LISTEN_MILLS);
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 監聽
	 * 
	 * 經過期間, passMills = (now - plantTime)
	 * 
	 * 剩餘期間, residualMills = refreshMills - (now - plantTime)
	 * 
	 * @return
	 */
	protected void listen() {
		// false=只有本地
		for (Role role : roleSetService.getRoles(false).values()) {
			try {
				// 是否已連線
				if (!role.isConnected() || !role.getTreasurePen().isConnected()) {
					continue;
				}

				// 更新上架祕寶
				boolean renew = renewTreasures(role);
				if (renew) {
					TreasurePen treasurePen = role.getTreasurePen();
					// 發訊息
					sendTreasurePen(role, treasurePen);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 更新上架祕寶,不發訊息,因會在sendConnect發送
		renewTreasures(result);

		// 發送連線
		sendRoleConnect(result, attatch);

		// 已連線
		result.getTreasurePen().setConnected(true);
		//
		return result;
	}

	/**
	 * 更新上架祕寶
	 * 
	 * @param role
	 * @return
	 */
	protected boolean renewTreasures(Role role) {
		boolean result = false;
		TreasurePen treasurePen = role.getTreasurePen();
		// 剩餘毫秒
		long residualMills = calcResidual(treasurePen);
		// System.out.println("剩餘時間: " + residualMills);
		// 可以再隨機上架祕寶
		if (residualMills <= 0) {
			treasurePen.setRefreshTime(System.currentTimeMillis());// 刷新時間
			treasurePen.setTreasures(randomTreasures());// 隨機上架祕寶
			result = true;
		}
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
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_INITIALIZE_RESPONSE, role.getId());

		// 祕寶欄位
		fillTreasurePen(message, role.getTreasurePen());
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

	// --------------------------------------------------

	/**
	 * 發送祕寶欄位
	 * 
	 * @param role
	 * @param treasurePen
	 * @return
	 */
	public Message sendTreasurePen(Role role, TreasurePen treasurePen) {
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_PEN_RESPONSE, role.getId());

		fillTreasurePen(message, treasurePen);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充祕寶欄位
	 * 
	 * @param message
	 * @param treasurePen
	 * @return
	 */
	public boolean fillTreasurePen(Message message, TreasurePen treasurePen) {
		boolean result = false;
		// 刷新時間
		message.addLong(treasurePen.getRefreshTime());
		// 剩餘毫秒
		long residualMills = calcResidual(treasurePen);
		message.addLong(residualMills);

		// 上架祕寶
		Map<Integer, Treasure> treasures = treasurePen.getTreasures();
		message.addInt(treasures.size());
		for (Map.Entry<Integer, Treasure> entry : treasures.entrySet()) {
			message.addInt(entry.getKey());// 上架祕寶索引
			fillTreasure(message, entry.getValue());
		}

		result = true;
		return result;
	}

	/**
	 * 發送上架祕寶
	 * 
	 * @param role
	 * @param treasure
	 * @return
	 */
	public Message sendTreasure(Role role, Treasure treasure) {
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_TREASURE_RESPONSE, role.getId());

		fillTreasure(message, treasure);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充上架祕寶
	 * 
	 * @param message
	 * @param treasure
	 * @return
	 */
	public boolean fillTreasure(Message message, Treasure treasure) {
		boolean result = false;
		/**
		 * @see org.openyu.mix.treasure.po.userType.TreasureUserType
		 */
		message.addString(treasure.getId());// 祕寶id
		message.addString(treasure.getStockId());// 祕寶庫id
		message.addBoolean(treasure.isBought());// 是否已購買

		result = true;
		return result;
	}

	/**
	 * 建構祕寶
	 * 
	 * @param stockId
	 * @param treasureId
	 * @return
	 */
	public Treasure createTreasure(String stockId, String treasureId) {
		Treasure result = null;
		//
		if (treasureCollector.containStock(stockId)) {
			result = treasureCollector.createTreasure(stockId, treasureId);
		} else {
			LOGGER.warn("[" + stockId + "] not exist in XML to be created");
		}
		return result;
	}

	/**
	 * 隨機上架祕寶
	 * 
	 * @return
	 */
	public Map<Integer, Treasure> randomTreasures() {
		Map<Integer, Treasure> result = treasureCollector.randomTreasures();
		if (result.size() == 0) {
			LOGGER.warn("some stock id not exist in XML to be created");
		}
		return result;
	}

	// /**
	// * 計算刷新剩餘時間
	// *
	// * @return
	// */
	// public long calcResidual(TreasurePen treasurePen)
	// {
	// long result = 0L;
	// //目前時間
	// long now = System.currentTimeMillis();
	//
	// //經過毫秒
	// long passMills = now - treasurePen.getRefreshTime();
	// //刷新毫秒
	// long refreshMills = treasureCollector.getRefreshMills();
	// //1個周期內
	// if (passMills / refreshMills == 0d)
	// {
	// //剩餘毫秒
	// result = refreshMills - passMills;
	// }
	// //超過1個周期
	// else
	// {
	// //剩餘毫秒
	// result = refreshMills - passMills % refreshMills;
	// }
	// //
	// return (result > 0 ? result : 0);
	// }

	/**
	 * 計算刷新剩餘時間
	 * 
	 * @return
	 */
	protected long calcResidual(TreasurePen treasurePen) {
		long result = 0L;
		// 目前時間
		long now = System.currentTimeMillis();

		// 經過毫秒
		long passMills = now - treasurePen.getRefreshTime();
		// 剩餘毫秒
		result = treasureCollector.getRefreshMills() - passMills;
		//
		return (result > 0 ? result : 0);
	}

	/**
	 * 刷新結果
	 */
	public static class RefreshResultImpl extends AppResultSupporter implements RefreshResult {
		private static final long serialVersionUID = -4944071242185614145L;

		/**
		 * 刷新時間
		 */
		private long refreshTime;

		/**
		 * 剩餘毫秒
		 */
		private long residualMills;

		/**
		 * 上架祕寶
		 */
		private Map<Integer, Treasure> treasures = new LinkedHashMap<Integer, Treasure>();

		/**
		 * 真正成功扣道具及儲值幣的次數
		 */
		private int totalTimes;

		/**
		 * 消耗的道具
		 */
		private List<Item> spendItems = new LinkedList<Item>();

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		public RefreshResultImpl(long refreshTime, long residualMills, Map<Integer, Treasure> treasures, int totalTimes,
				List<Item> spendItems, int spendCoin) {
			this.refreshTime = refreshTime;
			this.residualMills = residualMills;
			this.treasures = treasures;
			//
			this.totalTimes = totalTimes;
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		/**
		 * 消耗道具
		 * 
		 * @param refreshTime
		 * @param residualMills
		 * @param treasures
		 * @param totalTimes
		 * @param spendItems
		 */
		public RefreshResultImpl(long refreshTime, long residualMills, Map<Integer, Treasure> treasures, int totalTimes,
				List<Item> spendItems) {
			this(refreshTime, residualMills, treasures, totalTimes, spendItems, 0);
		}

		/**
		 * 消耗儲值幣
		 * 
		 * @param refreshTime
		 * @param residualMills
		 * @param treasures
		 * @param spendCoin
		 */
		public RefreshResultImpl(long refreshTime, long residualMills, Map<Integer, Treasure> treasures, int totalTimes,
				int spendCoin) {
			this(refreshTime, residualMills, treasures, totalTimes, new LinkedList<Item>(), spendCoin);

		}

		public RefreshResultImpl() {
			this(0L, 0L, null, 0, new LinkedList<Item>(), 0);
		}

		public long getRefreshTime() {
			return refreshTime;
		}

		public void setRefreshTime(long refreshTime) {
			this.refreshTime = refreshTime;
		}

		public long getResidualMills() {
			return residualMills;
		}

		public void setResidualMills(long residualMills) {
			this.residualMills = residualMills;
		}

		public Map<Integer, Treasure> getTreasures() {
			return treasures;
		}

		public void setTreasures(Map<Integer, Treasure> treasures) {
			this.treasures = treasures;
		}

		public int getTotalTimes() {
			return totalTimes;
		}

		public void setTotalTimes(int totalTimes) {
			this.totalTimes = totalTimes;
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
			builder.append("refreshTime", refreshTime);
			builder.append("residualMills", residualMills);
			//
			StringBuilder buff = null;
			if (treasures != null) {
				buff = new StringBuilder();
				int i = 0;
				for (Map.Entry<Integer, Treasure> entry : treasures.entrySet()) {
					buff.append(entry.getKey());
					buff.append("=");
					buff.append(entry.getValue().getId());
					if (i < treasures.size() - 1) {
						buff.append(", ");
					}
					i++;
				}
			}
			//
			builder.append("treasures", buff);
			//
			builder.append("totalTimes", totalTimes);
			append(builder, "spendItems", spendItems);
			builder.append("spendCoin", spendCoin);
			return builder.toString();
		}

		public Object clone() {
			RefreshResultImpl copy = null;
			copy = (RefreshResultImpl) super.clone();
			copy.treasures = clone(treasures);
			copy.spendItems = clone(spendItems);
			return copy;
		}
	}

	/**
	 * 刷新祕寶
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public RefreshResult refresh(boolean sendable, Role role) {
		RefreshResult result = null;
		TreasurePen treasurePen = null;
		// 檢查條件
		ErrorType errorType = checkRefresh(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 祕寶
			treasurePen = role.getTreasurePen();

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, treasureCollector.getRefreshItem(), 1,
					treasureCollector.getRefreshCoin(), CoinType.TREASURE_REFRESH,
					vipCollector.getTreasureCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (spendError == RoleService.ErrorType.NO_ERROR) {
				long now = System.currentTimeMillis();
				treasurePen.setRefreshTime(now);// 刷新時間
				//
				Map<Integer, Treasure> treasures = randomTreasures();
				treasurePen.setTreasures(treasures);// 上架祕寶
				// 剩餘毫秒
				long residualMills = calcResidual(treasurePen);

				// 消耗道具
				if (spendResult.getItemTimes() > 0) {
					result = new RefreshResultImpl(now, residualMills, treasures, spendResult.getTotalTimes(),
							spendResult.getItems());
				}
				// 消耗儲值幣
				else {
					result = new RefreshResultImpl(now, residualMills, treasures, spendResult.getTotalTimes(),
							spendResult.getCoin());
				}
			} else {
				errorType = roleErrorToTreasureError(spendError);
			}
		}

		// 發訊息
		if (sendable) {
			// 發送祕寶欄位
			if (errorType == ErrorType.NO_ERROR && treasurePen != null) {
				sendTreasurePen(role, treasurePen);
			}
			// 刷新訊息
			sendRefresh(errorType, role);
		}
		//
		return result;
	}

	/**
	 * role的errorType轉換成treasure的errorType
	 * 
	 * @param roleError
	 * @return
	 */
	protected ErrorType roleErrorToTreasureError(RoleService.ErrorType roleError) {
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
	 * 檢查刷新祕寶
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkRefresh(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < treasureCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送刷新祕寶
	 * 
	 * @param errorType
	 * @param role
	 * @return
	 */
	public Message sendRefresh(ErrorType errorType, Role role) {
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_REFRESH_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
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
	 * 購買秘寶結果
	 */
	public static class BuyResultImpl extends AppResultSupporter implements BuyResult {

		private static final long serialVersionUID = 9098649255414545945L;

		/**
		 * 購買類別
		 */
		private BuyType buyType;

		/**
		 * 上架祕寶索引
		 */
		private int index;

		/**
		 * 上架祕寶
		 */
		private Treasure treasure;

		/**
		 * 購買的道具
		 */
		private Item item;

		/**
		 * 消耗的金幣
		 */
		private long spendGold;

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		public BuyResultImpl(BuyType buyType, int index, Treasure treasure, Item item, long spendGold, int spendCoin) {
			this.buyType = buyType;
			this.index = index;
			this.treasure = treasure;
			this.item = item;
			//
			this.spendGold = spendGold;
			this.spendCoin = spendCoin;
		}

		/**
		 * 金幣購買祕寶用的建構子
		 * 
		 * @param buyType
		 * @param index
		 * @param treasure
		 * @param item
		 * @param spendGold
		 */
		public BuyResultImpl(BuyType buyType, int index, Treasure treasure, Item item, long spendGold) {
			this(buyType, index, treasure, item, spendGold, 0);
		}

		/**
		 * 儲值幣購買祕寶用的建構子
		 * 
		 * @param buyType
		 * @param index
		 * @param treasure
		 * @param item
		 * @param spendCoin
		 */
		public BuyResultImpl(BuyType buyType, int index, Treasure treasure, Item item, int spendCoin) {
			this(buyType, index, treasure, item, 0L, spendCoin);
		}

		public BuyResultImpl() {
			this(null, -1, null, null, 0L, 0);
		}

		public BuyType getBuyType() {
			return buyType;
		}

		public void setBuyType(BuyType buyType) {
			this.buyType = buyType;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public Treasure getTreasure() {
			return treasure;
		}

		public void setTreasure(Treasure treasure) {
			this.treasure = treasure;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public long getSpendGold() {
			return spendGold;
		}

		public void setSpendGold(long spendGold) {
			this.spendGold = spendGold;
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
			builder.append("buyType", buyType);
			builder.append("index", index);
			builder.append("treasure", (treasure != null ? treasure.getId() : null));
			builder.append("item", (item != null ? item.getId() + ", " + item.getUniqueId() : null));
			builder.append("spendGold", spendGold);
			builder.append("spendCoin", spendCoin);
			return builder.toString();
		}

		public Object clone() {
			BuyResultImpl copy = null;
			copy = (BuyResultImpl) super.clone();
			copy.treasure = clone(treasure);
			copy.item = clone(item);
			return copy;
		}
	}

	/**
	 * 購買秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @param buyValue
	 *            購買類別 @see BuyType
	 * @param index
	 * @return
	 */
	public BuyResult buy(boolean sendable, Role role, int buyValue, int index) {
		BuyResult result = null;
		// 購買類別
		BuyType buyType = EnumHelper.valueOf(BuyType.class, buyValue);
		if (buyType == null) {
			return result;
		}
		//
		switch (buyType) {
		case GOLD: {
			// 金幣購買
			result = goldBuy(sendable, role, index);
			break;
		}
		case COIN: {
			// 儲值幣購買
			result = coinBuy(sendable, role, index);
			break;
		}
		default: {
			if (sendable) {
				// 購買類別不存在
				sendBuy(ErrorType.BUY_TYPE_NOT_EXIST, role, result);
			}
			break;
		}
		}
		//
		return result;
	}

	/**
	 * 金幣購買秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @param index
	 * @return
	 */
	public BuyResult goldBuy(boolean sendable, Role role, int index) {
		BuyResult result = null;
		Notice notice = null;// 已購買的祕寶訊息
		//
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkGoldBuy(role, index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 祕寶
			TreasurePen treasurePen = role.getTreasurePen();
			Treasure treasure = treasurePen.getTreasures().get(index);
			Item item = itemService.createItem(treasure.getId(), treasure.getAmount());

			// 花費的金幣
			long spendGold = treasure.getAmount() * item.getPrice();
			// 扣金幣
			long decreaseGold = roleService.decreaseGold(true, role, spendGold, GoldType.TREASURE_BUY);
			// 成功
			if (decreaseGold != 0) {
				// 增加多個道具
				List<IncreaseItemResult> increaseResults = itemService.increaseItem(sendable, role, item);
				// 成功
				if (increaseResults.size() > 0) {
					treasure.setBought(true);// 已購買
					//
					IncreaseItemResult increaseResult = increaseResults.get(0);
					int tabIndex = increaseResult.getTabIndex();
					int gridIndex = increaseResult.getGridIndex();
					// 包包
					BagPen bagPen = role.getBagPen();
					Item bagItem = bagPen.getItem(tabIndex, gridIndex);// 放入包包後的道具

					// 購買的道具,拿包包內道具的uniqueId
					item.setUniqueId(bagItem.getUniqueId());

					// 加到已購買的祕寶,人氣榜
					notice = addNotice(role, treasure, item);

					// 結果
					result = new BuyResultImpl(BuyType.GOLD, index, treasure, item, spendGold);
				} else {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
				}
			} else {
				errorType = ErrorType.GOLD_NOT_ENOUGH;
			}
		}

		// 發訊息
		if (sendable) {
			sendBuy(errorType, role, result);
			//
			if (result != null) {
				// 公告通知
				sendNotice(notice);
				// 成名
				sendFamousBuy(role, result.getTreasure(), result.getItem());
			}
		}
		return result;
	}

	/**
	 * 檢查金幣購買秘寶
	 * 
	 * @param role
	 * @param index
	 * @return
	 */
	public ErrorType checkGoldBuy(Role role, int index) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < treasureCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 祕寶
		TreasurePen treasurePen = role.getTreasurePen();
		Treasure treasure = treasurePen.getTreasures().get(index);
		// 祕寶不存在
		if (treasure == null) {
			errorType = ErrorType.TREASURE_NOT_EXIST;
			return errorType;
		}

		// 是否已購買
		if (treasure.isBought()) {
			errorType = ErrorType.ALREADY_BUY;
			return errorType;
		}

		// 道具不存在
		Item item = itemService.createItem(treasure.getId(), treasure.getAmount());
		if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 道具金幣價格為0
		long price = item.getPrice();
		if (price == 0) {
			errorType = ErrorType.ITEM_PRICE_IS_ZERO;
			return errorType;
		}

		// 金幣不足
		long spendGold = treasure.getAmount() * price;
		boolean checkDecreaseGold = roleService.checkDecreaseGold(role, spendGold);
		if (!checkDecreaseGold) {
			errorType = ErrorType.GOLD_NOT_ENOUGH;
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
	 * 儲值幣購買祕寶
	 * 
	 * @param sendable
	 * @param role
	 * @param index
	 * @return
	 */
	public BuyResult coinBuy(boolean sendable, Role role, int index) {
		BuyResult result = null;
		Notice notice = null;// 已購買的祕寶訊息
		//
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkCoinBuy(role, index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 祕寶
			TreasurePen treasurePen = role.getTreasurePen();
			Treasure treasure = treasurePen.getTreasures().get(index);
			Item item = itemService.createItem(treasure.getId(), treasure.getAmount());

			// 花費的儲值幣
			int spendCoin = treasure.getAmount() * item.getCoin();
			// 扣儲值幣
			int decrease = accountService.decreaseCoin(sendable, role.getAccountId(), role, spendCoin,
					CoinType.TREASURE_BUY);
			// 成功
			if (decrease != 0) {
				// 增加多個道具
				List<IncreaseItemResult> increaseResults = itemService.increaseItem(sendable, role, item);
				// 成功
				if (increaseResults.size() > 0) {
					treasure.setBought(true);// 已購買
					//
					IncreaseItemResult increaseResult = increaseResults.get(0);
					int tabIndex = increaseResult.getTabIndex();
					int gridIndex = increaseResult.getGridIndex();
					// 包包
					BagPen bagPen = role.getBagPen();
					Item bagItem = bagPen.getItem(tabIndex, gridIndex);// 放入包包後的道具
					// 為了拿道具的uniqueId
					item.setUniqueId(bagItem.getUniqueId());

					// 加到已購買的祕寶,人氣榜
					notice = addNotice(role, treasure, item);

					// 結果
					result = new BuyResultImpl(BuyType.COIN, index, treasure, item, spendCoin);
				} else {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
				}
			} else {
				errorType = ErrorType.COIN_NOT_ENOUGH;
			}
		}

		// 發訊息
		if (sendable) {
			sendBuy(errorType, role, result);
			//
			if (result != null) {
				// 公告通知
				sendNotice(notice);
				// 成名
				sendFamousBuy(role, result.getTreasure(), result.getItem());
			}
		}
		return result;
	}

	/**
	 * 檢查儲值幣購買祕寶
	 * 
	 * @param role
	 * @param index
	 * @return
	 */
	public ErrorType checkCoinBuy(Role role, int index) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < treasureCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 祕寶
		TreasurePen treasurePen = role.getTreasurePen();
		Treasure treasure = treasurePen.getTreasures().get(index);
		// 祕寶不存在
		if (treasure == null) {
			errorType = ErrorType.TREASURE_NOT_EXIST;
			return errorType;
		}

		// 是否已購買
		if (treasure.isBought()) {
			errorType = ErrorType.ALREADY_BUY;
			return errorType;
		}

		// 道具不存在
		Item item = itemService.createItem(treasure.getId(), treasure.getAmount());
		if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 道具儲值幣價格為0
		int coin = item.getCoin();
		if (coin == 0) {
			errorType = ErrorType.ITEM_COIN_IS_ZERO;
			return errorType;
		}

		// vip不足
		if (role.getVipType().getValue() < vipCollector.getTreasureCoinVipType().getValue()) {
			errorType = ErrorType.VIP_NOT_ENOUGH;
			return errorType;
		}

		// 儲值幣不足
		int spendCoin = treasure.getAmount() * coin;
		boolean checkDecrease = accountService.checkDecreaseCoin(role.getAccountId(), spendCoin);
		if (!checkDecrease) {
			errorType = ErrorType.COIN_NOT_ENOUGH;
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
	 * 發送購買祕寶
	 * 
	 * @param errorType
	 * @param role
	 * @param buyResult
	 * @return
	 */
	public Message sendBuy(ErrorType errorType, Role role, BuyResult buyResult) {
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_BUY_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addInt(buyResult.getBuyType());// 購買類型
			message.addInt(buyResult.getIndex());// 上架祕寶索引
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
	 * 發送成名購買祕寶
	 * 
	 * @param role
	 * @param treasure
	 * @param item
	 * @return
	 */
	protected Message sendFamousBuy(Role role, Treasure treasure, Item item) {
		if (treasure == null || !treasure.isFamous()) {
			return null;
		}
		// 取所有角色id
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_FAMOUS_BUY_RESPONSE, receivers);

		message.addString(role.getName());// 角色名稱
		message.addString(item.getName());// 道具名稱
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 加到已購買的祕寶,人氣榜
	 * 
	 * @param role
	 * @param treasure
	 * @param item
	 * @return
	 */
	protected Notice addNotice(Role role, Treasure treasure, Item item) {
		Notice result = null;
		// 有成名的祕寶,才加到人氣榜
		// if (treasure != null && treasure.isFamous())

		// 改為只要購買就加到人氣榜,2013/04/03
		if (treasure != null) {
			if (board.size() >= MAX_NOTICE_SIZE) {
				board.poll();
			}
			//
			result = new NoticeImpl();
			result.setRoleName(role.getName());
			// 取道具名稱
			Map<String, Integer> nameAwards = new LinkedHashMap<String, Integer>();
			nameAwards.put(item.getName(), item.getAmount());
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
	 * 發送已購買的祕寶訊息
	 * 
	 * @param notice
	 * @return
	 */
	protected Message sendNotice(Notice notice) {
		if (notice == null) {
			return null;
		}
		// 取所有角色id,只限本地
		List<String> receivers = roleSetService.getRoleIds(false);
		//
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_NOTICE_RESPONSE, receivers);

		fillNotice(message, notice);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充已購買的祕寶訊息
	 * 
	 * @param message
	 * @param notice
	 * @return
	 */
	protected boolean fillNotice(Message message, Notice notice) {
		boolean result = false;
		//
		message.addString(notice.getRoleName());
		// 已經是道具名稱,因addNotice已處理
		Map<String, Integer> awards = notice.getAwards();
		message.addInt(awards.size());
		for (Map.Entry<String, Integer> entry : awards.entrySet()) {
			message.addString(entry.getKey());
			message.addInt(entry.getValue());
		}

		result = true;
		return result;
	}

	/**
	 * 發送已購買的祕寶,人氣榜
	 * 
	 * @param role
	 * @return
	 */
	protected Message sendBoard(Role role) {
		Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT,
				CoreMessageType.TREASURE_BOARD_RESPONSE, role.getId());

		message.addInt(board.size());
		for (Notice notice : board) {
			fillNotice(message, notice);
		}
		//
		messageService.addMessage(message);

		return message;
	}

}
