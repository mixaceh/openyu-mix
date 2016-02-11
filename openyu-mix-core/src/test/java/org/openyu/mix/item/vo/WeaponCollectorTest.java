package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.openyu.mix.item.vo.WeaponType;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.mix.item.vo.impl.WeaponImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class WeaponCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		WeaponCollector collector = WeaponCollector.getInstance(false);
		//
		// 武器
		Weapon weapon = new WeaponImpl("W_MARS_SWORD_E001");// W_系列_使用_等級
		weapon.setName("E 級單手劍");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.SWORD);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.RIGHT_HAND);
		AttributeGroup attributeGroup = weapon.getAttributeGroup();
		// 物攻
		AttributeType attributeType = AttributeType.PHYSICAL_ATTACK;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(8000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		//
		collector.getWeapons().put(weapon.getId(), weapon);

		// 武器
		weapon = new WeaponImpl("W_MARS_HAMMER_E001");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setName("E 級單手槌");
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.HAMMER);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.RIGHT_HAND);

		// 物攻
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(40);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(4000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(4000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(9600);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(60);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 武器
		weapon = new WeaponImpl("W_MARS_HANDS_SWORD_E001");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setName("E 級雙手劍");
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.HANDS_SWORD);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.PAIR_HAND);
		attributeGroup = weapon.getAttributeGroup();

		// 物攻
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(75);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7500);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7500);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6400);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(40);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		collector.getWeapons().put(weapon.getId(), weapon);

		// 武器
		weapon = new WeaponImpl("W_MARS_HANDS_HAMMER_E001");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setName("E 級雙手槌");
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.HANDS_HAMMER);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.PAIR_HAND);
		attributeGroup = weapon.getAttributeGroup();

		// 物攻
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(60);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7200);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(45);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		collector.getWeapons().put(weapon.getId(), weapon);

		// 武器
		weapon = new WeaponImpl("W_MARS_TWO_SWORD_E001");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setName("E 級雙刀");
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.TWO_SWORD);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.PAIR_HAND);
		attributeGroup = weapon.getAttributeGroup();

		// 物攻
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(75);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7500);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7500);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6400);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(40);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		collector.getWeapons().put(weapon.getId(), weapon);

		// 武器
		weapon = new WeaponImpl("W_MARS_TWO_HAMMER_E001");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setName("E 級雙槌");
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.TWO_HAMMER);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.PAIR_HAND);
		attributeGroup = weapon.getAttributeGroup();

		// 物攻
		attributeType = AttributeType.PHYSICAL_ATTACK;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(60);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));
		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(6000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(7200);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(45);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", " + attributeGroup.getFinal(attributeType));

		collector.getWeapons().put(weapon.getId(), weapon);

		// 最高強化等級
		collector.setMaxEnhanceLevel(EnhanceLevel._30);
		// 安全強化等級
		collector.setSafeEnhanceLevel(EnhanceLevel._3);
		// 幻想強化等級
		collector.setFantasyEnhanceLevel(EnhanceLevel._15);

		// 強化因子
		for (EnhanceLevel enhanceLevel : EnhanceLevel.values()) {
			// 強化等級=0,沒強化因子
			if (enhanceLevel == EnhanceLevel._0) {
				continue;
			}
			//
			EnhanceFactor enhanceFactor = new EnhanceFactorImpl();
			enhanceFactor.setId(enhanceLevel);
			enhanceFactor.setPoint(5 + enhanceLevel.getValue());
			enhanceFactor.setRate(5 + enhanceLevel.getValue() * 10);
			// 強化等級<=3,機率為1
			if (enhanceLevel.getValue() <= collector.getSafeEnhanceLevel().getValue()) {
				enhanceFactor.setProbability(10000);
			} else {
				// >=4
				enhanceFactor.setProbability(7500 - enhanceLevel.getValue() * 100);
			}
			//
			collector.getEnhanceFactors().put(enhanceFactor.getId(), enhanceFactor);
		}
		//
		result = CollectorHelper.writeToXml(WeaponCollector.class, collector);
		//
		System.out.println(result);

		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		WeaponCollector result = null;
		//
		result = CollectorHelper.readFromXml(WeaponCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(WeaponCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		WeaponCollector result = null;
		//
		result = CollectorHelper.readFromSer(WeaponCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		WeaponCollector result = null;
		//
		result = WeaponCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
		//
		System.out.println(result.getSafeEnhanceLevel());
		System.out.println(result.getEnhanceFactors());
		System.out.println(result.getAccuEnhanceFactors());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		WeaponCollector instance = WeaponCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = WeaponCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = WeaponCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		WeaponCollector instance = WeaponCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = WeaponCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = WeaponCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWeaponTypes() {
		Set<WeaponType> result = null;
		//
		result = WeaponCollector.getInstance().getWeaponTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWeapons() {
		Map<String, Weapon> result = null;
		//
		result = WeaponCollector.getInstance().getWeapons();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWeapon() {
		Weapon result = null;
		//
		result = WeaponCollector.getInstance().getWeapon("W_MARS_SWORD_E001");
		//
		System.out.println(result);
		assertEquals("W_MARS_SWORD_E001", result.getId());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWeaponsByWeaponType() {
		List<Weapon> result = null;
		//
		result = WeaponCollector.getInstance().getWeapons(WeaponType.SWORD);
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getWeaponIds() {
		List<String> result = null;
		//
		result = WeaponCollector.getInstance().getWeaponIds();
		//
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getEnhanceFactor() {
		EnhanceFactor result = null;
		//
		result = WeaponCollector.getInstance().getEnhanceFactor(EnhanceLevel._1);
		//
		System.out.println(result);
		assertEquals(EnhanceLevel._1, result.getId());
		//
		result = WeaponCollector.getInstance().getEnhanceFactor(7);
		System.out.println(result);
		assertEquals(EnhanceLevel._7, result.getId());
	}
}
