package org.openyu.mix.account.service.socklet;

import javax.crypto.SecretKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openyu.mix.account.AccountTestSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.commons.lang.EncodingHelper;
import org.openyu.commons.security.SecurityHelper;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class AccountSockletTest extends AccountTestSupporter {

	@Test
	public void ACCOUNT_AUTHORIZE_REQUEST() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String ALGORITHM = "MD5";
		// 048f4d8d4bc291ba442363d65d51ad29
		byte[] buff = SecurityHelper.md("1111", ALGORITHM);
		final String PASSWORD = EncodingHelper.encodeHex(buff);
		// System.out.println(PASSWORD);
		//
		Message message = messageService.createClient(ACCOUNT_ID,
				CoreMessageType.ACCOUNT_AUTHORIZE_REQUEST);
		message.addString(ACCOUNT_ID);
		message.addString(PASSWORD);
		accountSocklet.service(message);
	}

	// --------------------------------------------------
	// 啟動account,login,模擬真正連線
	// --------------------------------------------------
	public static class ConnectorTest extends AccountTestSupporter {
		@Before
		public void setUp() throws Exception {
			final String ACCOUNT_ID = "TEST_ACCOUNT_1";
			// 連線到account, localhost:4000
			javaConnector.setId(ACCOUNT_ID);
			javaConnector.setIp("localhost");
			javaConnector.setPort(4000);
			javaConnector.start();
		}

		@After
		public void tearDown() throws Exception {
			ThreadHelper.sleep(5 * 1000);
		}

		@Test
		public void ACCOUNT_AUTHORIZE_REQUEST() {
			final String ACCOUNT_ID = "TEST_ACCOUNT_1";
			final String ASSIGN_KEY = "FarFarAway";
			final String ALGORITHM = "HmacMD5";
			// b5f01d3a0898d8016b5633edfe6106b0
			SecretKey secretKey = SecurityHelper.createSecretKey(ASSIGN_KEY,
					ALGORITHM);
			byte[] buff = SecurityHelper.mac("1111", secretKey, ALGORITHM);
			final String PASSWORD = EncodingHelper.encodeHex(buff);
			System.out.println(PASSWORD);
			//
			Message message = messageService.createClient(ACCOUNT_ID,
					CoreMessageType.ACCOUNT_AUTHORIZE_REQUEST);
			message.addString(ACCOUNT_ID);
			message.addString(PASSWORD);
			//
			javaConnector.send(message);
		}
	}

}