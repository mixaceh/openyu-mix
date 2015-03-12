package org.openyu.mix.wuxing.po.bridge;

import org.openyu.mix.wuxing.po.userType.WuxingPenUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class WuxingPenBridge extends BaseStringBridgeSupporter
{

	private WuxingPenUserType userType = new WuxingPenUserType();

	public WuxingPenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
