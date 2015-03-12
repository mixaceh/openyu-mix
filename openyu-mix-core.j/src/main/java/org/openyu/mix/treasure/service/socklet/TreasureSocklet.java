package org.openyu.mix.treasure.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class TreasureSocklet extends AppSockletServiceSupporter {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TreasureSocklet.class);

	@Autowired
	@Qualifier("treasureService")
	protected transient TreasureService treasureService;

	public TreasureSocklet() {
	}

	public void service(Message message) {
		super.service(message);
		
		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case TREASURE_REFRESH_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			treasureService.refresh(true, role);
			break;
		}
		case TREASURE_BUY_REQUEST: {
			String roleId = message.getString(0);
			int buyBalue = message.getInt(1);
			int index = message.getInt(2);
			//
			Role role = checkRole(roleId);
			treasureService.buy(true, role, buyBalue, index);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技祕寶刷新
		case TREASURE_DEBUG_REFRESH_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_refresh(role);
			break;
		}
		// 祕技祕寶購買
		case TREASURE_DEBUG_BUY_REQUEST: {
			String roleId = message.getString(0);
			int buyValue = message.getInt(1);// 購買類別
			int index = message.getInt(2);// 上架祕寶索引
			//
			Role role = checkRole(roleId);
			DEBUG_buy(role, buyValue, index);
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
	 * 祕技祕寶刷新
	 * 
	 * @param role
	 */
	protected void DEBUG_refresh(Role role) {
		treasureService.refresh(true, role);
	}

	/**
	 * 祕技祕寶購買
	 * 
	 * @param role
	 * @param gridIndex
	 * @param index
	 */
	protected void DEBUG_buy(Role role, int buyValue, int index) {
		// 所有
		if (buyValue == -1 || index == -1) {
			for (Integer gridIndex : role.getTreasurePen().getTreasures()
					.keySet()) {
				// 金幣購買
				treasureService.buy(true, role, 1, gridIndex);
			}
		} else {
			treasureService.buy(true, role, buyValue, index);
		}
	}

}
