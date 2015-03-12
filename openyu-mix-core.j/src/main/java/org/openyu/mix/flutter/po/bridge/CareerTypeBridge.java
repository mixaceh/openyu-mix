package org.openyu.mix.flutter.po.bridge;

import org.openyu.mix.flutter.po.userType.CareerTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class CareerTypeBridge extends BaseStringBridgeSupporter
{

	private CareerTypeUserType userType = new CareerTypeUserType();

	public CareerTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
