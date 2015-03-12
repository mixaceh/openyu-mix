package org.openyu.mix.wuxing.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class WuxingSocklet extends AppSockletServiceSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingSocklet.class);

	@Autowired
	@Qualifier("wuxingService")
	protected transient WuxingService wuxingService;

	public WuxingSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case WUXING_PLAY_REQUEST: {
			String roleId = message.getString(0);
			int playValue = message.getInt(1);
			//
			Role role = checkRole(roleId);
			wuxingService.play(true, role, playValue);
			break;
		}
		case WUXING_PUT_ONE_REQUEST: {
			String roleId = message.getString(0);
			String itemId = message.getString(1);
			int amount = message.getInt(2);
			//
			Role role = checkRole(roleId);
			wuxingService.putOne(true, role, itemId, amount);
			break;
		}
		case WUXING_PUT_ALL_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			wuxingService.putAll(true, role);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技五行玩
		case WUXING_DEBUG_PLAY_REQUEST: {
			String roleId = message.getString(0);
			int playValue = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_play(role, playValue);
			break;
		}
		// 祕技五行中獎區所有道具放入包包
		case WUXING_DEBUG_PUT_ALL_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_putAll(role);
			break;
		}
		// 祕技五行重置
		case WUXING_DEBUG_RESET_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_reset(role);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	// --------------------------------------------------
	// 祕技
	// --------------------------------------------------
	/**
	 * 祕技五行玩
	 * 
	 * @param role
	 * @param playValue
	 */
	protected void DEBUG_play(Role role, int playValue) {
		wuxingService.play(true, role, playValue);
	}

	/**
	 * 祕技五行中獎區所有道具放入包包
	 * 
	 * @param role
	 */
	protected void DEBUG_putAll(Role role) {
		wuxingService.putAll(true, role);
	}

	/**
	 * 祕技五行重置
	 * 
	 * @param roleId
	 */
	protected void DEBUG_reset(Role role) {
		WuxingPen wuxingPen = role.getWuxingPen();
		//
		boolean result = wuxingPen.reset();
		// 發訊息
		if (result) {
			wuxingService.sendReset(role, wuxingPen);
		}
	}
}
