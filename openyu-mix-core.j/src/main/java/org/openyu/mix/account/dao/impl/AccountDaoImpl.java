package org.openyu.mix.account.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openyu.mix.account.dao.AccountDao;
import org.openyu.mix.account.po.AccountPo;
import org.openyu.mix.account.po.impl.AccountPoImpl;
import org.openyu.mix.app.dao.supporter.AppDaoSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountDaoImpl extends AppDaoSupporter implements AccountDao {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AccountDaoImpl.class);

	private static final String ACCOUNT_PO_NAME = AccountPoImpl.class.getName();

	public AccountDaoImpl() {
	}

	/**
	 * 查詢帳號
	 * 
	 * @param accountId
	 * @return
	 */
	public AccountPo findAccount(String accountId) {
		return findAccount((Locale) null, accountId);
	}

	/**
	 * 查詢帳號
	 * 
	 * @param locale
	 * @param accountId
	 * @return
	 */
	public AccountPo findAccount(Locale locale, String accountId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_PO_NAME + " ");
		hql.append("where 1=1 ");

		// id
		hql.append("and id = :id ");
		params.put("id", accountId);
		//
		return findUniqueByHql(locale, hql, params);
	}

	/**
	 * 查詢是否有效帳號
	 * 
	 * @param valid
	 * @return
	 */
	public List<AccountPo> findAccount(boolean valid) {
		return findAccount((Locale) null, valid);
	}

	/**
	 * 查詢是否有效帳號
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	public List<AccountPo> findAccount(Locale locale, boolean valid) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_PO_NAME + " ");
		hql.append("where 1=1 ");

		// vaild
		if (valid) {
			hql.append("and valid=:valid ");
		} else {
			hql.append("and (valid=:valid ");
			hql.append("or valid is null) ");
		}
		params.put("valid", valid);
		//
		return findByHql(null, locale, hql, params);
	}

	/**
	 * 查詢帳號的儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	public int findCoin(String accountId) {
		int result = 0;
		//
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		// TODO 可改為sql
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_PO_NAME + " ");
		hql.append("where 1=1 ");

		hql.append("and id = :id ");
		params.put("id", accountId);

		AccountPo accountPo = findUniqueByHql(hql.toString(), params);
		if (accountPo != null) {
			result = safeGet(accountPo.getCoin());
		}
		return result;
	}

	/**
	 * 查詢帳號的累計儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	public int findAccuCoin(String accountId) {
		int result = 0;
		//
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		// TODO 可改為sql
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_PO_NAME + " ");
		hql.append("where 1=1 ");

		hql.append("and id = :id ");
		params.put("id", accountId);

		AccountPo accountPo = findUniqueByHql(hql.toString(), params);
		if (accountPo != null) {
			result = safeGet(accountPo.getAccuCoin());
		}
		return result;
	}

	/**
	 * 依帳號id,密碼,查詢帳號
	 * 
	 * @param accountId
	 * @param password
	 * @return
	 */
	public AccountPo findAccount(String accountId, String password) {
		return findAccount(null, accountId, password);
	}

	/**
	 * 依locale,帳號id,密碼,查詢帳號
	 * 
	 * @param locale
	 * @param accountId
	 * @param password
	 * @return
	 */
	public AccountPo findAccount(Locale locale, String accountId,
			String password) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ACCOUNT_PO_NAME + " ");
		hql.append("where 1=1 ");

		// id
		hql.append("and id = :id ");
		params.put("id", accountId);

		// password
		if (password != null) {
			hql.append("and password = :password ");
			params.put("password", password);
		} else {
			hql.append("and (password='' ");
			hql.append("or password is null) ");
		}
		//
		return findUniqueByHql(locale, hql, params);
	}

}
