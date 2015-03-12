package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.openyu.mix.activity.vo.target.AccuCoinTargetActivity;
import org.openyu.mix.activity.vo.target.supporter.TargetActivitySupporter;
import org.openyu.mix.activity.vo.ActivityType;

@XmlRootElement(name = "accuCoinActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccuCoinTargetActivityImpl extends TargetActivitySupporter implements AccuCoinTargetActivity
{

	private static final long serialVersionUID = 484515552034102555L;

	public AccuCoinTargetActivityImpl(String id)
	{
		setId(id);
		setActivityType(ActivityType.ACCU_COIN_TARGET);
	}

	public AccuCoinTargetActivityImpl()
	{
		this(null);
	}
}
