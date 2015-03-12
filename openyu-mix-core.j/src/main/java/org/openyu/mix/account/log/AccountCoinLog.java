package org.openyu.mix.account.log;

import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.log.AppLogEntity;

/**
 * 帳戶儲值增減log,不做bean,直接用entity處理掉
 */
public interface AccountCoinLog extends AppLogEntity
{
	String KEY = AccountCoinLog.class.getName();

	/**
	 * 儲值操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 儲值增減的原因
	 * 
	 * @return
	 */
	CoinType getCoinType();

	void setCoinType(CoinType coinType);

	/**
	 * 儲值幣
	 * 
	 * @return
	 */
	Integer getCoin();

	void setCoin(Integer coin);

}
