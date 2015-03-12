package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 技能類別
 */
public enum SkillType implements IntEnum {

	;

	private final int intValue;

	private SkillType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
