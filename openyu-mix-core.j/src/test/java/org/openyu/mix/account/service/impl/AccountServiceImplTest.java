package org.openyu.mix.account.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import javax.crypto.SecretKey;

import org.junit.Test;
import org.openyu.mix.account.AccountTestSupporter;
import org.openyu.mix.account.po.impl.AccountPoImpl;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.account.service.AccountService.ErrorType;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.EncodingHelper;
import org.openyu.commons.lang.SystemHelper;
import org.openyu.commons.security.SecurityHelper;
import org.openyu.socklet.message.vo.Message;

public class AccountServiceImplTest extends AccountTestSupporter {

	/**
	 * 隨機產生帳號資料
	 * 
	 * @return
	 */
	public static Account randomAccount() {
		final String UNIQUE = randomUnique();
		final String ID = "TEST_ACCOUNT" + UNIQUE;
		final String ZH_TW_NAME = "測試帳號" + UNIQUE;
		final String EN_US_NAME = "Test Account" + UNIQUE;
		//
		Account result = new AccountImpl();
		result.setId(ID);
		result.setValid(randomBoolean());
		result.addName(Locale.TRADITIONAL_CHINESE, ZH_TW_NAME);
		result.addName(Locale.US, EN_US_NAME);
		//
		result.setCoin(randomInt());
		result.setAccuCoin(randomInt());
		result.setPassword(randomString());
		//
		return result;
	}

