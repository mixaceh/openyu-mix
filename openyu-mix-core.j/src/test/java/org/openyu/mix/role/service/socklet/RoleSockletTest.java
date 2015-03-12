package org.openyu.mix.role.service.socklet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class RoleSockletTest extends RoleTestSupporter
{

	@After
	public void tearDown() throws Exception
	{
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void ROLE_DEBUG_CHANGE_EXP_REQUEST()
	{
		Role role = mockRole();
		//
		Message message = messageService.createClient(role.getId(),
			CoreMessageType.ROLE_DEBUG_CHANGE_EXP_REQUEST);

		message.addString(role.getId());
		message.addLong(90000L);
		//
		roleSocklet.service(message);
	}

	@Test
	public void ROLE_DEBUG_CHANGE_LEVEL_REQUEST()
	{
		Role role = mockRole();
		//
		Message message = messageService.createClient(role.getId(),
			CoreMessageType.ROLE_DEBUG_CHANGE_LEVEL_REQUEST);

		message.addString(role.getId());
		message.addInt(1);
		//
		roleSocklet.service(message);
	}

	@Test
	public void ROLE_DEBUG_CHANGE_GOLD_REQUEST()
	{
		Role role = mockRole();
		//
		Message message = messageService.createClient(role.getId(),
			CoreMessageType.ROLE_DEBUG_CHANGE_GOLD_REQUEST);

		message.addString(role.getId());
		message.addLong(500L);
		//
		roleSocklet.service(message);
	}

	@Test
	public void ROLE_DEBUG_CHANGE_FAME_REQUEST()
	{
		Role role = mockRole();
		//
		Message message = messageService.createClient(role.getId(),
			CoreMessageType.ROLE_DEBUG_CHANGE_FAME_REQUEST);

		message.addString(role.getId());
		message.addInt(100);
		//
		roleSocklet.service(message);
	}

	@Test
	public void ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST()
	{
		Role role = mockRole();
		//
		Message message = messageService.createClient(role.getId(),
			CoreMessageType.ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST);

		message.addString(role.getId());
		message.addInt(1);
		message.addInt(10);
		message.addInt(10);
		message.addInt(1000);
		//
		roleSocklet.service(message);
	}

	// --------------------------------------------------
	// 啟動slave1,模擬真正連線
	// --------------------------------------------------
	public static class ConnectorTest extends RoleTestSupporter
	{
		@Before
		public void setUp() throws Exception
		{
			final String ROLE_ID = "TEST_ROLE";

			// 連線到slave1, localhost:4110
			javaConnector.setId(ROLE_ID);
			javaConnector.setIp("localhost");
			javaConnector.setPort(4110);
			javaConnector.start();
		}

		@After
		public void tearDown() throws Exception
		{
			ThreadHelper.sleep(5 * 1000);
		}

		@Test
		public void ROLE_DEBUG_CHANGE_EXP_REQUEST()
		{
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
				CoreMessageType.ROLE_DEBUG_CHANGE_EXP_REQUEST);

			message.addString(ROLE_ID);
			message.addLong(100L);
			//
			javaConnector.send(message);
		}

		@Test
		public void ROLE_DEBUG_CHANGE_LEVEL_REQUEST()
		{
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
				CoreMessageType.ROLE_DEBUG_CHANGE_LEVEL_REQUEST);

			message.addString(ROLE_ID);
			message.addInt(1);
			//
			javaConnector.send(message);
		}

		@Test
		public void ROLE_DEBUG_CHANGE_GOLD_REQUEST()
		{
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
				CoreMessageType.ROLE_DEBUG_CHANGE_GOLD_REQUEST);

			message.addString(ROLE_ID);
			message.addLong(500);
			//
			javaConnector.send(message);
		}

		@Test
		public void ROLE_DEBUG_CHANGE_FAME_REQUEST()
		{
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
				CoreMessageType.ROLE_DEBUG_CHANGE_FAME_REQUEST);

			message.addString(ROLE_ID);
			message.addInt(100);
			//
			javaConnector.send(message);
		}

		@Test
		public void ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST()
		{
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
				CoreMessageType.ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST);

			message.addString(ROLE_ID);
			message.addInt(1);
			message.addInt(10);
			message.addInt(10);
			message.addInt(1000);
			//
			javaConnector.send(message);
		}
	}
}
