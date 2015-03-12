package org.openyu.mix.item.vo.thing;

import org.openyu.mix.item.vo.LevelType;

/**
 * 強化武器道具
 */
public interface EnhanceWeaponThing extends EnhanceThing
{
	/**
	 * 裝備等級
	 * 
	 * @return
	 */
	LevelType getLevelType();

	void setLevelType(LevelType levelType);

}
