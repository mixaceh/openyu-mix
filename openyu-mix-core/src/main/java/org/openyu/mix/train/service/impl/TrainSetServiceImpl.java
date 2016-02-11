package org.openyu.mix.train.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.service.TrainSetService;

/**
 * 訓練角色集合服務, 存放所有本地/同步的訓練角色在mem中
 */
public class TrainSetServiceImpl extends AppServiceSupporter implements TrainSetService {

	private static final long serialVersionUID = 281568249375735970L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TrainSetServiceImpl.class);

	public TrainSetServiceImpl() {
	}

	@Override
	protected void checkConfig() throws Exception {

	}
	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 加入訓練角色
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
	 * 取得訓練角色
	 *
	 * @param roleId
	 * @return
	 */
	public Role getRole(String roleId) {
		Role result = null;
		if (roleId != null) {
			result = (Role) beans.get(roleId);
		}
		return result;
	}

	/**
	 * 取得所有訓練角色
	 *
	 * @return
	 */
	public Map<String, Role> getRoles() {
		Map<String, Role> result = new LinkedHashMap<String, Role>();
		for (Object entry : beans.getValues()) {
			if (entry instanceof Role) {
				Role value = (Role) entry;
				result.put(value.getId(), value);
			}
		}
		return result;
	}

	/**
	 * 取得所有訓練角色的id
	 *
	 * @return
	 */
	public List<String> getRoleIds() {
		List<String> result = new LinkedList<String>();
		for (String roleId : beans.getKeys()) {
			result.add(roleId);
		}
		return result;
	}

	/**
	 * 移除訓練角色
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
	 * 移除訓練角色
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
	 * 訓練角色是否存在
	 *
	 * @param roleId
	 * @return
	 */
	public boolean containRole(String roleId) {
		boolean result = false;
		if (roleId != null) {
			result = beans.contains(roleId);
		}
		return result;
	}

	/**
	 * 訓練角色是否存在
	 *
	 * @param role
	 * 
	 * @return
	 */
	public boolean containRole(Role role) {
		boolean result = false;
		if (role != null) {
			result = containRole(role.getId());
		}
		return result;
	}

}
