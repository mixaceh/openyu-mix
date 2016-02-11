package org.openyu.mix.manor.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.manor.po.useraype.CultureTypeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class CultureTypeBridge extends BaseStringBridgeSupporter
{

	private CultureTypeUserType userType = new CultureTypeUserType();

	public CultureTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
