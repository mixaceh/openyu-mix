package org.openyu.mix.chat.po.userType;

import static org.junit.Assert.*;

import java.util.Locale;
import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.vo.Friend;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.impl.FriendGroupImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;

public class FriendGroupUserTypeTest extends ChatTestSupporter {

	private static FriendGroupUserType userType = new FriendGroupUserType();

	@Test
	// 1000000 times: 924 mills.
	// 1000000 times: 1059 mills.
	// 1000000 times: 1075 mills.
	// verified
	public void marshal() {
		FriendGroup value = new FriendGroupImpl();
		Role role = mockRole();
		role.setLevel(10);
		role.setPower(100);
		role.setVipType(VipType._1);
		role.setName(Locale.US, "Test Role");
		value.addFriend(mockChat(), role);
		//
		Role role2 = mockRole2();
		role2.setLevel(15);
		role2.setPower(500);
		role2.setVipType(VipType._2);
		value.addFriend(mockChat2(), role2);
		//
		Role role3 = mockRole3();
		role3.setLevel(20);
		role3.setPower(1000);
		role3.setVipType(VipType._3);
		value.addFriend(mockChat3(), role3);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(
				"♥1♠3♠TEST_ROLE♠2♦zh_TW♣測試角色♦en_US♣Test Role♠2♠HUMAN_WARRIOR_1♠10♠100♠1♠TEST_ROLE_2♠1♦zh_TW♣測試角色_2♠2♠HUMAN_WARRIOR_1♠15♠500♠2♠TEST_ROLE_3♠1♦zh_TW♣測試角色_3♠2♠HUMAN_WARRIOR_1♠20♠1000♠3",
				result);
	}

	@Test
	// 1000000 times: 5646 mills.
	// 1000000 times: 5154 mills.
	// 1000000 times: 6044 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠3♠TEST_ROLE♠2♦zh_TW♣測試角色♦en_US♣Test Role♠2♠HUMAN_WARRIOR_1♠10♠100♠1♠TEST_ROLE_2♠1♦zh_TW♣測試角色_2♠2♠HUMAN_WARRIOR_1♠15♠500♠2♠TEST_ROLE_3♠1♦zh_TW♣測試角色_3♠2♠HUMAN_WARRIOR_1♠20♠1000♠3";
		//
		FriendGroup result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(3, result.getFriends().size());
		//
		Friend friend = result.getFriend("TEST_ROLE");
		assertNotNull(friend);
		//
		assertEquals("測試角色", friend.getName());
		assertEquals("Test Role", friend.getName(Locale.US));
	}
}
