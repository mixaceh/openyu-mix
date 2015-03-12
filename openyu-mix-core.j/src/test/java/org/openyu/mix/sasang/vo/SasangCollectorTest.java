package org.openyu.mix.sasang.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.mix.sasang.vo.impl.SasangImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class SasangCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		SasangCollector collector = SasangCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();

		// 說明
		collector.setDescription("青龍，白虎，玄武，朱雀，轉生幻化，在各種不同的輪迴中，有機會獲得祝福的強化武器捲。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"青龙，白虎，玄武，朱雀，转生幻化，在各种不同的轮回中，有机会获得祝福的强化武器卷。");

		// 等級限制
		collector.setLevelLimit(20);
		// 玩一次所花費的金幣
		collector.setPlayGold(250 * 10000L);// 250w
		// 花費金幣,每日可玩的次數
		collector.setDailyTimes(10);

		// 玩一次所花費的道具,數量為1
		collector.setPlayItem("T_SASANG_PLAY_G001");
		// 玩一次所花費的儲值幣
		collector.setPlayCoin(50);

		// 四象元素
		Sasang sasang = new SasangImpl(SasangType.VERMILION_BIRD);
		List<Integer> weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(2);
		weights.add(1);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.WHITE_TIGER);
		weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(2);
		weights.add(2);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.BLACK_TORTOISE);
		weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(3);
		weights.add(2);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.YIN);
		weights = new LinkedList<Integer>();
		weights.add(4);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.YANG);
		weights = new LinkedList<Integer>();
		weights.add(4);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(5);
		weights.add(5);
		weights.add(8);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.AZURE_DRAGON);
		weights = new LinkedList<Integer>();
		weights.add(4);
		weights.add(3);
		weights.add(1);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(5);
		weights.add(5);
		weights.add(7);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.VERMILION_BIRD);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(2);
		weights.add(1);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.WHITE_TIGER);
		weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(2);
		weights.add(1);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.BLACK_TORTOISE);
		weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(2);
		weights.add(2);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.YIN);
		weights = new LinkedList<Integer>();
		weights.add(3);
		weights.add(2);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.YANG);
		weights = new LinkedList<Integer>();
		weights.add(4);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);
		//
		sasang = new SasangImpl(SasangType.NOTHING);
		weights = new LinkedList<Integer>();
		weights.add(2);
		weights.add(3);
		weights.add(3);
		sasang.setWeights(weights);
		collector.getSasangs().add(sasang);

		// 獎勵,放武捲
		Prize prize = new PrizeImpl("111");
		prize.setLevel(1);
		prize.getAwards().put("T_ENHANCE_WEAPON_A_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("222");
		prize.setLevel(2);
		prize.getAwards().put("T_ENHANCE_WEAPON_C_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("333");
		prize.setLevel(3);
		prize.getAwards().put("T_ENHANCE_WEAPON_B_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("444");
		prize.setLevel(4);
		prize.getAwards().put("T_ENHANCE_WEAPON_D_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("555");
		prize.setLevel(5);
		prize.getAwards().put("T_ENHANCE_WEAPON_E_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("666");
		prize.setLevel(6);
		prize.getAwards().put("T_ENHANCE_WEAPON_E_B001", 1);
		prize.setFamous(true);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("11");
		prize.setLevel(7);
		prize.getAwards().put("T_ENHANCE_WEAPON_A_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("22");
		prize.setLevel(8);
		prize.getAwards().put("T_ENHANCE_WEAPON_C_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("33");
		prize.setLevel(9);
		prize.getAwards().put("T_ENHANCE_WEAPON_B_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("44");
		prize.setLevel(10);
		prize.getAwards().put("T_ENHANCE_WEAPON_D_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("55");
		prize.setLevel(11);
		prize.getAwards().put("T_ENHANCE_WEAPON_E_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("66");
		prize.setLevel(12);
		prize.getAwards().put("T_ENHANCE_WEAPON_E_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		prize = new PrizeImpl("0");// 安慰獎
		prize.setLevel(13);
		prize.getAwards().put("T_ROLE_EXP_G001", 1);
		prize.setFamous(false);
		collector.getPrizes().put(prize.getId(), prize);
		//
		result = collector.writeToXml(SasangCollector.class, collector);
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
		SasangCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(SasangCollector.class);
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
		result = beanCollector.writeToSerFromXml(SasangCollector.class);
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
		SasangCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(SasangCollector.class);
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
		SasangCollector collector = SasangCollector.getInstance();
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = SasangCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(collector.getSasangs());
		System.out.println(collector.getSasang(SasangType.AZURE_DRAGON));
		//
		System.out.println(collector.getPrizes());
		System.out.println(collector.getPrize("111"));
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		SasangCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = SasangCollector.getInstance();
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
		SasangCollector collector = SasangCollector.getInstance();
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
		//
		assertEquals(0, collector.getSasangs().size());
		assertEquals(0, collector.getPrizes().size());
	}

	@Test
	// 1000000 times: 396 mills.
	// 1000000 times: 393 mills.
	// 1000000 times: 434 mills.
	// verified
	public void getSasangTypes() {
		SasangCollector collector = SasangCollector.getInstance();
		//
		Set<SasangType> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = collector.getSasangTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getSasangs() {
		SasangCollector collector = SasangCollector.getInstance();
		List<Sasang> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = collector.getSasangs();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getSasang() {
		SasangCollector collector = SasangCollector.getInstance();
		Sasang result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = collector.getSasang(SasangType.AZURE_DRAGON);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(SasangType.AZURE_DRAGON, result.getId());
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getPrizes() {
		SasangCollector collector = SasangCollector.getInstance();
		Map<String, Prize> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = collector.getPrizes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getPrize() {
		SasangCollector collector = SasangCollector.getInstance();
		Prize result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {

			result = collector.getPrize("111");// 111
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals("111", result.getId());// 111
	}
}
