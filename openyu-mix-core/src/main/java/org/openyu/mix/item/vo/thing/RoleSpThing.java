package org.openyu.mix.item.vo.thing;

/**
 * 角色技魂(sp)道具
 */
public interface RoleSpThing extends RoleThing
{
	/**
	 * 技魂(sp)
	 * 
	 * @return
	 */
	long getSp();

	void setSp(long sp);
}
