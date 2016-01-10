package org.openyu.mix.manor.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService.ActionType;
import org.openyu.mix.manor.service.ManorService.ReclaimResult;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class ManorAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ManorAspect.class);

	@Autowired
	@Qualifier("manorLogService")
	private transient ManorLogService manorLogService;

	public ManorAspect() {

	}

	/**
	 * 開墾
	 * 
	 * ManorService
	 * 
	 * ReclaimResult reclaim(boolean sendable, Role role, int farmIndex, String
	 * landUniqueId);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.manor.service.ManorService.reclaim(..))", returning = "result")
	public void reclaim(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int farmIndex = (Integer) args[2];
			String landUniqueId = (String) args[3];
			//
			ReclaimResult returnValue = (ReclaimResult) result;
			//
			if (returnValue != null) {
				manorLogService.recordReclaim(role, returnValue.getFarmIndex(), returnValue.getLand(),
						returnValue.getSpendGold());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during reclaim()").toString(), e);
		}
	}
}
