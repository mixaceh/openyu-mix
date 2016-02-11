package org.openyu.mix.activity.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 活動類別
 */
public enum ActivityType implements IntEnum {
	// --------------------------------------------------
	// 獎勵活動
	// --------------------------------------------------
	/**
	 * 經驗獎勵活動
	 */
	EXP_AWARD(1),

	/**
	 * 技能經驗獎勵活動
	 */
	SP_AWARD(2),

	/**
	 * 技能經驗獎勵活動
	 */
	GOLD_AWARD(3),

	/**
	 * 聲望獎勵活動
	 */
	FAME_AWARD(4),

	/**
	 * vip獎勵活動
	 */
	VIP_AWARD(5),

	// --------------------------------------------------
	// 目標活動
	// --------------------------------------------------
	/**
	 * 經驗目標活動
	 */
	EXP_TARGET(51),

	/**
	 * 技能經驗目標活動
	 */
	SP_TARGET(52),

	/**
	 * 經驗目標活動
	 */
	GOLD_TARGET(53),

	/**
	 * 聲望目標活動
	 */
	FAME_TARGET(54),

	/**
	 * vip目標活動
	 */
	VIP_TARGET(55),

	/**
	 * 每日消費值幣目標活動
	 */
	DAILY_COIN_TARGET(61),

	/**
	 * 累計消費儲值幣目標活動
	 */
	ACCU_COIN_TARGET(62),

	;
	private final int value;

	private ActivityType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
