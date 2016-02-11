package org.openyu.mix.qixing.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 七星類別,key值
 * 
 */
public enum QixingType implements IntEnum {
	/**
	 * 天樞
	 */
	TIANSHU(1),

	/**
	 * 天璇
	 */
	TIANXUAN(2),

	/**
	 * 天璣
	 */
	TIANCHI(3),

	/**
	 * 天權
	 */
	TIANQUAN(4),

	/**
	 * 玉衡
	 */
	YUHENG(5),

	/**
	 * 開陽
	 */
	KAIYANG(6),

	/**
	 * 瑤光
	 */
	YAOQUANG(7),

	//
	;

	private final int intValue;

	private QixingType(int intValue) {
		this.intValue = intValue;
	}

	public int getValue() {
		return intValue;
	}
}
