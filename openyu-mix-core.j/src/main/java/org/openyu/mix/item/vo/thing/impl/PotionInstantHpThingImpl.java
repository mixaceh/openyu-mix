package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.PotionInstantHpThing;
import org.openyu.mix.item.vo.thing.supporter.PotionThingSupporter;

@XmlRootElement(name = "potionInstantHpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class PotionInstantHpThingImpl extends PotionThingSupporter implements
		PotionInstantHpThing {

	private static final long serialVersionUID = 5554781524412848170L;

	public PotionInstantHpThingImpl(String id) {
		super(id);
		setThingType(ThingType.POTION_INSTANT_HP);
	}

	public PotionInstantHpThingImpl() {
		this(null);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone() {
		PotionInstantHpThingImpl copy = null;
		copy = (PotionInstantHpThingImpl) super.clone();
		return copy;
	}

}
