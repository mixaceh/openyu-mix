package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.item.vo.PositionType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<positionTypes key="LEFT_HAND">1</positionTypes>
//--------------------------------------------------
public class EquipmentPositionTypeXmlAdapter
		extends
		BaseXmlAdapterSupporter<EquipmentPositionTypeXmlAdapter.EquipmentPositionTypeEntry, PositionType>
{

	public EquipmentPositionTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class EquipmentPositionTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public EquipmentPositionTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public EquipmentPositionTypeEntry()
		{}
	}

	// --------------------------------------------------
	public PositionType unmarshal(EquipmentPositionTypeEntry value) throws Exception
	{
		PositionType result = null;
		//
		if (value != null)
		{
			result = PositionType.valueOf(value.key);
		}
		return result;
	}

	public EquipmentPositionTypeEntry marshal(PositionType value) throws Exception
	{
		EquipmentPositionTypeEntry result = null;
		//
		if (value != null)
		{
			result = new EquipmentPositionTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
