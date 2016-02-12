package org.openyu.mix.sasang.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.sasang.po.usertype.SasangInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class SasangInfoBridge extends BaseStringBridgeSupporter
{

	private SasangInfoUserType userType = new SasangInfoUserType();

	public SasangInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
