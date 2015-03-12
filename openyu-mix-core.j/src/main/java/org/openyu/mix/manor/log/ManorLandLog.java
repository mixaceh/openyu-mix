package org.openyu.mix.manor.log;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.manor.service.ManorService.ActionType;
import org.openyu.mix.manor.vo.Land;

/**
 * 莊園土地log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface ManorLandLog extends AppLogEntity
{
	String KEY = ManorLandLog.class.getName();

	/**
	 * 操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 農場索引
	 * 
	 * @return
	 */
	Integer getFarmIndex();

	void setFarmIndex(Integer farmIndex);

	/**
	 * 土地
	 * 
	 * @return
	 */
	Land getLand();

	void setLand(Land land);

	/**
	 * 花費的金幣
	 * 
	 * @return
	 */
	Long getSpendGold();

	void setSpendGold(Long spendGold);

}
