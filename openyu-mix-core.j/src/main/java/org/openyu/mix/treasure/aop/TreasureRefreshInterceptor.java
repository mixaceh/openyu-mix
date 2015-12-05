package org.openyu.mix.treasure.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService.RefreshResult;

/**
 * 祕寶刷新攔截器
 */
public class TreasureRefreshInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = 5321914280602434336L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TreasureRefreshInterceptor.class);

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
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
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
				treasureLogService.recordRefresh(role, ret.getRefreshTime(), ret.getTreasures(), ret.getSpendItems(),
						ret.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
