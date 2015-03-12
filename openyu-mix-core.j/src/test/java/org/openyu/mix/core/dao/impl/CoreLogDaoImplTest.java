package org.openyu.mix.core.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.core.CoreTestSupporter;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.mix.core.log.impl.CoreConnectLogImpl;
import org.openyu.mix.core.vo.Core.ConnectAction;
import org.openyu.commons.util.DateHelper;

public class CoreLogDaoImplTest extends CoreTestSupporter {
	// --------------------------------------------------
	// CoreConnectionLog
	// --------------------------------------------------
	public static class CoreConnectionLogTest extends CoreTestSupporter {
		/**
		 * 隨機角色連線log
		 * 
		 * @return
		 */
		public static CoreConnectLog randomCoreConnectionLog() {
			final String UNIQUE = randomUnique();
			final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
			//
			final String ROLE_ID = "TEST_ROLE" + UNIQUE;
			final String ROLE_NAME = "測試角色" + UNIQUE;
			//
			CoreConnectLog result = new CoreConnectLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.10.1"));
			//
			result.setConnectAction(randomType(ConnectAction.class));
			result.setClientIp(randomIp("192.168.1"));
			result.setEnterTime(randomDateLong());
			result.setLeaveTime(result.getEnterTime()
					+ randomLong(60 * 60 * 1 * 1000));
			return result;
		}

		/**
		 * 檢核角色連線log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertCoreConnectionLog(CoreConnectLog expected,
				CoreConnectLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getRoleName(), actual.getRoleName());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				assertEquals(expected.getServerIp(), actual.getServerIp());
				//
				assertEquals(expected.getClientIp(), actual.getClientIp());
				assertEquals(expected.getEnterTime(), actual.getEnterTime());
				assertEquals(expected.getLeaveTime(), actual.getLeaveTime());
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
				CoreConnectLog coreConnectionLog = randomCoreConnectionLog();
				// create
				Serializable pk = coreLogDao.insert(coreConnectionLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				CoreConnectLog foundEntity = coreLogDao.find(
						CoreConnectLogImpl.class, coreConnectionLog.getSeq());
				printFind(i, foundEntity);
				assertCoreConnectionLog(coreConnectionLog, foundEntity);

				// update
				coreConnectionLog.setLogDate(DateHelper.today());
				int updated = coreLogDao.update(coreConnectionLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				CoreConnectLog deletedEntity = coreLogDao.delete(
						CoreConnectLogImpl.class, coreConnectionLog.getSeq());
				printDelete(i, deletedEntity);
				assertNotNull(deletedEntity);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// verified: ok
		public void insert() {
			int count = 10;
			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				// 隨機
				CoreConnectLog coreConnectionLog = randomCoreConnectionLog();
				//
				Serializable pk = coreLogDao.insert(coreConnectionLog);
				printInsert(i, pk);
				assertNotNull(pk);

				CoreConnectLog foundEntity = coreLogDao.find(
						CoreConnectLogImpl.class, coreConnectionLog.getSeq());
				assertCoreConnectionLog(coreConnectionLog,
						foundEntity);

				System.out.println(coreConnectionLog);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findCoreConnectLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<CoreConnectLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = coreLogDao.findCoreConnectLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteCoreConnectLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = coreLogDao.deleteCoreConnectLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findCoreConnectLogByEnterTime() {
			final String ROLE_ID = "TEST_ROLE";
			final long ENTER_TIME = 1348822422740L;
			CoreConnectLog result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = coreLogDao.findCoreConnectLog(ROLE_ID, ENTER_TIME);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertNotNull(result);
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findCoreConnectLogByLatest() {
			final String ROLE_ID = "TEST_ROLE";
			CoreConnectLog result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = coreLogDao.findCoreConnectLogByLatest(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertNotNull(result);
		}
	}

}
