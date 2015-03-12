package org.openyu.mix.manor.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService.ReclaimResult;
import org.openyu.mix.role.vo.Role;

/**
 * 莊園開墾攔截器
 */
public class ManorReclaimInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorReclaimInterceptor.class);

	@Autowired
	@Qualifier("manorLogService")
	protected transient ManorLogService manorLogService;

	public ManorReclaimInterceptor() {
	}

	/**
	 * ManorService
	 * 
	 * ReclaimResult reclaim(boolean sendable, Role role, int farmIndex, String
	 * landUniqueId);
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
			int farmIndex = (Integer) args[2];
			String landUniqueId = (String) args[3];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			ReclaimResult ret = (ReclaimResult) result;
			//
			if (ret != null) {
				manorLogService.recordReclaim(role, ret.getFarmIndex(),
						ret.getLand(), ret.getSpendGold());
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
