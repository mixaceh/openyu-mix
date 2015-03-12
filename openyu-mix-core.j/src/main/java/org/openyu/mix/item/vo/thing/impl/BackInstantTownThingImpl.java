package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.BackInstantTownThing;
import org.openyu.mix.item.vo.thing.supporter.BackThingSupporter;

@XmlRootElement(name = "backInstantTownThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackInstantTownThingImpl extends BackThingSupporter implements
		BackInstantTownThing {

	private static final long serialVersionUID = -2518491459033423143L;

	public BackInstantTownThingImpl(String id) {
		super(id);
		setThingType(ThingType.BACK_INSTANT_TOWN);
	}

	public BackInstantTownThingImpl() {
		this(null);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone() {
		BackInstantTownThingImpl copy = null;
		copy = (BackInstantTownThingImpl) super.clone();
		return copy;
	}

}
