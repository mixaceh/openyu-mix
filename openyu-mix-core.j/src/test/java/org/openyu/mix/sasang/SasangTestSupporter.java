package org.openyu.mix.sasang;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.sasang.vo.impl.SasangPenImpl;
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
	 * <aop:aspectj-autoproxy/> SasangMachine sasangMachine
	 * 
	 * <aop:aspectj-autoproxy proxy-target-class="true" /> SasangMachineImpl
	 * sasangMachine
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

	protected static SasangAspect sasangAspect;

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
		sasangSocklet = (SasangSocklet) applicationContext.getBean("sasangSocklet");
		//
		sasangLogDao = (SasangLogDao) applicationContext.getBean("sasangLogDao");
		sasangLogService = (SasangLogService) applicationContext.getBean("sasangLogService");
		sasangChangeAdapter = (SasangChangeAdapter) applicationContext.getBean("sasangChangeAdapter");
		sasangAspect = (SasangAspect) applicationContext.getBean("sasangAspect");
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

		@Test
		public void sasangAspect() {
			System.out.println(sasangAspect);
			assertNotNull(sasangAspect);
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
