package org.openyu.mix.role.log;

import org.openyu.mix.app.log.AppLogEntity;

/**
 * 角色聲望改變log,不做bean,直接用entity處理掉
 */
public interface RoleFameLog extends AppLogEntity
{
	String KEY = RoleFameLog.class.getName();

	/**
	 * 聲望
	 * 
	 * @return
	 */
	Integer getFame();

	void setFame(Integer fame);

	/**
	 * 改變前聲望
	 * 
	 * @return
	 */
	Integer getBeforeFame();

	void setBeforeFame(Integer beforeFame);

	/**
	 * 改變後聲望
	 * 
	 * @return
	 */
	Integer getAfterFame();

	void setAfterFame(Integer afterFame);

}
