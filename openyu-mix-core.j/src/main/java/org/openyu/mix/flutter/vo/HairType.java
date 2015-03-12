package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 髮型
 */
public enum HairType implements IntEnum {
	/**
	 * 短髮
	 */
	SHORT(1),

	/**
	 * 中髮
	 */
	MEDIUM(2),

	/**
	 * 長髮
	 */
	LONG(3),

	//
	;

	private final int intValue;

	private HairType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
