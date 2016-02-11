package org.openyu.mix.manor.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 成熟類別
 */
public enum MatureType implements IntEnum {
	/**
	 * 未種植
	 */
	PENDING(1),

	/**
	 * 成長中
	 */
	GROWING(2),

	/**
	 * 成熟了
	 */
	MATURE(3),

	/**
	 * 枯萎了
	 */
	WITHER(4),

	;
	private final int value;

	private MatureType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
