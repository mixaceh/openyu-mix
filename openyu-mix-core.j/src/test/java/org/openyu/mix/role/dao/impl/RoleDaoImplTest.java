package org.openyu.mix.role.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.po.impl.RolePoImpl;
import org.openyu.mix.role.service.impl.RoleServiceImplTest;
import org.openyu.mix.role.vo.impl.BagPenImplTest;
import org.openyu.mix.train.vo.TrainPen;

public class RoleDaoImplTest extends RoleTestSupporter {

	/**
	 * 隨機角色
	 * 
	 * @return
	 */
	public static RolePo randomRolePo() {
		final String UNIQUE = randomUnique();
		final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
		//
		final String ROLE_ID = "TEST_ROLE" + UNIQUE;
		final String ZH_TW_NAME = "測試角色" + UNIQUE;
		final String EN_US_NAME = "Test Role" + UNIQUE;
		//
		RolePo result = new RolePoImpl();
		result.setId(ROLE_ID);
		result.setValid(randomBoolean());
		result.addName(Locale.TRADITIONAL_CHINESE, ZH_TW_NAME);
		result.addName(Locale.US, EN_US_NAME);
		//
		// result.setRaceType(randomType(RaceType.class));
		// result.setCareerType(randomType(CareerType.class));
		//
		result.setExp(randomLong());
		result.setGold(randomLong());
		result.setLevel(randomInt());
		// 屬性
		result.getAttributeGroup().setAttributes(
				RoleServiceImplTest.randomAttributes());
		//
		result.setLeaveTime(randomLong());
		result.setAcceptorId("slave1");
		// 帳號
		result.setAccountId(ACCOUNT_ID);

		// 包包欄 2012/10/01
		final String THING_ID = "T_POTION_HP_G001";
		result.setBagPen(BagPenImplTest.mockBagPenWithSameThing(THING_ID));

		return result;
	}

	/**
	 * 檢核角色
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertRolePo(RolePo expected, RolePo actual) {
		if (expected == null) {
			assertNull(actual);
		} else {
			assertNotNull(actual);
			//
			assertEquals(expected.getId(), actual.getId());
			assertEquals(expected.getValid(), actual.getValid());
			assertCollectionEquals(expected.getNames(), actual.getNames());
			//
			// assertEquals(expected.getRaceType(), actual.getRaceType());
			// assertEquals(expected.getCareerType(), actual.getCareerType());
			//
			assertEquals(expected.getExp(), actual.getExp());
			assertEquals(expected.getGold(), actual.getGold());
			assertEquals(expected.getLevel(), actual.getLevel());
			RoleServiceImplTest.assertAttrubutes(expected.getAttributeGroup()
					.getAttributes(), actual.getAttributeGroup()
					.getAttributes());
			//
			assertEquals(expected.getLeaveTime(), actual.getLeaveTime());
			// 帳號
			assertEquals(expected.getAccountId(), actual.getAccountId());

			// 包包欄 2012/10/01
			RoleServiceImplTest.assertBagPen(expected.getBagPen(),
					actual.getBagPen());

		}
	}

	@Test
	// insert -> find -> delete
	//
	// 10 times: 7237 mills.
	// 10 times: 6825 mills.
	// 10 times: 6693 mills.
	//
	// verified: ok
	public void crud() {
		int count = 10;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 隨機
			RolePo rolePo = randomRolePo();

			// create
			Serializable pk = roleDao.insert(rolePo);
			printInsert(i, pk);
			assertNotNull(pk);

			// retrieve
			RolePo foundEntity = roleDao
					.find(RolePoImpl.class, rolePo.getSeq());
			printFind(i, foundEntity);
			assertRolePo(rolePo, foundEntity);

			// update
			rolePo.setValid(true);
			int updated = roleDao.update(rolePo);
			printUpdate(i, updated);
			assertTrue(updated > 0);

			// delete
			RolePo deletedEntity = roleDao.delete(RolePoImpl.class,
					rolePo.getSeq());
			printDelete(i, deletedEntity);
			assertNotNull(deletedEntity);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	// verified: ok
	public void insert() {
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 隨機
			RolePo rolePo = randomRolePo();
			//
			Serializable pk = roleDao.insert(rolePo);
			printInsert(i, pk);
			assertNotNull(pk);

			RolePo foundEntity = roleDao.find(RolePoImpl.class, rolePo.getSeq());
			assertRolePo(rolePo, foundEntity);

			System.out.println(rolePo);
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
		RolePo result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleDao.findRole(ROLE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		//
		System.out.println(result.getTrainPen());
		//
		TrainPen trainPen = result.getTrainPen();
		trainPen.addDailyMills(20 * 10000L);
		int update = roleDao.update(result);
		System.out.println(update);
		System.out.println(trainPen);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findRoleByValid() {
		List<RolePo> result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleDao.findRole(Locale.TRADITIONAL_CHINESE, true);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.size() + ", " + result);
		assertNotNull(result);
		//
		result = roleDao.findRole(false);
		System.out.println(result.size() + ", " + result);
	}

	@Test
	public void update() {
		final String ROLE_ID = "TEST_ROLE2";

		RolePo value = new RolePoImpl();
		value.setSeq(1L);
		value.setVersion(1);// 記得改版本號
		//
		value.setId(ROLE_ID);
		// value.setRaceType(RaceType.HUMAN);
		// value.setCareerType(CareerType.WARRIOR_1);
		//
		int result = 0;

		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = roleService.update(value);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result > 0);

		System.out.println(value.getTrainPen());
	}

	@Test
	public void reindex() {
		roleDao.reindex(RolePoImpl.class);
	}

}
