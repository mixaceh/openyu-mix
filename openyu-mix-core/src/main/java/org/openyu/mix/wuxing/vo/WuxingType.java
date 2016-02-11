package org.openyu.mix.wuxing.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 五行類別,key值
 * 
 */
public enum WuxingType implements IntEnum {
	/**
	 * 金
	 */
	METAL(1),

	/**
	 * 木
	 */
	WOOD(2),

	/**
	 * 土
	 */
	EARTH(3),

	/**
	 * 水
	 */
	WATER(4),

	/**
	 * 火
	 */
	FIRE(5),

	;

	private final int value;

	private WuxingType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
