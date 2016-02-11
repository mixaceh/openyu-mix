package org.openyu.mix.qixing.vo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.qixing.vo.QixingType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class QixingCollectorTest extends BaseTestSupporter {

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
		QixingCollector collector = QixingCollector.getInstance(false);
		//
		// 說明
		collector.setDescription("在夜空下出現閃亮的七星時，許下三個願望，有機會獲得憧憬技魂之力。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE, "在夜空下出现闪亮的七星时，许下三个愿望，有机会获得憧憬技魂之力。");
		// 等級限制
		collector.setLevelLimit(25);
		// 玩一次所花費的金幣
		collector.setPlayGold(200 * 10000L);// 200w
		// 花費金幣,每日可玩的次數
		collector.setDailyTimes(10);

		// 玩一次所花費的道具,數量為1
		collector.setPlayItem("T_QIXING_PLAY_G001");
		// 玩一次所花費的儲值幣
		collector.setPlayCoin(40);

		// 獎勵
		Prize prize = new PrizeImpl("3");// 0.0286
		prize.setLevel(3);
		prize.getAwards().put("T_ROLE_SP_G003", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("2");// 0.3429
		prize.setLevel(4);
		prize.getAwards().put("T_ROLE_SP_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("1");// 安慰獎,0.5143
		prize.setLevel(5);
		prize.getAwards().put("T_ROLE_EXP_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("0");// 0.1143
		prize.setLevel(6);
		prize.getAwards().put("T_ROLE_SP_G002", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		result = CollectorHelper.writeToXml(QixingCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		QixingCollector result = null;
		//
		result = CollectorHelper.readFromXml(QixingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(QixingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		QixingCollector result = null;
		//
		result = CollectorHelper.readFromSer(QixingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		QixingCollector result = null;
		//
		result = QixingCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		QixingCollector instance = QixingCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = QixingCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = QixingCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		QixingCollector instance = QixingCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = QixingCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = QixingCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getQixingTypes() {
		QixingCollector collector = QixingCollector.getInstance();
		//
		Set<QixingType> result = null;
		//
		result = collector.getQixingTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
