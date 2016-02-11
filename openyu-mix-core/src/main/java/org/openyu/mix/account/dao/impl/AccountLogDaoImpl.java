package org.openyu.mix.account.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.account.dao.AccountLogDao;
import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.account.log.impl.AccountCoinLogImpl;
import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountLogDaoImpl extends AppLogDaoSupporter implements
		AccountLogDao {

	private static final long serialVersionUID = -1459806965587983077L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AccountLogDaoImpl.class);

	/**
	 * 帳戶儲值增減log
	 */
	private static final String ACCOUNT_COIN_LOG_PO_NAME = AccountCoinLogImpl.class
			.getName();

	public AccountLogDaoImpl() {
	}

	// --------------------------------------------------
	// AccountCoinLog
	// --------------------------------------------------
	/**
	 * 查詢帳戶儲值增減log
	 * 
	 * @param accountId
	 * @return
	 */
	public List<AccountCoinLog> findAccountCoinLog(String accountId) {

		return findAccountCoinLog(null, accountId);
	}

	/**
	 * 分頁查詢帳戶儲值增減log
	 * 
	 * @param inquiry
	 * @param accountId
	 * @return
	 */
	public List<AccountCoinLog> findAccountCoinLog(Inquiry inquiry,
			String accountId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_COIN_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// accountId
		hql.append("and accountId = :accountId ");
		params.put("accountId", accountId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除帳戶儲值增減log
	 * 
	 * @param accountId
	 * @return
	 */
	public int deleteAccountCoinLog(String accountId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ACCOUNT_COIN_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// accountId
		hql.append("and accountId = :accountId ");
		params.put("accountId", accountId);
		//
		return executeByHql(hql, params);
	}

}
