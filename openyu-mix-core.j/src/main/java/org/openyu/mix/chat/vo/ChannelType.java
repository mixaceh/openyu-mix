package org.openyu.mix.chat.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 頻道類別
 */
public enum ChannelType implements IntEnum {
	/**
	 * 系統
	 */
	SYSTEM(1),

	/**
	 * 私聊
	 */
	PRIVATE(2),

	/**
	 * 附近
	 */
	NEARBY(3),

	/**
	 * 隊伍
	 */
	TEAM(4),

	/**
	 * 社團,盟,公會
	 */
	LEAGUE(5),

	/**
	 * 本地
	 */
	LOCAL(6),

	/**
	 * 世界
	 */
	WORLD(7),

	/**
	 * 交易
	 */
	TRADE(8),

	;
	private final int intValue;

	private ChannelType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
