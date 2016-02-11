package org.openyu.mix.chat.vo.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.FriendGroup.ErrorType;
import org.openyu.mix.chat.vo.FriendGroup.MaxType;
import org.openyu.mix.role.vo.Role;

public class FriendGroupImplTest extends ChatTestSupporter
{

	public static FriendGroup mockFriendGroup()
	{
		FriendGroup result = new FriendGroupImpl(MaxType.FRIEND);//50個
		return result;
	}

	@Test
	public void addFriend()
	{
		Chat chat = mockChat();
		Role role = mockRole();
		FriendGroup friendGroup = mockFriendGroup();
		//
		ErrorType result = ErrorType.NO_ERROR;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = friendGroup.addFriend(chat, role);
			chat.setFriendGroup(friendGroup);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(ErrorType.NO_ERROR, result);

		//聊天角色不存在
		result = friendGroup.addFriend(null, role);
		System.out.println(result);
		assertEquals(ErrorType.CHAT_NOT_EXIST, result);

		//角色不存在
		result = friendGroup.addFriend(chat, null);
		System.out.println(result);
		assertEquals(ErrorType.ROLE_NOT_EXIST, result);

		//對方未開啟加入好友
		chat.setOpenFriend(false);
		result = friendGroup.addFriend(chat, role);
		chat.setOpenFriend(true);
		System.out.println(result);
		assertEquals(ErrorType.NOT_OPEN_ADD, result);

		//已加為好友
		result = friendGroup.addFriend(chat, role);
		System.out.println(result);
		assertEquals(ErrorType.ALREADY_HAS_FRIEND, result);

		//超過最大數量
		Role role2 = mockRole2();
		Chat chat2 = mockChat2();
		friendGroup.setMaxType(MaxType.ONE);
		result = friendGroup.addFriend(chat2, role2);
		friendGroup.setMaxType(MaxType.FRIEND);
		System.out.println(result);
		assertEquals(ErrorType.OVER_MAX_SIZE, result);

		//循環增加
		friendGroup.setMaxType(MaxType.ONE);
		result = friendGroup.addFriend(chat2, role2, true);
		friendGroup.setMaxType(MaxType.FRIEND);
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result);
	}
}
