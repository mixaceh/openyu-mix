package org.openyu.mix.activity.vo;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
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
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class ActivityCollectorTest extends BeanTestSupporter {

	/**
	 * 模擬每日消費儲值幣活動
	 * 
	 * @return
	 */
	public static List<DailyCoinTargetActivity> mockDailyCoinTargetActivity() {
		List<DailyCoinTargetActivity> result = new LinkedList<DailyCoinTargetActivity>();
		//
		DailyCoinTargetActivity activity = new DailyCoinTargetActivityImpl(
				ActivityType.DAILY_COIN_TARGET + "_001");
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
		AccuCoinTargetActivity activity = new AccuCoinTargetActivityImpl(
				ActivityType.ACCU_COIN_TARGET + "_001");
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
		FameTargetActivity activity = new FameTargetActivityImpl(
				ActivityType.FAME_TARGET + "_001");
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
		VipTargetActivity activity = new VipTargetActivityImpl(
				ActivityType.VIP_TARGET + "_001");
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
	public void writeToXml() {
		String result = null;
		ActivityCollector collector = ActivityCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
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
			result = collector.writeToXml(ActivityCollector.class, collector);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		ActivityCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(ActivityCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = beanCollector.writeToSerFromXml(ActivityCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		ActivityCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(ActivityCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 399 mills.
	// 1000000 times: 398 mills.
	// 1000000 times: 401 mills.
	public void initialize() {
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			ActivityCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = ActivityCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(ActivityCollector.getInstance().getActivityIds());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		ActivityCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 10000 times: 1587 mills.
	// 10000 times: 1583 mills.
	// 10000 times: 1683 mills.
	public void reset() {
		ActivityCollector collector = ActivityCollector.getInstance();
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
		//
		assertEquals(0, collector.getActivitys().size());
	}

	@Test
	// 1000000 times: 396 mills.
	// 1000000 times: 393 mills.
	// 1000000 times: 434 mills.
	// verified
	public void getActivityTypes() {
		Set<ActivityType> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().getActivityTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getActivitys() {
		Map<String, Activity> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().getActivitys();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 2589 mills.
	// 1000000 times: 2585 mills.
	// 1000000 times: 2533 mills.
	// verified
	public void getActivity() {
		Activity result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().getActivity(
					"DailyCoinActivity_001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ActivityType.DAILY_COIN_TARGET, result.getActivityType());
		//
		DailyCoinTargetActivity dailyCoinActivity = (DailyCoinTargetActivity) result;
		System.out.println(dailyCoinActivity.getTargets());
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getActivitysByActivityType() {
		List<Activity> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().getActivitys(
					ActivityType.DAILY_COIN_TARGET);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void containActivity() {
		boolean result = false;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().containActivity(
					"DailyCoinActivity_001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result);
		//
		result = ActivityCollector.getInstance().containActivity("NOT_EXIST");
		assertFalse(result);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getActivityIds() {
		List<String> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ActivityCollector.getInstance().getActivityIds();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
