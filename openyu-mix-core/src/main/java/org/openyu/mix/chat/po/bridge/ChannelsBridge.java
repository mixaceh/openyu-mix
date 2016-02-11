package org.openyu.mix.chat.po.bridge;

import org.openyu.mix.chat.po.userType.ChannelsUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ChannelsBridge extends BaseStringBridgeSupporter
{

	private ChannelsUserType userType = new ChannelsUserType();

	public ChannelsBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
