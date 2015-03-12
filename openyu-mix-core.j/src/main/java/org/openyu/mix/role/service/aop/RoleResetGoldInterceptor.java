package org.openyu.mix.role.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.Role;

/**
 * 重置金幣攔截器
 */
public class RoleResetGoldInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(RoleResetGoldInterceptor.class);

	@Autowired
	@Qualifier("roleLogService")
	protected transient RoleLogService roleLogService;

	public RoleResetGoldInterceptor() {
	}

	/**
	 * RoleService
	 * 
	 * boolean resetGold(boolean sendable, String roleId, GoldReason
	 * goldReason);
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
			GoldType goldReason = (GoldType) args[2];

			// 改變前金幣
			long beforeGold = 0;
			if (role != null) {
				beforeGold = role.getGold();
			}

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			boolean ret = (Boolean) result;
			//
			if (ret && goldReason != null) {
				roleLogService.recordChangeGold(role, 0, beforeGold,
						ActionType.RESET, goldReason);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
