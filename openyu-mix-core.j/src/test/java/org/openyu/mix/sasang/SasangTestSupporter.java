package org.openyu.mix.sasang;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.sasang.aop.SasangPlayInterceptor;
import org.openyu.mix.sasang.aop.SasangPutAllInterceptor;
import org.openyu.mix.sasang.aop.SasangPutOneInterceptor;
import org.openyu.mix.sasang.dao.SasangLogDao;
import org.openyu.mix.sasang.service.SasangLogService;
import org.openyu.mix.sasang.service.SasangMachine;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.sasang.service.adapter.SasangChangeAdapter;
import org.openyu.mix.sasang.socklet.SasangSocklet;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.SasangCollector;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.sasang.vo.impl.SasangPenImpl;
import org.openyu.mix.role.vo.Role;

public class SasangTestSupporter extends AppTestSupporter {

	protected static SasangCollector sasangCollector = SasangCollector
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
	 * 
	 * 改取interface
	 */
	protected static SasangMachine sasangMachine;

	protected static SasangService sasangService;

	//
	protected static SasangSocklet sasangSocklet;

	// log
	protected static SasangLogDao sasangLogDao;

	//
	protected static SasangLogService sasangLogService;

	// 事件監聽器
	protected static SasangChangeAdapter sasangChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {//
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-schedule.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/sasang/applicationContext-sasang.xml",//
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
		sasangMachine = (SasangMachine) applicationContext
				.getBean("sasangMachine");
		sasangService = (SasangService) applicationContext
				.getBean("sasangService");
		sasangSocklet = (SasangSocklet) applicationContext
				.getBean("sasangSocklet");
		//
		sasangLogDao = (SasangLogDao) applicationContext
				.getBean("sasangLogDao");
		sasangLogService = (SasangLogService) applicationContext
				.getBean("sasangLogService");
		sasangChangeAdapter = (SasangChangeAdapter) applicationContext
				.getBean("sasangChangeAdapter");
	}

	public static class BeanTest extends SasangTestSupporter {

		@Test
		public void sasangMachine() {
			System.out.println(sasangMachine);
			assertNotNull(sasangMachine);
		}

		@Test
		public void sasangService() {
			System.out.println(sasangService);
			assertNotNull(sasangService);
		}

		@Test
		public void sasangPlayAdvice() {
			SasangPlayInterceptor bean = (SasangPlayInterceptor) applicationContext
					.getBean("sasangPlayAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPlayPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("sasangPlayPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPlayAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("sasangPlayAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutOneAdvice() {
			SasangPutOneInterceptor bean = (SasangPutOneInterceptor) applicationContext
					.getBean("sasangPutOneAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutOnePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("sasangPutOnePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutOneAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("sasangPutOneAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutAllAdvice() {
			SasangPutAllInterceptor bean = (SasangPutAllInterceptor) applicationContext
					.getBean("sasangPutAllAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutAllPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("sasangPutAllPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangPutAllAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("sasangPutAllAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void sasangLogDao() {
			System.out.println(sasangLogDao);
			assertNotNull(sasangLogDao);
		}

		@Test
		public void sasangLogService() {
			System.out.println(sasangLogService);
			assertNotNull(sasangLogService);
		}

		@Test
		public void sasangSocklet() {
			System.out.println(sasangSocklet);
			assertNotNull(sasangSocklet);
		}

		@Test
		public void sasangChangeAdapter() {
			System.out.println(sasangChangeAdapter);
			assertNotNull(sasangChangeAdapter);
		}
	}

	/**
	 * 模擬四象欄位
	 * 
	 * @param role
	 */
	public static SasangPen mockSasangPen(Role role) {
		SasangPen result = new SasangPenImpl(role);
		//
		result.setPlayTime(System.currentTimeMillis());
		result.setDailyTimes(1);
		Outcome outcome = sasangMachine.createOutcome("111");
		result.setOutcome(outcome);
		result.getAwards().put("T_ROLE_EXP_G001", 1);
		result.getAwards().put("T_ROLE_GOLD_G001", 5);
		result.getAwards().put("T_ROLE_FAME_G001", 10);
		return result;
	}
}
