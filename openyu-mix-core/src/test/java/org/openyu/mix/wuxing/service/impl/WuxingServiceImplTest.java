package org.openyu.mix.wuxing.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.mix.wuxing.WuxingTestSupporter;
import org.openyu.mix.wuxing.service.WuxingService.ErrorType;
import org.openyu.mix.wuxing.service.WuxingService.PlayResult;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.service.WuxingService.PutResult;
import org.openyu.mix.wuxing.vo.WuxingInfo;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class WuxingServiceImplTest extends WuxingTestSupporter {

	/**
	 * 連線
	 */
	@Test
	public void connect() {
		Role role = mockRole();
		WuxingInfo wuxingInfo = mockWuxingInfo(role);
		role.setWuxingInfo(wuxingInfo);
		//
		wuxingService.roleConnect(role.getId(), null);
	}

	@Test
	public void sendWuxingInfo() {
		Role role = mockRole();
		WuxingInfo wuxingInfo = mockWuxingInfo(role);
		role.setWuxingInfo(wuxingInfo);
		//
		wuxingService.sendWuxingInfo(role, role.getWuxingInfo());
	}

	@Test
	public void fillWuxingInfo() {
		Message result = null;
		//
		Role role = mockRole();
		WuxingInfo wuxingInfo = mockWuxingInfo(role);
		role.setWuxingInfo(wuxingInfo);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.SASANG, CoreModuleType.CLIENT, null, role.getId());
			wuxingService.fillWuxingInfo(result, wuxingInfo);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

	/**
	 * 玩五行
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void play() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// WuxingInfo wuxingInfo = role.getWuxingInfo();

		// 青銅按鈕,可玩1次,消耗金幣,有每日次數限制
		PlayResult result = wuxingService.play(true, role, 1);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getDailyTimes());

		// 加道具到包包
		Item item = itemService.createItem(wuxingCollector.getPlayItem(), 5);
		bagInfo.addItem(0, 0, item);
		// 銀按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		result = wuxingService.play(true, role, 2);// 玩1次
		System.out.println(result);

		// 金按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		result = wuxingService.play(true, role, 3);// 玩10次
		System.out.println(result);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 用道具或儲值幣玩
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void itemCoinPlay() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// 加道具到包包
		Item item = itemService.createItem(wuxingCollector.getPlayItem(), 1);
		bagInfo.addItem(0, 0, item);
		// 玩的結果
		PlayResult result = wuxingService.itemCoinPlay(true, role, PlayType.GALACTIC);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getSpendItems().size());

		// 扣儲值幣
		result = wuxingService.itemCoinPlay(true, role, PlayType.GALACTIC);
		System.out.println(result);
		assertTrue(result.getSpendCoin() > 0);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 用道具或儲值幣玩
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void itemCoinPlayWithGold() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// 加5個道具到包包
		Item item = itemService.createItem(wuxingCollector.getPlayItem(), 5);
		bagInfo.addItem(0, 0, item);
		// 玩的結果
		PlayResult result = wuxingService.itemCoinPlay(true, role, PlayType.GOLDEN);// 玩20次
		System.out.println(result);
		assertEquals(1, result.getSpendItems().size());// 但amount=5

		// 扣儲值幣
		result = wuxingService.itemCoinPlay(true, role, PlayType.GOLDEN);
		System.out.println(result);
		assertTrue(result.getSpendCoin() > 0);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查用道具或儲值幣玩
	 */
	public void checkItemCoinPlay() {
		Role role = mockRole();
		//
		ErrorType errorType = wuxingService.checkItemCoinPlay(role, 10);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(25);
		errorType = wuxingService.checkItemCoinPlay(role, 0);
		System.out.println(errorType);
		// /玩的次數為0
		assertEquals(ErrorType.PLAY_TIMES_IS_ZERO, errorType);
	}

	@Test
	/**
	 * 用金幣玩
	 */
	public void goldPlay() {
		Role role = mockRole();
		// BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		WuxingInfo wuxingInfo = role.getWuxingInfo();

		// 玩的結果
		PlayResult result = wuxingService.goldPlay(true, role);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getDailyTimes());

		// 已玩10次
		wuxingInfo.setDailyTimes(10);
		result = wuxingService.goldPlay(true, role);// 玩1次
		System.out.println(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查用金幣玩
	 */
	public void checkGoldPlay() {
		Role role = mockRole();
		WuxingInfo wuxingInfo = role.getWuxingInfo();
		//
		ErrorType errorType = wuxingService.checkGoldPlay(role);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(25);
		wuxingInfo.setDailyTimes(10);
		errorType = wuxingService.checkGoldPlay(role);
		System.out.println(errorType);
		// 超過每日次數
		assertEquals(ErrorType.OVER_PLAY_DAILY_TIMES, errorType);

		wuxingInfo.setDailyTimes(0);
		errorType = wuxingService.checkGoldPlay(role);
		System.out.println(errorType);
		// 金幣不足
		assertEquals(ErrorType.GOLD_NOT_ENOUGH, errorType);

		role.setGold(10000 * 10000L);// 1e
		errorType = wuxingService.checkGoldPlay(role);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 單擊獎勵放入包包
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void putOne() {
		Role role = mockRole();
		// BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		//
		WuxingInfo wuxingInfo = role.getWuxingInfo();
		wuxingInfo.getAwards().put("T_POTION_HP_G001", 1);
		//
		PutResult result = wuxingService.putOne(true, role, "T_POTION_HP_G001", 1);
		System.out.println(result);
		assertNotNull(result);
		assertEquals(1, result.getAwards().size());
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查單擊獎勵放入包包
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void checkPutOne() {
		Role role = mockRole();
		WuxingInfo wuxingInfo = role.getWuxingInfo();
		//
		ErrorType errorType = wuxingService.checkPutOne(role, "T_POTION_HP_G001", 1);
		System.out.println(errorType);
		// 獎勵不存在
		assertEquals(ErrorType.AWARD_NOT_EXIST, errorType);

		wuxingInfo.getAwards().put("T_POTION_HP_G001", 1);// 中獎區只有1個
		errorType = wuxingService.checkPutOne(role, "T_POTION_HP_G001", 5);// 偷塞5個
		System.out.println(errorType);
		// 獎勵不存在
		assertEquals(ErrorType.AWARD_NOT_EXIST, errorType);

		errorType = wuxingService.checkPutOne(role, "T_POTION_HP_G001", 1);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);

		errorType = wuxingService.checkPutOne(role, "T_NOT_EXIST", 1);
		System.out.println(errorType);
		// 道具不存在
		assertEquals(ErrorType.ITEM_NOT_EXIST, errorType);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 所有中獎區獎勵放入包包
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void putAll() {
		Role role = mockRole();
		BagInfo bagInfo = role.getBagInfo();
		role.setLevel(25);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		//
		WuxingInfo wuxingInfo = role.getWuxingInfo();
		wuxingInfo.getAwards().put("T_POTION_HP_G001", 1);
		// 弄個不存在的
		wuxingInfo.getAwards().put("T_NOT_EXIST", 10);

		// 有1個放不進去就不放了,以下的不會放入包包
		wuxingInfo.getAwards().put("T_POTION_HP_G002", 5);
		wuxingInfo.getAwards().put("T_POTION_HP_G003", 10);
		//
		PutResult result = wuxingService.putAll(true, role);
		System.out.println(result);
		System.out.println(bagInfo);
		assertNotNull(result);

		// 有1個放不進去就不放了
		assertEquals(1, result.getAwards().size());
		//
		ThreadHelper.sleep(3 * 1000L);
	}
}
