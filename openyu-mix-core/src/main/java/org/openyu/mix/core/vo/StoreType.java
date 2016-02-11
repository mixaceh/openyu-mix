package org.openyu.mix.core.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 儲存類別
 */
public enum StoreType implements IntEnum {
	
	/**
	 * 資料庫
	 */
	DATABASE(1),

	/**
	 * 檔案
	 */
	FILE(2),

	;
	private final int intValue;

	private StoreType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
