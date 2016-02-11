package org.openyu.mix.core.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.core.dao.CoreLogDao;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.mix.core.log.impl.CoreConnectLogImpl;
import org.openyu.mix.core.vo.Core.ConnectAction;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreLogDaoImpl extends AppLogDaoSupporter implements CoreLogDao {
	
	private static final long serialVersionUID = -9031106724111344122L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreLogDaoImpl.class);

	// private transient ConnectActionUserType connectActionUserType = new
	// ConnectActionUserType();

	/**
	 * 角色連線log
	 */
	private static final String CORE_CONNECT_LOG_PO_NAME = CoreConnectLogImpl.class
			.getName();

	public CoreLogDaoImpl() {
	}

	// --------------------------------------------------
	// CoreConnectLog
	// --------------------------------------------------
	/**
	 * 查詢角色連線log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<CoreConnectLog> findCoreConnectLog(String roleId) {

		return findCoreConnectLog(null, roleId);
	}

	/**
	 * 分頁查詢角色連線log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<CoreConnectLog> findCoreConnectLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(CORE_CONNECT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除角色連線log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteCoreConnectLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(CORE_CONNECT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	/**
	 * 查詢角色連線,上線時間log
	 * 
	 * @param roleId
	 * @param enterTime
	 * @return
	 */
	public CoreConnectLog findCoreConnectLog(String roleId, Long enterTime) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(CORE_CONNECT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		// enterTime
		hql.append("and enterTime = :enterTime ");
		params.put("enterTime", enterTime);
		//
		return findUniqueByHql(hql.toString(), params);
	}

	/**
	 * 
	 */
	public CoreConnectLog findCoreConnectLogByLatest(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(CORE_CONNECT_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);

		// connectAction
		// 若直接找欄位,則轉成字串
		// hql.append("and connect_action = :connectAction ");
		// String connectAction = connectActionUserType
		// .assemble(Core.ConnectAction.CONNECT);
		// params.put("connectAction", connectAction);

		// 若用物件找,則不轉
		hql.append("and connectAction = :connectAction ");
		params.put("connectAction", ConnectAction.CONNECT);
		//
		hql.append("order by enterTime desc ");
		//
		return findUniqueByHql(hql.toString(), params);
	}
}
