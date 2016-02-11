package org.openyu.mix.flutter.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeFactorImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class IndustryCollectorTest extends BaseTestSupporter {

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
		IndustryCollector collector = IndustryCollector.getInstance(false);
		//
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
		AttributeFactor attributeFactor = new AttributeFactorImpl(attributeType);
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
		result = CollectorHelper.writeToXml(IndustryCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		IndustryCollector result = null;
		//
		result = CollectorHelper.readFromXml(IndustryCollector.class);
		//
		System.out.println(result);
		System.out.println(result.getIndustrys());
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(IndustryCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		IndustryCollector result = null;
		//
		result = CollectorHelper.readFromSer(IndustryCollector.class);
		//
		System.out.println(result);
		System.out.println(result.getIndustrys());
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 2, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		IndustryCollector result = null;
		//
		result = IndustryCollector.getInstance();
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		IndustryCollector instance = IndustryCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = IndustryCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = IndustryCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		IndustryCollector instance = IndustryCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = IndustryCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = IndustryCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getRaceFactor() {
		Integer result = null;
		//
		result = IndustryCollector.getInstance().getRaceFactor(RaceType.RONG);
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getCareerFactor() {
		Integer result = null;
		//
		result = IndustryCollector.getInstance().getCareerFactor(CareerType.WARRIOR_1);
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getIndustrys() {
		Map<String, Industry> result = null;
		//
		result = IndustryCollector.getInstance().getIndustrys();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getIndustry() {
		Industry result = null;
		//
		result = IndustryCollector.getInstance().getIndustry(RaceType.RONG, CareerType.WARRIOR_1);
		//
		System.out.println(result);
		// 屬性群
		AttributeGroup attributeGroup = result.getAttributeGroup();
		//
		System.out.println(attributeGroup.getAttributes().size() + ", " + result);
		assertEquals(RaceType.RONG, result.getRaceType());
		assertEquals(CareerType.WARRIOR_1, result.getCareerType());
		//
		System.out.println(attributeGroup.getAttribute(AttributeType.STRENGTH));
		System.out.println(attributeGroup.getAttribute(AttributeType.MAX_HEALTH));
		System.out.println(attributeGroup.getAttribute(AttributeType.MAX_MANA));
		//
		result = IndustryCollector.getInstance().getIndustry(RaceType.YI, CareerType.ARCHER_1);
		System.out.println(result);
		assertEquals(RaceType.YI, result.getRaceType());
		assertEquals(CareerType.ARCHER_1, result.getCareerType());
		//
		result = IndustryCollector.getInstance().getIndustry("RONG_WARRIOR_1");
		System.out.println(attributeGroup.getAttributes().size() + ", " + result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getExp() {
		Long result = null;
		//
		result = IndustryCollector.getInstance().getExp(1);
		//
		System.out.println(result);

		result = IndustryCollector.getInstance().getExp(100);
		System.out.println(result);
	}
}
