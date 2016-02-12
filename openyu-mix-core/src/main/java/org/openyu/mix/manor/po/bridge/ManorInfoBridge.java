package org.openyu.mix.manor.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.manor.po.usertype.ManorInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ManorInfoBridge extends BaseStringBridgeSupporter
{

	private ManorInfoUserType userType = new ManorInfoUserType();

	public ManorInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
