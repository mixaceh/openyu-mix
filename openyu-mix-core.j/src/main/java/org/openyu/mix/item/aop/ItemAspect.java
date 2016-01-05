package org.openyu.mix.item.aop;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
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
			String uniqueId = (String) args[2];
			int amount = (Integer) args[3];
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
}
