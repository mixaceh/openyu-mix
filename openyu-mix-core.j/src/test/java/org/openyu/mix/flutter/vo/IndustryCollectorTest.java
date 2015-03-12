package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeFactorImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class IndustryCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		IndustryCollector collector = IndustryCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 最高等級
			collector.setMaxLevel(60);

			// 種族因子
			collector.getRaceFactors().put(RaceType.YUAN, 10000);
			collector.getRaceFactors().put(RaceType.RONG, 13000);
			collector.getRaceFactors().put(RaceType.YI, 13000);
			collector.getRaceFactors().put(RaceType.DI, 13000);
			collector.getRaceFactors().put(RaceType.MAN, 14000);

			// 職業因子
			int factor = 10000;
			for (CareerType careerType : CareerType.values()) {
				collector.getCareerFactors().put(careerType, factor);
				factor += 1000;
			}

			// 屬性因子
			AttributeType attributeType = AttributeType.PHYSICAL_ATTACK;
			AttributeFactor attributeFactor = new AttributeFactorImpl(
					attributeType);
			attributeFactor.setFactor(10000);
			attributeFactor.setFactor2(5000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			attributeFactor.setFactor2(5000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAGIC_ATTACK;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			attributeFactor.setFactor2(5000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAGIC_CRITICAL_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			attributeFactor.setFactor2(5000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAGIC_DEFENCE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAGIC_DODGE_RATE;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAX_HEALTH;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);
			//
			attributeType = AttributeType.MAX_MANA;
			attributeFactor = new AttributeFactorImpl(attributeType);
			attributeFactor.setFactor(10000);
			collector.getAttributeFactors().put(attributeType, attributeFactor);

			// 經驗表
			for (int j = 1; j <= collector.getMaxLevel(); j++) {
				long exp = (j * j) * 100 * 1000L;// 等級^2 *100(比率)*1000(一單位)
				collector.getExps().put(j, exp);
				// 1級=10w
				// 60級=3.6e
				// 100級=10e
			}
			//
			result = collector.writeToXml(IndustryCollector.class, collector);
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
		IndustryCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(IndustryCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		System.out.println(result.getIndustrys());
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = beanCollector.writeToSerFromXml(IndustryCollector.class);
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
		IndustryCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(IndustryCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		System.out.println(result.getIndustrys());
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
			IndustryCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = IndustryCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(IndustryCollector.getInstance().getRaceFactors());
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getRaceFactor() {
		Integer result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = IndustryCollector.getInstance().getRaceFactor(
					RaceType.RONG);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getCareerFactor() {
		Integer result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = IndustryCollector.getInstance().getCareerFactor(
					CareerType.WARRIOR_1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	// 1000000 times: 295 mills.
	// 1000000 times: 293 mills.
	// 1000000 times: 295 mills.
	public void getIndustrys() {
		Map<String, Industry> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = IndustryCollector.getInstance().getIndustrys();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getIndustry() {
		Industry result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = IndustryCollector.getInstance().getIndustry(RaceType.RONG,
					CareerType.WARRIOR_1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		// 屬性群
		AttributeGroup attributeGroup = result.getAttributeGroup();
		//
		System.out.println(attributeGroup.getAttributes().size() + ", "
				+ result);
		assertEquals(RaceType.RONG, result.getRaceType());
		assertEquals(CareerType.WARRIOR_1, result.getCareerType());
		//
		System.out.println(attributeGroup.getAttribute(AttributeType.STRENGTH));
		System.out.println(attributeGroup
				.getAttribute(AttributeType.MAX_HEALTH));
		System.out.println(attributeGroup.getAttribute(AttributeType.MAX_MANA));
		//
		result = IndustryCollector.getInstance().getIndustry(RaceType.YI,
				CareerType.ARCHER_1);
		System.out.println(result);
		assertEquals(RaceType.YI, result.getRaceType());
		assertEquals(CareerType.ARCHER_1, result.getCareerType());
		//
		result = IndustryCollector.getInstance().getIndustry("RONG_WARRIOR_1");
		System.out.println(attributeGroup.getAttributes().size() + ", "
				+ result);
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getExp() {
		Long result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = IndustryCollector.getInstance().getExp(1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);

		result = IndustryCollector.getInstance().getExp(100);
		System.out.println(result);
	}
}
