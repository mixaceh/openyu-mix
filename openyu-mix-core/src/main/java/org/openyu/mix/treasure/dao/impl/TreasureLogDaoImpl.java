package org.openyu.mix.treasure.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.treasure.dao.TreasureLogDao;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.mix.treasure.log.impl.TreasureBuyLogImpl;
import org.openyu.mix.treasure.log.impl.TreasureRefreshLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreasureLogDaoImpl extends AppLogDaoSupporter implements
		TreasureLogDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TreasureLogDaoImpl.class);

	private static final String TREASURE_REFRESH_LOG_PO_NAME = TreasureRefreshLogImpl.class
			.getName();

	private static final String TREASURE_BUY_LOG_PO_NAME = TreasureBuyLogImpl.class
			.getName();

	public TreasureLogDaoImpl() {
	}

	/**
	 * 查詢祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<TreasureRefreshLog> findTreasureRefreshLog(String roleId) {

		return findTreasureRefreshLog(null, roleId);
	}

	/**
	 * 分頁查詢祕寶刷新log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<TreasureRefreshLog> findTreasureRefreshLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(TREASURE_REFRESH_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteTreasureRefreshLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(TREASURE_REFRESH_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<TreasureBuyLog> findTreasureBuyLog(String roleId) {

		return findTreasureBuyLog(null, roleId);
	}

	/**
	 * 分頁查詢祕寶購買log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<TreasureBuyLog> findTreasureBuyLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(TREASURE_BUY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteTreasureBuyLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(TREASURE_BUY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}
}
