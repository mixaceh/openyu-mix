package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.EnhanceLandThing;
import org.openyu.mix.item.vo.thing.supporter.EnhanceThingSupporter;

@XmlRootElement(name = "enhanceLandThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnhanceLandThingImpl extends EnhanceThingSupporter implements
		EnhanceLandThing {

	private static final long serialVersionUID = 3357012040524942775L;

	public EnhanceLandThingImpl(String id) {
		super(id);
		setThingType(ThingType.ENHANCE_LAND);
	}

	public EnhanceLandThingImpl() {
		this(null);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone() {
		EnhanceLandThingImpl copy = null;
		copy = (EnhanceLandThingImpl) super.clone();
		return copy;
	}

}
