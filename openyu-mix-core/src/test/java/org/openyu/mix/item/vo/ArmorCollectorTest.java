package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
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

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class ArmorCollectorTest extends BaseTestSupporter {

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
		ArmorCollector collector = ArmorCollector.getInstance(false);
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
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

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
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

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
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

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
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(200);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

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
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(200);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

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
			if (enhanceLevel.getValue() <= collector.getSafeEnhanceLevel().getValue()) {
				enhanceFactor.setProbability(10000);
			} else {
				// >=4
				enhanceFactor.setProbability(4000 - enhanceLevel.getValue() * 150);
			}
			//
			collector.getEnhanceFactors().put(enhanceFactor.getId(), enhanceFactor);
		}
		//
		result = CollectorHelper.writeToXml(ArmorCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		ArmorCollector result = null;
		//
		result = CollectorHelper.readFromXml(ArmorCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(ArmorCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		ArmorCollector result = null;
		//
		result = CollectorHelper.readFromSer(ArmorCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		ArmorCollector result = null;
		//
		result = ArmorCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		ArmorCollector instance = ArmorCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ArmorCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = ArmorCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		ArmorCollector instance = ArmorCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ArmorCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = ArmorCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 100)
	// round: 5.41 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.04, time.total: 5.42, time.warmup: 0.03,
	// time.bench: 5.39
	public void getArmors() {
		Map<String, Armor> result = null;
		//
		result = ArmorCollector.getInstance().getArmors();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

}
