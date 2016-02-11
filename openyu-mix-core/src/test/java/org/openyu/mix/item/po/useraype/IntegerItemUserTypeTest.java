package org.openyu.mix.item.po.useraype;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.openyu.mix.item.po.useraype.IntegerItemUserType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class IntegerItemUserTypeTest extends BaseTestSupporter {

	private static IntegerItemUserType userType = new IntegerItemUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		Map<Integer, Item> value = new LinkedHashMap<Integer, Item>();
		//
		Item item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G001");
		item.setUniqueId("U_00");
		value.put(0, item);
		//
		item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G002");
		item.setUniqueId("U_01");
		value.put(1, item);
		//
		item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G003");
		item.setUniqueId("U_02");
		value.put(2, item);
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

		System.out.println(result);
		assertEquals(
				"♥1♠3♠0♠T_ROLE_EXP_G001♦U_00♦0♦0♠1♠T_ROLE_EXP_G002♦U_01♦0♦0♠2♠T_ROLE_EXP_G003♦U_02♦0♦0",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠3♠0♠T_ROLE_EXP_G001♦U_00♦0♦0♠1♠T_ROLE_EXP_G002♦U_01♦0♦0♠2♠T_ROLE_EXP_G003♦U_02♦0♦0";
		//
		Map<Integer, Item> result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertEquals(3, result.size());

	}
}
