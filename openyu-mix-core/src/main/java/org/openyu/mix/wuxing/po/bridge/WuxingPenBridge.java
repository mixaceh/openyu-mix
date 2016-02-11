package org.openyu.mix.wuxing.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.wuxing.po.usertype.WuxingPenUserType;

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
