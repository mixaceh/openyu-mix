package org.openyu.mix.item.vo.thing;

/**
 * 魔法藥水道具
 */
public interface PotionMpThing extends PotionThing
{
	/**
	 * 持續秒數
	 * 
	 * @return
	 */
	int getSecond();

	void setSecond(int second);
}
