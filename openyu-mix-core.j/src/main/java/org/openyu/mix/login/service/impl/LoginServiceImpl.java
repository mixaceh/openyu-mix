package org.openyu.mix.login.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.login.service.LoginService;
import org.openyu.commons.service.supporter.BaseServiceSupporter;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.vo.Message;

public class LoginServiceImpl extends BaseServiceSupporter implements
		LoginService {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	@Autowired
	@Qualifier("messageService")
	protected transient MessageService messageService;

	public LoginServiceImpl() {
	}

	public void authorize(String accountId, String authKey, String ip) {
		Message message = messageService.createMessage(CoreModuleType.LOGIN,
				CoreModuleType.ACCOUNT,
				CoreMessageType.ACCOUNT_AUTHORIZE_FROM_LOGIN_REQUEST);
		message.addString(accountId);
		message.addString(authKey);
		messageService.addMessage(message);
	}

}
