package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.RoleGoldThing;
import org.openyu.mix.item.vo.thing.supporter.RoleThingSupporter;

@XmlRootElement(name = "roleGoldThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleGoldThingImpl extends RoleThingSupporter implements
		RoleGoldThing {

	private static final long serialVersionUID = -1739693807640808791L;

	/**
	 * 金幣
	 */
	private long gold;

	public RoleGoldThingImpl(String id) {
		super(id);
		setThingType(ThingType.ROLE_GOLD);
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public RoleGoldThingImpl() {
		this(null);
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("gold", gold);
		return builder.toString();
	}

	public Object clone() {
		RoleGoldThingImpl copy = null;
		copy = (RoleGoldThingImpl) super.clone();
		return copy;
	}

}
