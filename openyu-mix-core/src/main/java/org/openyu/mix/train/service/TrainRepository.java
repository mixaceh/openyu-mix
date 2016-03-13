package org.openyu.mix.train.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.role.vo.Role;

/**
 * 訓練角色集合服務, 存放所有本地的訓練角色在mem中
 * 
 *  org/openyu/mix/train/applicationContext-app.xml
 */
public interface TrainRepository extends AppService {

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 加入訓練角色
	 * 
	 * @param role
	 * @return
	 */
	Role addRole(Role role);

	/**
	 * 取得訓練角色
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRole(String roleId);

	/**
	 * 取得所有訓練角色
	 * 
	 * @return
	 */
	Map<String, Role> getRoles();

	/**
	 * 取得所有訓練角色的id
	 * 
	 * @return
	 */
	List<String> getRoleIds();

	/**
	 * 移除訓練角色
	 * 
	 * @param roleId
	 * @return
	 */
	Role removeRole(String roleId);

	/**
	 * 移除訓練角色
	 * 
	 * @param role
	 * @return
	 */
	Role removeRole(Role role);

	/**
	 * 訓練角色是否存在
	 * 
	 * @param roleId
	 * @return
	 */
	boolean containRole(String roleId);

	/**
	 * 訓練角色是否存在
	 * 
	 * @param role
	 * @return
	 */
	boolean containRole(Role role);

}
