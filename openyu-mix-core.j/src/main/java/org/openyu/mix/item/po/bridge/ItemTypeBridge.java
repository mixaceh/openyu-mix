package org.openyu.mix.item.po.bridge;

import org.openyu.mix.item.po.userType.ItemTypeUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class ItemTypeBridge extends BaseStringBridgeSupporter
{

	private ItemTypeUserType userType = new ItemTypeUserType();

	public ItemTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
