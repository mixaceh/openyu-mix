package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.thing.EnhanceWeaponThing;
import org.openyu.mix.item.vo.thing.supporter.EnhanceThingSupporter;

@XmlRootElement(name = "enhanceWeaponThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnhanceWeaponThingImpl extends EnhanceThingSupporter implements
		EnhanceWeaponThing {

	private static final long serialVersionUID = -2527965119022423436L;

	/**
	 * 裝備等級
	 */
	private LevelType levelType;

	public EnhanceWeaponThingImpl(String id) {
		super(id);
		setThingType(ThingType.ENHANCE_WEAPON);
	}

	public EnhanceWeaponThingImpl() {
		this(null);
	}

	public LevelType getLevelType() {
		return levelType;
	}

	public void setLevelType(LevelType levelType) {
		this.levelType = levelType;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("levelType", levelType);
		return builder.toString();
	}

	public Object clone() {
		EnhanceWeaponThingImpl copy = null;
		copy = (EnhanceWeaponThingImpl) super.clone();
		return copy;
	}

}
