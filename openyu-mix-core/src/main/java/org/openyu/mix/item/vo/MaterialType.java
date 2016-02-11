package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 材料類別
 */
public enum MaterialType implements IntEnum {

	/**
	 * 布質
	 */
	CLOTH(1),

	/**
	 * 木質
	 */
	WOOD(2),

	/**
	 * 礦石
	 */
	ORE(3),

	;
	private final int value;

	private MaterialType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
