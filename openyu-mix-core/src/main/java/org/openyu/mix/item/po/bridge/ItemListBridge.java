package org.openyu.mix.item.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.item.po.usertype.ItemListUserType;

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
