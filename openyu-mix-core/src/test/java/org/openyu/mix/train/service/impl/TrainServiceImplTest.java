package org.openyu.mix.train.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.train.TrainTestSupporter;
import org.openyu.mix.train.service.TrainService.ErrorType;
import org.openyu.mix.train.service.TrainService.InspireResult;
import org.openyu.mix.train.service.TrainService.JoinResult;
import org.openyu.mix.train.service.TrainService.QuitResult;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.util.CalendarHelper;
import org.openyu.socklet.message.vo.Message;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class TrainServiceImplTest extends TrainTestSupporter {

	@Test
	/**
	 * 連線
	 */
	public void connect() {
		Role role = mockRole();
		//
		trainService.roleConnect(role.getId(), null);
	}

	@Test
	/**
	 * 監聽
	 */
	public void listen() {
		Role role = mockRole();
		TrainInfo trainInfo = role.getTrainInfo();

		// 加入
		trainSetService.addRole(role);
		trainInfo.setJoinTime(System.currentTimeMillis());
		// 剩10秒結束
		trainInfo.addDailyMills(trainCollector.getDailyMills() - 10 * 1000L);
		//
		// ThreadHelper.sleep(10 * 1000L);
	}

	@Test
	public void sendTrainInfo() {
		Role role = mockRole();
		//
		trainService.sendTrainInfo(role, role.getTrainInfo());
	}

	@Test
	public void fillTrainInfo() {
		Message result = null;
		//
		Role role = mockRole();
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.TRAIN, CoreModuleType.CLIENT, null, role.getId());
			trainService.fillTrainInfo(result, role.getTrainInfo());
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

	/**
	 * 加入訓練
	 */
	@Test
	public void join() {
		Role role = mockRole();
		//
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		// 加入結果
		JoinResult result = trainService.join(true, role);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查加入訓練
	 */
	@Test
	public void checkJoin() {
		Role role = mockRole();
		TrainInfo trainInfo = role.getTrainInfo();
		//
		ErrorType errorType = trainService.checkJoin(role);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(20);// 等級
		// 加入訓練
		trainSetService.addRole(role);
		errorType = trainService.checkJoin(role);
		System.out.println(errorType);
		// 已經加入訓練了
		assertEquals(ErrorType.ALREADY_JOIN, errorType);

		// 移除訓練
		trainSetService.removeRole(role);
		trainInfo.setDailyMills(trainCollector.getDailyMills());// 每天已訓練毫秒
		errorType = trainService.checkJoin(role);
		System.out.println(errorType);
		// 超過每天可訓練毫秒
		assertEquals(ErrorType.OVER_DAILY_MILLS, errorType);

		trainInfo.setDailyMills(0);// 每天已訓練毫秒
		errorType = trainService.checkJoin(role);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 離開訓練
	 */
	@Test
	public void quit() {
		Role role = mockRole();
		//
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e

		// 加入訓練
		trainSetService.addRole(role);
		// 離開結果
		QuitResult result = trainService.quit(true, role);
		System.out.println(result);
		assertNotNull(result);
		//
		// ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查離開訓練
	 */
	@Test
	public void checkQuit() {
		Role role = mockRole();
		//
		ErrorType errorType = trainService.checkQuit(role);
		System.out.println(errorType);
		// 還沒加入訓練
		assertEquals(ErrorType.NOT_JOIN, errorType);

		// 加入訓練
		trainSetService.addRole(role);
		errorType = trainService.checkQuit(role);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 鼓舞訓練
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 2.65 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 1, GC.time: 0.05, time.total: 2.65, time.warmup: 0.02,
	// time.bench: 2.65
	public void inspire() {
		Role role = mockRole();
		role.setVipType(VipType._2);
		role.setLevel(20);// 等級
		role.setGold(10000 * 10000L);// 1e
		//
		BagInfo bagInfo = role.getBagInfo();
		TrainInfo trainInfo = role.getTrainInfo();

		// 加道具到包包
		Item item = itemService.createItem(trainCollector.getInspireItem());
		bagInfo.addItem(0, 0, item);
		// 加入訓練
		trainSetService.addRole(role);
		// 鼓舞結果
		InspireResult result = trainService.inspire(true, role);
		System.out.println(result);
		assertNotNull(result);
		//
		trainInfo.setInspireTime(0);
		result = trainService.inspire(true, role);
		System.out.println(result);
		assertNotNull(result);
		//
		ThreadHelper.sleep(3 * 1000L);
	}

	/**
	 * 檢查鼓舞訓練
	 */
	@Test
	public void checkInspire() {
		Role role = mockRole();
		// 移除訓練
		trainSetService.removeRole(role);
		//
		ErrorType errorType = trainService.checkInspire(role);
		System.out.println(errorType);
		// 等級不足
		assertEquals(ErrorType.LEVLE_NOT_ENOUGH, errorType);

		role.setLevel(20);// 等級
		errorType = trainService.checkInspire(role);
		System.out.println(errorType);
		// 還沒加入訓練
		assertEquals(ErrorType.NOT_JOIN, errorType);

		// 加入訓練
		trainSetService.addRole(role);
		errorType = trainService.checkInspire(role);
		System.out.println(errorType);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, errorType);
	}

	/**
	 * 重置訓練
	 */
	@Test
	public void reset() {
		Role role = mockRole();
		TrainInfo trainInfo = role.getTrainInfo();
		trainInfo.setDailyMills(5 * 60 * 1000);
		trainInfo.setInspireTime(System.currentTimeMillis());

		// 今日23點,不用重置
		Calendar joinTime = CalendarHelper.today(23, 0, 0);
		trainInfo.setJoinTime(joinTime.getTimeInMillis());
		boolean result = trainService.reset(true, role);
		//
		System.out.println(result);
		assertFalse(result);
		assertTrue(trainInfo.getJoinTime() != 0);
		assertTrue(trainInfo.getDailyMills() != 0);
		assertTrue(trainInfo.getInspireTime() != 0);

		// 昨日23點,可以重置
		joinTime = CalendarHelper.yesterday(23, 0, 0);
		trainInfo.setJoinTime(joinTime.getTimeInMillis());
		// 若在訓練中,則離開訓練
		// 加入訓練
		trainSetService.addRole(role);
		result = trainService.reset(true, role);
		//
		System.out.println(result);
		assertTrue(trainInfo.getJoinTime() == 0);
		assertTrue(trainInfo.getQuitTime() == 0);
		assertTrue(trainInfo.getDailyMills() == 0);
		assertTrue(trainInfo.getInspireTime() == 0);
	}

	/**
	 * 檢查重置訓練
	 */
	@Test
	public void checkReset() {
		Role role = mockRole();
		TrainInfo trainInfo = role.getTrainInfo();
		trainInfo.setDailyMills(5 * 60 * 1000);
		trainInfo.setInspireTime(System.currentTimeMillis());

		// 今日23點,不用重置
		Calendar joinTime = CalendarHelper.today(23, 0, 0);
		trainInfo.setJoinTime(joinTime.getTimeInMillis());
		ErrorType errorType = trainService.checkReset(role);
		System.out.println(errorType);
		assertEquals(ErrorType.NO_ERROR, errorType);

		// 昨日23點,可以重置
		joinTime = CalendarHelper.yesterday(23, 0, 0);
		trainInfo.setJoinTime(joinTime.getTimeInMillis());
		errorType = trainService.checkReset(role);
		System.out.println(errorType);
		assertEquals(ErrorType.OVER_RESET_TIME, errorType);
	}
}
