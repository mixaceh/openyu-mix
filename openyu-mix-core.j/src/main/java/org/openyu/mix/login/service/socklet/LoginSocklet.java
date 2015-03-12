package org.openyu.mix.login.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.login.service.LoginService;
import org.openyu.socklet.message.vo.Message;
import org.openyu.mix.app.service.socklet.supporter.AppSockletServiceSupporter;

public class LoginSocklet extends AppSockletServiceSupporter {
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(LoginSocklet.class);

	@Autowired
	@Qualifier("loginService")
	protected transient LoginService loginService;

	public LoginSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();
		
		switch (messageType) {
		case LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST: {
			{
				String accountId = message.getString(0);
				String authKey = message.getString(1);
				String ip = message.getString(2);
				loginService.authorize(accountId, authKey, ip);
				break;
			}
		}
		default: {
			LOGGER.warn("Can't resolve: " + message);
			break;
		}
		}
	}

}
