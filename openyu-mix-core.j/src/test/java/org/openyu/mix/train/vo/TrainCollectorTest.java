package org.openyu.mix.train.vo;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class TrainCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		TrainCollector collector = TrainCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
		// 說明
		collector
				.setDescription("在間斷的空閒時間，不需打怪或解任務，就能獲得經驗，每日利用特定的方式，更可以加倍獲得經驗。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"在间断的空闲时间，不需打怪或解任务，就能获得经验，每日利用特定的方式，更可以加倍获得经验。");
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
		for (int i = collector.getLevelLimit(); i <= IndustryCollector
				.getInstance().getMaxLevel(); i++) {
			long exp = i * 10L;
			//
			collector.getExps().put(i, exp);
			// 20級=4000w/2000=2w周期/3=6.6w分/1440=46.2天
			// 60級=3.6e/6000=6w周期/3=20w分/1440=138.9天
			// 100級=10e/1w=10w周期/3=33w分/1440=229天
		}
		//
		result = collector.writeToXml(TrainCollector.class, collector);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		TrainCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(TrainCollector.class);
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
		long beg = System.currentTimeMillis();
		//
		result = beanCollector.writeToSerFromXml(TrainCollector.class);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		TrainCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(TrainCollector.class);
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
		TrainCollector collector = TrainCollector.getInstance();
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		result = collector.isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(collector.getExps());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		TrainCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = TrainCollector.getInstance();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 10000 times: 1587 mills.
	// 10000 times: 1583 mills.
	// 10000 times: 1683 mills.
	public void reset() {
		TrainCollector collector = TrainCollector.getInstance();
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
		//
		assertEquals(0, collector.getExps().size());
	}

}
