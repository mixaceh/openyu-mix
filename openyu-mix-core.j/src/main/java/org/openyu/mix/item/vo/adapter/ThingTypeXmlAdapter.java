package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.item.vo.ThingType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<thingTypes key="POTION_HP">1</thingTypes>
//--------------------------------------------------
public class ThingTypeXmlAdapter extends
		BaseXmlAdapterSupporter<ThingTypeXmlAdapter.ThingTypeEntry, ThingType>
{

	public ThingTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class ThingTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public ThingTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public ThingTypeEntry()
		{}
	}

	// --------------------------------------------------
	public ThingType unmarshal(ThingTypeEntry value) throws Exception
	{
		ThingType result = null;
		//
		if (value != null)
		{
			result = ThingType.valueOf(value.key);
		}
		return result;
	}

	public ThingTypeEntry marshal(ThingType value) throws Exception
	{
		ThingTypeEntry result = null;
		//
		if (value != null)
		{
			result = new ThingTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
