package org.openyu.mix.manor.vo;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.vo.EnhanceFactor;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.LandImpl;
import org.openyu.mix.manor.vo.impl.SeedImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class ManorCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		ManorCollector collector = ManorCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
		// 說明
		collector
				.setDescription("莊園內有農場,隨著等級的提升,可以獲得更多的農場,農場內需開墾不同屬性的土地來耕種,隨著土地的強化等級提升,會減少作物的成長時間,大量提升產量,作物成熟後收割,會獲得各種不同的材料,打造裝備");
		collector
				.getDescriptions()
				.addName(
						Locale.SIMPLIFIED_CHINESE,
						"庄园内有农场,随着等级的提升,可以获得更多的农场,农场内需开垦不同属性的土地来耕种,随着土地的强化等级提升,会减少作物的成长时间,大量提升产量,作物成熟后收割,会获得各种不同的材料,打造装备");

		// 最高強化等級
		collector.setMaxEnhanceLevel(EnhanceLevel._30);
		// 安全強化等級
		collector.setSafeEnhanceLevel(EnhanceLevel._3);
		// 幻想強化等級
		collector.setFantasyEnhanceLevel(EnhanceLevel._15);

		// 開墾花費的金幣
		collector.setReclaimGold(250 * 10000L);
		// 休耕花費的金幣
		collector.setDisuseGold(50 * 10000L);

		// 澆水
		collector.setWaterItem("T_MANOR_WATER_G001");
		collector.setWaterCoin(2);
		collector.setWaterRate(1000);

		// 祈禱
		collector.setPrayItem("T_MANOR_PRAY_G001");
		collector.setPrayCoin(5);
		collector.setPrayRate(2000);

		// 加速
		collector.setSpeedItem("T_MANOR_SPEED_G001");
		collector.setSpeedCoin(15);

		// 復活
		collector.setReviveItem("T_MANOR_REVIVE_G001");
		collector.setReviveCoin(20);

		// 種子
		Seed seed = new SeedImpl("S_COTTON_G001");
		seed.setName("棉花種子");
		seed.addName(Locale.SIMPLIFIED_CHINESE, "棉花种子");
		seed.setDescription("收割後可獲得棉布,打造裝備的材料之一");
		seed.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"收割后可获得棉布，打造装备的材料之一");
		//
		seed.setLevelLimit(20);
		seed.setPrice(1 * 10000L);// 1w
		seed.setCoin(1);
		seed.setGrowMills(8 * 60 * 60 * 1000L);// 8hr
		seed.setReapMills(seed.getGrowMills());
		seed.getProducts().put("M_COTTON_G001", 1);
		collector.getSeeds().put(seed.getId(), seed);
		//
		seed = new SeedImpl("S_OAK_G001");
		seed.setName("橡樹種子");
		seed.addName(Locale.SIMPLIFIED_CHINESE, "橡树种子");
		seed.setDescription("收割後可獲得橡樹木板,打造裝備的材料之一");
		seed.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"收割后可获得橡树木板，打造装备的材料之一");
		//
		seed.setLevelLimit(20);
		seed.setPrice(1 * 10000L);// 1w
		seed.setCoin(1);
		seed.setGrowMills(12 * 60 * 60 * 1000L);// 12hr
		seed.setReapMills(seed.getGrowMills());
		seed.getProducts().put("M_OAK_G001", 1);
		collector.getSeeds().put(seed.getId(), seed);

		// //種子因子
		// for (String id : collector.getSeeds().keySet())
		// {
		// SeedFactor plantFactor = new SeedFactorImpl(id);
		// plantFactor.setPoint(1 + id.length());
		// plantFactor.setRate(1 + id.length() * 10);
		// plantFactor.setProbability(7000 - id.length() * 150);
		// //
		// collector.getSeedFactors().put(plantFactor.getId(), plantFactor);
		// }

		// 土地
		Land land = new LandImpl("L_TROPICS_G001");
		land.setName("熱帶地");
		land.addName(Locale.SIMPLIFIED_CHINESE, "热带地");
		land.setLevelLimit(20);
		land.setPrice(10000 * 10000L);// 1e
		land.setCoin(2000);
		land.getSeeds().add("S_COTTON_G001");
		land.getSeeds().add("S_OAK_G001");
		//
		AttributeGroup attributeGroup = land.getAttributeGroup();
		// 產量
		AttributeType attributeType = AttributeType.MANOR_OUTPUT;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(2);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 產量(暴擊)率,萬分位(2000)
		attributeType = AttributeType.MANOR_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(3000);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);

		// 產量(暴擊)產量率,萬分位(2000)
		attributeType = AttributeType.MANOR_CRITICAL_OUTPUT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(3000);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);

		// 成長速度,萬分位(2000)
		attributeType = AttributeType.MANOR_SPEED_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(1000);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);
		//
		land = new LandImpl("L_DESERT_G001");
		land.setName("沙漠地");
		land.addName(Locale.SIMPLIFIED_CHINESE, "沙漠地");
		land.setLevelLimit(20);
		land.setPrice(15000 * 10000L);// 1.5e
		land.setCoin(3000);
		//
		attributeGroup = land.getAttributeGroup();
		// 產量
		attributeType = AttributeType.MANOR_OUTPUT;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(1);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 產量(暴擊)率,萬分位(2000)
		attributeType = AttributeType.MANOR_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(4000);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);

		// 產量(暴擊)產量率,萬分位(2000)
		attributeType = AttributeType.MANOR_CRITICAL_OUTPUT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(4000);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);

		// 成長速度,萬分位(2000)
		attributeType = AttributeType.MANOR_SPEED_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(500);
		attributeGroup.addAttribute(attribute);
		System.out.println(land.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		collector.getLands().put(land.getId(), land);

		// //土地因子
		// for (String id : collector.getLands().keySet())
		// {
		// LandFactor landFactor = new LandFactorImpl(id);
		// landFactor.setPoint(3 + id.length());
		// landFactor.setRate(3 + id.length() * 10);
		// landFactor.setProbability(7000 - id.length() * 150);
		// //
		// collector.getLandFactors().put(landFactor.getId(), landFactor);
		// }

		// 強化因子
		for (EnhanceLevel enhanceLevel : EnhanceLevel.values()) {
			// 強化等級=0,沒強化因子
			if (enhanceLevel == EnhanceLevel._0) {
				continue;
			}
			//
			EnhanceFactor enhanceFactor = new EnhanceFactorImpl();
			enhanceFactor.setId(enhanceLevel);
			enhanceFactor.setPoint(1 + enhanceLevel.getValue());
			enhanceFactor.setRate(1 + enhanceLevel.getValue() * 10);
			// 強化等級<=3,機率為1
			if (enhanceLevel.getValue() <= collector.getSafeEnhanceLevel()
					.getValue()) {
				enhanceFactor.setProbability(10000);
			} else {
				// >=4
				enhanceFactor
						.setProbability(8000 - enhanceLevel.getValue() * 150);
			}
			//
			collector.getEnhanceFactors().put(enhanceFactor.getId(),
					enhanceFactor);
		}
		//
		result = collector.writeToXml(ManorCollector.class, collector);
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
		ManorCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(ManorCollector.class);
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
		result = beanCollector.writeToSerFromXml(ManorCollector.class);
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
		ManorCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(ManorCollector.class);
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
		ManorCollector collector = ManorCollector.getInstance();
		int count = 1;
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
		ManorCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ManorCollector.getInstance();
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
		ManorCollector collector = ManorCollector.getInstance();
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
		assertEquals(0, collector.getLands().size());
		assertEquals(0, collector.getSeeds().size());
	}
}
