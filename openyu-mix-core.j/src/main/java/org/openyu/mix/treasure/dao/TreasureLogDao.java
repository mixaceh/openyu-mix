package org.openyu.mix.treasure.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface TreasureLogDao extends AppLogDao
{
	// --------------------------------------------------
	// TreasureRefreshLog
	// --------------------------------------------------
	/**
	 * 查詢祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	List<TreasureRefreshLog> findTreasureRefreshLog(String roleId);

	/**
	 * 分頁查詢祕寶刷新log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<TreasureRefreshLog> findTreasureRefreshLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteTreasureRefreshLog(String roleId);

	// --------------------------------------------------
	// TreasureBuyLog
	// --------------------------------------------------
	/**
	 * 查詢祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	List<TreasureBuyLog> findTreasureBuyLog(String roleId);

	/**
	 * 分頁查詢祕寶購買log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<TreasureBuyLog> findTreasureBuyLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteTreasureBuyLog(String roleId);

}
