package org.openyu.mix.chat.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.chat.service.StoreChatService;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.service.socklet.SasangSocklet;
import org.openyu.socklet.message.vo.Message;

public class ChatSocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = -139555024104115585L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(SasangSocklet.class);

	@Autowired
	@Qualifier("chatService")
	protected transient ChatService chatService;

	@Autowired
	@Qualifier("storeChatService")
	protected transient StoreChatService storeChatService;

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		// 聊天請求
		case CHAT_SPEAK_REQUEST: {
			String roleId = message.getString(0);
			int channelTypeValue = message.getInt(1);
			String text = message.getString(2);
			String html = message.getString(3);
			//
			Role role = checkRole(roleId);
			chatService.speak(role, channelTypeValue, text, html);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		case CHAT_DEBUG_STORE_REQUEST: {
			String chatId = message.getString(0);
			//
			Chat chat = checkChat(chatId);
			DEBUG_storeChat(chat);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	/**
	 * 秘技,儲存聊天角色
	 * 
	 * @param chatId
	 */
	protected void DEBUG_storeChat(Chat chat) {
		storeChatService.storeChat(true, chat);
	}
}
