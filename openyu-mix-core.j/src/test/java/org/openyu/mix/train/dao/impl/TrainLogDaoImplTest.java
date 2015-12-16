package org.openyu.mix.train.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.train.TrainTestSupporter;
import org.openyu.mix.train.log.TrainLog;
import org.openyu.mix.train.log.impl.TrainLogImpl;
import org.openyu.mix.train.service.TrainService.ActionType;
import org.openyu.commons.util.DateHelper;

public class TrainLogDaoImplTest extends TrainTestSupporter {

	// --------------------------------------------------
	// TrainLog
	// --------------------------------------------------
	public static class TrainLogTest extends TrainTestSupporter {
		/**
		 * 隨機訓練log
		 * 
		 * @return
		 */
		public static TrainLog randomTrainLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			TrainLog result = new TrainLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setActionType(randomType(ActionType.class));
			//
			result.setInspireTime(System.currentTimeMillis());

			return result;
		}

		/**
		 * 檢核訓練log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertTrainLog(TrainLog expected, TrainLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				//
				assertEquals(expected.getActionType(), actual.getActionType());
				assertEquals(expected.getInspireTime(), actual.getInspireTime());
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
				TrainLog manorLandLog = randomTrainLog();
				// create
				Serializable pk = trainLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				TrainLog foundEntity = trainLogDao.find(TrainLogImpl.class,
						manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertTrainLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = trainLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				TrainLog deletedEntity = trainLogDao.delete(TrainLogImpl.class,
						manorLandLog.getSeq());
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
				TrainLog manorLandLog = randomTrainLog();
				//
				Serializable pk = trainLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				TrainLog foundEntity = trainLogDao.find(TrainLogImpl.class,
						manorLandLog.getSeq());
				assertTrainLog(manorLandLog, foundEntity);

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
		public void findTrainLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<TrainLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = trainLogDao.findTrainLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteTrainLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = trainLogDao.deleteTrainLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

}
