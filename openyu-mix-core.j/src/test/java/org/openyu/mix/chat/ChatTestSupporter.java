package org.openyu.mix.chat;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.chat.dao.ChatDao;
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.chat.service.StoreChatService;
import org.openyu.mix.chat.socklet.ChatSocklet;
import org.openyu.mix.chat.service.DebugService;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.impl.ChannelImpl;
import org.openyu.mix.chat.vo.impl.ChatImpl;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.vo.Role;

public class ChatTestSupporter extends AppTestSupporter {

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	protected static DebugService debugService;

	protected static ChatDao chatDao;

	/**
	 * 聊天服務
	 */
	protected static ChatService chatService;

	/**
	 * 儲存聊天角色服務
	 * 
	 * 20140930
	 */
	protected static StoreChatService storeChatService;

	protected static ChatSocklet chatSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-scheduler.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/chat/applicationContext-chat.xml"//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext
				.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		debugService = (DebugService) applicationContext
				.getBean("debugService");
		//
		chatDao = (ChatDao) applicationContext.getBean("chatDao");
		chatService = (ChatService) applicationContext.getBean("chatService");
		storeChatService = (StoreChatService) applicationContext
				.getBean("storeChatService");
		chatSocklet = (ChatSocklet) applicationContext.getBean("chatSocklet");
	}

	public static ChatService getChatService() {
		return chatService;
	}

	public static void setChatService(ChatService chatService) {
		ChatTestSupporter.chatService = chatService;
	}

	public static class BeanTest extends ChatTestSupporter {

		@Test
		public void debugService() {
			System.out.println(debugService);
			assertNotNull(debugService);
		}

		@Test
		public void chatDao() {
			System.out.println(chatDao);
			assertNotNull(chatDao);
		}

		@Test
		public void chatService() {
			System.out.println(chatService);
			assertNotNull(chatService);
		}

		@Test
		public void storeChatService() {
			System.out.println(storeChatService);
			assertNotNull(storeChatService);
		}

		@Test
		public void chatSocklet() {
			System.out.println(chatSocklet);
			assertNotNull(chatSocklet);
		}
	}

	/**
	 * 模擬聊天角色
	 * 
	 * @return
	 */
	public static Chat mockChat() {
		Role role = mockRole();
		return mockChat(role);
	}

	/**
	 * 模擬聊天角色2
	 * 
	 * @return
	 */
	public static Chat mockChat2() {
		Role role = mockRole2();
		return mockChat(role);
	}

	/**
	 * 模擬聊天角色3
	 * 
	 * @return
	 */
	public static Chat mockChat3() {
		Role role = mockRole3();
		return mockChat(role);
	}

	/**
	 * 模擬聊天角色
	 * 
	 * @param role
	 * @return
	 */
	public static Chat mockChat(Role role) {

		// 可暫時先用此方式建構
		// Chat result = new ChatImpl();
		// result.setId(role.getId());
		// result.setOpenAdd(true);
		// //result.setChannels(channels);
		// result.setRole(role);

		Chat result = chatService.createChat(role.getId());

		// 加到mem
		chatSetService.addChat(result);
		return result;
	}

	/**
	 * 隨機聊天角色
	 * 
	 * @return
	 */
	public static Chat randomChat() {
		final String UNIQUE = randomUnique();
		//
		final String ROLE_ID = "TEST_ROLE" + UNIQUE;
		//
		Chat result = new ChatImpl();
		result.setId(ROLE_ID);
		result.setOpenFriend(randomBoolean());
		//
		for (ChannelType id : ChannelType.values()) {
			Channel channel = new ChannelImpl(id);
			channel.setOpened(randomBoolean());
			//
			result.addChannel(channel);
		}

		return result;
	}

}
