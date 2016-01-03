package org.openyu.mix.chat.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.commons.util.ConfigHelper;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DebugAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(DebugAspect.class);

	public DebugAspect() {

	}

	/**
	 * DebugService
	 * 
	 * cheat(String roleId, String text);
	 */
	@Around("execution(public * org.openyu.mix.chat.service.DebugService.cheat(..))")
	public Object cheat(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			String roleId = (String) args[0];
			String text = (String) args[1];

			// --------------------------------------------------
			// 1.debug=true
			// 2.roleId="TEST_ROLE"+xxx
			final String TEST_ROLE = "TEST_ROLE";
			if (ConfigHelper.isDebug()
					|| (roleId != null && roleId.length() >= 9 && TEST_ROLE.equals(roleId.substring(0, 9)))) {
				result = joinPoint.proceed();
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during cheat()").toString(), e);
		}
		return result;
	}
}
