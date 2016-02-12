package org.openyu.mix.wuxing.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.wuxing.po.usertype.WuxingInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class WuxingInfoBridge extends BaseStringBridgeSupporter
{

	private WuxingInfoUserType userType = new WuxingInfoUserType();

	public WuxingInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
