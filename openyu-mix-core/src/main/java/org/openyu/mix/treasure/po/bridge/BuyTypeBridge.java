package org.openyu.mix.treasure.po.bridge;

import org.openyu.mix.treasure.po.userType.BuyTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class BuyTypeBridge extends BaseStringBridgeSupporter
{

	private BuyTypeUserType userType = new BuyTypeUserType();

	public BuyTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
