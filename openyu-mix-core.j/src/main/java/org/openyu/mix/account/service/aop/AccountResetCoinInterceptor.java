package org.openyu.mix.account.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;

/**
 * 增加帳戶的儲值幣攔截器
 */
public class AccountResetCoinInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AccountResetCoinInterceptor.class);

	@Autowired
	@Qualifier("accountLogService")
	protected transient AccountLogService accountLogService;

	public AccountResetCoinInterceptor() {
	}

	/**
	 * AccountService
	 * 
	 * boolean resetCoin(boolean sendable, String accountId, Role role, boolean
	 * accuable, CoinReason coinReason);
	 */
	public Object invoke(final MethodInvocation methodInvocation,
			final Method method, final Class<?>[] paramTypes,
			final Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			boolean accuable = (Boolean) args[3];
			CoinType coinReason = (CoinType) args[4];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			boolean ret = (Boolean) result;
			//
			if (ret && coinReason != null) {
				accountLogService.recordChangeCoin(accountId, role, 0,
						ActionType.RESET, coinReason);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
