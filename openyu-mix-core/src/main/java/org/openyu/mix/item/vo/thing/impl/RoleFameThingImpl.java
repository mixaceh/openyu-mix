package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.RoleFameThing;
import org.openyu.mix.item.vo.thing.supporter.RoleThingSupporter;

@XmlRootElement(name = "roleFameThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleFameThingImpl extends RoleThingSupporter implements
		RoleFameThing {

	private static final long serialVersionUID = 1883291306392675923L;

	/**
	 * 聲望
	 */
	private int fame;

	public RoleFameThingImpl(String id) {
		super(id);
		setThingType(ThingType.ROLE_FAME);
	}

	public RoleFameThingImpl() {
		this(null);
	}

	public int getFame() {
		return fame;
	}

	public void setFame(int fame) {
		this.fame = fame;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("fame", fame);
		return builder.toString();
	}

	public Object clone() {
		RoleFameThingImpl copy = null;
		copy = (RoleFameThingImpl) super.clone();
		return copy;
	}

}
