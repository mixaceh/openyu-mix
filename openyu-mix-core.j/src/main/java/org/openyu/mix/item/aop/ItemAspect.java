package org.openyu.mix.item.aop;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.ErrorType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class ItemAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemAspect.class);

	@Autowired
	@Qualifier("itemService")
	private transient ItemService itemService;

	@Autowired
	@Qualifier("itemLogService")
	private transient ItemLogService itemLogService;

	public ItemAspect() {

	}

	/**
	 * 道具增加
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItem(boolean sendable, Role role, Item
	 * item)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.increaseItem(..))", returning = "result")
	public void increaseItem(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			Item item = (Item) args[2];
			//
			@SuppressWarnings("unchecked")
			List<IncreaseItemResult> returnValue = (List<IncreaseItemResult>) result;
			//
			if (returnValue.size() > 0) {
				itemLogService.recordIncreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during increaseItem()").toString(), e);
		}
	}

	/**
	 * 道具增加, with itemId
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItemWithItemId(boolean sendable, Role
	 * role, String itemId, int amount)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.increaseItemWithItemId(..))", returning = "result")
	public void increaseItemWithItemId(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String itemId = (String) args[2];
			int amount = (Integer) args[3];
			//
			@SuppressWarnings("unchecked")
			List<IncreaseItemResult> returnValue = (List<IncreaseItemResult>) result;
			//
			if (returnValue.size() > 0) {
				itemLogService.recordIncreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during increaseItemWithItemId()").toString(), e);
		}
	}

	/**
	 * 增加多個道具
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItems(boolean sendable, Role role,
	 * Map<String, Integer> items)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.increaseItems(..))", returning = "result")
	public void increaseItems(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			@SuppressWarnings("unchecked")
			Map<String, Integer> items = (Map<String, Integer>) args[2];
			//
			@SuppressWarnings("unchecked")
			List<IncreaseItemResult> returnValue = (List<IncreaseItemResult>) result;
			//
			if (returnValue.size() > 0) {
				itemLogService.recordIncreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during increaseItems()").toString(), e);
		}
	}

	/**
	 * 道具減少
	 * 
	 * ItemService
	 * 
	 * List<DecreaseItemResult> decreaseItem(boolean sendable, Role role, Item
	 * item)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.decreaseItem(..))", returning = "result")
	public void decreaseItem(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			Item item = (Item) args[2];
			//
			@SuppressWarnings("unchecked")
			List<DecreaseItemResult> returnValue = (List<DecreaseItemResult>) result;
			//
			if (returnValue.size() > 0) {
				itemLogService.recordDecreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during decreaseItem()").toString(), e);
		}
	}

	/**
	 * 道具減少, with itemId
	 * 
	 * ItemService
	 * 
	 * List<DecreaseItemResult> decreaseItemWithItemId(boolean sendable, Role
	 * role, String itemId, int amount)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.decreaseItemWithItemId(..))", returning = "result")
	public void decreaseItemWithItemId(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String itemId = (String) args[2];
			int amount = (Integer) args[3];
			//
			List<DecreaseItemResult> returnValue = (List<DecreaseItemResult>) result;
			//
			if (returnValue.size() > 0) {
				itemLogService.recordDecreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during decreaseItemWithItemId()").toString(), e);
		}
	}

	/**
	 * 道具減少, with uniqueId
	 * 
	 * ItemService
	 * 
	 * DecreaseItemResult decreaseItemWithUniqueId(boolean sendable, Role role,
	 * String uniqueId, int amount)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.item.service.ItemService.decreaseItemWithUniqueId(..))", returning = "result")
	public void decreaseItemWithUniqueId(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String targetId = (String) args[2];
			Item item = (Item) args[3];// 消耗的道具
			// 原道具
			Item origItem = itemService.getItem(role, targetId);
			// 強化前的道具
			Item beforeItem = origItem.clone(origItem);

			//
			DecreaseItemResult returnValue = (DecreaseItemResult) result;
			//
			if (returnValue != null) {
				itemLogService.recordDecreaseItem(role, ActionType.BAG, returnValue);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during decreaseItemWithUniqueId()").toString(), e);
		}
	}

	/**
	 * 改變強化
	 * 
	 * ItemService
	 * 
	 * int changeEnhance(boolean sendable, Role role, Item item, int
	 * enhanceValue);
	 */
	@Around("execution(public * org.openyu.mix.item.service.ItemService.changeEnhance(..))")
	public Object changeEnhance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			Item item = (Item) args[2];// 欲強化的道具
			int enhanceValue = (Integer) args[3];// 增減的強化

			// 強化前的道具
			Item beforeItem = clone(item);
			result = joinPoint.proceed();
			//
			int returnValue = safeGet((Integer) result);
			//
			if (returnValue != 0) {
				itemLogService.recordChangeEnhance(role, ActionType.CHANGE_ENHANCE, beforeItem, item, null);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during changeEnhance()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 使用強化防具道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceArmorThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	@Around("execution(public * org.openyu.mix.item.service.ItemService.useEnhanceArmorThing(..))")
	public Object useEnhanceArmorThing(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			result = doUseEnhance(joinPoint);
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during useEnhanceArmorThing()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 使用強化武器道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceWeaponThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	@Around("execution(public * org.openyu.mix.item.service.ItemService.useEnhanceWeaponThing(..))")
	public Object useEnhanceWeaponThing(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			result = doUseEnhance(joinPoint);
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during useEnhanceWeaponThing()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 使用強化土地道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceLandThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	@Around("execution(public * org.openyu.mix.item.service.ItemService.useEnhanceLandThing(..))")
	public Object useEnhanceLandThing(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		try {
			result = doUseEnhance(joinPoint);
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during useEnhanceLandThing()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 使用強化
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	protected Object doUseEnhance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//
		String method = joinPoint.getSignature().getName();
		// 參數
		Object[] args = joinPoint.getArgs();
		boolean sendable = (Boolean) args[0];
		Role role = (Role) args[1];
		String targetId = (String) args[2];
		Item item = (Item) args[3];// 消耗的道具
		// 原道具
		Item origItem = itemService.getItem(role, targetId);
		// 強化前的道具
		Item beforeItem = origItem.clone(origItem);
		//
		result = joinPoint.proceed();
		//
		ErrorType returnValue = (ErrorType) result;
		//
		if (ErrorType.NO_ERROR == returnValue) {
			itemLogService.recordChangeEnhance(role, ActionType.USE_ENHANCE, beforeItem, origItem, item);
		}
		return result;
	}
}
