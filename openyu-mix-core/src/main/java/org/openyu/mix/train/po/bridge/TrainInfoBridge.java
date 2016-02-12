package org.openyu.mix.train.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.train.po.usertype.TrainInfoUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class TrainInfoBridge extends BaseStringBridgeSupporter
{

	private TrainInfoUserType userType = new TrainInfoUserType();

	public TrainInfoBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
