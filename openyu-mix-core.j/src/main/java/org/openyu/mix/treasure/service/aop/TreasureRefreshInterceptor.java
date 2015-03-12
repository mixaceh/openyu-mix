package org.openyu.mix.treasure.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService.RefreshResult;

/**
 * 祕寶刷新攔截器
 */
public class TreasureRefreshInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TreasureRefreshInterceptor.class);

	@Autowired
	@Qualifier("treasureLogService")
	protected transient TreasureLogService treasureLogService;

	public TreasureRefreshInterceptor() {
	}

	/**
	 * TreasureService
	 * 
	 * RefreshResult refresh(boolean sendable, Role role)
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

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			RefreshResult ret = (RefreshResult) result;
			//
			if (ret != null) {
				treasureLogService.recordRefresh(role, ret.getRefreshTime(),
						ret.getTreasures(), ret.getSpendItems(),
						ret.getSpendCoin());
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
