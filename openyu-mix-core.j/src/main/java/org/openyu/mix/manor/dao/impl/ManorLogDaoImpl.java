package org.openyu.mix.manor.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.manor.dao.ManorLogDao;
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.mix.manor.log.impl.ManorLandLogImpl;
import org.openyu.mix.manor.log.impl.ManorSeedLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManorLogDaoImpl extends AppLogDaoSupporter implements ManorLogDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorLogDaoImpl.class);

	private static final String MANOR_LAND_LOG_PO_NAME = ManorLandLogImpl.class
			.getName();

	private static final String MANOR_SEED_LOG_PO_NAME = ManorSeedLogImpl.class
			.getName();

	public ManorLogDaoImpl() {
	}

	/**
	 * 查詢莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ManorLandLog> findManorLandLog(String roleId) {

		return findManorLandLog(null, roleId);
	}

	/**
	 * 分頁查詢莊園土地log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<ManorLandLog> findManorLandLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(MANOR_LAND_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteManorLandLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(MANOR_LAND_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ManorSeedLog> findManorSeedLog(String roleId) {

		return findManorSeedLog(null, roleId);
	}

	/**
	 * 分頁查詢莊園種子log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<ManorSeedLog> findManorSeedLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(MANOR_SEED_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteManorSeedLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(MANOR_SEED_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}
}
