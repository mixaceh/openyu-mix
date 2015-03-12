package org.openyu.mix.item.vo.thing;

import org.openyu.mix.item.vo.Thing;

/**
 * 藥水道具
 */
public interface PotionThing extends Thing
{

	/**
	 * 每秒恢復比率,萬分位(2000)
	 * 
	 * @return
	 */
	int getRate();

	void setRate(int rate);
}
