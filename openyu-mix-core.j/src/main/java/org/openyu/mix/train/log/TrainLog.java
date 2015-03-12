package org.openyu.mix.train.log;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.train.service.TrainService.ActionType;

/**
 * 訓練log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface TrainLog extends AppLogEntity
{
	String KEY = TrainLog.class.getName();

	/**
	 * 操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 鼓舞時間
	 * 
	 * @return
	 */
	Long getInspireTime();

	void setInspireTime(Long inspireTime);

	/**
	 * 消耗的道具
	 * 
	 * @return
	 */
	List<Item> getSpendItems();

	void setSpendItems(List<Item> spendItem);

	/**
	 * 消耗的儲值幣
	 * 
	 * @return
	 */
	Integer getSpendCoin();

	void setSpendCoin(Integer spendCoin);
}
