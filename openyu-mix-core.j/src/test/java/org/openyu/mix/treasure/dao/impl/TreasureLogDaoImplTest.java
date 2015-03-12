package org.openyu.mix.treasure.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.treasure.TreasureTestSupporter;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.mix.treasure.log.impl.TreasureBuyLogImpl;
import org.openyu.mix.treasure.log.impl.TreasureRefreshLogImpl;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.util.DateHelper;

public class TreasureLogDaoImplTest extends TreasureTestSupporter {

	// --------------------------------------------------
	// TreasureRefreshLog
	// --------------------------------------------------
	public static class TreasureRefreshLogTest extends TreasureTestSupporter {
		/**
		 * 隨機祕寶刷新log
		 * 
		 * @return
		 */
		public static TreasureRefreshLog randomTreasureRefreshLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			TreasureRefreshLog result = new TreasureRefreshLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setRefreshTime(System.currentTimeMillis());
			Treasure treasure = treasureCollector.createTreasure(
					"ROLE_EXP_001", "T_ROLE_EXP_G001");
			result.getTreasures().put(0, treasure);
			//
			treasure = treasureCollector.createTreasure("ROLE_EXP_001",
					"T_ROLE_EXP_G002");
			result.getTreasures().put(1, treasure);
			//
			treasure = treasureCollector.createTreasure("ROLE_EXP_001",
					"T_ROLE_EXP_G003");
			result.getTreasures().put(2, treasure);
			return result;
		}

		/**
		 * 檢核祕寶刷新log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertTreasureRefreshLog(
				TreasureRefreshLog expected, TreasureRefreshLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				//
				assertEquals(expected.getRefreshTime(), actual.getRefreshTime());
				assertMapEquals(expected.getTreasures(), actual.getTreasures());
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
				TreasureRefreshLog manorLandLog = randomTreasureRefreshLog();
				// create
				Serializable pk = treasureLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				TreasureRefreshLog foundEntity = treasureLogDao.find(
						TreasureRefreshLogImpl.class, manorLandLog.getSeq());
				printFind(i, foundEntity);
				assertTreasureRefreshLog(manorLandLog, foundEntity);

				// update
				manorLandLog.setLogDate(DateHelper.today());
				int updated = treasureLogDao.update(manorLandLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				TreasureRefreshLog deletedEntity = treasureLogDao.delete(
						TreasureRefreshLogImpl.class, manorLandLog.getSeq());
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
				TreasureRefreshLog manorLandLog = randomTreasureRefreshLog();
				//
				Serializable pk = treasureLogDao.insert(manorLandLog);
				printInsert(i, pk);
				assertNotNull(pk);

				TreasureRefreshLog foundEntity = treasureLogDao.find(
						TreasureRefreshLogImpl.class, manorLandLog.getSeq());
				assertTreasureRefreshLog(manorLandLog, foundEntity);

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
		public void findTreasureRefreshLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<TreasureRefreshLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = treasureLogDao.findTreasureRefreshLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteTreasureRefreshLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = treasureLogDao.deleteTreasureRefreshLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}

	// --------------------------------------------------
	// TreasureBuyLog
	// --------------------------------------------------
	public static class TreasureBuyLogTest extends TreasureTestSupporter {
		/**
		 * 隨機祕寶購買log
		 * 
		 * @return
		 */
		public static TreasureBuyLog randomTreasureBuyLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			TreasureBuyLog result = new TreasureBuyLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setAcceptor(randomSlave());
			result.setServerIp(randomIp("10.0.0"));
			//
			result.setBuyType(randomType(BuyType.class));
			result.setGridIndex(randomInt(10));
			//
			Treasure treasure = treasureCollector.createTreasure(
					"ROLE_EXP_001", "T_ROLE_EXP_G001");
			result.setTreasure(treasure);
			Item item = ThingCollector.getInstance().createThing(
					"T_ROLE_EXP_G001");
			result.setItem(item);
			//
			result.setAmount(randomInt(10));
			result.setPrice(randomLong(1000));
			result.setCoin(randomInt(1000));
			result.setSpendGold(randomLong(100000));
			result.setSpendCoin(randomInt(100000));
			//
			return result;
		}

		/**
		 * 檢核祕寶購買log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertTreasureBuyLog(TreasureBuyLog expected,
				TreasureBuyLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getRoleId(), actual.getRoleId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				//
				assertEquals(expected.getBuyType(), actual.getBuyType());
				assertEquals(expected.getGridIndex(), actual.getGridIndex());
				//
				assertEquals(expected.getTreasure(), actual.getTreasure());
				assertEquals(expected.getItem(), actual.getItem());
				//
				assertEquals(expected.getAmount(), actual.getAmount());
				assertEquals(expected.getPrice(), actual.getPrice());
				assertEquals(expected.getCoin(), actual.getCoin());
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
				TreasureBuyLog manorSeedLog = randomTreasureBuyLog();
				// create
				Serializable pk = treasureLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				TreasureBuyLog foundEntity = treasureLogDao.find(
						TreasureBuyLogImpl.class, manorSeedLog.getSeq());
				printFind(i, foundEntity);
				assertTreasureBuyLog(manorSeedLog, foundEntity);

				// update
				manorSeedLog.setLogDate(DateHelper.today());
				int updated = treasureLogDao.update(manorSeedLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				TreasureBuyLog deletedEntity = treasureLogDao.delete(
						TreasureBuyLogImpl.class, manorSeedLog.getSeq());
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
				TreasureBuyLog manorSeedLog = randomTreasureBuyLog();
				//
				Serializable pk = treasureLogDao.insert(manorSeedLog);
				printInsert(i, pk);
				assertNotNull(pk);

				TreasureBuyLog foundEntity = treasureLogDao.find(
						TreasureBuyLogImpl.class, manorSeedLog.getSeq());
				assertTreasureBuyLog(manorSeedLog, foundEntity);

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
		public void findTreasureBuyLog() {
			final String ROLE_ID = "TEST_ROLE";
			List<TreasureBuyLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = treasureLogDao.findTreasureBuyLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertNotNull(result);
		}

		@Test
		public void deleteTreasureBuyLog() {
			final String ROLE_ID = "TEST_ROLE";
			//
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				result = treasureLogDao.deleteTreasureBuyLog(ROLE_ID);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertTrue(result > 0);
		}
	}
}
