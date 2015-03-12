package org.openyu.mix.treasure.log;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;

/**
 * 祕寶購買log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface TreasureBuyLog extends AppLogEntity
{
	String KEY = TreasureBuyLog.class.getName();

	/**
	 * 購買類別
	 * 
	 * @return
	 */
	BuyType getBuyType();

	void setBuyType(BuyType buyType);

	/**
	 * 上架祕寶索引
	 * 
	 * @return
	 */
	Integer getGridIndex();

	void setGridIndex(Integer Integer);

	/**
	 * 上架祕寶
	 * 
	 * @return
	 */
	Treasure getTreasure();

	void setTreasure(Treasure treasure);

	/**
	 * 購買的道具
	 * 
	 * @return
	 */
	Item getItem();

	void setItem(Item item);

	/**
	 * 數量
	 * 
	 * @return
	 */
	Integer getAmount();

	void setAmount(Integer amount);

	/**
	 * 金幣價格
	 * 
	 * @return
	 */
	Long getPrice();

	void setPrice(Long price);

	/**
	 * 儲值幣價格
	 * 
	 * @return
	 */
	Integer getCoin();

	void setCoin(Integer coin);

	/**
	 * 消耗的金幣
	 * 
	 * @return
	 */
	Long getSpendGold();

	void setSpendGold(Long spendGold);

	/**
	 * 消耗的儲值幣
	 * 
	 * @return
	 */
	Integer getSpendCoin();

	void setSpendCoin(Integer spendCoin);

}
