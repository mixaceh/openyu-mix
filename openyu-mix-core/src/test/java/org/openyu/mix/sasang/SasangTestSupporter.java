package org.openyu.mix.sasang;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.sasang.aop.SasangAspect;
import org.openyu.mix.sasang.dao.SasangLogDao;
import org.openyu.mix.sasang.service.SasangLogService;
import org.openyu.mix.sasang.service.SasangMachine;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.sasang.service.adapter.SasangChangeAdapter;
import org.openyu.mix.sasang.socklet.SasangSocklet;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.SasangCollector;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.sasang.vo.impl.SasangInfoImpl;
import org.openyu.mix.role.vo.Role;

public class SasangTestSupporter extends AppTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	protected static SasangCollector sasangCollector = SasangCollector.getInstance();

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	/**
	 * <aop:aspectj-autoproxy/> SasangMachine
	 * 
	 * <aop:aspectj-autoproxy proxy-target-class="true" /> SasangMachineImpl
	 */
	protected static SasangMachine sasangMachine;

	protected static SasangService sasangService;
	// log
	protected static SasangLogDao sasangLogDao;

	protected static SasangLogService sasangLogService;

	protected static SasangAspect sasangAspect;

	//
	protected static SasangSocklet sasangSocklet;

	// 事件監聽器
	protected static SasangChangeAdapter sasangChangeAdapter;

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
				"org/openyu/mix/sasang/applicationContext-sasang.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		sasangMachine = (SasangMachine) applicationContext.getBean("sasangMachine");
		sasangService = (SasangService) applicationContext.getBean("sasangService");
		sasangLogDao = (SasangLogDao) applicationContext.getBean("sasangLogDao");
		sasangLogService = (SasangLogService) applicationContext.getBean("sasangLogService");
		sasangAspect = (SasangAspect) applicationContext.getBean("sasangAspect");
		sasangSocklet = (SasangSocklet) applicationContext.getBean("sasangSocklet");
		//
		sasangChangeAdapter = (SasangChangeAdapter) applicationContext.getBean("sasangChangeAdapter");
	}

	public static class BeanTest extends SasangTestSupporter {

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangMachine() {
			System.out.println(sasangMachine);
			assertNotNull(sasangMachine);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangService() {
			System.out.println(sasangService);
			assertNotNull(sasangService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangLogDao() {
			System.out.println(sasangLogDao);
			assertNotNull(sasangLogDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangLogService() {
			System.out.println(sasangLogService);
			assertNotNull(sasangLogService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangAspect() {
			System.out.println(sasangAspect);
			assertNotNull(sasangAspect);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void sasangSocklet() {
			System.out.println(sasangSocklet);
			assertNotNull(sasangSocklet);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
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
	public static SasangInfo mockSasangInfo(Role role) {
		SasangInfo result = new SasangInfoImpl(role);
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
