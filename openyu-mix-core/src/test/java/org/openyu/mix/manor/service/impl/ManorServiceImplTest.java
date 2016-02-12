package org.openyu.mix.manor.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.ManorTestSupporter;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.service.ManorService.CultureAllResult;
import org.openyu.mix.manor.service.ManorService.CultureResult;
import org.openyu.mix.manor.service.ManorService.ErrorType;
import org.openyu.mix.manor.service.ManorService.ReclaimResult;
import org.openyu.mix.manor.service.ManorService.SeedResult;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.ManorPen.Farm;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.manor.vo.impl.ManorPenImplTest;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.socklet.message.vo.Message;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class ManorServiceImplTest extends ManorTestSupporter {

	@Test
	/**
	 * 連線
	 */
	public void connect() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		manorService.roleConnect(role.getId(), null);
	}

	@Test
	/**
	 * 監聽
	 */
	public void listen() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		Seed seed = manorPen.getSeed(0, 0);

		// 祈禱,減少成長毫秒
		seed.setPrayTime(System.currentTimeMillis());

		// 澆水,增加成長速度
		seed.setWaterTime(System.currentTimeMillis());

		// 土地,增加成長速度,AttributeType.MANOR_SPEED_RATE
		Land land = manorPen.getFarm(0).getLand();
		// 強化
		land.setEnhanceLevel(EnhanceLevel._10);
		itemService.calcEnhanceAttributes(land);

		System.out.println(land.getEnhanceLevel() + " " + land.getAttributeGroup());

		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	public void sendManorPen() {
		Role role = mockRole();
		ManorPen manorPen = ManorPenImplTest.mockManorPen();
		role.setManorPen(manorPen);
		//
		manorService.sendManorPen(role, role.getManorPen());
	}

	@Test
	public void fillManorPen() {
		Message result = null;
		//
		Role role = mockRole();
		ManorPen manorPen = ManorPenImplTest.mockManorPen();
		role.setManorPen(manorPen);
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT, null, role.getId());
			manorService.fillManorPen(result, manorPen);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		// 所有tab
		System.out.println(result);

		// 移除1個種子
		manorPen.removeSeed(0, 0);
		// 鎖定 TabType._1,TabType._2
		manorPen.lock(1);
		manorPen.lock(2);
		//
		result = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT, null, role.getId());
		manorService.fillManorPen(result, manorPen);
		System.out.println(result);

		// 移除 TabType._0 的土地
		manorPen.getFarm(0).setLand(null);
		result = messageService.createMessage(CoreModuleType.MANOR, CoreModuleType.CLIENT, null, role.getId());
		manorService.fillManorPen(result, manorPen);
		System.out.println(result);
	}

	/**
	 * 開墾
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 1.64 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.11, time.total: 1.64, time.warmup: 0.00,
	// time.bench: 1.64
	public void reclaim() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		BagInfo bagInfo = role.getBagInfo();

		// 土地,塞到包包去
		Land land = itemService.createLand("L_TROPICS_G001");
		bagInfo.addItem(0, 0, land);

		// 農場結果
		ReclaimResult result = manorService.reclaim(true, role, 0, land.getUniqueId());
		System.out.println(result);
		assertNotNull(result);

		// 莊園
		ManorPen manorPen = role.getManorPen();
		assertEquals(1, manorPen.getFarmSize());
		// 農場
		Farm farm = manorPen.getFarm(0);
		assertEquals(land, farm.getLand());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 檢查開墾
	 */
	public void checkReclaim() {
		Role role = mockRole();
		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 莊園
		ManorPen manorPen = role.getManorPen();
		// 土地
		Land land = itemService.createLand("L_TROPICS_G001");
		//
		ErrorType errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 金幣不足
		assertEquals(ErrorType.GOLD_NOT_ENOUGH, errorType);

		role.setGold(10000 * 10000L);// 1e
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 土地不存在
		assertEquals(ErrorType.LAND_NOT_EXIST, errorType);

		// 土地,塞到包包去
		bagInfo.addItem(0, 0, land);
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);
		//
		role.setLevel(20);// 等級
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);

		manorPen.lock(0);// 鎖定
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, errorType);

		manorPen.unLock(0);
		Farm farm = manorPen.getFarm(0);
		farm.setLand(land);
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 已有土地
		assertEquals(ErrorType.ALREADY_HAD_LAND, errorType);

		farm.setLand(null);
		land.setAmount(0);// 數量=0;
		errorType = manorService.checkReclaim(role, 0, land.getUniqueId());
		System.out.println(errorType);
		// 無法扣除土地
		assertEquals(ErrorType.CAN_NOT_DECREASE_LAND, errorType);
	}

	/**
	 * 休耕
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 1.47 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.10, time.total: 1.47, time.warmup: 0.00,
	// time.bench: 1.47
	public void disuse() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		BagInfo bagInfo = role.getBagInfo();

		// 土地,塞到包包去
		Land land = itemService.createLand("L_TROPICS_G001");
		bagInfo.addItem(0, 0, land);
		// 開墾
		ReclaimResult result = manorService.reclaim(true, role, 0, land.getUniqueId());
		// System.out.println(result);
		assertNotNull(result);

		// 休耕
		result = manorService.disuse(true, role, 0);
		// System.out.println(result);
		assertNotNull(result);

		// 莊園
		ManorPen manorPen = role.getManorPen();
		assertEquals(1, manorPen.getFarmSize());
		// 農場
		Farm farm = manorPen.getFarm(0);
		// 休耕,所以農場應沒土地了
		assertNull(farm.getLand());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 檢查休耕
	 */
	public void checkDisuse() {
		Role role = mockRole();
		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 莊園
		ManorPen manorPen = role.getManorPen();
		// 土地
		Land land = itemService.createLand("L_TROPICS_G001");
		//
		ErrorType errorType = manorService.checkDisuse(role, 0);
		System.out.println(errorType);
		// 金幣不足
		assertEquals(ErrorType.GOLD_NOT_ENOUGH, errorType);

		role.setGold(10000 * 10000L);// 1e
		manorPen.lock(0);
		errorType = manorService.checkDisuse(role, 0);
		System.out.println(errorType);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, errorType);

		manorPen.unLock(0);
		errorType = manorService.checkDisuse(role, 0);
		System.out.println(errorType);
		// 土地不存在
		assertEquals(ErrorType.LAND_NOT_EXIST, errorType);

		Farm farm = manorPen.getFarm(0);
		farm.setLand(land);
		errorType = manorService.checkDisuse(role, 0);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);

		land.setAmount(0);
		errorType = manorService.checkDisuse(role, 0);
		System.out.println(errorType);
		// 無法增加土地
		assertEquals(ErrorType.CAN_NOT_INCREASE_LAND, errorType);
	}

	/**
	 * 種植,澆水,清除
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 2.58 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.08, time.total: 2.58, time.warmup: 0.00,
	// time.bench: 2.58
	public void culture() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);

		// 種植
		CultureResult result = manorService.culture(true, role, CultureType.PLANT.getValue(), 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);

		// 加道具到包包
		Item item = itemService.createItem(manorCollector.getWaterItem());
		bagInfo.addItem(0, 1, item);
		// 澆水
		result = manorService.culture(true, role, CultureType.WATER.getValue(), 0, 0, null);
		System.out.println(result);
		assertNotNull(result);

		// 種子2
		seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 沒道具扣儲值幣
		result = manorService.culture(true, role, CultureType.WATER.getValue(), 0, 1, null);
		System.out.println(result);
		assertNotNull(result);

		// 清除
		result = manorService.culture(true, role, CultureType.CLEAR.getValue(), 0, 0, null);
		System.out.println(result);
		assertNotNull(result);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 種植,然後裝成熟,再收割
	 */
	public void cultureWithHarvest() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e

		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);

		// 模擬開墾
		mockReclaim(role);
		// 莊園
		ManorPen manorPen = role.getManorPen();

		// 種植
		CultureResult result = manorService.culture(true, role, CultureType.PLANT.getValue(), 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);

		// 來吧,裝成熟
		Seed farmSeed = manorPen.getSeed(0, 0);
		farmSeed.setMatureTime(System.currentTimeMillis());
		farmSeed.setMatureType(MatureType.MATURE);

		// 收割
		result = manorService.culture(true, role, CultureType.HARVEST.getValue(), 0, 0, null);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 種植,然後加速,直接成熟,直接收割
	 */
	public void cultureWithSpeed() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e

		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);

		// 加莊園加速石到包包
		itemService.increaseItemWithItemId(true, role, "T_MANOR_SPEED_G001", 1);

		// 模擬開墾
		mockReclaim(role);
		// 莊園
		ManorPen manorPen = role.getManorPen();

		// 種植
		CultureResult result = manorService.culture(true, role, CultureType.PLANT.getValue(), 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);

		// 加速
		result = manorService.culture(true, role, CultureType.SPEED.getValue(), 0, 0, null);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 種植,搞枯萎,然後復活,把枯萎的復活,直接收割
	 */
	public void cultureWithRevive() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e

		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);

		// 模擬開墾
		mockReclaim(role);
		// 莊園
		ManorPen manorPen = role.getManorPen();

		// 種植
		CultureResult result = manorService.culture(true, role, CultureType.PLANT.getValue(), 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);

		// 來吧,搞枯萎
		Seed farmSeed = manorPen.getSeed(0, 0);
		farmSeed.setMatureTime(System.currentTimeMillis());
		farmSeed.setMatureType(MatureType.WITHER);

		// 復活
		result = manorService.culture(true, role, CultureType.REVIVE.getValue(), 0, 0, null);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 計算種子成熟剩餘時間
	 */
	// 1000000 times: 1747 mills.
	// 1000000 times: 1760 mills.
	// 1000000 times: 1760 mills.
	public void calcResidual() {
		// 土地
		Land land = itemService.createLand("L_TROPICS_G001");
		// 強化
		land.setEnhanceLevel(EnhanceLevel._30);
		itemService.calcEnhanceAttributes(land);
		System.out.println(land.getEnhanceLevel() + " " + land.getAttributeGroup());

		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 1);
		seed.setPlantTime(System.currentTimeMillis());
		// 祈禱,減少成長毫秒
		seed.setPrayTime(System.currentTimeMillis());
		// 澆水,增加成長速度
		seed.setWaterTime(System.currentTimeMillis() + 5000);

		long result = 0;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcResidual(land, seed);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);

	}

	@Test
	/**
	 * 計算種子產出數量
	 */
	// 1000000 times: 1747 mills.
	// 1000000 times: 1760 mills.
	// 1000000 times: 1760 mills.
	public void calcProducts() {
		// 土地
		Land land = itemService.createLand("L_TROPICS_G001");
		// 強化
		land.setEnhanceLevel(EnhanceLevel._7);
		itemService.calcEnhanceAttributes(land);
		System.out.println(land.getEnhanceLevel() + " " + land.getAttributeGroup());

		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 1);
		seed.setPlantTime(System.currentTimeMillis());
		seed.setWaterTime(System.currentTimeMillis() + 5000);

		Map<String, Integer> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcProducts(land, seed);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);// 39-50
	}

	@Test
	/**
	 * 種植
	 */
	public void plant() {
		Role role = mockRole();
		role.setLevel(20);// 等級

		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 1);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);

		// 模擬開墾
		mockReclaim(role);
		//
		CultureResult result = manorService.plant(true, role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	/**
	 * 檢查種植
	 */
	// 1000000 times: 1525 mills.
	// 1000000 times: 1506 mills.
	public void checkPlant() {
		ErrorType result = ErrorType.NO_ERROR;
		//
		Role role = mockRole();
		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 1);
		//
		result = manorService.checkPlant(role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		// 包包種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);
		result = manorService.checkPlant(role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, result);
		//
		role.setLevel(20);// 等級
		ManorPen manorPen = role.getManorPen();
		manorPen.lock(0);// 鎖定農場
		result = manorService.checkPlant(role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);
		//
		// 模擬開墾
		manorPen.unLock(0);// 解鎖農場
		mockReclaim(role);
		result = manorService.checkPlant(role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
		//
		manorPen.addSeed(0, 0, seed);
		result = manorService.checkPlant(role, 0, 0, seed.getUniqueId());
		System.out.println(result);
		// 已有種子
		assertEquals(ErrorType.ALREADY_HAD_SEED, result);
	}

	@Test
	/**
	 * 澆水
	 */
	public void water() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);

		// 加道具到包包
		Item item = itemService.createItem(manorCollector.getWaterItem());
		bagInfo.addItem(0, 0, item);
		//
		CultureResult result = manorService.water(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);

		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 沒道具扣儲值幣
		result = manorService.water(true, role, 0, 1);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查澆水
	 */
	public void checkWater() {
		Role role = mockRole();
		// BagInfo bagInfo = role.getBagInfo();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkWater(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkWater(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);
		Seed seed = manorPen.getSeed(0, 0);
		seed.setWaterTime(System.currentTimeMillis());
		result = manorService.checkWater(role, 0, 0);
		System.out.println(result);
		// 種子已澆水
		assertEquals(ErrorType.ALREADY_WATER, result);
	}

	@Test
	/**
	 * 祈禱
	 */
	public void pray() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		// 加道具到包包
		Item item = itemService.createItem(manorCollector.getPrayItem());
		bagInfo.addItem(0, 0, item);
		//
		CultureResult result = manorService.pray(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);

		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 沒道具扣儲值幣
		result = manorService.pray(true, role, 0, 1);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	/**
	 * 檢查祈禱
	 */
	public void checkPray() {
		Role role = mockRole();
		// BagInfo bagInfo = role.getBagInfo();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkPray(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkPray(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);
		Seed seed = manorPen.getSeed(0, 0);
		seed.setPrayTime(System.currentTimeMillis());
		result = manorService.checkPray(role, 0, 0);
		System.out.println(result);
		// 種子已祈禱
		assertEquals(ErrorType.ALREADY_PRAY, result);
	}

	@Test
	/**
	 * 加速
	 */
	public void speed() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		// 加道具到包包
		Item item = itemService.createItem(manorCollector.getSpeedItem());
		bagInfo.addItem(0, 0, item);
		//
		CultureResult result = manorService.speed(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 沒道具扣儲值幣
		result = manorService.speed(true, role, 0, 1);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查加速
	 */
	public void checkSpeed() {
		Role role = mockRole();
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		// BagInfo bagInfo = role.getBagInfo();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkSpeed(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkSpeed(role, 0, 0);
		System.out.println(result);
		// 土地不存在
		assertEquals(ErrorType.LAND_NOT_EXIST, result);

		// 模擬開墾
		mockReclaim(role);
		result = manorService.checkSpeed(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);

		Seed seed = manorPen.getSeed(0, 0);
		// 搞成沒種植
		seed.setPlantTime(0);
		seed.setMatureType(MatureType.PENDING);

		result = manorService.checkSpeed(role, 0, 0);
		System.out.println(result);
		// 沒在成長中
		assertEquals(ErrorType.NOT_GROWING, result);
	}

	@Test
	/**
	 * 收割
	 */
	public void harvest() {
		Role role = mockRole();
		ManorPen manorPen = role.getManorPen();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);

		Seed seed = manorPen.getSeed(0, 0);
		// 來吧,裝成熟
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.MATURE);
		//
		CultureResult result = manorService.harvest(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	/**
	 * 檢查收割
	 */
	public void checkHarvest() {
		Role role = mockRole();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkHarvest(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkHarvest(role, 0, 0);
		System.out.println(result);
		// 土地不存在
		assertEquals(ErrorType.LAND_NOT_EXIST, result);

		// 模擬開墾
		mockReclaim(role);
		result = manorService.checkHarvest(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);
		result = manorService.checkHarvest(role, 0, 0);
		System.out.println(result);
		// 還沒成熟
		assertEquals(ErrorType.NOT_MATURE, result);

		Seed seed = manorPen.getSeed(0, 0);
		// 來吧,裝成熟
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.MATURE);

		result = manorService.checkHarvest(role, 0, 0);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, result);
	}

	@Test
	/**
	 * 復活
	 */
	public void revive() {
		Role role = mockRole();
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		BagInfo bagInfo = role.getBagInfo();
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);

		Seed seed = manorPen.getSeed(0, 0);
		// 搞成枯萎
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.WITHER);
		//
		// 加道具到包包
		Item item = itemService.createItem(manorCollector.getReviveItem());
		bagInfo.addItem(0, 0, item);
		//
		CultureResult result = manorService.revive(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);

		// 種子2
		seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 搞成枯萎
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.WITHER);

		// 沒道具扣儲值幣
		result = manorService.revive(true, role, 0, 1);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查復活
	 */
	public void checkRevive() {
		Role role = mockRole();
		// BagInfo bagInfo = role.getBagInfo();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkRevive(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkRevive(role, 0, 0);
		System.out.println(result);
		// 土地不存在
		assertEquals(ErrorType.LAND_NOT_EXIST, result);

		// 模擬開墾
		mockReclaim(role);
		result = manorService.checkRevive(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);
		Seed seed = manorPen.getSeed(0, 0);

		// 搞成成長中
		seed.setMatureTime(0);
		seed.setMatureType(MatureType.GROWING);
		result = manorService.checkRevive(role, 0, 0);
		System.out.println(result);
		// 還沒枯萎
		assertEquals(ErrorType.NOT_WITHER, result);
	}

	@Test
	/**
	 * 清除
	 */
	public void clear() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		CultureResult result = manorService.clear(true, role, 0, 0);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	/**
	 * 檢查清除
	 */
	public void checkClear() {
		Role role = mockRole();
		ManorPen manorPen = role.getManorPen();
		//
		manorPen.lock(0);
		ErrorType result = manorService.checkClear(role, 0, 0);
		System.out.println(result);
		// 農場不存在
		assertEquals(ErrorType.FARM_NOT_EXIST, result);

		manorPen.unLock(0);
		result = manorService.checkClear(role, 0, 0);
		System.out.println(result);
		// 種子不存在
		assertEquals(ErrorType.SEED_NOT_EXIST, result);

		// 模擬種植
		mockPlant(role);
		//
		result = manorService.checkClear(role, 0, 0);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
	}

	@Test
	/**
	 * 計算成長中的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcGrowings() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcGrowings(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(3, result.size());
		//
		seed.setWaterTime(System.currentTimeMillis());
		result = manorService.calcGrowings(manorPen);
		System.out.println(result);
		assertEquals(3, result.size());
	}

	@Test
	/**
	 * 計算可以澆水的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcCanWaters() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcCanWaters(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(3, result.size());
		//
		seed.setWaterTime(System.currentTimeMillis());
		result = manorService.calcCanWaters(manorPen);
		System.out.println(result);
		assertEquals(2, result.size());
	}

	@Test
	/**
	 * 計算可以祈禱的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcCanPrays() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcCanPrays(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(3, result.size());
		//
		seed.setPrayTime(System.currentTimeMillis());
		result = manorService.calcCanPrays(manorPen);
		System.out.println(result);
		assertEquals(2, result.size());
	}

	@Test
	/**
	 * 計算可以加速的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcCanSpeeds() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcCanSpeeds(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(3, result.size());
		//
		seed.setMatureType(MatureType.MATURE);
		result = manorService.calcCanSpeeds(manorPen);
		System.out.println(result);
		assertEquals(2, result.size());
	}

	@Test
	/**
	 * 計算可以收割的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcCanHarvests() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.MATURE);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.MATURE);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcCanHarvests(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(2, result.size());
		//
		seed.setMatureTime(0);
		result = manorService.calcCanHarvests(manorPen);
		System.out.println(result);
		assertEquals(1, result.size());
	}

	@Test
	/**
	 * 計算可以復活的種子
	 */
	// 1000000 times: 2546 mills.
	// 1000000 times: 2423 mills.
	// 1000000 times: 1760 mills.
	public void calcCanRevives() {
		Role role = mockRole();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.WITHER);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		seed.setMatureTime(System.currentTimeMillis());
		seed.setMatureType(MatureType.WITHER);
		//
		List<SeedResult> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = manorService.calcCanRevives(manorPen);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(2, result.size());
		//
		seed.setMatureTime(0);
		result = manorService.calcCanRevives(manorPen);
		System.out.println(result);
		assertEquals(1, result.size());
	}

	@Test
	/**
	 * 澆水所有
	 * 
	 * 只消耗道具
	 */
	public void waterAll() {
		Role role = mockRole();
		role.setVipType(VipType._2);
		BagInfo bagInfo = role.getBagInfo();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		// 加5個道具到包包
		Item item = itemService.createItem(manorCollector.getWaterItem(), 1);
		bagInfo.addItem(0, 0, item);
		item = itemService.createItem(manorCollector.getWaterItem(), 4);
		bagInfo.addItem(0, 1, item);
		//
		CultureAllResult result = manorService.waterAll(true, role);// 會消耗3個道具
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 澆水所有
	 * 
	 * 消耗道具時,道具數量不足,就會消耗儲值幣,若儲值幣不足或扣coin的vip等級不夠,就不做了
	 */
	public void waterAllByItemCoin() {
		Role role = mockRole();
		role.setVipType(VipType._2);
		BagInfo bagInfo = role.getBagInfo();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		// 加1個道具到包包
		Item item = itemService.createItem(manorCollector.getWaterItem(), 1);
		bagInfo.addItem(0, 0, item);
		//
		manorService.waterAll(true, role);// 會消耗1個道具,2次儲值幣的總數
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 種植,澆水
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 2.35 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.06, time.total: 2.35, time.warmup: 0.00,
	// time.bench: 2.35
	public void cultureAll() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		// 包包
		BagInfo bagInfo = role.getBagInfo();
		// 種子
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);
		// 種子,塞到包包去
		bagInfo.addItem(0, 0, seed);
		ManorPen manorPen = role.getManorPen();

		// 模擬開墾
		mockReclaim(role);

		// 種植
		CultureResult result = manorService.culture(true, role, 1, 0, 0, seed.getUniqueId());
		System.out.println(result);
		assertNotNull(result);
		//
		// 種子2
		seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);

		// 加1個道具到包包
		Item item = itemService.createItem(manorCollector.getWaterItem());
		bagInfo.addItem(0, 1, item);
		//
		CultureAllResult allResult = manorService.cultureAll(true, role, 2);// 會消耗1個道具,1次儲值幣的總數
		System.out.println(allResult);
		assertNotNull(result);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 澆水所有
	 * 
	 * 沒道具,直接消耗儲值幣
	 */
	public void waterAllByCoin() {
		Role role = mockRole();
		role.setVipType(VipType._2);
		// BagInfo bagInfo = role.getBagInfo();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		manorService.waterAll(true, role);// 3次儲值幣的總數
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 祈禱所有
	 * 
	 * 只消耗道具
	 */
	public void prayAll() {
		Role role = mockRole();
		role.setVipType(VipType._2);
		BagInfo bagInfo = role.getBagInfo();
		// 模擬開墾
		mockReclaim(role);
		// 模擬種植
		mockPlant(role);
		//
		ManorPen manorPen = role.getManorPen();
		// 種子2
		Seed seed = mockSeed("S_COTTON_G001");
		manorPen.addSeed(0, 1, seed);
		// 種子3
		seed = mockSeed("S_OAK_G001");
		manorPen.addSeed(0, 2, seed);
		//
		// 加5個道具到包包
		Item item = itemService.createItem(manorCollector.getPrayItem(), 1);
		bagInfo.addItem(0, 0, item);
		item = itemService.createItem(manorCollector.getPrayItem(), 4);
		bagInfo.addItem(0, 1, item);
		//
		CultureAllResult result = manorService.prayAll(true, role);// 會消耗3個道具
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}
}
