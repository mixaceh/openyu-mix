package org.openyu.mix.chat.dao;

import org.openyu.mix.app.dao.AppDao;
import org.openyu.mix.chat.po.ChatPo;

public interface ChatDao extends AppDao
{
	/**
	 * 查詢聊天角色
	 * 
	 * @param chatId
	 * @return
	 */
	ChatPo findChat(String chatId);

}
