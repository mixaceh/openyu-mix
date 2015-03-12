package org.openyu.mix.login.service.socklet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.login.LoginTestSupporter;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class LoginSockletTest extends LoginTestSupporter
{

	@Test
	public void LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST()
	{
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String AUTH_KEY = "b91150d8b5608535e969b6c9a61fbb5c";
		final String IP = "10.0.0.1";
		//
		Message message = messageService.createMessage(CoreModuleType.ACCOUNT,
			CoreModuleType.LOGIN, CoreMessageType.LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST);
		message.addString(ACCOUNT_ID);
		message.addString(AUTH_KEY);
		message.addString(IP);
		loginSocklet.service(message);
	}

	//--------------------------------------------------
	// 啟動login,account,模擬真正連線
	//--------------------------------------------------
	public static class AcceptorTest extends LoginTestSupporter
	{
		@Before
		public void setUp() throws Exception
		{
			final String ACCOUNT_ID = "TEST_ACCOUNT_1";
			// 連線到login, localhost:4010
			javaConnector.setId(ACCOUNT_ID);
			javaConnector.setIp("localhost");
			javaConnector.setPort(4010);
			javaConnector.start();
		}

		@After
		public void tearDown() throws Exception
		{
			ThreadHelper.sleep(5 * 1000);
		}

		@Test
		public void LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST()
		{
			final String ACCOUNT_ID = "TEST_ACCOUNT_1";
			final String AUTH_KEY = "b91150d8b5608535e969b6c9a61fbb5c";
			final String IP = "10.0.0.1";
			//
			Message message = messageService.createMessage(CoreModuleType.ACCOUNT,
				CoreModuleType.LOGIN, CoreMessageType.LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST);
			message.setSender("account");// TODO sender要自己塞,麻煩
			message.addString(ACCOUNT_ID);
			message.addString(AUTH_KEY);
			message.addString(IP);
			//
			javaConnector.send(message);
		}
	}

}