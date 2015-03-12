package org.openyu.mix.item.service.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
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
public class ItemDecreaseItemInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemDecreaseItemInterceptor.class);

	@Autowired
	@Qualifier("itemLogService")
	protected transient ItemLogService itemLogService;

	/**
	 * 道具減少
	 * 
	 * DecreaseItemResult decreaseItem(boolean sendable, Role role, Item item)
	 */
	private static final Method decreaseItem = ClassHelper.getDeclaredMethod(
			ItemService.class, "decreaseItem", new Class[] { boolean.class,
					Role.class, Item.class });

	/**
	 * 道具減少,by itemId
	 * 
	 * DecreaseItemResult decreaseItem(boolean sendable, Role role, String
	 * itemId, int amount)
	 */
	private static final Method decreaseItemByItemId = ClassHelper
			.getDeclaredMethod(ItemService.class, "decreaseItem", new Class[] {
					boolean.class, Role.class, String.class, int.class });

	/**
	 * 道具減少,by uniqueId
	 * 
	 * DecreaseItemResult decreaseItemByUniqueId(boolean sendable, Role role,
	 * String uniqueId, int amount)
	 */
	private static final Method decreaseItemByUniqueId = ClassHelper
			.getDeclaredMethod(ItemService.class, "decreaseItemByUniqueId",
					new Class[] { boolean.class, Role.class, String.class,
							int.class });

	public ItemDecreaseItemInterceptor() {
	}

	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------

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
					itemLogService
							.recordDecreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(decreaseItemByItemId)) {
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
					itemLogService
							.recordDecreaseItem(role, ActionType.BAG, ret);
				}
			} else if (method.equals(decreaseItemByUniqueId)) {
				// 傳回值
				DecreaseItemResult ret = (DecreaseItemResult) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				String uniqueId = (String) args[2];
				int amount = (Integer) args[3];
				//
				if (ret != null) {
					itemLogService
							.recordDecreaseItem(role, ActionType.BAG, ret);
				}
			} else {
				LOGGER.error(method.getName() + " not matched to record");
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
