package org.openyu.mix.item.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.ItemTestSupporter;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.ErrorType;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.EnhanceResult;
import org.openyu.mix.item.service.impl.ItemServiceImpl.EnhanceResultImpl;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.BagPenImplTest;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class ItemServiceImplTest extends ItemTestSupporter {

	/**
	 * 建構道具
	 */
	// 1000000 times: 15729 mills.
	// 1000000 times: 15489 mills.
	// 1000000 times: 16194 mills.
	@Test
	public void createItem() {
		final int AMOUNT = 5;
		Thing result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 物品
			result = itemService.createItem("T_POTION_HP_G001", AMOUNT);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		printItem(result);
		assertEquals(ItemType.THING, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());

		// 材料
		Material material = itemService.createItem("M_COTTON_G001", AMOUNT);
		printItem(material);

		assertEquals(ItemType.MATERIAL, material.getItemType());
		assertEquals(AMOUNT, material.getAmount());

		// 防具,數量=1,不可堆疊
		Armor armor = itemService.createItem("A_MARS_BODY_HEAD_E001");
		armor.setEnhanceLevel(EnhanceLevel._6);// +6
		printItem(armor);

		assertEquals(ItemType.ARMOR, armor.getItemType());
		assertEquals(1, armor.getAmount());

		// 武器,數量=1,不可堆疊
		Weapon weapon = itemService.createItem("W_MARS_SWORD_E001");
		weapon.setEnhanceLevel(EnhanceLevel._16);// +16
		printItem(weapon);

		assertEquals(ItemType.WEAPON, weapon.getItemType());
		assertEquals(1, weapon.getAmount());

		// 種子
		Seed seed = itemService.createItem("S_COTTON_G001", AMOUNT);
		printItem(seed);

		assertEquals(ItemType.SEED, seed.getItemType());
		assertEquals(AMOUNT, seed.getAmount());

		// 土地,數量=1,不可堆疊
		Land land = itemService.createItem("L_TROPICS_G001");
		land.setEnhanceLevel(EnhanceLevel._7);// +7
		printItem(land);

		assertEquals(ItemType.LAND, land.getItemType());
		assertEquals(1, land.getAmount());
	}

	/**
	 * 建構裝備
	 */
	// 1000000 times: 45254 mills.
	@Test
	public void createEquipment() {
		Armor result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 防具,數量=1,不可堆疊
			result = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		printItem(result);
		assertEquals(ItemType.ARMOR, result.getItemType());
		assertEquals(1, result.getAmount());

		// 武器,數量=1,不可堆疊
		Weapon weapon = itemService.createEquipment("W_MARS_SWORD_E001");
		printItem(weapon);

		assertEquals(ItemType.WEAPON, weapon.getItemType());
		assertEquals(1, weapon.getAmount());
	}

	/**
	 * 建構物品
	 */
	// 1000000 times: 16205 mills.
	@Test
	public void createThing() {
		final int AMOUNT = 5;
		Thing result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 物品
			result = itemService.createThing("T_POTION_HP_G001", AMOUNT);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.THING, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());
	}

	/**
	 * 建構材料
	 */
	// 1000000 times: 16205 mills.
	@Test
	public void createMaterial() {
		final int AMOUNT = 5;
		Material result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 材料
			result = itemService.createMaterial("M_COTTON_G001", AMOUNT);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.MATERIAL, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());
	}

	/**
	 * 建構防具
	 */
	// 1000000 times: 16205 mills.
	@Test
	public void createArmor() {
		final int AMOUNT = 1;
		Armor result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 防具
			result = itemService.createArmor("A_MARS_BODY_HEAD_E001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.ARMOR, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());
	}

	/**
	 * 建構武器
	 */
	// 1000000 times: 16205 mills.
	@Test
	public void createWeapon() {
		final int AMOUNT = 1;
		Weapon result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 武器
			result = itemService.createWeapon("W_MARS_SWORD_E001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.WEAPON, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());
	}

	/**
	 * 建構種子
	 */
	@Test
	public void createSeed() {
		final int AMOUNT = 5;
		Seed result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 種子
			result = itemService.createSeed("S_COTTON_G001", AMOUNT);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.SEED, result.getItemType());
		assertEquals(AMOUNT, result.getAmount());
	}

	/**
	 *  建構土地
	 */
	@Test
	public void createLand() {
		Land result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 土地,數量=1,不可堆疊
			result = itemService.createLand("L_TROPICS_G001");
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end + -beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
		assertEquals(ItemType.LAND, result.getItemType());
		assertEquals(1, result.getAmount());
	}

	@Test
	// 1000000 times: 18 mills.
	public void sendItem() {
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPen();
		role.setBagPen(bagPen);
		Item item = bagPen.getItem(0, 0);
		//
		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			itemService.sendItem(role, item.getUniqueId());
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	// 1000000 times: 434 mills.
	// 1000000 times: 437 mills.
	// 1000000 times: 421 mills.
	public void fillItem() {
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPen();
		role.setBagPen(bagPen);
		// 道具
		Item item = bagPen.getItem(0, 0);
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ROLE,
					CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
					role.getId());
			itemService.fillItem(result, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);

		// 材料
		item = itemService.createItem("M_COTTON_G001");
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
				role.getId());
		itemService.fillItem(result, item);
		System.out.println(result);

		// 防具
		item = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
				role.getId());
		itemService.fillItem(result, item);
		System.out.println(result);

		// 武器
		item = itemService.createEquipment("W_MARS_SWORD_E001");
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
				role.getId());
		itemService.fillItem(result, item);
		System.out.println(result);

		// 種子
		item = itemService.createSeed("S_COTTON_G001", 100);
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
				role.getId());
		itemService.fillItem(result, item);
		System.out.println(result);

		// 土地
		item = itemService.createLand("L_TROPICS_G001");
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, CoreMessageType.ITEM_GET_ITEM_RESPONSE,
				role.getId());
		itemService.fillItem(result, item);
		System.out.println(result);
	}

	@Test
	public void sendBagPen() {
		Role role = mockRole();
		BagPen bagPen = BagPenImplTest.mockBagPen();
		role.setBagPen(bagPen);
		//
		itemService.sendBagPen(role.getId(), role.getBagPen());
	}

	@Test
	public void fillBagPen() {
		Message result = null;
		//
		Role role = mockRole();
		BagPen bagPen = BagPenImplTest.mockBagPen();
		role.setBagPen(bagPen);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ROLE,
					CoreModuleType.CLIENT, null, role.getId());
			itemService.fillBagPen(result, bagPen);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		// 所有tab
		System.out.println(result);

		// 移除1個道具
		bagPen.removeItem(0, 0);
		// 鎖定 TabType._1,TabType._2
		bagPen.lock(1);
		bagPen.lock(2);
		//
		result = messageService.createMessage(CoreModuleType.ROLE,
				CoreModuleType.CLIENT, null, role.getId());
		itemService.fillBagPen(result, bagPen);
		System.out.println(result);
	}

	@Test
	/**
	 * 測試包包未滿的狀況,檢查增加道具
	 */
	// 1000000 times: 17058 mills.
	public void checkIncreaseItem() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		Item item = itemService.createItem(THING_ID, 1);
		// 最大堆疊數量=0,所以剩餘可放數量=Integer.MAX_VALUE
		item.setMaxAmount(0);

		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkIncreaseItem(role, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		// 最大堆疊數量=50,每格已放10個道具,已放119格
		// 所以剩餘可放數量=40*119=4760+1個空格(50)=4810
		item.setMaxAmount(50);
		result = itemService.checkIncreaseItem(role, item);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setMaxAmount(10);

		// 包包內的道具
		Item existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);
		//
		result = itemService.checkIncreaseItem(role, item);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setAmount(16);// 放16個 > 剩餘可放數量15個,所以放不進去,包包滿了
		item.setMaxAmount(10);

		// 包包內的道具
		existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);
		//
		result = itemService.checkIncreaseItem(role, item);
		System.out.println(result);
		// 包包滿了
		assertEquals(BagPen.ErrorType.BAG_FULL, result);
	}

	@Test
	// 1000000 times: 5374 mills.
	/**
	 * 測試包包滿的狀況,檢查增加道具
	 */
	public void checkIncreaseItemByBagFull() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		role.setBagPen(bagPen);
		//
		Item item = itemService.createItem(THING_ID, 1);
		// 最大堆疊數量=0,所以剩餘可放數量=Integer.MAX_VALUE
		item.setMaxAmount(0);

		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkIncreaseItem(role, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		// 最大堆疊數量=50,,每格已放10個道具,已放120格
		// 所以剩餘可放數量=40*120=4800
		item.setMaxAmount(50);
		result = itemService.checkIncreaseItem(role, item);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		// 最大堆疊數量=10,所以剩餘可放數量=0
		item.setMaxAmount(10);
		result = itemService.checkIncreaseItem(role, item);
		System.out.println(result);
		// 包包滿了
		assertEquals(BagPen.ErrorType.BAG_FULL, result);
	}

	@Test
	// 1000000 times: 8806 mills.
	/**
	 * 測試包包未滿的狀況,檢查放入道具
	 */
	public void checkIncreaseItemByItemId() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkIncreaseItem(role, THING_ID, 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = itemService.checkIncreaseItem(role, "T_NOT_EXIST_001", 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 17 mills.
	public void increaseItem() {
		final String THING_ID = "T_POTION_HP_G001";// 治癒藥水
		final String WEAPON_ID = "W_MARS_SWORD_E001";// E 級單手劍
		//
		List<IncreaseItemResult> result = null;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		Item item = itemService.createItem(THING_ID, 1);
		// 最大堆疊數量=0,所以剩餘可放數量=Integer.MAX_VALUE
		item.setMaxAmount(0);

		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.increaseItem(true, role, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertTrue(result.size() > 0);

		// 最大堆疊數量=50,每格已放10個道具,已放119格
		// 所以剩餘可放數量=40*119=4760+1個空格(50)=4810
		item.setMaxAmount(50);
		result = itemService.increaseItem(true, role, item);
		System.out.println(result);
		// 沒有錯誤
		assertTrue(result.size() > 0);

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setMaxAmount(10);

		// 包包內的道具
		Item existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);
		//
		result = itemService.increaseItem(true, role, item);
		System.out.println(result);
		// 沒有錯誤
		assertTrue(result.size() > 0);

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setMaxAmount(10);
		item.setAmount(6);// 放6個 < 剩餘可放數量15個

		// 包包內的道具
		existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);// 原有5個,此時放入6個,會多一格新格子,放1個
		//
		result = itemService.increaseItem(true, role, item);
		System.out.println(result);
		// 沒有錯誤了
		assertTrue(result.size() > 0);
		assertEquals(10, existItem.getAmount());
		assertEquals(1, bagPen.getItem(0, 0).getAmount());

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setMaxAmount(10);
		item.setAmount(3);// 放3個 < 剩餘可放數量15個

		// 包包內的道具
		existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);
		//
		result = itemService.increaseItem(true, role, item);
		System.out.println(result);
		// 沒有錯誤了
		assertTrue(result.size() > 0);
		assertEquals(8, existItem.getAmount());

		// 最大堆疊數量=10,所以剩餘可放數量=5+1個空格的(10)=15
		item.setMaxAmount(10);
		item.setAmount(16);// 放16個 > 剩餘可放數量15個,所以放不進去,包包滿了

		// 包包內的道具
		existItem = bagPen.getItem(0, 1);
		existItem.setAmount(5);
		//
		result = itemService.increaseItem(true, role, item);
		System.out.println(result);
		// 包包滿了
		assertTrue(result.size() == 0);
		assertEquals(5, existItem.getAmount());
		//
		Equipment equipment = itemService.createEquipment(WEAPON_ID);// E 級單手劍
		bagPen.removeItem(0, 0);// 移除1個道具
		//
		result = itemService.increaseItem(true, role, equipment);
		System.out.println(result);
		assertTrue(result.size() > 0);
		//
		//ThreadHelper.sleep(3 * 1000);
	}

	@Test
	// 1000000 times: 17 mills.
	public void increaseItemByItemId() {
		final String THING_ID = "T_POTION_HP_G001";
		List<IncreaseItemResult> result = null;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		// 最大堆疊數量=0,所以剩餘可放數量=Integer.MAX_VALUE

		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.increaseItem(true, role, THING_ID, 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertNotNull(result);

		result = itemService.increaseItem(true, role, "T_NOT_EXIST_001", 1);
		System.out.println(result);
		// 道具不存在
		assertTrue(result.size() == 0);
		//
		//ThreadHelper.sleep(3 * 1000);
	}

	@Test
	// 1000000 times: 434 mills.
	// 1000000 times: 437 mills.
	// 1000000 times: 421 mills.
	public void fillIncreaseItem() {
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPen();
		role.setBagPen(bagPen);
		Item item = bagPen.getItem(0, 0);
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ROLE,
					CoreModuleType.CLIENT,
					CoreMessageType.ITEM_INCREASE_RESPONSE, role.getId());
			itemService.fillIncreaseItem(result, 0, 0, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	// 1000000 times: 1959 mills.
	public void checkDecreaseItem() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		Item item = itemService.createItem(THING_ID, 1);
		// 最大堆疊數量=0,所以剩餘可放數量=Integer.MAX_VALUE
		item.setMaxAmount(0);

		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkDecreaseItem(role, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = itemService.checkDecreaseItem(role, null);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);

		// 包包全清空
		bagPen.clearTab();
		result = itemService.checkDecreaseItem(role, item);
		System.out.println(result);
		// 道具數量不足
		assertEquals(BagPen.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 5382 mills.
	public void checkDecreaseItemByItemId() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkDecreaseItem(role, THING_ID, 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = itemService.checkDecreaseItem(role, null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);

		// 包包全清空
		bagPen.clearTab();
		result = itemService.checkDecreaseItem(role, THING_ID, 1);
		System.out.println(result);
		// 道具數量不足
		assertEquals(BagPen.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 5382 mills.
	public void checkDecreaseItemByUniqueId() {
		final String THING_ID = "T_POTION_HP_G001";
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		Item item = bagPen.getItem(0, 1);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkDecreaseItemByUniqueId(role,
					item.getUniqueId(), 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = itemService.checkDecreaseItemByUniqueId(role, null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);

		// 包包全清空
		bagPen.clearTab();
		result = itemService.checkDecreaseItemByUniqueId(role,
				item.getUniqueId(), 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 17 mills.
	public void decreaseItem() {
		final String THING_ID = "T_POTION_HP_G001";
		List<DecreaseItemResult> result = null;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		Item item = itemService.createItem(THING_ID, 1);

		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.decreaseItem(true, role, item);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertNotNull(result);

		// (0,1)=9,(0,2)=10
		item.setAmount(15);// 扣15個
		result = itemService.decreaseItem(true, role, item);
		// (0,1)=null
		System.out.println(bagPen.getItem(0, 1));
		assertNull(bagPen.getItem(0, 1));
		// (0,2)=4
		System.out.println(bagPen.getItem(0, 2).getAmount());
		assertEquals(4, bagPen.getItem(0, 2).getAmount());

		// 沒有錯誤
		assertNotNull(result);
		//
		//ThreadHelper.sleep(3 * 1000);
	}

	@Test
	// 1000000 times: 17 mills.
	public void decreaseItemByItemId() {
		final String THING_ID = "T_POTION_HP_G001";
		List<DecreaseItemResult> result = null;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		//
		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.decreaseItem(true, role, THING_ID, 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertNotNull(result);
		assertEquals(1, result.size());
		//
		result = itemService.decreaseItem(true, role, THING_ID, 10);
		System.out.println(result);
		// 沒有錯誤
		assertNotNull(result);
		assertEquals(2, result.size());
		//
		//ThreadHelper.sleep(3 * 1000);
	}

	@Test
	// 1000000 times: 17 mills.
	public void decreaseItemByUniqueId() {
		final String THING_ID = "T_POTION_HP_G001";
		DecreaseItemResult result = null;
		//
		Role role = mockRole();
		// 道具數量=10,最大堆疊數量=100,有120個道具
		BagPen bagPen = BagPenImplTest.mockBagPenBySameThing(THING_ID);
		bagPen.removeItem(0, 0);// 移除1個道具
		role.setBagPen(bagPen);
		Item item = bagPen.getItem(0, 1);
		//
		int count = 1;
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.decreaseItemByUniqueId(true, role,
					item.getUniqueId(), 1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertNotNull(result);
		//
		//ThreadHelper.sleep(3 * 1000);
	}

	@Test
	// 1000000 times: 16205 mills.
	public void calcItemsPrice() {
		Map<String, Integer> items = new LinkedHashMap<String, Integer>();
		items.put("M_COTTON_G001", 2);
		items.put("M_OAK_G001", 1);
		items.put("T_POTION_HP_G001", 1);
		items.put("T_ENHANCE_ARMOR_E_G001", 1);
		//
		long result = 0L;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 防具
			result = itemService.calcItemsPrice(items);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	/**
	 * 裝備一般強化
	 */
	public void enhanceEquipment() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 一般強化,若失敗,裝備消失
			result = itemService.enhanceEquipment(false, role, equipment,
					EnhanceType.GENERAL);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
	}

	@Test
	/**
	 * 裝備祝福強化
	 */
	public void enhanceEquipmentByBless() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 祝福強化,若失敗,強化歸0,裝備還在
			result = itemService.enhanceEquipment(false, role, equipment,
					EnhanceType.BLESS);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
	}

	@Test
	/**
	 * 裝備幻想強化
	 */
	public void enhanceEquipmentByFantasy() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 幻想強化,若失敗,保有原本強化,不歸0,裝備還在
			result = itemService.enhanceEquipment(false, role, equipment,
					EnhanceType.FANTASY);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
	}

	@Test
	/**
	 * 防具幻想強化
	 */
	public void enhanceArmorByFantasy() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Equipment equipment = itemService
				.createEquipment("A_MARS_BODY_HEAD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 幻想強化,若失敗,保有原本強化,不歸0,裝備還在
			result = itemService.enhanceEquipment(false, role, equipment,
					EnhanceType.FANTASY);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
	}

	@Test
	/**
	 * 重新計算所有屬性,強化等級會改變屬性
	 * 
	 * 防具,武器,土地
	 */
	public void calcEnhanceAttributes() {
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		// +1
		equipment.setEnhanceLevel(EnhanceLevel._1);
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			itemService.calcEnhanceAttributes(equipment);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());

		// +7
		equipment.setEnhanceLevel(EnhanceLevel._7);
		itemService.calcEnhanceAttributes(equipment);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//

	}

	@Test
	public void sendEnhanceEquipment() {
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		EnhanceResult enhanceResult = new EnhanceResultImpl();
		//
		int count = 1; 
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			itemService.sendEnhance(role, equipment, enhanceResult);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	public void fillEnhance() {
		Message result = null;
		//
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		//
		int count = 1; 
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ITEM,
					CoreModuleType.CLIENT,
					CoreMessageType.ITEM_ENHANCE_RESPONSE, role.getId());
			itemService.fillEnhance(result, equipment);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	public void unenhance() {
		boolean result = false;
		//
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		equipment.setEnhanceLevel(EnhanceLevel._7);
		//
		AttributeType attributeType = AttributeType.PHYSICAL_ATTACK;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(99);
		equipment.getAttributeGroup().addAttribute(attribute);
		//
		int count = 1; 
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = itemService.unenhance(true, role, equipment);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
	}

	@Test
	public void sendUnenhance() {
		Role role = mockRole();
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		//
		int count = 1; 
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			itemService.sendUnenhance(role, equipment);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	/**
	 * 土地一般強化
	 */
	public void enhanceLand() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Land land = itemService.createLand("L_TROPICS_G001");
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 一般強化,若失敗,土地消失
			result = itemService.enhanceLand(false, role, land,
					EnhanceType.GENERAL);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
	}

	@Test
	public void changeEnhance() {
		int result = 0;
		//
		Role role = mockRole();
		// +0
		Equipment equipment = itemService.createEquipment("W_MARS_SWORD_E001");
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		//
		result = itemService.changeEnhance(true, role, equipment, -1);
		// +0
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		assertEquals(0, result);
		assertEquals(0, equipment.getEnhanceLevel().getValue());

		// +7
		result = itemService.changeEnhance(true, role, equipment, 7);
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		assertEquals(7, result);
		assertEquals(7, equipment.getEnhanceLevel().getValue());

		// -1
		result = itemService.changeEnhance(true, role, equipment, -1);
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		assertEquals(-1, result);
		assertEquals(6, equipment.getEnhanceLevel().getValue());

		// -7 變為-1 => 0
		result = itemService.changeEnhance(true, role, equipment, -7);
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		assertEquals(-6, result);
		assertEquals(0, equipment.getEnhanceLevel().getValue());

		// +31 > 最高30 => 30
		result = itemService.changeEnhance(true, role, equipment, 31);
		System.out.println(result);
		System.out.println(equipment.getEnhanceLevel() + ", "
				+ equipment.getAttributeGroup());
		assertEquals(30, result);
		assertEquals(30, equipment.getEnhanceLevel().getValue());
	}

	@Test
	public void changeEnhanceByLand() {
		int result = 0;
		//
		Role role = mockRole();
		// +0
		Land land = itemService.createLand("L_TROPICS_G001");
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.changeEnhance(true, role, land, -1);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// +0
		System.out.println(result);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		assertEquals(0, result);
		assertEquals(0, land.getEnhanceLevel().getValue());

		// +7
		result = itemService.changeEnhance(true, role, land, 7);
		System.out.println(result);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		assertEquals(7, result);
		assertEquals(7, land.getEnhanceLevel().getValue());

		// -1
		result = itemService.changeEnhance(true, role, land, -1);
		System.out.println(result);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		assertEquals(-1, result);
		assertEquals(6, land.getEnhanceLevel().getValue());

		// -7 變為-1 => 0
		result = itemService.changeEnhance(true, role, land, -7);
		System.out.println(result);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		assertEquals(-6, result);
		assertEquals(0, land.getEnhanceLevel().getValue());

		// +31 > 最高30 => 30
		result = itemService.changeEnhance(true, role, land, 31);
		System.out.println(result);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		assertEquals(30, result);
		assertEquals(30, land.getEnhanceLevel().getValue());
	}

	@Test
	/**
	 * 土地祝福強化
	 */
	public void enhanceLandByBless() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Land land = itemService.createLand("L_TROPICS_G001");
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 祝福強化,若失敗,強化歸0,土地還在
			result = itemService.enhanceLand(false, role, land,
					EnhanceType.BLESS);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
	}

	@Test
	/**
	 * 土地幻想強化
	 */
	public void enhanceLandByFantasy() {
		EnhanceResult result = null;
		//
		Role role = mockRole();
		Land land = itemService.createLand("L_TROPICS_G001");
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		int times = 0;
		for (int i = 0; i < count; i++) {
			// 幻想強化,若失敗,保有原本強化,不歸0,土地還在
			result = itemService.enhanceLand(false, role, land,
					EnhanceType.FANTASY);
			if (result != null && result.isSuccess()) {
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("成功: " + times);
		System.out.println(land.getEnhanceLevel() + ", "
				+ land.getAttributeGroup());
	}

	@Test
	public void useItem() {
		Role role = mockRole();

		// 10個e防捲放包包
		Item armorE_G001 = itemService.createItem("T_ENHANCE_ARMOR_E_G001", 10);
		itemService.increaseItem(false, role, armorE_G001);

		// 強化防具
		// 10個d防捲放包包
		Item armorD_G001 = itemService.createItem("T_ENHANCE_ARMOR_D_G001", 10);
		itemService.increaseItem(false, role, armorD_G001);

		// 1件防具放包包
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		itemService.increaseItem(false, role, armor);

		// 使用道具強化
		itemService.useItem(true, role, armor.getUniqueId(),
				armorE_G001.getUniqueId(), 1);
		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(armorE_G001.getUniqueId())
				.getAmount());

		// 強化武器
		// 10個e武捲放包包
		Item weaponE_G001 = itemService.createItem("T_ENHANCE_WEAPON_E_G001",
				10);
		itemService.increaseItem(false, role, weaponE_G001);

		// 10個d武捲放包包
		Item weaponD_G001 = itemService.createItem("T_ENHANCE_WEAPON_D_G001",
				10);
		itemService.increaseItem(false, role, weaponD_G001);

		// 1件武器放包包
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");
		itemService.increaseItem(false, role, weapon);

		// 使用道具強化
		itemService.useItem(true, role, weapon.getUniqueId(),
				weaponE_G001.getUniqueId(), 1);
		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(weaponE_G001.getUniqueId())
				.getAmount());

		// 強化土地
		// 10個土地捲放包包
		Item landG001 = itemService.createItem("T_ENHANCE_LAND_G001", 10);
		itemService.increaseItem(false, role, landG001);

		// 1個土地放包包
		Land land = itemService.createLand("L_TROPICS_G001");
		itemService.increaseItem(false, role, land);

		// 使用道具強化
		itemService.useItem(true, role, land.getUniqueId(), landG001.getUniqueId(),
				1);
		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(landG001.getUniqueId())
				.getAmount());

	}

	@Test
	// 1000000 times: 1343 mills.
	// 1000000 times: 1093 mills.
	// 1000000 times: 1191 mills.
	public void checkUse() {
		ErrorType result = ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 10個e防捲放包包
		Item armorE = itemService.createItem("T_ENHANCE_ARMOR_E_G001", 10);
		itemService.increaseItem(false, role, armorE);
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = itemService.checkUseItem(role, armorE.getUniqueId(), 1);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);

		// 道具不存在
		itemService.decreaseItem(false, role, armorE);
		result = itemService.checkUseItem(role, armorE.getUniqueId(), 1);
		itemService.increaseItem(false, role, armorE);
		System.out.println(result);
		assertEquals(ErrorType.ITEM_NOT_EXIST, result);

		// 數量不合法
		result = itemService.checkUseItem(role, armorE.getUniqueId(), 0);
		System.out.println(result);
		assertEquals(ErrorType.AMOUNT_NOT_VALID, result);

		// 數量不足
		result = itemService.checkUseItem(role, armorE.getUniqueId(), 11);
		System.out.println(result);
		assertEquals(ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	public void useEnhanceArmorThing() {
		ErrorType result = ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 10個e防捲放包包
		Item armorE_G001 = itemService.createItem("T_ENHANCE_ARMOR_E_G001", 10);
		itemService.increaseItem(false, role, armorE_G001);

		// 10個幻想e防捲放包包
		Item armorE_F001 = itemService.createItem("T_ENHANCE_ARMOR_E_F001", 10);
		itemService.increaseItem(false, role, armorE_F001);

		// 10個d防捲放包包
		Item armorD_G001 = itemService.createItem("T_ENHANCE_ARMOR_D_G001", 10);
		itemService.increaseItem(false, role, armorD_G001);

		// 10個藥水放包包
		Item potionHp = itemService.createItem("T_POTION_HP_G001", 10);
		itemService.increaseItem(false, role, potionHp);

		// 1件武器放包包
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");
		itemService.increaseItem(false, role, weapon);

		// 1件防具放包包
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		itemService.increaseItem(false, role, armor);

		// 使用道具
		result = itemService.useEnhanceArmorThing(true, role,
				armor.getUniqueId(), armorE_G001);
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);

		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(armorE_G001.getUniqueId())
				.getAmount());

		// 不是防具,無法強化
		result = itemService.useEnhanceArmorThing(true, role,
				weapon.getUniqueId(), armorE_G001);
		System.out.println(result);
		assertEquals(ErrorType.EQUIPMENT_NOT_VALID, result);

		// 不是強化防具道具,無法強化
		result = itemService.useEnhanceArmorThing(true, role,
				armor.getUniqueId(), potionHp);
		System.out.println(result);
		assertEquals(ErrorType.ITEM_NOT_VALID, result);

		// 強化道具等級與裝備等級不符,無法強化
		result = itemService.useEnhanceArmorThing(true, role,
				armor.getUniqueId(), armorD_G001);
		System.out.println(result);
		assertEquals(ErrorType.EQUIPMENT_LEVEL_NOT_VALID, result);

		// 超過最高強化等級
		armor.setEnhanceLevel(EnhanceLevel._15);// +15
		result = itemService.useEnhanceArmorThing(true, role,
				armor.getUniqueId(), armorE_G001);
		System.out.println(result);
		assertEquals(ErrorType.OVER_MAX_ENHANCE_LEVEL, result);

		// 超過幻想強化,強化等級限制
		armor.setEnhanceLevel(EnhanceLevel._9);// +9
		result = itemService.useEnhanceArmorThing(true, role,
				armor.getUniqueId(), armorE_F001);
		System.out.println(result);
		assertEquals(ErrorType.OVER_FANTASY_ENHANCE_LEVEL, result);
	}

	@Test
	public void useEnhanceWeaponThing() {
		ErrorType result = ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 10個e武捲放包包
		Item weaponE_G001 = itemService.createItem("T_ENHANCE_WEAPON_E_G001",
				10);
		itemService.increaseItem(false, role, weaponE_G001);

		// 10個幻想e武捲放包包
		Item weaponE_F001 = itemService.createItem("T_ENHANCE_WEAPON_E_F001",
				10);
		itemService.increaseItem(false, role, weaponE_F001);

		// 10個d武捲放包包
		Item weaponD_G001 = itemService.createItem("T_ENHANCE_WEAPON_D_G001",
				10);
		itemService.increaseItem(false, role, weaponD_G001);

		// 10個藥水放包包
		Item potionHp = itemService.createItem("T_POTION_HP_G001", 10);
		itemService.increaseItem(false, role, potionHp);

		// 1件武器放包包
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");
		itemService.increaseItem(false, role, weapon);

		// 1件防具放包包
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		itemService.increaseItem(false, role, armor);

		// 使用道具
		result = itemService.useEnhanceWeaponThing(true, role,
				weapon.getUniqueId(), weaponE_G001);
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);

		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(weaponE_G001.getUniqueId())
				.getAmount());

		// 不是武器,無法強化
		result = itemService.useEnhanceWeaponThing(true, role,
				armor.getUniqueId(), weaponE_G001);
		System.out.println(result);
		assertEquals(ErrorType.EQUIPMENT_NOT_VALID, result);

		// 不是強化武器道具,無法強化
		result = itemService.useEnhanceWeaponThing(true, role,
				weapon.getUniqueId(), potionHp);
		System.out.println(result);
		assertEquals(ErrorType.ITEM_NOT_VALID, result);

		// 強化道具等級與裝備等級不符,無法強化
		result = itemService.useEnhanceWeaponThing(true, role,
				weapon.getUniqueId(), weaponD_G001);
		System.out.println(result);
		assertEquals(ErrorType.EQUIPMENT_LEVEL_NOT_VALID, result);

		// 直接改變強化,到最高強化等級
		itemService.changeEnhance(false, role, weapon, WeaponCollector
				.getInstance().getMaxEnhanceLevel().getValue());// +30
		result = itemService.useEnhanceWeaponThing(true, role,
				weapon.getUniqueId(), weaponE_G001);
		System.out.println(result);
		// 超過最高強化等級
		assertEquals(ErrorType.OVER_MAX_ENHANCE_LEVEL, result);

		// 清除強化
		itemService.unenhance(false, role, weapon);
		// 直接改變強化,到最高幻想強化等級
		itemService.changeEnhance(false, role, weapon, WeaponCollector
				.getInstance().getFantasyEnhanceLevel().getValue());// +15
		result = itemService.useEnhanceWeaponThing(true, role,
				weapon.getUniqueId(), weaponE_F001);
		System.out.println(result);
		// 超過幻想強化,強化等級限制
		assertEquals(ErrorType.OVER_FANTASY_ENHANCE_LEVEL, result);
	}

	@Test
	public void useEnhanceLandThing() {
		ErrorType result = ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 10個土地捲放包包
		Item landG001 = itemService.createItem("T_ENHANCE_LAND_G001", 10);
		itemService.increaseItem(false, role, landG001);

		// 10個幻想土地捲放包包
		Item landF001 = itemService.createItem("T_ENHANCE_LAND_F001", 10);
		itemService.increaseItem(false, role, landF001);

		// 10個藥水放包包
		Item potionHp = itemService.createItem("T_POTION_HP_G001", 10);
		itemService.increaseItem(false, role, potionHp);

		// 1個土地放包包
		Land land = itemService.createLand("L_TROPICS_G001");
		itemService.increaseItem(false, role, land);

		// 1件防具放包包
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");
		itemService.increaseItem(false, role, armor);

		// 使用道具
		result = itemService.useEnhanceLandThing(true, role,
				land.getUniqueId(), landG001);
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);

		// 扣1個道具,剩9個
		assertEquals(9, role.getBagPen().getItem(landG001.getUniqueId())
				.getAmount());

		// 不是土地,無法強化
		result = itemService.useEnhanceLandThing(true, role,
				armor.getUniqueId(), landG001);
		System.out.println(result);
		assertEquals(ErrorType.LAND_NOT_VALID, result);

		// 不是強化土地道具,無法強化
		result = itemService.useEnhanceLandThing(true, role,
				land.getUniqueId(), potionHp);
		System.out.println(result);
		assertEquals(ErrorType.ITEM_NOT_VALID, result);

		// 直接改變強化,到最高強化等級
		itemService.changeEnhance(false, role, land, ManorCollector
				.getInstance().getMaxEnhanceLevel().getValue());// +30

		result = itemService.useEnhanceLandThing(true, role,
				land.getUniqueId(), landG001);
		System.out.println(result);
		// 超過最高強化等級
		assertEquals(ErrorType.OVER_MAX_ENHANCE_LEVEL, result);
	}

	@Test
	public void useRoleExpThing() {
		ErrorType result = ErrorType.NO_ERROR;
		final int AMOUNT = 10;
		//
		Role role = mockRole();
		// 10個經驗之力放包包
		Item itemG001 = itemService.createItem("T_ROLE_EXP_G001", AMOUNT);
		itemService.increaseItem(false, role, itemG001);

		// 10個想願經驗之力放包包
		Item itemG002 = itemService.createItem("T_ROLE_EXP_G002", AMOUNT);
		itemService.increaseItem(false, role, itemG002);

		// 10個憧憬經驗之力放包包
		Item itemG003 = itemService.createItem("T_ROLE_EXP_G003", AMOUNT);
		itemService.increaseItem(false, role, itemG003);

		// 使用道具,經驗之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleExpThing(true, role, itemG001, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleExpThing(true, role, itemG001, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());

		// 使用道具,想願經驗之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleExpThing(true, role, itemG002, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleExpThing(true, role, itemG002, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());

		// 使用道具,憧憬經驗之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleExpThing(true, role, itemG003, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleExpThing(true, role, itemG003, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
	}

	@Test
	public void useRoleSpThing() {
		ErrorType result = ErrorType.NO_ERROR;
		final int AMOUNT = 10;
		//
		Role role = mockRole();
		// 10個技魂之力放包包
		Item itemG001 = itemService.createItem("T_ROLE_SP_G001", AMOUNT);
		itemService.increaseItem(false, role, itemG001);

		// 10個想願技魂之力放包包
		Item itemG002 = itemService.createItem("T_ROLE_SP_G002", AMOUNT);
		itemService.increaseItem(false, role, itemG002);

		// 10個憧憬技魂之力放包包
		Item itemG003 = itemService.createItem("T_ROLE_SP_G003", AMOUNT);
		itemService.increaseItem(false, role, itemG003);

		// 使用道具,技魂之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleSpThing(true, role, itemG001, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleSpThing(true, role, itemG001, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());

		// 使用道具,想願技魂之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleSpThing(true, role, itemG002, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleSpThing(true, role, itemG002, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());

		// 使用道具,憧憬技魂之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleSpThing(true, role, itemG003, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleSpThing(true, role, itemG003, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
	}

	@Test
	public void useRoleGoldThing() {
		ErrorType result = ErrorType.NO_ERROR;
		final int AMOUNT = 10;
		//
		Role role = mockRole();
		// 10個金幣之力放包包
		Item itemG001 = itemService.createItem("T_ROLE_GOLD_G001", AMOUNT);
		itemService.increaseItem(false, role, itemG001);

		// 10個想願金幣之力放包包
		Item itemG002 = itemService.createItem("T_ROLE_GOLD_G002", AMOUNT);
		itemService.increaseItem(false, role, itemG002);

		// 10個憧憬金幣之力放包包
		Item itemG003 = itemService.createItem("T_ROLE_GOLD_G003", AMOUNT);
		itemService.increaseItem(false, role, itemG003);

		// 使用道具,金幣之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleGoldThing(true, role, itemG001, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleGoldThing(true, role, itemG001, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());

		// 使用道具,想願金幣之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleGoldThing(true, role, itemG002, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleGoldThing(true, role, itemG002, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());

		// 使用道具,憧憬金幣之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleGoldThing(true, role, itemG003, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleGoldThing(true, role, itemG003, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
	}

	@Test
	public void useRoleFameThing() {
		ErrorType result = ErrorType.NO_ERROR;
		final int AMOUNT = 10;
		//
		Role role = mockRole();
		// 10個聲望之力放包包
		Item itemG001 = itemService.createItem("T_ROLE_FAME_G001", AMOUNT);
		itemService.increaseItem(false, role, itemG001);

		// 10個想願聲望之力放包包
		Item itemG002 = itemService.createItem("T_ROLE_FAME_G002", AMOUNT);
		itemService.increaseItem(false, role, itemG002);

		// 10個憧憬聲望之力放包包
		Item itemG003 = itemService.createItem("T_ROLE_FAME_G003", AMOUNT);
		itemService.increaseItem(false, role, itemG003);

		// 使用道具,聲望之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleFameThing(true, role, itemG001, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleFameThing(true, role, itemG001, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG001.getUniqueId())
				.getAmount());

		// 使用道具,想願聲望之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleFameThing(true, role, itemG002, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleFameThing(true, role, itemG002, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG002.getUniqueId())
				.getAmount());

		// 使用道具,憧憬聲望之力
		for (int i = 0; i < 2; i++) {
			result = itemService.useRoleFameThing(true, role, itemG003, 1);
		}
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(8, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
		//
		result = itemService.useRoleFameThing(true, role, itemG003, 7);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		assertEquals(1, role.getBagPen().getItem(itemG003.getUniqueId())
				.getAmount());
	}

	@Test
	public void putEquipment() {
		Role role = mockRole();
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");// E
																			// 級單手劍
		//
		boolean result = false;
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 裝上武器
			result = itemService.putEquipment(true, role, weapon);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		AttributeGroup equipmentGroup = role.getEquipmentGroup();
		System.out.println(equipmentGroup);
		assertEquals(40, equipmentGroup.getFinal(AttributeType.PHYSICAL_ATTACK));

		// 穿上頭盔
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");// E
																				// 級頭盔
		result = itemService.putEquipment(true, role, armor);
		System.out.println(result);
		System.out.println(equipmentGroup);
		assertEquals(5, equipmentGroup.getFinal(AttributeType.PHYSICAL_DEFENCE));

		// 穿上護手
		armor = itemService.createEquipment("A_MARS_BODY_HAND_E001");// E 級護手
		result = itemService.putEquipment(true, role, armor);
		System.out.println(result);
		System.out.println(equipmentGroup);
		assertEquals(10,
				equipmentGroup.getFinal(AttributeType.PHYSICAL_DEFENCE));
	}

	@Test
	public void takeEquipment() {
		Role role = mockRole();
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");// E
																			// 級單手劍

		// 裝上武器
		itemService.putEquipment(true, role, weapon);
		AttributeGroup equipmentGroup = role.getEquipmentGroup();
		System.out.println(equipmentGroup);
		assertEquals(40, equipmentGroup.getFinal(AttributeType.PHYSICAL_ATTACK));
		//
		boolean result = false;
		//
		int count = 1; 
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 拿下武器
			result = itemService.takeEquipment(true, role, weapon);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		System.out.println(equipmentGroup);
		assertEquals(0, equipmentGroup.getFinal(AttributeType.PHYSICAL_ATTACK));

		// 穿上頭盔
		Equipment armor = itemService.createEquipment("A_MARS_BODY_HEAD_E001");// E
																				// 級頭盔
		itemService.putEquipment(true, role, armor);
		System.out.println(equipmentGroup);
		assertEquals(5, equipmentGroup.getFinal(AttributeType.PHYSICAL_DEFENCE));

		// 脫掉頭盔
		result = itemService.takeEquipment(true, role, armor);
		System.out.println(result);
		System.out.println(equipmentGroup);
		assertEquals(0, equipmentGroup.getFinal(AttributeType.PHYSICAL_DEFENCE));
	}
}
