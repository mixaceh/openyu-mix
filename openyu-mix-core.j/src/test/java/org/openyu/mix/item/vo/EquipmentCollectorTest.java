package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class EquipmentCollectorTest extends BaseTestSupporter {

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
		EquipmentCollector collector = EquipmentCollector.getInstance(false);
		//
		result = CollectorHelper.writeToXml(EquipmentCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		EquipmentCollector result = null;
		//
		result = CollectorHelper.readFromXml(EquipmentCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(EquipmentCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		EquipmentCollector result = null;
		//
		result = CollectorHelper.readFromSer(EquipmentCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		EquipmentCollector result = null;
		//
		result = EquipmentCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		EquipmentCollector instance = EquipmentCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = EquipmentCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = EquipmentCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		EquipmentCollector instance = EquipmentCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = EquipmentCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = EquipmentCollector.restartInstance();
		assertNotNull(instance);
	}
}
