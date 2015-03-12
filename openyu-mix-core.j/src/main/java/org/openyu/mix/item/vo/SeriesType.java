package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.IntEnum;

/**
 * 系列類別
 * 
 * 奧林匹斯十二主神
 * 
 * 宙斯、希拉、波賽頓、阿瑞斯(瑪爾斯-Mars)、荷米斯、赫淮斯托斯、阿佛羅狄忒、雅典娜、阿波羅及阿蒂蜜絲
 */
public enum SeriesType implements IntEnum {
	/**
	 * 阿瑞斯(瑪爾斯-Mars)
	 */
	MARS(4),

	;
	private final int value;

	private SeriesType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
