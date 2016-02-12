package org.openyu.mix.item.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Attribute.AttributeAction;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.ArmorCollector;
import org.openyu.mix.item.vo.EnhanceFactor;
import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.MaterialCollector;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.EnhanceArmorThing;
import org.openyu.mix.item.vo.thing.EnhanceLandThing;
import org.openyu.mix.item.vo.thing.EnhanceWeaponThing;
import org.openyu.mix.item.vo.thing.RoleExpThing;
import org.openyu.mix.item.vo.thing.RoleFameThing;
import org.openyu.mix.item.vo.thing.RoleGoldThing;
import org.openyu.mix.item.vo.thing.RoleSpThing;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.role.service.RoleHelper;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.EquipmentPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.BagInfo.Tab;
import org.openyu.mix.role.vo.BagInfo.TabType;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 道具服務
 */
public class ItemServiceImpl extends AppServiceSupporter implements ItemService {

	private static final long serialVersionUID = 7093694572704829166L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	/**
	 * 物品收集器
	 */
	protected transient ThingCollector thingCollector = ThingCollector.getInstance();

	/**
	 * 材料收集器
	 */
	protected transient MaterialCollector materialCollector = MaterialCollector.getInstance();

	/**
	 * 防具收集器
	 */
	protected transient ArmorCollector armorCollector = ArmorCollector.getInstance();

	/**
	 * 武器收集器
	 */
	protected transient WeaponCollector weaponCollector = WeaponCollector.getInstance();

	/**
	 * 莊園收集器
	 */
	protected transient ManorCollector manorCollector = ManorCollector.getInstance();

	/**
	 * vip收集器
	 */
	protected transient VipCollector vipCollector = VipCollector.getInstance();

	public ItemServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {

	}

	// --------------------------------------------------
	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon
	 * 
	 * @param itemId
	 * @return
	 */
	public <T extends Item> T createItem(String itemId) {
		return createItem(itemId, 1);
	}

	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon
	 * 
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public <T extends Item> T createItem(String itemId, int amount) {
		return createItem(itemId, null, amount);
	}

	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon
	 * 
	 * 適用於來自於DB轉回物件時
	 * 
	 * @param itemId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Item> T createItem(String itemId, String uniqueId, int amount) {
		T result = null;
		// 物品
		if (thingCollector.containThing(itemId)) {
			result = (T) createThing(itemId, uniqueId, amount);
		}
		// 材料
		else if (materialCollector.containMaterial(itemId)) {
			result = (T) createMaterial(itemId, uniqueId, amount);
		}
		// 防具,amount不填
		else if (armorCollector.containArmor(itemId)) {
			result = (T) createArmor(itemId, uniqueId);
		}
		// 武器,amount不填
		else if (weaponCollector.containWeapon(itemId)) {
			result = (T) createWeapon(itemId, uniqueId);
		}
		// 莊園-種子
		else if (manorCollector.containSeed(itemId)) {
			result = (T) createSeed(itemId, uniqueId, amount);
		}
		// 莊園-土地,amount不填
		else if (manorCollector.containLand(itemId)) {
			result = (T) createLand(itemId, uniqueId);
		} else {
			LOGGER.warn("[" + itemId + "] not exist in XML to be created");
		}
		return result;
	}

	/**
	 * 建構裝備,依序為Armor,Weapon
	 * 
	 * @param equipmentId
	 * @return
	 */
	public <T extends Equipment> T createEquipment(String equipmentId) {
		return createEquipment(equipmentId, null);
	}

	/**
	 * 建構裝備,依序為Armor,Weapon
	 * 
	 * 適用於來自於DB轉回物件時
	 * 
	 * @param equipmentId
	 * @param uniqueId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Equipment> T createEquipment(String equipmentId, String uniqueId) {
		T result = null;
		// 防具
		if (armorCollector.containArmor(equipmentId)) {
			result = (T) createArmor(equipmentId, uniqueId);
		}
		// 武器
		else if (weaponCollector.containWeapon(equipmentId)) {
			result = (T) createWeapon(equipmentId, uniqueId);
		} else {
			LOGGER.warn("[" + equipmentId + "] not exist in XML to be created");
		}
		return result;
	}

	/**
	 * 建構物品
	 * 
	 * @param thingId
	 * @param amount
	 * @return
	 */
	public Thing createThing(String thingId, int amount) {
		return createThing(thingId, null, amount);
	}

	/**
	 * 建構物品
	 * 
	 * @param thingId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public Thing createThing(String thingId, String uniqueId, int amount) {
		Thing result = null;
		if (thingCollector.containThing(thingId)) {
			result = thingCollector.createThing(thingId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			result.setAmount(amount);// 數量
		}
		return result;
	}

	/**
	 * 建構材料
	 * 
	 * @param materialId
	 * @param amount
	 * @return
	 */
	public Material createMaterial(String materialId, int amount) {
		return createMaterial(materialId, null, amount);
	}

	/**
	 * 建構材料
	 * 
	 * @param materialId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public Material createMaterial(String materialId, String uniqueId, int amount) {
		Material result = null;
		if (materialCollector.containMaterial(materialId)) {
			result = materialCollector.createMaterial(materialId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			result.setAmount(amount);// 數量
		}
		return result;
	}

	/**
	 * 建構防具
	 * 
	 * @param armorId
	 * @return
	 */
	public Armor createArmor(String armorId) {
		return createArmor(armorId, null);
	}

