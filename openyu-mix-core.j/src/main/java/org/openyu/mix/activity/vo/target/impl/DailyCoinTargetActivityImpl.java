package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.openyu.mix.activity.vo.target.DailyCoinTargetActivity;
import org.openyu.mix.activity.vo.target.supporter.TargetActivitySupporter;
import org.openyu.mix.activity.vo.ActivityType;

@XmlRootElement(name = "dailyCoinActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class DailyCoinTargetActivityImpl extends TargetActivitySupporter implements DailyCoinTargetActivity
{

	private static final long serialVersionUID = 5500967496861030136L;

	public DailyCoinTargetActivityImpl(String id)
	{
		setId(id);
		setActivityType(ActivityType.DAILY_COIN_TARGET);
	}

	public DailyCoinTargetActivityImpl()
	{
		this(null);
	}
}
