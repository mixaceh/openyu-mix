package org.openyu.mix.item.vo.thing;

import org.openyu.mix.item.vo.Thing;

/**
 * 角色道具
 */
public interface RoleThing extends Thing
{

	/**
	 * 成功機率,萬分位(2000)
	 * 
	 * @return
	 */
	int getProbability();

	void setProbability(int probability);
}
