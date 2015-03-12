package org.openyu.mix.treasure.service.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.item.vo.Item;

import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.TreasureTestSupporter;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.thread.ThreadHelper;

public class TreasureLogServiceImplTest extends TreasureTestSupporter {
	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void recordRefresh() {
		Role role = mockRole();
		//
		Map<Integer, Treasure> treasures = treasureCollector.randomTreasures();
		treasureLogService.recordRefresh(role, System.currentTimeMillis(),
				treasures, null, 1000);
	}

	@Test
	public void recordBuy() {
		Role role = mockRole();
		// 祕寶
		Treasure treasure = treasureService.createTreasure("ROLE_EXP_001",
				"T_ROLE_EXP_G001");
		// 道具
		Item item = itemService.createItem("T_ROLE_EXP_G001", 1);
		treasureLogService.recordBuy(role, BuyType.COIN, 0, treasure, item,
				500, 0);
	}
}
