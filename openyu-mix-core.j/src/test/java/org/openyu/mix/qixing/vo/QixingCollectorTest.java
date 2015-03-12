package org.openyu.mix.qixing.vo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.qixing.vo.QixingType;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class QixingCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		QixingCollector collector = QixingCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
		// 說明
		collector.setDescription("在夜空下出現閃亮的七星時，許下三個願望，有機會獲得憧憬技魂之力。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"在夜空下出现闪亮的七星时，许下三个愿望，有机会获得憧憬技魂之力。");
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
		result = collector.writeToXml(QixingCollector.class, collector);
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
		QixingCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(QixingCollector.class);
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
		result = beanCollector.writeToSerFromXml(QixingCollector.class);
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
		QixingCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(QixingCollector.class);
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
		QixingCollector collector = QixingCollector.getInstance();
		int count = 1000000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = QixingCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		QixingCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = QixingCollector.getInstance();
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
		QixingCollector collector = QixingCollector.getInstance();
		//
		int count = 10000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
	}

	@Test
	// 1000000 times: 396 mills.
	// 1000000 times: 393 mills.
	// 1000000 times: 434 mills.
	// verified
	public void getQixingTypes() {
		QixingCollector collector = QixingCollector.getInstance();
		//
		Set<QixingType> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = collector.getQixingTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
