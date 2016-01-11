package org.openyu.mix.sasang.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openyu.mix.sasang.SasangTestSupporter;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.Outcome.OutcomeType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.SystemHelper;

public class SasangMachineImplTest extends SasangTestSupporter {

	protected static SasangMachineImpl sasangMachineImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init-cglib.xml", //
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
		sasangMachineImpl = (SasangMachineImpl) applicationContext.getBean("sasangMachine");

		// 1."applicationContext-init.xml", //
		// 預設jdk動態代理: com.sun.proxy.$Proxy77

		// 2."applicationContext-init-cglib.xml", //
		// cglib動態代理:
		// org.openyu.mix.sasang.service.impl.SasangMachineImpl$$EnhancerBySpringCGLIB$$ce6bf11f

		// System.out.println(sasangMachineImpl.getClass().getName());
	}

	@Test
	// 1000000 times: 1542 mills.
	// 1000000 times: 1385 mills.
	// 1000000 times: 1611 mills.
	public void calcSasangTypeWeights() {
		boolean result = false;
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcSasangTypeWeights();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachineImpl.getSasangWeights());
		//
		int weight = sasangMachineImpl.getSasangTypeWeight(SasangType.AZURE_DRAGON, 0);
		System.out.println(weight);
		assertEquals(4, weight);
		//
		weight = sasangMachineImpl.getSasangTypeWeight(SasangType.VERMILION_BIRD, 2);
		System.out.println(weight);
		assertEquals(2, weight);
	}

	@Test
	// 1000000 times: 297 mills.
	// 1000000 times: 298 mills.
	// 1000000 times: 289 mills.
	public void calcRoundWeightSums() {
		boolean result = false;
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcRoundWeightSums();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachineImpl.getRoundWeightSums());
		//
		int sum = sasangMachineImpl.getRoundWeightSum(0);
		System.out.println(sum);// 64
		assertEquals(64, sum);
		//
		sum = sasangMachineImpl.getRoundWeightSum(1);
		System.out.println(sum);// 64
		assertEquals(64, sum);
		//
		sum = sasangMachineImpl.getRoundWeightSum(2);
		System.out.println(sum);// 64
		assertEquals(64, sum);
		//
		sum = sasangMachineImpl.getRoundWeightSum(3);
		System.out.println(sum);// 0
	}

	@Test
	public void calcSameThreeProbs() {
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcSameThreeProbs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// {AZURE_DRAGON=4.57764E-5, WHITE_TIGER=2.746582E-4,
		// VERMILION_BIRD=1.525879E-4, BLACK_TORTOISE=4.577637E-4,
		// YIN=8.010864E-4, YANG=0.0010986328, NOTHING=0.1659851074}
		System.out.println(result);
		System.out.println(sasangMachineImpl.getSameThreeProbs());
		//
		double prob = sasangMachineImpl.getSameThreeProb(SasangType.AZURE_DRAGON);// 4.57764E-5,1/10w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(4.57764E-5), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameThreeProb(SasangType.WHITE_TIGER);// 2.746582E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(2.746582E-4), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameThreeProb(SasangType.VERMILION_BIRD);// 1.525879E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(1.525879E-4), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameThreeProb(SasangType.BLACK_TORTOISE);// 4.577637E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(4.577637E-4), Double.doubleToLongBits(prob));
	}

	@Test
	public void calcSameTwoProbs() {
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcSameTwoProbs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// {AZURE_DRAGON=0.0045013428, WHITE_TIGER=0.0123596191,
		// VERMILION_BIRD=0.0088195801, BLACK_TORTOISE=0.0166931152,
		// YIN=0.0237197876, YANG=0.0289306641, NOTHING=0.4214782715}
		System.out.println(result);
		System.out.println(sasangMachineImpl.getSameTwoProbs());
		//
		double prob = sasangMachineImpl.getSameTwoProb(SasangType.AZURE_DRAGON);// 0.0045013428
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0045013428), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameTwoProb(SasangType.WHITE_TIGER);// 0.0123596191
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0123596191), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameTwoProb(SasangType.VERMILION_BIRD);// 0.0088195801
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0088195801), Double.doubleToLongBits(prob));
		//
		prob = sasangMachineImpl.getSameTwoProb(SasangType.BLACK_TORTOISE);// 0.0166931152
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0166931152), Double.doubleToLongBits(prob));
	}

	@Test
	public void calcTotalProbSums() {
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcTotalProbSums();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		double[] probSums = sasangMachineImpl.getProbSums();
		SystemHelper.println(probSums);// 0.0028305054, 0.0950241089,
										// 0.9021453857

		double sum = 0d;
		for (double prob : probSums) {
			sum += prob;
		}
		System.out.println(sum);// 1.0
		assertEquals(Double.doubleToLongBits(1.0), Double.doubleToLongBits(sum));
	}

	//
	@Test
	// 1000000 times: 45 mills.
	// 1000000 times: 39 mills.
	// 1000000 times: 55 mills.
	public void startByType() {
		boolean result = false;
		int times = 0;
		int count = 1000000;// 100w
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.playByType(SasangType.AZURE_DRAGON, 3);// 3個相同青龍
			if (result) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);// 4.57764E-5, 46
		//
		times = 0;
		beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.playByType(SasangType.AZURE_DRAGON, 2);// 2個相同青龍
			if (result) {
				times += 1;
			}
		}
		//
		end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);// 0.0045013428, 4605
		//
		times = 0;
		beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.playByType(SasangType.WHITE_TIGER, 3);// 3個相同白虎
			if (result) {
				times += 1;
			}
		}
		//
		end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);// 2.746582E-4, 262
	}

	@Test
	public void buildOutcomes() {
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.buildOutcomes();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachineImpl.getOutcomes());

		int size = sasangMachineImpl.getOutcomes().size();
		System.out.println(size);
		assertEquals(343, size);// 3輪^7=343個
	}

	@Test
	public void calcOutcomesProbSum() {
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.calcOutcomesProbSum();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachineImpl.getOutcomesProbSum());// 1.0000000005
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.20 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.21, time.warmup: 0.00,
	// time.bench: 0.20
	public void play() {
		Outcome result = null;
		//
		int count3same = 0;
		int count2same = 0;
		int countStandAlone = 0;

		int count = 100;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.play();
			OutcomeType outcomeType = result.getOutcomeType();
			if (outcomeType == OutcomeType.SAME_THREE) {
				count3same += 1;
			} else if (outcomeType == OutcomeType.SAME_TWO) {
				count2same += 1;
			} else if (outcomeType == OutcomeType.STAND_ALONE) {
				countStandAlone += 1;
			} else {
				System.out.println(result.getOutcomeType());
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("3個相同總計: " + count3same);// 2867
		System.out.println("2個相同總計: " + count2same);// 94811
		System.out.println("安慰獎總計: " + countStandAlone);// 902322
		//
		System.out.println("中獎總計: " + (count3same + count2same + countStandAlone));
		//
		//
		result = sasangMachineImpl.play();
		System.out.println(result);
		assertNotNull(result);
		//
		result = sasangMachineImpl.play();
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.15 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.15, time.warmup: 0.00,
	// time.bench: 0.15
	public void playWithTimes() {
		List<Outcome> result = null;

		result = sasangMachineImpl.play(100);
		//
		System.out.println(result.size() + ", " + result);
		assertEquals(100, result.size());
		//
		result = sasangMachineImpl.play(10);
		System.out.println(result.size() + ", " + result);
		assertEquals(10, result.size());
	}

	@Test
	public void createOutcome() {
		Outcome result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = sasangMachineImpl.createOutcome("111");
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

}
