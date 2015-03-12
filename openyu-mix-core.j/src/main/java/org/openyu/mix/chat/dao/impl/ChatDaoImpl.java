package org.openyu.mix.chat.dao.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppDaoSupporter;
import org.openyu.mix.chat.dao.ChatDao;
import org.openyu.mix.chat.po.ChatPo;
import org.openyu.mix.chat.po.impl.ChatPoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatDaoImpl extends AppDaoSupporter implements ChatDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ChatDaoImpl.class);

	private static final String CHAT_PO_NAME = ChatPoImpl.class.getName();

	public ChatDaoImpl() {
	}

	/**
	 * 查詢聊天角色
	 * 
	 * @param chatId
	 * @return
	 */
	public ChatPo findChat(String chatId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(CHAT_PO_NAME + " ");
		hql.append("where 1=1 ");
		// id
		hql.append("and id = :id ");
		params.put("id", chatId);
		//
		return findUniqueByHql(hql.toString(), params);
	}
}
