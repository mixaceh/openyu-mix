package org.openyu.mix.wuxing.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.wuxing.po.usertype.WuxingInfoUserType;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.WuxingInfo;
import org.openyu.mix.wuxing.vo.impl.OutcomeImpl;
import org.openyu.mix.wuxing.vo.impl.WuxingInfoImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class WuxingInfoUserTypeTest extends BaseTestSupporter {

	private static WuxingInfoUserType userType = new WuxingInfoUserType();

	@Test
	// 1000000 times: 924 mills.
	// 1000000 times: 1059 mills.
	// 1000000 times: 1075 mills.
	// verified
	public void marshal() {
		WuxingInfo value = new WuxingInfoImpl();
		value.setPlayTime(1337568856471L);
		value.setDailyTimes(1);
		value.setAccuTimes(1);
		//
		Outcome outcome = new OutcomeImpl("21341", "21544", "12315");
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
				"♥1♠1337568856471♠1♠1♠21341♠21544♠12315♠2♦T_POTION_HP_G001♣1♦T_POTION_MP_G001♣1",
				result);
	}

	@Test
	// 1000 times: 642 mills.
	// 1000 times: 624 mills.
	// 1000 times: 655 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠1337568856471♠1♠1♠21341♠21544♠12315♠2♦T_POTION_HP_G001♣1♦T_POTION_MP_G001♣1";
		//
		WuxingInfo result = null;
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
		assertEquals("21341", outcome.getId());
		assertEquals("21544", outcome.getBankerId());
		assertEquals("12315", outcome.getPlayerId());
	}
}
