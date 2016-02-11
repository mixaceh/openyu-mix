package org.openyu.mix.item.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface ItemLogDao extends AppLogDao
{
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
}
