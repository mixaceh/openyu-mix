package org.openyu.mix.role.aop;


import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.Role;

/**
 * 增加金幣攔截器
 */
@Deprecated
public class RoleChangeGoldInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = -3698660193518015888L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(RoleChangeGoldInterceptor.class);

	@Autowired
	@Qualifier("roleLogService")
	protected transient RoleLogService roleLogService;

	public RoleChangeGoldInterceptor() {
	}

	/**
	 * RoleService
	 * 
	 * long changeGold(boolean sendable, Role role, long gold, GoldAction
	 * goldAction, GoldReason goldReason);
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
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

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			long ret = (Long) result;
			//
			if (ret != 0 && goldAction != null && goldReason != null) {
				roleLogService.recordChangeGold(role, ret, beforeGold,
						goldAction, goldReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}
}
