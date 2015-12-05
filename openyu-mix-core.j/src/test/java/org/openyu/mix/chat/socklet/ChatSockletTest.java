package org.openyu.mix.chat.socklet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.connector.vo.impl.JavaConnectorImpl;
import org.openyu.socklet.message.vo.Message;

public class ChatSockletTest extends ChatTestSupporter
{

	@Test
	public void javaConnector2()
	{
		// --------------------------------------------------
		//client 2
		//client -> slave2:4120
		// --------------------------------------------------
		JavaConnectorImpl javaConnector2 = new JavaConnectorImpl("TEST_ROLE_2", CoreModuleType.class,
			CoreMessageType.class, protocolService, "localhost", 4120);
		javaConnector2.setReceiver(new MessageReceiver());
		javaConnector2.start();
		//
		ThreadHelper.loop(50);
	}

	@Test
	public void javaConnector3()
	{
		// --------------------------------------------------
		//client 3
		//client -> slave3:4130
		// --------------------------------------------------
		JavaConnectorImpl javaConnector3 = new JavaConnectorImpl("TEST_ROLE_3", CoreModuleType.class,
			CoreMessageType.class, protocolService, "localhost", 4130);
		javaConnector3.setReceiver(new MessageReceiver());
		javaConnector3.start();
		//
		ThreadHelper.loop(50);
	}

	@Test
	public void CHAT_SPEAK_REQUEST()
	{
		final String ROLE_ID = "TEST_ROLE";
		//
		Message message = messageService.createClient(ROLE_ID, CoreMessageType.CHAT_SPEAK_REQUEST);
		message.addString(ROLE_ID);
		message.addInt(7);//7=世界聊天,Chat.ChatType
		message.addString("Hello world");//聊天內容
		message.addString("<hr/>");//html
		//
		chatSocklet.service(message);
	}

	//--------------------------------------------------
	// 啟動slave1,模擬真正連線
	//--------------------------------------------------
	public static class AcceptorTest extends ChatTestSupporter
	{
		@Before
		public void setUp() throws Exception
		{
			final String ROLE_ID = "TEST_ROLE";
			//連線到slave1, localhost:4110
			javaConnector.setId(ROLE_ID);
			javaConnector.setIp("localhost");
			javaConnector.setPort(4110);
			javaConnector.start();
		}

		@After
		public void tearDown() throws Exception
		{
			ThreadHelper.sleep(3 * 1000);
		}

		@Test
		public void CHAT_SPEAK_REQUEST_WORLD()
		{
			Message message = messageService.createClient(javaConnector.getId(),
				CoreMessageType.CHAT_SPEAK_REQUEST);
			message.addString(javaConnector.getId());
			message.addInt(7);//7=世界聊天,Channel.ChannelType
			message.addString("Hello world");//聊天內容
			message.addString("<hr/>");//html
			//
			javaConnector.send(message);
		}

		@Test
		public void CHAT_SPEAK_REQUEST_LOCAL()
		{

			Message message = messageService.createClient(javaConnector.getId(),
				CoreMessageType.CHAT_SPEAK_REQUEST);
			message.addString(javaConnector.getId());
			message.addInt(6);//6=本地聊天,Channel.ChannelType
			message.addString("Hello world");//聊天內容
			message.addString("<hr/>");//html
			//
			javaConnector.send(message);
		}
	}
}