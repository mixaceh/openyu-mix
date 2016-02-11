package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.thing.RoleExpThing;
import org.openyu.mix.item.vo.thing.supporter.RoleThingSupporter;

@XmlRootElement(name = "roleExpThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleExpThingImpl extends RoleThingSupporter implements
		RoleExpThing {

	private static final long serialVersionUID = 7599328645067647665L;

	/**
	 * 經驗
	 */
	private long exp;

	public RoleExpThingImpl(String id) {
		super(id);
		setThingType(ThingType.ROLE_EXP);
	}

	public RoleExpThingImpl() {
		this(null);
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("exp", exp);
		return builder.toString();
	}

	public Object clone() {
		RoleExpThingImpl copy = null;
		copy = (RoleExpThingImpl) super.clone();
		return copy;
	}

}
