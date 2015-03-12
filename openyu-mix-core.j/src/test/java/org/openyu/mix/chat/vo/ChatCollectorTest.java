package org.openyu.mix.chat.vo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.commons.junit.supporter.BeanTestSupporter;
import org.openyu.commons.security.SecurityType;
import org.openyu.commons.util.CompressType;
import org.openyu.commons.util.SerializeType;

public class ChatCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		ChatCollector collector = ChatCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			collector.setRetryNumber(3);
			collector.setRetryPauseMills(1 * 1000L);
			collector.setUnsaveDir("custom/chat/unsave");
			collector.setListenMills(3 * 60 * 1000L + 10000L);// 3分鐘又10秒
			//
			//
			collector.getSerializeProcessor().setSerialize(true);
			collector.getSerializeProcessor().setSerializeType(
					SerializeType.FST);
			//
			collector.getSecurityProcessor().setSecurity(true);
			collector.getSecurityProcessor().setSecurityType(
					SecurityType.AES_ECB_PKCS5Padding);
			collector.getSecurityProcessor().setSecurityKey(
					"Girls, LongTimeNoSee");
			//
			collector.getCompressProcessor().setCompress(true);
			collector.getCompressProcessor().setCompressType(
					CompressType.SNAPPY);
			//
			result = collector.writeToXml(ChatCollector.class, collector);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		ChatCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(ChatCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = beanCollector.writeToSerFromXml(ChatCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		ChatCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(ChatCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 399 mills.
	// 1000000 times: 398 mills.
	// 1000000 times: 401 mills.
	public void initialize() {
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			ChatCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = ChatCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(ChatCollector.getInstance().getRetryNumber());
		System.out.println(ChatCollector.getInstance().getRetryPauseMills());
		System.out.println(ChatCollector.getInstance().getUnsaveDir());
		System.out.println(ChatCollector.getInstance().getListenMills());
	}

}
