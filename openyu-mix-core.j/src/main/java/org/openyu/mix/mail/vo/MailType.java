package org.openyu.mix.mail.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 郵件類別
 */
public enum MailType implements IntEnum {
	/**
	 * 系統
	 */
	SYSTEM(1),

	/**
	 * 一般
	 */
	GENERAL(2),

	;
	private final int value;

	private MailType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
