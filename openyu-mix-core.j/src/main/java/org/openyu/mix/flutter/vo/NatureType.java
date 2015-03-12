package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 自然屬性類別
 * 
 * 六大自然屬性: 聖 暗 水 火 風 地
 * 
 */
public enum NatureType implements IntEnum
{
	/**
	 * 聖
	 */
	HOLY(1),
	/**
	 * 暗
	 */
	DARK(2),
	/**
	 * 水
	 */
	WATER(3),
	/**
	 * 火
	 */
	FIRE(4),
	/**
	 * 風
	 */
	WIND(5),
	/**
	 * 地
	 */
	EARTH(6);

	private final int intValue;

	private NatureType(int intValue)
	{
		this.intValue = intValue;
	}

	public int getValue()
	{
		return intValue;
	}

}
