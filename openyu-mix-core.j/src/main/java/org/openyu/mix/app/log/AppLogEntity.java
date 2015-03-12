package org.openyu.mix.app.log;

import org.openyu.commons.entity.SeqLogEntity;

/**
 * log不做bean,直接用entity處理掉
 */
public interface AppLogEntity extends SeqLogEntity
{
	String KEY = AppLogEntity.class.getName();

	/**
	 * 帳號id
	 * 
	 * @return
	 */
	String getAccountId();

	void setAccountId(String accountId);

	/**
	 * 角色id
	 * 
	 * @return
	 */
	String getRoleId();

	void setRoleId(String roleId);

	/**
	 * 角色名稱
	 * 
	 * @return
	 */
	String getRoleName();

	void setRoleName(String roleName);

	/**
	 * acceptor
	 * 
	 * @return
	 */
	String getAcceptor();

	void setAcceptor(String acceptor);

	/**
	 * 伺服器ip
	 * 
	 * @return
	 */
	String getServerIp();

	void setServerIp(String serverIp);

}
