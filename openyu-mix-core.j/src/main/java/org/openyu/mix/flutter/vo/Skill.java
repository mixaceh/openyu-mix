package org.openyu.mix.flutter.vo;

import org.openyu.mix.item.vo.EnhanceLevel;

/**
 * 技能
 */
public interface Skill {

	/**
	 * 技能類別,key
	 * 
	 * @return
	 */
	SkillType getSkillType();

	void setSkillType(SkillType skillType);

	/**
	 * 強化等級(玩家可以改變)
	 * 
	 * @return
	 */
	EnhanceLevel getEnhanceLevel();

	void setEnhanceLevel(EnhanceLevel enhanceLevel);
}
