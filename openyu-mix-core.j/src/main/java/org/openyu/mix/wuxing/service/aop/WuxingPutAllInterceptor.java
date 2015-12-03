package org.openyu.mix.wuxing.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.service.WuxingLogService;
import org.openyu.mix.wuxing.service.WuxingService.PutResult;
import org.openyu.mix.wuxing.service.WuxingService.PutType;

/**
 * 五行所有中獎區獎勵放入包包攔截器
 */
public class WuxingPutAllInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = 4741121694715451195L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingPutAllInterceptor.class);

	@Autowired
	@Qualifier("wuxingLogService")
	protected transient WuxingLogService wuxingLogService;

	public WuxingPutAllInterceptor() {
	}

	/**
	 * WuxingService
	 * 
	 * PutResult putAll(boolean sendable, Role role)
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
			Role role = (Role) args[1];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			PutResult ret = (PutResult) result;
			//
			if (ret != null) {
				wuxingLogService.recordPut(role, PutType.ALL, ret.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
