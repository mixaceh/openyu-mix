package org.openyu.mix.app.service;

import org.openyu.mix.role.vo.Role;

/**
 * 連線服務
 */
public interface ConnectService
{
	/**
	 * 連線
	 * 
	 * @param roleId
	 * @param attatch
	 */
	<T> Role connect(String roleId, T attatch);

	/**
	 * 發送連線
	 * 
	 * @param role
	 * @param attatch
	 */
	<T> void sendConnect(Role role, T attatch);

	/**
	 * 斷線
	 * 
	 * @param roleId
	 * @param attatch
	 */
	<T> Role disconnect(String roleId, T attatch);

	/**
	 * 發送斷線
	 * 
	 * @param role
	 * @param attatch
	 */
	<T> void sendDisconnect(Role role, T attatch);
}
