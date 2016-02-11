package org.openyu.mix.account.service.impl;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.dao.AccountDao;
import org.openyu.mix.account.po.AccountPo;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.anno.CommonTx;
import org.openyu.commons.lang.ClassHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.security.AuthKey;
import org.openyu.commons.security.AuthKeyService;
import org.openyu.commons.security.anno.DefaultAuthKeyService;
import org.openyu.commons.util.AssertHelper;
import org.openyu.socklet.message.vo.Message;

/**
 * 帳戶服務
 */
public class AccountServiceImpl extends AppServiceSupporter implements AccountService {

	private static final long serialVersionUID = 3372722011771351537L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@DefaultAuthKeyService
	protected transient AuthKeyService authKeyService;

	public AccountServiceImpl() {
	}

	public AccountDao getAccountDao() {
		return (AccountDao) getCommonDao();
	}

	@Autowired
	@Qualifier("accountDao")
	public void setAccountDao(AccountDao accountDao) {
		setCommonDao(accountDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The AccountDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------
	/**
	 * 查詢帳戶
	 * 
	 * @param accountId
	 * @return
	 */
	public Account findAccount(String accountId) {
		AccountPo orig = getAccountDao().findAccount(accountId);
		return ClassHelper.copyProperties(orig);
	}

	/**
	 * 查詢帳戶
	 * 
	 * @param locale
	 * @param accountId
	 * @return
	 */
	public Account findAccount(Locale locale, String accountId) {
		AccountPo orig = getAccountDao().findAccount(locale, accountId);
		return ClassHelper.copyProperties(orig);
	}

	/**
	 * 查詢是否有效帳戶
	 * 
	 * @param valid
	 * @return
	 */
	public List<Account> findAccount(boolean valid) {
		List<AccountPo> orig = getAccountDao().findAccount(valid);
		return ClassHelper.copyProperties(orig);
	}

	/**
	 * 查詢是否有效帳戶
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	public List<Account> findAccount(Locale locale, boolean valid) {
		List<AccountPo> orig = getAccountDao().findAccount(locale, valid);
		return ClassHelper.copyProperties(orig);
	}

	/**
	 * 查詢帳戶的儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	public int findCoin(String accountId) {
		return getAccountDao().findCoin(accountId);
	}

	/**
	 * 查詢帳號的累計儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	public int findAccuCoin(String accountId) {
		return getAccountDao().findAccuCoin(accountId);
	}

	/**
	 * 檢查是否可以增加帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param coin
	 *            正數
	 * @return
	 */
	public boolean checkIncreaseCoin(String accountId, int coin) {
		boolean result = false;
		int currentCoin = findCoin(accountId);

		// coin 不能為負數,都當正數處理,且兩數相加不能大於Integer.MAX_VALUE
		result = (coin < 0 ? false : !NumberHelper.isAddOverflow(coin, currentCoin));
		return result;
	}

	/**
	 * 增加帳戶的儲值幣
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            正數
	 * @param accuable
	 *            是否累計
	 * @param coinReason
	 *            log用,儲值增加的原因
	 * @return 真正增加的儲值幣,0=無改變, >0有改變
	 */
	@CommonTx
	public int increaseCoin(boolean sendable, String accountId, Role role, int coin, boolean accuable,
			CoinType coinReason) {
		int result = 0;
		if (coin > 0) {
			result = changeCoin(sendable, accountId, role, coin, accuable, ActionType.INCREASE, coinReason);
		}
		return result;
	}

	/**
	 * 檢查是否可以減少帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param coin
	 *            正數
	 * @return
	 */
	public boolean checkDecreaseCoin(String accountId, int coin) {
		boolean result = false;
		int currentCoin = findCoin(accountId);

		// coin 不能為負數,都當正數處理
		result = (coin < 0 || currentCoin == 0 ? false : (coin > currentCoin ? false : true));
		return result;
	}

	/**
	 * 減少帳戶的儲值幣
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            正數
	 * @param accuable
	 *            是否累計
	 * @param coinReason
	 *            log用,儲值增減的原因
	 * @return 真正減少的儲值幣,0=無改變, <0有改變
	 */
	@CommonTx
	public int decreaseCoin(boolean sendable, String accountId, Role role, int coin, CoinType coinReason) {
		int result = 0;
		if (coin > 0) {
			result = changeCoin(sendable, accountId, role, (-1) * coin, false, ActionType.DECREASE, coinReason);
		}
		return result;
	}

	/**
	 * 增減帳戶的儲值幣
	 * 
	 * 直接對db處理,並更新角色上的帳戶
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            +增加,-減少
	 * @param accuable
	 *            是否累計
	 * @param coinAction
	 *            log用,儲值操作類別
	 * @param coinReason
	 *            log用,儲值增減的原因
	 * @return 真正增減的儲值幣,0=無改變, !=0有改變
	 */
	@CommonTx
	public int changeCoin(boolean sendable, String accountId, Role role, int coin, boolean accuable,
			ActionType coinAction, CoinType coinReason) {
		int result = 0;
		// 檢查條件,若=0不改變了
		if (coin == 0) {
			return result;
		}
		// 從db讀取
		Account account = findAccount(accountId);
		if (account == null) {
			return result;
		}
		//
		if (coin < 0 && ActionType.INCREASE.equals(coinAction)) {
			coin = Math.abs(coin);
		} else if (coin > 0 && ActionType.DECREASE.equals(coinAction)) {
			coin = (-1) * coin;
		}
		//
		account.setCoin(account.getCoin() + coin);

		// 當coin為正數,且accuable=true,才累計到accuCoin
		if (coin > 0 && accuable) {
			account.setAccuCoin(account.getAccuCoin() + coin);
		}
		// 存db
		int update = update(account);
		// 成功
		if (update > 0) {
			result = coin;
			// 發送訊息
			if (result != 0 && role != null && sendable) {
				sendCoin(role, account.getCoin(), result);
			}
		}
		return result;
	}

	/**
	 * 重置帳戶儲值幣,coin=0,accuCoin=0
	 * 
	 * @param sendable
	 * @param accountId
	 * @param role
	 *            角色
	 * @param accuable
	 *            是否累計
	 * @param coinReason
	 * @return
	 */
	@CommonTx
	public boolean resetCoin(boolean sendable, String accountId, Role role, boolean accuable, CoinType coinReason) {
		boolean result = false;
		// 檢查條件
		// 從db讀取
		Account account = findAccount(accountId);
		if (account == null) {
			return result;
		}
		//
		if (account.getCoin() == 0) {
			return result;
		}
		if (accuable && account.getAccuCoin() == 0) {
			return result;
		}
		//
		account.setCoin(0);
		if (accuable) {
			account.setAccuCoin(0);
		}
		//
		// 存db
		int update = update(account);
		// 成功
		if (update > 0) {
			result = true;
			// 發送訊息
			if (result && role != null && sendable) {
				sendCoin(role, account.getCoin(), 0);
			}
		}
		return result;
	}

	/**
	 * 發送帳戶儲值幣回應
	 * 
	 * @param role
	 * @param coin
	 * @param diffCoin
	 */
	public Message sendCoin(Role role, int coin, int diffCoin) {
		Message message = messageService.createMessage(CoreModuleType.ACCOUNT, CoreModuleType.CLIENT,
				CoreMessageType.ACCOUNT_COIN_RESPONSE, role.getId());

		message.addInt(coin);// 目前的儲值
		message.addInt(diffCoin);// 增減的儲值
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送帳戶累計儲值回應
	 * 
	 * @param roleId
	 * @param accuCoin
	 */
	public Message sendAccuCoin(Role role, int accuCoin) {
		Message message = messageService.createMessage(CoreModuleType.ACCOUNT, CoreModuleType.CLIENT,
				CoreMessageType.ACCOUNT_ACCU_COIN_RESPONSE, role.getId());

		message.addInt(accuCoin);// 目前的累計儲值
		//
		messageService.addMessage(message);

		return message;
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 建立帳戶
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public Account createAccount(String accountId, String name) {
		Account result = new AccountImpl();
		result.setId(accountId);
		result.setName(name);
		result.setValid(true);
		// result.setCoin(0);
		// result.setAccuCoin(0);
		return result;
	}

	public void authorize(String accountId, String password) {
		String authKey = checkAccount(accountId, password);
		//
		if (authKey != null) {
			Message message = messageService.createMessage(CoreModuleType.ACCOUNT, CoreModuleType.LOGIN,
					CoreMessageType.LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST);
			message.addString(accountId);
			message.addString(authKey);
			message.addString(getClientIp(accountId));// client ip
			messageService.addMessage(message);
		} else {
			sendAuthorize(ErrorType.ID_PASSWORD_ERROR, accountId, null);
		}
	}

	public String checkAccount(String id, String password) {
		String result = null;
		AccountPo orig = getAccountDao().findAccount(id, password);
		if (orig != null) {
			AuthKey authKey = authKeyService.createAuthKey();
			result = authKey.getId();
		}
		return result;
	}

	public void authorizeFromLogin(String accountId, String authKey) {
		sendAuthorize(ErrorType.NO_ERROR, accountId, authKey);
	}

	public Message sendAuthorize(ErrorType errorType, String accountId, String authKey) {
		Message message = messageService.createMessage(CoreModuleType.ACCOUNT, CoreModuleType.CLIENT,
				CoreMessageType.ACCOUNT_AUTHORIZE_RESPONSE, accountId);

		message.addInt(errorType.getValue());// 0, errorType 錯誤碼

		switch (errorType) {
		// 沒有錯誤,才發其他欄位訊息
		case NO_ERROR:
			message.addString(accountId);
			message.addString(authKey);
			message.addString(getServerIp(accountId));// server ip
			message.addInt(getServerPort(accountId));// server port
			break;

		default:
			break;
		}
		//
		messageService.addMessage(message);

		return message;
	}
}
