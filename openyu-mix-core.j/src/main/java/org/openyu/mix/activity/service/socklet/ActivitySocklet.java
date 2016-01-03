package org.openyu.mix.activity.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.activity.service.ActivityService;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.socklet.message.vo.Message;

public class ActivitySocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = 1750563505056309852L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ActivitySocklet.class);

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	@Autowired
	@Qualifier("activityService")
	protected transient ActivityService activityService;

	public ActivitySocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message.getMessageType();
		switch (messageType) {
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}
}
