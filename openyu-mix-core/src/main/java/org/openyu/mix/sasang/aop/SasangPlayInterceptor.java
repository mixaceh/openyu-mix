package org.openyu.mix.sasang.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.service.SasangService.PlayResult;
import org.openyu.mix.sasang.service.SasangLogService;

/**
 * 四象玩的攔截器
 */
@Deprecated
public class SasangPlayInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = -8510371894911249679L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SasangPlayInterceptor.class);

	@Autowired
	@Qualifier("sasangLogService")
	protected transient SasangLogService sasangLogService;

	public SasangPlayInterceptor() {
	}

	/**
	 * SasangService
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
				sasangLogService.recordPlay(role, ret.getPlayType(), ret.getPlayTime(), ret.getOutcome(),
						ret.getTotalTimes(), ret.getSpendGold(), ret.getSpendItems(), ret.getSpendCoin());

				// 記錄開出的結果,有成名的
				sasangLogService.recordFamous(role, ret.getPlayType(), ret.getPlayTime(), ret.getOutcomes());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
