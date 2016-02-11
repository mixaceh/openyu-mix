package org.openyu.mix.account.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.account.po.usertype.CoinTypeUserType;

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
