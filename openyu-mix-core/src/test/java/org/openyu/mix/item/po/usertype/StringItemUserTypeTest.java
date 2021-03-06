package org.openyu.mix.item.po.usertype;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.openyu.mix.item.po.usertype.StringItemUserType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class StringItemUserTypeTest extends BaseTestSupporter {

	private static StringItemUserType userType = new StringItemUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		Map<String, Item> value = new LinkedHashMap<String, Item>();
		//
		Item item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G001");
		item.setUniqueId("U_00");
		value.put(item.getId(), item);
		//
		item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G002");
		item.setUniqueId("U_01");
		value.put(item.getId(), item);
		//
		item = ThingCollector.getInstance().createThing("T_ROLE_EXP_G003");
		item.setUniqueId("U_02");
		value.put(item.getId(), item);
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
				"♥1♠3♠T_ROLE_EXP_G001♠T_ROLE_EXP_G001♦U_00♦0♦0♠T_ROLE_EXP_G002♠T_ROLE_EXP_G002♦U_01♦0♦0♠T_ROLE_EXP_G003♠T_ROLE_EXP_G003♦U_02♦0♦0",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠3♠T_ROLE_EXP_G001♠T_ROLE_EXP_G001♦U_00♦0♦0♠T_ROLE_EXP_G002♠T_ROLE_EXP_G002♦U_01♦0♦0♠T_ROLE_EXP_G003♠T_ROLE_EXP_G003♦U_02♦0♦0";
		//
		Map<String, Item> result = null;
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
