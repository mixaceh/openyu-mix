package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.Career;
import org.openyu.mix.flutter.vo.CareerCollector;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.CareerImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class CareerCollectorTest extends BaseTestSupporter {

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
		CareerCollector collector = CareerCollector.getInstance(false);
		//
		Career career = new CareerImpl();
		career.setId(CareerType.WARRIOR_1);// 戰士1轉
		AttributeGroup attributeGroup = career.getAttributeGroup();
		//
		AttributeType attributeType = AttributeType.STRENGTH;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(30);// 初值
		attribute.setAddPoint(3);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.AGILITY;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(15);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.INTELLIGENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.SPIRIT;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.CONSTITUTION;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(30);// 初值
		attribute.setAddPoint(3);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(1000);// 初值
		attribute.setAddPoint(100);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_HEALTH;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(300);// 初值
		attribute.setAddPoint(30);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_MANA;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(150);// 初值
		attribute.setAddPoint(15);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
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
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.AGILITY;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(42);// 初值
		attribute.setAddPoint(4);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.INTELLIGENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.SPIRIT;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.CONSTITUTION;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(15);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(200);// 初值
		attribute.setAddPoint(20);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2000);// 初值
		attribute.setAddPoint(200);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_HEALTH;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(210);// 初值
		attribute.setAddPoint(21);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_MANA;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(255);// 初值
		attribute.setAddPoint(25);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(career.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		collector.getCareers().put(career.getId(), career);
		//
		result = CollectorHelper.writeToXml(CareerCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		CareerCollector result = null;
		//
		result = CollectorHelper.readFromXml(CareerCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(CareerCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		CareerCollector result = null;
		//
		result = CollectorHelper.readFromSer(CareerCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		CareerCollector instance = CareerCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = CareerCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = CareerCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		CareerCollector instance = CareerCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = CareerCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = CareerCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getCareer() {
		Career result = null;
		//
		result = CareerCollector.getInstance().getCareer(CareerType.WARRIOR_1);
		//
		System.out.println(result);
		assertEquals(CareerType.WARRIOR_1, result.getId());

		result = CareerCollector.getInstance().getCareer(CareerType.ARCHER_1);
		System.out.println(result);
		//
		System.out.println(result.getAttributeGroup().getAttribute(AttributeType.STRENGTH));
	}
}
