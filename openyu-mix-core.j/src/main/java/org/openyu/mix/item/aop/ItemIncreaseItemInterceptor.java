package org.openyu.mix.item.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.ClassHelper;

/**
 * 道具增加攔截器
 */
public class ItemIncreaseItemInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -9002923759003474302L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemIncreaseItemInterceptor.class);

	@Autowired
	@Qualifier("itemLogService")
	protected transient ItemLogService itemLogService;

	/**
	 * 道具增加
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItem(boolean sendable, Role role, Item
	 * item)
	 */
	private static final Method increaseItem = ClassHelper.getDeclaredMethod(ItemService.class, "increaseItem",
			new Class[] { boolean.class, Role.class, Item.class });

	/**
	 * 道具增加,by itemId
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItem(boolean sendable, Role role, String
	 * itemId, int amount)
	 */
	private static final Method increaseItemByItemId = ClassHelper.getDeclaredMethod(ItemService.class, "increaseItem",
			new Class[] { boolean.class, Role.class, String.class, int.class });

	/**
	 * 增加多個道具,by items
	 * 
	 * ItemService
	 * 
	 * List<IncreaseItemResult> increaseItem(boolean sendable, Role role,
	 * Map<String, Integer> items)
	 */
	private static final Method increaseItemByItems = ClassHelper.getDeclaredMethod(ItemService.class, "increaseItem",
			new Class[] { boolean.class, Role.class, Map.class });

	public ItemIncreaseItemInterceptor() {
	}

	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			Method method = methodInvocation.getMethod();
			Object[] args = methodInvocation.getArguments();

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			if (method.equals(increaseItem)) {
				// 傳回值
				@SuppressWarnings("unchecked")
				List<IncreaseItemResult> ret = (List<IncreaseItemResult>) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				Item item = (Item) args[2];
				//
				if (ret.size() > 0) {
					itemLogService.recordIncreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(increaseItemByItemId)) {
				// 傳回值
				@SuppressWarnings("unchecked")
				List<IncreaseItemResult> ret = (List<IncreaseItemResult>) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				String itemId = (String) args[2];
				int amount = (Integer) args[3];
				//
				if (ret.size() > 0) {
					itemLogService.recordIncreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(increaseItemByItems)) {
				// 傳回值
				@SuppressWarnings("unchecked")
				List<IncreaseItemResult> ret = (List<IncreaseItemResult>) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				@SuppressWarnings("unchecked")
				Map<String, Integer> items = (Map<String, Integer>) args[2];
				//
				if (ret.size() > 0) {
					itemLogService.recordIncreaseItem(role, ActionType.BAG, ret);
				}
			} else {
				LOGGER.error(method.getName() + " not matched to record");
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
