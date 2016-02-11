package org.openyu.mix.chat.service;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.core.vo.StoreType;
import org.openyu.mix.chat.service.ChatService.ErrorType;
import org.openyu.socklet.message.vo.Message;

/**
 * 儲存聊天角色服務
 * 
 * 1.儲存到db
 * 
 * 2.若失敗,則序列化到檔案, 目錄, file:custom/chat/unsave
 */
public interface StoreChatService extends AppService {

	/**
	 * 儲存所有聊天角色
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @return
	 */
	int storeChats(boolean sendable);

	/**
	 * 儲存聊天角色
	 * 
	 * 1.儲存到db, 失敗會重試
	 * 
	 * 2.若重試也失敗, 則序列化到檔案, 目錄, file:custom/chat/unsave
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param chat
	 * @return
	 */
	boolean storeChat(boolean sendable, Chat chat);

	/**
	 * 發送儲存聊天角色
	 *
	 * @param errorType
	 * @param chat
	 * @param storeType
	 * @return
	 */
	Message sendStoreChat(ErrorType errorType, Chat chat, StoreType storeType);

	/**
	 * 發送儲存聊天角色
	 *
	 * @param errorType
	 * @param chat
	 * @param storeType
	 * @param tries
	 *            重試次數
	 * @return
	 */
	Message sendStoreChat(ErrorType errorType, Chat chat, StoreType storeType,
			int tries);

	/**
	 * 序列化聊天角色
	 */
	interface SerializeChat extends AppResult {

		/**
		 * 是否發送訊息
		 * 
		 * @return
		 */
		boolean isSendable();

		void setSendable(boolean sendable);

		/**
		 * 聊天角色
		 * 
		 * @return
		 */
		Chat getChat();

		void setChat(Chat chat);

	}

	/**
	 * 序列化聊天角色
	 * 
	 * 序列化到檔案, 目錄, file:custom/chat/unsave
	 * 
	 * custom/chat/unsave/TEST_ROLE4573801Upgckwh9n.ser
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param chat
	 * @return 輸出檔名
	 */
	String serializeChat(boolean sendable, Chat chat);

	/**
	 * 反序列化聊天角色, 目錄, file:custom/chat/unsave
	 * 
	 * custom/chat/unsave/TEST_ROLE4573801Upgckwh9n.ser
	 * 
	 * @param chatId
	 *            TEST_ROLE4573801Upgckwh9n
	 * @return
	 */
	Chat deserializeChat(String chatId);

	/**
	 * ser檔名
	 * 
	 * @param chatId
	 * @return
	 */
	String serName(String chatId);
}
