package org.openyu.mix.role.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.role.po.useraype.GoldTypeUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
/**
 * 角色金幣增減的原因
 */
public class GoldTypeBridge extends BaseStringBridgeSupporter
{

	private GoldTypeUserType userType = new GoldTypeUserType();

	public GoldTypeBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
