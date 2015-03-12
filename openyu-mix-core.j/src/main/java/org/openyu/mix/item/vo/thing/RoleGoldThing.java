package org.openyu.mix.item.vo.thing;

/**
 * 角色金幣道具
 */
public interface RoleGoldThing extends RoleThing
{
	/**
	 * 金幣
	 * 
	 * @return
	 */
	long getGold();

	void setGold(long gold);
}
