package org.openyu.mix.role.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class RoleAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleAspect.class);

	@Autowired
	@Qualifier("roleLogService")
	private transient RoleLogService roleLogService;

	public RoleAspect() {

	}

	/**
	 * 增加金幣
	 * 
	 * RoleService
	 * 
	 * long increaseGold(boolean sendable, Role role, long gold, GoldReason
	 * goldReason);
	 */
	@Around("execution(public * org.openyu.mix.role.service.RoleService.increaseGold(..))")
	public Object increaseGold(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			long gold = (Long) args[2];
			GoldType goldReason = (GoldType) args[3];

			// 改變前金幣
			long beforeGold = 0;
			if (role != null) {
				beforeGold = role.getGold();
			}

			result = joinPoint.proceed();
			//
			long returnValue = safeGet((Long) result);
			//
			if (returnValue != 0 && goldReason != null) {
				roleLogService.recordIncreaseGold(role, returnValue, beforeGold, goldReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during increaseGold()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 減少金幣
	 * 
	 * RoleService
	 * 
	 * long decreaseGold(boolean sendable, Role role, long gold, GoldReason
	 * goldReason);
	 */
	@Around("execution(public * org.openyu.mix.role.service.RoleService.decreaseGold(..))")
	public Object decreaseGold(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			long gold = (Long) args[2];
			GoldType goldReason = (GoldType) args[3];

			// 改變前金幣
			long beforeGold = 0;
			if (role != null) {
				beforeGold = role.getGold();
			}

			result = joinPoint.proceed();
			//
			long returnValue = safeGet((Long) result);
			//
			if (returnValue != 0 && goldReason != null) {
				roleLogService.recordDecreaseGold(role, returnValue, beforeGold, goldReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during decreaseGold()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 增減金幣
	 * 
	 * RoleService
	 * 
	 * long changeGold(boolean sendable, Role role, long gold, GoldAction
	 * goldAction, GoldReason goldReason);
	 */
	@Around("execution(public * org.openyu.mix.role.service.RoleService.changeGold(..))")
	public Object changeGold(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			long gold = (Long) args[2];
			ActionType goldAction = (ActionType) args[3];
			GoldType goldReason = (GoldType) args[4];

			// 改變前金幣
			long beforeGold = 0;
			if (role != null) {
				beforeGold = role.getGold();
			}

			result = joinPoint.proceed();
			//
			long returnValue = safeGet((Long) result);
			//
			if (returnValue != 0 && goldAction != null && goldReason != null) {
				roleLogService.recordChangeGold(role, returnValue, beforeGold, goldAction, goldReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during changeGold()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 增減等級
	 * 
	 * RoleService
	 * 
	 * int changeLevel(boolean sendable, Role role, int level);
	 */
	@Around("execution(public * org.openyu.mix.role.service.RoleService.changeLevel(..))")
	public Object changeLevel(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int level = (Integer) args[2];

			// 改變前等級
			int beforeLevel = 0;
			if (role != null) {
				beforeLevel = role.getLevel();
			}

			result = joinPoint.proceed();
			//
			int returnValue = safeGet((Integer) result);
			//
			if (returnValue != 0) {
				roleLogService.recordChangeLevel(role, returnValue, beforeLevel);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during changeLevel()").toString(), e);
		}
		//
		return result;
	}
	
	/**
	 * 增減等級
	 * 
	 * RoleService
	 * 
	 * int changeLevel(boolean sendable, Role role, int level);
	 */
	@Around("execution(public * org.openyu.mix.role.service.RoleService.changeLevel(..))")
	public Object changeLevel(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int level = (Integer) args[2];

			// 改變前等級
			int beforeLevel = 0;
			if (role != null) {
				beforeLevel = role.getLevel();
			}

			result = joinPoint.proceed();
			//
			int returnValue = safeGet((Integer) result);
			//
			if (returnValue != 0) {
				roleLogService.recordChangeLevel(role, returnValue, beforeLevel);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during changeLevel()").toString(), e);
		}
		//
		return result;
	}
}
