package org.openyu.mix.mail.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.mail.service.MailService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.mail.vo.MailType;
import org.openyu.socklet.message.vo.Message;

public class MailSocklet extends AppSockletServiceSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(MailSocklet.class);

	@Autowired
	@Qualifier("mailService")
	protected transient MailService mailService;

	public MailSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case MAIL_MAIL_REQUEST: {
			String roleId = message.getString(0);
			String mailId = message.getString(1);
			//
			Role role = checkRole(roleId);
			mailService.mail(true, role, mailId);
			break;
		}
		case MAIL_NEW_REQUEST: {
			String roleId = message.getString(0);
			String title = message.getString(1);
			String content = message.getString(2);
			String receiverName = message.getString(3);
			long gold = message.getLong(4);
			int size = message.getInt(5);
			//
			String itemUniqueIds[] = new String[size];
			int idx = 0;
			for (int i = 0; i < size; i++) {
				itemUniqueIds[i] = message.getString(5 + (++idx));
			}
			//
			Role role = checkRole(roleId);
			mailService.newMail(true, role, MailType.GENERAL, title, content,
					receiverName, gold, itemUniqueIds);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

}
