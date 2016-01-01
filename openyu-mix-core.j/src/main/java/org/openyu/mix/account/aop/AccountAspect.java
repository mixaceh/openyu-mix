package org.openyu.mix.account.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class AccountAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountAspect.class);

	@Autowired
	@Qualifier("accountLogService")
	private transient AccountLogService accountLogService;

	public AccountAspect() {

	}

	@AfterReturning(pointcut = "execution(public * org.openyu.mix.account.service.AccountService.increaseCoin*(..))", returning = "result")
	public void recordIncreaseCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			int coin = (Integer) args[3];
			boolean accuable = (Boolean) args[4];
			CoinType coinReason = (CoinType) args[5];
			//
			int returnValue = safeGet((Integer) result);
			if (returnValue != 0 && coinReason != null) {
				accountLogService.recordIncreaseCoin(accountId, role, returnValue, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during recordIncreaseCoin()").toString(), e);
		}
	}

}
