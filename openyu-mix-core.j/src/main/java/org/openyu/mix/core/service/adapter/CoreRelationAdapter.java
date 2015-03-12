package org.openyu.mix.core.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.socklet.acceptor.service.event.RelationEvent;
import org.openyu.socklet.acceptor.service.event.adapter.RelationAdapter;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;

/**
 * 核心關連轉接器
 */
public class CoreRelationAdapter extends RelationAdapter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreRelationAdapter.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	public CoreRelationAdapter() {
	}

	/**
	 * 關聯主動/被動連線時用
	 * 
	 * @param relationEvent
	 */
	public void relationConnected(RelationEvent relationEvent) {
		String relationId = relationEvent.getId();
		// LOGGER.info("[" + relationId + "] Connected");
		sendRelationConnect(relationId);
	}

	/**
	 * 關聯主動/被動斷線時用
	 * 
	 * @param relationEvent
	 */
	public void relationDisconnected(RelationEvent relationEvent) {
		String relationId = relationEvent.getId();
		// LOGGER.info("[" + relationId + "] Disconnected");
		sendRelationDisconnect(relationId);
	}

	/**
	 * 關聯只有主動連線失敗時用
	 * 
	 * @param relationEvent
	 */
	public void relationRefused(RelationEvent relationEvent) {
		String relationId = relationEvent.getId();
		// LOGGER.info("[" + relationId + "] Refused");
		sendRelationRefused(relationId);
	}

	/**
	 * 發送關聯連線
	 * 
	 * @param relationId
	 */
	protected Message sendRelationConnect(String relationId) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM,
				CoreModuleType.CORE,
				CoreMessageType.CORE_RELATION_CONNECT_REQUEST);

		message.addString(relationId);
		//
		messageService.addMessage(message);

		return message;

	}

	/**
	 * 發送關聯斷線
	 * 
	 * @param relationId
	 */
	protected Message sendRelationDisconnect(String relationId) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM,
				CoreModuleType.CORE,
				CoreMessageType.CORE_RELATION_DISCONNECT_REQUEST);

		message.addString(relationId);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送關聯斷線
	 * 
	 * @param relationId
	 */
	protected Message sendRelationRefused(String relationId) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM,
				CoreModuleType.CORE,
				CoreMessageType.CORE_RELATION_REFUSED_REQUEST);

		message.addString(relationId);
		//
		messageService.addMessage(message);

		return message;
	}
}
