package org.openyu.mix.manor.service.impl;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.manor.ManorTestSupporter;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Seed;

import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;

public class ManorLogServiceImplTest extends ManorTestSupporter {
	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void recordReclaim() {
		Role role = mockRole();
		mockReclaim(role);
		//
		manorLogService.recordReclaim(role, 0, role.getManorPen().getFarm(0)
				.getLand(), 1000L);
	}

	@Test
	public void recordDisuse() {
		Role role = mockRole();
		mockReclaim(role);
		//
		manorLogService.recordDisuse(role, 0, role.getManorPen().getFarm(0)
				.getLand(), 1000L);
	}

	@Test
	public void recordCulture() {
		Role role = mockRole();
		mockReclaim(role);
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 1);
		manorLogService.recordCulture(role, CultureType.PLANT, 0, 0, seed,
				null, 1000);
	}
}
