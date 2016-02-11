package org.openyu.mix.chat.po.userType;

import static org.junit.Assert.*;
import org.junit.Test;

import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class ChannelTypeUserTypeTest extends BaseTestSupporter {

	private static ChannelTypeUserType userType = new ChannelTypeUserType();

	@Test
	// 1000000 times: 117 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 123 mills.
	// verified
	public void marshal() {
		ChannelType value = ChannelType.SYSTEM;
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
	// 1000000 times: 1346 mills.
	// 1000000 times: 1372 mills.
	// 1000000 times: 1382 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠1";
		//
		ChannelType result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(ChannelType.SYSTEM, result);
	}
}
