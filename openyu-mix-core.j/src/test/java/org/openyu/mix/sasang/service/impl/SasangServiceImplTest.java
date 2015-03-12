package org.openyu.mix.sasang.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openyu.mix.sasang.SasangTestSupporter;
import org.openyu.mix.sasang.service.SasangService.ErrorType;
import org.openyu.mix.sasang.service.SasangService.PlayResult;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.service.SasangService.PutResult;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class SasangServiceImplTest extends SasangTestSupporter {

	@Test
	/**
	 * 連線
	 */
	public void connect() {
		Role role = mockRole();
		SasangPen sasangPen = mockSasangPen(role);
		role.setSasangPen(sasangPen);
		//
		sasangService.roleConnect(role.getId(), null);
	}

	@Test
	public void sendSasangPen() {
		Role role = mockRole();
		SasangPen sasangPen = mockSasangPen(role);
		role.setSasangPen(sasangPen);
		//
		sasangService.sendSasangPen(role, role.getSasangPen());
	}

	@Test
	public void fillSasangPen() {
		Message result = null;
		//
		Role role = mockRole();
		SasangPen sasangPen = mockSasangPen(role);
		role.setSasangPen(sasangPen);
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.SASANG,
					CoreModuleType.CLIENT, null, role.getId());
			sasangService.fillSasangPen(result, sasangPen);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

	@Test
	/**
	 * 玩四象
	 */
	public void play() {
		Role role = mockRole();
		BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// SasangPen sasangPen = role.getSasangPen();

		// 青銅按鈕,可玩1次,消耗金幣,有每日次數限制
		PlayResult result = sasangService.play(true, role, 1);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getDailyTimes());

		// 加道具到包包
		Item item = itemService.createItem(sasangCollector.getPlayItem(), 5);
		bagPen.addItem(0, 0, item);
		// 銀按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		result = sasangService.play(true, role, 2);// 玩1次
		System.out.println(result);

		// 金按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		result = sasangService.play(true, role, 3);// 玩10次
		System.out.println(result);
		//
		//ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 用道具或儲值幣玩
	 */
	public void itemCoinPlay() {
		Role role = mockRole();
		BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// 加道具到包包
		Item item = itemService.createItem(sasangCollector.getPlayItem(), 1);
		bagPen.addItem(0, 0, item);
		// 玩的結果
		PlayResult result = sasangService.itemCoinPlay(true, role,
				PlayType.GALACTIC);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getSpendItems().size());

		// 扣儲值幣
		result = sasangService.itemCoinPlay(true, role, PlayType.GALACTIC);
		System.out.println(result);
		assertTrue(result.getSpendCoin() > 0);
		//
		//ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 用道具或儲值幣玩
	 */
	public void itemCoinPlayByGold() {
		Role role = mockRole();
		BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip

		// 加5個道具到包包
		Item item = itemService.createItem(sasangCollector.getPlayItem(), 5);
		bagPen.addItem(0, 0, item);
		// 玩的結果
		PlayResult result = sasangService.itemCoinPlay(true, role,
				PlayType.GOLDEN);// 玩20次
		System.out.println(result);
		assertEquals(1, result.getSpendItems().size());// 但amount=5

		// 扣儲值幣
		result = sasangService.itemCoinPlay(true, role, PlayType.GOLDEN);
		System.out.println(result);
		assertTrue(result.getSpendCoin() > 0);
		//
		//ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查用道具或儲值幣玩
	 */
	public void checkItemCoinPlay() {
		Role role = mockRole();
		//
		ErrorType errorType = sasangService.checkItemCoinPlay(role, 10);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(20);
		errorType = sasangService.checkItemCoinPlay(role, 0);
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
		// BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		SasangPen sasangPen = role.getSasangPen();

		// 玩的結果
		PlayResult result = sasangService.goldPlay(true, role);// 玩1次
		System.out.println(result);
		assertEquals(1, result.getDailyTimes());

		// 已玩10次
		sasangPen.setDailyTimes(10);
		result = sasangService.goldPlay(true, role);// 玩1次
		System.out.println(result);
		//
		//ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查用金幣玩
	 */
	public void checkGoldPlay() {
		Role role = mockRole();
		SasangPen sasangPen = role.getSasangPen();
		//
		ErrorType errorType = sasangService.checkGoldPlay(role);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(20);
		sasangPen.setDailyTimes(10);
		errorType = sasangService.checkGoldPlay(role);
		System.out.println(errorType);
		// 超過每日次數
		assertEquals(ErrorType.OVER_PLAY_DAILY_TIMES, errorType);

		sasangPen.setDailyTimes(0);
		errorType = sasangService.checkGoldPlay(role);
		System.out.println(errorType);
		// 金幣不足
		assertEquals(ErrorType.GOLD_NOT_ENOUGH, errorType);

		role.setGold(10000 * 10000L);// 1e
		errorType = sasangService.checkGoldPlay(role);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	@Test
	/**
	 * 單擊獎勵放入包包
	 */
	public void putOne() {
		Role role = mockRole();
		// BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		//
		SasangPen sasangPen = role.getSasangPen();
		sasangPen.getAwards().put("T_POTION_HP_G001", 1);
		//
		PutResult result = sasangService.putOne(true, role, "T_POTION_HP_G001",
				1);
		System.out.println(result);
		assertNotNull(result);
		assertEquals(1, result.getAwards().size());
		//
		//ThreadHelper.sleep(3 * 1000L);
	}

	@Test
	/**
	 * 檢查單擊獎勵放入包包
	 */
	public void checkPutOne() {
		Role role = mockRole();
		SasangPen sasangPen = role.getSasangPen();
		//
		ErrorType errorType = sasangService.checkPutOne(role,
				"T_POTION_HP_G001", 1);
		System.out.println(errorType);
		// 獎勵不存在
		assertEquals(ErrorType.AWARD_NOT_EXIST, errorType);

		sasangPen.getAwards().put("T_POTION_HP_G001", 1);// 中獎區只有1個
		errorType = sasangService.checkPutOne(role, "T_POTION_HP_G001", 5);// 偷塞5個
		System.out.println(errorType);
		// 獎勵不存在
		assertEquals(ErrorType.AWARD_NOT_EXIST, errorType);

		errorType = sasangService.checkPutOne(role, "T_POTION_HP_G001", 1);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);

		errorType = sasangService.checkPutOne(role, "T_NOT_EXIST", 1);
		System.out.println(errorType);
		// 道具不存在
		assertEquals(ErrorType.ITEM_NOT_EXIST, errorType);

	}

	@Test
	/**
	 * 所有中獎區獎勵放入包包
	 */
	public void putAll() {
		Role role = mockRole();
		BagPen bagPen = role.getBagPen();
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		role.setVipType(VipType._2);// vip
		//
		SasangPen sasangPen = role.getSasangPen();
		sasangPen.getAwards().put("T_POTION_HP_G001", 1);
		// 弄個不存在的
		sasangPen.getAwards().put("T_NOT_EXIST", 10);
		
		//有1個放不進去就不放了,以下的不會放入包包
		sasangPen.getAwards().put("T_POTION_HP_G002", 5);
		sasangPen.getAwards().put("T_POTION_HP_G003", 10);
		//
		PutResult result = sasangService.putAll(true, role);
		System.out.println(result);
		System.out.println(bagPen);
		assertNotNull(result);
		
		//有1個放不進去就不放了
		assertEquals(1, result.getAwards().size());
		//
		//ThreadHelper.sleep(3 * 1000L);
	}
}
