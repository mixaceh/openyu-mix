package org.openyu.mix.item.vo;

import org.openyu.commons.enumz.LimitEnum;

/**
 * 裝備等級, minValue <= level < maxValue
 */
public enum LevelType implements LimitEnum {

	V(181, 201), // 181-200級可裝備

	W(161, 181), // 161-180級可裝備

	X(141, 161), // 141-160級可裝備

	Y(121, 141), // 121-140級可裝備

	Z(101, 121), // 101-120級可裝備

	A(81, 101), // 81-100級可裝備

	B(61, 81), // 61-80級可裝備

	C(41, 61), // 41-60級可裝備

	D(21, 41), // 21-40級可裝備

	E(1, 21), // 1-20級可裝備

	;
	private final int minValue;

	private final int maxValue;

	private LevelType(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int minValue() {
		return minValue;
	}

	public int maxValue() {
		return maxValue;
	}
}