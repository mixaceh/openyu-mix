package org.openyu.mix.role.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.role.dao.RoleLogDao;
import org.openyu.mix.role.log.RoleFameLog;
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.log.RoleLevelLog;
import org.openyu.mix.role.log.impl.RoleFameLogImpl;
import org.openyu.mix.role.log.impl.RoleGoldLogImpl;
import org.openyu.mix.role.log.impl.RoleLevelLogImpl;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.anno.LogTx;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

public class RoleLogServiceImpl extends AppLogServiceSupporter implements RoleLogService {

	private static final long serialVersionUID = 6038962261669226696L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleLogServiceImpl.class);

	public RoleLogServiceImpl() {
	}

	public RoleLogDao getRoleLogDao() {
		return (RoleLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("roleLogDao")
	public void setRoleLogDao(RoleLogDao roleLogDao) {
		setCommonDao(roleLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The RoleLogDao is required");
	}
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// RoleLevelLog
	// --------------------------------------------------
	public List<RoleLevelLog> findRoleLevelLog(String roleId) {
		return getRoleLogDao().findRoleLevelLog(roleId);
	}

	public List<RoleLevelLog> findRoleLevelLog(Inquiry inquiry, String roleId) {
		return getRoleLogDao().findRoleLevelLog(inquiry, roleId);
	}

	public int deleteRoleLevelLog(String roleId) {
		return getRoleLogDao().deleteRoleLevelLog(roleId);
	}

	// --------------------------------------------------
	// RoleGoldLog
	// --------------------------------------------------
	public List<RoleGoldLog> findRoleGoldLog(String roleId) {
		return getRoleLogDao().findRoleGoldLog(roleId);
	}

	public List<RoleGoldLog> findRoleGoldLog(Inquiry inquiry, String roleId) {
		return getRoleLogDao().findRoleGoldLog(inquiry, roleId);
	}

	public int deleteRoleGoldLog(String roleId) {
		return getRoleLogDao().deleteRoleGoldLog(roleId);
	}

	// --------------------------------------------------
	// RoleFameLog
	// --------------------------------------------------
	public List<RoleFameLog> findRoleFameLog(String roleId) {
		return getRoleLogDao().findRoleFameLog(roleId);
	}

	public List<RoleFameLog> findRoleFameLog(Inquiry inquiry, String roleId) {
		return getRoleLogDao().findRoleFameLog(inquiry, roleId);
	}

	public int deleteRoleFameLog(String roleId) {
		return getRoleLogDao().deleteRoleFameLog(roleId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 紀錄增減等級
	 * 
	 * @param role
	 * @param level
	 * @param beforeLevel
	 */
	@LogTx
	public void recordChangeLevel(Role role, int level, int beforeLevel) {
		RoleLevelLog log = new RoleLevelLogImpl();
		recordRole(role, log);
		//
		log.setLevel(level);
		log.setBeforeLevel(beforeLevel);
		log.setAfterLevel(role.getLevel());
		//
		offerInsert(log);
	}

	/**
	 * 紀錄增加金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 */
	@LogTx
	public void recordIncreaseGold(Role role, long gold, long beforeGold, GoldType goldReason) {
		recordChangeGold(role, gold, beforeGold, ActionType.INCREASE, goldReason);

	}

	/**
	 * 紀錄減少金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 */
	@LogTx
	public void recordDecreaseGold(Role role, long gold, long beforeGold, GoldType goldReason) {
		recordChangeGold(role, -1 * gold, beforeGold, ActionType.DECREASE, goldReason);
	}

	/**
	 * 紀錄增減金幣
	 * 
	 * @param role
	 * @param gold
	 * @param beforeGold
	 * @param goldAction
	 */
	@LogTx
	public void recordChangeGold(Role role, long gold, long beforeGold, ActionType goldAction, GoldType goldReason) {
		RoleGoldLog log = new RoleGoldLogImpl();
		recordRole(role, log);
		//
		log.setActionType(goldAction);
		log.setGoldType(goldReason);
		//
		switch (goldAction) {
		case INCREASE: {
			if (gold < 0) {
				gold = Math.abs(gold);
			}
			break;
		}
		case DECREASE: {
			if (gold > 0) {
				gold = -1 * gold;
			}
			break;
		}
		default: {
			break;
		}
		}
		log.setGold(gold);
		//
		log.setBeforeGold(beforeGold);
		log.setAfterGold(role.getGold());
		//
		offerInsert(log);
	}

	/**
	 * 紀錄增減聲望
	 * 
	 * @param role
	 * @param fame
	 * @param beforeFame
	 */
	@LogTx
	public void recordChangeFame(Role role, int fame, int beforeFame) {
		RoleFameLog log = new RoleFameLogImpl();
		recordRole(role, log);
		//
		log.setFame(fame);
		log.setBeforeFame(beforeFame);
		log.setAfterFame(role.getFame());
		//
		offerInsert(log);
	}
}
