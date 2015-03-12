package org.openyu.mix.core.service;

import java.util.List;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface CoreLogService extends AppLogService
{
	// --------------------------------------------------
	// db
	// --------------------------------------------------

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
	 * 
	 * @param roleId
	 * @param enterTime
	 * @return
	 */
	CoreConnectLog findCoreConnectLog(String roleId, Long enterTime);

	/**
	 * 查詢角色連線,最後上線時間log
	 * 
	 * @param roleId
	 * @param enterTime
	 * @return
	 */
	CoreConnectLog findCoreConnectLogByLatest(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 紀錄角色連線
	 * 
	 * @param role
	 */
	void recordRoleConnect(Role role);

	/**
	 * 紀錄角色斷線
	 * 
	 * @param role
	 */
	void recordRoleDisconnect(Role role);

}
