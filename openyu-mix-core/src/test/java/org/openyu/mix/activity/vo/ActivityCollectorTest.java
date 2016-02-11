package org.openyu.mix.activity.vo;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.activity.vo.ActivityType;
import org.openyu.mix.activity.vo.target.AccuCoinTargetActivity;
import org.openyu.mix.activity.vo.target.CoinTarget;
import org.openyu.mix.activity.vo.target.DailyCoinTargetActivity;
import org.openyu.mix.activity.vo.target.FameTargetActivity;
import org.openyu.mix.activity.vo.target.FameTarget;
import org.openyu.mix.activity.vo.target.VipTargetActivity;
import org.openyu.mix.activity.vo.target.VipTarget;
import org.openyu.mix.activity.vo.target.impl.AccuCoinTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.CoinTargetImpl;
import org.openyu.mix.activity.vo.target.impl.DailyCoinTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.FameTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.FameTargetImpl;
import org.openyu.mix.activity.vo.target.impl.VipTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.VipTargetImpl;
import org.openyu.mix.vip.vo.VipType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class ActivityCollectorTest extends BaseTestSupporter {

	/**
	 * 模擬每日消費儲值幣活動
	 * 
	 * @return
	 */
	public static List<DailyCoinTargetActivity> mockDailyCoinTargetActivity() {
		List<DailyCoinTargetActivity> result = new LinkedList<DailyCoinTargetActivity>();
		//
		DailyCoinTargetActivity activity = new DailyCoinTargetActivityImpl(ActivityType.DAILY_COIN_TARGET + "_001");
		activity.setName("每日消費儲值幣目標活動");
		activity.setDescription("每日消費儲值幣可達成不同階段目標,每日00:00將會重置,可獲得豐碩的獎勵");
		activity.setBegDate("2013/01/01");
		activity.setEndDate("2013/06/30");
		activity.setDailyReset(true);
		activity.setInOrder(true);
		result.add(activity);
		//
		CoinTarget target = new CoinTargetImpl(0);
		target.setLevelLimit(10);
		// target.setDay(1);
		target.setTip("消費1000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_G001", 1);// E 級強化武器捲
		target.setCoin(1000);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(1);
		target.setLevelLimit(15);
		// target.setDay(1);
		target.setTip("消費1200儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_B001", 1);// 祝福 E 級強化武器捲
		target.setCoin(1200);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(2);
		target.setLevelLimit(20);
		// target.setDay(1);
		target.setTip("消費1500儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_D_G001", 1);// D 級強化武器捲
		target.setCoin(1500);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(3);
		target.setLevelLimit(25);
		// target.setDay(1);
		target.setTip("消費1800儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_D_B001", 1);// 祝福 D 級強化武器捲
		target.setCoin(1800);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(4);
		target.setLevelLimit(30);
		// target.setDay(1);
		target.setTip("消費3000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_F001", 1);// 幻想 E 級強化武器捲
		target.setCoin(3000);
		activity.getTargets().put(target.getId(), target);
		return result;
	}

	/**
	 * 模擬累計消費儲值幣活動
	 * 
	 * @return
	 */
	public static List<AccuCoinTargetActivity> mockAccuCoinTargetActivity() {
		List<AccuCoinTargetActivity> result = new LinkedList<AccuCoinTargetActivity>();
		//
		AccuCoinTargetActivity activity = new AccuCoinTargetActivityImpl(ActivityType.ACCU_COIN_TARGET + "_001");
		activity.setName("累計消費儲值幣目標活動");
		activity.setDescription("累計消費儲值幣可達成不同階段目標,可獲得豐碩的獎勵");
		activity.setBegDate("2013/01/01");
		activity.setEndDate("2013/06/30");
		activity.setDailyReset(false);
		activity.setInOrder(true);
		result.add(activity);
		//
		CoinTarget target = new CoinTargetImpl(0);
		target.setLevelLimit(10);
		// target.setDay(3);
		target.setTip("累計消費12000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_B001", 1);// 祝福 E 級強化武器捲
		target.getAwards().put("T_ROLE_EXP_G001", 5);// 經驗之力
		target.setCoin(12000);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(1);
		target.setLevelLimit(15);
		// target.setDay(3);
		target.setTip("累計消費32000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_B001", 1);// 祝福 E 級強化武器捲
		target.getAwards().put("T_ROLE_EXP_G002", 5);// 想願經驗之力
		target.setCoin(32000);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(2);
		target.setLevelLimit(20);
		// target.setDay(3);
		target.setTip("累計消費52000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_D_B001", 1);// 祝福 D 級強化武器捲
		target.getAwards().put("T_ROLE_GOLD_G001", 5);// 金幣之力
		target.setCoin(52000);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(3);
		target.setLevelLimit(25);
		// target.setDay(3);
		target.setTip("累計消費120000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_D_B001", 1);// 祝福 D 級強化武器捲
		target.getAwards().put("T_ROLE_GOLD_G002", 5);// 想願金幣之力
		target.setCoin(120000);
		activity.getTargets().put(target.getId(), target);
		//
		target = new CoinTargetImpl(4);
		target.setLevelLimit(30);
		// target.setDay(3);
		target.setTip("累計消費270000儲值幣,玩四象轉生是不錯的選擇");
		target.getAwards().put("T_ENHANCE_WEAPON_E_F001", 1);// 幻想 E 級強化武器捲
		target.getAwards().put("T_ENHANCE_WEAPON_D_F001", 1);// 幻想 D 級強化武器捲
		target.getAwards().put("T_ROLE_EXP_G003", 5);// 憧憬經驗之力
		target.getAwards().put("T_ROLE_GOLD_G003", 5);// 憧憬金幣之力
		target.setCoin(270000);
		activity.getTargets().put(target.getId(), target);
		return result;
	}

	/**
	 * 模擬聲望活動
	 * 
	 * @return
	 */
	public static List<FameTargetActivity> mockFameTargetActivity() {
		List<FameTargetActivity> result = new LinkedList<FameTargetActivity>();
		//
		FameTargetActivity activity = new FameTargetActivityImpl(ActivityType.FAME_TARGET + "_001");
		activity.setName("聲望目標活動");
		activity.setDescription("提升聲望達成不同階段目標,可獲得豐碩的獎勵");
		// activity.setBegDate("2013/01/01");
		// activity.setEndDate("2013/06/30");
		activity.setDailyReset(false);
		activity.setInOrder(true);
		result.add(activity);
		//
		FameTarget target = new FameTargetImpl(0);
		target.setLevelLimit(30);
		target.setExpiredDay(5);
		target.setTip("提升聲望到100");
		target.getAwards().put("T_ROLE_EXP_G001", 1);// 經驗之力
		target.setFame(100);
		activity.getTargets().put(target.getId(), target);
		//
		target = new FameTargetImpl(1);
		target.setLevelLimit(35);
		target.setExpiredDay(5);
		target.setTip("提升聲望到500");
		target.getAwards().put("T_ROLE_EXP_G001", 5);// 經驗之力
		target.setFame(500);
		activity.getTargets().put(target.getId(), target);
		//
		target = new FameTargetImpl(2);
		target.setLevelLimit(40);
		target.setExpiredDay(5);
		target.setTip("提升聲望到700");
		target.getAwards().put("T_ROLE_EXP_G002", 1);// 想願經驗之力
		target.setFame(700);
		activity.getTargets().put(target.getId(), target);
		//
		target = new FameTargetImpl(3);
		target.setLevelLimit(45);
		target.setExpiredDay(5);
		target.setTip("提升聲望到1500");
		target.getAwards().put("T_ROLE_EXP_G002", 5);// 想願經驗之力
		target.setFame(1500);
		activity.getTargets().put(target.getId(), target);
		//
		target = new FameTargetImpl(4);
		target.setLevelLimit(50);
		target.setExpiredDay(5);
		target.setTip("提升聲望到3000");
		target.getAwards().put("T_ROLE_EXP_G003", 5);// 憧憬經驗之力
		target.setFame(3000);
		activity.getTargets().put(target.getId(), target);
		return result;
	}

	/**
	 * 模擬vip活動
	 * 
	 * @return
	 */
	public static List<VipTargetActivity> mockVipTargetActivity() {
		List<VipTargetActivity> result = new LinkedList<VipTargetActivity>();
		//
		VipTargetActivity activity = new VipTargetActivityImpl(ActivityType.VIP_TARGET + "_001");
		activity.setName("vip目標活動");
		activity.setDescription("提升vip達成不同階段目標,可獲得豐碩的獎勵");
		// activity.setBegDate("2013/01/01");
		// activity.setEndDate("2013/06/30");
		activity.setDailyReset(false);
		activity.setInOrder(true);
		result.add(activity);
		//
		VipTarget target = new VipTargetImpl(0);
		target.setLevelLimit(30);
		target.setExpiredDay(5);
		target.setTip("提升vip到1級");
		target.getAwards().put("T_ROLE_GOLD_G001", 1);// 金幣之力
		target.setVipType(VipType._1);
		activity.getTargets().put(target.getId(), target);
		//
		target = new VipTargetImpl(1);
		target.setLevelLimit(35);
		target.setExpiredDay(5);
		target.setTip("提升vip到2級");
		target.getAwards().put("T_ROLE_GOLD_G001", 5);// 金幣之力
		target.setVipType(VipType._2);
		activity.getTargets().put(target.getId(), target);
		//
		target = new VipTargetImpl(2);
		target.setLevelLimit(40);
		target.setExpiredDay(5);
		target.setTip("提升vip到3級");
		target.getAwards().put("T_ROLE_GOLD_G002", 1);// 想願金幣之力
		target.setVipType(VipType._3);
		activity.getTargets().put(target.getId(), target);
		//
		target = new VipTargetImpl(3);
		target.setLevelLimit(45);
		target.setExpiredDay(5);
		target.setTip("提升vip到4級");
		target.getAwards().put("T_ROLE_GOLD_G002", 5);// 想願金幣之力
		target.setVipType(VipType._4);
		activity.getTargets().put(target.getId(), target);
		//
		target = new VipTargetImpl(4);
		target.setLevelLimit(50);
		target.setExpiredDay(5);
		target.setTip("提升vip到5級");
		target.getAwards().put("T_ROLE_GOLD_G003", 5);// 憧憬金幣之力
		target.setVipType(VipType._5);
		activity.getTargets().put(target.getId(), target);
		return result;
	}

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToXml() {
		String result = null;
		ActivityCollector collector = ActivityCollector.getInstance(false);
		//
		// 模擬每日消費儲值幣目標活動
		List<DailyCoinTargetActivity> dailyCoinActivity = mockDailyCoinTargetActivity();
		collector.addActivitys(dailyCoinActivity);

		// 模擬累計消費儲值幣目標活動
		List<AccuCoinTargetActivity> accuCoinActivity = mockAccuCoinTargetActivity();
		collector.addActivitys(accuCoinActivity);

		// 模擬聲望目標活動
		List<FameTargetActivity> fameCoinActivity = mockFameTargetActivity();
		collector.addActivitys(fameCoinActivity);

		// 模擬vip目標活動
		List<VipTargetActivity> vipCoinActivity = mockVipTargetActivity();
		collector.addActivitys(vipCoinActivity);
		//
		result = CollectorHelper.writeToXml(ActivityCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		ActivityCollector result = null;
		//
		result = CollectorHelper.readFromXml(ActivityCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(ActivityCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		ActivityCollector result = null;
		//
		result = CollectorHelper.readFromSer(ActivityCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		ActivityCollector result = null;
		//
		result = ActivityCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		ActivityCollector instance = ActivityCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ActivityCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = ActivityCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		ActivityCollector instance = ActivityCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = ActivityCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = ActivityCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getActivityTypes() {
		Set<ActivityType> result = null;
		//
		result = ActivityCollector.getInstance().getActivityTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getActivitys() {
		Map<String, Activity> result = null;
		//
		result = ActivityCollector.getInstance().getActivitys();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getActivity() {
		Activity result = null;
		//
		result = ActivityCollector.getInstance().getActivity("DailyCoinActivity_001");
		//
		System.out.println(result);
		assertEquals(ActivityType.DAILY_COIN_TARGET, result.getActivityType());
		//
		DailyCoinTargetActivity dailyCoinActivity = (DailyCoinTargetActivity) result;
		System.out.println(dailyCoinActivity.getTargets());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getActivitysByActivityType() {
		List<Activity> result = null;
		//
		result = ActivityCollector.getInstance().getActivitys(ActivityType.DAILY_COIN_TARGET);
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void containActivity() {
		boolean result = false;
		//
		result = ActivityCollector.getInstance().containActivity("DailyCoinActivity_001");
		//
		System.out.println(result);
		assertTrue(result);
		//
		result = ActivityCollector.getInstance().containActivity("NOT_EXIST");
		assertFalse(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getActivityIds() {
		List<String> result = null;
		//
		result = ActivityCollector.getInstance().getActivityIds();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
