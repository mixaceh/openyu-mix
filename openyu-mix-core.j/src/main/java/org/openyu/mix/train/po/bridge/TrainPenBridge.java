package org.openyu.mix.train.po.bridge;

import org.openyu.mix.train.po.userType.TrainPenUserType;
import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class TrainPenBridge extends BaseStringBridgeSupporter
{

	private TrainPenUserType userType = new TrainPenUserType();

	public TrainPenBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
