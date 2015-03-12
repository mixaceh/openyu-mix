package org.openyu.mix.role.log;

import org.openyu.mix.app.log.AppLogEntity;

/**
 * 角色等級改變log,不做bean,直接用entity處理掉
 */
public interface RoleLevelLog extends AppLogEntity
{
	String KEY = RoleLevelLog.class.getName();

	/**
	 * 等級
	 * 
	 * @return
	 */
	Integer getLevel();

	void setLevel(Integer level);

	/**
	 * 改變前等級
	 * 
	 * @return
	 */
	Integer getBeforeLevel();

	void setBeforeLevel(Integer beforeLevel);

	/**
	 * 改變後等級
	 * 
	 * @return
	 */
	Integer getAfterLevel();

	void setAfterLevel(Integer afterLevel);

}
