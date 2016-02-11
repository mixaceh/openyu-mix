package org.openyu.mix.role.po.bridge;

import org.openyu.mix.role.po.userType.BagPenUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class BagPenBridge extends BaseStringBridgeSupporter
{

	private BagPenUserType userType = new BagPenUserType();

	public BagPenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
