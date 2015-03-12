package org.openyu.mix.item.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.supporter.ItemSupporter;

@XmlRootElement(name = "thing")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThingImpl extends ItemSupporter implements Thing
{

	private static final long serialVersionUID = 2510268444923983897L;

	/**
	 * 物品類別
	 */
	private ThingType thingType;

	public ThingImpl(String id)
	{
		setId(id);
		setItemType(ItemType.THING);
	}

	public ThingImpl()
	{
		this(null);
	}

	public ThingType getThingType()
	{
		return thingType;
	}

	public void setThingType(ThingType thingType)
	{
		this.thingType = thingType;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("thingType", thingType);
		return builder.toString();
	}

	public Object clone()
	{
		ThingImpl copy = null;
		copy = (ThingImpl) super.clone();
		return copy;
	}

}
