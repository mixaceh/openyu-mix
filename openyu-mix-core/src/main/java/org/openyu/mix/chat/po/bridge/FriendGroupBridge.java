package org.openyu.mix.chat.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.chat.po.usertype.FriendGroupUserType;

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
