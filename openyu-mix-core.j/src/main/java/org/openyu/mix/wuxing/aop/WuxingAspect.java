package org.openyu.mix.wuxing.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.wuxing.service.WuxingLogService;
import org.openyu.mix.wuxing.service.WuxingService.PlayResult;
import org.openyu.mix.wuxing.service.WuxingService.PutResult;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class WuxingAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(WuxingAspect.class);

	@Autowired
	@Qualifier("wuxingLogService")
	private transient WuxingLogService wuxingLogService;

	public WuxingAspect() {

	}

	/**
	 * 玩五行
	 * 
	 * WuxingService
	 * 
	 * PlayResult play(boolean sendable, Role role, int playValue)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.wuxing.service.WuxingService.play(..))", returning = "result")
	public void play(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int playValue = (Integer) args[2];
			//
			PlayResult returnValue = (PlayResult) result;
			if (returnValue != null) {
				// 記錄玩的
				wuxingLogService.recordPlay(role, returnValue.getPlayType(), returnValue.getPlayTime(),
						returnValue.getOutcome(), returnValue.getTotalTimes(), returnValue.getSpendGold(),
						returnValue.getSpendItems(), returnValue.getSpendCoin());

				// 記錄開出的結果,有成名的
				wuxingLogService.recordFamous(role, returnValue.getPlayType(), returnValue.getPlayTime(),
						returnValue.getOutcomes());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during play()").toString(), e);
		}
	}

	/**
	 * 單擊獎勵放入包包
	 * 
	 * WuxingService
	 * 
	 * PutResult putOne(boolean sendable, Role role, String itemId, int amount)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.wuxing.service.WuxingService.putOne(..))", returning = "result")
	public void putOne(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String itemId = (String) args[2];
			int amount = (Integer) args[3];
			//
			PutResult returnValue = (PutResult) result;
			//
			if (returnValue != null) {
				wuxingLogService.recordPut(role, PutType.ONE, returnValue.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during putOne()").toString(), e);
		}
	}

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * WuxingService
	 * 
	 * PutResult putAll(boolean sendable, Role role)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.wuxing.service.WuxingService.putAll(..))", returning = "result")
	public void putAll(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			//
			PutResult returnValue = (PutResult) result;
			//
			if (returnValue != null) {
				wuxingLogService.recordPut(role, PutType.ALL, returnValue.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during putAll()").toString(), e);
		}
	}
}
