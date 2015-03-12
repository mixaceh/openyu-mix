package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.openyu.mix.activity.vo.target.FameTargetActivity;
import org.openyu.mix.activity.vo.target.supporter.TargetActivitySupporter;
import org.openyu.mix.activity.vo.ActivityType;

@XmlRootElement(name = "fameActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class FameTargetActivityImpl extends TargetActivitySupporter implements
		FameTargetActivity {

	private static final long serialVersionUID = 7964833486225845949L;

	public FameTargetActivityImpl(String id) {
		setId(id);
		setActivityType(ActivityType.FAME_TARGET);
	}

	public FameTargetActivityImpl() {
		this(null);
	}
}
