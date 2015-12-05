package org.openyu.mix.account.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.item.socklet.ItemSocklet;
import org.openyu.socklet.message.vo.Message;

public class AccountSocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = 8246246049693440479L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemSocklet.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	public AccountSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case ACCOUNT_AUTHORIZE_REQUEST: {

			String accountId = message.getString(0);
			String password = message.getString(1);
			accountService.authorize(accountId, password);
			break;

		}
		case ACCOUNT_AUTHORIZE_FROM_LOGIN_REQUEST: {

			String accountId = message.getString(0);
			String authKey = message.getString(1);
			accountService.authorizeFromLogin(accountId, authKey);
			break;
		}
		default: {
			LOGGER.warn("Can't resolve: " + message);
			break;
		}
		}
	}
}
