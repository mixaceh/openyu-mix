package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.Race;
import org.openyu.mix.flutter.vo.RaceCollector;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.RaceImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class RaceCollectorTest extends BeanTestSupporter{

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		RaceCollector collector = RaceCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Race race = new RaceImpl();
			race.setId(RaceType.RONG);// 西戎
			AttributeGroup attributeGroup = race.getAttributeGroup();
			//
			AttributeType attributeType = AttributeType.STRENGTH;
			Attribute attribute = new AttributeImpl(attributeType);
			attribute.setPoint(60);// 初值
			attribute.setAddPoint(6);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.AGILITY;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(30);// 初值
			attribute.setAddPoint(3);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.INTELLIGENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.SPIRIT;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.CONSTITUTION;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(1000);// 初值
			attribute.setAddPoint(100);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(20);// 初值
			attribute.setAddPoint(2);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(20);// 初值
			attribute.setAddPoint(2);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_HEALTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(600);// 初值
			attribute.setAddPoint(60);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_MANA;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(300);// 初值
			attribute.setAddPoint(30);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			collector.getRaces().put(race.getId(), race);
			//
			race = new RaceImpl();
			race.setId(RaceType.YI);// 東夷
			attributeGroup = race.getAttributeGroup();
			//
			attributeType = AttributeType.STRENGTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(42);// 初值
			attribute.setAddPoint(4);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.AGILITY;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(84);// 初值
			attribute.setAddPoint(8);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.INTELLIGENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.SPIRIT;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.CONSTITUTION;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(40);// 初值
			attribute.setAddPoint(4);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(200);// 初值
			attribute.setAddPoint(20);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2000);// 初值
			attribute.setAddPoint(200);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(20);// 初值
			attribute.setAddPoint(2);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(20);// 初值
			attribute.setAddPoint(2);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_HEALTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(420);// 初值
			attribute.setAddPoint(42);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_MANA;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(510);// 初值
			attribute.setAddPoint(51);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(race.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			collector.getRaces().put(race.getId(), race);
			//
			result = collector.writeToXml(RaceCollector.class, collector);
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
		RaceCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(RaceCollector.class);
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
			result = beanCollector.writeToSerFromXml(RaceCollector.class);
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
		RaceCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(RaceCollector.class);
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
			RaceCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = RaceCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(RaceCollector.getInstance().getRaces());
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getRace() {
		Race result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = RaceCollector.getInstance().getRace(RaceType.YUAN);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(RaceType.YUAN, result.getId());

		result = RaceCollector.getInstance().getRace(RaceType.YI);
		System.out.println(result);
		//
		System.out.println(result.getAttributeGroup().getAttribute(
				AttributeType.STRENGTH));
	}
}
