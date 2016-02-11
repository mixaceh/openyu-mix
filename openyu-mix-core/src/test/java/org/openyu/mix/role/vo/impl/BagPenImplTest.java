package org.openyu.mix.role.vo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.BagPen.Tab;
import org.openyu.mix.role.vo.BagPen.TabType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.SystemHelper;

public class BagPenImplTest extends BaseTestSupporter {

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
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @return
	 */
	public static BagPen mockBagPen() {
		return mockBagPen(null);
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @param role
	 * @return
	 */
	public static BagPen mockBagPen(Role role) {
		BagPen result = new BagPenImpl(role);
		TabType[] tabTypes = BagPen.TabType.values();
		for (TabType tabType : tabTypes) {
			int i = tabType.getValue();
			//
			Tab tab = result.getTab(i);
			if (tab == null) {
				continue;
			}
			for (int j = 0; j < BagPen.Tab.MAX_GRID_SIZE; j++) {
				Thing thing = new ThingImpl();
				thing.setId(Thing.UNIQUE_ID_PREFIX + i + "" + j);
				thing.setUniqueId("U_" + i + "" + j);
				thing.setItemType(ItemType.THING);
				thing.setThingType(ThingType.POTION_HP);
				thing.setAmount(10);// 道具數量
				thing.setMaxAmount(100);// 最大堆疊數量
				tab.addItem(j, thing);
			}
		}
		return result;
	}

	/**
	 * 模擬包包欄位,40個相同物品*4=160個,id=T_00, uniqueId=U_00-39
	 * 
	 * @return
	 */
	public static BagPen mockBagPenWithSameThing() {
		return mockBagPenWithSameThing(Thing.UNIQUE_ID_PREFIX + "00");
	}

	/**
	 * 模擬包包欄位,40個相同物品*3=120個,id=T_00, uniqueId=U_00-39
	 * 
	 * @param id
	 * @return
	 */
	public static BagPen mockBagPenWithSameThing(String id) {
		return mockBagPenWithSameThing(null, id);
	}

	/**
	 * 模擬包包欄位,40個相同物品*3=120個,id=T_xxx, uniqueId=U_00-39
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public static BagPen mockBagPenWithSameThing(Role role, String id) {
		BagPen result = new BagPenImpl(role);
		TabType[] tabTypes = BagPen.TabType.values();
		for (TabType tabType : tabTypes) {
			int i = tabType.getValue();
			//
			Tab tab = result.getTab(i);
			if (tab == null) {
				continue;
			}
			for (int j = 0; j < BagPen.Tab.MAX_GRID_SIZE; j++) {
				Thing thing = new ThingImpl();
				thing.setId(id);
				thing.setUniqueId("U_" + i + "" + j);
				thing.setItemType(ItemType.THING);
				thing.setThingType(ThingType.POTION_HP);
				thing.setAmount(10);// 道具數量
				thing.setMaxAmount(100);// 最大堆疊數量
				tab.addItem(j, thing);
			}
		}
		return result;
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @return
	 */
	public static BagPen mockBagPenWithOneTab() {
		return mockBagPenWithOneTab(null);
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @param role
	 * @return
	 */
	public static BagPen mockBagPenWithOneTab(Role role) {
		BagPen result = new BagPenImpl(role);

		int i = 0;
		Tab tab = result.getTab(i);// 第1頁
		for (int j = 0; j < BagPen.Tab.MAX_GRID_SIZE; j++) {
			Thing thing = new ThingImpl();
			thing.setId(Thing.UNIQUE_ID_PREFIX + i + "" + j);
			thing.setUniqueId("U_" + i + "" + j);
			thing.setItemType(ItemType.THING);
			thing.setThingType(ThingType.POTION_HP);
			thing.setAmount(10);// 道具數量
			thing.setMaxAmount(100);// 最大堆疊數量
			tab.addItem(j, thing);
		}
		//
		result.lock(1);// 第2頁鎖定
		result.lock(2);// 第3頁鎖定
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
		result.setItemType(ItemType.THING);
		result.setThingType(randomType(ThingType.class));
		result.setAmount(randomInt(10));// 道具數量
		result.setMaxAmount(randomInt(100));// 最大堆疊數量
		return result;
	}

	@Test
	// 1000000 times: 7 mills.
	// 1000000 times: 6 mills.
	// 1000000 times: 3 mills.
	// verified
	public void getTabSize() {
		BagPen bagPen = mockBagPen();
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getTabSize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(TabType.values().length, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void lock() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.lock(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = bagPen.lock(-1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
		//
		bagPen.unLock(0);// 解鎖
		bagPen.removeTab(0);// 移除一個包包頁
		//
		result = bagPen.lock(0);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagPen.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void isFull() {
		BagPen bagPen = mockBagPen();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.isFull();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		bagPen.removeItem(0, 0);
		result = bagPen.isFull();
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	public void containTabType() {
		BagPen bagPen = new BagPenImpl();
		boolean result = bagPen.containIndex(0);
		System.out.println(result);
		result = bagPen.containIndex(1);
		System.out.println(result);
		result = bagPen.containIndex(2);
		System.out.println(result);
		result = bagPen.containIndex(10);
		System.out.println(result);
		result = bagPen.containIndex(3);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addTab() {
		BagPen bagPen = new BagPenImpl();
		bagPen.removeTab(0);
		BagPen.Tab tab = new BagPenImpl.TabImpl();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.addTab(0, tab);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		result = bagPen.addTab(-1, tab);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagPen.addTab(0, null);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagPen.ErrorType.TAB_NOT_EXIST, result);
		//
		bagPen.removeTab(2);
		result = bagPen.addTab(0, tab);
		System.out.println(result);
		// 包包已有包包頁
		assertEquals(BagPen.ErrorType.ALREADY_HAS_TAB, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addItem() {
		BagPen bagPen = mockBagPen();
		bagPen.removeItem(0, 0);// 移除一個道具
		Item item = randomThing();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.addItem(0, 0, item);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.addItem(0, 0, item);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.addItem(-1, 0, item);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagPen.addItem(0, 0, (Item) null);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);

		// 包包頁滿了
		assertEquals((int) Tab.MAX_GRID_SIZE, (int) bagPen.getTab(0).getItemSize());
		result = bagPen.addItem(0, 0, item);
		System.out.println(result);
		assertEquals(BagPen.ErrorType.TAB_FULL, result);
		//
		bagPen.removeItem(0, 1);
		result = bagPen.addItem(0, 0, item);
		System.out.println(result);
		// 格子已有道具
		assertEquals(BagPen.ErrorType.ALREADY_HAS_ITEM, result);
		//
		bagPen.removeTab(0);// 移除一個包包頁
		result = bagPen.addItem(0, 0, item);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagPen.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmount() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.increaseAmount(0, 0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.increaseAmount(0, 0, 1);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.increaseAmount(-1, 0, 1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagPen.increaseAmount(0, -1, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);

		// 移除包包頁index=0,格子索引index=1的道具
		bagPen.removeItem(0, 1);
		result = bagPen.increaseAmount(0, 1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagPen.increaseAmount(0, 0, 100);
		System.out.println(result);
		// 超過道具最大數量
		assertEquals(BagPen.ErrorType.OVER_MAX_AMOUNT, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmountWithUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.increaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.increaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.increaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagPen.increaseAmount(UNIQUE_ID, 100);
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
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.removeItem(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.removeItem(0, 0);
		System.out.println(result);
		bagPen.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.removeItem(0, -1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagPen.removeItem(0, 0);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		bagPen.removeTab(0);// 移除一個包包頁
		result = bagPen.removeItem(0, 0);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagPen.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeItemWithUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.removeItem(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		result = bagPen.removeItem(UNIQUE_ID);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		Thing thing = new ThingImpl();// 再把道具塞回去
		thing.setId(Thing.UNIQUE_ID_PREFIX + "00");
		thing.setUniqueId("U_" + "00");
		bagPen.addItem(0, 0, thing);
		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.removeItem(UNIQUE_ID);
		System.out.println(result);
		bagPen.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmount() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.decreaseAmount(0, 0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.decreaseAmount(0, 0, 1);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.decreaseAmount(-1, 0, 1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagPen.decreaseAmount(0, -1, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);

		// 移除包包頁index=0,移除索引index=1的道具
		bagPen.removeItem(0, 1);
		result = bagPen.decreaseAmount(0, 1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagPen.decreaseAmount(0, 0, 100);
		System.out.println(result);
		// 數量不足時
		assertEquals(BagPen.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmountWithUniqueId() {
		final String UNIQUE_ID = "U_00";

		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.decreaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		//
		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.decreaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.decreaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagPen.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagPen.decreaseAmount(UNIQUE_ID, 100);
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
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.clearItem(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		assertEquals(TabType.values().length, bagPen.getTabSize());
		//
		bagPen = mockBagPen();
		bagPen.lock(1);// 鎖定一個包包頁
		result = bagPen.clearItem(1);
		System.out.println(result);
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		// 有一個包包頁無法清除,因為已鎖定
		assertEquals(TabType.values().length, bagPen.getTabSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearTab() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.clearTab();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		assertEquals(0, bagPen.getTabSize());
		//
		bagPen = mockBagPen();
		bagPen.lock(0);// 鎖定一個包包頁
		result = bagPen.clearTab();
		System.out.println(result);
		assertEquals(BagPen.ErrorType.AT_LEAST_ONE_TAB_LOCKED, result);
		// 有一個包包頁無法清除,因為已鎖定
		assertEquals(1, bagPen.getTabSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getTab() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.Tab result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getTab(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = bagPen.getTab(-1);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItem() {
		BagPen bagPen = mockBagPen();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getItem(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
		//
		result = bagPen.getItem(-1, 0);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 47 mills.
	// 1000000 times: 44 mills.
	// 1000000 times: 47 mills.
	// verified
	public void getItemWithUniqueId() {
		final String UNIQUE_ID = "U_00";

		BagPen bagPen = mockBagPen();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getItem(UNIQUE_ID);
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
		final String ID = "T_00";

		BagPen bagPen = mockBagPen();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getItems(ID);
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
	public void getItemsWithItemType() {
		BagPen bagPen = mockBagPen();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getItems(ItemType.THING);
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
	public void setTab() {
		final Integer INDEX = 0;
		BagPen bagPen = mockBagPen();
		BagPen.Tab tab = new BagPenImpl.TabImpl();
		tab.setId(INDEX);
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.setTab(INDEX, tab);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.setTab(INDEX, tab);
		System.out.println(result);
		bagPen.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.setTab(-1, tab);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItem() {
		BagPen bagPen = mockBagPen();
		Thing thing = randomThing();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.setItem(0, 0, thing);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);

		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.setItem(0, 0, thing);
		System.out.println(result);
		bagPen.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.setItem(0, -1, thing);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagPen.setItem(-1, 0, thing);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItemAmount() {
		BagPen bagPen = mockBagPen();
		//
		BagPen.ErrorType result = BagPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.setItemAmount(0, 0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagPen.ErrorType.NO_ERROR, result);
		assertEquals(0, bagPen.getItem(0, 0).getAmount());

		bagPen.lock(0);// 鎖定包包頁
		result = bagPen.setItemAmount(0, 0, 0);
		System.out.println(result);
		bagPen.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagPen.ErrorType.TAB_LOCKED, result);
		//
		result = bagPen.setItemAmount(0, -1, 0);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagPen.setItemAmount(-1, 0, 0);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagPen.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void containIndex() {
		final Integer INDEX = 0;

		BagPen bagPen = mockBagPen();
		//
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.containIndex(INDEX);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = bagPen.containIndex(-1);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getTabIndexs() {
		BagPen bagPen = mockBagPen();
		//
		List<Integer> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getTabIndexs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		result = bagPen.getTabIndexs(true);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getIndex() {
		final String UNIQUE_ID = "U_00";

		BagPen bagPen = mockBagPen();
		//
		int[] result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getIndex(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result[0] + ", " + result[1]);

		// 包包頁被鎖定
		bagPen.lock(0);
		result = bagPen.getIndex(UNIQUE_ID);
		bagPen.unLock(0);
		// System.out.println(result[0] + ", " + result[1]);
		assertNull(result);

		// 道具不存在
		result = bagPen.getIndex("U_xxx");
		// System.out.println(result[0] + ", " + result[1]);
		assertNull(result);
	}

	@Test
	// 1000000 times: 557 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 538 mills.
	// verified
	public void getIndexs() {
		final String ID = "T_00";

		BagPen bagPen = mockBagPenWithSameThing();// 160個相同物品
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getIndexs(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(160, result.size());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getAmount() {
		final String ID = "T_00";

		BagPen bagPen = mockBagPenWithSameThing();// 160個相同物品
		//
		int result = 0;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getAmount(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1600, result);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptySize() {
		BagPen bagPen = mockBagPenWithSameThing();// 160個相同物品
		bagPen.removeItem(0, 0);// 移除index=0,0
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getEmptySize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1, result);
		//
		bagPen.clearItem(0);
		result = bagPen.getEmptySize();
		System.out.println(result);
		assertEquals(40, result);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptyIndex() {
		BagPen bagPen = mockBagPenWithSameThing();// 160個相同物品
		bagPen.removeItem(0, 20);// 移除index=0,20
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getEmptyIndex();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(20, result[1]);
		//
		bagPen.clearItem(0);
		result = bagPen.getEmptyIndex();
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getPutIndex() {
		final String ID = "T_00";
		//
		BagPen bagPen = mockBagPenWithSameThing();// 160個相同物品
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagPen.getPutIndex(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);

		// 當有堆疊數量限制時
		Item item = bagPen.getItem(0, 0);
		item.setAmount(item.getMaxAmount());
		// System.out.println(item.getAmount() + " " + item.getMaxAmount());
		result = bagPen.getPutIndex(ID);
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(1, result[1]);

		// 無堆疊數量限制時
		item = bagPen.getItem(0, 0);
		item.setMaxAmount(0);
		// System.out.println(item.getAmount() + " " + item.getMaxAmount());
		result = bagPen.getPutIndex(ID);
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);

	}
}
