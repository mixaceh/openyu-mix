package org.openyu.mix.core.service.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.core.service.CoreLogService;
import org.openyu.mix.role.vo.Role;

/**
 * 角色連線攔截器
 */
public class CoreRoleConnectInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = 8460810589591167479L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(CoreRoleConnectInterceptor.class);

	@Autowired
	@Qualifier("coreLogService")
	protected transient CoreLogService coreLogService;

	public CoreRoleConnectInterceptor() {
	}

	protected Object doInvoke(final MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
			String roleId = (String) args[0];
			Object attatch = args[1];
			// 多緒處理
			threadService.submit(new Runnable() {
				public void run() {
					// --------------------------------------------------
					Object object = null;
					try {
						object = methodInvocation.proceed();
					} catch (Throwable e) {
						LOGGER.error(new StringBuilder("Exception encountered during run()").toString(), e);
					}
					// --------------------------------------------------

					// --------------------------------------------------
					// proceed後
					// --------------------------------------------------

					// 傳回值
					Role ret = (Role) object;
					//
					if (ret != null) {
						coreLogService.recordRoleConnect(ret);
					}
				}
			});
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
