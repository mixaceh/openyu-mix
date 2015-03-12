package org.openyu.mix.item.vo.thing.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.thing.EnhanceArmorThing;
import org.openyu.mix.item.vo.thing.supporter.EnhanceThingSupporter;

@XmlRootElement(name = "enhanceArmorThing")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnhanceArmorThingImpl extends EnhanceThingSupporter implements
		EnhanceArmorThing {

	private static final long serialVersionUID = 3357012040524942775L;

	/**
	 * 裝備等級
	 */
	private LevelType levelType;

	public EnhanceArmorThingImpl(String id) {
		super(id);
		setThingType(ThingType.ENHANCE_ARMOR);
	}

	public EnhanceArmorThingImpl() {
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
		EnhanceArmorThingImpl copy = null;
		copy = (EnhanceArmorThingImpl) super.clone();
		return copy;
	}

}