	/**
	 * 檢核帳號資料
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertAccount(Account expected, Account actual) {
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
	public void method() {
		Method[] methods = AccountServiceImpl.class.getMethods();
		SystemHelper.println(methods);
		//
		methods = AccountServiceImpl.class.getDeclaredMethods();
		SystemHelper.println(methods);
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
			Account account = randomAccount();
			// create
			Serializable pk = accountService.insert(account);
			printInsert(i, pk);
			assertNotNull(pk);

			// retrieve
			Account foundEntity = accountService.find(AccountImpl.class,
					account.getSeq());
			printFind(i, foundEntity);
			assertAccount(account, foundEntity);

			// update
			account.setValid(true);
			int updated = accountService.update(account);
			printUpdate(i, updated);
			assertTrue(updated > 0);

			// update2
			account.setCoin(100);
			updated = accountService.update(account);
			printUpdate(i, updated);
			assertTrue(updated > 0);

			// delete
			Account deletedEntity = accountService.delete(AccountImpl.class,
					account.getSeq());
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
			Account account = randomAccount();
			//
			Serializable pk = accountService.insert(account);
			printInsert(i, pk);
			assertNotNull(pk);

			Account foundEntity = accountService.find(AccountPoImpl.class,
					account.getSeq());
			assertAccount(account, foundEntity);

			System.out.println(account);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		Account result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.findAccount(ACCOUNT_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
		//
		result = accountService.findAccount(ACCOUNT_ID);
		System.out.println(result);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findAccountByValid() {
		List<Account> result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.findAccount(true);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.size() + ", " + result);
		assertNotNull(result);
		//
		result = accountService.findAccount(false);
		System.out.println(result.size() + ", " + result);
	}

	@Test
	public void findCoin() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		int result = 0;

		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.findCoin(ACCOUNT_ID);
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
			result = accountService.findAccuCoin(ACCOUNT_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void checkIncreaseCoin() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		//
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.checkIncreaseCoin(role.getAccountId(), 50);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = accountService
				.checkIncreaseCoin(role.getAccountId(), -1 * 100);
		System.out.println(result);
		assertFalse(result);

		// 溢位了
		result = accountService.checkIncreaseCoin(role.getAccountId(),
				Integer.MAX_VALUE);
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	public void increaseCoin() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		//
		int result = 0;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.increaseCoin(true, role.getAccountId(),
					role, 100, true, CoinType.ITEM_ACCOUNT_COIN_THING);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println(result);
		assertEquals(100, result);

		// coin=0
		result = accountService.increaseCoin(true, role.getAccountId(), role,
				0, true, CoinType.ITEM_ACCOUNT_COIN_THING);
		System.out.println(result);
		assertEquals(0, result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void checkDecreaseCoin() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		//
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.checkDecreaseCoin(role.getAccountId(), 10);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result);
		//
		result = accountService.checkDecreaseCoin(role.getAccountId(), 100);
		System.out.println(result);
		assertTrue(result);
	}

	@Test
	public void decreaseCoin() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		//
		int result = 0;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.decreaseCoin(true, role.getAccountId(),
					role, 10, CoinType.SASANG_PLAY);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(-10, result);

		// coin=0
		result = accountService.decreaseCoin(true, role.getAccountId(), role,
				0, CoinType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(0, result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void changeCoin() {
		Role role = mockRole();// accountId=TEST_ACCOUNT,roleId=TEST_ROLE
		//
		int result = 0;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.changeCoin(true, role.getAccountId(), role,
					2, true, ActionType.INCREASE,
					CoinType.ITEM_ACCOUNT_COIN_THING);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(2, result);

		//
		result = accountService.changeCoin(true, role.getAccountId(), role,
				(-1) * 1, true, ActionType.DECREASE, CoinType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(-1, result);

		// coin=0
		result = accountService.changeCoin(true, role.getAccountId(), role, 0,
				true, ActionType.INCREASE, CoinType.ITEM_ACCOUNT_COIN_THING);
		System.out.println(result);
		assertEquals(0, result);

		// coin=0
		result = accountService.changeCoin(true, role.getAccountId(), role, 0,
				true, ActionType.DECREASE, CoinType.SASANG_PLAY);
		System.out.println(result);
		assertEquals(0, result);
		//
		// ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void sendCoin() {
		Role role = mockRole();
		//
		Message result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = accountService.sendCoin(role, 1000, 50);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		assertNotNull(result);
		assertEquals(1000, result.getInt(0));
		assertEquals(50, result.getInt(1));
	}

	@Test
	public void sendAccuCoin() {
		Role role = mockRole();
		//
		Message result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = accountService.sendAccuCoin(role, 5000);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		assertNotNull(result);
		assertEquals(5000, result.getInt(0));
	}

	@Test
	public void createAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		final String NAME = "測試帳號";
		Account result = null;
		//
		int count = 1; //
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			String unique = randomUnique();
			result = accountService.createAccount(ACCOUNT_ID + unique, NAME);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

	@Test
	public void authorize() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String ASSIGN_KEY = "FarFarAway";
		final String ALGORITHM = "HmacMD5";
		// b5f01d3a0898d8016b5633edfe6106b0
		SecretKey secretKey = SecurityHelper.createSecretKey(ASSIGN_KEY,
				ALGORITHM);
		byte[] buff = SecurityHelper.mac("1111", secretKey, ALGORITHM);
		final String PASSWORD = EncodingHelper.encodeHex(buff);
		System.out.println(PASSWORD);
		//
		accountService.authorize(ACCOUNT_ID, PASSWORD);
	}

	@Test
	public void checkAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String ASSIGN_KEY = "FarFarAway";
		final String ALGORITHM = "HmacMD5";
		// b5f01d3a0898d8016b5633edfe6106b0
		SecretKey secretKey = SecurityHelper.createSecretKey(ASSIGN_KEY,
				ALGORITHM);
		byte[] buff = SecurityHelper.mac("1111", secretKey, ALGORITHM);
		final String PASSWORD = EncodingHelper.encodeHex(buff);
		System.out.println(PASSWORD);
		//
		String authKey = accountService.checkAccount(ACCOUNT_ID, PASSWORD);
		System.out.println(authKey);//
	}

	@Test
	public void authorizeFromLogin() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String AUTH_KEY = "b91150d8b5608535e969b6c9a61fbb5c";
		//
		accountService.authorizeFromLogin(ACCOUNT_ID, AUTH_KEY);
	}

	@Test
	public void sendAuthorize() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_1";
		final String AUTH_KEY = "b91150d8b5608535e969b6c9a61fbb5c";
		//
		Message result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = accountService.sendAuthorize(ErrorType.NO_ERROR,
					ACCOUNT_ID, AUTH_KEY);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		assertNotNull(result);
		assertEquals(ErrorType.NO_ERROR.getValue(), result.getInt(0));
		assertEquals(ACCOUNT_ID, result.getString(1));
		assertEquals(AUTH_KEY, result.getString(2));
	}

}
