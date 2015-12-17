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
import org.openyu.socklet.bootstrap.server.AcceptorBootstrap;
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

	/**
	 * 接收器服務
	 */
	private transient AcceptorService acceptorService;

	public AppServiceSupporter() {
	}

	/**
	 * 取得接收器id
	 * 
	 * @return
	 */
	protected String getAcceptorId() {
		return AcceptorBootstrap.getId();
	}

	/**
	 * 取得接收器實例id
	 * 
	 * @return
	 */
	protected String getAcceptorInstanceId() {
		return AcceptorBootstrap.getInstanceId();
	}

	/**
	 * 取得接收器輸出id
	 * 
	 * @return
	 */
	protected String getAcceptorOutputId() {
		return AcceptorBootstrap.getOutputId();
	}

	/**
	 * 取得server ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getServerIp(String sender) {
		return AcceptorBootstrap.getServerIp(sender);
	}

	/**
	 * 取得server port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getServerPort(String sender) {
		return AcceptorBootstrap.getServerPort(sender);
	}

	/**
	 * 取得client ip
	 * 
	 * @param sender
	 * @return
	 */
	protected String getClientIp(String sender) {
		return AcceptorBootstrap.getClientIp(sender);
	}

	/**
	 * 取得client port
	 * 
	 * @param sender
	 * @return
	 */
	protected int getClientPort(String sender) {
		return AcceptorBootstrap.getClientPort(sender);
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

}
