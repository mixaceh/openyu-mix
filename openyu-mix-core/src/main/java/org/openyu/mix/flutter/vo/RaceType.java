package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 種族類別,key值
 * 
 * 四大種族: 人類,妖精,半獸,矮人
 * 
 * 改為中國5大區域種族,2013/04/04
 * 
 * 中原(巫師),西戎(羌族,羊,鬥士)和東夷(弓手)、北狄(匈奴,遊牧,遊俠)、南蠻(衛士)合稱四夷
 */
public enum RaceType implements IntEnum {
	/**
	 * 中原
	 */
	YUAN(1),

	/**
	 * 西戎
	 */
	RONG(2),

	/**
	 * 東夷
	 */
	YI(3),

	/**
	 * 北狄
	 */
	DI(4),

	/**
	 * 南蠻
	 */
	MAN(5)

	;

	private final int intValue;

	private RaceType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
