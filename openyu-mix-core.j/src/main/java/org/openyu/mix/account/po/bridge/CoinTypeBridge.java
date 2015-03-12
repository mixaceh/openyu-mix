package org.openyu.mix.account.po.bridge;

import org.openyu.mix.account.po.userType.CoinTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
/**
 * 帳戶儲值加減的原因
 */
public class CoinTypeBridge extends BaseStringBridgeSupporter {

	private CoinTypeUserType userType = new CoinTypeUserType();

	public CoinTypeBridge() {

	}

	public String objectToString(Object value) {
		return userType.marshal(value, null);
	}
}
