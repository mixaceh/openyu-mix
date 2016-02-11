package org.openyu.mix.role.vo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.BagPen.Tab;

public class BagPenTabImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		BagPen bagPen = new BagPenImpl();
		//
		String result = CollectorHelper.writeToXml(BagPenImpl.class, bagPen);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		BagPen result = CollectorHelper.readFromXml(BagPenImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬包包頁,40個不同物品,id=T_0-39, uniqueId=U_0-39
	 * 
	 * @return
	 */
	public static Tab mockTab() {
		BagPen.Tab result = new BagPenImpl.TabImpl();
		for (int i = 0; i < BagPen.Tab.MAX_GRID_SIZE; i++) {
			Thing thing = new ThingImpl();
			thing.setId(Thing.UNIQUE_ID_PREFIX + i);
			thing.setUniqueId("U_" + i);
			thing.setItemType(ItemType.THING);
			thing.setThingType(ThingType.POTION_HP);
			thing.setAmount(10);// 道具數量
			thing.setMaxAmount(100);// 最大堆疊數量
			result.addItem(i, thing);
		}
		return result;
	}

	/**
	 * 模擬包包頁,40個相同物品,id=T_0, uniqueId=U_0-39
	 * 
	 * @return
	 */
	public static Tab mockSameThingTab() {
		BagPen.Tab result = new BagPenImpl.TabImpl();
		for (int i = 0; i < BagPen.Tab.MAX_GRID_SIZE; i++) {
			Thing thing = new ThingImpl();
			thing.setId(Thing.UNIQUE_ID_PREFIX + 0);
			thing.setUniqueId("U_" + i);
			thing.setItemType(ItemType.THING);
			thing.setThingType(ThingType.POTION_HP);
			thing.setAmount(10);// 道具數量
			thing.setMaxAmount(100);// 最大堆疊數量
			result.addItem(i, thing);
		}
		return result;
	}

	/**
	 * 隨機物品
	 * 
	 * @return
	 */
	public static Thing randomThing() {
		Thing result = new ThingImpl();
		result.setId(Thing.UNIQUE_ID_PREFIX + randomString());
		result.setUniqueId("U_" + randomUnique());
		result.setItemType(randomType(ItemType.class));
		result.setThingType(randomType(ThingType.class));
		result.setAmount(randomInt(10));// 道具數量
		result.setMaxAmount(randomInt(100));// 最大堆疊數量
		return result;
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItemSize() {
		BagPen.Tab tab = mockTab();
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getItemSize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(40, result.intValue());
		//
		tab.removeItem(0);
		result = tab.getItemSize();
		System.out.println(result);
		assertEquals(39, result.intValue());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void isFull() {
		BagPen.Tab tab = mockTab();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.isFull();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		tab.removeItem(0);
		result = tab.isFull();
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addItem() {
		BagPen.Tab tab = new BagPenImpl.TabImpl();
		Item item = randomThing();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.addItem(0, item);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		tab.setLocked(true);// 鎖定包包頁
		result = tab.addItem(0, item);
		System.out.println(result);
		tab.setLocked(false); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.addItem(-1, item);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = tab.addItem(0, (Item) null);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = tab.addItem(0, item);
		System.out.println(result);
		// 格子已有道具
		assertEquals(BagPen.ErrorType.ALREADY_HAS_ITEM, result);

		// 把包包頁塞滿
		for (int i = 1; i < Tab.MAX_GRID_SIZE; i++) {
			result = tab.addItem(i, item);
		}
		// 包包頁滿了
		assertEquals((int) Tab.MAX_GRID_SIZE, (int) tab.getItemSize());
		result = tab.addItem(0, item);
		System.out.println(result);
		assertEquals(BagPen.ErrorType.TAB_FULL, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmount() {
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.increaseAmount(0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		tab.setLocked(true);// 鎖定包包頁
		result = tab.increaseAmount(0, 1);
		System.out.println(result);
		tab.setLocked(false);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.increaseAmount((Integer) null, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);

		// 移除索引index=1的道具
		tab.removeItem(1);
		result = tab.increaseAmount(1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = tab.increaseAmount(0, 100);
		System.out.println(result);
		// 超過道具最大數量
		assertEquals(BagPen.ErrorType.OVER_MAX_AMOUNT, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmountByUniqueId() {
		final String UNIQUE_ID = "U_0";
		//
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.increaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		tab.setLocked(true);// 鎖定包包頁
		result = tab.increaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		tab.setLocked(false);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.increaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = tab.increaseAmount(UNIQUE_ID, 100);
		System.out.println(result);
		// 超過道具最大數量
		assertEquals(BagPen.ErrorType.OVER_MAX_AMOUNT, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeItem() {
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.removeItem(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		tab.setLocked(true);// 鎖定包包頁
		result = tab.removeItem(0);
		System.out.println(result);
		tab.setLocked(false); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.removeItem((Integer) null);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = tab.removeItem(0);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeItemByUniqueId() {
		final String UNIQUE_ID = "U_0";
		//
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.removeItem(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		tab.setLocked(true);// 鎖定包包頁
		result = tab.removeItem(UNIQUE_ID);
		System.out.println(result);
		tab.setLocked(false); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.removeItem(UNIQUE_ID);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmount() {
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.decreaseAmount(0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		tab.setLocked(true);// 鎖定包包頁
		result = tab.decreaseAmount(0, 1);
		System.out.println(result);
		tab.setLocked(false);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.decreaseAmount((Integer) null, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);

		// 移除索引index=1的道具
		tab.removeItem(1);
		result = tab.decreaseAmount(1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = tab.decreaseAmount(0, 100);
		System.out.println(result);
		// 數量不足時
		assertEquals(BagPen.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmountByUniqueId() {
		final String UNIQUE_ID = "U_0";

		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.decreaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		tab.setLocked(true);// 鎖定包包頁
		result = tab.decreaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		tab.setLocked(false);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.decreaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = tab.decreaseAmount(0, 100);
		System.out.println(result);
		// 數量不足時
		assertEquals(BagPen.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearItem() {
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.clearItem();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		tab.setLocked(true);// 鎖定包包頁
		result = tab.clearItem();
		System.out.println(result);
		tab.setLocked(false);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItem() {
		BagPen.Tab tab = mockTab();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getItem(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
		//
		result = tab.getItem(-1);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 47 mills.
	// 1000000 times: 44 mills.
	// 1000000 times: 47 mills.
	// verified
	public void getItemByUniqueId() {
		final String UNIQUE_ID = "U_0";

		BagPen.Tab tab = mockTab();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getItem(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItems() {
		final String ID = "T_0";

		BagPen.Tab tab = mockTab();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getItems(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItemsByItemType() {
		BagPen.Tab tab = mockTab();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getItems(ItemType.THING);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItem() {
		BagPen.Tab tab = mockTab();
		Thing thing = randomThing();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.setItem(0, thing);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		tab.setLocked(true);// 鎖定包包頁
		result = tab.setItem(0, thing);
		System.out.println(result);
		tab.setLocked(false); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.setItem(-1, thing);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItemAmount() {
		BagPen.Tab tab = mockTab();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.setItemAmount(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		assertEquals(0, tab.getItem(0).getAmount());

		tab.setLocked(true);// 鎖定包包頁
		result = tab.setItemAmount(0, 0);
		System.out.println(result);
		tab.setLocked(false); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = tab.setItemAmount(-1, 0);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void containIndex() {
		final Integer INDEX = 0;

		BagPen.Tab tab = mockTab();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.containIndex(INDEX);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = tab.containIndex(-1);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getIndex() {
		final String UNIQUE_ID = "U_0";

		BagPen.Tab tab = mockTab();
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getIndex(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(0, result.intValue());
	}

	@Test
	// 1000000 times: 557 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 538 mills.
	// verified
	public void getIndexs() {
		final String ID = "T_0";

		BagPen.Tab tab = mockSameThingTab();// 40個相同物品
		//
		List<Integer> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getIndexs(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(40, result.size());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getAmount() {
		final String ID = "T_0";

		BagPen.Tab tab = mockSameThingTab();// 40個相同物品
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getAmount(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(400, result.intValue());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptySize() {
		BagPen.Tab tab = mockSameThingTab();// 40個相同物品
		tab.removeItem(0);// 移除index=0
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getEmptySize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1, result.intValue());
		//
		tab.clearItem();
		result = tab.getEmptySize();
		System.out.println(result);
		assertEquals(40, result.intValue());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptyIndex() {
		BagPen.Tab tab = mockSameThingTab();// 40個相同物品
		tab.removeItem(20);// 移除index=20
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getEmptyIndex();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(20, result.intValue());
		//
		tab.clearItem();
		result = tab.getEmptyIndex();
		System.out.println(result);
		assertEquals(0, result.intValue());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getPutIndex() {
		final String ID = "T_0";
		//
		BagPen.Tab tab = mockSameThingTab();// 40個相同物品
		//
		Integer result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = tab.getPutIndex(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(0, result.intValue());

	}
}
