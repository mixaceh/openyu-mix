package org.openyu.mix.role.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.role.vo.Role;

/**
 * 角色儲存庫, 存放所有本地/同步的角色在mem中
 * 
 * org/openyu/mix/app/applicationContext-app.xml
 */
public interface RoleRepository extends AppService {

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 加入角色
	 * 
	 * @param role
	 * @return
	 */
	Role addRole(Role role);

	/**
	 * 取得角色,包含同步
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRole(String roleId);

	/**
	 * 取得角色
	 * 
	 * @param roleId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	Role getRole(String roleId, boolean includeSync);

	/**
	 * 取得所有角色,包含同步
	 * 
	 * @return
	 */
	Map<String, Role> getRoles();

	/**
	 * 取得所有角色
	 * 
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	Map<String, Role> getRoles(boolean includeSync);

	/**
	 * 取得所有角色的id,包含同步
	 * 
	 * @return
	 */
	List<String> getRoleIds();

	/**
	 * 取得所有角色的id
	 * 
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	List<String> getRoleIds(boolean includeSync);

	/**
	 * 移除角色,只限本地
	 * 
	 * @param roleId
	 * @return
	 */
	Role removeRole(String roleId);

	/**
	 * 移除角色,只限本地
	 * 
	 * @param role
	 * @return
	 */
	Role removeRole(Role role);

	/**
	 * 角色是否存在,包含同步
	 * 
	 * @param roleId
	 * @return
	 */
	boolean containRole(String roleId);

	/**
	 * 角色是否存在
	 * 
	 * @param roleId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	boolean containRole(String roleId, boolean includeSync);

	/**
	 * 角色是否存在,包含同步
	 * 
	 * @param role
	 * @return
	 */
	boolean containRole(Role role);

	/**
	 * 角色是否存在
	 * 
	 * @param role
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	boolean containRole(Role role, boolean includeSync);

	// --------------------------------------------------
	// 同步用角色
	// --------------------------------------------------
	/**
	 * 加入同步角色
	 * 
	 * @param syncRole
	 * @return
	 */
	Role addSyncRole(Role syncRole);

	/**
	 * 取得同步角色
	 * 
	 * @param syncRoleId
	 * @return
	 */
	Role getSyncRole(String syncRoleId);

	/**
	 * 移除同步角色
	 *
	 * @param syncRoleId
	 * @return
	 */
	Role removeSyncRole(String syncRoleId);

	/**
	 * 移除同步角色
	 *
	 * @param syncRole
	 * @return
	 */
	Role removeSyncRole(Role syncRole);
}
