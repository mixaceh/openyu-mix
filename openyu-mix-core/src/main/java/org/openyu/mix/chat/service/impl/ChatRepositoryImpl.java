package org.openyu.mix.chat.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.chat.service.ChatRepository;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.commons.util.concurrent.NullValueMap;
import org.openyu.commons.util.concurrent.impl.NullValueMapImpl;

/**
 * 聊天角色集合服務, 存放所有本地/同步的角色在mem中
 */
public class ChatRepositoryImpl extends AppServiceSupporter implements ChatRepository {

	private static final long serialVersionUID = -6007699325820709918L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ChatRepositoryImpl.class);

	/**
	 * 同步chat, 來自於其它server上的chat
	 * 
	 * <chatId,chat>
	 */
	private NullValueMap<String, Chat> syncChats = new NullValueMapImpl<String, Chat>();

	public ChatRepositoryImpl() {
	}

	@Override
	protected void checkConfig() throws Exception {

	}
	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 加入聊天角色
	 * 
	 * @param chat
	 * @return
	 */
	public Chat addChat(Chat chat) {
		Chat result = null;
		if (chat != null) {
			result = (Chat) beans.put(chat.getId(), chat);
		}
		return result;
	}

	/**
	 * 取得聊天角色,包含同步
	 * 
	 * @param chatId
	 * @return
	 */
	public Chat getChat(String chatId) {
		return getChat(chatId, true);
	}

	/**
	 * 取得聊天角色
	 * 
	 * @param chatId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public Chat getChat(String chatId, boolean includeSync) {
		Chat result = null;
		if (chatId != null) {
			result = (Chat) beans.get(chatId);
			// 同步chat
			if (result == null && includeSync) {
				result = syncChats.get(chatId);
			}
		}
		return result;
	}

	/**
	 * 取得所有聊天角色,包含同步
	 * 
	 * @return
	 */
	public Map<String, Chat> getChats() {
		return getChats(true);
	}

	/**
	 * 取得所有聊天角色
	 * 
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public Map<String, Chat> getChats(boolean includeSync) {
		Map<String, Chat> result = new ConcurrentHashMap<String, Chat>();
		for (Object entry : beans.getValues()) {
			if (entry instanceof Chat) {
				Chat value = (Chat) entry;
				result.put(value.getId(), value);
			}
		}

		// 同步chat
		if (includeSync) {
			for (Chat chat : syncChats.getValues()) {
				result.put(chat.getId(), chat);
			}
		}
		return result;
	}

	/**
	 * 取得所有聊天角色的id,包含同步
	 * 
	 * @return
	 */
	public List<String> getChatIds() {
		return getChatIds(true);
	}

	/**
	 * 取得所有聊天角色的id
	 * 
	 * @param sync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public List<String> getChatIds(boolean includeSync) {
		List<String> result = new LinkedList<String>();
		for (String chatId : beans.getKeys()) {
			result.add(chatId);
		}
		// 同步chat
		if (includeSync) {
			for (String chatId : syncChats.getKeys()) {
				result.add(chatId);
			}
		}
		return result;
	}

	/**
	 * 移除聊天角色,只限本地
	 * 
	 * @param chatId
	 * @return
	 */
	public Chat removeChat(String chatId) {
		Chat result = null;
		if (chatId != null) {
			result = (Chat) beans.remove(chatId);
		}
		return result;
	}

	/**
	 * 移除聊天角色,只限本地
	 * 
	 * @param chatId
	 * @return
	 */
	public Chat removeChat(Chat chat) {
		Chat result = null;
		if (chat != null) {
			result = removeChat(chat.getId());
		}
		return result;
	}

	/**
	 * 聊天角色是否存在,包含同步
	 * 
	 * @param chatId
	 * @return
	 */
	public boolean containChat(String chatId) {
		return containChat(chatId, true);
	}

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chatId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public boolean containChat(String chatId, boolean includeSync) {
		boolean result = false;
		if (chatId != null) {
			result = beans.contains(chatId);
			// 同步chat
			if (!result && includeSync) {
				result = syncChats.contains(chatId);
			}
		}
		return result;
	}

	/**
	 * 聊天角色是否存在,包含同步
	 * 
	 * @param chat
	 * @return
	 */
	public boolean containChat(Chat chat) {
		return containChat(chat, true);
	}

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chatId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	public boolean containChat(Chat chat, boolean includeSync) {
		boolean result = false;
		if (chat != null) {
			result = containChat(chat.getId(), includeSync);
		}
		return result;
	}

	// --------------------------------------------------
	// 同步用聊天角色
	// --------------------------------------------------
	/**
	 * 加入同步聊天角色
	 * 
	 * @param syncChat
	 * @return
	 */

	public Chat addSyncChat(Chat syncChat) {
		Chat result = null;
		if (syncChat != null) {
			result = (Chat) syncChats.put(syncChat.getId(), syncChat);
		}
		return result;
	}

	/**
	 * 取得同步聊天角色
	 * 
	 * @param syncChatId
	 * @return
	 */
	public Chat getSyncChat(String syncChatId) {
		Chat result = null;
		if (syncChatId != null) {
			result = syncChats.get(syncChatId);
		}
		return result;
	}

	/**
	 * 移除同步聊天角色
	 *
	 * @param syncChatId
	 * @return
	 */
	public Chat removeSyncChat(String syncChatId) {
		Chat result = null;
		if (syncChatId != null) {
			result = (Chat) syncChats.remove(syncChatId);
		}
		return result;
	}

	/**
	 * 移除同步聊天角色
	 *
	 * @param syncChat
	 * @return
	 */
	public Chat removeSyncChat(Chat syncChat) {
		Chat result = null;
		if (syncChat != null) {
			result = removeSyncChat(syncChat.getId());
		}
		return result;
	}
}