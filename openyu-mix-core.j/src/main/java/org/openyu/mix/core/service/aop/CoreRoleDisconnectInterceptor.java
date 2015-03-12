package org.openyu.mix.core.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.core.service.CoreLogService;
import org.openyu.mix.role.vo.Role;

/**
 * 角色斷線攔截器
 * 
 * 不要用多緒處理 因threadPool滿了, 當很多role斷線時, 會把threadPool撐爆
 */
public class CoreRoleDisconnectInterceptor extends
		AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -8497969453177168222L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(CoreRoleDisconnectInterceptor.class);

	@Autowired
	@Qualifier("coreLogService")
	protected transient CoreLogService coreLogService;

	public CoreRoleDisconnectInterceptor() {
	}

	public Object invoke(final MethodInvocation methodInvocation,
			final Method method, final Class<?>[] paramTypes,
			final Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			String roleId = (String) args[0];
			Object attatch = args[1];

			// 多緒處理
			// #issue: 不要用多緒處理 因threadPool滿了, 當很多role斷線時, 會把threadPool撐爆
			// threadService.submit(new Runnable() {
			// public void run() {
			// // --------------------------------------------------
			// Object object = null;
			// try {
			// object = methodInvocation.proceed();
			// } catch (Throwable ex) {
			// ex.printStackTrace();
			// }
			// // --------------------------------------------------
			//
			// // --------------------------------------------------
			// // proceed後
			// // --------------------------------------------------
			//
			// // 傳回值
			// Role ret = (Role) object;
			// //
			// if (ret != null) {
			// coreLogService.recordRoleDisconnect(ret);
			// }
			// }
			// });

			// 單緒處理
			// #fix 改用單緒處理角色斷線, 2014/11/10
			result = methodInvocation.proceed();
			// 傳回值
			Role ret = (Role) result;
			//
			if (ret != null) {
				coreLogService.recordRoleDisconnect(ret);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