	/**
	 * 建構防具
	 * 
	 * @param armorId
	 * @param uniqueId
	 * @return
	 */
	public Armor createArmor(String armorId, String uniqueId) {
		Armor result = null;
		if (armorCollector.containArmor(armorId)) {
			result = armorCollector.createArmor(armorId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			// 數量應為1,不可堆疊
			result.setAmount(1);// 數量
			result.setMaxAmount(1);// 最大數量
			result.setEnhanceLevel(EnhanceLevel._0);// 強化等級(玩家)
		}
		return result;
	}

	/**
	 * 建構武器
	 * 
	 * @param weaponId
	 * @return
	 */
	public Weapon createWeapon(String weaponId) {
		return createWeapon(weaponId, null);
	}

	/**
	 * 建構武器
	 * 
	 * @param weaponId
	 * @param uniqueId
	 * @return
	 */
	public Weapon createWeapon(String weaponId, String uniqueId) {
		Weapon result = null;
		if (weaponCollector.containWeapon(weaponId)) {
			result = weaponCollector.createWeapon(weaponId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			// 數量應為1,不可堆疊
			result.setAmount(1);// 數量
			result.setMaxAmount(1);// 最大數量
			result.setEnhanceLevel(EnhanceLevel._0);// 強化等級(玩家)
		}
		return result;
	}

	/**
	 * 建構種子
	 * 
	 * @param seedId
	 * @param amount
	 * @return
	 */
	public Seed createSeed(String seedId, int amount) {
		return createSeed(seedId, null, amount);
	}

	/**
	 * 建構種子
	 * 
	 * @param seedId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public Seed createSeed(String seedId, String uniqueId, int amount) {
		Seed result = null;
		if (manorCollector.containSeed(seedId)) {
			result = manorCollector.createSeed(seedId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			result.setAmount(amount);// 數量
			result.setPlantTime(0L);// 種植時間(玩家)
			result.setWaterTime(0L);// 澆水時間(玩家)
			result.setMatureTime(0L);// 成熟時間(玩家)
			result.setMatureType(MatureType.PENDING);// 成熟類別(玩家)
		}
		return result;
	}

	/**
	 * 建構土地
	 * 
	 * @param landId
	 * @return
	 */
	public Land createLand(String landId) {
		return createLand(landId, null);
	}

	/**
	 * 建構土地
	 * 
	 * @param landId
	 * @param uniqueId
	 * @return
	 */
	public Land createLand(String landId, String uniqueId) {
		Land result = null;
		if (manorCollector.containLand(landId)) {
			result = manorCollector.createLand(landId);
			if (StringHelper.notBlank(uniqueId)) {
				result.setUniqueId(uniqueId);
			}
			// 數量應為1,不可堆疊
			result.setAmount(1);// 數量
			result.setMaxAmount(1);// 最大數量
			result.setEnhanceLevel(EnhanceLevel._0);// 強化等級(玩家)
		}
		return result;
	}

	// --------------------------------------------------
	// 道具
	// --------------------------------------------------
	/**
	 * 取得道具
	 * 
	 * @param roleId
	 * @param uniqueId
	 * @return
	 */
	public Item getItem(Role role, String uniqueId) {
		Item result = null;
		// 檢查條件
		if (role == null) {
			return result;
		}
		//
		result = role.getBagInfo().getItem(uniqueId);
		return result;
	}

	/**
	 * 發送道具
	 * 
	 * @param role
	 * @param uniqueId
	 */
	public void sendItem(Role role, String uniqueId) {
		if (role == null) {
			return;
		}
		//
		Item item = getItem(role, uniqueId);
		if (item == null) {
			return;
		}
		//
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_GET_ITEM_RESPONSE, role.getId());
		//
		fillItem(message, item);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充道具
	 * 
	 * @param message
	 * @param item
	 */
	public void fillItem(Message message, Item item) {
		/**
		 * @see org.openyu.mix.item.po.usertype.ItemUserType
		 */
		message.addString(item.getId());// id
		message.addString(item.getUniqueId());// 唯一碼
		message.addInt(item.getAmount());// 數量
		message.addBoolean(item.isTied());// 是否綁定(束缚)
		//
		if (item instanceof Thing) {
			// 道具其它欄位
			Thing thing = (Thing) item;

			// 物品類別
			ThingType thingType = thing.getThingType();
			if (thingType == null) {
				return;
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (thingType) {
			default: {
				break;
			}
			}
		} else if (item instanceof Material) {
			// 材料相關資訊
			Material material = (Material) item;

			// 材料類別
			MaterialType materialType = material.getMaterialType();
			if (materialType == null) {
				return;
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (materialType) {
			default: {
				break;
			}
			}
		} else if (item instanceof Armor) {
			// 防具相關資訊
			Armor armor = (Armor) item;

			// 強化等級
			message.addInt(armor.getEnhanceLevel());
			// 填充所有屬性
			RoleHelper.fillAttributeGroup(message, armor.getAttributeGroup());
		} else if (item instanceof Weapon) {
			// 武器相關資訊
			Weapon weapon = (Weapon) item;

			// 強化等級
			message.addInt(weapon.getEnhanceLevel());
			// 填充所有屬性
			RoleHelper.fillAttributeGroup(message, weapon.getAttributeGroup());
		} else if (item instanceof Seed) {
			// 種子相關資訊
			Seed seed = (Seed) item;

			// 種植時間(玩家)
			message.addLong(seed.getPlantTime());
			// 澆水時間(玩家)
			message.addLong(seed.getWaterTime());
			// 祈禱時間(玩家)
			message.addLong(seed.getPrayTime());
			// 成熟時間(玩家)
			message.addLong(seed.getMatureTime());
			// 成熟類別(玩家)
			message.addInt(seed.getMatureType());

		} else if (item instanceof Land) {
			// 土地相關資訊
			Land land = (Land) item;

			// 強化等級(玩家)
			message.addInt(land.getEnhanceLevel());
			// 填充所有屬性
			RoleHelper.fillAttributeGroup(message, land.getAttributeGroup());
		} else {
			// just for pretty
		}
		//
	}

	/**
	 * 改變所有的包包頁是否鎖定
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	public boolean changeBagInfoLocked(boolean sendable, Role role) {
		boolean result = false;
		//
		BagInfo bagInfo = role.getBagInfo();
		TabType[] tabTypes = TabType.values();
		for (TabType tabType : tabTypes) {
			int index = tabType.getValue();
			// 由包包頁類型,取得vip類型
			VipType vipType = vipCollector.getVipType(tabType);
			if (vipType == null || role.getVipType().getValue() >= vipType.getValue()) {
				bagInfo.unLock(index);
			} else {
				bagInfo.lock(index);
			}
			//
			result = true;
		}
		//
		if (sendable) {
			sendTabsLocked(role.getId(), bagInfo);
		}
		//
		return result;
	}

	/**
	 * 發送單一包包頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param tab
	 */
	public void sendTabLocked(String roleId, Tab tab) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_TAB_LOCKED_RESPONSE, roleId);

		fillTabLocked(message, tab);
		//
		messageService.addMessage(message);
	}

	/**
	 * 發送所有包包頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param bagInfo
	 */
	public void sendTabsLocked(String roleId, BagInfo bagInfo) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_TABS_LOCKED_RESPONSE, roleId);

		Map<Integer, Tab> tabs = bagInfo.getTabs();// 包含被鎖定的包包頁
		message.addInt(tabs.size());
		for (Tab tab : tabs.values()) {
			fillTabLocked(message, tab);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充包包頁是否鎖定
	 * 
	 * @param message
	 * @param tab
	 */
	public void fillTabLocked(Message message, Tab tab) {
		message.addInt(tab.getId());// tabIndex
		message.addBoolean(tab.isLocked());// 是否鎖定
	}

	/**
	 * 發送包包欄位
	 * 
	 * @param roleId
	 * @param bagInfo
	 */
	public void sendBagInfo(String roleId, BagInfo bagInfo) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_BAG_RESPONSE, roleId);

		fillBagInfo(message, bagInfo);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充包包欄位
	 * 
	 * @param message
	 * @param bagInfo
	 */
	public void fillBagInfo(Message message, BagInfo bagInfo) {
		// 已解鎖包包頁數量
		message.addInt(bagInfo.getTabSize());
		//
		Map<Integer, Tab> tabs = bagInfo.getTabs();// 包含被鎖定的包包頁
		for (Tab tab : tabs.values()) {
			// 可能被鎖定
			if (tab.isLocked()) {
				continue;
			}
			//
			fillTab(message, tab);
		}
	}

	/**
	 * 發送包包頁
	 * 
	 * @param roleId
	 * @param tab
	 */
	public void sendTab(String roleId, Tab tab) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_TAB_RESPONSE, roleId);

