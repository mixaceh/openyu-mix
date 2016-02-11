package org.openyu.mix.treasure.po.useraype;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.treasure.po.useraype.TreasureUserType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasureCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class TreasureUserTypeTest extends BaseTestSupporter {

	private static TreasureUserType userType = new TreasureUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		Treasure value = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G001");
		value.setBought(true);
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
		assertEquals("♥1♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1", result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1";
		//
		Treasure result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertEquals("T_ROLE_EXP_G001", result.getId());
		assertEquals("ROLE_EXP_001", result.getStockId());
		assertEquals(true, result.isBought());

	}
}
