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
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.BagInfo.Tab;
import org.openyu.mix.role.vo.BagInfo.TabType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.SystemHelper;

public class BagInfoImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		BagInfo bagInfo = new BagInfoImpl();
		//
		String result = CollectorHelper.writeToXml(BagInfoImpl.class, bagInfo);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		BagInfo result = CollectorHelper.readFromXml(BagInfoImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @return
	 */
	public static BagInfo mockBagInfo() {
		return mockBagInfo(null);
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @param role
	 * @return
	 */
	public static BagInfo mockBagInfo(Role role) {
		BagInfo result = new BagInfoImpl(role);
		TabType[] tabTypes = BagInfo.TabType.values();
		for (TabType tabType : tabTypes) {
			int i = tabType.getValue();
			//
			Tab tab = result.getTab(i);
			if (tab == null) {
				continue;
			}
			for (int j = 0; j < BagInfo.Tab.MAX_GRID_SIZE; j++) {
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
	public static BagInfo mockBagInfoWithSameThing() {
		return mockBagInfoWithSameThing(Thing.UNIQUE_ID_PREFIX + "00");
	}

	/**
	 * 模擬包包欄位,40個相同物品*3=120個,id=T_00, uniqueId=U_00-39
	 * 
	 * @param id
	 * @return
	 */
	public static BagInfo mockBagInfoWithSameThing(String id) {
		return mockBagInfoWithSameThing(null, id);
	}

	/**
	 * 模擬包包欄位,40個相同物品*3=120個,id=T_xxx, uniqueId=U_00-39
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public static BagInfo mockBagInfoWithSameThing(Role role, String id) {
		BagInfo result = new BagInfoImpl(role);
		TabType[] tabTypes = BagInfo.TabType.values();
		for (TabType tabType : tabTypes) {
			int i = tabType.getValue();
			//
			Tab tab = result.getTab(i);
			if (tab == null) {
				continue;
			}
			for (int j = 0; j < BagInfo.Tab.MAX_GRID_SIZE; j++) {
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
	public static BagInfo mockBagInfoWithOneTab() {
		return mockBagInfoWithOneTab(null);
	}

	/**
	 * 模擬包包欄位,1個包包頁(tab),每頁40個不同物品,id=T_00-39, uniqueId=U_00-39
	 * 
	 * @param role
	 * @return
	 */
	public static BagInfo mockBagInfoWithOneTab(Role role) {
		BagInfo result = new BagInfoImpl(role);

		int i = 0;
		Tab tab = result.getTab(i);// 第1頁
		for (int j = 0; j < BagInfo.Tab.MAX_GRID_SIZE; j++) {
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
		BagInfo bagInfo = mockBagInfo();
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getTabSize();
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
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.lock(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		result = bagInfo.lock(-1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
		//
		bagInfo.unLock(0);// 解鎖
		bagInfo.removeTab(0);// 移除一個包包頁
		//
		result = bagInfo.lock(0);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagInfo.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void isFull() {
		BagInfo bagInfo = mockBagInfo();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.isFull();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		bagInfo.removeItem(0, 0);
		result = bagInfo.isFull();
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	public void containTabType() {
		BagInfo bagInfo = new BagInfoImpl();
		boolean result = bagInfo.containIndex(0);
		System.out.println(result);
		result = bagInfo.containIndex(1);
		System.out.println(result);
		result = bagInfo.containIndex(2);
		System.out.println(result);
		result = bagInfo.containIndex(10);
		System.out.println(result);
		result = bagInfo.containIndex(3);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addTab() {
		BagInfo bagInfo = new BagInfoImpl();
		bagInfo.removeTab(0);
		BagInfo.Tab tab = new BagInfoImpl.TabImpl();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.addTab(0, tab);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		result = bagInfo.addTab(-1, tab);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagInfo.addTab(0, null);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagInfo.ErrorType.TAB_NOT_EXIST, result);
		//
		bagInfo.removeTab(2);
		result = bagInfo.addTab(0, tab);
		System.out.println(result);
		// 包包已有包包頁
		assertEquals(BagInfo.ErrorType.ALREADY_HAS_TAB, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addItem() {
		BagInfo bagInfo = mockBagInfo();
		bagInfo.removeItem(0, 0);// 移除一個道具
		Item item = randomThing();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.addItem(0, 0, item);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.addItem(0, 0, item);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.addItem(-1, 0, item);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagInfo.addItem(0, 0, (Item) null);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);

		// 包包頁滿了
		assertEquals((int) Tab.MAX_GRID_SIZE, (int) bagInfo.getTab(0).getItemSize());
		result = bagInfo.addItem(0, 0, item);
		System.out.println(result);
		assertEquals(BagInfo.ErrorType.TAB_FULL, result);
		//
		bagInfo.removeItem(0, 1);
		result = bagInfo.addItem(0, 0, item);
		System.out.println(result);
		// 格子已有道具
		assertEquals(BagInfo.ErrorType.ALREADY_HAS_ITEM, result);
		//
		bagInfo.removeTab(0);// 移除一個包包頁
		result = bagInfo.addItem(0, 0, item);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagInfo.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmount() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.increaseAmount(0, 0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		//
		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.increaseAmount(0, 0, 1);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.increaseAmount(-1, 0, 1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagInfo.increaseAmount(0, -1, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_GRID_INDEX, result);

		// 移除包包頁index=0,格子索引index=1的道具
		bagInfo.removeItem(0, 1);
		result = bagInfo.increaseAmount(0, 1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagInfo.increaseAmount(0, 0, 100);
		System.out.println(result);
		// 超過道具最大數量
		assertEquals(BagInfo.ErrorType.OVER_MAX_AMOUNT, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void increaseAmountWithUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.increaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		//
		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.increaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.increaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagInfo.increaseAmount(UNIQUE_ID, 100);
		System.out.println(result);
		// 超過道具最大數量
		assertEquals(BagInfo.ErrorType.OVER_MAX_AMOUNT, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeItem() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.removeItem(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.removeItem(0, 0);
		System.out.println(result);
		bagInfo.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.removeItem(0, -1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagInfo.removeItem(0, 0);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		bagInfo.removeTab(0);// 移除一個包包頁
		result = bagInfo.removeItem(0, 0);
		System.out.println(result);
		// 包包頁不存在
		assertEquals(BagInfo.ErrorType.TAB_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeItemWithUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.removeItem(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		//
		result = bagInfo.removeItem(UNIQUE_ID);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		Thing thing = new ThingImpl();// 再把道具塞回去
		thing.setId(Thing.UNIQUE_ID_PREFIX + "00");
		thing.setUniqueId("U_" + "00");
		bagInfo.addItem(0, 0, thing);
		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.removeItem(UNIQUE_ID);
		System.out.println(result);
		bagInfo.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmount() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.decreaseAmount(0, 0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		//
		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.decreaseAmount(0, 0, 1);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.decreaseAmount(-1, 0, 1);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
		//
		result = bagInfo.decreaseAmount(0, -1, 1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_GRID_INDEX, result);

		// 移除包包頁index=0,移除索引index=1的道具
		bagInfo.removeItem(0, 1);
		result = bagInfo.decreaseAmount(0, 1, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagInfo.decreaseAmount(0, 0, 100);
		System.out.println(result);
		// 數量不足時
		assertEquals(BagInfo.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void decreaseAmountWithUniqueId() {
		final String UNIQUE_ID = "U_00";

		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.decreaseAmount(UNIQUE_ID, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		//
		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.decreaseAmount(UNIQUE_ID, 1);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.decreaseAmount((String) null, 1);
		System.out.println(result);
		// 道具不存在
		assertEquals(BagInfo.ErrorType.ITEM_NOT_EXIST, result);
		//
		result = bagInfo.decreaseAmount(UNIQUE_ID, 100);
		System.out.println(result);
		// 數量不足時
		assertEquals(BagInfo.ErrorType.AMOUNT_NOT_ENOUGH, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearItem() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.clearItem(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		assertEquals(TabType.values().length, bagInfo.getTabSize());
		//
		bagInfo = mockBagInfo();
		bagInfo.lock(1);// 鎖定一個包包頁
		result = bagInfo.clearItem(1);
		System.out.println(result);
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		// 有一個包包頁無法清除,因為已鎖定
		assertEquals(TabType.values().length, bagInfo.getTabSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearTab() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.clearTab();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		assertEquals(0, bagInfo.getTabSize());
		//
		bagInfo = mockBagInfo();
		bagInfo.lock(0);// 鎖定一個包包頁
		result = bagInfo.clearTab();
		System.out.println(result);
		assertEquals(BagInfo.ErrorType.AT_LEAST_ONE_TAB_LOCKED, result);
		// 有一個包包頁無法清除,因為已鎖定
		assertEquals(1, bagInfo.getTabSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getTab() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.Tab result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getTab(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = bagInfo.getTab(-1);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getItem() {
		BagInfo bagInfo = mockBagInfo();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getItem(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
		//
		result = bagInfo.getItem(-1, 0);
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

		BagInfo bagInfo = mockBagInfo();
		//
		Item result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getItem(UNIQUE_ID);
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

		BagInfo bagInfo = mockBagInfo();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getItems(ID);
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
		BagInfo bagInfo = mockBagInfo();
		//
		List<Item> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getItems(ItemType.THING);
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
		BagInfo bagInfo = mockBagInfo();
		BagInfo.Tab tab = new BagInfoImpl.TabImpl();
		tab.setId(INDEX);
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.setTab(INDEX, tab);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.setTab(INDEX, tab);
		System.out.println(result);
		bagInfo.unLock(0);// 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.setTab(-1, tab);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItem() {
		BagInfo bagInfo = mockBagInfo();
		Thing thing = randomThing();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.setItem(0, 0, thing);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);

		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.setItem(0, 0, thing);
		System.out.println(result);
		bagInfo.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.setItem(0, -1, thing);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagInfo.setItem(-1, 0, thing);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setItemAmount() {
		BagInfo bagInfo = mockBagInfo();
		//
		BagInfo.ErrorType result = BagInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.setItemAmount(0, 0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(BagInfo.ErrorType.NO_ERROR, result);
		assertEquals(0, bagInfo.getItem(0, 0).getAmount());

		bagInfo.lock(0);// 鎖定包包頁
		result = bagInfo.setItemAmount(0, 0, 0);
		System.out.println(result);
		bagInfo.unLock(0); // 解鎖包包頁
		// 包包頁被鎖定
		assertEquals(BagInfo.ErrorType.TAB_LOCKED, result);
		//
		result = bagInfo.setItemAmount(0, -1, 0);
		System.out.println(result);
		// 超過格子索引
		assertEquals(BagInfo.ErrorType.OVER_GRID_INDEX, result);
		//
		result = bagInfo.setItemAmount(-1, 0, 0);
		System.out.println(result);
		// 超過包包頁索引
		assertEquals(BagInfo.ErrorType.OVER_TAB_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void containIndex() {
		final Integer INDEX = 0;

		BagInfo bagInfo = mockBagInfo();
		//
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.containIndex(INDEX);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = bagInfo.containIndex(-1);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getTabIndexs() {
		BagInfo bagInfo = mockBagInfo();
		//
		List<Integer> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getTabIndexs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		result = bagInfo.getTabIndexs(true);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getIndex() {
		final String UNIQUE_ID = "U_00";

		BagInfo bagInfo = mockBagInfo();
		//
		int[] result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getIndex(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result[0] + ", " + result[1]);

		// 包包頁被鎖定
		bagInfo.lock(0);
		result = bagInfo.getIndex(UNIQUE_ID);
		bagInfo.unLock(0);
		// System.out.println(result[0] + ", " + result[1]);
		assertNull(result);

		// 道具不存在
		result = bagInfo.getIndex("U_xxx");
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

		BagInfo bagInfo = mockBagInfoWithSameThing();// 160個相同物品
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getIndexs(ID);
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

		BagInfo bagInfo = mockBagInfoWithSameThing();// 160個相同物品
		//
		int result = 0;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getAmount(ID);
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
		BagInfo bagInfo = mockBagInfoWithSameThing();// 160個相同物品
		bagInfo.removeItem(0, 0);// 移除index=0,0
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getEmptySize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1, result);
		//
		bagInfo.clearItem(0);
		result = bagInfo.getEmptySize();
		System.out.println(result);
		assertEquals(40, result);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptyIndex() {
		BagInfo bagInfo = mockBagInfoWithSameThing();// 160個相同物品
		bagInfo.removeItem(0, 20);// 移除index=0,20
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getEmptyIndex();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(20, result[1]);
		//
		bagInfo.clearItem(0);
		result = bagInfo.getEmptyIndex();
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
		BagInfo bagInfo = mockBagInfoWithSameThing();// 160個相同物品
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = bagInfo.getPutIndex(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);

		// 當有堆疊數量限制時
		Item item = bagInfo.getItem(0, 0);
		item.setAmount(item.getMaxAmount());
		// System.out.println(item.getAmount() + " " + item.getMaxAmount());
		result = bagInfo.getPutIndex(ID);
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(1, result[1]);

		// 無堆疊數量限制時
		item = bagInfo.getItem(0, 0);
		item.setMaxAmount(0);
		// System.out.println(item.getAmount() + " " + item.getMaxAmount());
		result = bagInfo.getPutIndex(ID);
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);

	}
}
