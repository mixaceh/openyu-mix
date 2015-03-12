package org.openyu.mix.role.service;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.role.service.RoleService.ErrorType;
import org.openyu.mix.core.vo.StoreType;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

/**
 * 儲存角色服務
 * 
 * 1.儲存到db
 * 
 * 2.若失敗,則序列化到檔案, 目錄, file:custom/role/unsave
 */
public interface StoreRoleService extends AppService {

	/**
	 * 儲存所有角色
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @return
	 */
	int storeRoles(boolean sendable);

	/**
	 * 儲存角色
	 * 
	 * 1.儲存到db, 失敗會重試
	 * 
	 * 2.若重試也失敗, 則序列化到檔案, 目錄, file:custom/role/unsave
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 * @return
	 */
	boolean storeRole(boolean sendable, Role role);

	/**
	 * 發送儲存角色
	 *
	 * @param errorType
	 * @param role
	 *            角色
	 * 
	 * @param storeType
	 * @return
	 */
	Message sendStoreRole(ErrorType errorType, Role role, StoreType storeType);

	/**
	 * 發送儲存角色
	 *
	 * @param errorType
	 * @param role
	 *            角色
	 * 
	 * @param storeType
	 * @param tries
	 *            重試次數
	 * @return
	 */
	Message sendStoreRole(ErrorType errorType, Role role, StoreType storeType,
			int tries);

	/**
	 * 序列化角色
	 */
	interface SerializeRole extends AppResult {

		/**
		 * 是否發送訊息
		 * 
		 * @return
		 */
		boolean isSendable();

		void setSendable(boolean sendable);

		/**
		 * 角色
		 * 
		 * @return
		 */
		Role getRole();

		void setRole(Role role);

	}

	/**
	 * 序列化角色
	 * 
	 * 序列化到檔案, 目錄, file:custom/role/unsave
	 * 
	 * custom/role/unsave/TEST_ROLE1074406tRBJjZLZt.ser
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 * @return 輸出檔名
	 */
	String serializeRole(boolean sendable, Role role);

	/**
	 * 反序列化角色, 目錄, file:custom/role/unsave
	 * 
	 * custom/role/unsave/TEST_ROLE1074406tRBJjZLZt.ser
	 * 
	 * @param roleId
	 *            TEST_ROLE1074406tRBJjZLZt
	 * @return
	 */
	Role deserializeRole(String roleId);

	/**
	 * ser檔名
	 * 
	 * @param roleId
	 * @return
	 */
	String serName(String roleId);

}
