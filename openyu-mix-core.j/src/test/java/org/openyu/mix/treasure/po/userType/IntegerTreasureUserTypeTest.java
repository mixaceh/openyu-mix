package org.openyu.mix.treasure.po.userType;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasureCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class IntegerTreasureUserTypeTest extends BaseTestSupporter {

	private static IntegerTreasureUserType userType = new IntegerTreasureUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		Map<Integer, Treasure> value = new LinkedHashMap<Integer, Treasure>();
		//
		Treasure treasure = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G001");
		treasure.setBought(true);
		value.put(0, treasure);
		//
		treasure = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G002");
		treasure.setBought(true);
		value.put(1, treasure);
		//
		treasure = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G003");
		treasure.setBought(true);
		value.put(2, treasure);
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
				"♥1♠3♠0♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1♠1♠T_ROLE_EXP_G002♦ROLE_EXP_001♦1♠2♠T_ROLE_EXP_G003♦ROLE_EXP_001♦1",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠3♠0♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1♠1♠T_ROLE_EXP_G002♦ROLE_EXP_001♦1♠2♠T_ROLE_EXP_G003♦ROLE_EXP_001♦1";
		//
		Map<Integer, Treasure> result = null;
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