		fillTab(message, tab);
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充包包頁
	 * 
	 * @param message
	 * @param tab
	 */
	public void fillTab(Message message, Tab tab) {
		message.addInt(tab.getId());// tabIndex
		// 已放道具的格子數量
		message.addInt(tab.getItemSize());
		//
		Map<Integer, Item> items = tab.getItems();
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			// 格子索引
			int gridIndex = entry.getKey();
			// 道具
			Item item = entry.getValue();
			if (item == null) {
				continue;
			}
			//
			message.addInt(gridIndex);// gridIndex
			//
			fillItem(message, item);
		}
	}

	/**
	 * 檢查增加道具,by itemId
	 * 
	 * @param roleId
	 * @param item
	 * @param amount
	 * @return
	 */
	public BagInfo.ErrorType checkIncreaseItemWithItemId(Role role, String itemId, int amount) {
		Item item = createItem(itemId, amount);
		return checkIncreaseItem(role, item);
	}

	/**
	 * 檢查增加道具
	 * 
	 * @param roleId
	 * @param item
	 * @return
	 */
	public BagInfo.ErrorType checkIncreaseItem(Role role, Item item) {
		BagInfo.ErrorType errorType = BagInfo.ErrorType.NO_ERROR;
		// 檢查條件
		// 角色不存在
		if (role == null) {
			errorType = BagInfo.ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 道具不存在
		if (item == null) {
			errorType = BagInfo.ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 數量不合法
		int amount = item.getAmount();// 要放的數量
		if (amount <= 0) {
			errorType = BagInfo.ErrorType.AMOUNT_NOT_VALID;
			return errorType;
		}

		BagInfo bagInfo = role.getBagInfo();// 包包欄
		// 相同種類的道具集合
		List<Item> origItems = bagInfo.getItems(item.getId());
		int maxAmount = item.getMaxAmount();// 最大堆疊數量,0=無堆疊數量限制

		// 若已有道具,檢查總剩餘可放的數量
		int residualAmount = 0;// 剩餘可放的數量
		//
		for (Item origItem : origItems)// 原本的道具
		{
			// 只檢查包包的道具的最大數量,並未包含裝備,倉庫中的數量
			int origAmount = origItem.getAmount();

			// 1.原道具綁定(束缚),新道具綁定(束缚),才可堆疊
			// 2.原道具非綁定(非束缚),新道具非綁定(非束缚),才可堆疊
			if ((origItem.isTied() && item.isTied()) || (!origItem.isTied() && !item.isTied())) {
				// 當有堆疊數量限制時,maxAmount!=0
				if (maxAmount != 0) {
					residualAmount += (maxAmount - origAmount);
				}
				// 無堆疊數量限制,maxAmount==0
				else {
					residualAmount = Integer.MAX_VALUE;
				}
			}
		}

		// 若包包已滿,無法再放新一格的道具時,則檢查剩餘可放數量能不能在堆疊上去
		if (bagInfo.isFull()) {
			if (residualAmount < amount) {
				errorType = BagInfo.ErrorType.BAG_FULL;
				return errorType;
			}
		}
		// 包包沒滿
		else {
			int emptySize = bagInfo.getEmptySize();
			// 當有堆疊數量限制時,maxAmount!=0
			if (maxAmount != 0) {
				// residualAmount += (maxAmount * emptySize);//有可能會溢位
				for (int i = 0; i < emptySize; i++) {
					// 溢位了
					boolean overflow = NumberHelper.isAddOverflow(residualAmount, maxAmount);
					if (overflow) {
						residualAmount = Integer.MAX_VALUE;// 就取最大值,作為剩餘可放數量
						break;
					} else {
						residualAmount += maxAmount;
					}
				}
				//
				if (residualAmount < amount) {
					errorType = BagInfo.ErrorType.BAG_FULL;
					return errorType;
				}
			}
			// 無堆疊數量限制,maxAmount==0
			else {
				residualAmount = Integer.MAX_VALUE;
			}
		}
		// System.out.println("剩餘可放數量: " + residualAmount);
		return errorType;
	}

	/**
	 * 檢查增加多個不同道具
	 * 
	 * @param roleId
	 * @param items
	 * @return
	 */
	public BagInfo.ErrorType checkIncreaseItems(Role role, Map<String, Integer> items) {
		BagInfo.ErrorType errorType = BagInfo.ErrorType.NO_ERROR;
		// TODO 未實作
		return errorType;
	}

	/**
	 * 增加道具結果
	 */
	public static class IncreaseResultImpl extends AppResultSupporter implements IncreaseItemResult {

		private static final long serialVersionUID = 6149235756950195199L;

		/**
		 * 包包頁索引
		 */
		private int tabIndex;

		/**
		 * 格子索引
		 */
		private int gridIndex;

		/**
		 * 增加的道具
		 */
		private Item item;

		/**
		 * 原道具數量
		 */
		private int origAmount;

		/**
		 * 是否新的道具
		 */
		private boolean newable;

		public IncreaseResultImpl(int tabIndex, int gridIndex, Item item, int origAmount, boolean newable) {
			this.tabIndex = tabIndex;
			this.gridIndex = gridIndex;
			this.item = item;
			this.origAmount = origAmount;
			this.newable = newable;
		}

		public IncreaseResultImpl() {
			this(-1, -1, null, 0, false);
		}

		public int getTabIndex() {
			return tabIndex;
		}

		public void setTabIndex(int tabIndex) {
			this.tabIndex = tabIndex;
		}

		public int getGridIndex() {
			return gridIndex;
		}

		public void setGridIndex(int gridIndex) {
			this.gridIndex = gridIndex;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public int getOrigAmount() {
			return origAmount;
		}

		public void setOrigAmount(int origAmount) {
			this.origAmount = origAmount;
		}

		public boolean isNewable() {
			return newable;
		}

		public void setNewable(boolean newable) {
			this.newable = newable;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("tabIndex", tabIndex);
			builder.append("gridIndex", gridIndex);
			builder.append("item",
					(item != null ? item.getId() + ", " + item.getUniqueId() + ", " + item.getAmount() : null));
			builder.append("amount", origAmount);
			builder.append("newable", newable);
			return builder.toString();
		}
	}

	/**
	 * 增加道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	public List<IncreaseItemResult> increaseItem(boolean sendable, Role role, Item item) {
		List<IncreaseItemResult> result = new LinkedList<IncreaseItemResult>();
		//
		if (item == null) {
			return result;
		}
		//
		BagInfo bagInfo = role.getBagInfo();// 包包欄
		int amount = item.getAmount();// 要加的數量
		int maxAmount = item.getMaxAmount();// 最大堆疊數量,0=無堆疊數量限制
		// 檢查是否已有相同道具,若有先堆疊
		List<int[]> indexes = bagInfo.getIndexs(item.getId());
		for (int[] index : indexes) {
			int tabIndex = index[0];
			int gridIndex = index[1];
			Item origItem = bagInfo.getItem(tabIndex, gridIndex);// 原道具
			// 包包的道具數量
			int origAmount = origItem.getAmount();// 原數量
			// 總計數量
			int totalAmount = origAmount + amount;

			// 1.原道具綁定(束缚),新道具綁定(束缚),才可堆疊
			// 2.原道具非綁定(非束缚),新道具非綁定(非束缚),才可堆疊
			if ((origItem.isTied() && item.isTied()) || (!origItem.isTied() && !item.isTied())) {
				// 當有堆疊數量限制時,maxAmount!=0
				if (maxAmount != 0) {
					// 本格還能放
					if (totalAmount <= maxAmount) {
						origItem.setAmount(totalAmount);
						//
						Item cloneItem = clone(origItem);// 增加的道具
						cloneItem.setAmount(amount);// 增加的數量
						// 增加結果
						IncreaseItemResult buffResult = new IncreaseResultImpl(tabIndex, gridIndex, cloneItem,
								origAmount, false);
						result.add(buffResult);
						//
						amount = 0;// 一次放完
					}
					// 超過本格最大數量
					else {
						int residualAmount = maxAmount - origAmount;// 剩餘可放的數量
						if (residualAmount > 0) {
							origItem.setAmount(maxAmount);
							//
							Item cloneItem = clone(origItem);// 增加的道具
							cloneItem.setAmount(residualAmount);// 增加的數量
							// 增加結果
							IncreaseItemResult buffResult = new IncreaseResultImpl(tabIndex, gridIndex, cloneItem,
									origAmount, false);
							result.add(buffResult);
							//
							amount -= residualAmount;// 還有要放的
						}
					}
				}
				// 無堆疊數量限制,maxAmount==0
				else {
					origItem.setAmount(totalAmount);
					//
					Item cloneItem = clone(origItem);// 增加的道具
					cloneItem.setAmount(amount);// 增加的數量
					// 增加結果
					IncreaseItemResult buffResult = new IncreaseResultImpl(tabIndex, gridIndex, cloneItem, origAmount,
							false);
					result.add(buffResult);
					//
					amount = 0;// 一次放完
				}
			}
			//
			if (amount <= 0) {
				break;
			}
		}
		//
		boolean rollback = false;// 當包包滿時,是否還原數量
		// 剩下的數量,就用新空格放,因為上面已經先堆疊了
		if (amount > 0) {
			int[] emptyIndex = bagInfo.getEmptyIndex();
			if (emptyIndex != null) {
				int tabIndex = emptyIndex[0];
				int gridIndex = emptyIndex[1];
				// 當有堆疊數量限制時,maxAmount!=0
				if (maxAmount != 0 && amount > maxAmount) {
					rollback = true;
				} else {
					item.setAmount(amount);
					bagInfo.addItem(tabIndex, gridIndex, item);

					// Item newItem = clone(item);//增加的道具
					// newItem.setAmount(amount);//增加的數量
					// bagInfo.addItem(tabIndex, gridIndex, newItem);
					//
					Item cloneItem = clone(item);// 增加的道具
					// 增加結果
					IncreaseItemResult buffResult = new IncreaseResultImpl(tabIndex, gridIndex, cloneItem, 0, true);
					result.add(buffResult);

				}
			} else {
				// 剩下的數量,若已無空格可放,則全部還原,因為放失敗了
				rollback = true;
			}
		}
		// 若還沒放完,包包滿,則需還原數量,因為放失敗了
		if (rollback) {
			for (IncreaseItemResult buffResult : result) {
				int tabIndex = buffResult.getTabIndex();
				int gridIndex = buffResult.getGridIndex();

				// 非新的格子時,還原原本數量
				if (!buffResult.isNewable()) {
					bagInfo.setItemAmount(tabIndex, gridIndex, buffResult.getOrigAmount());
				}
				// 若新的格子,則刪掉剛放入的
				else {
					bagInfo.removeItem(tabIndex, gridIndex);
				}
			}
			// 因為放失敗了,都還原數量了,就沒有改變的結果
			result.clear();
		}

		// 發訊息
		if (sendable && result != null) {
			sendIncreaseItem(role, result);
		}
		return result;
	}

	/**
	 * 增加道具,by itemId
	 * 
	 * @param sendable
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public List<IncreaseItemResult> increaseItemWithItemId(boolean sendable, Role role, String itemId, int amount) {
		Item item = createItem(itemId, amount);
		return increaseItem(sendable, role, item);
	}

	/**
	 * 增加多個道具
	 * 
	 * @param sendable
	 * @param role
	 * @param items
	 * @return
	 */
	public List<IncreaseItemResult> increaseItems(boolean sendable, Role role, Map<String, Integer> items) {
		List<IncreaseItemResult> result = new LinkedList<IncreaseItemResult>();
		//
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			// 增加1個道具的結果
			List<IncreaseItemResult> buffResult = increaseItemWithItemId(false, role, entry.getKey(), entry.getValue());
			result.addAll(buffResult);
		}

		// 發訊息
		if (sendable) {
			sendIncreaseItem(role, result);
		}
		//
		return result;
	}

	/**
	 * 發送增加道具
	 * 
	 * @param roleId
	 * @param increaseItemResults
	 */
	public void sendIncreaseItem(Role role, List<IncreaseItemResult> increaseItemResults) {
		if (role == null) {
			return;
		}
		//
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_INCREASE_RESPONSE, role.getId());
		//
		BagInfo bagInfo = role.getBagInfo();
		message.addInt(increaseItemResults.size());// 道具個數
		for (IncreaseItemResult itemResult : increaseItemResults) {
			int tabIndex = itemResult.getTabIndex();
			int gridIndex = itemResult.getGridIndex();
			// 從包包拿道具最後的結果
			Item item = bagInfo.getItem(tabIndex, gridIndex);
			//
			fillIncreaseItem(message, tabIndex, gridIndex, item);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充增加道具
	 * 
	 * @param message
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 */
	public void fillIncreaseItem(Message message, int tabIndex, int gridIndex, Item item) {
		message.addInt(tabIndex);
		message.addInt(gridIndex);
		//
		fillItem(message, item);
	}

	/**
	 * 檢查減少道具,by itemId
	 * 
	 * @param roleId
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public BagInfo.ErrorType checkDecreaseItem(Role role, String itemId, int amount) {
		Item item = createItem(itemId, amount);
		return checkDecreaseItem(role, item);
	}

	/**
	 * 檢查減少道具
	 * 
	 * @param roleId
	 * @param item
	 * @return
	 */
	public BagInfo.ErrorType checkDecreaseItem(Role role, Item item) {
		BagInfo.ErrorType errorType = BagInfo.ErrorType.NO_ERROR;
		// 檢查條件
		if (role == null) {
			errorType = BagInfo.ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 道具不存在
		if (item == null) {
			errorType = BagInfo.ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 數量不合法
		int amount = item.getAmount();// 要減的數量
		if (amount <= 0) {
			errorType = BagInfo.ErrorType.AMOUNT_NOT_VALID;
			return errorType;
		}

		BagInfo bagInfo = role.getBagInfo();
		int totalAmount = bagInfo.getAmount(item.getId());
		// 道具數量不足
		if (amount > totalAmount) {
			errorType = BagInfo.ErrorType.AMOUNT_NOT_ENOUGH;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 檢查減少道具,by uniqueId
	 * 
	 * @param roleId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public BagInfo.ErrorType checkDecreaseItemWithUniqueId(Role role, String uniqueId, int amount) {
		BagInfo.ErrorType errorType = BagInfo.ErrorType.NO_ERROR;
		// 檢查條件
		if (role == null) {
			errorType = BagInfo.ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		// 道具不存在
		Item item = getItem(role, uniqueId);
		if (item == null) {
			errorType = BagInfo.ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 數量不合法
		if (amount <= 0) {
			errorType = BagInfo.ErrorType.AMOUNT_NOT_VALID;
			return errorType;
		}

		// 道具數量不足
		if (amount > item.getAmount()) {
			errorType = BagInfo.ErrorType.AMOUNT_NOT_ENOUGH;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 減少道具結果
	 */
	public static class DecreaseResultImpl extends AppResultSupporter implements DecreaseItemResult {

		private static final long serialVersionUID = 2174402077967444166L;

		/**
		 * 包包頁索引
		 */
		private int tabIndex;

		/**
		 * 格子索引
		 */
		private int gridIndex;

		/**
		 * 扣除的道具
		 */
		private Item item;

		public DecreaseResultImpl(int tabIndex, int gridIndex, Item item) {
			this.tabIndex = tabIndex;
			this.gridIndex = gridIndex;
			this.item = item;
		}

		public DecreaseResultImpl() {
			this(-1, -1, null);
		}

		public int getTabIndex() {
			return tabIndex;
		}

		public void setTabIndex(int tabIndex) {
			this.tabIndex = tabIndex;
		}

		public int getGridIndex() {
			return gridIndex;
		}

		public void setGridIndex(int gridIndex) {
			this.gridIndex = gridIndex;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("tabIndex", tabIndex);
			builder.append("gridIndex", gridIndex);
			builder.append("item",
					(item != null ? item.getId() + ", " + item.getUniqueId() + ", " + item.getAmount() : null));
			return builder.toString();
		}

	}

	/**
	 * 減少道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	public List<DecreaseItemResult> decreaseItem(boolean sendable, Role role, Item item) {
		List<DecreaseItemResult> result = new LinkedList<DecreaseItemResult>();
		if (item == null) {
			return result;
		}
		//
		BagInfo bagInfo = role.getBagInfo();// 包包欄
		int amount = Math.abs(item.getAmount());// 要減的數量
		// 檢查是否已有相同道具,若有一一減少數量
		// 相同種類的道具集合
		List<Item> origItems = bagInfo.getItems(item.getId());
		for (Item origItem : origItems)// 原本的道具
		{
			int origAmount = origItem.getAmount();
			//
			// 大於一格的數量時
			if (amount > origAmount) {
				DecreaseItemResult buffResult = decreaseItemWithUniqueId(sendable, role, origItem.getUniqueId(),
						origAmount);
				if (buffResult != null) {
					// result.getItems().put(origItem.getUniqueId(),
					// origAmount);
					result.add(buffResult);
					//
					amount -= origAmount;// 剩下還沒扣的數量
				}
			}
			// 一格就能扣玩的數量
			else {
				DecreaseItemResult buffResult = decreaseItemWithUniqueId(sendable, role, origItem.getUniqueId(),
						amount);
				if (buffResult != null) {
					// result.getItems().put(origItem.getUniqueId(), amount);
					result.add(buffResult);
					//
					amount = 0;// 一次扣完
					break;
				} else {
					continue;
				}
			}
		}
		return result;
	}

	/**
	 * 減少道具,by itemId
	 * 
	 * @param sendable
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public List<DecreaseItemResult> decreaseItemWithItemId(boolean sendable, Role role, String itemId, int amount) {
		Item item = createItem(itemId, amount);
		return decreaseItem(sendable, role, item);
	}

	/**
	 * 減少道具,by uniqueId
	 * 
	 * @param sendable
	 * @param role
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public DecreaseItemResult decreaseItemWithUniqueId(boolean sendable, Role role, String uniqueId, int amount) {
		DecreaseItemResult result = null;
		//
		BagInfo bagInfo = role.getBagInfo();// 包包欄
		amount = Math.abs(amount);
		Item origItem = bagInfo.getItem(uniqueId);// 原道具
		int[] indexs = bagInfo.getIndex(uniqueId);
		BagInfo.ErrorType bagError = bagInfo.decreaseAmount(uniqueId, amount);
		//
		if (bagError == BagInfo.ErrorType.NO_ERROR) {
			Item cloneItem = clone(origItem);// 減少的道具
			cloneItem.setAmount(amount);// 減少的數量
			result = new DecreaseResultImpl(indexs[0], indexs[1], cloneItem);
		}
		//
		if (sendable && result != null) {
			sendDecreaseItem(role, result);
		}

		return result;
	}

	/**
	 * 發送減少道具
	 * 
	 * @param role
	 * @param decreaseResult
	 */
	protected void sendDecreaseItem(Role role, DecreaseItemResult decreaseResult) {
		List<DecreaseItemResult> decreaseResults = new LinkedList<DecreaseItemResult>();
		decreaseResults.add(decreaseResult);
		//
		sendDecreaseItem(role, decreaseResults);
	}

	/**
	 * 發送減少道具
	 * 
	 * @param role
	 * @param decreaseItemResults
	 */
	public void sendDecreaseItem(Role role, List<DecreaseItemResult> decreaseItemResults) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_DECREASE_RESPONSE, role.getId());
		//
		message.addInt(decreaseItemResults.size());// 道具個數
		for (DecreaseItemResult itemResult : decreaseItemResults) {
			Item item = itemResult.getItem();
			message.addString(item.getUniqueId());// 道具uniqueId
			message.addInt(item.getAmount());// 道具數量
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 計算道具累加價格
	 * 
	 * @param items
	 *            <道具id,數量>
	 * @return
	 */
	public long calcItemsPrice(Map<String, Integer> items) {
		long result = 0;
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			String key = entry.getKey();// 道具id
			int value = safeGet(entry.getValue());// 數量
			Item item = createItem(key);
			if (item != null) {
				result += item.getPrice() * value;
			}
		}
		return result;
	}

	/**
	 * 強化裝備結果
	 */
	public static class EnhanceResultImpl extends AppResultSupporter implements EnhanceResult {

		private static final long serialVersionUID = 4771450693493495692L;

		/**
		 * 是否成功
		 */
		private boolean success;

		/**
		 * 是否消失
		 */
		private boolean disappear;

		public EnhanceResultImpl() {
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public boolean isDisappear() {
			return disappear;
		}

		public void setDisappear(boolean disappear) {
			this.disappear = disappear;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("success", success);
			builder.append("disappear", disappear);
			return builder.toString();
		}
	}

	/**
	 * 強化裝備
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @param enhanceType
	 *            強化類型
	 * @return
	 */
	public EnhanceResult enhanceEquipment(boolean sendable, Role role, Equipment equipment, EnhanceType enhanceType) {
		EnhanceResult result = null;
		//
		if (equipment != null && enhanceType != null) {
			// 下一級的強化等級
			int nextEnhanceValue = equipment.getEnhanceLevel().getValue() + 1;

			// //檢查強化
			// ErrorType errorType = checkEnhance(equipment, nextEnhanceValue,
			// enhanceType);
			// if (errorType != ErrorType.NO_ERROR)
			// {
			// return result;
			// }
			// 下一級的強化因子
			EnhanceFactor nextEnhanceFactor = getEnhanceFactor(equipment, nextEnhanceValue);

			// 強化結果
			result = new EnhanceResultImpl();
			boolean success = randomEnhance(equipment, nextEnhanceFactor);
			result.setSuccess(success);
			// 成功
			if (success) {
				// 強化等級
				equipment.setEnhanceLevel(nextEnhanceFactor.getId());
				// 增加屬性
				changeAttributes(equipment.getAttributeGroup(), nextEnhanceFactor);
			}
			// 失敗,依照不同強化類型處理
			else {
				switch (enhanceType) {
				// 一般強化,失敗裝備消失
				case GENERAL: {
					// 清除強化,不發訊息
					unenhance(false, role, equipment);
					result.setDisappear(true);// 消失
					break;
				}
					// 祝福強化,失敗強化歸0,裝備還在
				case BLESS: {
					// 清除強化,不發訊息
					unenhance(false, role, equipment);
					break;
				}
					// 幻想強化,失敗強化保有原本強化,強化不歸0,且裝備還在
				case FANTASY: {
					break;
				}
				}
			}
		}
		return result;
	}

	/**
	 * 檢查強化
	 * 
	 * @param item
	 * @param enhanceValue
	 * @param enhanceType
	 * @return
	 */
	public ErrorType checkEnhance(Item item, int enhanceValue, EnhanceType enhanceType) {
		ErrorType errorType = ErrorType.NO_ERROR;

		// 是否超過最高強化等級
		boolean overMax = isOverMaxEnhanceLevel(item, enhanceValue);
		if (overMax) {
			errorType = ErrorType.OVER_MAX_ENHANCE_LEVEL;
			return errorType;
		}

		EnhanceFactor nextEnhanceFactor = getEnhanceFactor(item, enhanceValue);
		// 強化因子,若無因子,就無法再強化了
		if (nextEnhanceFactor == null) {
			errorType = ErrorType.ENHANCE_FACTOR_NOT_EXIST;
			return errorType;
		}

		// 是否超過幻想強化等級
		boolean overFantasy = isOverFantasyEnhanceLevel(item, enhanceValue, enhanceType);
		if (overFantasy) {
			errorType = ErrorType.OVER_FANTASY_ENHANCE_LEVEL;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 增加所有屬性,一級級加上去
	 * 
	 * @param attributeGroup
	 * @param enhanceFactor
	 */
	protected void changeAttributes(AttributeGroup attributeGroup, EnhanceFactor enhanceFactor) {
		// 屬性群
		Map<AttributeType, Attribute> attributes = attributeGroup.getAttributes();
		for (Attribute attribute : attributes.values()) {
			// 增加強化所提升的屬性值,所有屬性增加的屬性值都一樣
			attribute.changeAddPoint(enhanceFactor.getPoint());
			attribute.changeAddRate(enhanceFactor.getRate());
		}
	}

	/**
	 * 計算強化後的所有屬性,強化等級會改變屬性
	 * 
	 * 防具,武器,土地
	 * 
	 * @param item
	 * @return
	 */
	public boolean calcEnhanceAttributes(Item item) {
		boolean result = false;
		// 強化因子
		EnhanceFactor enhanceFactor = getAccuEnhanceFactor(item);
		if (enhanceFactor != null) {
			AttributeGroup attributeGroup = null;
			if (item instanceof Equipment) {
				Equipment equipment = (Equipment) item;
				attributeGroup = equipment.getAttributeGroup();
			} else if (item instanceof Land) {
				Land land = (Land) item;
				attributeGroup = land.getAttributeGroup();
			} else {
				return result;
			}

			// 屬性群
			if (attributeGroup != null) {
				Map<AttributeType, Attribute> attributes = attributeGroup.getAttributes();
				for (Attribute attribute : attributes.values()) {
					attribute.setAddPoint(enhanceFactor.getPoint());
					attribute.setAddRate(enhanceFactor.getRate());
				}
				//
				result = true;
			}
		}
		return result;
	}

	/**
	 * 取得最高強化等級
	 * 
	 * @param item
	 * @return
	 */
	protected EnhanceLevel getMaxEnhanceLevel(Item item) {
		EnhanceLevel result = null;
		// 防具
		if (item instanceof Armor) {
			result = armorCollector.getMaxEnhanceLevel();
		}
		// 武器
		else if (item instanceof Weapon) {
			result = weaponCollector.getMaxEnhanceLevel();
		}
		// 土地
		else if (item instanceof Land) {
			result = manorCollector.getMaxEnhanceLevel();
		} else {
			// jest for pretty
		}
		return result;
	}

	/**
	 * 是否超過最高強化等級
	 * 
	 * @param enhanceValue
	 * @param item
	 * @return
	 */
	protected boolean isOverMaxEnhanceLevel(Item item, int enhanceValue) {
		boolean result = false;
		EnhanceLevel maxEnhanceLevel = getMaxEnhanceLevel(item);
		if (maxEnhanceLevel != null && enhanceValue > maxEnhanceLevel.getValue()) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否超過幻想強化等級
	 * 
	 * @param enhanceValue
	 * @param item
	 * @return
	 */
	protected boolean isOverFantasyEnhanceLevel(Item item, int enhanceValue, EnhanceType enhanceType) {
		boolean result = false;
		EnhanceLevel fantasyEnhanceLevel = getFantasyEnhanceLevel(item);
		if (enhanceType == EnhanceType.FANTASY && fantasyEnhanceLevel != null
				&& enhanceValue > fantasyEnhanceLevel.getValue()) {
			result = true;
		}
		return result;
	}

	/**
	 * 取得安全強化等級
	 * 
	 * @param item
	 * @return
	 */
	protected EnhanceLevel getSafeEnhanceLevel(Item item) {
		EnhanceLevel result = null;
		// 防具
		if (item instanceof Armor) {
			result = armorCollector.getSafeEnhanceLevel();
		}
		// 武器
		else if (item instanceof Weapon) {
			result = weaponCollector.getSafeEnhanceLevel();
		}
		// 土地
		else if (item instanceof Land) {
			result = manorCollector.getSafeEnhanceLevel();
		} else {
			// jest for pretty
		}
		return result;
	}

	/**
	 * 取得安全強化等級
	 * 
	 * @param item
	 * @return
	 */
	protected EnhanceLevel getFantasyEnhanceLevel(Item item) {
		EnhanceLevel result = null;
		// 防具
		if (item instanceof Armor) {
			result = armorCollector.getFantasyEnhanceLevel();
		}
		// 武器
		else if (item instanceof Weapon) {
			result = weaponCollector.getFantasyEnhanceLevel();
		}
		// 土地
		else if (item instanceof Land) {
			result = manorCollector.getFantasyEnhanceLevel();
		} else {
			// jest for pretty
		}
		return result;
	}

	/**
	 * 取得強化因子
	 * 
	 * @param item
	 * @param enhanceValue
	 * @return
	 */
	protected EnhanceFactor getEnhanceFactor(Item item, int enhanceValue) {
		EnhanceFactor result = null;
		// 防具
		if (item instanceof Armor) {
			result = armorCollector.getEnhanceFactor(enhanceValue);
		}
		// 武器
		else if (item instanceof Weapon) {
			result = weaponCollector.getEnhanceFactor(enhanceValue);
		}
		// 土地
		else if (item instanceof Land) {
			result = manorCollector.getEnhanceFactor(enhanceValue);
		} else {
			// jest for pretty
		}
		return result;
	}

	/**
	 * 取得累計強化因子
	 * 
	 * @param item
	 * @return
	 */
	protected EnhanceFactor getAccuEnhanceFactor(Item item) {
		EnhanceFactor result = null;
		// 防具
		if (item instanceof Armor) {
			Armor armor = (Armor) item;
			result = armorCollector.getAccuEnhanceFactor(armor.getEnhanceLevel());
		}
		// 武器
		else if (item instanceof Weapon) {
			Weapon weapon = (Weapon) item;
			result = weaponCollector.getAccuEnhanceFactor(weapon.getEnhanceLevel());
		}
		// 土地
		else if (item instanceof Land) {
			Land land = (Land) item;
			result = manorCollector.getAccuEnhanceFactor(land.getEnhanceLevel());
		} else {
			// jest for pretty
		}
		return result;
	}

	/**
	 * 機率強化/安全強化
	 * 
	 * @param item
	 * @param enhanceFactor
	 * @return
	 */
	protected boolean randomEnhance(Item item, EnhanceFactor enhanceFactor) {
		boolean result = false;
		//
		if (item == null || enhanceFactor == null) {
			return result;
		}

		// 安全強化等級, +1-3,不會爆掉, > +3才有機率爆掉
		EnhanceLevel safeEnhanceLevel = getSafeEnhanceLevel(item);

		// 機率強化,當強化> +3 以上,會有機率性失敗
		if (safeEnhanceLevel == null || enhanceFactor.getId().getValue() > safeEnhanceLevel.getValue()) {
			result = NumberHelper.randomWin(enhanceFactor.getProbability());
		}
		// 安全強化,當強化<= +3,一定會成功,此為+3為強化安定值
		else {
			result = true;
		}
		return result;
	}

	/**
	 * 發送強化裝備
	 * 
	 * @param role
	 * @param item
	 * @param enhanceResult
	 */
	public void sendEnhance(Role role, Item item, EnhanceResult enhanceResult) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_ENHANCE_RESPONSE, role.getId());

		message.addBoolean(enhanceResult.isSuccess());// 是否成功
		message.addBoolean(enhanceResult.isDisappear());// 裝備是否消失

		// 裝備沒消失,才發屬性
		if (!enhanceResult.isDisappear()) {
			fillEnhance(message, item);
		}
		//
		messageService.addMessage(message);
	}

	/**
	 * 填充強化裝備
	 * 
	 * @param message
	 * @param item
	 */
	public void fillEnhance(Message message, Item item) {
		message.addString(item.getId());// id
		message.addString(item.getUniqueId());// uniqueId
		//
		AttributeGroup attributeGroup = null;
		if (item instanceof Equipment) {
			Equipment equipment = (Equipment) item;
			attributeGroup = equipment.getAttributeGroup();
			message.addInt(equipment.getEnhanceLevel());// 強化等級
		} else if (item instanceof Land) {
			Land land = (Land) item;
			attributeGroup = land.getAttributeGroup();
			message.addInt(land.getEnhanceLevel());// 強化等級
		}

		// 屬性群
		if (attributeGroup != null) {
			Map<AttributeType, Attribute> attributes = attributeGroup.getAttributes();
			message.addInt(attributes.size());
			for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
				message.addInt(entry.getKey());// 屬性類別,key
				message.addInt(entry.getValue().getPoint());// 屬性值
				message.addInt(entry.getValue().getAddPoint());// 強化所提升的屬性值
				message.addInt(entry.getValue().getAddRate());// 強化所增加的比率值
			}
		}
	}

	/**
	 * 清除強化
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	public boolean unenhance(boolean sendable, Role role, Item item) {
		boolean result = false;
		// 裝備
		Equipment equipment = null;
		// 土地
		Land land = null;
		if (item instanceof Equipment) {
			equipment = (Equipment) item;
			// +0
			equipment.setEnhanceLevel(EnhanceLevel._0);
			// 只清除強化部分
			equipment.getAttributeGroup().clearAddAttributes();
			result = true;
		} else if (item instanceof Land) {
			land = (Land) item;
			// +0
			land.setEnhanceLevel(EnhanceLevel._0);
			// 只清除強化部分
			land.getAttributeGroup().clearAddAttributes();
			result = true;
		} else {
			// jest for pretty
		}

		// 發訊息
		if (result && sendable) {
			sendUnenhance(role, item);
		}

		return result;
	}

	/**
	 * 發送清除裝備強化
	 * 
	 * @param role
	 * @param item
	 */
	public void sendUnenhance(Role role, Item item) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_UNENHANCE_RESPONSE, role.getId());

		message.addString(item.getId());// id
		message.addString(item.getUniqueId());// uniqueId
		//
		messageService.addMessage(message);
	}

	/**
	 * 強化土地
	 * 
	 * @param sendable
	 * @param role
	 * @param land
	 * @param enhanceType
	 *            強化類型
	 * @return
	 */
	public EnhanceResult enhanceLand(boolean sendable, Role role, Land land, EnhanceType enhanceType) {
		EnhanceResult result = null;
		//
		if (land != null && enhanceType != null) {
			// 下一級的強化等級
			int nextEnhanceValue = land.getEnhanceLevel().getValue() + 1;

			// //檢查強化
			// ErrorType errorType = checkEnhance(land, nextEnhanceValue,
			// enhanceType);
			// if (errorType != ErrorType.NO_ERROR)
			// {
			// return result;
			// }
			// 下一級的強化因子
			EnhanceFactor nextEnhanceFactor = getEnhanceFactor(land, nextEnhanceValue);

			// 強化結果
			result = new EnhanceResultImpl();
			boolean success = randomEnhance(land, nextEnhanceFactor);
			result.setSuccess(success);
			// 成功
			if (success) {
				// 強化等級
				land.setEnhanceLevel(nextEnhanceFactor.getId());
				// 累計強化屬性
				changeAttributes(land.getAttributeGroup(), nextEnhanceFactor);
			}
			// 失敗,依照不同強化類型處理
			else {
				switch (enhanceType) {
				// 一般強化,失敗土地消失
				case GENERAL: {
					// 清除強化,不發訊息
					unenhance(false, role, land);
					result.setDisappear(true);// 消失
					break;
				}
					// 祝福強化,失敗強化歸0,土地還在
				case BLESS: {
					// 清除強化,不發訊息
					unenhance(false, role, land);
					break;
				}
					// 幻想強化,失敗強化保有原本強化,強化不歸0,且土地還在
				case FANTASY: {
					break;
				}
				}
			}
		}
		return result;
	}

	/**
	 * 增減強化等級
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param enhanceValue
	 * @return
	 */
	public int changeEnhance(boolean sendable, Role role, Item item, int enhanceValue) {
		// 增減的強化等級
		int result = 0;
		// 檢查條件
		if (role == null) {
			return result;
		}
		// 若=0不改變了
		if (enhanceValue == 0) {
			return result;
		}
		// 最高強化等級
		EnhanceLevel maxEnhanceLevel = getMaxEnhanceLevel(item);
		// 原強化等級
		EnhanceLevel origEnhanceLevel = null;
		// 裝備
		Equipment equipment = null;
		// 土地
		Land land = null;
		if (item instanceof Equipment) {
			equipment = (Equipment) item;
			origEnhanceLevel = equipment.getEnhanceLevel();
		} else if (item instanceof Land) {
			land = (Land) item;
			origEnhanceLevel = land.getEnhanceLevel();
		} else {
			return result;
		}

		// 原強化等級
		origEnhanceLevel = (origEnhanceLevel != null ? origEnhanceLevel : EnhanceLevel._0);
		int origEnhanceValue = origEnhanceLevel.getValue();

		// 新的強化等級
		int newEnhanceValue = origEnhanceValue + enhanceValue;

		// 最後強化等級若小於0,則強化還是等於0
		// (原0,-1) => 0
		// (原1,-1) => 0
		// (原2,-1) => 1
		//
		// (原1,-1) => 0
		// (原2,-3) => 0
		if (newEnhanceValue < 0 && origEnhanceLevel != EnhanceLevel._0) {
			if (equipment != null || land != null) {
				// 清除強化
				unenhance(false, role, item);
				// 增減的強化等級
				result = (-1) * origEnhanceValue;
			} else {
				// jest for pretty
			}
		}
		//
		else if (newEnhanceValue >= 0) {
			EnhanceLevel newEnhanceLevel = null;
			// 是否超過最高強化等級
			boolean overMax = isOverMaxEnhanceLevel(item, newEnhanceValue);
			if (overMax) {
				newEnhanceValue = maxEnhanceLevel.getValue();
			}
			// 新的強化等級
			newEnhanceLevel = EnumHelper.valueOf(EnhanceLevel.class, newEnhanceValue);
			//
			if (newEnhanceLevel != null) {
				boolean changed = false;
				// 裝備
				if (equipment != null) {
					equipment.setEnhanceLevel(newEnhanceLevel);
					calcEnhanceAttributes(equipment);
					changed = true;

				}
				// 土地
				else if (land != null) {
					land.setEnhanceLevel(newEnhanceLevel);
					calcEnhanceAttributes(land);
					changed = true;
				} else {
					// jest for pretty
				}

				// 增減的強化等級
				if (changed) {
					// 增加強化
					if (enhanceValue > 0) {
						result = newEnhanceValue - origEnhanceValue;
					}
					// 減少強化
					else {
						result = enhanceValue;
					}
				}
			}
		}

		// 是否發訊息
		if (result != 0 && sendable) {
			EnhanceResult enhanceResult = new EnhanceResultImpl();
			enhanceResult.setSuccess(true);
			enhanceResult.setDisappear(false);
			//
			sendEnhance(role, item, enhanceResult);
		}
		return result;
	}

	/**
	 * 處理使用道具請求
	 * 
	 * 1.檢查道具
	 * 
	 * 2.使用道具
	 * 
	 * 3.移除道具
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param targetId
	 *            對象id(角色id,裝備uniqueId)
	 * @param itemUniqueId
	 *            道具uniqueId
	 * @param amount
	 *            數量
	 * @return
	 */
	public void useItem(boolean sendable, Role role, String targetId, String itemUniqueId, int amount) {
		ErrorType errorType = ErrorType.NO_ERROR;
		// 檢查條件
		errorType = checkUseItem(role, itemUniqueId, amount);
		if (ErrorType.NO_ERROR == errorType) {
			// 從包包拿消耗道具
			Item item = getItem(role, itemUniqueId);
			// 2.使用道具
			switch (item.getItemType()) {
			case THING: {
				Thing thing = (Thing) item;
				switch (thing.getThingType()) {
				case POTION_HP: {
					// TODO 使用道具
					break;
				}
				case POTION_INSTANT_HP: {
					// TODO 使用道具
					break;
				}
				case POTION_MP: {
					// TODO 使用道具
					break;
				}
				case POTION_INSTANT_MP: {
					// TODO 使用道具
					break;
				}
				case BACK_TOWN: {
					// TODO 使用道具
					break;
				}
				case BACK_INSTANT_TOWN: {
					// TODO 使用道具
					break;
				}
				case ENHANCE_ARMOR: {
					errorType = useEnhanceArmorThing(sendable, role, targetId, item);
					break;
				}
				case ENHANCE_WEAPON: {
					errorType = useEnhanceWeaponThing(sendable, role, targetId, item);
					break;
				}
				case ENHANCE_LAND: {
					errorType = useEnhanceLandThing(sendable, role, targetId, item);
					break;
				}
				case ROLE_EXP: {
					errorType = useRoleExpThing(sendable, role, item, amount);
					break;
				}
				case ROLE_SP: {
					errorType = useRoleSpThing(sendable, role, item, amount);
					break;
				}
				case ROLE_GOLD: {
					errorType = useRoleGoldThing(sendable, role, item, amount);
					break;
				}
				case ROLE_FAME: {
					errorType = useRoleFameThing(sendable, role, item, amount);
					break;
				}
				default: {
					break;
				}
				}
				break;
			}
			default: {
				break;
			}
			}
		}
		//
		if (sendable) {
			sendUseItem(errorType, role);
		}
	}

	/**
	 * 檢查使用道具
	 * 
	 * @param role
	 * @param itemUniqueId
	 *            道具uniqueId
	 * @param amount
	 * @return
	 */
	public ErrorType checkUseItem(Role role, String itemUniqueId, int amount) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			return errorType;
		}

		Item item = getItem(role, itemUniqueId);
		// 道具不存在
		if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
			return errorType;
		}

		// 數量不合法
		if (amount <= 0) {
			errorType = ErrorType.AMOUNT_NOT_VALID;
			return errorType;
		}

		// 數量不足
		if (amount > item.getAmount()) {
			errorType = ErrorType.AMOUNT_NOT_ENOUGH;
			return errorType;
		}

		// 檢查減少道具
		BagInfo.ErrorType bagError = checkDecreaseItemWithUniqueId(role, item.getUniqueId(), amount);
		if (bagError != BagInfo.ErrorType.NO_ERROR) {
			errorType = ErrorType.CAN_NOT_DECREASE_ITEM;
			return errorType;
		}
		//
		return errorType;
	}

	/**
	 * 使用強化防具道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            防具uniqueId
	 * @param item
	 *            道具
	 * @return
	 */
	public ErrorType useEnhanceArmorThing(boolean sendable, Role role, String targetId, Item item) {
		ErrorType errorType = ErrorType.NO_ERROR;

		// 1.目前只從包包拿目標道具,未來考慮拿已裝備後的道具再強化
		Item targetItem = getItem(role, targetId);
		// 裝備不存在
		if (targetItem == null) {
			errorType = ErrorType.EQUIPMENT_NOT_EXIST;
		}
		// 不是防具,無法強化
		else if (!(targetItem instanceof Armor)) {
			errorType = ErrorType.EQUIPMENT_NOT_VALID;
		}
		// 道具不存在
		else if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		// 不是強化防具道具,無法強化
		else if (!(item instanceof EnhanceArmorThing)) {
			errorType = ErrorType.ITEM_NOT_VALID;
		} else {
			// 想要強化的裝備
			Armor armor = (Armor) targetItem;
			// 消耗的強化道具
			EnhanceArmorThing thing = (EnhanceArmorThing) item;

			// 下一級的強化等級
			int nextEnhanceValue = armor.getEnhanceLevel().getValue() + 1;

			// 檢查強化
			errorType = checkEnhance(armor, nextEnhanceValue, thing.getEnhanceType());
			if (errorType != ErrorType.NO_ERROR) {
				return errorType;
			}

			// 強化道具等級與裝備等級不符,無法強化
			if (thing.getLevelType() != armor.getLevelType()) {
				errorType = ErrorType.EQUIPMENT_LEVEL_NOT_VALID;
				return errorType;
			}

			// 使用道具強化
			EnhanceResult enhanceResult = enhanceEquipment(sendable, role, armor, thing.getEnhanceType());

			// 強化結果
			if (enhanceResult != null) {
				// 移除消耗的強化道具
				decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), 1);

				// 防具爆掉了,移除武器
				if (enhanceResult.isDisappear()) {
					decreaseItemWithUniqueId(sendable, role, armor.getUniqueId(), 1);
				}

				// 發訊息
				if (sendable) {
					sendEnhance(role, armor, enhanceResult);
				}
			}
		}
		return errorType;
	}

	/**
	 * 使用強化武器道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            武器uniqueId
	 * @param item
	 *            道具
	 * @return
	 */
	public ErrorType useEnhanceWeaponThing(boolean sendable, Role role, String targetId, Item item) {
		ErrorType errorType = ErrorType.NO_ERROR;

		// 1.目前只從包包拿目標道具,未來考慮拿已裝備後的道具再強化
		Item targetItem = getItem(role, targetId);
		// 裝備不存在
		if (targetItem == null) {
			errorType = ErrorType.EQUIPMENT_NOT_EXIST;
		}
		// 不是武器,無法強化
		else if (!(targetItem instanceof Weapon)) {
			errorType = ErrorType.EQUIPMENT_NOT_VALID;
		}
		// 道具不存在
		else if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		// 不是強化武器道具,無法強化
		else if (!(item instanceof EnhanceWeaponThing)) {
			errorType = ErrorType.ITEM_NOT_VALID;
		} else {
			// 想要強化的裝備
			Weapon weapon = (Weapon) targetItem;
			// 消耗的強化道具
			EnhanceWeaponThing thing = (EnhanceWeaponThing) item;

			// 下一級的強化等級
			int nextEnhanceValue = weapon.getEnhanceLevel().getValue() + 1;

			// 檢查強化
			errorType = checkEnhance(weapon, nextEnhanceValue, thing.getEnhanceType());
			if (errorType != ErrorType.NO_ERROR) {
				return errorType;
			}

			// 強化道具等級與裝備等級不符,無法強化
			if (thing.getLevelType() != weapon.getLevelType()) {
				errorType = ErrorType.EQUIPMENT_LEVEL_NOT_VALID;
				return errorType;
			}

			// 使用道具強化
			EnhanceResult enhanceResult = enhanceEquipment(sendable, role, weapon, thing.getEnhanceType());

			// 強化結果
			if (enhanceResult != null) {
				// 移除消耗的強化道具
				decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), 1);

				// 武器爆掉了,移除武器
				if (enhanceResult.isDisappear()) {
					decreaseItemWithUniqueId(sendable, role, weapon.getUniqueId(), 1);
				}

				// 發訊息
				if (sendable) {
					sendEnhance(role, weapon, enhanceResult);
				}
			}
		}
		return errorType;
	}

	/**
	 * 使用強化土地道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            土地uniqueId
	 * @param item
	 *            道具
	 * @return
	 */
	public ErrorType useEnhanceLandThing(boolean sendable, Role role, String targetId, Item item) {
		ErrorType errorType = ErrorType.NO_ERROR;

		// 1.目前只從包包拿目標道具,未來考慮拿已裝備後的道具再強化
		Item targetItem = getItem(role, targetId);
		// 土地不存在
		if (targetItem == null) {
			errorType = ErrorType.LAND_NOT_EXIST;
		}
		// 不是土地,無法強化
		else if (!(targetItem instanceof Land)) {
			errorType = ErrorType.LAND_NOT_VALID;
		}
		// 道具不存在
		else if (item == null) {
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		// 不是強化土地道具,無法強化
		else if (!(item instanceof EnhanceLandThing)) {
			errorType = ErrorType.ITEM_NOT_VALID;
		} else {
			// 想要強化的裝備
			Land land = (Land) targetItem;
			// 消耗的強化道具
			EnhanceLandThing thing = (EnhanceLandThing) item;

			// 下一級的強化等級
			int nextEnhanceValue = land.getEnhanceLevel().getValue() + 1;

			// 檢查強化
			errorType = checkEnhance(land, nextEnhanceValue, thing.getEnhanceType());
			if (errorType != ErrorType.NO_ERROR) {
				return errorType;
			}

			// 使用道具強化
			EnhanceResult enhanceResult = enhanceLand(sendable, role, land, thing.getEnhanceType());

			// 強化結果
			if (enhanceResult != null) {
				// 移除消耗的強化道具
				decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), 1);

				// 武器爆掉了,移除武器
				if (enhanceResult.isDisappear()) {
					decreaseItemWithUniqueId(sendable, role, land.getUniqueId(), 1);
				}

				// 發訊息
				if (sendable) {
					sendEnhance(role, land, enhanceResult);
				}
			}
		}
		return errorType;
	}

	/**
	 * 使用增加經驗道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 *            道具
	 * @param amount
	 *            數量
	 * @return
	 */
	public ErrorType useRoleExpThing(boolean sendable, Role role, Item item, int amount) {
		ErrorType result = ErrorType.NO_ERROR;
		// 道具不存在
		if (item == null) {
			result = ErrorType.ITEM_NOT_EXIST;
		} else {
			// 消耗的道具
			RoleExpThing thing = (RoleExpThing) item;
			long exp = 0L;
			// 多個道具
			for (int i = 0; i < amount; i++) {
				// 成功
				boolean success = NumberHelper.randomWin(thing.getProbability());
				if (success) {
					exp += thing.getExp();
				}
			}
			//
			if (exp > 0) {
				// 發送到ROLE處理
				sendChangeExp(role, exp);
			}
			// 移除消耗的道具
			decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), amount);
		}
		return result;
	}

	/**
	 * 發送到ROLE處理增減經驗
	 * 
	 * @param role
	 * @param exp
	 * @return
	 */
	protected Message sendChangeExp(Role role, long exp) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.ROLE,
				CoreMessageType.ROLE_CHANGE_EXP_REQUEST, role.getId());

		message.addString(role.getId());// 0, String, 角色id
		message.addLong(exp);// 1, long, 增減的經驗
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 使用增加技魂(sp)道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	public ErrorType useRoleSpThing(boolean sendable, Role role, Item item, int amount) {
		ErrorType result = ErrorType.NO_ERROR;
		// 道具不存在
		if (item == null) {
			result = ErrorType.ITEM_NOT_EXIST;
		} else {
			// 消耗的道具
			RoleSpThing thing = (RoleSpThing) item;
			long sp = 0L;
			// 多個道具
			for (int i = 0; i < amount; i++) {
				// 成功
				boolean success = NumberHelper.randomWin(thing.getProbability());
				if (success) {
					sp += thing.getSp();
				}
			}
			//
			if (sp > 0) {
				sendChangeSp(role, sp);
			}
			// 移除消耗的道具
			decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), amount);
		}
		return result;
	}

	/**
	 * 發送到ROLE處理增減技魂(sp)
	 * 
	 * @param role
	 * @param sp
	 * @return
	 */
	protected Message sendChangeSp(Role role, long sp) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.ROLE,
				CoreMessageType.ROLE_CHANGE_SP_REQUEST, role.getId());

		message.addString(role.getId());// 0, String, 角色id
		message.addLong(sp);// 1, long, 增減的技魂
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 使用增加金幣道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 *            道具
	 * @param amount
	 *            數量
	 * @return
	 */
	public ErrorType useRoleGoldThing(boolean sendable, Role role, Item item, int amount) {
		ErrorType result = ErrorType.NO_ERROR;
		// 道具不存在
		if (item == null) {
			result = ErrorType.ITEM_NOT_EXIST;
		} else {
			// 消耗的道具
			RoleGoldThing thing = (RoleGoldThing) item;
			long gold = 0L;
			// 多個道具
			for (int i = 0; i < amount; i++) {
				// 成功
				boolean success = NumberHelper.randomWin(thing.getProbability());
				if (success) {
					gold += thing.getGold();
				}
			}
			//
			if (gold > 0) {
				sendChangeGold(role, gold, GoldType.ITEM_ROLE_GOLD_THING);
			}
			// 移除消耗的道具
			decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), amount);
		}
		return result;
	}

	/**
	 * 發送到ROLE處理增減金幣
	 * 
	 * @param role
	 * @param gold
	 * @param goldReason
	 * @return
	 */
	protected Message sendChangeGold(Role role, long gold, GoldType goldReason) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.ROLE,
				CoreMessageType.ROLE_CHANGE_GOLD_REQUEST, role.getId());

		message.addString(role.getId());// 0, String, 角色id
		message.addLong(gold);// 1, long, 增減的金幣
		message.addInt(goldReason);// 2, int, GoldType=金幣增減的原因
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 使用增加聲望道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 *            道具
	 * @param amount
	 *            數量
	 * @return
	 */
	public ErrorType useRoleFameThing(boolean sendable, Role role, Item item, int amount) {
		ErrorType result = ErrorType.NO_ERROR;
		// 道具不存在
		if (item == null) {
			result = ErrorType.ITEM_NOT_EXIST;
		} else {
			// 消耗的道具
			RoleFameThing thing = (RoleFameThing) item;
			int fame = 0;
			// 多個道具
			for (int i = 0; i < amount; i++) {
				// 成功
				boolean success = NumberHelper.randomWin(thing.getProbability());
				if (success) {
					fame += thing.getFame();
				}
			}
			//
			if (fame > 0) {
				sendChangeFame(role, fame);
			}
			// 移除消耗的道具
			decreaseItemWithUniqueId(sendable, role, thing.getUniqueId(), amount);
		}
		return result;
	}

	/**
	 * 發送到ROLE處理增減聲望
	 * 
	 * @param role
	 * @param fame
	 * @return
	 */
	protected Message sendChangeFame(Role role, int fame) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.ROLE,
				CoreMessageType.ROLE_CHANGE_FAME_REQUEST, role.getId());

		message.addString(role.getId());// 0, String, 角色id
		message.addInt(fame);// 1, int, 增減的聲望
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送使用道具回應
	 * 
	 * @param errorType
	 * @param role
	 */
	public void sendUseItem(ErrorType errorType, Role role) {
		Message message = messageService.createMessage(CoreModuleType.ITEM, CoreModuleType.CLIENT,
				CoreMessageType.ITEM_USE_RESPONSE, role.getId());

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
	 * 穿裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @return
	 */
	public boolean putEquipment(boolean sendable, Role role, Equipment equipment) {
		boolean result = false;
		// 檢查條件
		if (equipment == null) {
			return result;
		}
		// 裝備欄
		EquipmentPen equipmentPen = role.getEquipmentPen();
		// 穿裝備
		EquipmentPen.ErrorType errorType = equipmentPen.addEquipment(equipment);
		if (EquipmentPen.ErrorType.NO_ERROR != errorType) {
			return result;
		}
		// 增加equipmentGroup屬性
		increaseAttributes(role, equipment);

		// TODO 同步所有slave

		// TODO 發裝備異動訊息

		// TODO 發屬性異動訊息

		result = true;
		return result;
	}

	public void increaseAttributes(Role role, Equipment equipment) {
		changeAttributes(role, equipment, AttributeAction.INCREASE);
	}

	public void decreaseAttributes(Role role, Equipment equipment) {
		changeAttributes(role, equipment, AttributeAction.DECREASE);
	}

	/**
	 * 增減裝備屬性
	 * 
	 * @param role
	 * @param equipment
	 * @param attributeAction
	 *            INCREASE=增加屬性,DECREASE=減少屬性
	 */
	public void changeAttributes(Role role, Equipment equipment, AttributeAction attributeAction) {
		// 檢查條件
		if (role == null) {
			return;
		}
		//
		if (equipment == null) {
			return;
		}
		//
		// 角色裝備屬性群
		AttributeGroup roleEquipmentGroup = role.getEquipmentGroup();

		// 增減因子
		int factor = 0;
		switch (attributeAction) {
		case INCREASE: {
			factor = 1;
			break;
		}
		case DECREASE: {
			factor = (-1);
			break;
		}
		default: {
			break;
		}
		}

		// 裝備屬性群
		AttributeGroup attributeGroup = equipment.getAttributeGroup();
		Map<AttributeType, Attribute> attributes = attributeGroup.getAttributes();
		for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
			AttributeType key = entry.getKey();
			Attribute value = entry.getValue();
			// 判斷角色上是否有此裝備屬性
			Attribute existAttribute = roleEquipmentGroup.getAttribute(key);
			if (existAttribute == null) {
				// 若無屬性,塞一個新的進去
				existAttribute = new AttributeImpl(key);
				roleEquipmentGroup.addAttribute(existAttribute);
			}

			// 增減屬性值
			existAttribute.changePoint(value.getPoint() * factor);
			existAttribute.changeAddPoint(value.getAddPoint() * factor);
			existAttribute.changeAddRate(value.getAddRate() * factor);
			// 若final屬性為0,則移掉吧
			if (existAttribute.getFinal() == 0) {
				roleEquipmentGroup.removeAttribute(existAttribute);
			}
		}

		// 精鍊屬性群
		AttributeGroup refiningGroup = equipment.getRefiningGroup();
		Map<AttributeType, Attribute> refiningAttributes = refiningGroup.getAttributes();
		for (Map.Entry<AttributeType, Attribute> entry : refiningAttributes.entrySet()) {
			AttributeType key = entry.getKey();
			Attribute value = entry.getValue();
			// 判斷角色上是否有此裝備屬性
			Attribute existAttribute = roleEquipmentGroup.getAttribute(key);
			if (existAttribute == null) {
				// 若無屬性,塞一個新的進去
				existAttribute = new AttributeImpl(key);
				roleEquipmentGroup.addAttribute(existAttribute);
			}

			// 增減屬性值
			existAttribute.changePoint(value.getPoint() * factor);
			existAttribute.changeAddPoint(value.getAddPoint() * factor);
			existAttribute.changeAddRate(value.getAddRate() * factor);
			// 若final屬性為0,則移掉吧
			if (existAttribute.getFinal() == 0) {
				roleEquipmentGroup.removeAttribute(existAttribute);
			}

		}
	}

	/**
	 * 脫裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @return
	 */
	public boolean takeEquipment(boolean sendable, Role role, Equipment equipment) {
		boolean result = false;
		// 檢查條件
		if (equipment == null) {
			return result;
		}
		// 裝備欄
		EquipmentPen equipmentPen = role.getEquipmentPen();
		// 脫裝備
		EquipmentPen.ErrorType errorType = equipmentPen.removeEquipment(equipment);
		if (EquipmentPen.ErrorType.NO_ERROR != errorType) {
			return result;
		}
		// 減少equipmentGroup屬性
		decreaseAttributes(role, equipment);

		// TODO 同步所有slave

		// TODO 發裝備異動訊息

		// TODO 發屬性異動訊息

		result = true;
		return result;
	}

}
