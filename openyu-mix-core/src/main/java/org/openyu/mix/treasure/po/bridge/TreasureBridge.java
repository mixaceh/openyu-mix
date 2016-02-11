package org.openyu.mix.treasure.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.treasure.po.usertype.TreasureUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class TreasureBridge extends BaseStringBridgeSupporter
{

	private TreasureUserType userType = new TreasureUserType();

	public TreasureBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
