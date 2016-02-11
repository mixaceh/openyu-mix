package org.openyu.mix.item.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.item.po.useraype.ItemTypeUserType;

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
