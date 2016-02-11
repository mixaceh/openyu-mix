package org.openyu.mix.treasure.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.treasure.po.usertype.BuyTypeUserType;

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
