package org.openyu.mix.item.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.BagPen.Tab;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 道具服務
 */
public interface ItemService extends AppService {
	// --------------------------------------------------
	// ItemIncreaseLog 加到包包,倉庫,郵件,BAG,WAREHOUSE,MAIL
	//
	// ItemDecreaseLog 從包包,倉庫,郵件刪除,BAG,WAREHOUSE,MAIL
	//
	// ItenEnhanceLog 道具強化,USE_ENHANCE,CHANGE_ENHANCE
	// --------------------------------------------------
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		/**
		 * 包包
		 */
		BAG(1),

		/**
		 * 倉庫
		 */
		WAREHOUSE(2),

		/**
		 * 郵件
		 */
		MAIL(3),

		/**
		 * 使用強化道具,增減強化等級
		 */
		USE_ENHANCE(4),

		/**
		 * 沒使用強化道具,增減強化等級
		 */
		CHANGE_ENHANCE(5),

		//
		;
		private final int value;

		private ActionType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum {
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(11),

		/**
		 * 道具不存在
		 */
		ITEM_NOT_EXIST(13),

		/**
		 * 道具不合法
		 */
		ITEM_NOT_VALID(14),

		/**
		 * 數量不合法
		 */
		AMOUNT_NOT_VALID(15),

		/**
		 * 數量不足
		 */
		AMOUNT_NOT_ENOUGH(16),

		/**
		 * 超過最高強化等級
		 */
		OVER_MAX_ENHANCE_LEVEL(21),

		/**
		 * 超過幻想強化等級
		 */
		OVER_FANTASY_ENHANCE_LEVEL(22),

		/**
		 * 強化因子不存在
		 */
		ENHANCE_FACTOR_NOT_EXIST(23),

		/**
		 * 裝備不存在
		 */
		EQUIPMENT_NOT_EXIST(24),

		/**
		 * 裝備不合法
		 */
		EQUIPMENT_NOT_VALID(25),

		/**
		 * 裝備等級不合法
		 */
		EQUIPMENT_LEVEL_NOT_VALID(26),

		/**
		 * 土地不存在
		 */
		LAND_NOT_EXIST(27),

		/**
		 * 土地不合法
		 */
		LAND_NOT_VALID(28),

		/**
		 * 土地等級不合法
		 */
		LAND_LEVEL_NOT_VALID(29),

		/**
		 * 無法增加道具
		 */
		CAN_NOT_INCREASE_ITEM(31),

		/**
		 * 無法減少道具
		 */
		CAN_NOT_DECREASE_ITEM(32),

		/**
		 * 等級不足
		 */
		LEVLE_NOT_ENOUGH(51),

		/**
		 * 金幣不足
		 */
		GOLD_NOT_ENOUGH(52),

		/**
		 * 儲值幣不足
		 */
		COIN_NOT_ENOUGH(53),

		/**
		 * vip不足
		 */
		VIP_NOT_ENOUGH(54),
		//
		;

		private final int value;

		private ErrorType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this,
					ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon,Seed,Land
	 * 
	 * @param itemId
	 * @return
	 */
	<T extends Item> T createItem(String itemId);

	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon,Seed,Land
	 * 
	 * @param itemId
	 * @param amount
	 * @return
	 */
	<T extends Item> T createItem(String itemId, int amount);

	/**
	 * 建構道具,依序為Thing,Material,Armor,Weapon,Seed,Land
	 * 
	 * 適用於來自於DB轉回物件時
	 * 
	 * @param itemId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	<T extends Item> T createItem(String itemId, String uniqueId, int amount);

	/**
	 * 建構裝備,依序為Armor,Weapon
	 * 
	 * @param equipmentId
	 * @return
	 */
	<T extends Equipment> T createEquipment(String equipmentId);

	/**
	 * 建構裝備,依序為Armor,Weapon
	 * 
	 * 適用於來自於DB轉回物件時
	 * 
	 * @param equipmentId
	 * @param uniqueId
	 * @return
	 */
	<T extends Equipment> T createEquipment(String equipmentId, String uniqueId);

	/**
	 * 建構物品
	 * 
	 * @param thingId
	 * @param amount
	 * @return
	 */
	Thing createThing(String thingId, int amount);

	/**
	 * 建構物品
	 * 
	 * @param thingId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	Thing createThing(String thingId, String uniqueId, int amount);

	/**
	 * 建構材料
	 * 
	 * @param materialId
	 * @param amount
	 * @return
	 */
	Material createMaterial(String materialId, int amount);

	/**
	 * 建構材料
	 * 
	 * @param materialId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	Material createMaterial(String materialId, String uniqueId, int amount);

	/**
	 * 建構防具
	 * 
	 * @param armorId
	 * @return
	 */
	Armor createArmor(String armorId);

	/**
	 * 建構防具
	 * 
	 * @param armorId
	 * @param uniqueId
	 * @return
	 */
	Armor createArmor(String armorId, String uniqueId);

	/**
	 * 建構武器
	 * 
	 * @param weaponId
	 * @return
	 */
	Weapon createWeapon(String weaponId);

	/**
	 * 建構武器
	 * 
	 * @param weaponId
	 * @param uniqueId
	 * @return
	 */
	Weapon createWeapon(String weaponId, String uniqueId);

	/**
	 * 建構種子
	 * 
	 * @param seedId
	 * @param amount
	 * @return
	 */
	Seed createSeed(String seedId, int amount);

	/**
	 * 建構種子
	 * 
	 * @param seedId
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	Seed createSeed(String seedId, String uniqueId, int amount);

	/**
	 * 建構土地
	 * 
	 * @param landId
	 * @return
	 */
	Land createLand(String landId);

	/**
	 * 建構土地
	 * 
	 * @param landId
	 * @param uniqueId
	 * @return
	 */
	Land createLand(String landId, String uniqueId);

	// --------------------------------------------------
	// 道具
	// --------------------------------------------------
	/**
	 * 取得道具
	 * 
	 * @param role
	 * @param uniqueId
	 * @return
	 */
	Item getItem(Role role, String uniqueId);

	/**
	 * 發送道具
	 * 
	 * @param role
	 * @param uniqueId
	 */
	void sendItem(Role role, String uniqueId);

	/**
	 * 填充道具
	 * 
	 * @param message
	 * @param item
	 */
	void fillItem(Message message, Item item);

	/**
	 * 改變所有的包包頁是否鎖定
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	boolean changeBagPenLocked(boolean sendable, Role role);

	/**
	 * 發送單一包包頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param tab
	 */
	void sendTabLocked(String roleId, Tab tab);

	/**
	 * 發送所有包包頁是否鎖定回應
	 * 
	 * @param roleId
	 * @param bagPen
	 */
	void sendTabsLocked(String roleId, BagPen bagPen);

	/**
	 * 填充包包頁是否鎖定
	 * 
	 * @param message
	 * @param tab
	 */
	void fillTabLocked(Message message, Tab tab);

	/**
	 * 發送包包欄位
	 * 
	 * @param roleId
	 * @param bagPen
	 */
	void sendBagPen(String roleId, BagPen bagPen);

	/**
	 * 填充包包欄位
	 * 
	 * @param message
	 * @param bagPen
	 */
	void fillBagPen(Message message, BagPen bagPen);

	/**
	 * 發送包包頁
	 * 
	 * @param roleId
	 * @param tab
	 */
	void sendTab(String roleId, Tab tab);

	/**
	 * 填充包包頁
	 * 
	 * @param message
	 * @param tab
	 */
	void fillTab(Message message, Tab tab);

	/**
	 * 檢查增加道具, with itemId
	 * 
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	BagPen.ErrorType checkIncreaseItemWithItemId(Role role, String itemId, int amount);

	/**
	 * 檢查增加道具
	 * 
	 * @param role
	 * @param item
	 * @return
	 */
	BagPen.ErrorType checkIncreaseItem(Role role, Item item);

	/**
	 * 檢查增加多個不同道具
	 * 
	 * @param role
	 * @param items
	 * @return
	 */
	BagPen.ErrorType checkIncreaseItems(Role role, Map<String, Integer> items);

	/**
	 * 增加道具結果
	 */
	interface IncreaseItemResult extends AppResult {
		/**
		 * 包包頁索引
		 * 
		 * @return
		 */
		int getTabIndex();

		void setTabIndex(int tabIndex);

		/**
		 * 格子索引
		 * 
		 * @return
		 */
		int getGridIndex();

		void setGridIndex(int gridIndex);

		/**
		 * 增加的道具
		 * 
		 * @return
		 */
		Item getItem();

		void setItem(Item item);

		/**
		 * 原道具數量
		 * 
		 * @return
		 */
		int getOrigAmount();

		void setOrigAmount(int origAmount);

		/**
		 * 是否新的道具
		 */
		boolean isNewable();

		void setNewable(boolean newable);
	}

	/**
	 * 增加道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	List<IncreaseItemResult> increaseItem(boolean sendable, Role role, Item item);

	/**
	 * 增加道具, with itemId
	 * 
	 * @param sendable
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	List<IncreaseItemResult> increaseItemWithItemId(boolean sendable, Role role,
			String itemId, int amount);

	/**
	 * 增加多個道具
	 * 
	 * @param sendable
	 * @param role
	 * @param items
	 * @return
	 */
	List<IncreaseItemResult> increaseItems(boolean sendable, Role role,
			Map<String, Integer> items);

	/**
	 * 發送增加道具
	 * 
	 * @param role
	 * @param increaseItemResults
	 */
	void sendIncreaseItem(Role role,
			List<IncreaseItemResult> increaseItemResults);

	/**
	 * 填充增加道具
	 * 
	 * @param message
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 */
	void fillIncreaseItem(Message message, int tabIndex, int gridIndex,
			Item item);

	/**
	 * 檢查減少道具,by itemId
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	BagPen.ErrorType checkDecreaseItem(Role role, String itemId, int amount);

	/**
	 * 檢查減少道具
	 * 
	 * @param role
	 * @param item
	 * @return
	 */
	BagPen.ErrorType checkDecreaseItem(Role role, Item item);

	/**
	 * 檢查減少道具,by uniqueId
	 * 
	 * @param role
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	BagPen.ErrorType checkDecreaseItemWithUniqueId(Role role, String uniqueId,
			int amount);

	/**
	 * 減少道具結果
	 */
	interface DecreaseItemResult extends AppResult {
		/**
		 * 包包頁索引
		 * 
		 * @return
		 */
		int getTabIndex();

		void setTabIndex(int tabIndex);

		/**
		 * 格子索引
		 * 
		 * @return
		 */
		int getGridIndex();

		void setGridIndex(int gridIndex);

		/**
		 * 扣除的道具
		 * 
		 * @return
		 */
		Item getItem();

		void setItem(Item item);

	}

	/**
	 * 減少道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	List<DecreaseItemResult> decreaseItem(boolean sendable, Role role, Item item);

	/**
	 * 減少道具, with itemId
	 * 
	 * @param sendable
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	List<DecreaseItemResult> decreaseItemWithItemId(boolean sendable, Role role,
			String itemId, int amount);

	/**
	 * 減少道具, with uniqueId
	 * 
	 * @param sendable
	 * @param role
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	DecreaseItemResult decreaseItemWithUniqueId(boolean sendable, Role role,
			String uniqueId, int amount);

	/**
	 * 發送減少道具
	 * 
	 * @param role
	 * @param decreaseItemResults
	 */
	void sendDecreaseItem(Role role,
			List<DecreaseItemResult> decreaseItemResults);

	/**
	 * 計算道具累加價格
	 * 
	 * @param items
	 *            <道具id,數量>
	 * @return
	 */
	long calcItemsPrice(Map<String, Integer> items);

	// --------------------------------------------------
	// 強化裝備
	// --------------------------------------------------
	/**
	 * 強化裝備結果
	 */
	interface EnhanceResult extends AppResult {
		/**
		 * 是否成功
		 * 
		 * @return
		 */
		boolean isSuccess();

		void setSuccess(boolean success);

		/**
		 * 是否消失
		 * 
		 * @return
		 */
		boolean isDisappear();

		void setDisappear(boolean disappear);
	}

	/**
	 * 強化裝備
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @param enhanceType
	 *            強化類型
	 */
	EnhanceResult enhanceEquipment(boolean sendable, Role role,
			Equipment equipment, EnhanceType enhanceType);

	/**
	 * 檢查強化
	 * 
	 * @param item
	 * @param enhanceValue
	 * @param enhanceType
	 * @return
	 */
	ErrorType checkEnhance(Item item, int enhanceValue, EnhanceType enhanceType);

	/**
	 * 計算強化後的所有屬性,強化等級會改變屬性
	 * 
	 * 防具,武器,土地
	 * 
	 * @param item
	 * @return
	 */
	boolean calcEnhanceAttributes(Item item);

	/**
	 * 發送強化
	 * 
	 * @param role
	 * @param item
	 * @param enhanceResult
	 */
	void sendEnhance(Role role, Item item, EnhanceResult enhanceResult);

	/**
	 * 填充強化
	 * 
	 * @param message
	 * @param item
	 */
	void fillEnhance(Message message, Item item);

	/**
	 * 清除強化
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @return
	 */
	boolean unenhance(boolean sendable, Role role, Item item);

	/**
	 * 發送清除強化
	 * 
	 * @param role
	 * @param item
	 */
	void sendUnenhance(Role role, Item item);

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
	EnhanceResult enhanceLand(boolean sendable, Role role, Land land,
			EnhanceType enhanceType);

	/**
	 * 增減強化等級
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param enhanceValue
	 *            增減的強化
	 * @return
	 */
	int changeEnhance(boolean sendable, Role role, Item item, int enhanceValue);

	// --------------------------------------------------
	// 使用道具
	// --------------------------------------------------

	/**
	 * 使用道具
	 * 
	 * 1.檢查道具
	 * 
	 * 2.使用道具
	 * 
	 * 3.移除道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            對象id(角色id,裝備uniqueId)
	 * @param itemUniqueId
	 *            道具uniqueId
	 * @param amount
	 *            數量
	 * @return
	 */
	void useItem(boolean sendable, Role role, String targetId, String itemUniqueId,
			int amount);

	/**
	 * 檢查使用道具
	 * 
	 * @param role
	 * @param itemUniqueId
	 *            道具uniqueId
	 * @param amount
	 * @return
	 */
	ErrorType checkUseItem(Role role, String itemUniqueId, int amount);

	/**
	 * 使用強化防具道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            防具uniqueId
	 * @param item
	 *            消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceArmorThing(boolean sendable, Role role,
			String targetId, Item item);

	/**
	 * 使用強化武器道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            武器uniqueId
	 * @param item
	 *            消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceWeaponThing(boolean sendable, Role role,
			String targetId, Item item);

	/**
	 * 使用強化土地道具
	 * 
	 * @param sendable
	 * @param role
	 * @param targetId
	 *            土地uniqueId
	 * @param item
	 *            消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceLandThing(boolean sendable, Role role, String targetId,
			Item item);

	/**
	 * 使用增加經驗道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleExpThing(boolean sendable, Role role, Item item, int amount);

	/**
	 * 使用增加技魂(sp)道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleSpThing(boolean sendable, Role role, Item item, int amount);

	/**
	 * 使用增加金幣道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleGoldThing(boolean sendable, Role role, Item item,
			int amount);

	/**
	 * 使用增加聲望道具
	 * 
	 * @param sendable
	 * @param role
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleFameThing(boolean sendable, Role role, Item item,
			int amount);

	/**
	 * 發送使用道具回應
	 * 
	 * @param errorType
	 * @param role
	 */
	void sendUseItem(ErrorType errorType, Role role);

	//

	/**
	 * 穿裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @return
	 */
	boolean putEquipment(boolean sendable, Role role, Equipment equipment);

	/**
	 * 脫裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param role
	 * @param equipment
	 * @return
	 */
	boolean takeEquipment(boolean sendable, Role role, Equipment equipment);

}
