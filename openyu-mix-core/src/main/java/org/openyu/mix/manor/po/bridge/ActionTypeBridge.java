package org.openyu.mix.manor.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.manor.po.usertype.ActionTypeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ActionTypeBridge extends BaseStringBridgeSupporter
{

	private ActionTypeUserType userType = new ActionTypeUserType();

	public ActionTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
