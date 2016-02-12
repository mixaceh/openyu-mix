package org.openyu.mix.train.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.train.po.usertype.TrainInfoUserType;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.train.vo.impl.TrainInfoImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class TrainInfoUserTypeTest extends BaseTestSupporter {

	private static TrainInfoUserType userType = new TrainInfoUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		TrainInfo value = new TrainInfoImpl();
		//
		value.setJoinTime(180000);
		value.setQuitTime(270000);
		value.setDailyMills(5000);
		value.setInspireTime(2000);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		//
		System.out.println(result);
		assertEquals("♥1♠180000♠270000♠5000♠2000", result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠180000♠270000♠5000♠2000";
		//
		TrainInfo result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		assertEquals(180000, result.getJoinTime());
		assertEquals(270000, result.getQuitTime());
		assertEquals(5000, result.getDailyMills());
		assertEquals(2000, result.getInspireTime());
	}
}
