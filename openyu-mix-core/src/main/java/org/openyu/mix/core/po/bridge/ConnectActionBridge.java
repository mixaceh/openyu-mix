package org.openyu.mix.core.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.core.po.usertype.ConnectActionUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ConnectActionBridge extends BaseStringBridgeSupporter
{

	private ConnectActionUserType userType = new ConnectActionUserType();

	public ConnectActionBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
