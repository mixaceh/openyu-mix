package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 道具類別
 */
public enum ItemType implements IntEnum {
	/**
	 * 物品
	 */
	THING(1),

	/**
	 * 材料
	 */
	MATERIAL(2),

	/**
	 * 裝備-防具
	 */
	ARMOR(3),

	/**
	 * 裝備-武器
	 */
	WEAPON(4),

	/**
	 * 種子
	 */
	SEED(5),

	/**
	 * 土地
	 */
	LAND(6),

	//
	;
	private final int value;

	private ItemType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}