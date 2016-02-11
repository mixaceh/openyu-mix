package org.openyu.mix.item.vo.thing;

/**
 * 角色聲望道具
 */
public interface RoleFameThing extends RoleThing
{
	/**
	 * 聲望
	 * 
	 * @return
	 */
	int getFame();

	void setFame(int fame);
}
