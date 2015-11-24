package org.openyu.mix.app.service.socklet.supporter;

import org.openyu.mix.app.service.socklet.AppSockletService;
import org.openyu.mix.role.ex.RoleException;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.chat.service.ChatSetService;
import org.openyu.mix.chat.ex.ChatException;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;
import org.openyu.socklet.socklet.service.supporter.SockletServiceSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AppSockletServiceSupporter extends SockletServiceSupporter
		implements AppSockletService {

	private static final long serialVersionUID = -7928804185521125204L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AppSockletServiceSupporter.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	/**
	 * 角色集合服務, 存放所有本地/同步的角色在mem中
	 */
	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	/**
	 * 聊天角色集合服務, 存放所有本地/同步的角色在mem中
	 */
	@Autowired
	@Qualifier("chatSetService")
	protected transient ChatSetService chatSetService;

	public AppSockletServiceSupporter() {
	}

	public void service(Message message) {

	}

	/**
	 * 檢查角色是否在memory中
	 * 
	 * @param roleId
	 * @return
	 */
	protected Role checkRole(String roleId) {
		Role result = null;
		result = roleSetService.getRole(roleId);
		if (result == null) {
			throw new RoleException("[" + roleId + "] is not in memory");
		}
		return result;
	}

	/**
	 * 檢查聊天角色是否在memory中
	 * 
	 * @param roleId
	 * @return
	 */
	protected Chat checkChat(String chatId) {
		Chat result = null;
		result = chatSetService.getChat(chatId);
		if (result == null) {
			throw new ChatException("[" + chatId + "] is not in memory");
		}
		return result;
	}
}
