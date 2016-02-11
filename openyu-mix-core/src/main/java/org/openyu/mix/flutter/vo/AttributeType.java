package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 屬性類別,key
 */
public enum AttributeType implements IntEnum {
	// ---------------------------------------------------
	// 基礎屬性,五大屬性
	// ---------------------------------------------------
	/**
	 * 力量(戰士)
	 */
	STRENGTH(1),

	/**
	 * 敏捷,迴避(弓手)
	 */
	AGILITY(2),

	/**
	 * 智力(巫師)
	 */
	INTELLIGENCE(3),

	/**
	 * 精神(補師)
	 */
	SPIRIT(4),

	/**
	 * 體質(共通)
	 */
	CONSTITUTION(5),

	// ---------------------------------------------------
	// 變動屬性
	// ---------------------------------------------------
	/**
	 * hp
	 */
	HEALTH(11),

	/**
	 * max hp
	 * 
	 * CONSTITUTION*AttributeFactor.factor
	 */
	MAX_HEALTH(12),

	/**
	 * mp
	 */
	MANA(13),

	/**
	 * max mp
	 * 
	 * SPIRIT*AttributeFactor.factor
	 */
	MAX_MANA(14),

	/**
	 * 活力值 ep,經驗值加倍
	 */
	ENERGY(15),

	/**
	 * max ep
	 */
	MAX_ENERGY(16),

	/**
	 * pp
	 */
	PK(17),

	/**
	 * max pp
	 */
	MAX_PK(18),

	// ---------------------------------------------------
	// 戰鬥屬性
	// ---------------------------------------------------

	// ---------------------------------------------------
	// 物理攻擊
	// ---------------------------------------------------
	/**
	 * 物攻
	 * 
	 * STRENGTH*AttributeFactor.factor+AGILITY*AttributeFactor.factor2
	 */
	PHYSICAL_ATTACK(21),

	/**
	 * 物攻致命(暴擊)率,萬分位(2000)
	 * 
	 * STRENGTH*AttributeFactor.factor+AGILITY*AttributeFactor.factor2
	 */
	PHYSICAL_CRITICAL_RATE(22),

	/**
	 * 物攻致命(暴擊)傷害率,萬分位(2000)
	 * 
	 * STRENGTH*AttributeFactor.factor
	 */
	PHYSICAL_CRITICAL_DAMAGE_RATE(23),

	/**
	 * 物攻命中率,萬分位(2000)
	 */
	PHYSICAL_HIT_RATE(24),

	/**
	 * 物攻速度
	 */
	PHYSICAL_ATTACK_SPEED(25),

	// ---------------------------------------------------
	// 物理防禦
	// ---------------------------------------------------
	/**
	 * 物防
	 * 
	 * AGILITY*AttributeFactor.factor
	 */
	PHYSICAL_DEFENCE(31),

	/**
	 * 物防迴避率,萬分位(2000)
	 * 
	 * AGILITY*AttributeFactor.factor
	 */
	PHYSICAL_DODGE_RATE(32),

	// ---------------------------------------------------
	// 魔法攻擊
	// ---------------------------------------------------
	/**
	 * 魔攻
	 * 
	 * INTELLIGENCE*AttributeFactor.factor+SPIRIT*AttributeFactor.factor2
	 */
	MAGIC_ATTACK(41),

	/**
	 * 魔攻致命(暴擊)率,萬分位(2000)
	 * 
	 * SPIRIT*AttributeFactor.factor+INTELLIGENCE*AttributeFactor.factor2
	 */
	MAGIC_CRITICAL_RATE(42),

	/**
	 * 魔攻致命(暴擊)傷害率,萬分位(2000)
	 * 
	 * INTELLIGENCE*AttributeFactor
	 */
	MAGIC_CRITICAL_DAMAGE_RATE(43),

	/**
	 * 魔攻命中率,萬分位(2000)
	 */
	MAGIC_HIT_RATE(44),

	/**
	 * 施法速度
	 */
	MAGIC_ATTACK_SPEED(45),

	// ---------------------------------------------------
	// 魔法防禦
	// ---------------------------------------------------
	/**
	 * 魔防
	 * 
	 * SPIRIT*AttributeFactor
	 */
	MAGIC_DEFENCE(51),

	/**
	 * 魔防迴避率,萬分位(2000)
	 * 
	 * SPIRIT*AttributeFactor
	 */
	MAGIC_DODGE_RATE(52),

	// ---------------------------------------------------
	// 共通屬性
	// ---------------------------------------------------
	/**
	 * 移動速度
	 */
	MOVE_SPEED(61),

	// ---------------------------------------------------
	// 莊園屬性
	// ---------------------------------------------------

	// 土地可強化,以下為土地屬性
	/**
	 * 產量
	 */
	MANOR_OUTPUT(201),

	/**
	 * 產量(暴擊)率,萬分位(2000)
	 */
	MANOR_CRITICAL_RATE(202),

	/**
	 * 產量(暴擊)產量率,萬分位(2000)
	 * 
	 * 
	 */
	MANOR_CRITICAL_OUTPUT_RATE(203),

	/**
	 * 增加成長速度,萬分位(2000)
	 */
	MANOR_SPEED_RATE(204),

	//
	;

	private final int intValue;

	private AttributeType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}

}
