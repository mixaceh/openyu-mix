package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.Race;
import org.openyu.mix.flutter.vo.RaceCollector;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.RaceImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class RaceCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		RaceCollector collector = RaceCollector.getInstance(false);
		//
		Race race = new RaceImpl();
		race.setId(RaceType.RONG);// 西戎
		AttributeGroup attributeGroup = race.getAttributeGroup();
		//
		AttributeType attributeType = AttributeType.STRENGTH;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(60);// 初值
		attribute.setAddPoint(6);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.AGILITY;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(30);// 初值
		attribute.setAddPoint(3);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.INTELLIGENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.SPIRIT;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.CONSTITUTION;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(1000);// 初值
		attribute.setAddPoint(100);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(20);// 初值
		attribute.setAddPoint(2);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(20);// 初值
		attribute.setAddPoint(2);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_HEALTH;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(600);// 初值
		attribute.setAddPoint(60);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_MANA;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(300);// 初值
		attribute.setAddPoint(30);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
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
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.AGILITY;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(84);// 初值
		attribute.setAddPoint(8);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.INTELLIGENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.SPIRIT;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.CONSTITUTION;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(40);// 初值
		attribute.setAddPoint(4);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(200);// 初值
		attribute.setAddPoint(20);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2000);// 初值
		attribute.setAddPoint(200);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(20);// 初值
		attribute.setAddPoint(2);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(20);// 初值
		attribute.setAddPoint(2);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);// 初值
		attribute.setAddPoint(10);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DEFENCE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAGIC_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(10);// 初值
		attribute.setAddPoint(1);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_HEALTH;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(420);// 初值
		attribute.setAddPoint(42);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		attributeType = AttributeType.MAX_MANA;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(510);// 初值
		attribute.setAddPoint(51);// 成長值
		attributeGroup.addAttribute(attribute);
		System.out.println(race.getId() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		//
		collector.getRaces().put(race.getId(), race);
		//
		result = CollectorHelper.writeToXml(RaceCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		RaceCollector result = null;
		//
		result = CollectorHelper.readFromXml(RaceCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(RaceCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		RaceCollector result = null;
		//
		result = CollectorHelper.readFromSer(RaceCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 2, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		RaceCollector result = null;
		//
		result = RaceCollector.getInstance();
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		RaceCollector instance = RaceCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = RaceCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = RaceCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		RaceCollector instance = RaceCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = RaceCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = RaceCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getRace() {
		Race result = null;
		//
		result = RaceCollector.getInstance().getRace(RaceType.YUAN);
		//
		System.out.println(result);
		assertEquals(RaceType.YUAN, result.getId());

		result = RaceCollector.getInstance().getRace(RaceType.YI);
		System.out.println(result);
		//
		System.out.println(result.getAttributeGroup().getAttribute(AttributeType.STRENGTH));
	}
}
