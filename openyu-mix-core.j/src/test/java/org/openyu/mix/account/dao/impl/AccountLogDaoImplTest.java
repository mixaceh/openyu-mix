package org.openyu.mix.account.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.account.AccountTestSupporter;
import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.account.log.impl.AccountCoinLogImpl;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.commons.util.DateHelper;

public class AccountLogDaoImplTest extends AccountTestSupporter {
	// --------------------------------------------------
	// AccountCoinLog
	// --------------------------------------------------
	public static class AccountCoinLogTest extends AccountTestSupporter {

		/**
		 * 隨機帳戶儲值增減log
		 * 
		 * @return
		 */
		public static AccountCoinLog randomAccountCoinLog() {
			final String ACCOUNT_ID = "TEST_ACCOUNT" + randomUnique();
			final String ROLE_ID = "TEST_ROLE" + randomUnique();
			final String ROLE_NAME = "測試角色";
			//
			AccountCoinLog result = new AccountCoinLogImpl();
			result.setLogDate(DateHelper.today());
			result.setAccountId(ACCOUNT_ID);
			result.setRoleId(ROLE_ID);
			result.setRoleName(ROLE_NAME);
			result.setServerIp(randomIp("192.168.0"));
			result.setAcceptor(randomString());
			//
			result.setActionType(randomType(ActionType.class));
			result.setCoinType(randomType(CoinType.class));
			result.setCoin(randomInt(10));
			return result;
		}

		/**
		 * 檢核帳戶儲值增減log
		 * 
		 * @param expected
		 * @param actual
		 */
		public static void assertAccountCoinLog(AccountCoinLog expected,
				AccountCoinLog actual) {
			if (expected == null) {
				assertNull(actual);
			} else {
				// TODO assert date 值不對
				// assertEquals(expected.getLogDate(), actual.getLogDate());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getAccountId(), actual.getAccountId());
				assertEquals(expected.getAcceptor(), actual.getAcceptor());
				assertEquals(expected.getActionType(), actual.getActionType());
				assertEquals(expected.getCoinType(), actual.getCoinType());
				assertEquals(expected.getCoin(), actual.getCoin());
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
				AccountCoinLog accountCoinLog = randomAccountCoinLog();
				// create
				Serializable pk = accountLogDao.insert(accountCoinLog);
				printInsert(i, pk);
				assertNotNull(pk);

				// retrieve
				AccountCoinLog foundEntity = accountLogDao.find(
						AccountCoinLogImpl.class, accountCoinLog.getSeq());
				printFind(i, foundEntity);
				assertAccountCoinLog(accountCoinLog, foundEntity);

				// update
				accountCoinLog.setLogDate(DateHelper.today());
				int updated = accountLogDao.update(accountCoinLog);
				printUpdate(i, updated);
				assertTrue(updated > 0);

				// delete
				AccountCoinLog deletedEntity = accountLogDao.delete(
						AccountCoinLogImpl.class, accountCoinLog.getSeq());
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
				AccountCoinLog accountCoinLog = randomAccountCoinLog();
				//
				Serializable pk = accountLogDao.insert(accountCoinLog);
				printInsert(i, pk);
				assertNotNull(pk);

				AccountCoinLog foundEntity = accountLogDao.find(
						AccountCoinLogImpl.class, accountCoinLog.getSeq());
				assertAccountCoinLog(accountCoinLog, foundEntity);

				System.out.println(accountCoinLog);
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");
		}

		@Test
		// 1 times: 272 mills.
		// 1 times: 276 mills.
		// 1 times: 276 mills.
		// verified: ok
		public void findAccountCoinLog() {
			List<AccountCoinLog> result = null;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				AccountCoinLog accountCoinLog = randomAccountCoinLog();
				accountLogDao.insert(accountCoinLog);
				//
				AccountCoinLog accountCoinLog2 = randomAccountCoinLog();
				accountCoinLog2.setAccountId(accountCoinLog.getAccountId());
				accountLogDao.insert(accountCoinLog2);
				//
				AccountCoinLog accountCoinLog3 = randomAccountCoinLog();
				accountCoinLog3.setAccountId(accountCoinLog.getAccountId());
				accountLogDao.insert(accountCoinLog3);
				//
				result = accountLogDao.findAccountCoinLog(accountCoinLog
						.getAccountId());
				//
				for (AccountCoinLog entity : result) {
					accountLogDao.delete(entity);
				}
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result.size() + ", " + result);
			assertEquals(3, result.size());
		}

		@Test
		public void deleteAccountCoinLog() {
			int result = 0;
			int count = 1;

			long beg = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				AccountCoinLog accountCoinLog = randomAccountCoinLog();
				accountLogDao.insert(accountCoinLog);
				accountLogDao.insert(accountCoinLog);
				accountLogDao.insert(accountCoinLog);
				//
				result = accountLogDao.deleteAccountCoinLog(accountCoinLog
						.getAccountId());
			}
			long end = System.currentTimeMillis();
			System.out.println(count + " times: " + (end - beg) + " mills. ");

			System.out.println(result);
			assertEquals(3, result);
		}
	}
}
