package org.openyu.mix.item.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import org.openyu.mix.item.ItemTestSupporter;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.impl.ItemServiceImpl.IncreaseResultImpl;
import org.openyu.mix.item.service.impl.ItemServiceImpl.DecreaseResultImpl;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ThingCollector;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponCollector;
import org.openyu.mix.item.vo.thing.EnhanceThing;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.BagPenImplTest;
import org.openyu.commons.thread.ThreadHelper;

public class ItemLogServiceImplTest extends ItemTestSupporter {

	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(5 * 1000);
	}

	@Test
	public void recordIncreaseItem() {
		Role role = mockRole();
		Item item = mockItem();
		//
		List<IncreaseItemResult> results = new LinkedList<IncreaseItemResult>();
		IncreaseItemResult result = new IncreaseResultImpl();
		result.setItem(item);
		results.add(result);
		//
		itemLogService.recordIncreaseItem(role, ActionType.BAG, results);
	}

	@Test
	public void recordDecreaseItem() {
		Role role = mockRole();
		Item item = mockItem();
		//
		List<DecreaseItemResult> results = new LinkedList<DecreaseItemResult>();
		DecreaseItemResult result = new DecreaseResultImpl();
		result.setItem(item);
		results.add(result);
		//
		itemLogService.recordDecreaseItem(role, ActionType.BAG, results);
	}

	@Test
	public void recordDecreaseItem2() {
		Role role = mockRole();
		BagPen bagPen = BagPenImplTest.mockBagPen();
		Item item = mockItem();
		//
		DecreaseItemResult result = new DecreaseResultImpl();
		//
		itemLogService.recordDecreaseItem(role, ActionType.BAG, result);
	}

	@Test
	public void recordChangeEnhance() {
		Role role = mockRole();
		// 強化前的道具
		Weapon beforeItem = WeaponCollector.getInstance().createWeapon(
				"W_MARS_SWORD_E001");
		beforeItem.setAmount(1);
		beforeItem.setEnhanceLevel(EnhanceLevel._0);

		// 強化後的道具
		Weapon afterItem = beforeItem.clone(beforeItem);
		afterItem.setEnhanceLevel(EnhanceLevel._1);// +1
		//
		EnhanceThing enhanceThing = (EnhanceThing) ThingCollector.getInstance()
				.createThing("T_ENHANCE_WEAPON_E_G001");
		enhanceThing.setAmount(1);
		//
		itemLogService.recordChangeEnhance(role, ActionType.BAG, beforeItem,
				afterItem, enhanceThing);

		// 再強化一次
		beforeItem = afterItem.clone(afterItem);
		afterItem.setEnhanceLevel(EnhanceLevel._7);// +7
		itemLogService.recordChangeEnhance(role, ActionType.BAG, beforeItem,
				afterItem, enhanceThing);
	}
}
