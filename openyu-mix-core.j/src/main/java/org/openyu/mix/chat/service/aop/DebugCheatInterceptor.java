package org.openyu.mix.chat.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.commons.util.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 秘技攔截器
 */
public class DebugCheatInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(DebugCheatInterceptor.class);

	public DebugCheatInterceptor() {
	}

	/**
	 * DebugService
	 * 
	 * cheat(String roleId, String text);
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
			String roleId = (String) args[0];
			String text = (String) args[1];

			// --------------------------------------------------
			// 1.debug=true
			// 2.roleId="TEST_ROLE"+xxx
			final String TEST_ROLE = "TEST_ROLE";
			if (ConfigHelper.isDebug()
					|| (roleId != null && roleId.length() >= 9 && TEST_ROLE
							.equals(roleId.substring(0, 9)))) {
				result = methodInvocation.proceed();
			}
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
