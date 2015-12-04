package org.openyu.mix.wuxing;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.service.aop.WuxingPlayInterceptor;
import org.openyu.mix.wuxing.service.aop.WuxingPutAllInterceptor;
import org.openyu.mix.wuxing.service.aop.WuxingPutOneInterceptor;
import org.openyu.mix.wuxing.dao.WuxingLogDao;
import org.openyu.mix.wuxing.service.WuxingLogService;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.service.adapter.WuxingChangeAdapter;
import org.openyu.mix.wuxing.service.impl.WuxingMachineImpl;
import org.openyu.mix.wuxing.service.socklet.WuxingSocklet;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.WuxingCollector;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.wuxing.vo.impl.WuxingPenImpl;

public class WuxingTestSupporter extends AppTestSupporter {
	protected static WuxingCollector wuxingCollector = WuxingCollector
			.getInstance();

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	/**
	 * 取實作class,所以不能用aop
	 */
	protected static WuxingMachineImpl wuxingMachine;

	protected static WuxingService wuxingService;

	protected static WuxingSocklet wuxingSocklet;

	// log
	protected static WuxingLogDao wuxingLogDao;

	//
	protected static WuxingLogService wuxingLogService;

	// 事件監聽器
	protected static WuxingChangeAdapter wuxingChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-message.xml",//
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-schedule.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/wuxing/applicationContext-wuxing.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext
				.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		wuxingMachine = (WuxingMachineImpl) applicationContext
				.getBean("wuxingMachine");
		wuxingService = (WuxingService) applicationContext
				.getBean("wuxingService");
		wuxingSocklet = (WuxingSocklet) applicationContext
				.getBean("wuxingSocklet");
		//
		wuxingLogDao = (WuxingLogDao) applicationContext
				.getBean("wuxingLogDao");
		wuxingLogService = (WuxingLogService) applicationContext
				.getBean("wuxingLogService");
		wuxingChangeAdapter = (WuxingChangeAdapter) applicationContext
				.getBean("wuxingChangeAdapter");
	}

	public static class BeanTest extends WuxingTestSupporter {

		@Test
		public void wuxingMachine() {
			System.out.println(wuxingMachine);
			assertNotNull(wuxingMachine);
		}

		@Test
		public void wuxingService() {
			System.out.println(wuxingService);
			assertNotNull(wuxingService);
		}

		@Test
		public void wuxingPlayAdvice() {
			WuxingPlayInterceptor bean = (WuxingPlayInterceptor) applicationContext
					.getBean("wuxingPlayAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPlayPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("wuxingPlayPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPlayAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("wuxingPlayAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutOneAdvice() {
			WuxingPutOneInterceptor bean = (WuxingPutOneInterceptor) applicationContext
					.getBean("wuxingPutOneAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutOnePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("wuxingPutOnePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutOneAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("wuxingPutOneAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutAllAdvice() {
			WuxingPutAllInterceptor bean = (WuxingPutAllInterceptor) applicationContext
					.getBean("wuxingPutAllAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutAllPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("wuxingPutAllPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingPutAllAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("wuxingPutAllAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void wuxingLogDao() {
			System.out.println(wuxingLogDao);
			assertNotNull(wuxingLogDao);
		}

		@Test
		public void wuxingLogService() {
			System.out.println(wuxingLogService);
			assertNotNull(wuxingLogService);
		}

		@Test
		public void wuxingSocklet() {
			System.out.println(wuxingSocklet);
			assertNotNull(wuxingSocklet);
		}
	}

	/**
	 * 模擬五行欄位
	 * 
	 * @param role
	 */
	public static WuxingPen mockWuxingPen(Role role) {
		WuxingPen result = new WuxingPenImpl(role);
		//
		result.setPlayTime(System.currentTimeMillis());
		result.setDailyTimes(1);
		Outcome outcome = wuxingMachine
				.createOutcome("13221", "34352", "42243");
		result.setOutcome(outcome);
		result.getAwards().put("T_ROLE_EXP_G001", 1);
		result.getAwards().put("T_ROLE_GOLD_G001", 5);
		result.getAwards().put("T_ROLE_FAME_G001", 10);
		return result;
	}
}
