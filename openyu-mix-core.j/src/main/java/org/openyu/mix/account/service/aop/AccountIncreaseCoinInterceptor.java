package org.openyu.mix.account.service.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;

/**
 * 增加帳戶的儲值幣攔截器
 */
public class AccountIncreaseCoinInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -6365809971377142215L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountIncreaseCoinInterceptor.class);

	@Autowired
	@Qualifier("accountLogService")
	protected transient AccountLogService accountLogService;

	public AccountIncreaseCoinInterceptor() {
	}

	/**
	 * AccountService
	 * 
	 * int increaseCoin(boolean sendable, String accountId,Role role, int coin,
	 * boolean accuable, CoinReason coinReason);
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			int coin = (Integer) args[3];
			boolean accuable = (Boolean) args[4];
			CoinType coinReason = (CoinType) args[5];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			int ret = (Integer) result;
			//
			if (ret != 0 && coinReason != null) {
				accountLogService.recordIncreaseCoin(accountId, role, ret, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
