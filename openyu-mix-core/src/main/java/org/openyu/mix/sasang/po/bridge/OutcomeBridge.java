package org.openyu.mix.sasang.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.sasang.po.usertype.OutcomeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class OutcomeBridge extends BaseStringBridgeSupporter
{

	private OutcomeUserType userType = new OutcomeUserType();

	public OutcomeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
