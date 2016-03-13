package org.openyu.mix.train;

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
import org.openyu.mix.train.aop.TrainAspect;
import org.openyu.mix.train.dao.TrainLogDao;
import org.openyu.mix.train.service.TrainLogService;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.train.service.TrainRepository;
import org.openyu.mix.train.service.adapter.TrainChangeAdapter;
import org.openyu.mix.train.socklet.TrainSocklet;
import org.openyu.mix.train.vo.TrainCollector;

public class TrainTestSupporter extends AppTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	protected static TrainCollector trainCollector = TrainCollector.getInstance();

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	protected static TrainRepository trainRepository;

	protected static TrainService trainService;
	// log
	protected static TrainLogDao trainLogDao;

	protected static TrainLogService trainLogService;

	protected static TrainAspect trainAspect;

	protected static TrainSocklet trainSocklet;

	// 事件監聽器
	protected static TrainChangeAdapter trainChangeAdapter;

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
				"org/openyu/mix/train/applicationContext-train.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		//
		trainRepository = (TrainRepository) applicationContext.getBean("trainRepository");
		trainService = (TrainService) applicationContext.getBean("trainService");
		trainLogDao = (TrainLogDao) applicationContext.getBean("trainLogDao");
		trainLogService = (TrainLogService) applicationContext.getBean("trainLogService");
		trainAspect = (TrainAspect) applicationContext.getBean("trainAspect");
		//
		trainSocklet = (TrainSocklet) applicationContext.getBean("trainSocklet");
		//
		trainChangeAdapter = (TrainChangeAdapter) applicationContext.getBean("trainChangeAdapter");
	}

	public static class BeanTest extends TrainTestSupporter {

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainRepository() {
			System.out.println(trainRepository);
			assertNotNull(trainRepository);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainService() {
			System.out.println(trainService);
			assertNotNull(trainService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainLogDao() {
			System.out.println(trainLogDao);
			assertNotNull(trainLogDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainLogService() {
			System.out.println(trainLogService);
			assertNotNull(trainLogService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainAspect() {
			System.out.println(trainAspect);
			assertNotNull(trainAspect);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainSocklet() {
			System.out.println(trainSocklet);
			assertNotNull(trainSocklet);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void trainChangeAdapter() {
			System.out.println(trainChangeAdapter);
			assertNotNull(trainChangeAdapter);
		}
	}

}
