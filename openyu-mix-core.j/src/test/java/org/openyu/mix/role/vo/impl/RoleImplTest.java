package org.openyu.mix.role.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.mix.role.vo.Role;

public class RoleImplTest extends BaseTestSupporter {
	@Test
	public void writeToXml() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		final String ROLE_ID = "TEST_ROLE";
		Role role = new RoleImpl();
		role.setId(ROLE_ID);
		role.setName("測試角色");
		// role.setRaceType(RaceType.HUMAN);
		// role.setCareerType(CareerType.WARRIOR_1);
		role.setExp(100L);
		role.setLevel(10);
		role.setGold(50000L);
		//
		role.setEnterTime(System.currentTimeMillis());
		role.setLeaveTime(System.currentTimeMillis());
		//
		Account account = new AccountImpl();
		account.setId(ACCOUNT_ID);
		account.setCoin(123);
		account.setAccuCoin(999);
		role.setAccountId(account.getId());
		//
		String result = CollectorHelper.writeToXml(RoleImpl.class, role);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Role result = CollectorHelper.readFromXml(RoleImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
