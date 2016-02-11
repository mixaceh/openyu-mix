package org.openyu.mix.item.vo.thing;

/**
 * 治癒藥水道具
 */
public interface PotionHpThing extends PotionThing
{
	/**
	 * 持續秒數
	 * 
	 * @return
	 */
	int getSecond();

	void setSecond(int second);
}
