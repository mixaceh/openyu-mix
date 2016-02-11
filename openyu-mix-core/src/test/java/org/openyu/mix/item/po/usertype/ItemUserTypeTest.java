package org.openyu.mix.item.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.item.po.usertype.ItemUserType;
import org.openyu.mix.item.vo.ArmorCollector;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.MaterialCollector;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class ItemUserTypeTest extends BaseTestSupporter {

	private static ItemUserType userType = new ItemUserType();

	private static transient ThingCollector thingCollector = ThingCollector
			.getInstance();

	private static transient MaterialCollector materialCollector = MaterialCollector
			.getInstance();

	private static transient ArmorCollector armorCollector = ArmorCollector
			.getInstance();

	private static transient WeaponCollector weaponCollector = WeaponCollector
			.getInstance();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshalByThing() {
		Thing value = thingCollector.createThing("T_POTION_HP_G001");// 一般小紅水
		value.setUniqueId(Thing.UNIQUE_ID_PREFIX + "01234567890");
		value.setAmount(50);// 50個
		value.setTied(true);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("♥1♠T_POTION_HP_G001♦T_01234567890♦50♦1", result);
		//
		result = userType.assembleBy_1(value);
		System.out.println(result);
		assertEquals("T_POTION_HP_G001♦T_01234567890♦50♦1", result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshalByThing() {
		String value = "♥1♠T_POTION_HP_G001♦T_01234567890♦50♦1";
		//
		Item result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals("T_POTION_HP_G001", result.getId());
		assertEquals("T_01234567890", result.getUniqueId());
		assertEquals(50, result.getAmount());
		assertEquals(true, result.isTied());
	}

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshalByWeapon() {
		Weapon value = weaponCollector.createWeapon("W_MARS_SWORD_E001");// E
																			// 級單手劍
		value.setUniqueId(Weapon.UNIQUE_ID_PREFIX + "01234567890");
		value.setAmount(1);// 1個
		value.setEnhanceLevel(EnhanceLevel._7);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(
				"♥1♠W_MARS_SWORD_E001♦W_01234567890♦1♦0♦7♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0",
				result);
		//
		result = userType.assembleBy_1(value);
		System.out.println(result);
		assertEquals(
				"W_MARS_SWORD_E001♦W_01234567890♦1♦0♦7♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0",
				result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshalByWeapon() {
		String value = "♥1♠W_MARS_SWORD_E001♦W_01234567890♦1♦0♦7♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0";
		//
		Weapon result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals("W_MARS_SWORD_E001", result.getId());
		assertEquals("W_01234567890", result.getUniqueId());
		assertEquals(1, result.getAmount());
		assertEquals(false, result.isTied());
		assertEquals(EnhanceLevel._7, result.getEnhanceLevel());
		assertEquals(5, result.getAttributeGroup().getAttributes().size());
		//
		value = "♥1♠W_MARS_SWORD_E001♦W_01234567890♦1♦0♦7";
		result = userType.unmarshal(value, null, null);
		System.out.println(result);
		assertEquals(0, result.getAttributeGroup().getAttributes().size());
	}
}
