package org.openyu.mix.manor.po.userType;

import static org.junit.Assert.*;

import org.junit.Test;

import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.ManorPen.Farm;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.ManorPenImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class ManorPenUserTypeTest extends BaseTestSupporter {

	private static ManorPenUserType userType = new ManorPenUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		ManorPen manorPen = new ManorPenImpl();
		// 種子,放第0頁
		Seed seed = ManorCollector.getInstance().createSeed("S_COTTON_G001");
		seed.setUniqueId("S_00");
		seed.setAmount(1);
		// manorPen.addSeed(0, 0, seed);
		seed.setPlantTime(180000);
		seed.setWaterTime(210000);
		seed.setMatureType(MatureType.GROWING);
		manorPen.getFarm(0).getSeeds().put(0, seed);

		// 農場0,有土地
		Farm farm = manorPen.getFarm(0);
		Land land = ManorCollector.getInstance().createLand("L_TROPICS_G001");
		land.setUniqueId("L_00");
		land.setEnhanceLevel(EnhanceLevel._7);
		land.setAmount(1);
		farm.setLand(land);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(manorPen, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 種子
		System.out.println(result);
		assertEquals(
				"♥1♠3♠0♠0♠1♠L_TROPICS_G001♦L_00♦1♦0♦7♦4♦201♣2♣0♣0♦202♣3000♣0♣0♦203♣3000♣0♣0♦204♣1000♣0♣0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠1♠0♠0♠2♠1♠0♠0",
				result);

		// 種子,放第1頁
		manorPen.unLock(1);
		seed = ManorCollector.getInstance().createSeed("S_OAK_G001");
		seed.setUniqueId("T_01");
		seed.setAmount(1);
		// manorPen.addSeed(1, 0, seed);
		seed.setPlantTime(360000);
		seed.setWaterTime(420000);
		seed.setMatureTime(490000);
		seed.setMatureType(MatureType.MATURE);
		manorPen.getFarm(1).getSeeds().put(0, seed);

		//
		result = userType.marshal(manorPen, null);
		System.out.println(result);
		assertEquals(
				"♥1♠3♠0♠0♠1♠L_TROPICS_G001♦L_00♦1♦0♦7♦4♦201♣2♣0♣0♦202♣3000♣0♣0♦203♣3000♣0♣0♦204♣1000♣0♣0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠0♠0♠1♠0♠S_OAK_G001♦T_01♦1♦0♦360000♦420000♦490000♦3♠2♠1♠0♠0",
				result);

		// 移除土地
		farm = manorPen.getFarm(0);
		farm.setLand(null);
		result = userType.marshal(manorPen, null);
		System.out.println(result);
		assertEquals(
				"♥1♠3♠0♠0♠0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠0♠0♠1♠0♠S_OAK_G001♦T_01♦1♦0♦360000♦420000♦490000♦3♠2♠1♠0♠0",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠3♠0♠0♠1♠L_TROPICS_G001♦L_00♦1♦0♦7♦4♦201♣2♣0♣0♦202♣3000♣0♣0♦203♣3000♣0♣0♦204♣1000♣0♣0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠1♠0♠0♠2♠1♠0♠0";
		//
		ManorPen result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 土地
		Land land = result.getFarm(0).getLand();
		assertEquals("L_TROPICS_G001", land.getId());
		assertEquals(EnhanceLevel._7, land.getEnhanceLevel());
		assertEquals(4, land.getAttributeGroup().getAttributes().size());

		// 種子
		Seed seed = result.getSeed(0, 0);
		assertEquals("S_COTTON_G001", seed.getId());
		assertEquals(180000, seed.getPlantTime());

		//
		value = "♥1♠3♠0♠0♠1♠L_TROPICS_G001♦L_00♦1♦0♦7♦4♦201♣2♣0♣0♦202♣3000♣0♣0♦203♣3000♣0♣0♦204♣1000♣0♣0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠0♠0♠1♠0♠S_OAK_G001♦T_01♦1♦0♦360000♦420000♦490000♦3♠2♠1♠0♠0";
		result = userType.unmarshal(value, null, null);
		System.out.println(result);
		seed = result.getSeed(1, 0);
		assertEquals("S_OAK_G001", seed.getId());
		assertEquals(360000, seed.getPlantTime());
		assertEquals(2, result.getFarmSize());

		// 移除土地
		value = "♥1♠3♠0♠0♠0♠1♠0♠S_COTTON_G001♦S_00♦1♦0♦180000♦210000♦0♦2♠1♠0♠0♠1♠0♠S_OAK_G001♦T_01♦1♦0♦360000♦420000♦490000♦3♠2♠1♠0♠0";
		result = userType.unmarshal(value, null, null);
		System.out.println(result);
		land = result.getFarm(0).getLand();
		assertNull(land);
	}
}
