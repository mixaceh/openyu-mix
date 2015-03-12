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
 * 五行單擊獎勵放入包包攔截器
 */
public class WuxingPutOneInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingPutOneInterceptor.class);

	@Autowired
	@Qualifier("wuxingLogService")
	protected transient WuxingLogService wuxingLogService;

	public WuxingPutOneInterceptor() {
	}

	/**
	 * WuxingService
	 * 
	 * PutResult putOne(boolean sendable, Role role, String itemId, int amount)
	 */
	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String itemId = (String) args[2];
			int amount = (Integer) args[3];

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
				wuxingLogService.recordPut(role, PutType.ONE, ret.getAwards());
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
