package org.openyu.mix.app.service.supporter;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.role.vo.Role;

import org.openyu.commons.service.supporter.LogServiceSupporter;
import org.openyu.socklet.bootstrap.server.AcceptorBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日誌服務
 */
public abstract class AppLogServiceSupporter extends LogServiceSupporter implements AppLogService {

	private static final long serialVersionUID = 5897073418058666600L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AppLogServiceSupporter.class);

	public AppLogServiceSupporter() {
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
			//
			entity.setServerIp(getServerIp(role.getId()));
			entity.setServerPort(getServerPort(role.getId()));
		}
	}

}
