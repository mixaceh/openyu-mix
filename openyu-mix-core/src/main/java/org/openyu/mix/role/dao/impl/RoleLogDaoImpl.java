package org.openyu.mix.role.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.role.dao.RoleLogDao;
import org.openyu.mix.role.log.RoleFameLog;
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.log.RoleLevelLog;
import org.openyu.mix.role.log.impl.RoleFameLogImpl;
import org.openyu.mix.role.log.impl.RoleGoldLogImpl;
import org.openyu.mix.role.log.impl.RoleLevelLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleLogDaoImpl extends AppLogDaoSupporter implements RoleLogDao {

	private static final long serialVersionUID = -1623793467239947375L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleLogDaoImpl.class);
	/**
	 * 等級改變log
	 */
	private static final String ROLE_LEVEL_LOG_PO_NAME = RoleLevelLogImpl.class.getName();

	/**
	 * 金幣改變log
	 */
	private static final String ROLE_GOLD_LOG_PO_NAME = RoleGoldLogImpl.class.getName();

	/**
	 * 聲望改變log
	 */
	private static final String ROLE_FAME_LOG_PO_NAME = RoleFameLogImpl.class.getName();

	public RoleLogDaoImpl() {
	}

	// --------------------------------------------------
	// RoleLevelLog
	// --------------------------------------------------
	/**
	 * 查詢等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleLevelLog> findRoleLevelLog(String roleId) {

		return findRoleLevelLog(null, roleId);
	}

	/**
	 * 分頁查詢等級改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<RoleLevelLog> findRoleLevelLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ROLE_LEVEL_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除等級改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleLevelLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ROLE_LEVEL_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	// --------------------------------------------------
	// RoleGoldLog
	// --------------------------------------------------
	/**
	 * 查詢金幣改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleGoldLog> findRoleGoldLog(String roleId) {

		return findRoleGoldLog(null, roleId);
	}

	/**
	 * 分頁查詢金幣改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<RoleGoldLog> findRoleGoldLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ROLE_GOLD_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除金幣改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleGoldLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ROLE_GOLD_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	// --------------------------------------------------
	// RoleFameLog
	// --------------------------------------------------
	/**
	 * 查詢聲望改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleFameLog> findRoleFameLog(String roleId) {

		return findRoleFameLog(null, roleId);
	}

	/**
	 * 分頁查詢聲望改變log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<RoleFameLog> findRoleFameLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ROLE_FAME_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除聲望改變log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleFameLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ROLE_FAME_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

}
