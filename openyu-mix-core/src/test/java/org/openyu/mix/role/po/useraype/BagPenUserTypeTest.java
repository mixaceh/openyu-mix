package org.openyu.mix.role.po.useraype;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.mix.role.po.useraype.BagPenUserType;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.BagPen.TabType;
import org.openyu.mix.role.vo.impl.BagPenImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class BagPenUserTypeTest extends BaseTestSupporter {

	private static BagPenUserType userType = new BagPenUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		BagPen bagPen = new BagPenImpl();
		// 道具,放第0頁
		Thing thing = ThingCollector.getInstance().createThing(
				"T_POTION_HP_G001");
		thing.setUniqueId("T_00");
		thing.setAmount(100);
		bagPen.addItem(0, 0, thing);

		// 道具,放第1頁
		thing = ThingCollector.getInstance().createThing("T_POTION_HP_G002");
		thing.setUniqueId("T_01");
		thing.setAmount(100);
		//第一頁解除鎖定
		bagPen.unLock(1);
		bagPen.addItem(1, 0, thing);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(bagPen, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 道具
		System.out.println(result);
		assertEquals(
				"♥1♠4♠0♠0♠1♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠1♠0♠10♠0♠0",
				result);

		// 武器
		Weapon weapon = WeaponCollector.getInstance().createWeapon(
				"W_MARS_SWORD_E001");
		weapon.setUniqueId("W_01");
		weapon.setAmount(1);
		weapon.setEnhanceLevel(EnhanceLevel._0);
		bagPen.addItem(0, 1, weapon);
		//
		result = userType.marshal(bagPen, null);
		// 武器
		System.out.println(result);
		assertEquals(
				"♥1♠4♠0♠0♠2♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠W_MARS_SWORD_E001♦W_01♦1♦0♦0♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠1♠0♠10♠0♠0",
				result);

		// 鎖定
		bagPen.lock(1);// 鎖定 TabType._1
		bagPen.lock(2);// 鎖定 TabType._2
		//
		result = userType.marshal(bagPen, null);
		System.out.println(result);
		assertEquals(
				"♥1♠4♠0♠0♠2♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠W_MARS_SWORD_E001♦W_01♦1♦0♦0♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0♠1♠1♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠1♠0♠10♠0♠0",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠4♠0♠0♠1♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠1♠0♠10♠0♠0";
		//
		BagPen result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("T_POTION_HP_G001", result.getItem(0, 0).getId());
		assertEquals("T_POTION_HP_G002", result.getItem(1, 0).getId());

		// 武器
		value = "♥1♠4♠0♠0♠2♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠W_MARS_SWORD_E001♦W_01♦1♦0♦0♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠0♠0♠10♠0♠0";
		result = userType.unmarshal(value, null, null);
		System.out.println(result);
		assertEquals("W_MARS_SWORD_E001", result.getItem(0, 1).getId());
		assertEquals(TabType.values().length, result.getTabSize());

		// 鎖定
		value = "♥1♠4♠0♠0♠2♠0♠T_POTION_HP_G001♦T_00♦100♦0♠1♠W_MARS_SWORD_E001♦W_01♦1♦0♦0♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0♠1♠1♠1♠0♠T_POTION_HP_G002♦T_01♦100♦0♠2♠1♠0♠10♠0♠0";
		result = userType.unmarshal(value, null, null);
		System.out.println(result);
		assertEquals("T_POTION_HP_G002", result.getItem(1, 0, true).getId());// 忽略鎖定
		assertEquals(TabType.values().length - 2, result.getTabSize());

	}
}
