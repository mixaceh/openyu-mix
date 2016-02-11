package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 部位類別,位置類別
 */
public enum PositionType implements IntEnum {

	// ---------------------------------------------------
	// 武器 HAND-手上拿的
	// ---------------------------------------------------
	/**
	 * 左手
	 */
	LEFT_HAND(1),

	/**
	 * 右手
	 */
	RIGHT_HAND(2),

	/**
	 * 雙手
	 */
	PAIR_HAND(3),

	// ---------------------------------------------------
	// 防具 BODY-身上穿的防具
	// ---------------------------------------------------
	/**
	 * 頭
	 */
	BODY_HEAD(11),

	/**
	 * 手
	 */
	BODY_HAND(12),

	/**
	 * 腳
	 */
	BODY_FOOT(13),
	/**
	 * 上半身
	 */
	BODY_UPPER(14),

	/**
	 * 下半身
	 */
	BODY_LOWER(15),

	/**
	 * 披風(背部)
	 */
	BODY_CLOAK(16),

	// ---------------------------------------------------
	// 防具 FITTING-身上穿的配件
	// ---------------------------------------------------
	/**
	 * 頭飾
	 */
	FITTING_HEAD_JEWELRY(21),

	/**
	 * 手镯
	 */
	FITTING_BRACELET(22),

	/**
	 * 腳飾
	 */
	FITTING_FOOT_JEWELRY(23),

	/**
	 * 披肩
	 */
	FITTING_SHAWL(24),

	/**
	 * 腰帶
	 */
	FITTING_BELT(25),

	// ---------------------------------------------------
	// 防具 ACCESSORIES-飾品
	// ---------------------------------------------------
	/**
	 * 項鍊
	 */
	ACCESSORIES_NECKLACE(31),

	/**
	 * 耳環
	 */
	ACCESSORIES_EARRING(32),

	/**
	 * 戒指
	 */
	ACCESSORIES_RING(33),

	;
	private final int value;

	private PositionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}