package org.openyu.mix.chat.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.openyu.commons.util.ConfigHelper;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 秘技攔截器
 */
public class DebugCheatInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(DebugCheatInterceptor.class);

	public DebugCheatInterceptor() {
	}

	/**
	 * DebugService
	 * 
	 * cheat(String roleId, String text);
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			Object[] args = methodInvocation.getArguments();
			// 參數
			String roleId = (String) args[0];
			String text = (String) args[1];

			// --------------------------------------------------
			// 1.debug=true
			// 2.roleId="TEST_ROLE"+xxx
			final String TEST_ROLE = "TEST_ROLE";
			if (ConfigHelper.isDebug()
					|| (roleId != null && roleId.length() >= 9 && TEST_ROLE.equals(roleId.substring(0, 9)))) {
				result = methodInvocation.proceed();
			}
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
