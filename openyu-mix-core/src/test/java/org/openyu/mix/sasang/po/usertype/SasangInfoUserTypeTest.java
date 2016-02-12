package org.openyu.mix.sasang.po.usertype;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.sasang.po.usertype.SasangInfoUserType;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.sasang.vo.impl.OutcomeImpl;
import org.openyu.mix.sasang.vo.impl.SasangInfoImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class SasangInfoUserTypeTest extends BaseTestSupporter {

	private static SasangInfoUserType userType = new SasangInfoUserType();

	@Test
	// 1000000 times: 924 mills.
	// 1000000 times: 1059 mills.
	// 1000000 times: 1075 mills.
	// verified
	public void marshal() {
		SasangInfo value = new SasangInfoImpl();
		value.setPlayTime(1337568856471L);
		value.setDailyTimes(1);
		value.setAccuTimes(1);
		//
		Outcome outcome = new OutcomeImpl("123");
		value.setOutcome(outcome);
		//
		value.getAwards().put("T_POTION_HP_G001", 1);
		value.getAwards().put("T_POTION_MP_G001", 1);
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

		System.out.println(result.length() + ", " + result);
		assertEquals(
				"♥1♠1337568856471♠1♠1♠123♠2♦T_POTION_HP_G001♣1♦T_POTION_MP_G001♣1",
				result);
	}

	@Test
	// 1000 times: 642 mills.
	// 1000 times: 624 mills.
	// 1000 times: 655 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠1337568856471♠1♠1♠123♠2♦T_POTION_HP_G001♣1♦T_POTION_MP_G001♣1";
		//
		SasangInfo result = null;
		//
		int count = 1000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		assertEquals(1, result.getDailyTimes());
		assertEquals(1, result.getAccuTimes());
		assertEquals(1337568856471L, result.getPlayTime());
		assertEquals(2, result.getAwards().size());
		//
		Outcome outcome = result.getOutcome();
		assertEquals("123", outcome.getId());
	}
}
