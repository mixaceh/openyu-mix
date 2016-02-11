package org.openyu.mix.treasure.po.usertype;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.treasure.po.usertype.TreasurePenUserType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasureCollector;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.impl.TreasurePenImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class TreasurePenUserTypeTest extends BaseTestSupporter {

	private static TreasurePenUserType userType = new TreasurePenUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		TreasurePen value = new TreasurePenImpl();

		value.setRefreshTime(180000);
		// 祕寶
		Treasure treasure = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G001");
		treasure.setBought(true);
		value.getTreasures().put(0, treasure);
		//
		treasure = TreasureCollector.getInstance().createTreasure(
				"ROLE_EXP_001", "T_ROLE_EXP_G002");
		treasure.setBought(true);
		value.getTreasures().put(1, treasure);
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
		assertEquals(
				"♥1♠180000♠2♠0♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1♠1♠T_ROLE_EXP_G002♦ROLE_EXP_001♦1",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠180000♠2♠0♠T_ROLE_EXP_G001♦ROLE_EXP_001♦1♠1♠T_ROLE_EXP_G002♦ROLE_EXP_001♦1";
		//
		TreasurePen result = null;
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
		assertEquals(180000, result.getRefreshTime());

		Treasure treasure = result.getTreasures().get(0);
		assertEquals("T_ROLE_EXP_G001", treasure.getId());
		assertEquals("ROLE_EXP_001", treasure.getStockId());
		assertTrue(treasure.isBought());

	}
}
