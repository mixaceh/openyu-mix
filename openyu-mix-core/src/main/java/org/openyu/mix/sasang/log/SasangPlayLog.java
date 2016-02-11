package org.openyu.mix.sasang.log;

import java.util.List;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.vo.Outcome;

/**
 * 四象玩的log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface SasangPlayLog extends AppLogEntity
{
	String KEY = SasangPlayLog.class.getName();

	/**
	 * 玩的類別
	 * 
	 * @return
	 */
	PlayType getPlayType();

	void setPlayType(PlayType playType);

	/**
	 * 玩的時間
	 * 
	 * @return
	 */
	Long getPlayTime();

	void setPlayTime(Long playTime);

	/**
	 * 玩的結果
	 * 
	 * @return
	 */
	Outcome getOutcome();

	void setOutcome(Outcome outcome);

	/**
	 * 真正成功扣道具及儲值幣的次數
	 * 
	 * @return
	 */
	Integer getRealTimes();

	void setRealTimes(Integer realTimes);

	/**
	 * 消耗的金幣
	 * 
	 * @return
	 */
	Long getSpendGold();

	void setSpendGold(Long spendGold);

	/**
	 * 消耗的道具
	 * 
	 * @return
	 */
	List<Item> getSpendItems();

	void setSpendItems(List<Item> spendItems);

	/**
	 * 消耗的儲值幣
	 * 
	 * @return
	 */
	Integer getSpendCoin();

	void setSpendCoin(Integer spendCoin);
}
