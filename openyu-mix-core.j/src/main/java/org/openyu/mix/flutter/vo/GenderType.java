package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 性別
 */
public enum GenderType implements IntEnum {
	/**
	 * 男性
	 */
	MALE(1),

	/**
	 * 女性
	 */
	FEMALE(2),

	//
	;

	private final int intValue;

	private GenderType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}