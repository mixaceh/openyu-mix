package org.openyu.mix.treasure.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppAroundAdviceSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService.BuyResult;

/**
 * 祕寶購買攔截器
 */
@Deprecated
public class TreasureBuyInterceptor extends AppAroundAdviceSupporter {

	private static final long serialVersionUID = 6260420634235652046L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TreasureBuyInterceptor.class);

	@Autowired
	@Qualifier("treasureLogService")
	protected transient TreasureLogService treasureLogService;

	public TreasureBuyInterceptor() {
	}

	/**
	 * TreasureService
	 * 
	 * BuyResult buy(boolean sendable, Role role, int buyValue, int index)
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
			int buyValue = (Integer) args[2];
			int index = (Integer) args[3];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			BuyResult ret = (BuyResult) result;
			//
			if (ret != null) {
				treasureLogService.recordBuy(role, ret.getBuyType(), ret.getIndex(), ret.getTreasure(), ret.getItem(),
						ret.getSpendGold(), ret.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
