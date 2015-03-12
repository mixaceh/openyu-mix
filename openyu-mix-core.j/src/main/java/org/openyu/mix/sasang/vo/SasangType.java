package org.openyu.mix.sasang.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 四象類別,key值
 * 
 */
public enum SasangType implements IntEnum {
	
	/**
	 * 青龍
	 */
	AZURE_DRAGON(1),

	/**
	 * 白虎
	 */
	WHITE_TIGER(2),

	/**
	 * 朱雀
	 */
	VERMILION_BIRD(3),

	/**
	 * 玄武
	 */
	BLACK_TORTOISE(4),

	/**
	 * 陰
	 */
	YIN(5),

	/**
	 * 陽
	 */
	YANG(6),

	/**
	 * 虛無
	 */
	NOTHING(7),

	//
	;

	private final int value;

	private SasangType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
