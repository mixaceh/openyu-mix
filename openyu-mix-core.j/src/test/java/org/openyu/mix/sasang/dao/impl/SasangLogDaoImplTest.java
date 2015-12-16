package org.openyu.mix.sasang.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.sasang.SasangTestSupporter;
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.log.SasangPutLog;
import org.openyu.mix.sasang.log.impl.SasangFamousLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPlayLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPutLogImpl;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.service.SasangService.PutType;
import org.openyu.commons.util.DateHelper;

public class SasangLogDaoImplTest extends SasangTestSupporter {

	// --------------------------------------------------
	// SasangPlayLog
	// --------------------------------------------------
	public static class SasangPlayLogTest extends SasangTestSupporter {
		/**
		 * 隨機四象玩的log
		 * 
		 * @return
		 */
		public static SasangPlayLog randomSasangPlayLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			SasangPlayLog result = new SasangPlayLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setPlayType(randomType(PlayType.class));
			result.setPlayTime(System.currentTimeMillis());
			result.setRealTimes(randomInt(10));
			//
			result.setSpendGold(randomLong(100000));
			result.setSpendCoin(randomInt(100000));
			return result;
		}

		/**
		 * 檢核四象玩的log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertSasangPlayLog(SasangPlayLog expected,
				SasangPlayLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				//
				assertEquals(expected.getPlayType(), actual.getPlayType());
				assertEquals(expected.getPlayTime(), actual.getPlayTime());
				assertEquals(expected.getRealTimes(), actual.getRealTimes());
				//
				assertEquals(expected.getSpendGold(), actual.getSpendGold());
				assertEquals(expected.getSpendCoin(), actual.getSpendCoin());
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
				SasangPlayLog manorLandLog = randomSasangPlayLog();
				// create
				Serializable pk = sasangLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				SasangPlayLog foundEntity = sasangLogDao.find(
						SasangPlayLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertSasangPlayLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = sasangLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				SasangPlayLog deletedEntity = sasangLogDao.delete(
						SasangPlayLogImpl.class, manorLandLog.getSeq());
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
				SasangPlayLog manorLandLog = randomSasangPlayLog();
				//
				Serializable pk = sasangLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				SasangPlayLog foundEntity = sasangLogDao.find(
						SasangPlayLogImpl.class, manorLandLog.getSeq());
				assertSasangPlayLog(manorLandLog, foundEntity);

				System.out.println(manorLandLog);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findSasangPlayLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<SasangPlayLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.findSasangPlayLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteSasangPlayLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.deleteSasangPlayLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// SasangPutLog
	// --------------------------------------------------
	public static class SasangPutLogTest extends SasangTestSupporter {
		/**
		 * 隨機四象放入log
		 * 
		 * @return
		 */
		public static SasangPutLog randomSasangPutLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			SasangPutLog result = new SasangPutLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setPutType(randomType(PutType.class));
			//
			return result;
		}

		/**
		 * 檢核四象放入log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertSasangPutLog(SasangPutLog expected,
				SasangPutLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				//
				assertEquals(expected.getPutType(), actual.getPutType());
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
				SasangPutLog manorSeedLog = randomSasangPutLog();
				// create
				Serializable pk = sasangLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				SasangPutLog foundEntity = sasangLogDao.find(
						SasangPutLogImpl.class, manorSeedLog.getSeq());
				printFind(i, foundEntity);
				assertSasangPutLog(manorSeedLog, foundEntity);

				// update
				manorSeedLog.setLogDate(DateHelper.today());
				int updated = sasangLogDao.update(manorSeedLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				SasangPutLog deletedEntity = sasangLogDao.delete(
						SasangPutLogImpl.class, manorSeedLog.getSeq());
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
				SasangPutLog manorSeedLog = randomSasangPutLog();
				//
				Serializable pk = sasangLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				SasangPutLog foundEntity = sasangLogDao.find(
						SasangPutLogImpl.class, manorSeedLog.getSeq());
				assertSasangPutLog(manorSeedLog, foundEntity);

				System.out.println(manorSeedLog);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findSasangPutLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<SasangPutLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.findSasangPutLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteSasangPutLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.deleteSasangPutLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// SasangFamousLog
	// --------------------------------------------------
	public static class SasangFamousLogTest extends SasangTestSupporter {
		/**
		 * 隨機四象玩的log
		 * 
		 * @return
		 */
		public static SasangFamousLog randomSasangFamousLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			SasangFamousLog result = new SasangFamousLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setPlayType(randomType(PlayType.class));
			result.setPlayTime(System.currentTimeMillis());
			return result;
		}

		/**
		 * 檢核四象玩的log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertSasangFamousLog(SasangFamousLog expected,
				SasangFamousLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				//
				assertEquals(expected.getPlayType(), actual.getPlayType());
				assertEquals(expected.getPlayTime(), actual.getPlayTime());
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
				SasangFamousLog manorLandLog = randomSasangFamousLog();
				// create
				Serializable pk = sasangLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				SasangFamousLog foundEntity = sasangLogDao.find(
						SasangFamousLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertSasangFamousLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = sasangLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				SasangFamousLog deletedEntity = sasangLogDao.delete(
						SasangFamousLogImpl.class, manorLandLog.getSeq());
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
				SasangFamousLog manorLandLog = randomSasangFamousLog();
				//
				Serializable pk = sasangLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				SasangFamousLog foundEntity = sasangLogDao.find(
						SasangFamousLogImpl.class, manorLandLog.getSeq());
				assertSasangFamousLog(manorLandLog, foundEntity);

				System.out.println(manorLandLog);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findSasangFamousLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<SasangFamousLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.findSasangFamousLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteSasangFamousLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = sasangLogDao.deleteSasangFamousLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

}
