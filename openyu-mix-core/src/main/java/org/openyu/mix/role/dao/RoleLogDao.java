package org.openyu.mix.role.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.role.log.RoleFameLog;
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.log.RoleLevelLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface RoleLogDao extends AppLogDao
{
	// --------------------------------------------------
	// RoleLevelLog
	// --------------------------------------------------
	/**
	 * 查詢等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleLevelLog> findRoleLevelLog(String roleId);

	/**
	 * 分頁查詢等級改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<RoleLevelLog> findRoleLevelLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRoleLevelLog(String roleId);

	// --------------------------------------------------
	// RoleGoldLog
	// --------------------------------------------------
	/**
	 * 查詢金幣改變log
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleGoldLog> findRoleGoldLog(String roleId);

	/**
	 * 分頁查詢金幣改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<RoleGoldLog> findRoleGoldLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除金幣改變log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRoleGoldLog(String roleId);

	// --------------------------------------------------
	// RoleFameLog
	// --------------------------------------------------
	/**
	 * 查詢聲望改變log
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleFameLog> findRoleFameLog(String roleId);

	/**
	 * 分頁查詢聲望改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<RoleFameLog> findRoleFameLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除聲望改變log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRoleFameLog(String roleId);

}
