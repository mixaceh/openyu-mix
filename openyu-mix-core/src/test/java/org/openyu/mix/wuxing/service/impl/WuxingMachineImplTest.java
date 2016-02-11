package org.openyu.mix.wuxing.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.wuxing.WuxingTestSupporter;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.Outcome.OutcomeType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import org.openyu.mix.wuxing.vo.WuxingType;

public class WuxingMachineImplTest extends WuxingTestSupporter {

	protected static WuxingMachineImpl wuxingMachineImpl;

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
		wuxingMachineImpl = (WuxingMachineImpl) applicationContext.getBean("wuxingMachine");

		// 1."applicationContext-init.xml", //
		// 預設jdk動態代理: com.sun.proxy.$Proxy77

		// 2."applicationContext-init-cglib.xml", //
		// cglib動態代理:
		// org.openyu.mix.sasang.service.impl.SasangMachineImpl$$EnhancerBySpringCGLIB$$ce6bf11f

		// System.out.println(sasangMachineImpl.getClass().getName());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void buildOutcomeTypes() {
		Map<WuxingType, Map<Integer, OutcomeType>> result = null;
		result = wuxingMachineImpl.buildOutcomeTypes();
		System.out.println(result);
		assertEquals(5, result.size());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void randomWuxingTypes() {
		List<WuxingType> result = null;

		result = wuxingMachineImpl.randomWuxingTypes();

		System.out.println(result);
		assertEquals(5, result.size());
		//
		result = wuxingMachineImpl.randomWuxingTypes();
		System.out.println(result);
		//
		result = wuxingMachineImpl.randomWuxingTypes();
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void play() {
		Outcome result = null;
		//
		result = wuxingMachineImpl.play();
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.16 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.16, time.warmup: 0.00,
	// time.bench: 0.16
	public void playWithTimes() {
		List<Outcome> result = null;

		result = wuxingMachineImpl.play(100);

		System.out.println(result.size() + ", " + result);
		assertEquals(100, result.size());
		//
		result = wuxingMachineImpl.play(10);
		System.out.println(result.size() + ", " + result);
		assertEquals(10, result.size());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void calcPrizes() {
		List<Prize> result = null;

		int count = 1;// 100w
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// @see org.openyu.mix.wuxing.vo.Outcome.OutcomeType
			result = wuxingMachineImpl.calcPrizes("14444");// 1勝4和
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		Prize prize = result.get(0);
		assertEquals(5, prize.getLevel());
		//
		prize = result.get(1);
		assertEquals(2, prize.getLevel());
	}

}
