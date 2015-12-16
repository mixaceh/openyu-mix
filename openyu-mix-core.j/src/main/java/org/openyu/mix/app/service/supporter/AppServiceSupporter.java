package org.openyu.mix.app.service.supporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.role.vo.Role;

import java.util.Map;

import org.openyu.commons.service.supporter.CommonServiceSupporter;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.anno.DefaultThreadService;
import org.openyu.socklet.acceptor.service.AcceptorService;
import org.openyu.socklet.connector.vo.AcceptorConnector;
import org.openyu.socklet.message.anno.DefaultMessageService;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;

public class AppServiceSupporter extends CommonServiceSupporter implements AppService {

	private static final long serialVersionUID = -8839967232533601475L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AppServiceSupporter.class);

	/**
	 * 線程服務
	 */
	@DefaultThreadService
	protected transient ThreadService threadService;

	/**
	 * 訊息服務
	 */
	@DefaultMessageService
	protected transient MessageService messageService;

	private transient AcceptorService acceptorService;

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

	protected synchronized AcceptorService getAcceptorService() {
		if (acceptorService == null) {
			Map<String, AcceptorService> acceptorServices = applicationContext.getBeansOfType(AcceptorService.class);
			for (AcceptorService entry : acceptorServices.values()) {
				boolean started = entry.isStarted();
				if (started) {
					acceptorService = entry;
				}
				break;
			}
		}
		return acceptorService;
	}

	/**
	 * 取得接收器 id
	 * 
	 * @return
	 */
	protected String getAcceptorId() {
		return getAcceptorService().getId();
	}

	/**
	 * 取得接收器 實例id
	 * @return
	 */
	protected String getAcceptorInstanceId() {
		return getAcceptorService().getInstanceId();
	}
	/**
	 * 取得接收器 輸出id
	 * @return
	 */
	protected String getAcceptorOutputId() {
		return getAcceptorService().getOutputId();
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
