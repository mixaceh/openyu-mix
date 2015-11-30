package org.openyu.mix.role.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.vo.Role;

/**
 * 增減等級攔截器
 */
public class RoleChangeLevelInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(RoleChangeLevelInterceptor.class);

	@Autowired
	@Qualifier("roleLogService")
	protected transient RoleLogService roleLogService;

	public RoleChangeLevelInterceptor() {
	}

	/**
	 * RoleService
	 * 
	 * int changeLevel(boolean sendable, Role role, int level);
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
			int level = (Integer) args[2];

			// 改變前等級
			int beforeLevel = 0;
			if (role != null) {
				beforeLevel = role.getLevel();
			}

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			int ret = (Integer) result;

			// 有增減時,會有正負等級差,沒增減時=0
			if (ret != 0) {
				roleLogService.recordChangeLevel(role, ret, beforeLevel);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
