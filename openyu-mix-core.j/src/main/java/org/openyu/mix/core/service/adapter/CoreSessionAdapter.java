package org.openyu.mix.core.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;
import org.openyu.socklet.session.service.event.SessionEvent;
import org.openyu.socklet.session.service.event.adapter.SessionAdapter;
import org.openyu.socklet.session.vo.Session;

/**
 * 核心會話轉接器
 */
public class CoreSessionAdapter extends SessionAdapter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreSessionAdapter.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	public CoreSessionAdapter() {
	}

	/**
	 * 會話已建立
	 * 
	 * @param sessionEvent
	 */
	public void sessionCreated(SessionEvent sessionEvent) {
		Session session = sessionEvent.getSession();
		String sender = session.getId();
		// LOGGER.info("[" + sender+ "] Connected");
		sendRoleConnect(sender);
	}

	/**
	 * 會話已銷毀
	 * 
	 * @param sessionEvent
	 */
	public void sessionDestroyed(SessionEvent sessionEvent) {
		Session session = sessionEvent.getSession();
		String sender = session.getId();
		// LOGGER.info("[" +sender + "] Disconnected");
		sendRoleDisconnect(sender);
	}

	/**
	 * 發送角色連線
	 * 
	 * @param sender
	 */
	protected Message sendRoleConnect(String sender) {
		Message message = messageService.createMessage(CoreModuleType.CLIENT,
				CoreModuleType.CORE, CoreMessageType.CORE_ROLE_CONNECT_REQUEST);

		message.addString(sender);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送角色斷線
	 * 
	 * @param sender
	 */
	protected Message sendRoleDisconnect(String sender) {
		Message message = messageService.createMessage(CoreModuleType.CLIENT,
				CoreModuleType.CORE,
				CoreMessageType.CORE_ROLE_DISCONNECT_REQUEST);

		message.addString(sender);
		//
		messageService.addMessage(message);

		return message;
	}
}
