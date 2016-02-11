package org.openyu.mix.role.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.ManorPen.FarmType;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.po.impl.RolePoImpl;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.BagPen.TabType;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.mix.account.service.impl.AccountServiceImplTest;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.vip.vo.VipType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import org.openyu.commons.lang.ClassHelper;
import org.openyu.commons.thread.ThreadHelper;

public class RoleServiceImplTest extends RoleTestSupporter {

	@Test
	public void copy2RolePo() {
		final String ROLE_ID = "TEST_ROLE" + randomUnique();

		Role orig = new RoleImpl();
		orig.setId(ROLE_ID);
		// orig.setCareerType(CareerType.WARRIOR_1);
		orig.getAttributeGroup().setAttributes(randomAttributes());
		//
		RolePo dest = ClassHelper.copyProperties(orig);

		// attrubutes
		System.out.println("dest.getAttributes: " + dest.getAttributeGroup().getAttributes());
		assertAttrubutes(orig.getAttributeGroup().getAttributes(), dest.getAttributeGroup().getAttributes());

	}

	@Test
	public void copy2Role() {
		final String ROLE_ID = "TEST_ROLE" + randomUnique();

		RolePo orig = new RolePoImpl();
		orig.setId(ROLE_ID);
		// orig.setCareerType(CareerType.WARRIOR_1);
		orig.getAttributeGroup().setAttributes(randomAttributes());
		//
		Role dest = ClassHelper.copyProperties(orig);

		// attrubutes
		System.out.println("dest.getAttributes: " + dest.getAttributeGroup().getAttributes());
		assertAttrubutes(orig.getAttributeGroup().getAttributes(), dest.getAttributeGroup().getAttributes());
	}

	@Test
	public void copyAttributeType() {
		AttributeType orig = AttributeType.MAX_HEALTH;
		System.out.println(orig);

		AttributeType dest = ClassHelper.copyProperties(orig);
		System.out.println(dest);
	}

