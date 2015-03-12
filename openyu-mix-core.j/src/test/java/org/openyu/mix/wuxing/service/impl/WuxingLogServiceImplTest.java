package org.openyu.mix.wuxing.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.WuxingTestSupporter;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.impl.OutcomeImpl;
import org.openyu.commons.thread.ThreadHelper;

public class WuxingLogServiceImplTest extends WuxingTestSupporter {
	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void recordPlay() {
		Role role = mockRole();
		//
		Outcome outcome = new OutcomeImpl("21341", "21544", "12315");
		wuxingLogService.recordPlay(role, PlayType.BRONZE,
				System.currentTimeMillis(), outcome, 10, 1000L, null, 50);
	}

	@Test
	public void recordPut() {
		Role role = mockRole();
		//
		Map<String, Integer> awards = new LinkedHashMap<String, Integer>();
		awards.put("T_POTION_HP_G001", 1);
		awards.put("T_POTION_HP_G002", 5);
		wuxingLogService.recordPut(role, PutType.ALL, awards);
	}

	@Test
	public void recordFamous() {
		Role role = mockRole();
		//
		List<Outcome> outcomes = new LinkedList<Outcome>();
		Outcome outcome = new OutcomeImpl("21341", "21544", "12315");
		//
		Prize prize = new PrizeImpl("5");
		prize.setFamous(true);
		outcome.getPrizes().add(prize);
		//
		prize = new PrizeImpl("0");
		prize.setFamous(false);
		outcome.getPrizes().add(prize);
		//
		outcomes.add(outcome);
		wuxingLogService.recordFamous(role, PlayType.BRONZE, 1, outcomes);
	}
}
