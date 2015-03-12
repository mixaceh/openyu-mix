package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.SeriesType;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.impl.ArmorImpl;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class ArmorCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		ArmorCollector collector = ArmorCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 防具
			Armor armor = new ArmorImpl("A_MARS_BODY_HEAD_E001");// A_系列_部位_等級
			armor.setName("E 級頭盔");
			armor.setItemType(ItemType.ARMOR);
			armor.setSeriesType(SeriesType.MARS);
			armor.setPositionType(PositionType.BODY_HEAD);
			armor.setLevelType(LevelType.E);
			AttributeGroup attributeGroup = armor.getAttributeGroup();

			// 物防
			AttributeType attributeType = AttributeType.PHYSICAL_DEFENCE;
			Attribute attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			// 物防迴避率
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			collector.getArmors().put(armor.getId(), armor);

			// 防具
			armor = new ArmorImpl("A_MARS_BODY_HAND_E001");
			armor.setName("E 級護手");
			armor.setItemType(ItemType.ARMOR);
			armor.setSeriesType(SeriesType.MARS);
			armor.setPositionType(PositionType.BODY_HAND);
			armor.setLevelType(LevelType.E);
			attributeGroup = armor.getAttributeGroup();

			// 物防
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			// 物防迴避率
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			collector.getArmors().put(armor.getId(), armor);

			// 防具
			armor = new ArmorImpl("A_MARS_BODY_FOOT_E001");
			armor.setName("E 級靴子");
			armor.setItemType(ItemType.ARMOR);
			armor.setSeriesType(SeriesType.MARS);
			armor.setPositionType(PositionType.BODY_FOOT);
			armor.setLevelType(LevelType.E);
			attributeGroup = armor.getAttributeGroup();

			// 物防
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			// 物防迴避率
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			collector.getArmors().put(armor.getId(), armor);

			// 防具
			armor = new ArmorImpl("A_MARS_BODY_UPPER_E001");
			armor.setName("E 級胸甲");
			armor.setItemType(ItemType.ARMOR);
			armor.setSeriesType(SeriesType.MARS);
			armor.setPositionType(PositionType.BODY_UPPER);
			armor.setLevelType(LevelType.E);
			attributeGroup = armor.getAttributeGroup();

			// 物防
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			// 物防迴避率
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(200);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			collector.getArmors().put(armor.getId(), armor);

			// 防具
			armor = new ArmorImpl("A_MARS_BODY_LOWER_E001");
			armor.setName("E 級脛甲");
			armor.setItemType(ItemType.ARMOR);
			armor.setSeriesType(SeriesType.MARS);
			armor.setPositionType(PositionType.BODY_LOWER);
			armor.setLevelType(LevelType.E);
			attributeGroup = armor.getAttributeGroup();

			// 物防
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			// 物防迴避率
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(200);
			attributeGroup.addAttribute(attribute);
			System.out.println(armor.getName() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));

			collector.getArmors().put(armor.getId(), armor);

			// 最高強化等級
			collector.setMaxEnhanceLevel(EnhanceLevel._15);
			// 安全強化等級
			collector.setSafeEnhanceLevel(EnhanceLevel._3);
			// 幻想強化等級
			collector.setFantasyEnhanceLevel(EnhanceLevel._9);

			// 強化因子
			for (EnhanceLevel enhanceLevel : EnhanceLevel.values()) {
				// 強化等級=0,沒強化因子
				if (enhanceLevel == EnhanceLevel._0) {
					continue;
				}
				// 防具只到+15
				if (enhanceLevel.getValue() > EnhanceLevel._15.getValue()) {
					break;
				}
				//
				EnhanceFactor enhanceFactor = new EnhanceFactorImpl();
				enhanceFactor.setId(enhanceLevel);
				enhanceFactor.setPoint(2 + enhanceLevel.getValue());
				enhanceFactor.setRate(2 + enhanceLevel.getValue() * 10);
				// 強化等級<=3,機率為1
				if (enhanceLevel.getValue() <= collector.getSafeEnhanceLevel()
						.getValue()) {
					enhanceFactor.setProbability(10000);
				} else {
					// >=4
					enhanceFactor
							.setProbability(4000 - enhanceLevel.getValue() * 150);
				}
				//
				collector.getEnhanceFactors().put(enhanceFactor.getId(),
						enhanceFactor);
			}
			//
			result = collector.writeToXml(ArmorCollector.class, collector);
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
		ArmorCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(ArmorCollector.class);
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
			result = beanCollector.writeToSerFromXml(ArmorCollector.class);
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
		ArmorCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(ArmorCollector.class);
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
		ArmorCollector collector = ArmorCollector.getInstance();
		int count = 1000000;
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
		System.out.println(collector.getSafeEnhanceLevel());
		System.out.println(collector.getEnhanceFactors());
		System.out.println(collector.getAccuEnhanceFactors());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		ArmorCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ArmorCollector.getInstance();
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
		ArmorCollector collector = ArmorCollector.getInstance();
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
		assertEquals(0, collector.getArmors().size());
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getArmors() {
		Map<String, Armor> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ArmorCollector.getInstance().getArmors();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

}
