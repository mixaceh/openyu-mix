package org.openyu.mix.account.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccountAspectImpl {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountAspectImpl.class);

	public AccountAspectImpl() {

	}

	@Pointcut(value = "execution(public * org.openyu.mix.account.service.AccountService.sendInit(..))")
	public void sendInit() {
	}

	/**
	 * 發送初始化前
	 * 
	 * @param point
	 */
	@Before(value = "sendInit()")
	public void sendInitBefore(JoinPoint point) {
		LOGGER.info("before calling " + point.getSignature().getName() + "()");
	}

	/**
	 * 發送初始化
	 * 
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "sendInit()")
	public Object sendInitAround(ProceedingJoinPoint point) throws Throwable {
		LOGGER.info("around calling " + point.getSignature().getName() + "()");
		//
		Object result = point.proceed();
		//
		LOGGER.info("around result: " + result);
		return result;
	}

	/**
	 * 發送初始化後
	 * 
	 * @param point
	 * @param result
	 */
	@AfterReturning(value = "sendInit()", returning = "result")
	public void sendInitAfter(JoinPoint point, Object result) {
		LOGGER.info("after at " + point.getTarget().getClass());
	}

	/**
	 * 發送初始化例外
	 * 
	 * @param point
	 * @param ex
	 */
	@AfterThrowing(value = "sendInit()", throwing = "ex")
	public void sendInitThrow(JoinPoint point, Throwable ex) {
		LOGGER.info("ex: " + ex);
	}
}
