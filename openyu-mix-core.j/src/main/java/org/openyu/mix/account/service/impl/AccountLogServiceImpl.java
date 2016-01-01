package org.openyu.mix.account.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.dao.AccountLogDao;
import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.account.log.impl.AccountCoinLogImpl;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.anno.LogTx;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

/**
 * 帳戶日誌服務
 */
public class AccountLogServiceImpl extends AppLogServiceSupporter implements AccountLogService {

	private static final long serialVersionUID = 656386026958140296L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountLogServiceImpl.class);

	public AccountLogServiceImpl() {
	}

	public AccountLogDao getAccountLogDao() {
		return (AccountLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("accountLogDao")
	public void setAccountLogDao(AccountLogDao accountLogDao) {
		setCommonDao(accountLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The AccountLogDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// AccountCoinLog
	// --------------------------------------------------
	public List<AccountCoinLog> findAccountCoinLog(String accountId) {
		return getAccountLogDao().findAccountCoinLog(accountId);
	}

	public List<AccountCoinLog> findAccountCoinLog(Inquiry inquiry, String accountId) {
		return getAccountLogDao().findAccountCoinLog(inquiry, accountId);
	}

	public int deleteAccountCoinLog(String accountId) {
		return getAccountLogDao().deleteAccountCoinLog(accountId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 紀錄增加帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param role
	 * @param coin
	 * @param coinReason
	 */
	@LogTx
	public void recordIncreaseCoin(String accountId, Role role, int coin, CoinType coinReason) {
		recordChangeCoin(accountId, role, coin, ActionType.INCREASE, coinReason);
	}

	/**
	 * 紀錄減少帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param role
	 * @param coin
	 * @param coinReason
	 */
	@LogTx
	public void recordDecreaseCoin(String accountId, Role role, int coin, CoinType coinReason) {
		recordChangeCoin(accountId, role, -1 * coin, ActionType.DECREASE, coinReason);
	}

	/**
	 * 紀錄增減帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param role
	 * @param coin
	 * @param coinAction
	 * @param coinReason
	 */
	@LogTx
	public void recordChangeCoin(String accountId, Role role, int coin, ActionType coinAction, CoinType coinReason) {
		AccountCoinLog log = new AccountCoinLogImpl();
		// 紀錄角色相關資訊
		recordRole(role, log);
		//
		log.setActionType(coinAction);
		log.setCoinType(coinReason);
		//
		switch (coinAction) {
		case INCREASE: {
			if (coin < 0) {
				coin = Math.abs(coin);
			}
			break;
		}
		case DECREASE: {
			if (coin > 0) {
				coin = -1 * coin;
			}
			break;
		}
		default: {
			break;
		}
		}
		log.setCoin(coin);
		//
		offerInsert(log);
	}
}
