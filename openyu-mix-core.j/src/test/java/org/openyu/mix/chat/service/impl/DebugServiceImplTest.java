package org.openyu.mix.chat.service.impl;

import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.util.ConfigHelper;

public class DebugServiceImplTest extends ChatTestSupporter
{

	@Test
	public void cheat()
	{
		Role role = mockRole();

		System.out.println("isDebug: " + ConfigHelper.isDebug());
		//只有發送訊息
		debugService.cheat(role.getId(), "exp 100");
	}

}
