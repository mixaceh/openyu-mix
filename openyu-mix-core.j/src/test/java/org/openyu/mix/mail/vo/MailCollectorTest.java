package org.openyu.mix.mail.vo;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class MailCollectorTest extends BaseTestSupporter {

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
		MailCollector collector = MailCollector.getInstance(false);
		//
		collector.setExpiredDay(14);
		collector.setSpendGold(1000L);
		//
		result = CollectorHelper.writeToXml(MailCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		MailCollector result = null;
		//
		result = CollectorHelper.readFromXml(MailCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(MailCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		MailCollector result = null;
		//
		result = CollectorHelper.readFromSer(MailCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 2, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		MailCollector result = null;
		//
		result = MailCollector.getInstance();
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		MailCollector instance = MailCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = MailCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = MailCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		MailCollector instance = MailCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = MailCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = MailCollector.restartInstance();
		assertNotNull(instance);
	}

}
