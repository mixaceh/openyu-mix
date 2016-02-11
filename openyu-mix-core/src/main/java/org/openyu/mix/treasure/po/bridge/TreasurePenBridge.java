package org.openyu.mix.treasure.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.treasure.po.usertype.TreasurePenUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class TreasurePenBridge extends BaseStringBridgeSupporter
{

	private TreasurePenUserType userType = new TreasurePenUserType();

	public TreasurePenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
