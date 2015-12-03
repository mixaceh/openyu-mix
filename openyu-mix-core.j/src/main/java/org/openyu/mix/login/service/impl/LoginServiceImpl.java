package org.openyu.mix.login.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.login.service.LoginService;
import org.openyu.socklet.message.vo.Message;

public class LoginServiceImpl extends AppServiceSupporter implements LoginService {

	private static final long serialVersionUID = 836781171420996474L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	public LoginServiceImpl() {
	}

	public void authorize(String accountId, String authKey, String ip) {
		Message message = messageService.createMessage(CoreModuleType.LOGIN, CoreModuleType.ACCOUNT,
				CoreMessageType.ACCOUNT_AUTHORIZE_FROM_LOGIN_REQUEST);
		message.addString(accountId);
		message.addString(authKey);
		messageService.addMessage(message);
	}

}
