package org.openyu.mix.account.service.impl;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.account.AccountTestSupporter;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;

public class AccountLogServiceImplTest extends AccountTestSupporter {

	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(5 * 1000);
	}

	@Test
	public void recordIncreaseCoin() {
		Role role = mockRole();

		accountLogService.recordIncreaseCoin(role.getAccountId(), role, 5,
				CoinType.ITEM_ACCOUNT_COIN_THING);
		//
		accountLogService.recordIncreaseCoin(role.getAccountId(), role, 15,
				CoinType.ITEM_ACCOUNT_COIN_THING);
		//
//		ThreadHelper.sleep(3 * 1000);
//		int deleted = accountLogService.deleteAccountCoinLog(role
//				.getAccountId());
//		System.out.println("deleted: " + deleted);
	}

	@Test
	public void recordDecreaseCoin() {
		Role role = mockRole();

		accountLogService.recordDecreaseCoin(role.getAccountId(), role, 3,
				CoinType.SASANG_PLAY);
		//
		accountLogService.recordDecreaseCoin(role.getAccountId(), role, 6,
				CoinType.TREASURE_REFRESH);
	}

	@Test
	public void recordChangeCoin() {
		Role role = mockRole();

		accountLogService.recordChangeCoin(role.getAccountId(), role, 5,
				ActionType.INCREASE, CoinType.ITEM_ACCOUNT_COIN_THING);
		//
		accountLogService.recordChangeCoin(role.getAccountId(), role, 3,
				ActionType.DECREASE, CoinType.SASANG_PLAY);
	}
}
