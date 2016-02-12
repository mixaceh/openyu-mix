package org.openyu.mix.item.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.BagInfo.Tab;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class ItemSocklet extends AppSockletServiceSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemSocklet.class);

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	public ItemSocklet() {
	}

	// --------------------------------------------------
	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case ITEM_DECREASE_ITEM_REQUEST: {
			String roleId = message.getString(0);
			String itemUniqueId = message.getString(1);
			int amount = message.getInt(2);
			//
			Role role = checkRole(roleId);
			itemService
					.decreaseItemWithUniqueId(true, role, itemUniqueId, amount);
			break;
		}
		case ITEM_USE_ITEM_REQUEST: {
			String roleId = message.getString(0);
			String targetId = message.getString(1);
			String itemUniqueId = message.getString(2);
			int amount = message.getInt(3);
			//
			Role role = checkRole(roleId);
			itemService.useItem(true, role, targetId, itemUniqueId, amount);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技增減道具
		case ITEM_DEBUG_CHANGE_ITEM_REQUEST: {
			String roleId = message.getString(0);
			String itemId = message.getString(1);
			int amount = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_changeItem(role, itemId, amount);
			break;
		}
		// 祕技包包清除
		case ITEM_DEBUG_CLEAR_BAG_REQUEST: {
			String roleId = message.getString(0);
			int tabIndex = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_clearBag(role, tabIndex);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	/**
	 * 祕技增減道具
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 */
	protected void DEBUG_changeItem(Role role, String itemId, int amount) {
		if (amount > 0) {
			itemService.increaseItemWithItemId(true, role, itemId, amount);
		} else if (amount < 0) {
			itemService.decreaseItemWithItemId(true, role, itemId, Math.abs(amount));
		}
		// amount=0,移除此道具
		else {
			BagInfo bagInfo = role.getBagInfo();
			int origAmount = bagInfo.getAmount(itemId);
			if (origAmount > 0) {
				itemService.decreaseItemWithItemId(true, role, itemId, origAmount);
			}
		}
	}

	/**
	 * 祕技清除包包
	 * 
	 * @param role
	 * @param tabIndex
	 */
	protected void DEBUG_clearBag(Role role, int tabIndex) {
		BagInfo bagInfo = role.getBagInfo();
		// tabIndex= -1 ,清所有包包頁
		if (tabIndex == -1) {
			BagInfo.ErrorType bagError = bagInfo.clearItem(true);// 鎖定也清除
			if (bagError == BagInfo.ErrorType.NO_ERROR) {
				itemService.sendBagInfo(role.getId(), bagInfo);
			}
		}
		// 清除指定包包頁
		else {
			Tab tab = bagInfo.getTab(tabIndex, true);
			if (tab != null) {
				BagInfo.ErrorType bagError = tab.clearItem(true);// 鎖定也清除
				if (bagError == BagInfo.ErrorType.NO_ERROR) {
					itemService.sendTab(role.getId(), tab);
				}
			}
		}
	}
}
