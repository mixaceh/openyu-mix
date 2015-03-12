package org.openyu.mix.account.dao;

import java.util.List;

import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface AccountLogDao extends AppLogDao
{
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

}
