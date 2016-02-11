package org.openyu.mix.item.vo.thing;

/**
 * 返回城鎮道具
 */
public interface BackTownThing extends BackThing
{
	/**
	 * 等待秒數
	 * 
	 * @return
	 */
	int getSecond();

	void setSecond(int second);
}
