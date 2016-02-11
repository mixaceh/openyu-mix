package org.openyu.mix.vip.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.commons.enumz.IntEnum;

/**
 * vip類型
 */
public enum VipType implements IntEnum
{
	/**
	 * 預設0
	 */
	_0(0),

	/**
	 * 1-5級
	 */
	_1(1), _2(2), _3(3), _4(4), _5(5),

	/**
	 * 6-10級
	 */
	_6(6), _7(7), _8(8), _9(9), _10(10),

	;
	private final int value;

	private VipType(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.append("(" + value + ") " + super.toString());
		return builder.toString();
	}
}
