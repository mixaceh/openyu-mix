package org.openyu.mix.account.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.vo.Role;

/**
 * 減少帳戶的儲值幣攔截器
 */
@Deprecated
public class AccountDecreaseCoinInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = 2994293721064879272L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountDecreaseCoinInterceptor.class);

	@Autowired
	@Qualifier("accountLogService")
	protected transient AccountLogService accountLogService;

	public AccountDecreaseCoinInterceptor() {
	}

	/**
	 * AccountService
	 * 
	 * int decreaseCoin(boolean sendable, String accountId, Role role, int coin,
	 * CoinReason coinReason);
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
			CoinType coinReason = (CoinType) args[4];

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
				accountLogService.recordDecreaseCoin(accountId, role, ret, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
