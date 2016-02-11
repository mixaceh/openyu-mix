package org.openyu.mix.wuxing;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.aop.SasangAspect;
import org.openyu.mix.wuxing.aop.WuxingAspect;
import org.openyu.mix.wuxing.aop.WuxingPlayInterceptor;
import org.openyu.mix.wuxing.aop.WuxingPutAllInterceptor;
import org.openyu.mix.wuxing.aop.WuxingPutOneInterceptor;
import org.openyu.mix.wuxing.dao.WuxingLogDao;
import org.openyu.mix.wuxing.service.WuxingLogService;
import org.openyu.mix.wuxing.service.WuxingMachine;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.service.adapter.WuxingChangeAdapter;
import org.openyu.mix.wuxing.socklet.WuxingSocklet;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.WuxingCollector;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.wuxing.vo.impl.WuxingPenImpl;

public class WuxingTestSupporter extends AppTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	protected static WuxingCollector wuxingCollector = WuxingCollector.getInstance();

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
	protected static WuxingMachine wuxingMachine;

	protected static WuxingService wuxingService;

	// log
	protected static WuxingLogDao wuxingLogDao;

	protected static WuxingLogService wuxingLogService;

	protected static WuxingAspect wuxingAspect;

	//
	protected static WuxingSocklet wuxingSocklet;

	// 事件監聽器
	protected static WuxingChangeAdapter wuxingChangeAdapter;

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
				"org/openyu/mix/account/applicationContext-account.xml", //
				"org/openyu/mix/item/applicationContext-item.xml", //
				"org/openyu/mix/role/applicationContext-role.xml", //
				"org/openyu/mix/wuxing/applicationContext-wuxing.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		wuxingMachine = (WuxingMachine) applicationContext.getBean("wuxingMachine");
		wuxingService = (WuxingService) applicationContext.getBean("wuxingService");
		wuxingSocklet = (WuxingSocklet) applicationContext.getBean("wuxingSocklet");
		//
		wuxingLogDao = (WuxingLogDao) applicationContext.getBean("wuxingLogDao");
		wuxingLogService = (WuxingLogService) applicationContext.getBean("wuxingLogService");
		wuxingChangeAdapter = (WuxingChangeAdapter) applicationContext.getBean("wuxingChangeAdapter");
	}

	public static class BeanTest extends WuxingTestSupporter {

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void wuxingMachine() {
			System.out.println(wuxingMachine);
			assertNotNull(wuxingMachine);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void wuxingService() {
			System.out.println(wuxingService);
			assertNotNull(wuxingService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void wuxingLogDao() {
			System.out.println(wuxingLogDao);
			assertNotNull(wuxingLogDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void wuxingLogService() {
			System.out.println(wuxingLogService);
			assertNotNull(wuxingLogService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void wuxingAspect() {
			System.out.println(wuxingAspect);
			assertNotNull(wuxingAspect);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
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
		Outcome outcome = wuxingMachine.createOutcome("13221", "34352", "42243");
		result.setOutcome(outcome);
		result.getAwards().put("T_ROLE_EXP_G001", 1);
		result.getAwards().put("T_ROLE_GOLD_G001", 5);
		result.getAwards().put("T_ROLE_FAME_G001", 10);
		return result;
	}
}
