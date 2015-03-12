package org.openyu.mix.role.service;

import java.util.List;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.role.log.RoleFameLog;
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.log.RoleLevelLog;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface RoleLogService extends AppLogService
{
	// --------------------------------------------------
	// db
	// --------------------------------------------------

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
	 * 查詢等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleFameLog> findRoleFameLog(String roleId);

	/**
	 * 分頁查詢等級改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<RoleFameLog> findRoleFameLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRoleFameLog(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	// --------------------------------------------------
	// RoleLevelLog
	// --------------------------------------------------
	/**
	 * 紀錄增減等級
	 * 
	 * @param role
	 * @param level
	 * @param beforeLevel
	 */
	void recordChangeLevel(Role role, int level, int beforeLevel);

	// --------------------------------------------------
	// RoleGoldLog
	// --------------------------------------------------
	/**
	 * 紀錄增加金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 */
	void recordIncreaseGold(Role role, long gold, long beforeGold, GoldType goldReason);

	/**
	 * 紀錄減少金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 */
	void recordDecreaseGold(Role role, long gold, long beforeGold, GoldType goldReason);

	/**
	 * 紀錄增減金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 * @param goldAction
	 */
	void recordChangeGold(Role role, long gold, long beforeGold, ActionType goldAction,
							GoldType goldReason);

	/**
	 * 紀錄增減聲望
	 * 
	 * @param role
	 * @param fame
	 * @param beforeFame
	 */
	void recordChangeFame(Role role, int fame, int beforeFame);

}
