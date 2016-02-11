package org.openyu.mix.system.socklet;

import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.system.service.SystemService;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SystemSocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = 1785146099813920212L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SystemSocklet.class);

	@Autowired
	@Qualifier("systemService")
	protected transient SystemService systemService;

	public SystemSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message.getMessageType();

		switch (messageType) {
		default: {
			LOGGER.error("Can't resolve : " + message);
			break;
		}
		}
	}

}
