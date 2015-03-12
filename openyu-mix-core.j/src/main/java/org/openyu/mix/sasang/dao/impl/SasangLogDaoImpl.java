package org.openyu.mix.sasang.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.sasang.dao.SasangLogDao;
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.log.SasangPutLog;
import org.openyu.mix.sasang.log.impl.SasangFamousLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPlayLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPutLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SasangLogDaoImpl extends AppLogDaoSupporter implements
		SasangLogDao {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(SasangLogDaoImpl.class);

	private static final String SASANG_PLAY_LOG_PO_NAME = SasangPlayLogImpl.class
			.getName();

	private static final String SASANG_PUT_LOG_PO_NAME = SasangPutLogImpl.class
			.getName();

	private static final String SASANG_FAMOUS_LOG_PO_NAME = SasangFamousLogImpl.class
			.getName();

	public SasangLogDaoImpl() {
	}

	/**
	 * 查詢四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SasangPlayLog> findSasangPlayLog(String roleId) {

		return findSasangPlayLog(null, roleId);
	}

	/**
	 * 分頁查詢四象玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<SasangPlayLog> findSasangPlayLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(SASANG_PLAY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteSasangPlayLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(SASANG_PLAY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢四象放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SasangPutLog> findSasangPutLog(String roleId) {
		return findSasangPutLog(null, roleId);
	}

	/**
	 * 分頁查詢四象放入次數log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<SasangPutLog> findSasangPutLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(SASANG_PUT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除四象放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteSasangPutLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(SASANG_PUT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢四象放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SasangFamousLog> findSasangFamousLog(String roleId) {
		return findSasangFamousLog(null, roleId);
	}

	/**
	 * 分頁查詢四象放入次數log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<SasangFamousLog> findSasangFamousLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(SASANG_FAMOUS_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除四象放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteSasangFamousLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(SASANG_FAMOUS_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}
}
