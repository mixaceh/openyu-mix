package org.openyu.mix.vip.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.openyu.mix.manor.vo.ManorPen.FarmType;
import org.openyu.mix.role.vo.BagPen.TabType;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class VipCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		VipCollector collector = VipCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
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
		result = collector.writeToXml(VipCollector.class, collector);
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
		VipCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(VipCollector.class);
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
		result = beanCollector.writeToSerFromXml(VipCollector.class);
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
		VipCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(VipCollector.class);
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
			VipCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = VipCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(VipCollector.getInstance().getBagTabTypes());
		//
		System.out.println(VipCollector.getInstance().getSasangCoinVipType());
		System.out.println(VipCollector.getInstance().getManorCoinVipType());
		//
		System.out.println(VipCollector.getInstance().getManorFarmTypes());
	}

	@Test
	// 1000000 times: 268 mills.
	// 1000000 times: 261 mills.
	// 1000000 times: 267 mills.
	public void getVipTypes() {
		Map<VipType, Integer> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = VipCollector.getInstance().getVipTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 268 mills.
	// 1000000 times: 261 mills.
	// 1000000 times: 267 mills.
	public void getVipType() {
		VipType result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = VipCollector.getInstance().getVipType(11);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
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
	// 1000000 times: 730 mills.
	public void getMaxVipType() {
		VipType result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = VipCollector.getInstance().getMaxVipType();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

}
