package org.openyu.mix.manor.log;

import java.util.List;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Seed;

/**
 * 莊園種子log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface ManorSeedLog extends AppLogEntity
{
	String KEY = ManorSeedLog.class.getName();

	/**
	 * 耕種類別
	 * 
	 * @return
	 */
	CultureType getCultureType();

	void setCultureType(CultureType cultureType);

	/**
	 * 農場索引
	 * 
	 * @return
	 */
	Integer getFarmIndex();

	void setFarmIndex(Integer farmIndex);

	/**
	 * 格子索引
	 * 
	 * @return
	 */
	Integer getGridIndex();

	void setGridIndex(Integer gridIndex);

	/**
	 * 耕種的種子
	 * 
	 * @return
	 */
	Seed getSeed();

	void setSeed(Seed seed);

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
