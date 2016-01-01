package org.openyu.mix.account;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.aop.AccountAspect;
import org.openyu.mix.account.dao.AccountDao;
import org.openyu.mix.account.dao.AccountLogDao;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.socklet.AccountSocklet;
import org.openyu.mix.app.AppTestSupporter;

public class AccountTestSupporter extends AppTestSupporter {

	protected static AccountDao accountDao;

	protected static AccountService accountService;

	protected static AccountLogDao accountLogDao;

	protected static AccountLogService accountLogService;

	protected static AccountAspect accountAspect;

	protected static AccountSocklet accountSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-scheduler.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				// "org/openyu/mix/item/applicationContext-item.xml",//
				// "org/openyu/mix/role/applicationContext-role.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		accountDao = applicationContext.getBean("accountDao", AccountDao.class);
		accountService = applicationContext.getBean("accountService", AccountService.class);
		accountLogDao = applicationContext.getBean("accountLogDao", AccountLogDao.class);
		accountLogService = applicationContext.getBean("accountLogService", AccountLogService.class);
		accountAspect = applicationContext.getBean("accountAspect", AccountAspect.class);
		accountSocklet = applicationContext.getBean("accountSocklet", AccountSocklet.class);
	}

	// --------------------------------------------------

	public static class BeanTest extends AccountTestSupporter {

		@Test
		public void accountDao() {
			System.out.println(accountDao);
			assertNotNull(accountDao);
		}

		@Test
		public void accountService() {
			System.out.println(accountService);
			assertNotNull(accountService);
		}

		@Test
		public void accountLogDao() {
			System.out.println(accountLogDao);
			assertNotNull(accountLogDao);
		}

		@Test
		public void accountLogService() {
			System.out.println(accountLogService);
			assertNotNull(accountLogService);
		}

		@Test
		public void accountAspect() {
			System.out.println(accountAspect);
			assertNotNull(accountAspect);
		}

		@Test
		public void accountSocklet() {
			System.out.println(accountSocklet);
			assertNotNull(accountSocklet);
		}
	}
}
