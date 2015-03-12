package org.openyu.mix.flutter.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<attributeTypes key="HealthPoint">1</attributeTypes>
//--------------------------------------------------
public class AttributeTypeXmlAdapter extends
		BaseXmlAdapterSupporter<AttributeTypeXmlAdapter.AttributeTypeEntry, AttributeType>
{

	public AttributeTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class AttributeTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public AttributeTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public AttributeTypeEntry()
		{}
	}

	// --------------------------------------------------
	public AttributeType unmarshal(AttributeTypeEntry value) throws Exception
	{
		AttributeType result = null;
		//
		if (value != null)
		{
			result = AttributeType.valueOf(value.key);
		}
		return result;
	}

	public AttributeTypeEntry marshal(AttributeType value) throws Exception
	{
		AttributeTypeEntry result = null;
		//
		if (value != null)
		{
			result = new AttributeTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
