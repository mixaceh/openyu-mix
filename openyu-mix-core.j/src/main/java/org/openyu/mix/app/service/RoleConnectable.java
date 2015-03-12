package org.openyu.mix.app.service;

import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

/**
 * 角色連線
 */
public interface RoleConnectable {

	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	<T> Role roleConnect(String roleId, T attatch);

	/**
	 * 發送角色連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	<T> Message sendRoleConnect(Role role, T attatch);

	/**
	 * 角色斷線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	<T> Role roleDisconnect(String roleId, T attatch);

	/**
	 * 發送角色斷線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	<T> Message sendRoleDisconnect(Role role, T attatch);

	/**
	 * 角色無法連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	<T> Role roleRefused(String roleId, T attatch);

	/**
	 * 發送角色無法連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	<T> Message sendRoleRefused(Role role, T attatch);
}
