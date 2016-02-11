package org.openyu.mix.treasure.log;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.vo.Treasure;

/**
 * 祕寶刷新log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface TreasureRefreshLog extends AppLogEntity
{
	String KEY = TreasureRefreshLog.class.getName();

	/**
	 * 刷新時間
	 * 
	 * @return
	 */
	Long getRefreshTime();

	void setRefreshTime(Long refreshTime);

	/**
	 * 上架祕寶
	 * 
	 * @return
	 */
	Map<Integer, Treasure> getTreasures();

	void setTreasures(Map<Integer, Treasure> treasures);

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
