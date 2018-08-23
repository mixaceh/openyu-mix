package org.openyu.mix.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.core.dao.CoreLogDao;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.mix.core.log.impl.CoreConnectLogImpl;
import org.openyu.mix.core.service.CoreLogService;
import org.openyu.mix.core.vo.Core.ConnectAction;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.anno.LogTx;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;
import org.openyu.commons.util.concurrent.NullValueMap;
import org.openyu.commons.util.concurrent.impl.NullValueMapImpl;

public class CoreLogServiceImpl extends AppLogServiceSupporter implements CoreLogService {

	private static final long serialVersionUID = -8168690228036491535L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(CoreLogServiceImpl.class);

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	/**
	 * mem,連線上放入CoreConnectLog,斷線時取出,改善效率
	 */
	private NullValueMap<String, CoreConnectLog> coreConnectLogs = new NullValueMapImpl<String, CoreConnectLog>();

	public CoreLogServiceImpl() {
	}

	public CoreLogDao getCoreLogDao() {
		return (CoreLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("coreLogDao")
	public void setCoreLogDao(CoreLogDao coreLogDao) {
		setCommonDao(coreLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The CoreLogDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// CoreConnectLog
	// --------------------------------------------------
	public List<CoreConnectLog> findCoreConnectLog(String roleId) {
		return getCoreLogDao().findCoreConnectLog(roleId);
	}

	public List<CoreConnectLog> findCoreConnectLog(Inquiry inquiry, String roleId) {
		return getCoreLogDao().findCoreConnectLog(inquiry, roleId);
	}

	public int deleteCoreConnectLog(String roleId) {
		return getCoreLogDao().deleteCoreConnectLog(roleId);
	}

	public CoreConnectLog findCoreConnectLog(String roleId, Long enterTime) {
		return getCoreLogDao().findCoreConnectLog(roleId, enterTime);
	}

	public CoreConnectLog findCoreConnectLogByLatest(String roleId) {
		return getCoreLogDao().findCoreConnectLogByLatest(roleId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	@LogTx
	public void recordRoleConnect(Role role) {
		if (role == null) {
			return;
		}
		//
		CoreConnectLog log = new CoreConnectLogImpl();
		recordRole(role, log);
		//
		log.setClientIp(getClientIp(role.getId()));
		log.setClientPort(getClientPort(role.getId()));
		log.setConnectAction(ConnectAction.CONNECT);
		log.setEnterTime(role.getEnterTime());
		//
		offerInsert(log);
		// 加到cache
		coreConnectLogs.put(role.getId(), log);
	}

	@LogTx
	public void recordRoleDisconnect(Role role) {
		if (role == null) {
			return;
		}
		String roleId = role.getId();
		// 1.查詢慢,這可能會卡,recordDisconnect 用多緒處理解決
		// CoreConnectLog log = findCoreConnectLog(role.getId(),
		// role.getEnterTime());

		// 2.連線時,把log,丟到cache,斷線時再取出,取代從db找,可以比db更快
		// 再加上CoreService.connect是多緒的,再卡就罵人了
		CoreConnectLog log = coreConnectLogs.get(roleId);
		boolean insert = false;
		if (log == null) {
			log = new CoreConnectLogImpl();
			recordRole(role, log);
			insert = true;
		}
		//
		log.setConnectAction(ConnectAction.DISCONNECT);
		if (role.getLeaveTime() != 0) {
			log.setLeaveTime(role.getLeaveTime());
		}

		// 計算在線毫秒
		long enterTime = safeGet(log.getEnterTime());
		long leaveTime = safeGet(log.getLeaveTime());
		long online = 0;
		if (enterTime != 0) {
			online = leaveTime - enterTime;
			if (online > 0) {
				log.setOnlineMills(online);
			}
		}
		// 新增log
		if (insert) {
			offerInsert(log);
		} else {
			offerUpdate(log);
		}

		// 從mem移除
		boolean contains = coreConnectLogs.contains(roleId);
		if (contains) {
			coreConnectLogs.remove(roleId);
		}
	}

}
