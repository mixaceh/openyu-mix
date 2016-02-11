package org.openyu.mix.flutter.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.flutter.po.useraype.AttributeGroupUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class AttributeGroupBridge extends BaseStringBridgeSupporter
{

	private AttributeGroupUserType userType = new AttributeGroupUserType();

	public AttributeGroupBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
