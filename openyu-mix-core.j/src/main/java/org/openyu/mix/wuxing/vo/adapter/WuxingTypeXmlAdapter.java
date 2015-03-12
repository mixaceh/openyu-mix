package org.openyu.mix.wuxing.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.wuxing.vo.WuxingType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<wuxingTypes key="METAL">1</wuxingTypes>
//--------------------------------------------------
public class WuxingTypeXmlAdapter extends
		BaseXmlAdapterSupporter<WuxingTypeXmlAdapter.WuxingTypeEntry, WuxingType>
{

	public WuxingTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class WuxingTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public WuxingTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public WuxingTypeEntry()
		{}
	}

	// --------------------------------------------------
	public WuxingType unmarshal(WuxingTypeEntry value) throws Exception
	{
		WuxingType result = null;
		//
		if (value != null)
		{
			result = WuxingType.valueOf(value.key);
		}
		return result;
	}

	public WuxingTypeEntry marshal(WuxingType value) throws Exception
	{
		WuxingTypeEntry result = null;
		//
		if (value != null)
		{
			result = new WuxingTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
