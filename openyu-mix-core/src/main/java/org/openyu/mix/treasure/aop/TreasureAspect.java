package org.openyu.mix.treasure.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService.BuyResult;
import org.openyu.mix.treasure.service.TreasureService.RefreshResult;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class TreasureAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TreasureAspect.class);

	@Autowired
	@Qualifier("treasureLogService")
	private transient TreasureLogService treasureLogService;

	public TreasureAspect() {

	}

	/**
	 * 購買秘寶
	 * 
	 * TreasureService
	 * 
	 * BuyResult buy(boolean sendable, Role role, int buyValue, int index)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.treasure.service.TreasureService.buy(..))", returning = "result")
	public void buy(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int buyValue = (Integer) args[2];
			int index = (Integer) args[3];
			//
			BuyResult returnValue = (BuyResult) result;
			//
			if (returnValue != null) {
				treasureLogService.recordBuy(role, returnValue.getBuyType(), returnValue.getIndex(),
						returnValue.getTreasure(), returnValue.getItem(), returnValue.getSpendGold(),
						returnValue.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during buy()").toString(), e);
		}
	}

	/**
	 * 刷新秘寶
	 * 
	 * TreasureService
	 * 
	 * RefreshResult refresh(boolean sendable, Role role)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.treasure.service.TreasureService.refresh(..))", returning = "result")
	public void refresh(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			//
			RefreshResult returnValue = (RefreshResult) result;
			//
			if (returnValue != null) {
				treasureLogService.recordRefresh(role, returnValue.getRefreshTime(), returnValue.getTreasures(),
						returnValue.getSpendItems(), returnValue.getSpendCoin());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during refresh()").toString(), e);
		}
	}
}
