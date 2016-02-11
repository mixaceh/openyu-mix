package org.openyu.mix.account.service;

import java.util.List;

import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

/**
 * 帳戶日誌服務
 */
public interface AccountLogService extends AppLogService {
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// AccountCoinLog
	// --------------------------------------------------
	/**
	 * 查詢帳戶儲值增減log
	 * 
	 * @param accountId
	 * @return
	 */
	List<AccountCoinLog> findAccountCoinLog(String accountId);

	/**
	 * 分頁查詢帳戶儲值增減log
	 * 
	 * @param inquiry
	 * @param accountId
	 * @return
	 */
	List<AccountCoinLog> findAccountCoinLog(Inquiry inquiry, String accountId);

	/**
	 * 刪除帳戶儲值增減log
	 * 
	 * @param accountId
	 * @return
	 */
	int deleteAccountCoinLog(String accountId);

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
	void recordIncreaseCoin(String accountId, Role role, int coin,
			CoinType coinReason);

	/**
	 * 紀錄減少帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param role
	 * @param coin
	 * @param coinReason
	 */
	void recordDecreaseCoin(String accountId, Role role, int coin,
			CoinType coinReason);

	/**
	 * 紀錄增減帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param role
	 * @param coin
	 * @param coinAction
	 * @param coinReason
	 */
	void recordChangeCoin(String accountId, Role role, int coin,
			ActionType coinAction, CoinType coinReason);

}
