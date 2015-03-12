package org.openyu.mix.app.service.supporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.service.supporter.OjServiceSupporter;
import org.openyu.socklet.acceptor.service.AcceptorService;
import org.openyu.socklet.acceptor.vo.AcceptorStarter;
import org.openyu.socklet.connector.vo.AcceptorConnector;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;

public class AppServiceSupporter extends OjServiceSupporter implements
		AppService {

	private static final long serialVersionUID = -8839967232533601475L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AppServiceSupporter.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	private transient AcceptorStarter acceptorStarter;

	private transient AcceptorService acceptorService;

	private transient String acceptor;

	private transient String instanceId;

	private transient String outputId;

	public AppServiceSupporter() {
	}

	// --------------------------------------------------
	// RoleConnectable
	// --------------------------------------------------
	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		return null;
	}

	/**
	 * 發送角色連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleConnect(Role role, T attatch) {
		return null;
	}

	/**
	 * 角色斷線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleDisconnect(String roleId, T attatch) {
		return null;
	}

	/**
	 * 發送角色斷線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleDisconnect(Role role, T attatch) {
		return null;
	}

	/**
	 * 角色無法連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleRefused(String roleId, T attatch) {
		return null;
	}

	/**
	 * 發送角色無法連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleRefused(Role role, T attatch) {
		return null;
	}

	// --------------------------------------------------
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

	/**
	 * 當有acceptorStarter,就應有acceptorService
	 * 
	 * @return
	 */
	protected AcceptorService getAcceptorService() {
		if (acceptorService == null) {
			AcceptorStarter starter = getAcceptorStarter();
			if (starter != null) {
				acceptorService = starter.getAcceptorService();
			}
		}
		return acceptorService;
	}

	/**
	 * 取得acceptor id
	 * 
	 * @return
	 */
	protected String getAcceptor() {
		if (acceptor == null) {
			AcceptorService service = getAcceptorService();
			if (service != null) {
				acceptor = service.getId();
			}
		}
		return acceptor;
	}

	/**
	 * 取得實例id
	 * 
	 * @return
	 */
	protected String getInstanceId() {
		if (instanceId == null) {
			AcceptorService service = getAcceptorService();
			if (service != null) {
				instanceId = service.getInstanceId();
			}
		}
		return instanceId;
	}

	/**
	 * 取得輸出id
	 * 
	 * @return
	 */
	protected String getOutputId() {
		if (outputId == null) {
			AcceptorService service = getAcceptorService();
			if (service != null) {
				outputId = service.getOutputId();
			}
		}
		return outputId;
	}

	/**
	 * 取得client的server ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getServerIp(String sender) {
		String result = null;
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				result = connector.getServerIp();
			}
		}
		return result;
	}

	/**
	 * 取得client的server port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getServerPort(String sender) {
		int result = 0;
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				result = connector.getServerPort();
			}
		}
		return result;
	}

	/**
	 * 取得client ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getClientIp(String sender) {
		String result = null;
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				result = connector.getClientIp();
			}
		}
		return result;
	}

	/**
	 * 取得client port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getClientPort(String sender) {
		int result = 0;
		AcceptorService service = getAcceptorService();
		if (service != null) {
			AcceptorConnector connector = service.getAcceptorConnector(sender);
			if (connector != null) {
				result = connector.getClientPort();
			}
		}
		return result;
	}
}
