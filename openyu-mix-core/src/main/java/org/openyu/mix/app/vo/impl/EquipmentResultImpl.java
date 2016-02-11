package org.openyu.mix.app.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.vo.EquipmentResult;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.item.vo.Equipment;

@XmlRootElement(name = "equipmentResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipmentResultImpl extends AppResultSupporter implements EquipmentResult
{

	private static final long serialVersionUID = 8213239002550783791L;

	/**
	 * 裝備
	 */
	private Equipment equipment;

	public EquipmentResultImpl()
	{

	}

	public Equipment getEquipment()
	{
		return equipment;
	}

	public void setEquipment(Equipment equipment)
	{
		this.equipment = equipment;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.appendSuper(super.toString());
		builder.append("equipment",
			(equipment != null ? equipment.getId() + ", " + equipment.getUniqueId() : null));
		return builder.toString();
	}

	public Object clone()
	{
		EquipmentResultImpl copy = null;
		copy = (EquipmentResultImpl) super.clone();
		copy.equipment = clone(equipment);
		return copy;
	}
}
