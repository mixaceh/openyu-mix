package org.openyu.mix.wuxing.po.bridge;

import org.openyu.mix.wuxing.po.userType.PlayTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class PlayTypeBridge extends BaseStringBridgeSupporter
{

	private PlayTypeUserType userType = new PlayTypeUserType();

	public PlayTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
