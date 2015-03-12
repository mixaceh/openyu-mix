package org.openyu.mix.item.vo.thing;

import org.openyu.mix.item.vo.LevelType;

/**
 * 強化防具道具
 */
public interface EnhanceArmorThing extends EnhanceThing
{
	/**
	 * 裝備等級
	 * 
	 * @return
	 */
	LevelType getLevelType();

	void setLevelType(LevelType levelType);

}
