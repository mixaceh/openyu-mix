package org.openyu.mix.sasang.po.bridge;

import org.openyu.mix.sasang.po.userType.SasangPenUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class SasangPenBridge extends BaseStringBridgeSupporter
{

	private SasangPenUserType userType = new SasangPenUserType();

	public SasangPenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
