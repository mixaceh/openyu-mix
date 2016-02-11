package org.openyu.mix.train.vo;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.IndustryCollector;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class TrainCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToXml() {
		String result = null;
		TrainCollector collector = TrainCollector.getInstance(false);
		//
		// 說明
		collector.setDescription("在間斷的空閒時間，不需打怪或解任務，就能獲得經驗，每日利用特定的方式，更可以加倍獲得經驗。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE, "在间断的空闲时间，不需打怪或解任务，就能获得经验，每日利用特定的方式，更可以加倍获得经验。");
		// 等級限制
		collector.setLevelLimit(10);
		// 每日限制毫秒
		collector.setDailyMills(8 * 60 * 60 * 1000L);// 8hr
		// 間隔毫秒
		collector.setIntervalMills(20 * 1000L);// 秒

		// 鼓舞的道具,數量為1
		collector.setInspireItem("T_TRAIN_INSPIRE_G001");
		// 鼓舞的儲值幣
		collector.setInspireCoin(200);
		// 鼓舞效果,經驗增加比率,萬分位(2000)
		collector.setInspireRate(5000);

		// 每周期獲得的經驗
		for (int i = collector.getLevelLimit(); i <= IndustryCollector.getInstance().getMaxLevel(); i++) {
			long exp = i * 10L;
			//
			collector.getExps().put(i, exp);
			// 20級=4000w/2000=2w周期/3=6.6w分/1440=46.2天
			// 60級=3.6e/6000=6w周期/3=20w分/1440=138.9天
			// 100級=10e/1w=10w周期/3=33w分/1440=229天
		}
		//
		result = CollectorHelper.writeToXml(TrainCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		TrainCollector result = null;
		//
		result = CollectorHelper.readFromXml(TrainCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(TrainCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		TrainCollector result = null;
		//
		result = CollectorHelper.readFromSer(TrainCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		TrainCollector result = null;
		//
		result = TrainCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);

		System.out.println(result.getExps());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		TrainCollector instance = TrainCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = TrainCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = TrainCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		TrainCollector instance = TrainCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = TrainCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = TrainCollector.restartInstance();
		assertNotNull(instance);
	}
}
