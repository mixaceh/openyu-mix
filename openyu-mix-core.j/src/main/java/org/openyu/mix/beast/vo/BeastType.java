package org.openyu.mix.beast.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 神獸類別
 */
public enum BeastType implements IntEnum {
	// FLY(1), // 腾空
	// HUMAN(2), // 人型
	// MIMSEIS(3), // 拟态
	// STONE(4), // 坚体
	// BRUTE(5), // 蛮劲
	// CLOUD(6), // 云体
	// FLAME(7), // 火流
	// ICE(8), // 冰体
	// LIGHT(9), // 电光
	// POISON(10), // 毒息

	//
	;

	private final int value;

	private BeastType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
