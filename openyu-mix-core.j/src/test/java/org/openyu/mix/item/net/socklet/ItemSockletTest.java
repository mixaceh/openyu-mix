package org.openyu.mix.item.net.socklet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.item.ItemTestSupporter;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.message.vo.Message;

public class ItemSockletTest extends ItemTestSupporter {

	@After
	public void tearDown() throws Exception {
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void ITEM_DECREASE_ITEM_REQUEST() {
		Role role = mockRole();
		
		// 10個e武捲放包包
		Item weaponScroll = itemService.createItem("T_ENHANCE_WEAPON_E_G001",
				10);
		itemService.increaseItem(false, role, weaponScroll);
		//
		Message message = messageService.createClient(role.getId(),
				CoreMessageType.ITEM_DECREASE_ITEM_REQUEST);
		message.addString(role.getId());
		message.addString(weaponScroll.getUniqueId());
		message.addInt(1);
		//
		itemSocklet.service(message);
	}

	@Test
	public void ITEM_USE_ITEM_REQUEST() {
		Role role = mockRole();
		
		// 10個e武捲放包包
		Item weaponScroll = itemService.createItem("T_ENHANCE_WEAPON_E_G001",
				10);
		itemService.increaseItem(false, role, weaponScroll);
		
		// 1件武器放包包
		Equipment weapon = itemService.createEquipment("W_MARS_SWORD_E001");
		itemService.increaseItem(false, role, weapon);
		//
		Message message = messageService.createClient(role.getId(),
				CoreMessageType.ITEM_USE_ITEM_REQUEST);

		message.addString(role.getId());
		message.addString(weapon.getUniqueId());
		message.addString(weaponScroll.getUniqueId());
		message.addInt(1);
		//
		itemSocklet.service(message);
	}

	// --------------------------------------------------
	// 啟動slave1,模擬真正連線
	// --------------------------------------------------
	public static class ConnectorTest extends ItemTestSupporter {
		@Before
		public void setUp() throws Exception {
			final String ROLE_ID = "TEST_ROLE";

			// 連線到slave1, localhost:4110
			javaConnector.setId(ROLE_ID);
			javaConnector.setIp("localhost");
			javaConnector.setPort(4110);
			javaConnector.start();
		}

		@After
		public void tearDown() throws Exception {
			ThreadHelper.sleep(5 * 1000);
		}

		@Test
		public void ITEM_DECREASE_REQUEST() {
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
					CoreMessageType.ITEM_DECREASE_ITEM_REQUEST);

			message.addString(ROLE_ID);
			message.addString("T_1862242nU5h72VqW");
			message.addInt(1);
			//
			javaConnector.send(message);
		}

		@Test
		// +5
		// ♥1♠4♠0♠7♠0♠T_POTION_HP_G001♦T_hp♦50♦0♠1♠T_ENHANCE_WEAPON_E_G001♦T_e♦95♦0♠2♠W_MARS_SWORD_E001♦W_0♦1♦0♦5♦5♦21♣50♣30♣165♦22♣5000♣30♣165♦23♣5000♣30♣165♦24♣8000♣30♣165♦25♣50♣30♣165♠3♠W_MARS_SWORD_E001♦W_1♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠4♠W_MARS_SWORD_E001♦W_2♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠5♠W_MARS_SWORD_E001♦W_3♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠6♠W_MARS_SWORD_E001♦W_4♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠1♠0♠2♠0♠10♠0
		//
		// +6爆掉了
		// ♥1♠4♠0♠6♠0♠T_POTION_HP_G001♦T_hp♦50♦0♠1♠T_ENHANCE_WEAPON_E_G001♦T_e♦94♦0♠3♠W_MARS_SWORD_E001♦W_1♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠4♠W_MARS_SWORD_E001♦W_2♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠5♠W_MARS_SWORD_E001♦W_3♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠6♠W_MARS_SWORD_E001♦W_4♦1♦0♦0♦5♦21♣50♣0♣0♦22♣5000♣0♣0♦23♣5000♣0♣0♦24♣8000♣0♣0♦25♣50♣0♣0♠1♠0♠2♠0♠10♠0
		public void ITEM_USE_REQUEST() {
			final String ROLE_ID = "TEST_ROLE";
			//
			Message message = messageService.createClient(ROLE_ID,
					CoreMessageType.ITEM_USE_ITEM_REQUEST);

			message.addString(ROLE_ID);
			message.addString("W_0");
			message.addString("T_e");
			message.addInt(1);
			//
			javaConnector.send(message);
		}

	}
}
