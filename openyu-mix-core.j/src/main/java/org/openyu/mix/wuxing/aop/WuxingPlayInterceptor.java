package org.openyu.mix.wuxing.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.service.WuxingService.PlayResult;
import org.openyu.mix.wuxing.service.WuxingLogService;

/**
 * 五行玩的攔截器
 */
public class WuxingPlayInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = 3244885022032659905L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingPlayInterceptor.class);

	@Autowired
	@Qualifier("wuxingLogService")
	protected transient WuxingLogService wuxingLogService;

	public WuxingPlayInterceptor() {
	}

	/**
	 * WuxingService
	 * 
	 * PlayResult play(boolean sendable, Role role, int playValue)
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
			int playValue = (Integer) args[2];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			PlayResult ret = (PlayResult) result;
			//
			if (ret != null) {
				// 記錄玩的
				wuxingLogService.recordPlay(role, ret.getPlayType(),
						ret.getPlayTime(), ret.getOutcome(),
						ret.getTotalTimes(), ret.getSpendGold(),
						ret.getSpendItems(), ret.getSpendCoin());

				// 記錄開出的結果,有成名的
				wuxingLogService.recordFamous(role, ret.getPlayType(),
						ret.getPlayTime(), ret.getOutcomes());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
