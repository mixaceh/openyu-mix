package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 臉型
 */
public enum FaceType implements IntEnum {
	/**
	 * 可愛
	 */
	CUTE(1),

	/**
	 * 氣質
	 */
	TEMPERAMENT(2),

	/**
	 * 亮麗
	 */
	BRIGHT(3),

	/**
	 * 冷酷
	 */
	COOL(4),

	//
	;

	private final int intValue;

	private FaceType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
