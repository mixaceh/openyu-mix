package org.openyu.mix.role.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.vo.Role;

/**
 * 增減聲望攔截器
 */
public class RoleChangeFameInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleChangeFameInterceptor.class);

	@Autowired
	@Qualifier("roleLogService")
	protected transient RoleLogService roleLogService;

	public RoleChangeFameInterceptor() {
	}

	/**
	 * RoleService
	 * 
	 * int changeFame(boolean sendable, Role role, int fame);
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int fame = (Integer) args[2];

			// 改變前等級
			int beforeFame = 0;
			if (role != null) {
				beforeFame = role.getFame();
			}

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			int ret = (Integer) result;
			//
			if (ret != 0) {
				roleLogService.recordChangeFame(role, ret, beforeFame);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
