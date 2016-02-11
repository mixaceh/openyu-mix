package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 武器類別
 */
public enum WeaponType implements IntEnum {
	// ---------------------------------------------------
	// 單手系列
	// ---------------------------------------------------
	/**
	 * 單手劍(1支)
	 */
	SWORD(1),

	/**
	 * 單手槌(1支)
	 */
	HAMMER(2),

	/**
	 * 單手匕首(1支)
	 */
	DAGGER(3),

	/**
	 * 單手法杖(1支)
	 */
	STAFF(4),

	// ---------------------------------------------------
	// 雙手系列
	// ---------------------------------------------------
	/**
	 * 雙手劍(1支)
	 */
	HANDS_SWORD(11),

	/**
	 * 雙手槌(1支)
	 */
	HANDS_HAMMER(12),

	/**
	 * 雙手匕首(1支)
	 */
	HANDS_DAGGER(13),

	/**
	 * 雙手法杖(1支)
	 */
	HANDS_STAFF(14),

	/**
	 * 雙手弓(1支)
	 */
	HANDS_BOW(15),

	// ---------------------------------------------------
	// 融合系列
	// ---------------------------------------------------
	/**
	 * 雙刀(兩支)
	 */
	TWO_SWORD(21),

	/**
	 * 雙槌(兩支)
	 */
	TWO_HAMMER(22),

	/**
	 * 雙匕首(兩支)
	 */
	TWO_DAGGER(23),

	/**
	 * 雙法杖(兩支)
	 */
	TWO_STAFF(24),

	/**
	 * 雙弓(兩支)
	 */
	TWO_BOW(25),

	;
	private final int value;

	private WeaponType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}