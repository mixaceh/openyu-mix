package org.openyu.mix.core.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreService;
import org.openyu.socklet.message.vo.Message;
import org.openyu.mix.app.service.socklet.supporter.AppSockletServiceSupporter;

public class CoreSocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = 5530428568785532692L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreSocklet.class);

	@Autowired
	@Qualifier("coreService")
	protected transient CoreService coreService;

	public CoreSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		// 本文
		case CORE_CONTEXT_CONNECT_REQUEST: {
			String contextId = message.getString(0);
			coreService.contextConnect(contextId, null);
			break;
		}
		case CORE_CONTEXT_DISCONNECT_REQUEST: {
			String contextId = message.getString(0);
			coreService.contextDisconnect(contextId, null);
			break;
		}
		// 伺服器關連
		case CORE_RELATION_CONNECT_REQUEST: {
			String relationId = message.getString(0);
			coreService.relationConnect(relationId, null);
			break;
		}
		case CORE_RELATION_DISCONNECT_REQUEST: {
			String relationId = message.getString(0);
			coreService.relationDisconnect(relationId, null);
			break;
		}
		case CORE_RELATION_REFUSED_REQUEST: {
			String relationId = message.getString(0);
			coreService.relationRefused(relationId, null);
			break;
		}
		// 角色
		case CORE_ROLE_CONNECT_REQUEST: {
			String roleId = message.getString(0);
			coreService.roleConnect(roleId, null);
			break;
		}
		case CORE_ROLE_DISCONNECT_REQUEST: {
			String roleId = message.getString(0);
			coreService.roleDisconnect(roleId, null);
			break;
		}
		default: {
			LOGGER.warn("Can't resolve: " + message);
			break;
		}
		}
	}
}
