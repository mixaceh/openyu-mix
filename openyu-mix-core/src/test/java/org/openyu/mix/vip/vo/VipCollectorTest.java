package org.openyu.mix.vip.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.manor.vo.ManorInfo.FarmType;
import org.openyu.mix.role.vo.BagInfo.TabType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class VipCollectorTest extends BaseTestSupporter {

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
		VipCollector collector = VipCollector.getInstance(false);
		//
		// 包包頁應對的vip類型
		collector.getBagTabTypes().put(TabType._0, VipType._0);
		collector.getBagTabTypes().put(TabType._1, VipType._1);
		collector.getBagTabTypes().put(TabType._2, VipType._2);
		// 莊園農場頁應對的vip類型
		collector.getManorFarmTypes().put(FarmType._0, VipType._0);
		collector.getManorFarmTypes().put(FarmType._1, VipType._2);
		collector.getManorFarmTypes().put(FarmType._2, VipType._4);

		// collector.getManorFarmTypes().put(FarmType._3, VipType._6);
		// collector.getManorFarmTypes().put(FarmType._4, VipType._8);
		// collector.getManorFarmTypes().put(FarmType._5, VipType._10);
		collector.setSasangCoinVipType(VipType._2);
		collector.setManorCoinVipType(VipType._2);
		collector.setTreasureCoinVipType(VipType._2);
		collector.setTrainCoinVipType(VipType._2);
		collector.setWuxingCoinVipType(VipType._2);
		//
		result = CollectorHelper.writeToXml(VipCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		VipCollector result = null;
		//
		result = CollectorHelper.readFromXml(VipCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(VipCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		VipCollector result = null;
		//
		result = CollectorHelper.readFromSer(VipCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		VipCollector result = null;
		//
		result = VipCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
		//
		System.out.println(VipCollector.getInstance().getBagTabTypes());
		//
		System.out.println(VipCollector.getInstance().getSasangCoinVipType());
		System.out.println(VipCollector.getInstance().getManorCoinVipType());
		//
		System.out.println(VipCollector.getInstance().getManorFarmTypes());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		VipCollector instance = VipCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = VipCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = VipCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		VipCollector instance = VipCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = VipCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = VipCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getVipTypes() {
		Map<VipType, Integer> result = null;
		//
		result = VipCollector.getInstance().getVipTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getVipType() {
		VipType result = null;
		//
		result = VipCollector.getInstance().getVipType(11);
		//
		System.out.println(result);
		assertEquals(VipType._1, result);
		//
		result = VipCollector.getInstance().getVipType(101);
		System.out.println(result);
		assertEquals(VipType._2, result);

		result = VipCollector.getInstance().getVipType(0);
		System.out.println(result);
		assertEquals(VipType._0, result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getMaxVipType() {
		VipType result = null;
		//
		result = VipCollector.getInstance().getMaxVipType();
		//
		System.out.println(result);
	}
}
