package org.openyu.mix.role.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.role.po.usertype.BagInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class BagInfoBridge extends BaseStringBridgeSupporter
{

	private BagInfoUserType userType = new BagInfoUserType();

	public BagInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
