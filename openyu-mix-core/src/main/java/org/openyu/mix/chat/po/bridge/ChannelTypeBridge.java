package org.openyu.mix.chat.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.chat.po.usertype.ChannelTypeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ChannelTypeBridge extends BaseStringBridgeSupporter
{

	private ChannelTypeUserType userType = new ChannelTypeUserType();

	public ChannelTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
