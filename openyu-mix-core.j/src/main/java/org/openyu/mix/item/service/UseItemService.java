package org.openyu.mix.item.service;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.service.ItemService.ErrorType;
import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.vo.Land;
import org.openyu.socklet.message.vo.Message;

/**
 * 使用道具服務
 */
public interface UseItemService extends AppService
{

	// --------------------------------------------------
	// 強化裝備
	// --------------------------------------------------
	/**
	 * 強化裝備結果
	 */
	public interface EnhanceResult extends AppResult
	{
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
	 * @param roleId
	 * @param equipment
	 * @param enhanceType 強化類型
	 */
	EnhanceResult enhanceEquipment(boolean sendable, String roleId, Equipment equipment,
									EnhanceType enhanceType);

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
	 * @param roleId
	 * @param item
	 * @param enhanceResult
	 */
	void sendEnhance(String roleId, Item item, EnhanceResult enhanceResult);

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
	 * @param roleId
	 * @param item
	 * @return
	 */
	boolean disenhance(boolean sendable, String roleId, Item item);

	/**
	 * 發送清除強化
	 * 
	 * @param roleId
	 * @param item
	 */
	void sendDisenhance(String roleId, Item item);

	/**
	 * 強化土地
	 * 
	 * @param sendable
	 * @param roleId
	 * @param land
	 * @param enhanceType 強化類型
	 * @return
	 */
	EnhanceResult enhanceLand(boolean sendable, String roleId, Land land, EnhanceType enhanceType);

	/**
	 * 增減強化等級
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param enhanceValue 增減的強化
	 * @return
	 */
	int changeEnhance(boolean sendable, String roleId, Item item, int enhanceValue);

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
	 * @param roleId
	 * @param targetId 對象id(角色id,裝備uniqueId)
	 * @param itemUniqueId 道具uniqueId
	 * @param amount 數量
	 * @return
	 */
	void use(boolean sendable, String roleId, String targetId, String itemUniqueId, int amount);

	/**
	 * 檢查使用道具
	 * 
	 * @param roleId
	 * @param itemUniqueId 道具uniqueId
	 * @param amount
	 * @return
	 */
	ErrorType checkUse(String roleId, String itemUniqueId, int amount);

	/**
	 * 使用強化防具道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param targetId 防具uniqueId
	 * @param item 消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceArmorThing(boolean sendable, String roleId, String targetId, Item item);

	/**
	 * 使用強化武器道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param targetId 武器uniqueId
	 * @param item 消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceWeaponThing(boolean sendable, String roleId, String targetId, Item item);

	/**
	 * 使用強化土地道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param targetId 土地uniqueId
	 * @param item 消耗的強化道具
	 * @return
	 */
	ErrorType useEnhanceLandThing(boolean sendable, String roleId, String targetId, Item item);

	/**
	 * 使用增加經驗道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleExpThing(boolean sendable, String roleId, Item item, int amount);

	/**
	 * 使用增加技魂(sp)道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleSpThing(boolean sendable, String roleId, Item item, int amount);

	/**
	 * 使用增加金幣道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleGoldThing(boolean sendable, String roleId, Item item, int amount);

	/**
	 * 使用增加聲望道具
	 * 
	 * @param sendable
	 * @param roleId
	 * @param item
	 * @param amount
	 * @return
	 */
	ErrorType useRoleFameThing(boolean sendable, String roleId, Item item, int amount);

	/**
	 * 發送使用道具回應
	 * 
	 * @param errorType
	 * @param roleId
	 */
	void sendUse(ErrorType errorType, String roleId);

}
