package org.openyu.mix.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.core.service.CoreLogService;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class CoreAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(CoreAspect.class);

	@Autowired
	@Qualifier("coreLogService")
	private transient CoreLogService coreLogService;

	public CoreAspect() {

	}

	/**
	 * 角色連線
	 * 
	 * CoreService
	 * 
	 * <T> Role roleConnect(String roleId, T attatch);
	 */
	@Around("execution(public * org.openyu.mix.core.service.CoreService.roleConnect(..))")
	public void roleConnect(final ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			String roleId = (String) args[0];
			Object attatch = args[1];
			// 多緒處理
			threadService.submit(new Runnable() {
				public void run() {
					// --------------------------------------------------
					Object object = null;
					try {
						object = joinPoint.proceed();
					} catch (Throwable e) {
						LOGGER.error(new StringBuilder("Exception encountered during run()").toString(), e);
					}
					// --------------------------------------------------
					// proceed後
					// --------------------------------------------------

					// 傳回值
					Role returnValue = (Role) object;
					//
					if (returnValue != null) {
						coreLogService.recordRoleConnect(returnValue);
					}
				}
			});
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during roleConnect()").toString(), e);
		}
	}

}
