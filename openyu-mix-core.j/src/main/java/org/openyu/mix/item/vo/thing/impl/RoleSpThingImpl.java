package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.RoleSpThing;
import org.openyu.mix.item.vo.thing.supporter.RoleThingSupporter;

@XmlRootElement(name = "roleSpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleSpThingImpl extends RoleThingSupporter implements RoleSpThing {

	private static final long serialVersionUID = 7599328645067647665L;

	/**
	 * 技魂(sp)
	 */
	private long sp;

	public RoleSpThingImpl(String id) {
		super(id);
		setThingType(ThingType.ROLE_SP);
	}

	public RoleSpThingImpl() {
		this(null);
	}

	public long getSp() {
		return sp;
	}

	public void setSp(long sp) {
		this.sp = sp;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("sp", sp);
		return builder.toString();
	}

	public Object clone() {
		RoleSpThingImpl copy = null;
		copy = (RoleSpThingImpl) super.clone();
		return copy;
	}

}
