package org.openyu.mix.wuxing.po.bridge;

import org.openyu.mix.wuxing.po.userType.PutTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class PutTypeBridge extends BaseStringBridgeSupporter
{

	private PutTypeUserType userType = new PutTypeUserType();

	public PutTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
