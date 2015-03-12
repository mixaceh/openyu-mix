package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.BackTownThing;
import org.openyu.mix.item.vo.thing.supporter.BackThingSupporter;

@XmlRootElement(name = "backTownThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackTownThingImpl extends BackThingSupporter implements
		BackTownThing {

	private static final long serialVersionUID = -2518491459033423143L;

	/**
	 * 等待秒數
	 */
	private int second;

	public BackTownThingImpl(String id) {
		super(id);
		setThingType(ThingType.BACK_TOWN);
	}

	public BackTownThingImpl() {
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
		BackTownThingImpl copy = null;
		copy = (BackTownThingImpl) super.clone();
		return copy;
	}

}
