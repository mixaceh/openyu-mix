package org.openyu.mix.item.service;

import java.util.List;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

/**
 * 道具日誌服務
 */
public interface ItemLogService extends AppLogService
{
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------
	/**
	 * 查詢道具增加log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ItemIncreaseLog> findItemIncreaseLog(String roleId);

	/**
	 * 分頁查詢道具增加log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ItemIncreaseLog> findItemIncreaseLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除道具增加log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteItemIncreaseLog(String roleId);

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------
	/**
	 * 查詢道具減少log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ItemDecreaseLog> findItemDecreaseLog(String roleId);

	/**
	 * 分頁查詢道具減少log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ItemDecreaseLog> findItemDecreaseLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除道具減少log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteItemDecreaseLog(String roleId);

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------
	/**
	 * 查詢道具強化log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ItemEnhanceLog> findItemEnhanceLog(String roleId);

	/**
	 * 分頁查詢道具強化log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ItemEnhanceLog> findItemEnhanceLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除道具強化log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteItemEnhanceLog(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------

	/**
	 * 紀錄道具增加
	 * 
	 * @param role
	 * @param actionType
	 * @param increaseResults
	 */
	void recordIncreaseItem(Role role, ActionType actionType, List<IncreaseItemResult> increaseItemResults);

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------

	/**
	 * 紀錄道具減少
	 * 
	 * @param role
	 * @param actionType
	 * @param decreaseResults
	 */
	void recordDecreaseItem(Role role, ActionType actionType, List<DecreaseItemResult> decreaseItemResults);

	/**
	 * 紀錄道具減少,by uniqueId
	 * 
	 * @param role
	 * @param actionType
	 * @param decreaseResult
	 */
	void recordDecreaseItem(Role role, ActionType actionType, DecreaseItemResult decreaseItemResult);

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------

	/**
	 * 紀錄道具強化
	 * 
	 * @param role
	 * @param beforeItem 強化前道具
	 * @param afterItem 強化後道具
	 * @param spendItem 消耗的道具
	 * @param actionType
	 */
	void recordChangeEnhance(Role role, ActionType actionType, Item beforeItem, Item afterItem,
						Item spendItem);

}
