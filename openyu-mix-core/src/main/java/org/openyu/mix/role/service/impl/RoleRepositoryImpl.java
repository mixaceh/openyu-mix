package org.openyu.mix.role.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.role.service.RoleRepository;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.util.concurrent.NullValueMap;
import org.openyu.commons.util.concurrent.impl.NullValueMapImpl;

/**
 * 角色儲存庫, 存放所有本地/同步的角色在mem中
 */
public class RoleRepositoryImpl extends AppServiceSupporter implements RoleRepository {

	private static final long serialVersionUID = 7119102377797182772L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleRepositoryImpl.class);

	/**
	 * 同步role, 來自於其它server上的role
	 *
	 * <roleId,role>
	 */
	private NullValueMap<String, Role> syncRoles = new NullValueMapImpl<String, Role>();

	public RoleRepositoryImpl() {
	}

	@Override
	protected void checkConfig() throws Exception {

	}
	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 加入角色
	 *
	 * @param role
	 * @return
	 */
	public Role addRole(Role role) {
		Role result = null;
		if (role != null) {
			result = (Role) beans.put(role.getId(), role);
		}
		return result;
	}

	/**
	 * 取得角色,包含同步
	 *
	 * @param roleId
	 * @return
	 */
	public Role getRole(String roleId) {
		return getRole(roleId, true);
	}

	/**
	 * 取得角色
	 *
	 * @param roleId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public Role getRole(String roleId, boolean includeSync) {
		Role result = null;
		if (roleId != null) {
			result = (Role) beans.get(roleId);
			// 同步role
			if (result == null && includeSync) {
				result = syncRoles.get(roleId);
			}
		}
		return result;
	}

	/**
	 * 取得所有角色,包含同步
	 *
	 * @return
	 */
	public Map<String, Role> getRoles() {
		return getRoles(true);
	}

	/**
	 * 取得所有角色
	 *
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public Map<String, Role> getRoles(boolean includeSync) {
		Map<String, Role> result = new LinkedHashMap<String, Role>();
		for (Object entry : beans.getValues()) {
			if (entry instanceof Role) {
				Role value = (Role) entry;
				result.put(value.getId(), value);
			}
		}

		// 同步role
		if (includeSync) {
			for (Role role : syncRoles.getValues()) {
				result.put(role.getId(), role);
			}
		}
		return result;
	}

	/**
	 * 取得所有角色的id,包含同步
	 *
	 * @return
	 */
	public List<String> getRoleIds() {
		return getRoleIds(true);
	}

	/**
	 * 取得所有角色的id
	 *
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public List<String> getRoleIds(boolean includeSync) {
		List<String> result = new LinkedList<String>();
		for (String roleId : beans.getKeys()) {
			result.add(roleId);
		}
		// 同步role
		if (includeSync) {
			for (String roleId : syncRoles.getKeys()) {
				result.add(roleId);
			}
		}
		return result;
	}

	/**
	 * 移除角色,只限本地
	 *
	 * @param roleId
	 * @return
	 */
	public Role removeRole(String roleId) {
		Role result = null;
		if (roleId != null) {
			result = (Role) beans.remove(roleId);
		}
		return result;
	}

	/**
	 * 移除角色,只限本地
	 *
	 * @param role
	 * @return
	 */
	public Role removeRole(Role role) {
		Role result = null;
		if (role != null) {
			result = removeRole(role.getId());
		}
		return result;
	}

	/**
	 * 角色是否存在,包含同步
	 *
	 * @param roleId
	 * @return
	 */
	public boolean containRole(String roleId) {
		return containRole(roleId, true);
	}

	/**
	 * 角色是否存在
	 *
	 * @param roleId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public boolean containRole(String roleId, boolean includeSync) {
		boolean result = false;
		if (roleId != null) {
			result = beans.contains(roleId);
			// 同步role
			if (!result && includeSync) {
				result = syncRoles.contains(roleId);
			}
		}
		return result;
	}

	/**
	 * 角色是否存在,包含同步
	 *
	 * @param role
	 * @return
	 */
	public boolean containRole(Role role) {
		return containRole(role, true);
	}

	/**
	 * 角色是否存在
	 *
	 * @param role
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public boolean containRole(Role role, boolean includeSync) {
		boolean result = false;
		if (role != null) {
			result = containRole(role.getId(), includeSync);
		}
		return result;
	}

	// --------------------------------------------------
	// 同步用角色
	// --------------------------------------------------
	/**
	 * 加入同步角色
	 * 
	 * @param syncRole
	 * @return
	 */

	public Role addSyncRole(Role syncRole) {
		Role result = null;
		if (syncRole != null) {
			result = (Role) syncRoles.put(syncRole.getId(), syncRole);
		}
		return result;
	}

	/**
	 * 取得同步角色
	 * 
	 * @param syncRoleId
	 * @return
	 */
	public Role getSyncRole(String syncRoleId) {
		Role result = null;
		if (syncRoleId != null) {
			result = syncRoles.get(syncRoleId);
		}
		return result;
	}

	/**
	 * 移除同步角色
	 *
	 * @param syncRoleId
	 * @return
	 */
	public Role removeSyncRole(String syncRoleId) {
		Role result = null;
		if (syncRoleId != null) {
			result = (Role) syncRoles.remove(syncRoleId);
		}
		return result;
	}

	/**
	 * 移除同步角色
	 *
	 * @param syncRole
	 * @return
	 */
	public Role removeSyncRole(Role syncRole) {
		Role result = null;
		if (syncRole != null) {
			result = removeSyncRole(syncRole.getId());
		}
		return result;
	}
}
