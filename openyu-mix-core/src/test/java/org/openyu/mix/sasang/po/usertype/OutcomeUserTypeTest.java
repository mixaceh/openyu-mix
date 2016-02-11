package org.openyu.mix.sasang.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.sasang.po.usertype.OutcomeUserType;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.impl.OutcomeImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class OutcomeUserTypeTest extends BaseTestSupporter {

	private static OutcomeUserType userType = new OutcomeUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		Outcome value = new OutcomeImpl("111");
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
		assertEquals("♥1♠111", result);
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
		String value = "♥1♠111";
		//
		Outcome result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("111", result.getId());
	}
}
