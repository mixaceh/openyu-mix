package org.openyu.mix.wuxing.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.wuxing.dao.WuxingLogDao;
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.log.WuxingPlayLog;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.mix.wuxing.log.impl.WuxingFamousLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPlayLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPutLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WuxingLogDaoImpl extends AppLogDaoSupporter implements
		WuxingLogDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingLogDaoImpl.class);
	
	private static final String WUXING_PLAY_LOG_PO_NAME = WuxingPlayLogImpl.class
			.getName();

	private static final String WUXING_PUT_LOG_PO_NAME = WuxingPutLogImpl.class
			.getName();

	private static final String WUXING_FAMOUS_LOG_PO_NAME = WuxingFamousLogImpl.class
			.getName();

	public WuxingLogDaoImpl() {
	}

	/**
	 * 查詢五行玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<WuxingPlayLog> findWuxingPlayLog(String roleId) {

		return findWuxingPlayLog(null, roleId);
	}

	/**
	 * 分頁查詢五行玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<WuxingPlayLog> findWuxingPlayLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(WUXING_PLAY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除五行玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteWuxingPlayLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(WUXING_PLAY_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢五行放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<WuxingPutLog> findWuxingPutLog(String roleId) {
		return findWuxingPutLog(null, roleId);
	}

	/**
	 * 分頁查詢五行放入次數log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<WuxingPutLog> findWuxingPutLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(WUXING_PUT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除五行放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteWuxingPutLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(WUXING_PUT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢五行放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<WuxingFamousLog> findWuxingFamousLog(String roleId) {
		return findWuxingFamousLog(null, roleId);
	}

	/**
	 * 分頁查詢五行放入次數log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<WuxingFamousLog> findWuxingFamousLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(WUXING_FAMOUS_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除五行放入次數log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteWuxingFamousLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(WUXING_FAMOUS_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}
}
