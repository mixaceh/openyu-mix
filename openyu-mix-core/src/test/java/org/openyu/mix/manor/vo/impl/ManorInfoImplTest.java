package org.openyu.mix.manor.vo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.manor.vo.ManorInfo.Farm;
import org.openyu.mix.manor.vo.ManorInfo.FarmType;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.ManorInfoImpl.FarmImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.SystemHelper;

public class ManorInfoImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		ManorInfo value = new ManorInfoImpl();
		//
		Farm farm = new FarmImpl(0);
		value.getFarms().put(farm.getId(), farm);
		//
		String result = CollectorHelper.writeToXml(ManorInfoImpl.class, value);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		ManorInfo result = CollectorHelper.readFromXml(ManorInfoImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=S_00-08, uniqueId=U_00-08
	 * 
	 * @return
	 */
	public static ManorInfo mockManorInfo() {
		return mockManorInfo(null);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=S_00-08, uniqueId=U_00-08
	 * 
	 * @param role
	 * @return
	 */
	public static ManorInfo mockManorInfo(Role role) {
		ManorInfo result = new ManorInfoImpl(role);
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
			for (int j = 0; j < ManorInfo.Farm.MAX_GRID_SIZE; j++) {
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
	public static ManorInfo mockManorInfoBySameSeed() {
		return mockManorInfoBySameSeed(Seed.UNIQUE_ID_PREFIX + "00");
	}

	/**
	 * 模擬莊園欄位,9個相同種子,id=T_00, uniqueId=U_00-08
	 * 
	 * @param id
	 * @return
	 */
	public static ManorInfo mockManorInfoBySameSeed(String id) {
		return mockManorInfoBySameSeed(null, id);
	}

	/**
	 * 模擬莊園欄位,9個相同種子,id=T_xxx, uniqueId=U_00-08
	 * 
	 * @param role
	 * @param id
	 * @return
	 */
	public static ManorInfo mockManorInfoBySameSeed(Role role, String id) {
		ManorInfo result = new ManorInfoImpl(role);
		FarmType[] farmTypes = ManorInfo.FarmType.values();
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
			for (int j = 0; j < ManorInfo.Farm.MAX_GRID_SIZE; j++) {
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
	public static ManorInfo mockManorInfoByOneFarm() {
		return mockManorInfoByOneFarm(null);
	}

	/**
	 * 模擬莊園欄位,1個田地頁(farm),每頁9個不同種子,id=T_00-08, uniqueId=U_00-08
	 * 
	 * @param role
	 * @return
	 */
	public static ManorInfo mockManorInfoByOneFarm(Role role) {
		ManorInfo result = new ManorInfoImpl(role);

		int i = 0;
		Farm farm = new FarmImpl(i);// 第1頁
		Land land = new LandImpl(Land.UNIQUE_ID_PREFIX + i);
		farm.setLand(land);
		//
		for (int j = 0; j < ManorInfo.Farm.MAX_GRID_SIZE; j++) {
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
		ManorInfo manorInfo = mockManorInfo();
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getFarmSize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(FarmType.values().length, result);
		//
		manorInfo.lock(0);
		manorInfo.lock(1);
		result = manorInfo.getFarmSize();
		System.out.println(result);
		assertEquals(FarmType.values().length - 2, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void isFull() {
		ManorInfo manorInfo = mockManorInfo();
		//
		Boolean result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.isFull();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		manorInfo.removeSeed(0, 0);
		result = manorInfo.isFull();
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addFarm() {
		ManorInfo manorInfo = new ManorInfoImpl();
		manorInfo.removeFarm(0);
		ManorInfo.Farm farm = new ManorInfoImpl.FarmImpl();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.addFarm(0, farm);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);

		result = manorInfo.addFarm(-1, farm);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorInfo.ErrorType.OVER_FARM_INDEX, result);
		//
		result = manorInfo.addFarm(0, null);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorInfo.ErrorType.FARM_NOT_EXIST, result);
		//
		manorInfo.removeFarm(2);
		result = manorInfo.addFarm(0, farm);
		System.out.println(result);
		// 莊園已有農場頁
		assertEquals(ManorInfo.ErrorType.ALREADY_HAS_FARM, result);
	}

	//
	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addSeed() {
		ManorInfo manorInfo = mockManorInfo();
		manorInfo.removeSeed(0, 0);// 移除一個種子
		Seed seed = randomSeed();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.addSeed(0, 0, seed);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);

		//
		result = manorInfo.addSeed(-1, 0, seed);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorInfo.ErrorType.OVER_FARM_INDEX, result);
		//
		result = manorInfo.addSeed(0, 0, (Seed) null);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorInfo.ErrorType.SEED_NOT_EXIST, result);

		// 農場頁滿了
		assertEquals((int) Farm.MAX_GRID_SIZE, (int) manorInfo.getFarm(0).getSeedSize());
		result = manorInfo.addSeed(0, 0, seed);
		System.out.println(result);
		assertEquals(ManorInfo.ErrorType.FARM_FULL, result);
		//
		manorInfo.removeSeed(0, 1);
		result = manorInfo.addSeed(0, 0, seed);
		System.out.println(result);
		// 格子已有種子
		assertEquals(ManorInfo.ErrorType.ALREADY_HAS_SEED, result);
		//
		manorInfo.removeFarm(0);// 移除一個農場頁
		result = manorInfo.addSeed(0, 0, seed);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorInfo.ErrorType.FARM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeSeed() {
		ManorInfo manorInfo = mockManorInfo();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.removeSeed(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		//
		result = manorInfo.removeSeed(0, -1);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorInfo.ErrorType.OVER_GRID_INDEX, result);
		//
		result = manorInfo.removeSeed(0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorInfo.ErrorType.SEED_NOT_EXIST, result);
		//
		manorInfo.removeFarm(0);// 移除一個農場頁
		result = manorInfo.removeSeed(0, 0);
		System.out.println(result);
		// 農場頁不存在
		assertEquals(ManorInfo.ErrorType.FARM_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeSeedByUniqueId() {
		final String UNIQUE_ID = "U_00";
		//
		ManorInfo manorInfo = mockManorInfo();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.removeSeed(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		//
		result = manorInfo.removeSeed(UNIQUE_ID);
		System.out.println(result);
		// 種子不存在
		assertEquals(ManorInfo.ErrorType.SEED_NOT_EXIST, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearSeed() {
		ManorInfo manorInfo = mockManorInfo();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.clearSeed(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		assertEquals(FarmType.values().length, manorInfo.getFarmSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void clearFarm() {
		ManorInfo manorInfo = mockManorInfo();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.clearFarm();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		assertEquals(0, manorInfo.getFarmSize());
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getFarm() {
		ManorInfo manorInfo = mockManorInfo();
		//
		ManorInfo.Farm result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getFarm(0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = manorInfo.getFarm(-1);
		System.out.println(result);
		assertNull(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getSeed() {
		ManorInfo manorInfo = mockManorInfo();
		//
		Seed result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getSeed(0, 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId() + ", " + result.getUniqueId());
		assertNotNull(result);
		//
		result = manorInfo.getSeed(-1, 0);
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

		ManorInfo manorInfo = mockManorInfo();
		//
		Seed result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getSeed(UNIQUE_ID);
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

		ManorInfo manorInfo = mockManorInfo();
		//
		List<Seed> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getSeeds(ID);
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
		ManorInfo manorInfo = mockManorInfo();
		ManorInfo.Farm farm = new ManorInfoImpl.FarmImpl();
		farm.setId(INDEX);
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.setFarm(INDEX, farm);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		//
		result = manorInfo.setFarm(-1, farm);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorInfo.ErrorType.OVER_FARM_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void setSeed() {
		ManorInfo manorInfo = mockManorInfo();
		Seed thing = randomSeed();
		//
		ManorInfo.ErrorType result = ManorInfo.ErrorType.NO_ERROR;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.setSeed(0, 0, thing);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ManorInfo.ErrorType.NO_ERROR, result);
		//
		result = manorInfo.setSeed(0, -1, thing);
		System.out.println(result);
		// 超過格子索引
		assertEquals(ManorInfo.ErrorType.OVER_GRID_INDEX, result);
		//
		result = manorInfo.setSeed(-1, 0, thing);
		System.out.println(result);
		// 超過農場頁索引
		assertEquals(ManorInfo.ErrorType.OVER_FARM_INDEX, result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void containIndex() {
		final Integer INDEX = 0;

		ManorInfo manorInfo = mockManorInfo();
		//
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.containIndex(INDEX);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = manorInfo.containIndex(-1);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getFarmIndexs() {
		ManorInfo manorInfo = mockManorInfo();
		//
		List<Integer> result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getFarmIndexs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		result = manorInfo.getFarmIndexs(true);
		System.out.println(result);
	}

	@Test
	// 1000000 times: 32 mills.
	// 1000000 times: 37 mills.
	// 1000000 times: 32 mills.
	// verified
	public void getIndex() {
		final String UNIQUE_ID = "U_00";

		ManorInfo manorInfo = mockManorInfo();
		//
		int[] result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getIndex(UNIQUE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result[0] + ", " + result[1]);
		// 種子不存在
		result = manorInfo.getIndex("U_xxx");
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

		ManorInfo manorInfo = mockManorInfoBySameSeed();// 9個相同種子
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getIndexs(ID);
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
		ManorInfo manorInfo = mockManorInfoBySameSeed();// 9個相同種子
		manorInfo.removeSeed(0, 0);// 移掉1個
		//
		List<int[]> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getIndexs();
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
		ManorInfo manorInfo = mockManorInfoBySameSeed();// 81個相同種子
		manorInfo.removeSeed(0, 0);// 移除index=0,0
		//
		int result = 0;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getEmptySize();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(1, result);
		//
		manorInfo.clearSeed(0);
		result = manorInfo.getEmptySize();
		System.out.println(result);
		assertEquals(9, result);
	}

	@Test
	// 1000000 times: 611 mills.
	// 1000000 times: 551 mills.
	// 1000000 times: 528 mills.
	// verified
	public void getEmptyIndex() {
		ManorInfo manorInfo = mockManorInfoBySameSeed();// 81個相同物品
		manorInfo.removeSeed(0, 8);// 移除index=0,8
		//
		int[] result = null;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = manorInfo.getEmptyIndex();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(8, result[1]);
		//
		manorInfo.clearSeed(0);
		result = manorInfo.getEmptyIndex();
		SystemHelper.println(result);
		assertEquals(0, result[0]);
		assertEquals(0, result[1]);
	}

}
