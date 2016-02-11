package org.openyu.mix.train.service.impl;

import org.junit.After;
import org.junit.Test;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.TrainTestSupporter;
import org.openyu.commons.thread.ThreadHelper;

public class TrainLogServiceImplTest extends TrainTestSupporter {
	
	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void recordInspire() {
		Role role = mockRole();
		//
		trainLogService.recordInspire(role, System.currentTimeMillis(), null,
				1000);
	}

}
