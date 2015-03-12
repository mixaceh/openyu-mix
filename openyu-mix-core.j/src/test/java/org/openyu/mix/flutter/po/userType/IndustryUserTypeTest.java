package org.openyu.mix.flutter.po.userType;

import static org.junit.Assert.*;
import org.junit.Test;

import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class IndustryUserTypeTest extends BaseTestSupporter {

	private static IndustryUserType userType = new IndustryUserType();

	@Test
	// 1000000 times: 113 mills.
	// 1000000 times: 124 mills.
	// 1000000 times: 118 mills.
	// verified
	public void marshal() {
		Industry value = IndustryCollector.getInstance().getIndustry(
				"HUMAN_WARRIOR_1");
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
		assertEquals("♥1♠HUMAN_WARRIOR_1", result);
	}

	@Test
	// 1000000 times: 1341 mills.
	// 1000000 times: 1388 mills.
	// 1000000 times: 1335 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠HUMAN_WARRIOR_1";
		//
		Industry result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("HUMAN_WARRIOR_1", result.getId());
	}
}
