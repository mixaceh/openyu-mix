package org.openyu.mix.account.po;

import org.openyu.commons.entity.SeqIdAuditNamesEntity;

/**
 * 帳號
 */
public interface AccountPo extends SeqIdAuditNamesEntity
{
	/**
	 * 是否有效
	 * 
	 * @return
	 */
	Boolean getValid();

	void setValid(Boolean valid);

	//可以設為雙向關聯,不過為了省mem,作單向關聯即可

	//	/**
	//	 * 角色
	//	 * 
	//	 * @return
	//	 */
	//	Set<RolePo> getRoles();
	//
	//	void setRoles(Set<RolePo> roles);

	/**
	 * 儲值總額
	 * 
	 * @return
	 */
	Integer getCoin();

	void setCoin(Integer coin);

	/**
	 * 累計儲值總額
	 * 
	 * @return
	 */
	Integer getAccuCoin();

	void setAccuCoin(Integer accuCoin);

	/**
	 * 密碼,md5加密
	 * 
	 * @return
	 */
	String getPassword();

	void setPassword(String password);
}
