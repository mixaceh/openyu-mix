package org.openyu.mix.monster.vo;

import org.openyu.mix.flutter.vo.Flutter;

/**
 * 怪物
 */
public interface Monster extends Flutter {

	/**
	 * 怪物類型
	 * 
	 * @return
	 */
	MonsterType getMonsterType();

	void setMonsterType(MonsterType monsterType);

}
