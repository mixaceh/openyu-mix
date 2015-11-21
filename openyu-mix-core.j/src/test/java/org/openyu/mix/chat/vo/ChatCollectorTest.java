package org.openyu.mix.chat.vo;

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

public class ChatCollectorTest extends BaseTestSupporter {

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
		ChatCollector collector = ChatCollector.getInstance(false);
		//
		collector.setRetryNumber(3);
		collector.setRetryPauseMills(1 * 1000L);
		collector.setUnsaveDir("custom/chat/unsave");
		collector.setListenMills(3 * 60 * 1000L + 10000L);// 3分鐘又10秒
		//
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
		result = CollectorHelper.writeToXml(ChatCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		ChatCollector result = null;
		//
		result = CollectorHelper.readFromXml(ChatCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(ChatCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		ChatCollector result = null;
		//
		result = CollectorHelper.readFromSer(ChatCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 2, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		ChatCollector result = null;
		//
		result = ChatCollector.getInstance();
		//
		System.out.println(result);
		//
		System.out.println(ChatCollector.getInstance().getRetryNumber());
		System.out.println(ChatCollector.getInstance().getRetryPauseMills());
		System.out.println(ChatCollector.getInstance().getUnsaveDir());
		System.out.println(ChatCollector.getInstance().getListenMills());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		ChatCollector instance = ChatCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ChatCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = ChatCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		ChatCollector instance = ChatCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ChatCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = ChatCollector.restartInstance();
		assertNotNull(instance);
	}
}
