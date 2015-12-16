package org.openyu.mix.manor.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.manor.ManorTestSupporter;
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.mix.manor.log.impl.ManorLandLogImpl;
import org.openyu.mix.manor.log.impl.ManorSeedLogImpl;
import org.openyu.mix.manor.service.ManorService.ActionType;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.LandImpl;
import org.openyu.mix.manor.vo.impl.SeedImpl;
import org.openyu.commons.util.DateHelper;

public class ManorLogDaoImplTest extends ManorTestSupporter {

	// --------------------------------------------------
	// ManorLandLog
	// --------------------------------------------------
	public static class ManorLandLogTest extends ManorTestSupporter {
		/**
		 * 隨機莊園土地log
		 * 
		 * @return
		 */
		public static ManorLandLog randomManorLandLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			ManorLandLog result = new ManorLandLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setActionType(randomType(ActionType.class));
			result.setFarmIndex(randomInt(10));
			//
			Land land = new LandImpl("L_TROPICS_G001");
			land.setUniqueId(Land.UNIQUE_ID_PREFIX + randomUnique());
			land.setAmount(1);
			result.setLand(land);
			return result;
		}

		/**
		 * 檢核莊園土地log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertManorLandLog(ManorLandLog expected,
				ManorLandLog actual) {
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
				assertEquals(expected.getFarmIndex(), actual.getFarmIndex());
				assertEquals(expected.getLand(), actual.getLand());
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
				ManorLandLog manorLandLog = randomManorLandLog();
				// create
				Serializable pk = manorLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				ManorLandLog foundEntity = manorLogDao.find(
						ManorLandLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertManorLandLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = manorLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				ManorLandLog deletedEntity = manorLogDao.delete(
						ManorLandLogImpl.class, manorLandLog.getSeq());
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
				ManorLandLog manorLandLog = randomManorLandLog();
				//
				Serializable pk = manorLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				ManorLandLog foundEntity = manorLogDao.find(
						ManorLandLogImpl.class, manorLandLog.getSeq());
				assertManorLandLog(manorLandLog, foundEntity);

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
		public void findManorLandLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<ManorLandLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = manorLogDao.findManorLandLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteManorLandLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = manorLogDao.deleteManorLandLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// ManorSeedLog
	// --------------------------------------------------
	public static class ManorSeedLogTest extends ManorTestSupporter {
		/**
		 * 隨機莊園種子log
		 * 
		 * @return
		 */
		public static ManorSeedLog randomManorSeedLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			ManorSeedLog result = new ManorSeedLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptorId(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setCultureType(randomType(CultureType.class));
			result.setFarmIndex(randomInt(10));
			result.setGridIndex(randomInt(10));
			//
			Seed seed = new SeedImpl("S_COTTON_G001");
			seed.setUniqueId(Seed.UNIQUE_ID_PREFIX + randomUnique());
			seed.setAmount(1);
			result.setSeed(seed);
			return result;
		}

		/**
		 * 檢核莊園種子log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertManorSeedLog(ManorSeedLog expected,
				ManorSeedLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptorId(), actual.getAcceptorId());
				//
				assertEquals(expected.getCultureType(), actual.getCultureType());
				assertEquals(expected.getFarmIndex(), actual.getFarmIndex());
				assertEquals(expected.getGridIndex(), actual.getGridIndex());
				assertEquals(expected.getSeed(), actual.getSeed());
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
				ManorSeedLog manorSeedLog = randomManorSeedLog();
				// create
				Serializable pk = manorLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				ManorSeedLog foundEntity = manorLogDao.find(
						ManorSeedLogImpl.class, manorSeedLog.getSeq());
				printFind(i, foundEntity);
				assertManorSeedLog(manorSeedLog, foundEntity);

				// update
				manorSeedLog.setLogDate(DateHelper.today());
				int updated = manorLogDao.update(manorSeedLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				ManorSeedLog deletedEntity = manorLogDao.delete(
						ManorSeedLogImpl.class, manorSeedLog.getSeq());
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
				ManorSeedLog manorSeedLog = randomManorSeedLog();
				//
				Serializable pk = manorLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				ManorSeedLog foundEntity = manorLogDao.find(
						ManorSeedLogImpl.class, manorSeedLog.getSeq());
				assertManorSeedLog(manorSeedLog, foundEntity);

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
		public void findManorSeedLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<ManorSeedLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = manorLogDao.findManorSeedLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteManorSeedLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = manorLogDao.deleteManorSeedLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}
}
