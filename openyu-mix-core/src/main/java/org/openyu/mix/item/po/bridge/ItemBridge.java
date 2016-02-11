package org.openyu.mix.item.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.item.po.useraype.ItemUserType;

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
