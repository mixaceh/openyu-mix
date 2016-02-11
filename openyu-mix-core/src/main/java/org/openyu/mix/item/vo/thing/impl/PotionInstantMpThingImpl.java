package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.PotionInstantMpThing;
import org.openyu.mix.item.vo.thing.supporter.PotionThingSupporter;

@XmlRootElement(name = "potionInstantMpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class PotionInstantMpThingImpl extends PotionThingSupporter implements
		PotionInstantMpThing {

	private static final long serialVersionUID = -5653617185088381469L;

	public PotionInstantMpThingImpl(String id) {
		super(id);
		setThingType(ThingType.POTION_INSTANT_MP);
	}

	public PotionInstantMpThingImpl() {
		this(null);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone() {
		PotionInstantMpThingImpl copy = null;
		copy = (PotionInstantMpThingImpl) super.clone();
		return copy;
	}

}
