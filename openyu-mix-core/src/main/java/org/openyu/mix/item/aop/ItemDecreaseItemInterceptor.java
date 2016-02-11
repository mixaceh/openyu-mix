package org.openyu.mix.item.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.ClassHelper;

/**
 * 道具減少攔截器
 */
@Deprecated
public class ItemDecreaseItemInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = -6372664297794476330L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemDecreaseItemInterceptor.class);

	@Autowired
	@Qualifier("itemLogService")
	protected transient ItemLogService itemLogService;

	/**
	 * 道具減少
	 * 
	 * ItemService
	 * 
	 * List<DecreaseItemResult>  decreaseItem(boolean sendable, Role role, Item item)
	 */
	private static final Method decreaseItem = ClassHelper.getDeclaredMethod(ItemService.class, "decreaseItem",
			new Class[] { boolean.class, Role.class, Item.class });

	/**
	 * 道具減少, with itemId
	 * 
	 * ItemService
	 * 
	 * List<DecreaseItemResult>  decreaseItemWithItemId(boolean sendable, Role role,
	 * String itemId, int amount)
	 */
	private static final Method decreaseItemWithItemId = ClassHelper.getDeclaredMethod(ItemService.class,
			"decreaseItemWithItemId", new Class[] { boolean.class, Role.class, String.class, int.class });

	/**
	 * 道具減少, with uniqueId
	 * 
	 * ItemService
	 * 
	 * DecreaseItemResult decreaseItemWithUniqueId(boolean sendable, Role role,
	 * String uniqueId, int amount)
	 */
	private static final Method decreaseItemWithUniqueId = ClassHelper.getDeclaredMethod(ItemService.class,
			"decreaseItemWithUniqueId", new Class[] { boolean.class, Role.class, String.class, int.class });

	public ItemDecreaseItemInterceptor() {
	}

	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
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

			if (method.equals(decreaseItem)) {
				// 傳回值
				@SuppressWarnings("unchecked")
				List<DecreaseItemResult> ret = (List<DecreaseItemResult>) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				Item item = (Item) args[2];
				//
				if (ret.size() > 0) {
					itemLogService.recordDecreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(decreaseItemWithItemId)) {
				// 傳回值
				@SuppressWarnings("unchecked")
				List<DecreaseItemResult> ret = (List<DecreaseItemResult>) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				String itemId = (String) args[2];
				int amount = (Integer) args[3];
				//
				if (ret.size() > 0) {
					itemLogService.recordDecreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(decreaseItemWithUniqueId)) {
				// 傳回值
				DecreaseItemResult ret = (DecreaseItemResult) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				String uniqueId = (String) args[2];
				int amount = (Integer) args[3];
				//
				if (ret != null) {
					itemLogService.recordDecreaseItem(role, ActionType.BAG, ret);
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
