package org.openyu.mix.train.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.train.po.usertype.TrainPenUserType;

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
