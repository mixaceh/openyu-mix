package org.openyu.mix.flutter.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 職業類別,key
 * 
 * 五大職業: 鬥士,衛士,遊俠,弓手,巫師
 * 
 * 其他三職業先不用(牧師,法師,招喚)
 * 
 * 轉職: 1-5轉
 */
public enum CareerType implements IntEnum {

	/**
	 * 戰士(鬥士),1-5轉
	 */
	WARRIOR_1(1, 1), // WARRIOR_2(2, 2), WARRIOR_3(3, 3), WARRIOR_4(4, 4),
						// WARRIOR_5(5, 5),

	/**
	 * 弓手,1-5轉
	 */
	ARCHER_1(11, 1), // ARCHER_2(12, 2), ARCHER_3(13, 3), ARCHER_4(14, 4),
						// ARCHER_5(15, 5),

	/**
	 * 巫師,1-5轉
	 */
	MAGE_1(21, 1), // MAGE_2(22, 2), MAGE_3(23, 3), MAGE_4(24, 4), MAGE_5(25,
					// 5),

	/**
	 * 遊俠(小刀),1-5轉
	 */
	RANGER_1(31, 1), // RANGER_2(32, 2), RANGER_3(33, 3), RANGER_4(34, 4),
						// RANGER_5(35, 5),

	/**
	 * 衛士(肉肉),1-5轉
	 */
	GUARD_1(41, 1), // GUARD_2(42, 2), GUARD_3(43, 3), GUARD_4(44, 4),
					// GUARD_5(45, 5),

	//
	// /**
	// * 補師,1-5轉
	// */
	// PRIEST_1(31, 1), PRIEST_2(32, 2), PRIEST_3(33, 3), PRIEST_4(34, 4),
	// PRIEST_5(35, 5),
	//
	// /**
	// * 法師(輔助),1-5轉
	// */
	// AIDER_1(41, 1), AIDER_2(42, 2), AIDER_3(43, 3), AIDER_4(44, 4),
	// AIDER_5(45, 5),
	//
	// /**
	// * 招喚,1-5轉
	// */
	// SUMMONER_1(51, 1), SUMMONER_2(52, 2), SUMMONER_3(53, 3), SUMMONER_4(54,
	// 4), SUMMONER_5(55,
	// 5)

	;

	private final int intValue;

	/**
	 * 職業等級,表示1-5轉
	 */
	private final int levelValue;

	private CareerType(int intValue, int levelValue) {
		this.intValue = intValue;
		this.levelValue = levelValue;
	}

	public int getValue() {
		return intValue;
	}

	public int levelValue() {
		return levelValue;
	}
}