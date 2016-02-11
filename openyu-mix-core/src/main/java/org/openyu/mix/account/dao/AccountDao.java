package org.openyu.mix.account.dao;

import java.util.List;
import java.util.Locale;

import org.openyu.mix.account.po.AccountPo;
import org.openyu.mix.app.dao.AppDao;

public interface AccountDao extends AppDao
{

	/**
	 * 查詢帳號
	 * 
	 * @param accountId
	 * @return
	 */
	AccountPo findAccount(String accountId);

	/**
	 * 查詢帳號
	 * 
	 * @param locale
	 * @param accountId
	 * @return
	 */
	AccountPo findAccount(Locale locale, String accountId);

	/**
	 * 查詢是否有效帳號
	 * 
	 * @param valid
	 * @return
	 */
	List<AccountPo> findAccount(boolean valid);

	/**
	 * 查詢是否有效帳號
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	List<AccountPo> findAccount(Locale locale, boolean valid);

	/**
	 * 查詢帳號的儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	int findCoin(String accountId);

	/**
	 * 查詢帳號的累計儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	int findAccuCoin(String accountId);

	/**
	 * 依帳號id,密碼,查詢帳號
	 * @param accountId
	 * @param password
	 * @return
	 */
	AccountPo findAccount(String accountId, String password);

	/**
	 * 依locale,帳號id,密碼,查詢帳號
	 * @param locale
	 * @param accountId
	 * @param password
	 * @return
	 */
	AccountPo findAccount(Locale locale, String accountId, String password);

}
