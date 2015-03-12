package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.PotionHpThing;
import org.openyu.mix.item.vo.thing.supporter.PotionThingSupporter;

@XmlRootElement(name = "potionHpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class PotionHpThingImpl extends PotionThingSupporter implements
		PotionHpThing {

	private static final long serialVersionUID = -2518491459033423143L;

	/**
	 * 持續秒數
	 */
	private int second;

	public PotionHpThingImpl(String id) {
		super(id);
		setThingType(ThingType.POTION_HP);
	}

	public PotionHpThingImpl() {
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
		PotionHpThingImpl copy = null;
		copy = (PotionHpThingImpl) super.clone();
		return copy;
	}

}
