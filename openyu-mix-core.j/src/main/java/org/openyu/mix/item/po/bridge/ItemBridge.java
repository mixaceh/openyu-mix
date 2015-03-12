package org.openyu.mix.item.po.bridge;

import org.openyu.mix.item.po.userType.ItemUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ItemBridge extends BaseStringBridgeSupporter
{

	private ItemUserType userType = new ItemUserType();

	public ItemBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
