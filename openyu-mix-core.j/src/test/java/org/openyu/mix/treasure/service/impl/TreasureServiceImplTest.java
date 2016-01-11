package org.openyu.mix.treasure.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.TreasureTestSupporter;
import org.openyu.mix.treasure.service.TreasureService.BuyResult;
import org.openyu.mix.treasure.service.impl.TreasureServiceImpl.BuyResultImpl;
import org.openyu.mix.treasure.service.TreasureService.ErrorType;
import org.openyu.mix.treasure.service.TreasureService.RefreshResult;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class TreasureServiceImplTest extends TreasureTestSupporter {

	@Test
	/**
	 * 連線
	 */
	public void connect() {
		Role role = mockRole();
		//
		treasureService.roleConnect(role.getId(), null);
	}

	@Test
	/**
	 * 監聽
	 */
	public void listen() {
		Role role = mockRole();
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);

		// 剩2秒刷新
		treasurePen.setRefreshTime(treasurePen.getRefreshTime() - treasureCollector.getRefreshMills() + 2 * 1000L);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	// @Test
	// /**
	// * 監聽
	// */
	// public void listen2()
	// {
	// Role role = mockRole();
	// TreasurePen treasurePen = mockTreasurePen(role);
	// role.setTreasurePen(treasurePen);
	//
	// //超過經過1個周期,剩5秒刷新
	// treasurePen.setRefreshTime(treasurePen.getRefreshTime()
	// - treasureCollector.getRefreshMills() * 2 + 5 * 1000L);
	// //
	// ThreadHelper.sleep(60 * 1000L);
	// }

	@Test
	public void sendTreasurePen() {
		Role role = mockRole();
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.sendTreasurePen(role, role.getTreasurePen());
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertNotNull(result);
		assertEquals(CoreMessageType.TREASURE_PEN_RESPONSE, result.getMessageType());
	}

	@Test
	public void fillTreasurePen() {
		Role role = mockRole();
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);
		//
		boolean result = false;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT, null,
					role.getId());
			result = treasureService.fillTreasurePen(message, treasurePen);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
	}

	@Test
	public void sendTreasure() {
		Role role = mockRole();
		Treasure treasuer = treasureCollector.randomTreasure("ROLE_EXP_001");
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.sendTreasure(role, treasuer);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertNotNull(result);
		assertEquals(CoreMessageType.TREASURE_TREASURE_RESPONSE, result.getMessageType());
	}

	@Test
	public void fillTreasure() {
		Role role = mockRole();
		Treasure treasuer = treasureCollector.randomTreasure("ROLE_EXP_001");
		//
		boolean result = false;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			Message message = messageService.createMessage(CoreModuleType.TREASURE, CoreModuleType.CLIENT, null,
					role.getId());
			result = treasureService.fillTreasure(message, treasuer);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
	}

	/**
	 * 建構祕寶
	 */
	@Test
	public void createTreasure() {
		Treasure result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.createTreasure("ROLE_EXP_001", "T_ROLE_EXP_G001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 隨機上架祕寶
	 */
	@Test
	public void randomTreasures() {
		Map<Integer, Treasure> result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.randomTreasures();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	/**
	 * 刷新秘寶
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 3.20 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.06, time.total: 3.20, time.warmup: 0.00,
	// time.bench: 3.20
	public void refresh() {
		Role role = mockRole();
		BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// 加道具到包包
		Item item = itemService.createItem(treasureCollector.getRefreshItem());
		bagPen.addItem(0, 0, item);
		// 刷新結果
		RefreshResult result = treasureService.refresh(true, role);
		System.out.println(result);
		assertNotNull(result);

		// 祕寶
		TreasurePen treasurePen = role.getTreasurePen();
		assertTrue(treasurePen.getTreasures().size() > 0);

		// 扣儲值幣
		result = treasureService.refresh(true, role);
		System.out.println(result);
		assertNotNull(result);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查刷新秘寶
	 */
	@Test
	public void checkRefresh() {
		Role role = mockRole();
		//
		ErrorType errorType = treasureService.checkRefresh(role);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);
	}

	/**
	 * 購買秘寶
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 2.71 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.06, time.total: 2.71, time.warmup: 0.00,
	// time.bench: 2.71
	public void buy() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		// 祕寶
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);

		// 金幣購買
		BuyResult result = treasureService.buy(true, role, 1, 0);
		System.out.println(result);
		assertNotNull(result);

		// 儲值幣購買
		role.setVipType(VipType._2);
		result = treasureService.buy(true, role, 2, 2);
		System.out.println(result);
		assertNotNull(result);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 發送刷新祕寶
	 */
	@Test
	public void sendRefresh() {
		Role role = mockRole();
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.sendRefresh(ErrorType.NO_ERROR, role);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertNotNull(result);
		assertEquals(CoreMessageType.TREASURE_REFRESH_RESPONSE, result.getMessageType());
	}

	/**
	 * 金幣購買秘寶
	 */
	@Test
	public void goldBuy() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		// 祕寶
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);

		// 購買結果
		BuyResult result = treasureService.goldBuy(true, role, 2);
		System.out.println(result);
		assertNotNull(result);

		// 再買一次,應該是失敗
		result = treasureService.goldBuy(true, role, 2);
		System.out.println(result);
		assertNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查金幣購買秘寶
	 */
	@Test
	public void checkGoldBuy() {
		Role role = mockRole();
		// 祕寶
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);
		//
		ErrorType errorType = treasureService.checkGoldBuy(role, 0);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);
		//
		role.setLevel(20);// 等級
		errorType = treasureService.checkGoldBuy(role, -1);
		System.out.println(errorType);
		// 祕寶不存在
		assertEquals(ErrorType.TREASURE_NOT_EXIST, errorType);

		// 祕寶
		Treasure treasure = treasurePen.getTreasures().get(0);
		treasure.setBought(true);
		errorType = treasureService.checkGoldBuy(role, 0);
		System.out.println(errorType);
		// 祕寶已購買
		assertEquals(ErrorType.ALREADY_BUY, errorType);

		treasure.setBought(false);
		String origId = treasure.getId();
		treasure.setId("NO ITEM");
		errorType = treasureService.checkGoldBuy(role, 0);
		System.out.println(errorType);
		// 道具不存在
		assertEquals(ErrorType.ITEM_NOT_EXIST, errorType);

		treasure.setId(origId);
		errorType = treasureService.checkGoldBuy(role, 0);
		System.out.println(errorType);
		// 金幣不足
		assertEquals(ErrorType.GOLD_NOT_ENOUGH, errorType);

		role.setGold(10000 * 10000L);// 1e
		errorType = treasureService.checkGoldBuy(role, 0);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 儲值幣購買秘寶
	 */
	@Test
	public void coinBuy() {
		Role role = mockRole();
		role.setLevel(20);// 等級
		// 祕寶
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);

		role.setVipType(VipType._2);
		// 購買
		BuyResult result = treasureService.coinBuy(true, role, 2);
		System.out.println(result);
		assertNotNull(result);

		// 再買一次,應該是失敗
		result = treasureService.coinBuy(true, role, 2);
		System.out.println(result);
		assertNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查儲值幣購買秘寶
	 */
	@Test
	public void checkCoinBuy() {
		Role role = mockRole();
		// 祕寶
		TreasurePen treasurePen = mockTreasurePen(role);
		role.setTreasurePen(treasurePen);
		//
		ErrorType errorType = treasureService.checkCoinBuy(role, 0);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);
		//
		role.setLevel(20);// 等級
		errorType = treasureService.checkCoinBuy(role, -1);
		System.out.println(errorType);
		// 祕寶不存在
		assertEquals(ErrorType.TREASURE_NOT_EXIST, errorType);

		// 祕寶
		Treasure treasure = treasurePen.getTreasures().get(0);
		treasure.setBought(true);
		errorType = treasureService.checkCoinBuy(role, 0);
		System.out.println(errorType);
		// 祕寶已購買
		assertEquals(ErrorType.ALREADY_BUY, errorType);

		treasure.setBought(false);
		String origId = treasure.getId();
		treasure.setId("NO ITEM");
		errorType = treasureService.checkCoinBuy(role, 0);
		System.out.println(errorType);
		// 道具不存在
		assertEquals(ErrorType.ITEM_NOT_EXIST, errorType);
		treasure.setId(origId);

		role.setLevel(20);// 等級
		errorType = treasureService.checkRefresh(role);
		System.out.println(errorType);
		// vip不足
		assertEquals(ErrorType.NO_ERROR, errorType);

		role.setVipType(VipType._2);// vip
		// 儲值幣重置歸0
		accountService.resetCoin(false, role.getAccountId(), role, false, CoinType.DEBUG_RESET);
		errorType = treasureService.checkCoinBuy(role, 0);
		System.out.println(errorType);
		// 儲值幣不足
		assertEquals(ErrorType.COIN_NOT_ENOUGH, errorType);

		// 給儲值幣
		accountService.increaseCoin(false, role.getAccountId(), role, 10000, false, CoinType.DEBUG_INCREASE);
		errorType = treasureService.checkCoinBuy(role, 0);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 發送購買祕寶
	 */
	@Test
	public void sendBuy() {
		Role role = mockRole();
		BuyResult buyResult = new BuyResultImpl();
		//
		Message result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = treasureService.sendBuy(ErrorType.NO_ERROR, role, buyResult);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		assertNotNull(result);
		assertEquals(CoreMessageType.TREASURE_BUY_RESPONSE, result.getMessageType());
	}

}
