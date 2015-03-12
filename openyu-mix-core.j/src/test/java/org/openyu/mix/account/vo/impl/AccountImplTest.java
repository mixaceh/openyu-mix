package org.openyu.mix.account.vo.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.account.vo.Account;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class AccountImplTest extends BeanTestSupporter {

	@Test
	public void writeToXml() {
		final String ID = "TEST_ACCOUNT";

		Account value = new AccountImpl();
		value.setId(ID);
		//
		value.setCoin(100);
		value.setAccuCoin(12345);
		//
		value.addName(Locale.TRADITIONAL_CHINESE, "測試帳號");
		value.addName(Locale.SIMPLIFIED_CHINESE, "测试帐号");
		value.addName(Locale.US, "Test Account");
		//
		String result = beanCollector.writeToXml(AccountImpl.class, value);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Account result = beanCollector.readFromXml(AccountImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
