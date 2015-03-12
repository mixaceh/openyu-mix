package org.openyu.mix.core.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.socklet.context.service.event.ContextEvent;
import org.openyu.socklet.context.service.event.adapter.ContextAdapter;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;

/**
 * 核心本文轉接器
 */
public class CoreContextAdapter extends ContextAdapter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreContextAdapter.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	public CoreContextAdapter() {
	}

	/**
	 * 本文已初始化
	 * 
	 * @param contextEvent
	 */
	public void contextInitialized(ContextEvent contextEvent) {
		String contextId = contextEvent.getId();
		sendContextConnect(contextId);
	}

	/**
	 * 本文已銷毀化
	 * 
	 * @param contextEvent
	 */
	public void contextDestroyed(ContextEvent contextEvent) {
		String contextId = contextEvent.getId();
		sendContextDisconnect(contextId);
	}

	/**
	 * 發送本文連線
	 * 
	 * @param contextId
	 */
	protected Message sendContextConnect(String contextId) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM,
				CoreModuleType.CORE,
				CoreMessageType.CORE_CONTEXT_CONNECT_REQUEST);

		message.addString(contextId);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送本文斷線
	 * 
	 * @param contextId
	 */
	protected Message sendContextDisconnect(String contextId) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM,
				CoreModuleType.CORE,
				CoreMessageType.CORE_CONTEXT_DISCONNECT_REQUEST);

		message.addString(contextId);
		//
		messageService.addMessage(message);

		return message;
	}

}
