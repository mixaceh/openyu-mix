package org.openyu.mix.role.service.impl;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;

public class RoleLogServiceImplTest extends RoleTestSupporter {

	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(5 * 1000);
	}

	@Test
	public void recordChangeLevel() {
		Role role = mockRole();
		role.setLevel(2);

		roleLogService.recordChangeLevel(role, 1, 1);
	}

	@Test
	public void recordIncreaseGold() {
		Role role = mockRole();
		role.setGold(200L);

		roleLogService.recordIncreaseGold(role, 100L, 100L, GoldType.SHOP_SELL);
	}

	@Test
	public void recordDecreaseGold() {
		Role role = mockRole();
		role.setGold(0L);

		roleLogService.recordDecreaseGold(role, 100L, 100L, GoldType.SHOP_BUY);
	}

	@Test
	public void recordChangeGold() {
		Role role = mockRole();
		role.setGold(200L);

		// 加100
		roleLogService.recordChangeGold(role, 100L, 100L, ActionType.INCREASE,
				GoldType.SHOP_SELL);

		// 扣100
		role.setGold(100L);
		roleLogService.recordChangeGold(role, 100L, 200L, ActionType.DECREASE,
				GoldType.SASANG_PLAY);
	}

	@Test
	public void recordChangeFame() {
		Role role = mockRole();
		role.setFame(10);

		roleLogService.recordChangeFame(role, 20, 10);
	}
}
