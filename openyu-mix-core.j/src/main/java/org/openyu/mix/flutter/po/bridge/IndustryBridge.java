package org.openyu.mix.flutter.po.bridge;

import org.openyu.mix.flutter.po.userType.IndustryUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class IndustryBridge extends BaseStringBridgeSupporter
{

	private IndustryUserType userType = new IndustryUserType();

	public IndustryBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
