package org.openyu.mix.flutter.po.userType;

import static org.junit.Assert.*;
import org.junit.Test;

import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class RaceTypeUserTypeTest extends BaseTestSupporter {

	private static RaceTypeUserType userType = new RaceTypeUserType();

	@Test
	// 1000000 times: 113 mills.
	// 1000000 times: 124 mills.
	// 1000000 times: 118 mills.
	// verified
	public void marshal() {
		RaceType value = RaceType.YUAN;
		//
		String result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 1
		System.out.println(result);
		//
		assertEquals("♥1♠1", result);
	}

	@Test
	// 1000000 times: 1341 mills.
	// 1000000 times: 1388 mills.
	// 1000000 times: 1335 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠1";
		//
		RaceType result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(RaceType.YUAN, result);
	}
}
