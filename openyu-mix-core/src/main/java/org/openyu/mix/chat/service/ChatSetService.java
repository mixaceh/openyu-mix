package org.openyu.mix.chat.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.chat.vo.Chat;

/**
 * 聊天角色集合服務, 存放所有本地/同步的角色在mem中
 * 
 * org/openyu/mix/app/applicationContext-app.xml
 */
public interface ChatSetService extends AppService {

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 加入聊天角色
	 * 
	 * @param chat
	 * @return
	 */
	Chat addChat(Chat chat);

	/**
	 * 取得聊天角色,包含同步
	 * 
	 * @param chatId
	 * @return
	 */
	Chat getChat(String chatId);

	/**
	 * 取得聊天角色
	 * 
	 * @param chatId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	Chat getChat(String chatId, boolean includeSync);

	/**
	 * 取得所有聊天角色,包含同步
	 * 
	 * @return
	 */
	Map<String, Chat> getChats();

	/**
	 * 取得所有聊天角色
	 * 
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	Map<String, Chat> getChats(boolean includeSync);

	/**
	 * 取得所有聊天角色的id,包含同步
	 * 
	 * @return
	 */
	List<String> getChatIds();

	/**
	 * 取得所有聊天角色的id
	 * 
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	List<String> getChatIds(boolean includeSync);

	/**
	 * 移除聊天角色
	 * 
	 * @param chatId
	 * @return
	 */
	Chat removeChat(String chatId);

	/**
	 * 移除聊天角色
	 * 
	 * @param chat
	 * @return
	 */
	Chat removeChat(Chat chat);

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chatId
	 * @return
	 */
	boolean containChat(String chatId);

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chatId
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	boolean containChat(String chatId, boolean includeSync);

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chat
	 * @return
	 */
	boolean containChat(Chat chat);

	/**
	 * 聊天角色是否存在
	 * 
	 * @param chat
	 * @param includeSync
	 *            true=包含同步,false=只有本地
	 * @return
	 */
	boolean containChat(Chat chat, boolean includeSync);

	// --------------------------------------------------
	// 同步用聊天角色
	// --------------------------------------------------
	/**
	 * 加入同步聊天角色
	 * 
	 * @param syncChat
	 * @return
	 */
	Chat addSyncChat(Chat syncChat);

	/**
	 * 取得同步聊天角色
	 * 
	 * @param syncChatId
	 * @return
	 */
	Chat getSyncChat(String syncChatId);

	/**
	 * 移除同步聊天角色
	 *
	 * @param syncChatId
	 * @return
	 */
	Chat removeSyncChat(String syncChatId);

	/**
	 * 移除同步聊天角色
	 *
	 * @param syncChat
	 * @return
	 */
	Chat removeSyncChat(Chat syncChat);

}
