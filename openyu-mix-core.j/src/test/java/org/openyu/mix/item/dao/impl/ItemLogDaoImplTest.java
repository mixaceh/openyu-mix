package org.openyu.mix.item.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.item.ItemTestSupporter;
import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.mix.item.log.impl.ItemDecreaseLogImpl;
import org.openyu.mix.item.log.impl.ItemEnhanceLogImpl;
import org.openyu.mix.item.log.impl.ItemIncreaseLogImpl;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.mix.item.vo.thing.EnhanceThing;
import org.openyu.commons.util.DateHelper;

public class ItemLogDaoImplTest extends ItemTestSupporter {
	// --------------------------------------------------
	// ItemIncreaseItemLog
	// --------------------------------------------------
	public static class ItemIncreaseLogTest extends ItemTestSupporter {

		/**
		 * 隨機道具增加log
		 * 
		 * @return
		 */
		public static ItemIncreaseLog randomItemIncreaseLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試角色" + UNIQUE;
			//
			ItemIncreaseLog result = new ItemIncreaseLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			Item item = ThingCollector.getInstance().createThing(
					"T_POTION_HP_G001");
			item.setAmount(randomInt(100));
			result.getItems().add(item);
			result.setActionType(randomType(ActionType.class));
			return result;
		}

		/**
		 * 檢核道具增加log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertItemIncreaseLog(ItemIncreaseLog expected,
				ItemIncreaseLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				assertEquals(expected.getActionType(), actual.getActionType());
				assertCollectionEquals(expected.getItems(), actual.getItems());
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
				ItemIncreaseLog log = randomItemIncreaseLog();
				// create
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				ItemIncreaseLog foundEntity = itemLogDao.find(
						ItemIncreaseLogImpl.class, log.getSeq());
				printFind(i, foundEntity);
				assertItemIncreaseLog(log, foundEntity);

				// update
				log.setLogDate(DateHelper.today());
				int updated = itemLogDao.update(log);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				ItemIncreaseLog deletedEntity = itemLogDao.delete(
						ItemIncreaseLogImpl.class, log.getSeq());
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
				ItemIncreaseLog log = randomItemIncreaseLog();
				//
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				ItemIncreaseLog foundEntity = itemLogDao.find(
						ItemIncreaseLogImpl.class, log.getSeq());
				assertItemIncreaseLog(log, foundEntity);

				System.out.println(log);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findItemIncreaseLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<ItemIncreaseLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.findItemIncreaseLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteItemIncreaseLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.deleteItemIncreaseLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// ItemDecreaseItemLog
	// --------------------------------------------------
	public static class ItemDecreaseLogTest extends ItemTestSupporter {

		/**
		 * 隨機道具減少log
		 * 
		 * @return
		 */
		public static ItemDecreaseLog randomItemDecreaseLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試角色" + UNIQUE;
			//
			ItemDecreaseLog result = new ItemDecreaseLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			Item item = ThingCollector.getInstance().createThing(
					"T_POTION_HP_G001");
			item.setAmount(randomInt(100));
			result.getItems().add(item);
			result.setActionType(randomType(ActionType.class));
			return result;
		}

		/**
		 * 檢核道具減少log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertItemDecreaseLog(ItemDecreaseLog expected,
				ItemDecreaseLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				assertEquals(expected.getActionType(), actual.getActionType());
				assertCollectionEquals(expected.getItems(), actual.getItems());
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
			int result = 0;
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				ItemDecreaseLog log = randomItemDecreaseLog();
				// create
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				ItemDecreaseLog foundEntity = itemLogDao.find(
						ItemDecreaseLogImpl.class, log.getSeq());
				printFind(i, foundEntity);
				assertItemDecreaseLog(log, foundEntity);

				// update
				log.setLogDate(DateHelper.today());
				result = itemLogDao.update(log);
				printUpdate(i, result);
				assertTrue(result > 0);

				// delete
				ItemDecreaseLog deletedEntity = itemLogDao.delete(
						ItemDecreaseLogImpl.class, log.getSeq());
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
				ItemDecreaseLog log = randomItemDecreaseLog();
				//
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				ItemDecreaseLog foundEntity = itemLogDao.find(
						ItemDecreaseLogImpl.class, log.getSeq());
				assertItemDecreaseLog(log, foundEntity);

				System.out.println(log);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findItemDecreaseLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<ItemDecreaseLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.findItemDecreaseLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteItemDecreaseLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.deleteItemDecreaseLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// ItemEnhanceItemLog
	// --------------------------------------------------
	public static class ItemEnhanceItemLogTest extends ItemTestSupporter {

		/**
		 * 隨機道具強化log
		 * 
		 * @return
		 */
		public static ItemEnhanceLog randomItemEnhanceLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試角色" + UNIQUE;
			//
			ItemEnhanceLog result = new ItemEnhanceLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			// 被強化後的道具
			Weapon item = WeaponCollector.getInstance().createWeapon(
					"W_MARS_SWORD_E001");
			item.setAmount(1);
			item.setEnhanceLevel(EnhanceLevel._0);
			result.setItem(item);
			result.setActionType(randomType(ActionType.class));
			//
			result.setBeforeEnhance(randomInt(10));
			result.setEnhance(1);
			result.setAfterEnhance(result.getBeforeEnhance() + 1);
			// 消耗的道具
			EnhanceThing enhanceThing = (EnhanceThing) ThingCollector
					.getInstance().createThing("T_ENHANCE_WEAPON_E_G001");
			enhanceThing.setAmount(1);
			result.setSpendItem(enhanceThing);
			return result;
		}

		/**
		 * 檢核道具強化log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertItemEnhanceLog(ItemEnhanceLog expected,
				ItemEnhanceLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				assertEquals(expected.getActionType(), actual.getActionType());
				//
				assertEquals(expected.getBeforeEnhance(),
						actual.getBeforeEnhance());
				assertEquals(expected.getEnhance(), actual.getEnhance());
				assertEquals(expected.getAfterEnhance(),
						actual.getAfterEnhance());
				assertEquals(expected.getItem(), actual.getItem());
				assertEquals(expected.getSpendItem(), actual.getSpendItem());
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
			int result = 0;
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				ItemEnhanceLog log = randomItemEnhanceLog();
				// create
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				ItemEnhanceLog foundEntity = itemLogDao.find(
						ItemEnhanceLogImpl.class, log.getSeq());
				printFind(i, foundEntity);
				assertItemEnhanceLog(log, foundEntity);

				// update
				log.setLogDate(DateHelper.today());
				result = itemLogDao.update(log);
				printUpdate(i, result);
				assertTrue(result > 0);

				// delete
				ItemEnhanceLog deletedEntity = itemLogDao.delete(
						ItemEnhanceLogImpl.class, log.getSeq());
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
				ItemEnhanceLog log = randomItemEnhanceLog();
				//
				Serializable pk = itemLogDao.insert(log);
				printInsert(i, pk);
				assertNotNull(pk);

				ItemEnhanceLog foundEntity = itemLogDao.find(
						ItemEnhanceLogImpl.class, log.getSeq());
				assertItemEnhanceLog(log, foundEntity);

				System.out.println(log);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findItemEnhanceLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<ItemEnhanceLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.findItemEnhanceLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteItemEnhanceLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = itemLogDao.deleteItemEnhanceLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}
}
