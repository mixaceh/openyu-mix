package org.openyu.mix.train.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.service.TrainLogService;
import org.openyu.mix.train.service.TrainService.InspireResult;

/**
 * 訓練鼓舞攔截器
 */
public class TrainInspireInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = -3463487277608619783L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TrainInspireInterceptor.class);

	@Autowired
	@Qualifier("trainLogService")
	protected transient TrainLogService trainLogService;

	public TrainInspireInterceptor() {
	}

	/**
	 * TrainService
	 * 
	 * InspireResult inspire(boolean sendable, Role role);
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

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			InspireResult ret = (InspireResult) result;
			//
			if (ret != null) {
				trainLogService.recordInspire(role, ret.getInspireTime(), ret.getSpendItems(), ret.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
