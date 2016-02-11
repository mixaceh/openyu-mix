package org.openyu.mix.role.log;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;

/**
 * 角色金幣改變log,不做bean,直接用entity處理掉
 */
public interface RoleGoldLog extends AppLogEntity
{
	String KEY = RoleGoldLog.class.getName();

	/**
	 * 金幣操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 金幣增減的原因
	 * 
	 * @return
	 */
	GoldType getGoldType();

	void setGoldType(GoldType goldType);

	/**
	 * 金幣
	 * 
	 * @return
	 */
	Long getGold();

	void setGold(Long gold);

	/**
	 * 改變前金幣
	 * 
	 * @return
	 */
	Long getBeforeGold();

	void setBeforeGold(Long beforeGold);

	/**
	 * 改變後金幣
	 * 
	 * @return
	 */
	Long getAfterGold();

	void setAfterGold(Long afterGold);

}
