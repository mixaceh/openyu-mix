package org.openyu.mix.account;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.openyu.mix.account.aop.AccountChangeCoinInterceptor;
import org.openyu.mix.account.aop.AccountDecreaseCoinInterceptor;
import org.openyu.mix.account.aop.AccountIncreaseCoinInterceptor;
import org.openyu.mix.account.dao.AccountDao;
import org.openyu.mix.account.dao.AccountLogDao;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.socklet.AccountSocklet;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.mix.app.AppTestSupporter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
//
		"classpath:applicationContext-init.xml",//
		"classpath:META-INF/applicationContext-commons-core.xml",//
		"classpath:applicationContext-message.xml",//
		"classpath:applicationContext-database.xml",//
		"classpath:applicationContext-database-log.xml",//
		// "classpath:applicationContext-schedule.xml",// 排程
		"classpath:META-INF/applicationContext-sls.xml",//
		"classpath:org/openyu/mix/app/applicationContext-app.xml",//
		// biz
		"classpath:org/openyu/mix/account/applicationContext-account.xml",//
		// "classpath:org/openyu/mix/item/applicationContext-item.xml",//
		// "classpath:org/openyu/mix/role/applicationContext-role.xml",//
})
public class AccountTestSupporter2 extends AppTestSupporter {

	@Autowired
	protected static ApplicationContext applicationContext;

	@Autowired
	protected static AccountDao accountDao;

	/**
	 * 帳戶服務
	 */
	@Autowired
	protected static AccountService accountService;

	// log
	@Autowired
	protected static AccountLogDao accountLogDao;

	@Autowired
	protected static AccountLogService accountLogService;

	@Autowired
	protected static AccountSocklet accountSocklet;

	// @BeforeClass
	// public static void setUpBeforeClass() throws Exception {
	// applicationContext = new ClassPathXmlApplicationContext(new String[] {
	// "applicationContext-init.xml",//
	// "META-INF/applicationContext-commons-core.xml",//
	// "applicationContext-message.xml",//
	// "applicationContext-database.xml",//
	// "applicationContext-database-log.xml",//
	// // "applicationContext-schedule.xml",// 排程
	// "META-INF/applicationContext-sls.xml",//
	// "org/openyu/mix/app/applicationContext-app.xml",//
	// // biz
	// "org/openyu/mix/account/applicationContext-account.xml",//
	// // "org/openyu/mix/item/applicationContext-item.xml",//
	// // "org/openyu/mix/role/applicationContext-role.xml",//
	// });
	// // ---------------------------------------------------
	// initialize();
	// // ---------------------------------------------------
	// accountDao = (AccountDao) applicationContext.getBean("accountDao");
	// accountService = (AccountService) applicationContext
	// .getBean("accountService");
	// accountLogDao = (AccountLogDao) applicationContext
	// .getBean("accountLogDao");
	// accountLogService = (AccountLogService) applicationContext
	// .getBean("accountLogService");
	// accountSocklet = (AccountSocklet) applicationContext
	// .getBean("accountSocklet");
	// }

	// --------------------------------------------------

	public static class BeanTest extends AccountTestSupporter2 {

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
		public void accountServiceTxPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("accountServiceTxPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountServiceTxAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("accountServiceTxAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountIncreaseCoinAdvice() {
			AccountIncreaseCoinInterceptor bean = (AccountIncreaseCoinInterceptor) applicationContext
					.getBean("accountIncreaseCoinAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountIncreaseCoinPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("accountIncreaseCoinPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountIncreaseCoinAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("accountIncreaseCoinAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountDecreaseCoinAdvice() {
			AccountDecreaseCoinInterceptor bean = (AccountDecreaseCoinInterceptor) applicationContext
					.getBean("accountDecreaseCoinAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountDecreaseCoinPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("accountDecreaseCoinPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountDecreaseCoinAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("accountDecreaseCoinAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountChangeCoinAdvice() {
			AccountChangeCoinInterceptor bean = (AccountChangeCoinInterceptor) applicationContext
					.getBean("accountChangeCoinAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountChangeCoinPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("accountChangeCoinPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void accountChangeCoinAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("accountChangeCoinAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
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
		public void accountSocklet() {
			System.out.println(accountSocklet);
			assertNotNull(accountSocklet);
		}
	}

	/**
	 * 模擬帳戶
	 * 
	 * @return
	 */
	public static Account mockAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		final String ACCOUNT_NAME = "測試帳戶";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 * 
	 * @return
	 */
	public static Account mockAccount2() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_2";
		final String ACCOUNT_NAME = "測試帳戶_2";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 * 
	 * @return
	 */
	public static Account mockAccount3() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_3";
		final String ACCOUNT_NAME = "測試帳戶_3";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 * 
	 * @param accountId
	 * @return
	 */
	public static Account mockAccount(String accountId, String name) {
		// 會造成 circular reference,故不用此方式
		// Account result = accountService.createAccount(accountId, name);
		//
		Account result = new AccountImpl();
		result.setId(accountId);
		result.setName(name);
		result.setValid(true);
		result.setCoin(100);
		result.setAccuCoin(5000);// vip6
		return result;
	}

}
