package org.openyu.mix.role.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.log.RoleFameLog;
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.log.RoleLevelLog;
import org.openyu.mix.role.log.impl.RoleFameLogImpl;
import org.openyu.mix.role.log.impl.RoleGoldLogImpl;
import org.openyu.mix.role.log.impl.RoleLevelLogImpl;
import org.openyu.mix.role.service.RoleService.ActionType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import org.openyu.commons.util.DateHelper;

public class RoleLogDaoImplTest extends RoleTestSupporter {
	// --------------------------------------------------
	// RoleLevelLog
	// --------------------------------------------------
	public static class RoleLevelLogTest extends RoleTestSupporter {
		/**
		 * 隨機等級改變log
		 * 
		 * @return
		 */
		public static RoleLevelLog randomRoleLevelLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			//
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試" + UNIQUE;
			//
			RoleLevelLog result = new RoleLevelLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setBeforeLevel(randomInt(10));
			result.setLevel(1);
			result.setAfterLevel(result.getBeforeLevel() + 1);
			return result;
		}

		/**
		 * 檢核等級改變log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertRoleLevelLog(RoleLevelLog expected, RoleLevelLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				assertEquals(expected.getBeforeLevel(), actual.getBeforeLevel());
				assertEquals(expected.getAfterLevel(), actual.getAfterLevel());
			}
		}

		@Test
		// insert -> find -> delete
		//
		// 10 times: 7237 mills.
		// 10 times: 6825 mills.
		// 10 times: 6693 mills.
		//
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void crud() {
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				RoleLevelLog roleLevelLog = randomRoleLevelLog();
				// create
				Serializable pk = roleLogDao.insert(roleLevelLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				RoleLevelLog foundEntity = roleLogDao.find(RoleLevelLogImpl.class, roleLevelLog.getSeq());
				printFind(i, foundEntity);
				assertRoleLevelLog(roleLevelLog, foundEntity);

				// update
				roleLevelLog.setLogDate(DateHelper.today());
				int updated = roleLogDao.update(roleLevelLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				RoleLevelLog deletedEntity = roleLogDao.delete(RoleLevelLogImpl.class, roleLevelLog.getSeq());
				printDelete(i, deletedEntity);
				assertNotNull(deletedEntity);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void insert() {
			// 隨機
			RoleLevelLog roleLevelLog = randomRoleLevelLog();
			//
			Serializable pk = roleLogDao.insert(roleLevelLog);
			printInsert(pk);
			assertNotNull(pk);

			RoleLevelLog foundEntity = roleLogDao.find(RoleLevelLogImpl.class, roleLevelLog.getSeq());
			assertRoleLevelLog(roleLevelLog, foundEntity);

			System.out.println(roleLevelLog);
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findRoleLevelLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<RoleLevelLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.findRoleLevelLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteRoleLevelLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.deleteRoleLevelLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// RoleGoldLog
	// --------------------------------------------------
	public static class RoleGoldLogTest extends RoleTestSupporter {

		/**
		 * 隨機等級改變log
		 * 
		 * @return
		 */
		public static RoleGoldLog randomRoleGoldLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試" + UNIQUE;
			//
			RoleGoldLog result = new RoleGoldLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setActionType(randomType(ActionType.class));
			result.setBeforeGold(randomLong(1000));
			result.setGold(100L);
			result.setAfterGold(result.getBeforeGold() + 100L);
			return result;
		}

		/**
		 * 檢核等級改變log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertRoleGoldLog(RoleGoldLog expected, RoleGoldLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				assertEquals(expected.getActionType(), actual.getActionType());
				assertEquals(expected.getBeforeGold(), actual.getBeforeGold());
				assertEquals(expected.getAfterGold(), actual.getAfterGold());
			}
		}

		@Test
		// insert -> find -> delete
		//
		// 10 times: 7237 mills.
		// 10 times: 6825 mills.
		// 10 times: 6693 mills.
		//
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void crud() {
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				RoleGoldLog roleGoldLog = randomRoleGoldLog();
				// create
				Serializable pk = roleLogDao.insert(roleGoldLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				RoleGoldLog foundEntity = roleLogDao.find(RoleGoldLogImpl.class, roleGoldLog.getSeq());
				printFind(i, foundEntity);
				assertRoleGoldLog(roleGoldLog, foundEntity);

				// update
				roleGoldLog.setLogDate(DateHelper.today());
				int updated = roleLogDao.update(roleGoldLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				RoleGoldLog deletedEntity = roleLogDao.delete(RoleGoldLogImpl.class, roleGoldLog.getSeq());
				printDelete(i, deletedEntity);
				assertNotNull(deletedEntity);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void insert() {
			// 隨機
			RoleGoldLog roleGoldLog = randomRoleGoldLog();
			//
			Serializable pk = roleLogDao.insert(roleGoldLog);
			printInsert(pk);
			assertNotNull(pk);

			RoleGoldLog foundEntity = roleLogDao.find(RoleGoldLogImpl.class, roleGoldLog.getSeq());
			assertRoleGoldLog(roleGoldLog, foundEntity);

			System.out.println(roleGoldLog);
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findRoleGoldLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<RoleGoldLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.findRoleGoldLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteRoleGoldLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.deleteRoleGoldLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// RoleFameLog
	// --------------------------------------------------
	public static class RoleFameLogTest extends RoleTestSupporter {
		/**
		 * 隨機等級改變log
		 * 
		 * @return
		 */
		public static RoleFameLog randomRoleFameLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			//
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試" + UNIQUE;
			//
			RoleFameLog result = new RoleFameLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setBeforeFame(randomInt(10));
			result.setFame(1);
			result.setAfterFame(result.getBeforeFame() + 1);
			return result;
		}

		/**
		 * 檢核等級改變log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertRoleFameLog(RoleFameLog expected, RoleFameLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				assertEquals(expected.getBeforeFame(), actual.getBeforeFame());
				assertEquals(expected.getAfterFame(), actual.getAfterFame());
			}
		}

		@Test
		// insert -> find -> delete
		//
		// 10 times: 7237 mills.
		// 10 times: 6825 mills.
		// 10 times: 6693 mills.
		//
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void crud() {
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				RoleFameLog roleFameLog = randomRoleFameLog();
				// create
				Serializable pk = roleLogDao.insert(roleFameLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				RoleFameLog foundEntity = roleLogDao.find(RoleFameLogImpl.class, roleFameLog.getSeq());
				printFind(i, foundEntity);
				assertRoleFameLog(roleFameLog, foundEntity);

				// update
				roleFameLog.setLogDate(DateHelper.today());
				int updated = roleLogDao.update(roleFameLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				RoleFameLog deletedEntity = roleLogDao.delete(RoleFameLogImpl.class, roleFameLog.getSeq());
				printDelete(i, deletedEntity);
				assertNotNull(deletedEntity);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void insert() {
			// 隨機
			RoleFameLog roleFameLog = randomRoleFameLog();
			//
			Serializable pk = roleLogDao.insert(roleFameLog);
			printInsert(pk);
			assertNotNull(pk);

			RoleFameLog foundEntity = roleLogDao.find(RoleFameLogImpl.class, roleFameLog.getSeq());
			assertRoleFameLog(roleFameLog, foundEntity);

			System.out.println(roleFameLog);
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findRoleFameLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<RoleFameLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.findRoleFameLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteRoleFameLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = roleLogDao.deleteRoleFameLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}
}
