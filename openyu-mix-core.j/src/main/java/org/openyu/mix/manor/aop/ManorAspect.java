package org.openyu.mix.manor.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService.ReclaimResult;
import org.openyu.mix.manor.service.ManorService.DisuseResult;
import org.openyu.mix.manor.service.ManorService.CultureResult;
import org.openyu.mix.manor.service.ManorService.CultureAllResult;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class ManorAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ManorAspect.class);

	@Autowired
	@Qualifier("manorLogService")
	private transient ManorLogService manorLogService;

	public ManorAspect() {

	}

	/**
	 * 開墾
	 * 
	 * ManorService
	 * 
	 * ReclaimResult reclaim(boolean sendable, Role role, int farmIndex, String
	 * landUniqueId);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.manor.service.ManorService.reclaim(..))", returning = "result")
	public void reclaim(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int farmIndex = (Integer) args[2];
			String landUniqueId = (String) args[3];
			//
			ReclaimResult returnValue = (ReclaimResult) result;
			//
			if (returnValue != null) {
				manorLogService.recordReclaim(role, returnValue.getFarmIndex(), returnValue.getLand(),
						returnValue.getSpendGold());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during reclaim()").toString(), e);
		}
	}

	/**
	 * 休耕
	 * 
	 * ManorService
	 * 
	 * DisuseResult disuse(boolean sendable, Role role, int farmIndex);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.manor.service.ManorService.disuse(..))", returning = "result")
	public void disuse(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int farmIndex = (Integer) args[2];
			//
			DisuseResult returnValue = (DisuseResult) result;
			//
			if (returnValue != null) {
				manorLogService.recordDisuse(role, returnValue.getFarmIndex(), returnValue.getLand(),
						returnValue.getSpendGold());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during disuse()").toString(), e);
		}
	}

	/**
	 * 耕種
	 * 
	 * ManorService
	 * 
	 * CultureResult culture(boolean sendable, Role role, int cultureValue, int
	 * farmIndex, int gridIndex, String seedUniqueId);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.manor.service.ManorService.culture(..))", returning = "result")
	public void culture(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int cultureValue = (Integer) args[2];
			int farmIndex = (Integer) args[3];
			int gridIndex = (Integer) args[4];
			String seedUniqueId = (String) args[5];
			//
			CultureResult returnValue = (CultureResult) result;
			//
			if (returnValue != null) {
				manorLogService.recordCulture(role, returnValue.getCultureType(), farmIndex, gridIndex,
						returnValue.getSeed(), returnValue.getSpendItems(), returnValue.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during culture()").toString(), e);
		}
	}

	/**
	 * 耕種
	 * 
	 * ManorService
	 * 
	 * CultureResult culture(boolean sendable, Role role, int cultureValue, int
	 * farmIndex, int gridIndex, String seedUniqueId);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.manor.service.ManorService.cultureAll(..))", returning = "result")
	public void cultureAll(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int cultureValue = (Integer) args[2];
			//
			CultureAllResult returnValue = (CultureAllResult) result;
			//
			if (returnValue != null) {
				// TODO 有分不同耕種類型
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during cultureAll()").toString(), e);
		}
	}
}
