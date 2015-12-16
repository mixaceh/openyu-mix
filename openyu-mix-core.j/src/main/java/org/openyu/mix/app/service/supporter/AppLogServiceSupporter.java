package org.openyu.mix.app.service.supporter;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.service.supporter.BaseLogServiceSupporter;
import org.openyu.socklet.acceptor.service.AcceptorService;
import org.openyu.socklet.acceptor.vo.AcceptorStarter;
import org.openyu.socklet.connector.vo.AcceptorConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日誌服務
 */
public class AppLogServiceSupporter extends BaseLogServiceSupporter
		implements AppLogService {
	
	private static final long serialVersionUID = 5897073418058666600L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AppLogServiceSupporter.class);

	protected transient AcceptorStarter acceptorStarter;

	protected transient AcceptorService acceptorService;

	public AppLogServiceSupporter() {
	}

	/**
	 * 當單元測試時,是沒有真正啟動acceptor,所以沒有acceptorStarter
	 * 
	 * @return
	 */
	protected AcceptorStarter getAcceptorStarter() {
		if (acceptorStarter == null) {
			try {
				acceptorStarter = (AcceptorStarter) applicationContext
						.getBean("acceptorStarter");
			} catch (Exception ex) {
			}
		}
		return acceptorStarter;
	}

	protected AcceptorService getAcceptorService() {
		AcceptorStarter starter = getAcceptorStarter();
		if (starter != null) {
			return starter.getAcceptorService();
		}
		return null;
	}

	/**
	 * 取得acceptor id
	 * 
	 * @return
	 */
	protected String getAcceptor() {
		AcceptorService service = getAcceptorService();
		if (service != null) {
			return service.getId();
		}
		return null;
	}

	/**
	 * 取得server ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getServerIp(String sender) {
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				return connector.getServerIp();
			}
		}
		return null;
	}

	/**
	 * 取得server port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getServerPort(String sender) {
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				return connector.getServerPort();
			}
		}
		return 0;
	}

	/**
	 * 取得client ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getClientIp(String sender) {
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				return connector.getClientIp();
			}
		}
		return null;
	}

	/**
	 * 取得client port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getClientPort(String sender) {
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				return connector.getClientPort();
			}
		}
		return 0;
	}

	// --------------------------------------------------

	/**
	 * 紀錄角色相關資訊
	 * 
	 * @param role
	 * @param entity
	 */
	protected void recordRole(Role role, AppLogEntity entity) {
		if (role != null) {
			entity.setAccountId(role.getAccountId());
			entity.setRoleId(role.getId());
			entity.setRoleName(role.getName());
			entity.setAcceptorId(role.getAcceptorId());
			entity.setServerIp(getServerIp(role.getId()));
		}
	}

}
