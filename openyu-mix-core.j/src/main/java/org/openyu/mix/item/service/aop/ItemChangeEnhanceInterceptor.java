package org.openyu.mix.item.service.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;

/**
 * 增減強化等級攔截器
 */
public class ItemChangeEnhanceInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -8227167285281213446L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemChangeEnhanceInterceptor.class);

	@Autowired
	@Qualifier("itemLogService")
	protected transient ItemLogService itemLogService;

	public ItemChangeEnhanceInterceptor() {
	}

	/**
	 * ItemService
	 * 
	 * int changeEnhance(boolean sendable, Role role, Item item, int
	 * enhanceValue);
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			Item item = (Item) args[2];// 欲強化的道具
			int enhanceValue = (Integer) args[3];// 增減的強化

			// 強化前的道具
			Item beforeItem = clone(item);

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			int ret = (Integer) result;
			//
			if (ret != 0) {
				itemLogService.recordChangeEnhance(role, ActionType.CHANGE_ENHANCE, beforeItem, item, null);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
