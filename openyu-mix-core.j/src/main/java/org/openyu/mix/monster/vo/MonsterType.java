package org.openyu.mix.monster.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 怪物類別
 */
public enum MonsterType implements IntEnum {
	/**
	 * 普通
	 */
	NORMAL(1),

	/**
	 * 經典,傑作
	 */
	CLASSIC(2),

	/**
	 * 首領
	 */
	BOSS(3),

	/**
	 * 副本
	 */
	INSTANCE(4),

	/**
	 * 副本首領
	 */
	INSTANCE_BOSS(5),

	/**
	 * 寵物
	 */
	PET(6),

	/**
	 * 寵物首領
	 */
	PET_BOSS(7),

	//
	;

	private final int value;

	private MonsterType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
