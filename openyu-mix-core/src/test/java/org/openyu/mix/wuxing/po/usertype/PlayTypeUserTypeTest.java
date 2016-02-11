package org.openyu.mix.wuxing.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.wuxing.po.usertype.PlayTypeUserType;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class PlayTypeUserTypeTest extends BaseTestSupporter {

	private static PlayTypeUserType userType = new PlayTypeUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		PlayType value = PlayType.GOLDEN;
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
		assertEquals("♥1♠3", result);
	}

	@Test
	// #issue
	// 1000000 times: 3959 mills.
	// 1000000 times: 4180 mills.
	// 1000000 times: 4470 mills.
	// #fix
	// 1000000 times: 2768 mills.
	// 1000000 times: 2425 mills.
	// 1000000 times: 2559 mills.
	public void unmarshal() {
		String value = "♥1♠3";
		//
		PlayType result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(PlayType.GOLDEN, result);
	}
}
