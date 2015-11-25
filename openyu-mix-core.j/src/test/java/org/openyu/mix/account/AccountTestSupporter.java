package org.openyu.mix.account;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.dao.AccountDao;
import org.openyu.mix.account.dao.AccountLogDao;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.aop.AccountChangeCoinInterceptor;
import org.openyu.mix.account.service.aop.AccountDecreaseCoinInterceptor;
import org.openyu.mix.account.service.aop.AccountIncreaseCoinInterceptor;
import org.openyu.mix.account.service.socklet.AccountSocklet;
import org.openyu.mix.app.AppTestSupporter;

public class AccountTestSupporter extends AppTestSupporter {

	protected static AccountDao accountDao;

	/**
	 * 帳戶服務
	 */
	protected static AccountService accountService;

	// log
	protected static AccountLogDao accountLogDao;

	protected static AccountLogService accountLogService;

	protected static AccountSocklet accountSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-message.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-schedule.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				// "org/openyu/mix/item/applicationContext-item.xml",//
				// "org/openyu/mix/role/applicationContext-role.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		accountDao = (AccountDao) applicationContext.getBean("accountDao");
		accountService = (AccountService) applicationContext.getBean("accountService");
		accountLogDao = (AccountLogDao) applicationContext.getBean("accountLogDao");
		accountLogService = (AccountLogService) applicationContext.getBean("accountLogService");
		accountSocklet = (AccountSocklet) applicationContext.getBean("accountSocklet");
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
}
