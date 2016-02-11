package org.openyu.mix.account.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.account.po.useraype.ActionTypeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
/**
 * 帳戶儲值操作類別
 */
public class ActionTypeBridge extends BaseStringBridgeSupporter {

	private ActionTypeUserType userType = new ActionTypeUserType();

	public ActionTypeBridge() {

	}

	public String objectToString(Object value) {
		return userType.marshal(value, null);
	}
}
