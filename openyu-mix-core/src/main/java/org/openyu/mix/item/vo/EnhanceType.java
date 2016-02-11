package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 強化類型
 * 
 */
public enum EnhanceType implements IntEnum
{

	//想願 WILLING
	//憧憬 YEARN

	/**
	 * 一般強化,失敗裝備消失
	 */
	GENERAL(1),

	/**
	 * 祝福強化,失敗強化歸0,裝備還在
	 */
	BLESS(2),

	/**
	 * 幻想強化,失敗強化保有原本強化,不歸0,裝備還在
	 */
	FANTASY(3),

	;
	private final int value;

	private EnhanceType(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
