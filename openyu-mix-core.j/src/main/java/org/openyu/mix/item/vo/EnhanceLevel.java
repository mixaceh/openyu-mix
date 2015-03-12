package org.openyu.mix.item.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.commons.enumz.IntEnum;

/**
 * 強化等級(玩家可以改變)
 */
public enum EnhanceLevel implements IntEnum
{

	/**
	 * 預設0
	 */
	_0(0),

	/**
	 * +1-+10(技能到+30)
	 */
	_1(1), _2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), _10(10),

	/**
	 * +11-+20
	 */
	_11(11), _12(12), _13(13), _14(14), _15(15), _16(16), _17(17), _18(18), _19(19), _20(20),

	/**
	 * +21-+30
	 */
	_21(21), _22(22), _23(23), _24(24), _25(25), _26(26), _27(27), _28(28), _29(29), _30(30),

	;
	private final int value;

	private EnhanceLevel(int value)
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
		builder.append("+" + value);
		return builder.toString();
	}
}
