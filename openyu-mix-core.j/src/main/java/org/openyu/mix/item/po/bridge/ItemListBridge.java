package org.openyu.mix.item.po.bridge;

import org.openyu.mix.item.po.userType.ItemListUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ItemListBridge extends BaseStringBridgeSupporter
{

	private ItemListUserType userType = new ItemListUserType();

	public ItemListBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
