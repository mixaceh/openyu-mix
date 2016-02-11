package org.openyu.mix.core.service.impl;

import org.junit.After;
import org.junit.Test;
import org.openyu.mix.core.CoreTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;

public class CoreLogServiceImplTest extends CoreTestSupporter
{
	@After
	public void tearDown() throws Exception
	{
		ThreadHelper.sleep(5 * 1000);
	}

	@Test
	public void recordConnection()
	{
		Role role = mockRole();
		role.setEnterTime(System.currentTimeMillis());
		//
		coreLogService.recordRoleConnect(role);
	}

	@Test
	public void recordDisconnection()
	{
		Role role = mockRole();
		role.setLeaveTime(System.currentTimeMillis());
		//
		coreLogService.recordRoleDisconnect(role);
	}
}
