package org.openyu.mix.sasang.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.SasangTestSupporter;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.service.SasangService.PutType;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.impl.OutcomeImpl;
import org.openyu.commons.thread.ThreadHelper;

public class SasangLogServiceImplTest extends SasangTestSupporter {

	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void recordPlay() {
		Role role = mockRole();
		//
		Outcome outcome = new OutcomeImpl("123");
		sasangLogService.recordPlay(role, PlayType.BRONZE,
				System.currentTimeMillis(), outcome, 10, 1000L, null, 50);
	}

	@Test
	public void recordPut() {
		Role role = mockRole();
		//
		Map<String, Integer> awards = new LinkedHashMap<String, Integer>();
		awards.put("T_POTION_HP_G001", 1);
		awards.put("T_POTION_HP_G002", 5);
		sasangLogService.recordPut(role, PutType.ALL, awards);
	}

	@Test
	public void recordFamous() {
		Role role = mockRole();
		//
		List<Outcome> outcomes = new LinkedList<Outcome>();
		Outcome outcome = new OutcomeImpl("111");
		Prize prize = new PrizeImpl("111");
		prize.setFamous(true);
		outcome.setPrize(prize);
		outcomes.add(outcome);
		sasangLogService.recordFamous(role, PlayType.BRONZE, 1, outcomes);
	}
}
