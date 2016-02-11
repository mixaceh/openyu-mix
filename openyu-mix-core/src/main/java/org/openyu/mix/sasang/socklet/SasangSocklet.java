package org.openyu.mix.sasang.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class SasangSocklet extends AppSockletServiceSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(SasangSocklet.class);

	@Autowired
	@Qualifier("sasangService")
	protected transient SasangService sasangService;

	public SasangSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case SASANG_PLAY_REQUEST: {
			String roleId = message.getString(0);
			int playValue = message.getInt(1);
			//
			Role role = checkRole(roleId);
			sasangService.play(true, role, playValue);
			break;
		}
		case SASANG_PUT_ONE_REQUEST: {
			String roleId = message.getString(0);
			String itemId = message.getString(1);
			int amount = message.getInt(2);
			//
			Role role = checkRole(roleId);
			sasangService.putOne(true, role, itemId, amount);
			break;
		}
		case SASANG_PUT_ALL_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			sasangService.putAll(true, role);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技四象玩
		case SASANG_DEBUG_PLAY_REQUEST: {
			String roleId = message.getString(0);
			int playValue = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_play(role, playValue);
			break;
		}
		// 祕技四象中獎區所有道具放入包包
		case SASANG_DEBUG_PUT_ALL_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_putAll(role);
			break;
		}
		// 祕技四象重置
		case SASANG_DEBUG_RESET_REQUEST: {
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
	 * 祕技四象玩
	 * 
	 * @param role
	 * @param playValue
	 */
	protected void DEBUG_play(Role role, int playValue) {
		sasangService.play(true, role, playValue);
	}

	/**
	 * 祕技四象中獎區所有道具放入包包
	 * 
	 * @param role
	 */
	protected void DEBUG_putAll(Role role) {
		sasangService.putAll(true, role);
	}

	/**
	 * 祕技四象重置
	 * 
	 * @param role
	 */
	protected void DEBUG_reset(Role role) {
		SasangPen sasangPen = role.getSasangPen();
		//
		boolean result = sasangPen.reset();
		// 發訊息
		if (result) {
			sasangService.sendReset(role, sasangPen);
		}
	}
}
