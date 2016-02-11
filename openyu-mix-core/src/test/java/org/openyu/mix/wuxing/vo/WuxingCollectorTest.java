package org.openyu.mix.wuxing.vo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.wuxing.vo.WuxingType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class WuxingCollectorTest extends BaseTestSupporter {

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
		WuxingCollector collector = WuxingCollector.getInstance(false);
		//
		// 說明
		collector.setDescription("金，木，水，火，土，相生相剋時，有機會獲得祝福的強化防具捲。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE, "金，木，水，火，土，相生相克时，有机会获得祝福的强化防具卷。");
		// 等級限制
		collector.setLevelLimit(25);
		// 玩一次所花費的金幣
		collector.setPlayGold(350 * 10000L);// 350w
		// 花費金幣,每日可玩的次數
		collector.setDailyTimes(10);

		// 玩一次所花費的道具,數量為1
		collector.setPlayItem("T_WUXING_PLAY_G001");
		// 玩一次所花費的儲值幣
		collector.setPlayCoin(70);

		// 獎勵,birth的獎勵
		Prize prize = new PrizeImpl("5");// 0.00032
		prize.setLevel(1);
		prize.getAwards().put("T_ENHANCE_ARMOR_A_G001", 1);
		prize.setFamous(true);
		collector.getBirthPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("4");// 0.0016
		prize.setLevel(2);
		prize.getAwards().put("T_ENHANCE_ARMOR_B_G001", 1);
		prize.setFamous(true);
		collector.getBirthPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("3");// 0.008
		prize.setLevel(3);
		prize.getAwards().put("T_ENHANCE_ARMOR_C_G001", 1);
		prize.setFamous(false);
		collector.getBirthPrizes().put(prize.getId(), prize);

		// 獎勵,tie的獎勵
		prize = new PrizeImpl("5");// 0.01024
		prize.setLevel(1);
		prize.getAwards().put("T_ENHANCE_ARMOR_C_G001", 1);
		prize.setFamous(true);
		collector.getTiePrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("4");// 0.0256
		prize.setLevel(2);
		prize.getAwards().put("T_ENHANCE_ARMOR_D_G001", 1);
		prize.setFamous(false);
		collector.getTiePrizes().put(prize.getId(), prize);

		// 獎勵,final的獎勵
		prize = new PrizeImpl("5");// 0.00032
		prize.setLevel(1);
		prize.getAwards().put("T_ENHANCE_ARMOR_A_B001", 1);
		prize.setFamous(true);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("4");// 0.0016
		prize.setLevel(2);
		prize.getAwards().put("T_ENHANCE_ARMOR_B_B001", 1);
		prize.setFamous(true);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("3");// 0.008
		prize.setLevel(3);
		prize.getAwards().put("T_ENHANCE_ARMOR_C_G001", 1);
		prize.setFamous(false);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("2");// 0.04
		prize.setLevel(4);
		prize.getAwards().put("T_ENHANCE_ARMOR_D_G001", 1);
		prize.setFamous(false);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("1");// 0.2
		prize.setLevel(5);
		prize.getAwards().put("T_ENHANCE_ARMOR_E_G001", 1);
		prize.setFamous(false);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("0");// 安慰獎
		prize.setLevel(6);
		prize.getAwards().put("T_ROLE_EXP_G001", 1);
		prize.setFamous(false);
		collector.getFinalPrizes().put(prize.getId(), prize);
		//
		result = CollectorHelper.writeToXml(WuxingCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		WuxingCollector result = null;
		//
		result = CollectorHelper.readFromXml(WuxingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(WuxingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		WuxingCollector result = null;
		//
		result = CollectorHelper.readFromSer(WuxingCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		WuxingCollector result = null;
		//
		result = WuxingCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWuxingTypes() {
		WuxingCollector collector = WuxingCollector.getInstance();
		//
		Set<WuxingType> result = null;
		//
		result = collector.getWuxingTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
