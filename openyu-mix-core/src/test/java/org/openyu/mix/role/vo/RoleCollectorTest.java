package org.openyu.mix.role.vo;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.security.SecurityType;
import org.openyu.commons.util.CompressType;
import org.openyu.commons.util.SerializeType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class RoleCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToXml() {
		RoleCollector collector = RoleCollector.getInstance(false);
		String result = null;
		//
		collector.setRetryNumber(3);
		collector.setRetryPauseMills(1 * 1000L);
		collector.setUnsaveDir("custom/role/unsave");
		collector.setListenMills(3 * 60 * 1000L);// 3分鐘
		//
		collector.getSerializeProcessor().setSerialize(true);
		collector.getSerializeProcessor().setSerializeType(SerializeType.FST);
		//
		collector.getSecurityProcessor().setSecurity(true);
		collector.getSecurityProcessor().setSecurityType(SecurityType.AES_ECB_PKCS5Padding);
		collector.getSecurityProcessor().setSecurityKey("Girls, LongTimeNoSee");
		//
		collector.getCompressProcessor().setCompress(true);
		collector.getCompressProcessor().setCompressType(CompressType.SNAPPY);
		//
		result = CollectorHelper.writeToXml(RoleCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		RoleCollector result = null;
		//
		result = CollectorHelper.readFromXml(RoleCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(RoleCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		RoleCollector result = null;
		//
		result = CollectorHelper.readFromSer(RoleCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 2, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		RoleCollector result = null;
		//
		result = RoleCollector.getInstance();
		//
		System.out.println(result);
		//
		System.out.println(RoleCollector.getInstance().getRetryNumber());
		System.out.println(RoleCollector.getInstance().getRetryPauseMills());
		System.out.println(RoleCollector.getInstance().getUnsaveDir());
		System.out.println(RoleCollector.getInstance().getListenMills());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		RoleCollector instance = RoleCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = RoleCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = RoleCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		RoleCollector instance = RoleCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = RoleCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = RoleCollector.restartInstance();
		assertNotNull(instance);
	}
}
