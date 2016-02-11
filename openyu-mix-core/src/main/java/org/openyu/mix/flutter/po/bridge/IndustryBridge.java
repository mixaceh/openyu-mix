package org.openyu.mix.flutter.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.flutter.po.usertype.IndustryUserType;

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
