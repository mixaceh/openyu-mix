package org.openyu.mix.flutter.po.bridge;

import org.openyu.mix.flutter.po.userType.RaceTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class RaceTypeBridge extends BaseStringBridgeSupporter
{

	private RaceTypeUserType userType = new RaceTypeUserType();

	public RaceTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
