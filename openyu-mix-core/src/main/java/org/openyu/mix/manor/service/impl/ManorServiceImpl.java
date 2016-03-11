package org.openyu.mix.manor.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.ManorInfo.Farm;
import org.openyu.mix.manor.vo.ManorInfo.FarmType;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleRepository;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.service.RoleService.SpendResult;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.socklet.message.vo.Message;

public class ManorServiceImpl extends AppServiceSupporter implements ManorService {

	private static final long serialVersionUID = -1082911837246717368L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ManorServiceImpl.class);

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
	@Qualifier("roleRepository")
	protected transient RoleRepository roleRepository;

	private transient ManorCollector manorCollector = ManorCollector.getInstance();

	private transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 監聽毫秒
	 */
	private long LISTEN_MILLS = 10 * 1000L;

	/**
	 * 監聽
	 */
	private transient ManorListenRunner manorListenRunner;

	public ManorServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {

	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.manorListenRunner = new ManorListenRunner(threadService);
		this.manorListenRunner.start();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.manorListenRunner.shutdown();
	}

	// --------------------------------------------------

	/**
	 * 監聽
	 */
	protected class ManorListenRunner extends BaseRunnableSupporter {

		public ManorListenRunner(ThreadService threadService) {
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
	 * 經過毫秒, passMills = (now - plantTime)
	 * 
	 * 剩餘毫秒, residualMills = growMills - (now - plantTime)
	 * 
	 * @return
	 */
	protected void listen() {
		// false=只有本地
		for (Role role : roleRepository.getRoles(false).values()) {
			try {
				// 角色是否已連線
				if (!role.isConnected() || !role.getManorInfo().isConnected()) {
					continue;
				}

				// 處理種子狀態
				handleSeed(role);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 處理種子狀態
	 * 
	 * @param role
	 */
	protected void handleSeed(Role role) {
		ManorInfo manorInfo = role.getManorInfo();
		for (Farm farm : manorInfo.getFarms().values()) {
			int farmIndex = farm.getId();
			// 土地
			Land land = farm.getLand();
			for (Map.Entry<Integer, Seed> entry : farm.getSeeds().entrySet()) {
				int gridIndex = entry.getKey();
				Seed seed = entry.getValue();
				MatureType matureType = seed.getMatureType();
				if (matureType == null) {
					continue;
				}
				// 目前時間
				long now = System.currentTimeMillis();
				switch (matureType) {
				case GROWING: {
					// 剩餘毫秒
					long residualMills = calcResidual(land, seed);
					// System.out.println("剩餘毫秒: " + residualMills);
					// 成熟了
					if (residualMills <= 0) {
						// 成熟
						seed.setMatureType(MatureType.MATURE);
						seed.setMatureTime(now);
						// 發訊息
						CultureResult cultureResult = new CultureResultImpl(CultureType.PLANT, farmIndex, gridIndex,
								seed, residualMills);
						sendCulture(ErrorType.NO_ERROR, role, cultureResult);
					}
					break;
				}
				case MATURE: {
					// 成熟經過期間
					long matureMills = now - seed.getMatureTime();
					// 超過收割時間,枯萎了
					if (matureMills >= seed.getReapMills()) {
						seed.setMatureType(MatureType.WITHER);
						// 發訊息
						CultureResult cultureResult = new CultureResultImpl(CultureType.PLANT, farmIndex, gridIndex,
								seed, 0L);
						sendCulture(ErrorType.NO_ERROR, role, cultureResult);
					}
					break;
				}
				default: {
					break;
				}
				}
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
		Role result = roleRepository.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 處理種子狀態
		handleSeed(result);

		// 發送連線
		sendRoleConnect(result, attatch);

		// 已連線
		result.getManorInfo().setConnected(true);
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
		Message message = sendInitialize(role);
		return message;
	}

	protected Message sendInitialize(Role role) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_INITIALIZE_RESPONSE, role.getId());

		// 莊園
		fillManorInfo(message, role.getManorInfo());
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
		Role result = roleRepository.getRole(roleId);
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
	 * 改變所有的農場頁是否鎖定
	 * 
	 * @param sendable
	 * @param role
	 */
	public boolean changeManorInfoLocked(boolean sendable, Role role) {
		boolean result = false;
		//
		ManorInfo manorInfo = role.getManorInfo();
		FarmType[] tabTypes = FarmType.values();
		for (FarmType farmType : tabTypes) {
			int index = farmType.getValue();
			// 由農場頁類型,取得vip類型
			VipType vipType = vipCollector.getVipType(farmType);
			if (vipType == null || role.getVipType().getValue() >= vipType.getValue()) {
				manorInfo.unLock(index);
			} else {
				manorInfo.lock(index);
			}
			//
			result = true;
		}
		//
		if (sendable) {
			sendFarmsLocked(role.getId(), manorInfo);
		}
		//
		return result;
	}

	/**
	 * 發送單一農場頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param tab
	 */
	protected void sendFarmLocked(String roleId, Farm farm) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_FARM_LOCKED_RESPONSE, roleId);

		fillFarmLocked(message, farm);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充農場是否鎖定
	 * 
	 * @param message
	 * @param tab
	 */
	protected void fillFarmLocked(Message message, Farm farm) {
		message.addInt(farm.getId());// farmIndex
		message.addBoolean(farm.isLocked());// 是否鎖定
	}

	/**
	 * 發送所有農場頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param bagInfo
	 */
	protected void sendFarmsLocked(String roleId, ManorInfo manorInfo) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_FARMS_LOCKED_RESPONSE, roleId);

		Map<Integer, Farm> tabs = manorInfo.getFarms();// 包含被鎖定的農場頁
		message.addInt(tabs.size());
		for (Farm tab : tabs.values()) {
			fillFarmLocked(message, tab);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 發送莊園欄位
	 * 
	 * @param role
	 * @param bagInfo
	 */
	public Message sendManorInfo(Role role, ManorInfo manorInfo) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_PEN_RESPONSE, role.getId());

		fillManorInfo(message, manorInfo);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充莊園欄位
	 * 
	 * @param message
	 * @param manorInfo
	 */
	public boolean fillManorInfo(Message message, ManorInfo manorInfo) {
		boolean result = false;
		// 已解鎖農場頁size
		message.addInt(manorInfo.getFarmSize());
		//
		Map<Integer, Farm> farms = manorInfo.getFarms();// 包含被鎖定的農場頁
		for (Farm farm : farms.values()) {
			// 可能被鎖定
			if (farm.isLocked()) {
				continue;
			}
			//
			fillFarm(message, farm);
		}

		result = true;
		return result;
	}

	/**
	 * 發送農場頁
	 * 
	 * @param role
	 * @param farm
	 */
	public Message sendFarm(Role role, Farm farm) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_FARM_RESPONSE, role.getId());

		fillFarm(message, farm);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充農場頁
	 * 
	 * @param message
	 * @param farm
	 */
	public boolean fillFarm(Message message, Farm farm) {
		boolean result = false;
		message.addInt(farm.getId());// farmIndex
		// 土地
		Land land = farm.getLand();
		// 是否有土地,可能沒開墾,就沒土地可用
		boolean hadLand = (land != null);
		message.addBoolean(hadLand);
		//
		if (hadLand) {
			itemService.fillItem(message, land);
		}
		// 已放種子的格子數量
		message.addInt(farm.getSeedSize());
		//
		Map<Integer, Seed> seeds = farm.getSeeds();
		for (Map.Entry<Integer, Seed> entry : seeds.entrySet()) {
			// 格子索引
			int gridIndex = entry.getKey();
			// 種子
			Seed seed = entry.getValue();
			if (seed == null) {
				continue;
			}
			//
			message.addInt(gridIndex);// gridIndex
			//
			itemService.fillItem(message, seed);
			// 剩餘毫秒
			long residualMills = calcResidual(land, seed);
			message.addLong(residualMills);
		}

		result = true;
		return result;
	}

	/**
	 * 開墾結果
	 */
	public static class ReclaimResultImpl extends AppResultSupporter implements ReclaimResult {

		private static final long serialVersionUID = -7956428086169764989L;

		/**
		 * 農場索引
		 */
		private int farmIndex;

		/**
		 * 土地
		 */
		private Land land;

		/**
		 * 花費的金幣
		 */
		private long spendGold;

		public ReclaimResultImpl(int farmIndex, Land land, long spendGold) {
			this.farmIndex = farmIndex;
			this.land = land;
			this.spendGold = spendGold;
		}

		public ReclaimResultImpl() {
			this(-1, null, 0L);
		}

		public int getFarmIndex() {
			return farmIndex;
		}

		public void setFarmIndex(int farmIndex) {
			this.farmIndex = farmIndex;
		}

		public Land getLand() {
			return land;
		}

		public void setLand(Land land) {
			this.land = land;
		}

		public long getSpendGold() {
			return spendGold;
		}

		public void setSpendGold(long spendGold) {
			this.spendGold = spendGold;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("farmIndex", farmIndex);
			builder.append("land", (land != null ? land.getId() + ", " + land.getUniqueId() : null));
			builder.append("spendGold", spendGold);
			return builder.toString();
		}

		public Object clone() {
			ReclaimResultImpl copy = null;
			copy = (ReclaimResultImpl) super.clone();
			copy.land = clone(land);
			return copy;
		}
	}

	/**
	 * 開墾
	 * 
	 * @param role
	 * @param farmIndex
	 * @param landUniqueId
	 */
	public ReclaimResult reclaim(boolean sendable, Role role, int farmIndex, String landUniqueId) {
		ReclaimResult result = null;
		Land land = null;
		// 檢查條件
		ErrorType errorType = checkReclaim(role, farmIndex, landUniqueId);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 土地
			land = (Land) itemService.getItem(role, landUniqueId);

			// 扣金幣
			long spendGold = manorCollector.getReclaimGold();
			long decreaseGold = roleService.decreaseGold(sendable, role, spendGold, GoldType.MANOR_RECLAIM);
			// 成功
			if (decreaseGold != 0) {
				// 把土地從包包扣除
				List<DecreaseItemResult> decreaseResults = itemService.decreaseItem(sendable, role, land);
				// 成功
				if (decreaseResults.size() > 0) {
					// 農場
					Farm farm = manorInfo.getFarm(farmIndex);
					farm.setLand(land);

					// 開墾結果
					result = new ReclaimResultImpl(farmIndex, land, spendGold);
				} else {
					errorType = ErrorType.CAN_NOT_DECREASE_LAND;
				}
			} else {
				errorType = ErrorType.GOLD_NOT_ENOUGH;
			}
		}

		// 發訊息
		if (sendable) {
			sendReclaim(errorType, role, farmIndex, land);
		}
		//
		return result;
	}

	/**
	 * 檢查開墾
	 * 
	 * @param role
	 * @param farmIndex
	 * @param landUniqueId
	 */
	public ErrorType checkReclaim(Role role, int farmIndex, String landUniqueId) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 金幣不足
		long spendGold = manorCollector.getReclaimGold();
		boolean checkDecreaseGold = roleService.checkDecreaseGold(role, spendGold);
		if (!checkDecreaseGold) {
			errorType = ErrorType.GOLD_NOT_ENOUGH;
			return errorType;
		}

		Item item = itemService.getItem(role, landUniqueId);
		// 包包土地不存在
		if (item == null || !(item instanceof Land)) {
			errorType = ErrorType.LAND_NOT_EXIST;
			return errorType;
		}
		// 包包土地
		Land land = (Land) item;

		// 等級不足
		if (role.getLevel() < land.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 農場不存在
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 已有土地
		Land existLand = farm.getLand();
		if (existLand != null) {
			errorType = ErrorType.ALREADY_HAD_LAND;
			return errorType;
		}

		// 檢查包包減少土地
		BagInfo.ErrorType bagError = itemService.checkDecreaseItem(role, land);
		if (bagError != BagInfo.ErrorType.NO_ERROR) {
			errorType = ErrorType.CAN_NOT_DECREASE_LAND;
			return errorType;
		}

		return errorType;
	}

	/**
	 * 發送開墾回應
	 * 
	 * @param errorType
	 * @param role
	 * @param farmIndex
	 * @param land
	 */
	public Message sendReclaim(ErrorType errorType, Role role, int farmIndex, Land land) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_RECLAIM_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addInt(farmIndex);// farmIndex
			message.addString(land.getId());// 土地id
			message.addString(land.getUniqueId());// 土地uniqueId
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
	 * 休耕結果
	 */
	public static class DisuseResultImpl extends ReclaimResultImpl implements DisuseResult {

		private static final long serialVersionUID = 4804066996834416637L;

		public DisuseResultImpl(int farmIndex, Land land, long spendGold) {
			super(farmIndex, land, spendGold);
		}

		public DisuseResultImpl() {
			this(-1, null, 0L);
		}
	}

	/**
	 * 休耕
	 * 
	 * @param role
	 * @param farmIndex
	 */
	public DisuseResult disuse(boolean sendable, Role role, int farmIndex) {
		DisuseResult result = null;
		// 檢查條件
		ErrorType errorType = checkDisuse(role, farmIndex);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			Land land = farm.getLand();

			// 扣金幣
			long spendGold = manorCollector.getDisuseGold();
			long decreaseGold = roleService.decreaseGold(sendable, role, spendGold, GoldType.MANOR_DISUSE);
			// 成功
			if (decreaseGold != 0) {
				// 把土地放回包包
				List<IncreaseItemResult> increaseResults = itemService.increaseItem(sendable, role, land);
				// 成功
				if (increaseResults.size() > 0) {
					farm.setLand(null);// 移除土地
					farm.clearSeed();// 清除所有農場頁內的種子

					// 休耕結果
					result = new DisuseResultImpl(farmIndex, land, spendGold);
				} else {
					errorType = ErrorType.CAN_NOT_INCREASE_LAND;
				}
			} else {
				errorType = ErrorType.GOLD_NOT_ENOUGH;
			}

		}

		// 發訊息
		if (sendable) {
			sendDisuse(errorType, role, farmIndex);
		}
		//
		return result;
	}

	/**
	 * 檢查休耕
	 * 
	 * @param roleId
	 * @param farmIndex
	 * @return
	 */
	public ErrorType checkDisuse(Role role, int farmIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 金幣不足
		long spendGold = manorCollector.getDisuseGold();
		boolean checkDecreaseGold = roleService.checkDecreaseGold(role, spendGold);
		if (!checkDecreaseGold) {
			errorType = ErrorType.GOLD_NOT_ENOUGH;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 土地
		Land land = farm.getLand();
		if (land == null) {
			errorType = ErrorType.LAND_NOT_EXIST;
			return errorType;
		}

		// 包包是否可增加土地
		BagInfo.ErrorType bagError = itemService.checkIncreaseItem(role, land);
		// 包包無法增加土地
		if (bagError != BagInfo.ErrorType.NO_ERROR) {
			errorType = ErrorType.CAN_NOT_INCREASE_LAND;
			return errorType;
		}

		return errorType;
	}

	/**
	 * 發送休耕
	 * 
	 * @param errorType
	 * @param role
	 * @param farmIndex
	 */
	public Message sendDisuse(ErrorType errorType, Role role, int farmIndex) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_DISUSE_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			message.addInt(farmIndex);// farmIndex
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
	 * 耕種結果
	 */
	public static class CultureResultImpl extends AppResultSupporter implements CultureResult {
		private static final long serialVersionUID = -7816000021627261466L;

		private CultureType cultureType;

		private int farmIndex;

		private int gridIndex;

		private Seed seed;

		/**
		 * 剩餘毫秒
		 */
		private long residualMills;

		/**
		 * 消耗的道具
		 */
		private List<Item> spendItems = new LinkedList<Item>();

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		public CultureResultImpl(CultureType cultureType, int farmIndex, int gridIndex, Seed seed, long residualMills,
				List<Item> spendItems, int spendCoin) {
			this.cultureType = cultureType;
			this.farmIndex = farmIndex;
			this.gridIndex = gridIndex;
			this.seed = seed;
			this.residualMills = residualMills;
			//
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		/**
		 * 消耗道具
		 * 
		 * @param cultureType
		 * @param farmIndex
		 * @param gridIndex
		 * @param seed
		 * @param residualMills
		 * @param spendItems
		 */
		public CultureResultImpl(CultureType cultureType, int farmIndex, int gridIndex, Seed seed, long residualMills,
				List<Item> spendItems) {
			this(cultureType, farmIndex, gridIndex, seed, residualMills, spendItems, 0);
		}

		/**
		 * 消耗儲值幣
		 * 
		 * @param cultureType
		 * @param farmIndex
		 * @param gridIndex
		 * @param seed
		 * @param residualMills
		 * @param spendCoin
		 */
		public CultureResultImpl(CultureType cultureType, int farmIndex, int gridIndex, Seed seed, long residualMills,
				int spendCoin) {
			this(cultureType, farmIndex, gridIndex, seed, residualMills, new LinkedList<Item>(), spendCoin);
		}

		public CultureResultImpl(CultureType cultureType, int farmIndex, int gridIndex, Seed seed, long residualMills) {
			this(cultureType, farmIndex, gridIndex, seed, residualMills, new LinkedList<Item>(), 0);
		}

		public CultureResultImpl() {
			this(null, -1, -1, null, 0L, new LinkedList<Item>(), 0);
		}

		public CultureType getCultureType() {
			return cultureType;
		}

		public void setCultureType(CultureType cultureType) {
			this.cultureType = cultureType;
		}

		public int getFarmIndex() {
			return farmIndex;
		}

		public void setFarmIndex(int farmIndex) {
			this.farmIndex = farmIndex;
		}

		public int getGridIndex() {
			return gridIndex;
		}

		public void setGridIndex(int gridIndex) {
			this.gridIndex = gridIndex;
		}

		public Seed getSeed() {
			return seed;
		}

		public void setSeed(Seed seed) {
			this.seed = seed;
		}

		public long getResidualMills() {
			return residualMills;
		}

		public void setResidualMills(long residualMills) {
			this.residualMills = residualMills;
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
			builder.append("cultureType", cultureType);
			builder.append("farmIndex", farmIndex);
			builder.append("gridIndex", gridIndex);
			builder.append("seed", (seed != null ? seed.getId() + ", " + seed.getUniqueId() : null));
			builder.append("residualMills", residualMills);
			//
			append(builder, "spendItems", spendItems);
			builder.append("spendCoin", spendCoin);
			return builder.toString();
		}

		public Object clone() {
			CultureResultImpl copy = null;
			copy = (CultureResultImpl) super.clone();
			copy.seed = clone(seed);
			copy.spendItems = clone(spendItems);
			return copy;
		}
	}

	/**
	 * 耕種
	 * 
	 * @param role
	 * @param cultureValue
	 *            耕種類別 @see CultureType
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 */
	public CultureResult culture(boolean sendable, Role role, int cultureValue, int farmIndex, int gridIndex,
			String seedUniqueId) {
		CultureResult result = null;
		// 耕種類別
		CultureType cultureType = EnumHelper.valueOf(CultureType.class, cultureValue);
		if (cultureType == null) {
			return result;
		}
		//
		switch (cultureType) {
		case PLANT: {
			// 種植
			result = plant(sendable, role, farmIndex, gridIndex, seedUniqueId);
			break;
		}
		case WATER: {
			// 澆水
			result = water(sendable, role, farmIndex, gridIndex);
			break;
		}
		case PRAY: {
			// 祈禱
			result = pray(sendable, role, farmIndex, gridIndex);
			break;
		}
		case SPEED: {
			// 加速
			result = speed(sendable, role, farmIndex, gridIndex);
			break;
		}
		case HARVEST: {
			// 收割
			result = harvest(sendable, role, farmIndex, gridIndex);
			break;
		}
		case REVIVE: {
			// 復活
			result = revive(sendable, role, farmIndex, gridIndex);
			break;
		}
		case CLEAR: {
			// 清除
			result = clear(sendable, role, farmIndex, gridIndex);
			break;
		}
		default: {
			if (sendable) {
				// 耕種類別不存在
				sendCulture(ErrorType.CULTURE_TYPE_NOT_EXIST, role, result);
			}
			break;
		}
		}
		//
		return result;
	}

	/**
	 * 計算種子成長剩餘毫秒
	 * 
	 * @param land
	 * @param seed
	 * @return
	 */
	public long calcResidual(Land land, Seed seed) {
		long result = 0L;
		// 目前時間
		long now = System.currentTimeMillis();
		// 經過毫秒
		long passMills = now - seed.getPlantTime();

		// 祈禱效果,減少成長毫秒
		int prayRate = 0;
		if (seed.getPrayTime() > 0) {
			prayRate = manorCollector.getPrayRate();
		}
		// 如:8hr*0.8=6.4hr
		long growMills = (long) (seed.getGrowMills() * (1 - ratio(prayRate)));
		// 剩餘毫秒
		result = growMills - passMills;// 成長毫秒-經過毫秒
		if (result <= 0) {
			return 0;
		}

		// 澆水效果,增加成長速度
		int waterRate = 0;
		if (seed.getWaterTime() > 0) {
			waterRate = manorCollector.getWaterRate();
		}

		// 若土地有成長速度,AttributeType.MANOR_SPEED_RATE
		AttributeGroup attributeGroup = land.getAttributeGroup();
		// 成長速度比例,如:0.2
		double speedRatio = ratio(waterRate + attributeGroup.getFinal(AttributeType.MANOR_SPEED_RATE));
		if (speedRatio > 0) {
			result = (long) ((1 - speedRatio) * result);
			// System.out.println("有增加成長速度, 剩餘毫秒: " + result);
		}
		//
		return (result > 0 ? result : 0);
	}

	/**
	 * 計算種子產出數量
	 * 
	 * @param land
	 * @param seed
	 * @return
	 */
	public Map<String, Integer> calcProducts(Land land, Seed seed) {
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		// 原產出數量
		Map<String, Integer> origItems = seed.getProducts();
		//
		AttributeGroup attributeGroup = land.getAttributeGroup();
		int output = attributeGroup.getFinal(AttributeType.MANOR_OUTPUT);// 2

		double criticalRatio = ratio(attributeGroup.getFinal(AttributeType.MANOR_CRITICAL_RATE));// 0.3
		double criticalQutputRatio = ratio(attributeGroup.getFinal(AttributeType.MANOR_CRITICAL_OUTPUT_RATE));// 0.3
		//
		for (Map.Entry<String, Integer> entry : origItems.entrySet()) {
			String itemId = entry.getKey();
			int amount = entry.getValue();
			int newQuality = 0;
			// 暴擊
			if (NumberHelper.randomWin(criticalRatio)) {
				newQuality = amount + (int) (output * (1 + criticalQutputRatio));
			} else {
				newQuality = amount + output;
			}
			//
			result.put(itemId, newQuality);
		}

		return result;
	}

	/**
	 * 是否成長中
	 * 
	 * @param seed
	 * @return
	 */
	protected boolean isGrowing(Seed seed) {
		boolean result = false;
		// 成長中
		if (seed.getPlantTime() > 0 && seed.getMatureType() == MatureType.GROWING) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否成熟了
	 * 
	 * @param seed
	 * @return
	 */
	protected boolean isMature(Seed seed) {
		boolean result = false;
		// 成熟了
		if (seed.getMatureTime() > 0 && seed.getMatureType() == MatureType.MATURE) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否枯萎了
	 * 
	 * @param seed
	 * @return
	 */
	protected boolean isWither(Seed seed) {
		boolean result = false;
		// 枯萎了
		if (seed.getMatureTime() > 0 && seed.getMatureType() == MatureType.WITHER) {
			result = true;
		}
		return result;
	}

	/**
	 * 種植
	 * 
	 * @param role
	 * @param cultureType
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 * @return
	 */
	public CultureResult plant(boolean sendable, Role role, int farmIndex, int gridIndex, String seedUniqueId) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkPlant(role, farmIndex, gridIndex, seedUniqueId);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			Land land = farm.getLand();
			// 種子
			Seed seed = (Seed) itemService.getItem(role, seedUniqueId);
			// 把種子從包包扣除,扣1個種子
			DecreaseItemResult decreaseResult = itemService.decreaseItemWithUniqueId(sendable, role, seedUniqueId, 1);
			// 成功
			if (decreaseResult != null) {
				// clone 1個種子,因包包內種子可能堆疊很多個,不要直接拿來用
				Seed cloneSeed = (Seed) itemService.createSeed(seed.getId(), seed.getUniqueId(), 1);
				long now = System.currentTimeMillis();
				cloneSeed.setPlantTime(now);// 種植時間
				cloneSeed.setMatureType(MatureType.GROWING);// 成熟類別
				farm.addSeed(gridIndex, cloneSeed);// 塞到農場去了

				// 剩餘毫秒
				long residualMills = calcResidual(land, cloneSeed);
				// 耕種結果
				result = new CultureResultImpl(CultureType.PLANT, farmIndex, gridIndex, cloneSeed, residualMills);
			} else {
				errorType = ErrorType.CAN_NOT_DECREASE_SEED;
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查種植
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 * @return
	 */
	public ErrorType checkPlant(Role role, int farmIndex, int gridIndex, String seedUniqueId) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		Item item = itemService.getItem(role, seedUniqueId);
		// 包包種子不存在
		if (item == null || !(item instanceof Seed)) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}
		// 種子
		Seed seed = (Seed) item;

		// 包包是否減少種子,扣1個種子
		BagInfo.ErrorType bagError = itemService.checkDecreaseItemWithUniqueId(role, seedUniqueId, 1);
		if (bagError != BagInfo.ErrorType.NO_ERROR) {
			errorType = ErrorType.CAN_NOT_DECREASE_SEED;
			return errorType;
		}

		// 等級不足
		if (role.getLevel() < seed.getLevelLimit()) {
			errorType = ErrorType.LEVLE_NOT_ENOUGH;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		boolean contains = farm.containIndex(gridIndex);
		if (contains) {
			errorType = ErrorType.ALREADY_HAD_SEED;
			return errorType;
		}

		// 農場滿了
		int emptySize = farm.getEmptySize();
		if (emptySize == 0) {
			errorType = ErrorType.FARM_FULL;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 澆水
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult water(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkWater(role, farmIndex, gridIndex);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			Land land = farm.getLand();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, manorCollector.getWaterItem(), 1,
					manorCollector.getWaterCoin(), CoinType.MANOR_WATER, vipCollector.getManorCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (spendError == RoleService.ErrorType.NO_ERROR) {
				seed.setWaterTime(System.currentTimeMillis());// 澆水時間
				// 剩餘毫秒
				long residualMills = calcResidual(land, seed);

				// 消耗道具
				if (spendResult.getItemTimes() > 0) {
					result = new CultureResultImpl(CultureType.WATER, farmIndex, gridIndex, seed, residualMills,
							spendResult.getItems());
				}
				// 消耗儲值幣
				else {
					result = new CultureResultImpl(CultureType.WATER, farmIndex, gridIndex, seed, residualMills,
							spendResult.getCoin());
				}
			} else {
				errorType = roleErrorToManorError(spendError);
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查澆水
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkWater(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}

		boolean growing = isGrowing(seed);
		// 沒在成長中
		if (!growing) {
			errorType = ErrorType.NOT_GROWING;
			return errorType;
		}

		// 種子已澆水
		if (seed.getWaterTime() > 0) {
			errorType = ErrorType.ALREADY_WATER;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 祈禱
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult pray(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkPray(role, farmIndex, gridIndex);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			Land land = farm.getLand();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, manorCollector.getPrayItem(), 1,
					manorCollector.getPrayCoin(), CoinType.MANOR_PRAY, vipCollector.getManorCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (spendError == RoleService.ErrorType.NO_ERROR) {
				seed.setPrayTime(System.currentTimeMillis());// 祈禱時間
				// 剩餘毫秒
				long residualMills = calcResidual(land, seed);

				// 消耗道具
				if (spendResult.getItemTimes() > 0) {
					result = new CultureResultImpl(CultureType.PRAY, farmIndex, gridIndex, seed, residualMills,
							spendResult.getItems());
				}
				// 消耗儲值幣
				else {
					result = new CultureResultImpl(CultureType.PRAY, farmIndex, gridIndex, seed, residualMills,
							spendResult.getItems());
				}
			} else {
				errorType = roleErrorToManorError(spendError);
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查祈禱
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkPray(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}

		boolean growing = isGrowing(seed);
		// 沒在成長中
		if (!growing) {
			errorType = ErrorType.NOT_GROWING;
			return errorType;
		}

		// 種子已祈禱
		if (seed.getPrayTime() > 0) {
			errorType = ErrorType.ALREADY_PRAY;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 加速,直接成熟,直接收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult speed(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkSpeed(role, farmIndex, gridIndex);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			// Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			// Land land = farm.getLand();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, manorCollector.getSpeedItem(), 1,
					manorCollector.getSpeedCoin(), CoinType.MANOR_SPEED, vipCollector.getManorCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (spendError == RoleService.ErrorType.NO_ERROR) {
				// 計算種子產出數量
				// Map<String, Integer> awardItems = calcSeedItems(land, seed);

				// 加速將無法感受到土地強化的效果,不增加產量
				Map<String, Integer> awardItems = seed.getProducts();
				// 檢查包包增加多個不同道具
				BagInfo.ErrorType bagError = itemService.checkIncreaseItems(role, awardItems);
				if (bagError != BagInfo.ErrorType.NO_ERROR) {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
				} else {
					// 增加多個道具
					List<IncreaseItemResult> increaseResults = itemService.increaseItems(sendable, role, awardItems);
					// 成功
					if (increaseResults.size() > 0) {
						// 移除種子
						manorInfo.removeSeed(farmIndex, gridIndex);

						// 消耗道具
						if (spendResult.getItemTimes() > 0) {
							result = new CultureResultImpl(CultureType.SPEED, farmIndex, gridIndex, seed, 0,
									spendResult.getItems());
						}
						// 消耗儲值幣
						else {
							result = new CultureResultImpl(CultureType.SPEED, farmIndex, gridIndex, seed, 0,
									spendResult.getCoin());
						}
					} else {
						errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
					}
				}
			} else {
				errorType = roleErrorToManorError(spendError);
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查加速
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkSpeed(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 土地
		Land land = farm.getLand();
		if (land == null) {
			errorType = ErrorType.LAND_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}

		boolean growing = isGrowing(seed);
		// 沒在成長中
		if (!growing) {
			errorType = ErrorType.NOT_GROWING;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult harvest(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkHarvest(role, farmIndex, gridIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			Land land = farm.getLand();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);

			// 計算種子產出數量
			Map<String, Integer> awardItems = calcProducts(land, seed);
			// 檢查包包增加多個不同道具
			BagInfo.ErrorType bagError = itemService.checkIncreaseItems(role, awardItems);
			if (bagError != BagInfo.ErrorType.NO_ERROR) {
				errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
			} else {
				// 增加多個道具
				List<IncreaseItemResult> increaseResults = itemService.increaseItems(sendable, role, awardItems);
				// 成功
				if (increaseResults.size() > 0) {
					// 移除種子
					manorInfo.removeSeed(farmIndex, gridIndex);
					// 耕種結果
					result = new CultureResultImpl(CultureType.HARVEST, farmIndex, gridIndex, seed, 0);
				} else {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
				}
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查收割
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkHarvest(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 土地
		Land land = farm.getLand();
		if (land == null) {
			errorType = ErrorType.LAND_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}

		// 還未成熟
		boolean mature = isMature(seed);
		if (!mature) {
			errorType = ErrorType.NOT_MATURE;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 復活,把枯萎的復活,直接收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult revive(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkRevive(role, farmIndex, gridIndex);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 農場
			// Farm farm = manorInfo.getFarm(farmIndex);
			// 土地
			// Land land = farm.getLand();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);

			// 消耗道具或儲值幣
			SpendResult spendResult = roleService.spendByItemCoin(sendable, role, manorCollector.getReviveItem(), 1,
					manorCollector.getReviveCoin(), CoinType.MANOR_REVIVE, vipCollector.getManorCoinVipType());
			RoleService.ErrorType spendError = spendResult.getErrorType();
			// System.out.println("spendError: " + spendError);

			// 扣成功
			if (errorType == ErrorType.NO_ERROR) {
				// 計算種子產出數量
				// Map<String, Integer> awardItems = calcSeedItems(land, seed);

				// 復活將無法感受到土地強化的效果,不增加產量
				Map<String, Integer> awardItems = seed.getProducts();
				// 檢查包包增加多個不同道具
				BagInfo.ErrorType bagError = itemService.checkIncreaseItems(role, awardItems);
				if (bagError != BagInfo.ErrorType.NO_ERROR) {
					errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
				} else {
					// 增加多個道具
					List<IncreaseItemResult> increaseResults = itemService.increaseItems(sendable, role, awardItems);
					// 成功
					if (increaseResults.size() > 0) {
						// 移除種子
						manorInfo.removeSeed(farmIndex, gridIndex);

						// 消耗道具
						if (spendResult.getItemTimes() > 0) {
							result = new CultureResultImpl(CultureType.REVIVE, farmIndex, gridIndex, seed, 0,
									spendResult.getItems());
						}
						// 消耗儲值幣
						else {
							result = new CultureResultImpl(CultureType.REVIVE, farmIndex, gridIndex, seed, 0,
									spendResult.getCoin());
						}
					} else {
						errorType = ErrorType.CAN_NOT_INCREASE_ITEM;
					}
				}
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查復活
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkRevive(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 土地
		Land land = farm.getLand();
		if (land == null) {
			errorType = ErrorType.LAND_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}

		boolean wither = isWither(seed);
		// 還沒枯萎
		if (!wither) {
			errorType = ErrorType.NOT_WITHER;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 清除
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public CultureResult clear(boolean sendable, Role role, int farmIndex, int gridIndex) {
		CultureResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkClear(role, farmIndex, gridIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 種子
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			// 移除種子
			ManorInfo.ErrorType manorError = manorInfo.removeSeed(farmIndex, gridIndex);
			if (manorError == ManorInfo.ErrorType.NO_ERROR) {
				// 耕種結果
				result = new CultureResultImpl(CultureType.CLEAR, farmIndex, gridIndex, seed, 0);
			}
		}

		// 發訊息
		if (sendable) {
			sendCulture(errorType, role, result);
		}
		return result;
	}

	/**
	 * 檢查清除
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType checkClear(Role role, int farmIndex, int gridIndex) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		// 莊園是否有農場
		Farm farm = manorInfo.getFarm(farmIndex);
		if (farm == null) {
			errorType = ErrorType.FARM_NOT_EXIST;
			return errorType;
		}

		// 農場是否有種子
		Seed seed = farm.getSeed(gridIndex);
		if (seed == null) {
			errorType = ErrorType.SEED_NOT_EXIST;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 發送耕種
	 * 
	 * @param errorType
	 * @param role
	 * @param cultureResult
	 */
	public Message sendCulture(ErrorType errorType, Role role, CultureResult cultureResult) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_CULTURE_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			fillCultureResult(message, cultureResult);
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
	 * 填充耕種
	 * 
	 * @param message
	 * @param cultureResult
	 */
	protected void fillCultureResult(Message message, CultureResult cultureResult) {
		message.addInt(cultureResult.getCultureType());
		message.addInt(cultureResult.getFarmIndex());
		message.addInt(cultureResult.getGridIndex());
		// 種子
		Seed seed = cultureResult.getSeed();
		if (seed != null) {
			message.addString(seed.getId());
			message.addString(seed.getUniqueId());
			//
			message.addLong(seed.getPlantTime());
			message.addLong(seed.getWaterTime());
			message.addLong(seed.getMatureTime());
			message.addInt(seed.getMatureType());
			message.addLong(cultureResult.getResidualMills());// 剩餘毫秒
		}
	}

	/**
	 * 發送耕種所有
	 * 
	 * @param errorType
	 * @param role
	 * @param cultureResults
	 */
	public Message sendCultureAll(ErrorType errorType, Role role, CultureAllResult cultureAllResult) {
		Message message = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT,
				CoreMessageType.MANOR_CULTURE_ALL_RESPONSE, role.getId());

		message.addInt(errorType);// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR: {
			List<CultureResult> cultureResults = cultureAllResult.getCultureResults();
			message.addInt(cultureResults.size());
			for (CultureResult cultureResult : cultureResults) {
				fillCultureResult(message, cultureResult);
			}
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

	public static class SeedResultImpl extends AppResultSupporter implements SeedResult {

		private static final long serialVersionUID = -1396419886151130049L;

		private int farmIndex;

		private int gridIndex;

		private Farm farm;

		private Land land;

		private Seed seed;

		public SeedResultImpl(int farmIndex, int gridIndex, Farm farm, Land land, Seed seed) {
			this.farmIndex = farmIndex;
			this.gridIndex = gridIndex;
			this.farm = farm;
			this.land = land;
			this.seed = seed;
		}

		public SeedResultImpl() {
			this(-1, -1, null, null, null);
		}

		public int getFarmIndex() {
			return farmIndex;
		}

		public void setFarmIndex(int farmIndex) {
			this.farmIndex = farmIndex;
		}

		public int getGridIndex() {
			return gridIndex;
		}

		public void setGridIndex(int gridIndex) {
			this.gridIndex = gridIndex;
		}

		public Farm getFarm() {
			return farm;
		}

		public void setFarm(Farm farm) {
			this.farm = farm;
		}

		public Land getLand() {
			return land;
		}

		public void setLand(Land land) {
			this.land = land;
		}

		public Seed getSeed() {
			return seed;
		}

		public void setSeed(Seed seed) {
			this.seed = seed;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("farmIndex", farmIndex);
			builder.append("gridIndex", gridIndex);
			builder.append("farm", (farm != null ? farm.getId() : null));
			builder.append("land", (land != null ? land.getId() + ", " + land.getUniqueId() : null));
			builder.append("seed", (seed != null ? seed.getId() + ", " + seed.getUniqueId() : null));
			return builder.toString();
		}

		public Object clone() {
			SeedResultImpl copy = null;
			copy = (SeedResultImpl) super.clone();
			copy.farm = clone(farm);
			copy.land = clone(land);
			copy.seed = clone(seed);
			return copy;
		}
	}

	/**
	 * 計算成長中的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcGrowings(ManorInfo manorInfo) {
		List<SeedResult> result = new LinkedList<SeedResult>();
		for (int[] index : manorInfo.getIndexs()) {
			int farmIndex = index[0];
			int gridIndex = index[1];
			Farm farm = manorInfo.getFarm(farmIndex);
			Land land = farm.getLand();
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			boolean growing = isGrowing(seed);
			// 成長中
			if (growing) {
				SeedResult buffResult = new SeedResultImpl(farmIndex, gridIndex, farm, land, seed);
				result.add(buffResult);
			}
		}
		return result;
	}

	/**
	 * 計算可以澆水的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcCanWaters(ManorInfo manorInfo) {
		List<SeedResult> result = new LinkedList<SeedResult>();
		for (int[] index : manorInfo.getIndexs()) {
			int farmIndex = index[0];
			int gridIndex = index[1];
			Farm farm = manorInfo.getFarm(farmIndex);
			Land land = farm.getLand();
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			boolean growing = isGrowing(seed);
			// 成長中,還沒澆水
			if (growing && seed.getWaterTime() == 0) {
				SeedResult buffResult = new SeedResultImpl(farmIndex, gridIndex, farm, land, seed);
				result.add(buffResult);
			}
		}
		return result;
	}

	/**
	 * 計算可以祈禱的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcCanPrays(ManorInfo manorInfo) {
		List<SeedResult> result = new LinkedList<SeedResult>();
		for (int[] index : manorInfo.getIndexs()) {
			int farmIndex = index[0];
			int gridIndex = index[1];
			Farm farm = manorInfo.getFarm(farmIndex);
			Land land = farm.getLand();
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			boolean growing = isGrowing(seed);
			// 成長中,還沒祈禱
			if (growing && seed.getPrayTime() == 0) {
				SeedResult buffResult = new SeedResultImpl(farmIndex, gridIndex, farm, land, seed);
				result.add(buffResult);
			}
		}
		return result;
	}

	/**
	 * 計算可以加速的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcCanSpeeds(ManorInfo manorInfo) {
		return calcGrowings(manorInfo);
	}

	/**
	 * 計算可以收割的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcCanHarvests(ManorInfo manorInfo) {
		List<SeedResult> result = new LinkedList<SeedResult>();
		for (int[] index : manorInfo.getIndexs()) {
			int farmIndex = index[0];
			int gridIndex = index[1];
			Farm farm = manorInfo.getFarm(farmIndex);
			Land land = farm.getLand();
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			boolean mature = isMature(seed);
			// 已成熟
			if (mature) {
				SeedResult buffResult = new SeedResultImpl(farmIndex, gridIndex, farm, land, seed);
				result.add(buffResult);
			}
		}
		return result;
	}

	/**
	 * 計算可以復活的種子
	 * 
	 * @param manorInfo
	 * @return
	 */
	public List<SeedResult> calcCanRevives(ManorInfo manorInfo) {
		List<SeedResult> result = new LinkedList<SeedResult>();
		for (int[] index : manorInfo.getIndexs()) {
			int farmIndex = index[0];
			int gridIndex = index[1];
			Farm farm = manorInfo.getFarm(farmIndex);
			Land land = farm.getLand();
			Seed seed = manorInfo.getSeed(farmIndex, gridIndex);
			boolean wither = isWither(seed);
			// 已枯萎
			if (wither) {
				SeedResult buffResult = new SeedResultImpl(farmIndex, gridIndex, farm, land, seed);
				result.add(buffResult);
			}
		}
		return result;
	}

	/**
	 * 耕種結果
	 */
	public static class CultureResultAllImpl extends AppResultSupporter implements CultureAllResult {
		private static final long serialVersionUID = -7816000021627261466L;

		private CultureType cultureType;

		/**
		 * 消耗的道具
		 */
		private List<Item> spendItems = new LinkedList<Item>();

		/**
		 * 消耗的儲值幣
		 */
		private int spendCoin;

		/**
		 * 耕種結果
		 */
		private List<CultureResult> cultureResults = new LinkedList<CultureResult>();

		public CultureResultAllImpl(CultureType cultureType, List<Item> spendItems, int spendCoin) {
			this.cultureType = cultureType;
			this.spendItems = spendItems;
			this.spendCoin = spendCoin;
		}

		/**
		 * 消耗道具
		 * 
		 * @param cultureType
		 * @param spendItems
		 */
		public CultureResultAllImpl(CultureType cultureType, List<Item> spendItems) {
			this(cultureType, spendItems, 0);
		}

		/**
		 * 消耗儲值幣
		 * 
		 * @param cultureType
		 * @param spendCoin
		 */
		public CultureResultAllImpl(CultureType cultureType, int spendCoin) {
			this(cultureType, new LinkedList<Item>(), 0);
		}

		public CultureResultAllImpl() {
			this(null, new LinkedList<Item>(), 0);
		}

		public CultureType getCultureType() {
			return cultureType;
		}

		public void setCultureType(CultureType cultureType) {
			this.cultureType = cultureType;
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

		public List<CultureResult> getCultureResults() {
			return cultureResults;
		}

		public void setCultureResults(List<CultureResult> cultureResults) {
			this.cultureResults = cultureResults;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("cultureType", cultureType);
			append(builder, "spendItems", spendItems);
			builder.append("spendCoin", spendCoin);
			builder.append("cultureResults", cultureResults);
			return builder.toString();
		}

		public Object clone() {
			CultureResultAllImpl copy = null;
			copy = (CultureResultAllImpl) super.clone();
			copy.spendItems = clone(spendItems);
			copy.cultureResults = clone(cultureResults);
			return copy;
		}
	}

	/**
	 * 耕種所有
	 * 
	 * @param sendable
	 * @param roleId
	 * @param cultureValue
	 * @return
	 */
	public CultureAllResult cultureAll(boolean sendable, Role role, int cultureValue) {
		CultureAllResult result = null;
		// 耕種類別
		CultureType cultureType = EnumHelper.valueOf(CultureType.class, cultureValue);
		if (cultureType == null) {
			return result;
		}
		//
		switch (cultureType) {
		case WATER: {
			// 澆水
			result = waterAll(sendable, role);
			break;
		}
		case PRAY: {
			// 祈禱
			result = prayAll(sendable, role);
			break;
		}
		case SPEED: {
			// 加速
			// result = speedAll(sendable, role);
			break;
		}
		case HARVEST: {
			// 收割
			// result = harvestAll(sendable, role, farmIndex, gridIndex);
			break;
		}
		case REVIVE: {
			// 復活
			// result = reviveAll(sendable, role, farmIndex, gridIndex);
			break;
		}
		case CLEAR: {
			// 清除
			// result = clearAll(sendable, role, farmIndex, gridIndex);
			break;
		}
		default: {
			if (sendable) {
				// 耕種類別不存在
				sendCultureAll(ErrorType.CULTURE_TYPE_NOT_EXIST, role, result);
			}
			break;
		}
		}
		//
		return result;
	}

	/**
	 * 澆水所有
	 * 
	 * @param sendable
	 * @param role
	 */
	public CultureAllResult waterAll(boolean sendable, Role role) {
		CultureAllResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkWaterAll(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 計算成長中,且還沒澆水的種子
			List<SeedResult> canWaters = calcCanWaters(manorInfo);
			int spendTimes = canWaters.size();
			// 沒種子可澆水
			if (spendTimes == 0) {
				errorType = ErrorType.SEED_NOT_EXIST;
			}
			// 有種子可以澆水的次數
			else {
				// 消耗道具或儲值幣
				SpendResult spendResult = roleService.spendByItemCoin(sendable, role, spendTimes,
						manorCollector.getWaterItem(), 1, manorCollector.getWaterCoin(), CoinType.MANOR_WATER_ALL,
						vipCollector.getManorCoinVipType());
				RoleService.ErrorType spendError = spendResult.getErrorType();
				// System.out.println("spendError: "+spendError);
				// 扣成功
				if (spendError == RoleService.ErrorType.NO_ERROR) {
					result = new CultureResultAllImpl(CultureType.WATER, spendResult.getItems(), spendResult.getCoin());
					//
					int i = 0;
					for (SeedResult seedResult : canWaters) {
						Land land = seedResult.getLand();
						Seed seed = seedResult.getSeed();
						// 可以澆水的次數
						if (i >= spendResult.getTotalTimes()) {
							break;
						}
						seed.setWaterTime(System.currentTimeMillis());// 澆水時間
						// 剩餘毫秒
						long residualMills = calcResidual(land, seed);
						// System.out.println("剩餘毫秒: " + residualMills);
						//
						CultureResult cultureResult = new CultureResultImpl(CultureType.WATER,
								seedResult.getFarmIndex(), seedResult.getGridIndex(), seed, residualMills);
						//
						result.getCultureResults().add(cultureResult);
						//
						i++;
					}
				} else {
					errorType = roleErrorToManorError(spendError);
				}
			}
		}

		// 發訊息
		if (sendable) {
			sendCultureAll(errorType, role, result);
		}
		//
		return result;
	}

	protected ErrorType checkWaterAll(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * role的errorType轉換成manor的errorType
	 * 
	 * @param roleError
	 * @return
	 */
	protected ErrorType roleErrorToManorError(RoleService.ErrorType roleError) {
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
	 * 祈禱所有
	 * 
	 * @param sendable
	 * @param role
	 */
	public CultureAllResult prayAll(boolean sendable, Role role) {
		CultureAllResult result = null;
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkPrayAll(role);
		if (errorType == ErrorType.NO_ERROR) {
			// 莊園
			ManorInfo manorInfo = role.getManorInfo();
			// 計算成長中,且還沒祈禱的種子
			List<SeedResult> canPrays = calcCanPrays(manorInfo);
			int spendTimes = canPrays.size();
			// 沒種子可祈禱
			if (spendTimes == 0) {
				errorType = ErrorType.SEED_NOT_EXIST;
			}
			// 有種子可以祈禱的次數
			else {
				// 消耗道具或儲值幣
				SpendResult spendResult = roleService.spendByItemCoin(sendable, role, spendTimes,
						manorCollector.getPrayItem(), 1, manorCollector.getPrayCoin(), CoinType.MANOR_PRAY_ALL,
						vipCollector.getManorCoinVipType());
				RoleService.ErrorType spendError = spendResult.getErrorType();
				// System.out.println("spendError: "+spendError);
				// 扣成功
				if (spendError == RoleService.ErrorType.NO_ERROR) {
					result = new CultureResultAllImpl(CultureType.PRAY, spendResult.getItems(), spendResult.getCoin());
					//
					int i = 0;
					for (SeedResult seedResult : canPrays) {
						Land land = seedResult.getLand();
						Seed seed = seedResult.getSeed();
						// 可以祈禱的次數
						if (i >= spendResult.getTotalTimes()) {
							break;
						}
						seed.setPrayTime(System.currentTimeMillis());// 祈禱時間
						// 剩餘毫秒
						long residualMills = calcResidual(land, seed);
						// System.out.println("剩餘毫秒: " + residualMills);

						CultureResult cultureResult = new CultureResultImpl(CultureType.PRAY, seedResult.getFarmIndex(),
								seedResult.getGridIndex(), seed, residualMills);
						//
						result.getCultureResults().add(cultureResult);
						//
						i++;
					}
				} else {
					errorType = roleErrorToManorError(spendError);
				}
			}
		}

		// 發訊息
		if (sendable) {
			sendCultureAll(errorType, role, result);
		}
		//
		return result;
	}

	protected ErrorType checkPrayAll(Role role) {
		ErrorType errorType = ErrorType.NO_ERROR;
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}
		//
		return errorType;
	}
}