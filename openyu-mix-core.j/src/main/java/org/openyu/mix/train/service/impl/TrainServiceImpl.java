package org.openyu.mix.train.service.impl;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.service.RoleService.SpendResult;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.train.service.TrainSetService;
import org.openyu.mix.train.vo.TrainCollector;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.anno.DefaultThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.commons.util.CalendarHelper;
import org.openyu.commons.util.DateHelper;
import org.openyu.socklet.message.vo.Message;

/**
 * 訓練服務
 */
public class TrainServiceImpl extends AppServiceSupporter implements TrainService {

	private static final long serialVersionUID = -4308347953505176980L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TrainServiceImpl.class);

	@DefaultThreadService
	private transient ThreadService threadService;

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
	@Qualifier("trainSetService")
	protected transient TrainSetService trainSetService;

	private transient TrainCollector trainCollector = TrainCollector.getInstance();

	private transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 監聽
	 */
	private transient TrainListenRunner trainListenRunner;

	public TrainServiceImpl() {
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.trainListenRunner = new TrainListenRunner(threadService);
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.trainListenRunner.shutdown();
	}

	/**
	 * 監聽
	 */
	protected class TrainListenRunner extends BaseRunnableSupporter {
		public TrainListenRunner(ThreadService threadService) {
			super(threadService);
		}

		@Override
		protected void doRun() throws Exception {
			while (true) {
				try {
					listen();
					// 訓練周期,間隔毫秒
					ThreadHelper.sleep(trainCollector.getIntervalMills());
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
	 * 剩餘期間, residualMills = joinMills - (now - plantTime)
	 * 
	 * @return
	 */
	protected void listen() {
		List<Role> removeRoles = new LinkedList<Role>();// 移除的角色
		// 所有已加入的訓練角色
		for (Role role : trainSetService.getRoles().values()) {
			try {
				TrainPen trainPen = role.getTrainPen();
				// 剩餘毫秒
				long residualMills = calcResidual(trainPen);
				// System.out.println("剩餘時間: " + residualMills);

				// 訓練時間滿了,就該離開
				if (residualMills <= 0) {
					removeRoles.add(role);// 移除的角色
				} else {
					// 增加每天已訓練毫秒
					trainPen.addDailyMills(trainCollector.getIntervalMills());
					// exp=exp * (1 + 活動增加的比率+ 鼓舞增加的比率);
					// 等級經驗
					long levelExp = trainCollector.getExps().get(role.getLevel());

					// 活動增加的經驗
					int activityRate = trainCollector.getActivityRate();
					long activityExp = 0L;
					if (activityRate > 0) {
						activityExp = (long) (levelExp * ratio(activityRate));
					}

					// 鼓舞增加的經驗
					int inspireRate = trainCollector.getInspireRate();
					long inspireExp = 0L;
					if (trainPen.getInspireTime() > 0 && inspireRate > 0) {
						inspireExp = (long) (levelExp * ratio(inspireRate));
					}

					// 經驗=等級經驗+活動增加的經驗+鼓舞增加的經驗+(其他增加的...)
					long exp = levelExp + activityExp + inspireExp;
					// System.out.println(levelExp + " " + activityExp + " " +
					// inspireExp);

					// 增加經驗
					roleService.changeExp(true, role, exp);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				//
				removeRoles.add(role);// 移除的角色
			}
		}

		// 移除訓練中的角色
		if (removeRoles.size() > 0) {
			for (Role role : removeRoles) {
				String roleId = role.getId();
				Role trainRole = trainSetService.removeRole(roleId);
				// 移除成功
				if (trainRole != null) {
					long now = System.currentTimeMillis();
					TrainPen trainPen = role.getTrainPen();
					trainPen.setQuitTime(now);// 結束時間
					// 發訊息
					sendQuit(ErrorType.NO_ERROR, role, now);
				}
			}
		}
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
		// 重置每日可訓練毫秒限制,不發訊息,因會在sendConnect發送
		reset(false, result);

		// 發送連線
		sendRoleConnect(result, attatch);

		// 已連線
		result.getTrainPen().setConnected(true);
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
		return message;
	}

	protected Message sendInitialize(Role role) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_INITIALIZE_RESPONSE, role.getId());

		// 訓練欄位
		fillTrainPen(message, role.getTrainPen());
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

		// 是否訓練中
		boolean contains = trainSetService.containRole(roleId);
		if (contains) {
			Role trainRole = trainSetService.removeRole(roleId);
			// 移除成功
			if (trainRole != null) {
				long now = System.currentTimeMillis();
				TrainPen trainPen = result.getTrainPen();
				trainPen.setQuitTime(now);// 結束時間
			}
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
	 * 發送訓練欄位
	 * 
	 * @param role
	 * @param trainPen
	 */
	public void sendTrainPen(Role role, TrainPen trainPen) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_PEN_RESPONSE, role.getId());

		fillTrainPen(message, trainPen);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充訓練欄位
	 * 
	 * @param message
	 * @param trainPen
	 */
	public void fillTrainPen(Message message, TrainPen trainPen) {
		message.addLong(trainPen.getJoinTime());// 加入時間
		message.addLong(trainPen.getQuitTime());// 離開時間
		// 每天已訓練毫秒
		message.addLong(trainPen.getDailyMills());
		// 鼓舞時間
		message.addLong(trainPen.getInspireTime());
		// 剩餘毫秒
		long residualMills = calcResidual(trainPen);
		message.addLong(residualMills);
	}

	/**
	 * 計算訓練剩餘時間
	 * 
	 * @return
	 */
	public long calcResidual(TrainPen trainPen) {
		long result = 0L;
		// 剩餘毫秒
		result = trainCollector.getDailyMills() - trainPen.getDailyMills();
		//
		return (result > 0 ? result : 0);
	}

	/**
	 * 加入訓練結果
	 */
	public static class JoinResultImpl extends AppResultSupporter implements JoinResult {

		private static final long serialVersionUID = -4672126667914443523L;

		/**
		 * 加入時間
		 */
		private long joinTime;

		/**
		 * 離開時間
		 */
		private long quitTime;

		/**
		 * 剩餘毫秒
		 */
		private long residualMills;

		public JoinResultImpl(long joinTime, long quitTime, long residualMills) {
			this.joinTime = joinTime;
			this.quitTime = quitTime;
			this.residualMills = residualMills;
		}

		public JoinResultImpl() {
			this(0L, 0L, 0L);
		}

		public long getJoinTime() {
			return joinTime;
		}

		public void setJoinTime(long joinTime) {
			this.joinTime = joinTime;
		}

		public long getQuitTime() {
			return quitTime;
		}

		public void setQuitTime(long quitTime) {
			this.quitTime = quitTime;
		}

		public long getResidualMills() {
			return residualMills;
		}

		public void setResidualMills(long residualMills) {
			this.residualMills = residualMills;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("joinTime", joinTime);
			builder.append("quitTime", quitTime);
			builder.append("residualMills", residualMills);
			return builder.toString();
		}

		public Object clone() {
			JoinResultImpl copy = null;
			copy = (JoinResultImpl) super.clone();
			return copy;
		}
	}

	/**
	 * 加入訓練
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public JoinResult join(boolean sendable, Role role) {
		JoinResult result = null;
		TrainPen trainPen = null;
		// 檢查條件
		ErrorType errorType = checkJoin(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 角色
			// 訓練
			trainPen = role.getTrainPen();

			long now = System.currentTimeMillis();
			trainPen.setJoinTime(now);// 開始時間
			trainPen.setQuitTime(0);// 結束時間

			// 加入訓練角色
			trainSetService.addRole(role);
			// 剩餘毫秒
			long residualMills = calcResidual(trainPen);
			// 結果
			result = new JoinResultImpl(now, 0, residualMills);
		}

		// 發訊息
		if (sendable) {
			// 發送訓練欄位
			if (errorType == ErrorType.NO_ERROR && trainPen != null) {
				sendTrainPen(role, trainPen);
			}
			//
			sendJoin(errorType, role);
		}
		//
		return result;
	}

	/**
	 * 檢查加入訓練
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkJoin(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < trainCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 已經加入訓練了
		boolean contains = trainSetService.containRole(role);
		if (contains) {
			errorType = ErrorType.ALREADY_JOIN;
			return errorType;
		}

		TrainPen trainPen = role.getTrainPen();
		// 剩餘毫秒
		long residualMills = calcResidual(trainPen);
		// 超過每天可訓練毫秒
		if (residualMills <= 0) {
			errorType = ErrorType.OVER_DAILY_MILLS;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送加入訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	public void sendJoin(ErrorType errorType, Role role) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_JOIN_RESPONSE, role.getId());

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
	}

	/**
	 * 離開訓練結果
	 */
	public static class QuitResultImpl extends JoinResultImpl implements QuitResult {
		private static final long serialVersionUID = -847990980872370073L;

		public QuitResultImpl(long joinTime, long quitTime, long residualMills) {
			super(joinTime, quitTime, residualMills);
		}

		public QuitResultImpl() {
			this(0L, 0L, 0L);
		}
	}

	/**
	 * 離開訓練
	 * 
	 * @param sendable
	 * @param role
	 */
	public QuitResult quit(boolean sendable, Role role) {
		QuitResult result = null;
		TrainPen trainPen = null;
		long now = System.currentTimeMillis();
		// 檢查條件
		ErrorType errorType = checkQuit(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 訓練
			trainPen = role.getTrainPen();

			// 是否訓練中
			boolean contains = trainSetService.containRole(role);
			if (contains) {
				Role trainRole = trainSetService.removeRole(role);
				// 移除成功
				if (trainRole != null) {
					trainPen.setQuitTime(now);// 結束時間
					// 剩餘毫秒
					long residualMills = calcResidual(trainPen);
					// 結果
					result = new QuitResultImpl(trainPen.getJoinTime(), now, residualMills);
				}
				// 無法離開
				else {
					errorType = ErrorType.CAN_NOT_QUIT;
				}
			}
		}

		// 發訊息
		if (sendable) {
			sendQuit(errorType, role, now);
		}
		//
		return result;
	}

	/**
	 * 檢查離開訓練
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkQuit(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 還沒加入訓練
		boolean contains = trainSetService.containRole(role);
		if (!contains) {
			errorType = ErrorType.NOT_JOIN;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送離開訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	public void sendQuit(ErrorType errorType, Role role, long quitTime) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_QUIT_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addLong(quitTime);
			break;
		}
		default: {
			break;
		}
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 鼓舞訓練結果
	 */
	public static class InspireResultImpl extends JoinResultImpl implements InspireResult {

		private static final long serialVersionUID = -5194549511933383673L;

		/**
		 * 鼓舞時間
		 */
		private long inspireTime;

		/**
		 * 消耗的道具
		 */
		private List<Item> spendItems = new LinkedList<Item>();

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		public InspireResultImpl(long inspireTime, List<Item> spendItems, int spendCoin) {
			this.inspireTime = inspireTime;
			//
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		public InspireResultImpl(long inspireTime, List<Item> spendItems) {
			this(inspireTime, spendItems, 0);
		}

		public InspireResultImpl(long inspireTime, int spendCoin) {
			this(inspireTime, new LinkedList<Item>(), spendCoin);
		}

		public long getInspireTime() {
			return inspireTime;
		}

		public void setInspireTime(long inspireTime) {
			this.inspireTime = inspireTime;
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
			builder.append("inspireTime", inspireTime);
			//
			append(builder, "spendItems", spendItems);
			builder.append("spendCoin", spendCoin);
			return builder.toString();
		}

		public Object clone() {
			InspireResultImpl copy = null;
			copy = (InspireResultImpl) super.clone();
			copy.spendItems = clone(spendItems);
			return copy;
		}
	}

	/**
	 * 鼓舞訓練
	 * 
	 * @param sendable
	 * @param role
	 */
	public InspireResult inspire(boolean sendable, Role role) {
		InspireResult result = null;
		long now = System.currentTimeMillis();
		// 檢查條件
		ErrorType errorType = checkInspire(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 訓練
			TrainPen trainPen = role.getTrainPen();

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, trainCollector.getInspireItem(), 1,
					trainCollector.getInspireCoin(), CoinType.TRAIN_INSPIRE, vipCollector.getTrainCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (spendError == RoleService.ErrorType.NO_ERROR) {
				trainPen.setInspireTime(now);// 鼓舞時間
				// 消耗道具
				if (spendResult.getItemTimes() > 0)

				{
					result = new InspireResultImpl(now, spendResult.getItems());
				}
				// 消耗儲值幣
				else {
					result = new InspireResultImpl(now, spendResult.getCoin());
				}
			} else {
				errorType = roleErrorToTrainError(spendError);
			}
		}

		// 發訊息
		if (sendable) {
			sendInspire(errorType, role, now);
		}
		//
		return result;
	}

	/**
	 * role的errorType轉換成train的errorType
	 * 
	 * @param roleError
	 * @return
	 */
	protected ErrorType roleErrorToTrainError(RoleService.ErrorType roleError) {
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
	 * 檢查鼓舞訓練
	 * 
	 * @param role
	 * @return
	 */
	public ErrorType checkInspire(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < trainCollector.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 還沒加入訓練
		boolean contains = trainSetService.containRole(role);
		if (!contains) {
			errorType = ErrorType.NOT_JOIN;
			return errorType;
		}

		// 已經鼓舞了
		TrainPen trainPen = role.getTrainPen();
		if (trainPen.getInspireTime() > 0) {
			errorType = ErrorType.ALREADY_INSPIRE;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送鼓舞訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	public void sendInspire(ErrorType errorType, Role role, long inspireTime) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_INSPIRE_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addLong(inspireTime);
			break;
		}
		default: {
			break;
		}
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 重置每日可訓練毫秒限制
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public boolean reset(boolean sendable, Role role) {
		boolean result = false;
		TrainPen trainPen = null;
		// 檢查條件
		ErrorType errorType = checkReset(role);
		// 超過重置時間
		if (errorType == ErrorType.OVER_RESET_TIME) {
			// 訓練
			trainPen = role.getTrainPen();
			// 重置
			result = trainPen.reset();
		}

		// 發訊息
		if (sendable && result) {
			sendReset(role, trainPen);
		}
		//
		return result;
	}

	/**
	 * 檢查重置訓練
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
		TrainPen trainPen = role.getTrainPen();
		// 今日早上0點
		Calendar today = CalendarHelper.today(0, 0, 0);
		// 明日早上0點
		Calendar tomorrow = CalendarHelper.tomorrow(0, 0, 0);

		// 訓練加入時間
		long joinTime = trainPen.getJoinTime();
		boolean overTime = DateHelper.isOverTime(joinTime, System.currentTimeMillis(), today.getTimeInMillis(),
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
	 * 發送重置訓練
	 * 
	 * @param role
	 * @param trainPen
	 */
	public void sendReset(Role role, TrainPen trainPen) {
		Message message = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT,
				CoreMessageType.TRAIN_RESET_RESPONSE, role.getId());

		fillTrainPen(message, trainPen);
		//
		messageService.addMessage(message);
	}

	/**
	 * 重置每日可訓練毫秒限制,所有線上玩家
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
				if (!role.isConnected() || !role.getTrainPen().isConnected()) {
					continue;
				}
				//
				String roleId = role.getId();
				// 是否訓練中
				boolean contains = trainSetService.containRole(roleId);
				if (contains) {
					Role trainRole = trainSetService.removeRole(roleId);
					// 移除成功
					if (trainRole != null) {
						long now = System.currentTimeMillis();
						TrainPen trainPen = role.getTrainPen();
						trainPen.setQuitTime(now);// 結束時間
						// 發訊息
						sendQuit(ErrorType.NO_ERROR, role, now);
					}
				}

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
