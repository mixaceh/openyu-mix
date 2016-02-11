package org.openyu.mix.manor.po.bridge;

import org.openyu.mix.manor.po.userType.ManorPenUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ManorPenBridge extends BaseStringBridgeSupporter
{

	private ManorPenUserType userType = new ManorPenUserType();

	public ManorPenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
