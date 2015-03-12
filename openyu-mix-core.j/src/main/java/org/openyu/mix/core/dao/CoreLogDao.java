package org.openyu.mix.core.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface CoreLogDao extends AppLogDao
{
	// --------------------------------------------------
	// CoreConnectLog
	// --------------------------------------------------
	/**
	 * 查詢角色連線log
	 * 
	 * @param roleId
	 * @return
	 */
	List<CoreConnectLog> findCoreConnectLog(String roleId);

	/**
	 * 分頁查詢角色連線log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<CoreConnectLog> findCoreConnectLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除角色連線log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteCoreConnectLog(String roleId);

	/**
	 * 查詢角色連線,上線時間log
	 * @param roleId
	 * @param enterTime
	 * @return
	 */
	CoreConnectLog findCoreConnectLog(String roleId, Long enterTime);

	/**
	 * 查詢角色連線,最後上線時間log
	 * @param roleId
	 * @param enterTime
	 * @return
	 */
	CoreConnectLog findCoreConnectLogByLatest(String roleId);

}
