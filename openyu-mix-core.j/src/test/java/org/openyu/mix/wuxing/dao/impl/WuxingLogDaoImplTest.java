package org.openyu.mix.wuxing.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.wuxing.WuxingTestSupporter;
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.log.WuxingPlayLog;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.mix.wuxing.log.impl.WuxingFamousLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPlayLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPutLogImpl;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.commons.util.DateHelper;

public class WuxingLogDaoImplTest extends WuxingTestSupporter {

	// --------------------------------------------------
	// WuxingPlayLog
	// --------------------------------------------------
	public static class WuxingPlayLogTest extends WuxingTestSupporter {
		/**
		 * 隨機五行玩的log
		 * 
		 * @return
		 */
		public static WuxingPlayLog randomWuxingPlayLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			WuxingPlayLog result = new WuxingPlayLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
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
		 * 檢核五行玩的log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertWuxingPlayLog(WuxingPlayLog expected,
				WuxingPlayLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
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
				WuxingPlayLog manorLandLog = randomWuxingPlayLog();
				// create
				Serializable pk = wuxingLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				WuxingPlayLog foundEntity = wuxingLogDao.find(
						WuxingPlayLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertWuxingPlayLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = wuxingLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				WuxingPlayLog deletedEntity = wuxingLogDao.delete(
						WuxingPlayLogImpl.class, manorLandLog.getSeq());
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
				WuxingPlayLog manorLandLog = randomWuxingPlayLog();
				//
				Serializable pk = wuxingLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				WuxingPlayLog foundEntity = wuxingLogDao.find(
						WuxingPlayLogImpl.class, manorLandLog.getSeq());
				assertWuxingPlayLog(manorLandLog, foundEntity);

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
		public void findWuxingPlayLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<WuxingPlayLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.findWuxingPlayLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteWuxingPlayLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.deleteWuxingPlayLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// WuxingPutLog
	// --------------------------------------------------
	public static class WuxingPutLogTest extends WuxingTestSupporter {
		/**
		 * 隨機五行放入log
		 * 
		 * @return
		 */
		public static WuxingPutLog randomWuxingPutLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			WuxingPutLog result = new WuxingPutLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setPutType(randomType(PutType.class));
			//
			return result;
		}

		/**
		 * 檢核五行放入log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertWuxingPutLog(WuxingPutLog expected,
				WuxingPutLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
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
				WuxingPutLog manorSeedLog = randomWuxingPutLog();
				// create
				Serializable pk = wuxingLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				WuxingPutLog foundEntity = wuxingLogDao.find(
						WuxingPutLogImpl.class, manorSeedLog.getSeq());
				printFind(i, foundEntity);
				assertWuxingPutLog(manorSeedLog, foundEntity);

				// update
				manorSeedLog.setLogDate(DateHelper.today());
				int updated = wuxingLogDao.update(manorSeedLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				WuxingPutLog deletedEntity = wuxingLogDao.delete(
						WuxingPutLogImpl.class, manorSeedLog.getSeq());
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
				WuxingPutLog manorSeedLog = randomWuxingPutLog();
				//
				Serializable pk = wuxingLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				WuxingPutLog foundEntity = wuxingLogDao.find(
						WuxingPutLogImpl.class, manorSeedLog.getSeq());
				assertWuxingPutLog(manorSeedLog, foundEntity);

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
		public void findWuxingPutLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<WuxingPutLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.findWuxingPutLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteWuxingPutLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.deleteWuxingPutLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// WuxingFamousLog
	// --------------------------------------------------
	public static class WuxingFamousLogTest extends WuxingTestSupporter {
		/**
		 * 隨機五行玩的log
		 * 
		 * @return
		 */
		public static WuxingFamousLog randomWuxingFamousLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			WuxingFamousLog result = new WuxingFamousLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setPlayType(randomType(PlayType.class));
			result.setPlayTime(System.currentTimeMillis());
			return result;
		}

		/**
		 * 檢核五行玩的log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertWuxingFamousLog(WuxingFamousLog expected,
				WuxingFamousLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
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
				WuxingFamousLog manorLandLog = randomWuxingFamousLog();
				// create
				Serializable pk = wuxingLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				WuxingFamousLog foundEntity = wuxingLogDao.find(
						WuxingFamousLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertWuxingFamousLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = wuxingLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				WuxingFamousLog deletedEntity = wuxingLogDao.delete(
						WuxingFamousLogImpl.class, manorLandLog.getSeq());
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
				WuxingFamousLog manorLandLog = randomWuxingFamousLog();
				//
				Serializable pk = wuxingLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				WuxingFamousLog foundEntity = wuxingLogDao.find(
						WuxingFamousLogImpl.class, manorLandLog.getSeq());
				assertWuxingFamousLog(manorLandLog, foundEntity);

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
		public void findWuxingFamousLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<WuxingFamousLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.findWuxingFamousLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteWuxingFamousLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = wuxingLogDao.deleteWuxingFamousLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

}
