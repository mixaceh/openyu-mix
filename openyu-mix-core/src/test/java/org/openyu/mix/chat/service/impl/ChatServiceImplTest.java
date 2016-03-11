package org.openyu.mix.chat.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.service.ChatService.ErrorType;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.impl.ChatImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.thread.ThreadHelper;

public class ChatServiceImplTest extends ChatTestSupporter {

	@Test
	public void connect() {
		Role role = mockRole();
		Chat chat = mockChat();
		//
		chatService.roleConnect(role.getId(), chat);
	}

	@Test
	public void createChat() {
		Role role = mockRole();
		Chat result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = chatService.createChat(role.getId());
		}

		// 加到mem
		chatSetService.addChat(result);
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.getId());
		System.out.println(chatService.getBeans().size() + ", "
				+ chatService.getBeans());
	}

	@Test
	public void speak() {
		Role role = mockRole();
		//
		chatService.speak(role, ChannelType.WORLD.getValue(), "Hello world",
				"<hr/>");
	}

	@Test
	public void sendSpeak() {
		Role role = mockRole();
		//
		List<String> receivers = roleRepository.getRoleIds();
		chatService.sendSpeak(ErrorType.NO_ERROR, receivers, ChannelType.WORLD,
				role, role.getName(), "Hello world", "<hr/>");
	}
}
