package org.openyu.mix.item.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.supporter.EquipmentSupporter;

@XmlRootElement(name = "armor")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArmorImpl extends EquipmentSupporter implements Armor
{

	private static final long serialVersionUID = -8719551220374997732L;

	public ArmorImpl(String id)
	{
		setId(id);
		setItemType(ItemType.ARMOR);
	}

	public ArmorImpl()
	{
		this(null);
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone()
	{
		ArmorImpl copy = null;
		copy = (ArmorImpl) super.clone();
		return copy;
	}

}
