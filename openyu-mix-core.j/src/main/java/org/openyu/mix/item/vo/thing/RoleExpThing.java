package org.openyu.mix.item.vo.thing;

/**
 * 角色經驗道具
 */
public interface RoleExpThing extends RoleThing
{
	/**
	 * 經驗
	 * 
	 * @return
	 */
	long getExp();

	void setExp(long exp);
}
