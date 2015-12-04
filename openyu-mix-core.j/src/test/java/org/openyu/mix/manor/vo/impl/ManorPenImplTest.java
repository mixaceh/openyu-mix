package org.openyu.mix.manor.vo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.ManorPen.Farm;
import org.openyu.mix.manor.vo.ManorPen.FarmType;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.ManorPenImpl.FarmImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.SystemHelper;

public class ManorPenImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		ManorPen value = new ManorPenImpl();
		//
		Farm farm = new FarmImpl(0);
		value.getFarms().put(farm.getId(), farm);
		//
		String result = CollectorHelper.writeToXml(ManorPenImpl.class, value);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		ManorPen result = CollectorHelper.readFromXml(ManorPenImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=S_00-08, uniqueId=U_00-08
	 * 
	 * @return
	 */
	public static ManorPen mockManorPen() {
		return mockManorPen(null);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=S_00-08, uniqueId=U_00-08
	 * 
	 * @param role
	 * @return
	 */
	public static ManorPen mockManorPen(Role role) {
		ManorPen result = new ManorPenImpl(role);
		FarmType[] farmTypes = FarmType.values();
		for (FarmType farmType : farmTypes) {
			int i = farmType.getValue();
			//
			Farm farm = result.getFarm(i);
			if (farm == null) {
				continue;
			}
			//
			Land land = new LandImpl(Land.UNIQUE_ID_PREFIX + i);
			land.setUniqueId("U_0");
			land.setAmount(1);
			land.setMaxAmount(1);
			farm.setLand(land);
			//
			for (int j = 0; j < ManorPen.Farm.MAX_GRID_SIZE; j++) {
				Seed seed = new SeedImpl();
				seed.setId(Seed.UNIQUE_ID_PREFIX + i + "" + j);
				seed.setUniqueId("U_" + i + "" + j);
				seed.setAmount(1);// 種子數量
				seed.setMaxAmount(1);// 最大堆疊數量
				farm.addSeed(j, seed);
			}
			//
			result.addFarm(farm.getId(), farm);
		}
		return result;
	}

	/**
	 * 模擬莊園欄位,9個相同種子,id=T_00, uniqueId=U_00-08
	 * 
	 * @return
	 */
	public static ManorPen mockManorPenBySameSeed() {
		return mockManorPenBySameSeed(Seed.UNIQUE_ID_PREFIX + "00");
	}

	/**
	 * 模擬莊園欄位,9個相同種子,id=T_00, uniqueId=U_00-08
	 * 
	 * @param id
	 * @return
	 */
	public static ManorPen mockManorPenBySameSeed(String id) {
		return mockManorPenBySameSeed(null, id);
	}

	/**
	 * 模擬莊園欄位,9個相同種子,id=T_xxx, uniqueId=U_00-08
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public static ManorPen mockManorPenBySameSeed(Role role, String id) {
		ManorPen result = new ManorPenImpl(role);
		FarmType[] farmTypes = ManorPen.FarmType.values();
		for (FarmType farmType : farmTypes) {
			int i = farmType.getValue();
			//
			Farm farm = result.getFarm(i);
			if (farm == null) {
				continue;
			}
			//
			Land land = new LandImpl(Land.UNIQUE_ID_PREFIX + i);
			farm.setLand(land);
			//
			for (int j = 0; j < ManorPen.Farm.MAX_GRID_SIZE; j++) {
				Seed seed = new SeedImpl();
				seed.setId(id);
				seed.setUniqueId("U_" + i + "" + j);
				seed.setAmount(1);// 種子數量
				seed.setMaxAmount(1);// 最大堆疊數量
				farm.addSeed(j, seed);
			}
			//
			result.addFarm(farm.getId(), farm);
		}
		return result;
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=T_00-08, uniqueId=U_00-08
	 * 
	 * @return
	 */
	public static ManorPen mockManorPenByOneFarm() {
		return mockManorPenByOneFarm(null);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=T_00-08, uniqueId=U_00-08
	 * 
	 * @param role
	 * @return
	 */
	public static ManorPen mockManorPenByOneFarm(Role role) {
		ManorPen result = new ManorPenImpl(role);

		int i = 0;
		Farm farm = new FarmImpl(i);// 第1頁
		Land land = new LandImpl(Land.UNIQUE_ID_PREFIX + i);
		farm.setLand(land);
		//
		for (int j = 0; j < ManorPen.Farm.MAX_GRID_SIZE; j++) {
			Seed seed = new SeedImpl();
			seed.setId(Seed.UNIQUE_ID_PREFIX + i + "" + j);
			seed.setUniqueId("U_" + i + "" + j);
			seed.setAmount(1);// 種子數量
			seed.setMaxAmount(1);// 最大堆疊數量
			farm.addSeed(j, seed);
		}
		//
		result.addFarm(farm.getId(), farm);
		return result;
	}

	/**
	 * 隨機種子
	 * 
	 * @return
	 */
	public static Seed randomSeed() {
		Seed result = new SeedImpl();
		result.setId(Seed.UNIQUE_ID_PREFIX + randomString());
		result.setUniqueId("U_" + randomUnique());
		result.setAmount(randomInt(10));// 種子數量
		result.setMaxAmount(randomInt(100));// 最大堆疊數量
		return result;
	}

	@Test
	// 1000000 times: 7 mills.
	// 1000000 times: 6 mills.
	// 1000000 times: 3 mills.
	// verified
	public void getFarmSize() {
		ManorPen manorPen = mockManorPen();
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getFarmSize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(FarmType.values().length, result);
		//
		manorPen.lock(0);
		manorPen.lock(1);
		result = manorPen.getFarmSize();
		System.out.println(result);
		assertEquals(FarmType.values().length - 2, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void isFull() {
		ManorPen manorPen = mockManorPen();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.isFull();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		manorPen.removeSeed(0, 0);
		result = manorPen.isFull();
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addFarm() {
		ManorPen manorPen = new ManorPenImpl();
		manorPen.removeFarm(0);
		ManorPen.Farm farm = new ManorPenImpl.FarmImpl();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.addFarm(0, farm);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);

		result = manorPen.addFarm(-1, farm);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorPen.ErrorType.OVER_FARM_INDEX, result);
		//
		result = manorPen.addFarm(0, null);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorPen.ErrorType.FARM_NOT_EXIST, result);
		//
		manorPen.removeFarm(2);
		result = manorPen.addFarm(0, farm);
		System.out.println(result);
		// 莊園已有農場頁
		assertEquals(ManorPen.ErrorType.ALREADY_HAS_FARM, result);
	}

	//
	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addSeed() {
		ManorPen manorPen = mockManorPen();
		manorPen.removeSeed(0, 0);// 移除一個種子
		Seed seed = randomSeed();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.addSeed(0, 0, seed);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);

		//
		result = manorPen.addSeed(-1, 0, seed);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorPen.ErrorType.OVER_FARM_INDEX, result);
		//
		result = manorPen.addSeed(0, 0, (Seed) null);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorPen.ErrorType.SEED_NOT_EXIST, result);

		// 農場頁滿了
		assertEquals((int) Farm.MAX_GRID_SIZE, (int) manorPen.getFarm(0).getSeedSize());
		result = manorPen.addSeed(0, 0, seed);
		System.out.println(result);
		assertEquals(ManorPen.ErrorType.FARM_FULL, result);
		//
		manorPen.removeSeed(0, 1);
		result = manorPen.addSeed(0, 0, seed);
		System.out.println(result);
		// 格子已有種子
		assertEquals(ManorPen.ErrorType.ALREADY_HAS_SEED, result);
		//
		manorPen.removeFarm(0);// 移除一個農場頁
		result = manorPen.addSeed(0, 0, seed);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorPen.ErrorType.FARM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeSeed() {
		ManorPen manorPen = mockManorPen();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.removeSeed(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		//
		result = manorPen.removeSeed(0, -1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = manorPen.removeSeed(0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorPen.ErrorType.SEED_NOT_EXIST, result);
		//
		manorPen.removeFarm(0);// 移除一個農場頁
		result = manorPen.removeSeed(0, 0);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorPen.ErrorType.FARM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeSeedByUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		ManorPen manorPen = mockManorPen();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.removeSeed(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		//
		result = manorPen.removeSeed(UNIQUE_ID);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorPen.ErrorType.SEED_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearSeed() {
		ManorPen manorPen = mockManorPen();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.clearSeed(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		assertEquals(FarmType.values().length, manorPen.getFarmSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearFarm() {
		ManorPen manorPen = mockManorPen();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.clearFarm();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		assertEquals(0, manorPen.getFarmSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getFarm() {
		ManorPen manorPen = mockManorPen();
		//
		ManorPen.Farm result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getFarm(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = manorPen.getFarm(-1);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getSeed() {
		ManorPen manorPen = mockManorPen();
		//
		Seed result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getSeed(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
		//
		result = manorPen.getSeed(-1, 0);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 47 mills.
	// 1000000 times: 44 mills.
	// 1000000 times: 47 mills.
	// verified
	public void getSeedByUniqueId() {
		final String UNIQUE_ID = "U_00";

		ManorPen manorPen = mockManorPen();
		//
		Seed result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getSeed(UNIQUE_ID);
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
	public void getSeeds() {
		final String ID = "S_00";

		ManorPen manorPen = mockManorPen();
		//
		List<Seed> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getSeeds(ID);
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
	public void setFarm() {
		final Integer INDEX = 0;
		ManorPen manorPen = mockManorPen();
		ManorPen.Farm farm = new ManorPenImpl.FarmImpl();
		farm.setId(INDEX);
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.setFarm(INDEX, farm);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		//
		result = manorPen.setFarm(-1, farm);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorPen.ErrorType.OVER_FARM_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setSeed() {
		ManorPen manorPen = mockManorPen();
		Seed thing = randomSeed();
		//
		ManorPen.ErrorType result = ManorPen.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.setSeed(0, 0, thing);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorPen.ErrorType.NO_ERROR, result);
		//
		result = manorPen.setSeed(0, -1, thing);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorPen.ErrorType.OVER_GRID_INDEX, result);
		//
		result = manorPen.setSeed(-1, 0, thing);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorPen.ErrorType.OVER_FARM_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void containIndex() {
		final Integer INDEX = 0;

		ManorPen manorPen = mockManorPen();
		//
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.containIndex(INDEX);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = manorPen.containIndex(-1);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getFarmIndexs() {
		ManorPen manorPen = mockManorPen();
		//
		List<Integer> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getFarmIndexs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		result = manorPen.getFarmIndexs(true);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getIndex() {
		final String UNIQUE_ID = "U_00";

		ManorPen manorPen = mockManorPen();
		//
		int[] result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getIndex(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result[0] + ", " + result[1]);
		// 種子不存在
		result = manorPen.getIndex("U_xxx");
		// System.out.println(result[0] + ", " + result[1]);
		assertNull(result);
	}

	@Test
	// 1000000 times: 557 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 538 mills.
	// verified
	public void getIndexsById() {
		final String ID = "S_00";

		ManorPen manorPen = mockManorPenBySameSeed();// 9個相同種子
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getIndexs(ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(9, result.size());
	}

	@Test
	// 1000000 times: 557 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 538 mills.
	// verified
	public void getIndexs() {
		ManorPen manorPen = mockManorPenBySameSeed();// 9個相同種子
		manorPen.removeSeed(0, 0);// 移掉1個
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getIndexs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(8, result.size());
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptySize() {
		ManorPen manorPen = mockManorPenBySameSeed();// 81個相同種子
		manorPen.removeSeed(0, 0);// 移除index=0,0
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getEmptySize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1, result);
		//
		manorPen.clearSeed(0);
		result = manorPen.getEmptySize();
		System.out.println(result);
		assertEquals(9, result);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptyIndex() {
		ManorPen manorPen = mockManorPenBySameSeed();// 81個相同物品
		manorPen.removeSeed(0, 8);// 移除index=0,8
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorPen.getEmptyIndex();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(8, result[1]);
		//
		manorPen.clearSeed(0);
		result = manorPen.getEmptyIndex();
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);
	}

}
