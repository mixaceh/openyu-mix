package org.openyu.mix.account.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openyu.mix.account.AccountTestSupporter;
import org.openyu.mix.account.po.AccountPo;
import org.openyu.mix.account.po.impl.AccountPoImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class AccountDaoImplTest extends AccountTestSupporter {

	/**
	 * 隨機產生帳號資料
	 * 
	 * @return
	 */
	public static AccountPo randomAccountPo() {
		final String UNIQUE = randomUnique();
		final String ID = "TEST_ACCOUNT" + UNIQUE;
		final String ZH_TW_NAME = "測試帳號" + UNIQUE;
		final String EN_US_NAME = "Test Account" + UNIQUE;
		//
		AccountPo result = new AccountPoImpl();
		result.setId(ID);
		result.setValid(randomBoolean());
		result.addName(Locale.TRADITIONAL_CHINESE, ZH_TW_NAME);
		result.addName(Locale.US, EN_US_NAME);
		//
		result.setCoin(randomInt());
		result.setAccuCoin(randomInt());
		//
		return result;
	}

	/**
	 * 檢核帳號資料
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertAccountPo(AccountPo expected, AccountPo actual) {
		assertNotNull(expected);
		assertNotNull(actual);
		//
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getValid(), actual.getValid());
		assertCollectionEquals(expected.getNames(), actual.getNames());
		//
		assertEquals(expected.getCoin(), actual.getCoin());
		assertEquals(expected.getAccuCoin(), actual.getAccuCoin());
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
			AccountPo accountPo = randomAccountPo();
			// create
			Serializable pk = accountDao.insert(accountPo);
			printInsert(i, pk);
			assertNotNull(pk);

			// retrieve
			AccountPo foundEntity = accountDao.find(AccountPoImpl.class, accountPo.getSeq());
			printFind(i, foundEntity);
			assertAccountPo(accountPo, foundEntity);

			// update
			accountPo.setValid(true);
			int updated = accountDao.update(accountPo);
			printUpdate(i, updated);
			assertTrue(updated > 0);

			// delete
			AccountPo deletedEntity = accountDao.delete(AccountPoImpl.class, accountPo.getSeq());
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
		AccountPo accountPo = randomAccountPo();
		//
		Serializable pk = accountDao.insert(accountPo);
		printInsert(pk);
		assertNotNull(pk);

		AccountPo foundEntity = accountDao.find(AccountPoImpl.class, accountPo.getSeq());
		assertAccountPo(accountPo, foundEntity);

		System.out.println(accountPo);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		AccountPo result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountDao.findAccount(Locale.TRADITIONAL_CHINESE, ACCOUNT_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = accountDao.findAccount(ACCOUNT_ID);
		System.out.println(result);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findAccountByValid() {
		List<AccountPo> result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountDao.findAccount(Locale.TRADITIONAL_CHINESE, true);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.size() + ", " + result);
		assertNotNull(result);
		//
		result = accountDao.findAccount(false);
		System.out.println(result.size() + ", " + result);
	}

	@Test
	public void reindex() {
		accountDao.reindex(AccountPoImpl.class);
	}

	@Test
	public void findCoin() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		int result = 0;

		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountDao.findCoin(ACCOUNT_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void findAccuCoin() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		int result = 0;

		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountDao.findAccuCoin(ACCOUNT_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void findAccountByIdPassword() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		// final String PASSWORD = SecurityHelper.encryptPassword("1111");
		final String PASSWORD = null;
		System.out.println(PASSWORD);
		//
		AccountPo result = null;

		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountDao.findAccount(ACCOUNT_ID, PASSWORD);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}
}
