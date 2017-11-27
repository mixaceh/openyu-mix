package org.openyu.mix.role.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.service.StoreRoleService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.socklet.message.vo.Message;

public class RoleSocklet extends AppSockletServiceSupporter {

	private static final long serialVersionUID = -2673220967705020441L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(RoleSocklet.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("storeRoleService")
	protected transient StoreRoleService storeRoleService;

	public RoleSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		// 同步角色
		case ROLE_SYNC_ROLE_CONNECT_REQUEST: {
			Role syncRole = message.getObject(0);
			roleService.syncRoleConnect(syncRole);
			break;
		}
		case ROLE_SYNC_ROLE_DISCONNECT_REQUEST: {
			String syncRoleId = message.getString(0);
			roleService.syncRoleDisconnect(syncRoleId);
			break;
		}
		case ROLE_SYNC_ROLE_FIELD_REQUEST: {
			String syncRoleId = message.getString(0);
			String fieldName = message.getString(1);
			Object value = message.getObject(2);
			roleService.syncRoleField(syncRoleId, fieldName, value);
			break;
		}

		// 增減經驗
		case ROLE_CHANGE_EXP_REQUEST: {
			String roleId = message.getString(0);
			long exp = message.getLong(1);
			//
			Role role = checkRole(roleId);
			roleService.changeExp(true, role, exp);
			break;
		}
		// 增減技魂(sp)
		case ROLE_CHANGE_SP_REQUEST: {
			String roleId = message.getString(0);
			long sp = message.getLong(1);
			//
			Role role = checkRole(roleId);
			roleService.changeSp(true, role, sp);
			break;
		}
		// 增減金幣
		case ROLE_CHANGE_GOLD_REQUEST: {
			String roleId = message.getString(0);
			long gold = message.getLong(1);
			int goldReasonValue = message.getInt(2);
			GoldType goldReason = EnumHelper.valueOf(GoldType.class,
					goldReasonValue);
			//
			Role role = checkRole(roleId);
			// 增加
			if (gold > 0) {
				roleService.increaseGold(true, role, gold, goldReason);
			}
			// 減少
			else if (gold < 0) {
				roleService
						.decreaseGold(true, role, Math.abs(gold), goldReason);
			}
			break;
		}
		// 增減聲望
		case ROLE_CHANGE_FAME_REQUEST: {
			String roleId = message.getString(0);
			int fame = message.getInt(1);
			//
			Role role = checkRole(roleId);
			roleService.changeFame(true, role, fame);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		case ROLE_DEBUG_STORE_ROLE_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_storeRole(role);
			break;
		}
		case ROLE_DEBUG_CHANGE_EXP_REQUEST: {
			String roleId = message.getString(0);
			long exp = message.getLong(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeExp(role, exp);
			break;
		}
		case ROLE_DEBUG_CHANGE_LEVEL_REQUEST: {
			String roleId = message.getString(0);
			int level = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeLevel(role, level);
			break;
		}
		case ROLE_DEBUG_CHANGE_GOLD_REQUEST: {
			String roleId = message.getString(0);
			long gold = message.getLong(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeGold(role, gold);
			break;
		}
		case ROLE_DEBUG_CHANGE_FAME_REQUEST: {
			String roleId = message.getString(0);
			int fame = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeFame(role, fame);
			break;
		}
		case ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST: {
			String roleId = message.getString(0);
			int type = message.getInt(1);
			int point = message.getInt(2);
			int addPoint = message.getInt(3);
			int addRate = message.getInt(4);
			//
			Role role = checkRole(roleId);
			roleService.changeAttribute(true, role, type, point, addPoint,
					addRate);
			break;
		}
		case ROLE_DEBUG_CHANGE_COIN_REQUEST: {
			String roleId = message.getString(0);
			int coin = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeCoin(role, coin);
			break;
		}
		case ROLE_DEBUG_CHANGE_VIP_REQUEST: {
			String roleId = message.getString(0);
			int vip = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_changeVip(role, vip);
			break;
		}
		case ROLE_DEBUG_INFO_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_info(role);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	/**
	 * 秘技,儲存角色
	 * 
	 * @param role
	 */
	protected void DEBUG_storeRole(Role role) {
		storeRoleService.storeRole(true, role);
	}

	/**
	 * 秘技,增減經驗
	 * 
	 * @param role
	 */
	protected void DEBUG_changeExp(Role role, long exp) {
		roleService.changeExp(true, role, exp);
	}

	/**
	 * 祕技,增減等級
	 * 
	 * @param role
	 * @param level
	 */
	protected void DEBUG_changeLevel(Role role, int level) {
		// 重置,最低level=1
		if (level == 0) {
			int origLevel = role.getLevel();
			roleService.changeLevel(true, role, (-1) * origLevel);
		} else {
			roleService.changeLevel(true, role, level);
		}
	}

	/**
	 * 祕技,增減聲望
	 * 
	 * @param role
	 * @param level
	 */
	protected void DEBUG_changeFame(Role role, int fame) {
		// 重置,fame=0
		if (fame == 0) {
			int origFame = role.getFame();

			roleService.changeFame(true, role, (-1) * origFame);
		} else {
			roleService.changeFame(true, role, fame);
		}
	}

	/**
	 * 秘技,增減金幣
	 * 
	 * 為了處理金幣增減的原因,所以再寫一個method處理
	 * 
	 * @param role
	 * @param gold
	 */
	protected void DEBUG_changeGold(Role role, long gold) {
		// 增加
		if (gold > 0) {
			roleService.increaseGold(true, role, gold, GoldType.DEBUG_INCREASE);
		}
		// 減少
		else if (gold < 0) {
			roleService.decreaseGold(true, role, Math.abs(gold),
					GoldType.DEBUG_DECREASE);
		}
		// gold=0,重置
		else {
			roleService.resetGold(true, role, GoldType.DEBUG_RESET);
		}
	}

	/**
	 * 秘技,增減儲值幣
	 * 
	 * 為了處理儲值幣增減的原因,所以再寫一個method處理
	 * 
	 * @param role
	 * @param coin
	 */
	protected void DEBUG_changeCoin(Role role, int coin) {
		String accountId = role.getAccountId();
		if (accountId == null) {
			return;
		}
		//
		if (coin > 0) {
			accountService.increaseCoin(true, accountId, role, coin, false,
					CoinType.DEBUG_INCREASE);
		} else if (coin < 0) {
			accountService.decreaseCoin(true, accountId, role, Math.abs(coin),
					CoinType.DEBUG_DECREASE);
		}
		// 重置,coin=0
		else {
			accountService.resetCoin(true, accountId, role, false,
					CoinType.DEBUG_RESET);
		}
	}

	/**
	 * 秘技,增減vip
	 * 
	 * @param role
	 * @param vip
	 */
	protected void DEBUG_changeVip(Role role, int vip) {
		// 重置,vipType=VipType._0
		if (vip == 0) {
			VipType origVipType = role.getVipType();
			if (origVipType != null) {
				roleService
						.changeVip(true, role, (-1) * origVipType.getValue());
			}
		} else {
			roleService.changeVip(true, role, vip);
		}
	}
	
	protected void DEBUG_info(Role role) {
			roleService.sendRoleConnect(role, new AccountImpl());
	}
}
