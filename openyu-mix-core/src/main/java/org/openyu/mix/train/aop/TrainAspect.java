package org.openyu.mix.train.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.train.service.TrainLogService;
import org.openyu.mix.train.service.TrainService.ActionType;
import org.openyu.mix.train.service.TrainService.InspireResult;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class TrainAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TrainAspect.class);

	@Autowired
	@Qualifier("trainLogService")
	private transient TrainLogService trainLogService;

	public TrainAspect() {

	}

	/**
	 * 鼓舞訓練
	 * 
	 * TrainService
	 * 
	 * InspireResult inspire(boolean sendable, Role role);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.train.service.TrainService.inspire(..))", returning = "result")
	public void resetCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			//
			InspireResult returnValue = (InspireResult) result;
			//
			if (returnValue != null) {
				trainLogService.recordInspire(role, returnValue.getInspireTime(), returnValue.getSpendItems(),
						returnValue.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during inspire()").toString(), e);
		}
	}
}