	@Test
	// verified: ok
	public void insert() {
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 隨機
			Role role = randomRole();
			//
			Serializable pk = roleService.insert(role);
			printInsert(i, pk);
			assertNotNull(pk);
			Role findRole = roleService.find(RoleImpl.class, role.getSeq());
			assertRole(role, findRole);

			System.out.println(role);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findRole() {
		final String ROLE_ID = "TEST_ROLE";
		Role result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.findRole(ROLE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		System.out.println(result.getBagPen());
		//
		TrainPen trainPen = result.getTrainPen();
		trainPen.addDailyMills(20 * 10000L);
		int update = roleService.update(result);
		System.out.println(update);
		//
		System.out.println(trainPen);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findRoleByValid() {
		List<Role> result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.findRole(Locale.TRADITIONAL_CHINESE, true);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.size() + ", " + result);
		assertNotNull(result);
		//
		result = roleService.findRole(false);
		System.out.println(result.size() + ", " + result);
	}

	@Test
	public void update() {
		final String ROLE_ID = "TEST_ROLE";

		Role value = roleService.findRole(ROLE_ID);
		boolean beforeValid = false;
		if (value != null) {
			beforeValid = value.getValid();
			System.out.println("beforeValid: " + beforeValid);
			value.setValid(beforeValid);
		}
		//
		int result = 0;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.update(value);
			printUpdate(i, result);
			assertTrue(result > 0);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	public void connect() {
		Account account = AccountServiceImplTest.mockAccount();
		final String ROLE_ID = "TEST_ROLE";
		final String NAME = "測試角色";
		//
		Role role = roleService.createRole(ROLE_ID, NAME, RaceType.RONG, CareerType.WARRIOR_1, GenderType.FEMALE,
				HairType.SHORT, FaceType.CUTE);
		// 加到mem
		roleSetService.addRole(role);
		//
		roleService.roleConnect(ROLE_ID, account);
	}

	@Test
	public void disconnect() {
		final String ROLE_ID = "TEST_ROLE";
		//
		roleService.roleConnect(ROLE_ID, null);
	}

	@Test
	public void createRole() {
		final String ROLE_ID = "TEST_ROLE";
		final String NAME = "測試角色";
		Role result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = roleService.createRole(ROLE_ID, NAME, RaceType.RONG, CareerType.WARRIOR_1, GenderType.FEMALE,
					HairType.SHORT, FaceType.CUTE);
			// 加到mem
			roleSetService.addRole(result);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void createRoleBagPen() {
		final String ROLE_ID = "TEST_ROLE";
		final String NAME = "測試角色";
		Role result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = roleService.createRole(ROLE_ID, NAME, RaceType.RONG, CareerType.WARRIOR_1, GenderType.FEMALE,
					HairType.SHORT, FaceType.CUTE);
			// 加到mem
			roleSetService.addRole(result);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 包包,只開放第1頁,任務頁,其他頁鎖定
		BagPen bagPen = result.getBagPen();
		System.out.println(bagPen);
		// 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
		assertNotNull(bagPen.getTab(TabType._0));
		assertNull(bagPen.getTab(TabType._1));
		assertNull(bagPen.getTab(TabType._2));
		assertNotNull(bagPen.getTab(TabType.QUEST));
	}

	@Test
	public void createRoleManorPen() {
		final String ROLE_ID = "TEST_ROLE";
		final String NAME = "測試角色";
		Role result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = roleService.createRole(ROLE_ID, NAME, RaceType.RONG, CareerType.WARRIOR_1, GenderType.FEMALE,
					HairType.SHORT, FaceType.CUTE);
			// 加到mem
			roleSetService.addRole(result);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 莊園,只開放第1頁,其他頁鎖定
		ManorPen manorPen = result.getManorPen();
		System.out.println(manorPen);
		// 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
		assertNotNull(manorPen.getFarm(FarmType._0));
		assertNull(manorPen.getFarm(FarmType._1));
		assertNull(manorPen.getFarm(FarmType._2));
	}

	@Test
	// 1000000 times: 14214 mills.
	// 1000000 times: 14400 mills.
	public void calcAttrubutes() {
		boolean result = false;
		//
		Role role = mockRole();
		System.out.println(role.getAttributeGroup());
		// lv=10
		role.setLevel(10);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = roleService.calcAttrubutes(role);
		}

		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		System.out.println(role.getAttributeGroup());
		assertEquals(true, result);

		// lv=20
		role.setLevel(20);
		result = roleService.calcAttrubutes(role);
		System.out.println(result);
		System.out.println(role.getAttributeGroup());
		assertEquals(true, result);
	}

	@Test
	public void changeExp() {
		Role role = mockRole();
		long result = 0;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		// 1級滿=10w exp
		// 2級滿=40w exp
		for (int i = 0; i < count; i++) {
			// 增加9w exp
			result = roleService.changeExp(true, role, 90000L);// lv1,9w
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

		// lv1,9w
		// max=60, 增加3w exp -> lv2,2w
		// max=2, 增加3w exp -> lv2,0w
		result = roleService.changeExp(true, role, 30000L);// lv2,2w
		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

		// lv2,2w
		result = roleService.changeExp(true, role, 50000L);// lv2,7w
		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

		// lv2,7w
		result = roleService.changeExp(true, role, 50000L);// lv2,12w
		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

		// lv2,12w
		result = roleService.changeExp(true, role, 1300000L);// lv4,12w
		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

		// lv4,12w,TODO 扣經驗變負數了
		result = roleService.changeExp(true, role, -130000L);// lv4,-1w
		System.out.println(result);
		System.out.println("level: " + role.getLevel() + ", exp: " + role.getExp());

	}

	@Test
	public void sendExp() {
		Role role = mockRole();
		role.setExp(10000);
		roleService.sendExp(role, role.getExp(), 5000);
	}

	@Test
	public void changeSp() {
		Role role = mockRole();
		role.setSp(100);
		long result = 0;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 會發訊息
			result = roleService.changeSp(true, role, 50);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(150, role.getSp());
		//
		result = roleService.changeSp(true, role, (-1) * 10);
		System.out.println(result);
		assertEquals(140, role.getSp());
		//
		result = roleService.changeSp(true, role, 0);
		System.out.println(result);
		assertEquals(140, role.getSp());
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void sendSp() {
		Role role = mockRole();
		role.setSp(100);
		roleService.sendSp(role, role.getSp(), 50);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.20 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.20, time.warmup: 0.00,
	// time.bench: 0.20
	public void changeLevel() {
		Role role = mockRole();
		// 加入監聽
		role.addBeanChangeListener(roleChangeAdapter);
		int result = 0;
		// 增加59個等級
		result = roleService.changeLevel(true, role, 59);
		System.out.println(result);
		printAttribute(role);
		assertEquals(60, role.getLevel());// 60級

		// 減少2個等級
		result = roleService.changeLevel(true, role, (-1) * 2);
		System.out.println(result);
		// printAttribute(role);
		assertEquals(58, role.getLevel());// 58等

		// 減少60個等級,因原本58等,所以實際減少57級
		result = roleService.changeLevel(true, role, (-1) * 60);
		System.out.println(result);
		// printAttribute(role);
		assertEquals(1, role.getLevel());// 1等

		// 增加0個等級
		result = roleService.changeLevel(true, role, 0);
		System.out.println(result);
		// printAttribute(role);
		assertEquals(1, role.getLevel());// 1等
		//
		// 移除監聽
		role.removeBeanChangeListener(roleChangeAdapter);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void sendLevel() {
		Role role = mockRole();
		role.setLevel(10);
		roleService.sendLevel(role, role.getLevel());
	}

	@Test
	public void checkIncreaseGold() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		role.setGold(10L);
		//
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.checkIncreaseGold(role, 50L);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = roleService.checkIncreaseGold(role, -1 * 100L);
		System.out.println(result);
		assertFalse(result);
		//
		role.setGold(1L);// 溢位了, 1+ Long.MAX_VALUE
		result = roleService.checkIncreaseGold(role, Long.MAX_VALUE);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void increaseGold() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		role.setGold(10L);
		//
		long result = 0L;
		result = roleService.increaseGold(true, role, 100L, GoldType.SHOP_SELL);

		System.out.println(result);
		assertEquals(110L, role.getGold());
		//
		result = roleService.increaseGold(true, role, 0L, GoldType.SHOP_SELL);
		System.out.println(result);
		assertEquals(110L, role.getGold());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void checkDecreaseGold() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		role.setGold(100L);
		//
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.checkDecreaseGold(role, 50L);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = roleService.checkDecreaseGold(role, -1 * 100L);
		System.out.println(result);
		assertFalse(result);
		//
		result = roleService.checkDecreaseGold(role, Long.MAX_VALUE);
		assertFalse(result);
		System.out.println(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.78 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.78, time.warmup: 0.00,
	// time.bench: 0.78
	public void decreaseGold() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		role.setGold(100L);
		//
		long result = 0L;
		// 會發訊息,會有log
		result = roleService.decreaseGold(true, role, 50L, GoldType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(50L, role.getGold());
		//
		result = roleService.decreaseGold(true, role, 0L, GoldType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(50L, role.getGold());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 0.46 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 0.46, time.warmup: 0.00,
	// time.bench: 0.46
	public void changeGold() {
		Role role = mockRole();
		role.setGold(500L);
		long result = 0L;
		//
		// 會發訊息
		result = roleService.changeGold(true, role, 500L, ActionType.INCREASE, GoldType.SHOP_SELL);
		System.out.println(result);
		assertEquals(1000L, role.getGold());
		//
		result = roleService.changeGold(true, role, -1 * 100L, ActionType.DECREASE, GoldType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(900L, role.getGold());

		//
		result = roleService.changeGold(true, role, 0, ActionType.DECREASE, GoldType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(900L, role.getGold());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void sendGold() {
		Role role = mockRole();
		role.setGold(1000L);
		roleService.sendGold(role, role.getGold(), 50);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	// round: 1.56 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+-
	// 0.00], GC.calls: 0, GC.time: 0.00, time.total: 1.56, time.warmup: 0.02,
	// time.bench: 1.56
	public void changeFame() {
		Role role = mockRole();
		role.setFame(100);
		int result = 0;
		// 會發訊息
		result = roleService.changeFame(true, role, 50);
		System.out.println(result);
		assertEquals(150, role.getFame());
		//
		result = roleService.changeFame(true, role, (-1) * 10);
		System.out.println(result);
		assertEquals(140, role.getFame());
		//
		result = roleService.changeFame(true, role, 0);
		System.out.println(result);
		assertEquals(140, role.getFame());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void sendFame() {
		Role role = mockRole();
		role.setFame(100);
		roleService.sendFame(role, role.getFame(), 50);
	}

	@Test
	public void changeAttribute() {
		Role role = mockRole();
		Attribute result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 會發訊息
			result = roleService.changeAttribute(true, role, AttributeType.STRENGTH.getValue(), 10, 10, 1000);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(118, role.getAttributeGroup().getPoint(AttributeType.STRENGTH));
		assertEquals(140, role.getAttributeGroup().getFinal(AttributeType.STRENGTH));
	}

	@Test
	public void sendAttribute() {
		Role role = mockRole();
		//
		roleService.sendAttribute(role, role.getAttributeGroup().getAttribute(AttributeType.STRENGTH));
		roleService.sendAttribute(role, role.getAttributeGroup().getAttribute(AttributeType.PHYSICAL_ATTACK));
	}

	@Test
	public void sendAttributeGroup() {
		Role role = mockRole();
		//
		roleService.sendAttributeGroup(role, role.getAttributeGroup());
	}

	@Test
	public void setValue() {
		Role role = mockRole();
		System.out.println(role.getVipType());
		//
		String fieldName = "vipType";
		String setterName = ClassHelper.setterName(fieldName);
		//
		VipType fieldValue = VipType._0;
		//
		Method method = ClassHelper.getDeclaredMethod(Role.class, setterName, fieldValue.getClass());
		System.out.println(method);
		//
		ClassHelper.invokeDeclaredMethod(role, setterName, fieldValue.getClass(), VipType._1);
		System.out.println(role.getVipType());
		//
	}

	@Test
	/**
	 * 每10級發1次訊息
	 */
	public void famousByLevel_10() {
		Role role = mockRole();

		// 1 -> 10
		int diffLevel = 9;
		role.setLevel(role.getLevel() + diffLevel);// 10
		//
		int famousLevel = role.getLevel() - diffLevel;
		if ((famousLevel / 10) < (role.getLevel() / 10)) {
			System.out.println("famous: " + role.getLevel());
		}
		// 10 -> 15
		diffLevel = 5;
		role.setLevel(role.getLevel() + diffLevel);// 15
		famousLevel = role.getLevel() - diffLevel;
		if ((famousLevel / 10) < (role.getLevel() / 10)) {
			System.out.println("famous: " + role.getLevel());
		}
		// 15 -> 21
		diffLevel = 6;
		role.setLevel(role.getLevel() + diffLevel);// 21
		famousLevel = role.getLevel() - diffLevel;
		if ((famousLevel / 10) < (role.getLevel() / 10)) {
			System.out.println("famous: " + role.getLevel());
		}
	}
}
