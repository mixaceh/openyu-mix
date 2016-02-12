package org.openyu.mix.treasure.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.treasure.po.usertype.TreasureInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class TreasureInfoBridge extends BaseStringBridgeSupporter
{

	private TreasureInfoUserType userType = new TreasureInfoUserType();

	public TreasureInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
