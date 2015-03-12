package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.Career;
import org.openyu.mix.flutter.vo.CareerCollector;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.CareerImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class CareerCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		CareerCollector collector = CareerCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Career career = new CareerImpl();
			career.setId(CareerType.WARRIOR_1);// 戰士1轉
			AttributeGroup attributeGroup = career.getAttributeGroup();
			//
			AttributeType attributeType = AttributeType.STRENGTH;
			Attribute attribute = new AttributeImpl(attributeType);
			attribute.setPoint(30);// 初值
			attribute.setAddPoint(3);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.AGILITY;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(15);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.INTELLIGENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.SPIRIT;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.CONSTITUTION;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(30);// 初值
			attribute.setAddPoint(3);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(100);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(1000);// 初值
			attribute.setAddPoint(100);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(10);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(50);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_HEALTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(300);// 初值
			attribute.setAddPoint(30);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_MANA;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(150);// 初值
			attribute.setAddPoint(15);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			collector.getCareers().put(career.getId(), career);

			career = new CareerImpl();
			career.setId(CareerType.ARCHER_1);// 弓手1轉
			attributeGroup = career.getAttributeGroup();
			//
			attributeType = AttributeType.STRENGTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(21);// 初值
			attribute.setAddPoint(2);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.AGILITY;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(42);// 初值
			attribute.setAddPoint(4);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.INTELLIGENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.SPIRIT;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.CONSTITUTION;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(15);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(200);// 初值
			attribute.setAddPoint(20);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2000);// 初值
			attribute.setAddPoint(200);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.PHYSICAL_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_ATTACK;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(2);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(50);// 初值
			attribute.setAddPoint(10);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DEFENCE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAGIC_DODGE_RATE;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(5);// 初值
			attribute.setAddPoint(1);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_HEALTH;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(210);// 初值
			attribute.setAddPoint(21);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			attributeType = AttributeType.MAX_MANA;
			attribute = new AttributeImpl(attributeType);
			attribute.setPoint(255);// 初值
			attribute.setAddPoint(25);// 成長值
			attributeGroup.addAttribute(attribute);
			System.out.println(career.getId() + ", " + attributeType + ", "
					+ attributeGroup.getFinal(attributeType));
			//
			collector.getCareers().put(career.getId(), career);

			//
			result = collector.writeToXml(CareerCollector.class, collector);
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
		CareerCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(CareerCollector.class);
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
			result = beanCollector.writeToSerFromXml(CareerCollector.class);
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
		CareerCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(CareerCollector.class);
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
			CareerCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = CareerCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(CareerCollector.getInstance().getCareers());
	}

	@Test
	// 1000000 times: 657 mills.
	// 1000000 times: 650 mills.
	// 1000000 times: 469 mills.
	public void getCareer() {
		Career result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = CareerCollector.getInstance().getCareer(
					CareerType.WARRIOR_1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(CareerType.WARRIOR_1, result.getId());

		result = CareerCollector.getInstance().getCareer(CareerType.ARCHER_1);
		System.out.println(result);
		//
		System.out.println(result.getAttributeGroup().getAttribute(
				AttributeType.STRENGTH));
	}
}
