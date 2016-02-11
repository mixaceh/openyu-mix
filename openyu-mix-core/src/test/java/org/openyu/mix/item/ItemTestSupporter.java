package org.openyu.mix.item;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.aop.ItemAspect;
import org.openyu.mix.item.dao.ItemLogDao;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.socklet.ItemSocklet;

public class ItemTestSupporter extends AppTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	/**
	 * 道具服務
	 */
	protected static ItemService itemService;

	protected static ItemLogDao itemLogDao;
	//
	protected static ItemLogService itemLogService;

	protected static ItemAspect itemAspect;

	protected static ItemSocklet itemSocklet;

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
				// "org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				// "org/openyu/mix/role/applicationContext-role.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		itemService = (ItemService) applicationContext.getBean("itemService");
		itemLogDao = (ItemLogDao) applicationContext.getBean("itemLogDao");
		itemLogService = (ItemLogService) applicationContext.getBean("itemLogService");
		itemAspect = (ItemAspect) applicationContext.getBean("itemAspect");
		itemSocklet = (ItemSocklet) applicationContext.getBean("itemSocklet");
	}

	// --------------------------------------------------

	public static class BeanTest extends ItemTestSupporter {

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void itemService() {
			System.out.println(itemService);
			assertNotNull(itemService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void itemLogDao() {
			System.out.println(itemLogDao);
			assertNotNull(itemLogDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void itemLogService() {
			System.out.println(itemLogService);
			assertNotNull(itemLogService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void itemAspect() {
			System.out.println(itemAspect);
			assertNotNull(itemAspect);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void itemSocklet() {
			System.out.println(itemSocklet);
			assertNotNull(itemSocklet);
		}
	}
}
