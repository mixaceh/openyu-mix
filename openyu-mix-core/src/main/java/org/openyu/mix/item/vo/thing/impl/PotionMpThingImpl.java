package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.PotionMpThing;
import org.openyu.mix.item.vo.thing.supporter.PotionThingSupporter;

@XmlRootElement(name = "potionMpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class PotionMpThingImpl extends PotionThingSupporter implements
		PotionMpThing {

	private static final long serialVersionUID = -495203720828185875L;

	/**
	 * 持續秒數
	 */
	private int second;

	public PotionMpThingImpl(String id) {
		super(id);
		setThingType(ThingType.POTION_MP);
	}

	public PotionMpThingImpl() {
		this(null);
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("second", second);
		return builder.toString();
	}

	public Object clone() {
		PotionMpThingImpl copy = null;
		copy = (PotionMpThingImpl) super.clone();
		return copy;
	}

}
