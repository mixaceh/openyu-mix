package org.openyu.mix.account.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;

/**
 * 增減帳戶的儲值幣攔截器
 */
public class AccountChangeCoinInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -6116656191642710414L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountChangeCoinInterceptor.class);

	@Autowired
	@Qualifier("accountLogService")
	protected transient AccountLogService accountLogService;

	public AccountChangeCoinInterceptor() {
	}

	/**
	 * AccountService
	 * 
	 * int changeCoin(boolean sendable, String accountId, Role role, int coin,
	 * boolean accuable, CoinAction coinAction, CoinReason coinReason);
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
			ActionType coinAction = (ActionType) args[5];
			CoinType coinReason = (CoinType) args[6];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			int ret = (Integer) result;
			//
			if (ret != 0 && coinAction != null && coinReason != null) {
				accountLogService.recordChangeCoin(accountId, role, ret, coinAction, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
