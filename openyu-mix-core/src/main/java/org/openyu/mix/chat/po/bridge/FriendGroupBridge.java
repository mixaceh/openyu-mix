package org.openyu.mix.chat.po.bridge;

import org.openyu.mix.chat.po.userType.FriendGroupUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class FriendGroupBridge extends BaseStringBridgeSupporter
{

	private FriendGroupUserType userType = new FriendGroupUserType();

	public FriendGroupBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
