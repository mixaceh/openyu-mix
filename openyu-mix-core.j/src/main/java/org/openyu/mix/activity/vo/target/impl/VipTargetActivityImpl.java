package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.openyu.mix.activity.vo.target.VipTargetActivity;
import org.openyu.mix.activity.vo.target.supporter.TargetActivitySupporter;
import org.openyu.mix.activity.vo.ActivityType;

@XmlRootElement(name = "vipCoinActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class VipTargetActivityImpl extends TargetActivitySupporter implements
		VipTargetActivity {

	private static final long serialVersionUID = 5500967496861030136L;

	public VipTargetActivityImpl(String id) {
		setId(id);
		setActivityType(ActivityType.VIP_TARGET);
	}

	public VipTargetActivityImpl() {
		this(null);
	}
}
