package org.openyu.mix.sasang.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<sasangTypes key="AZURE_DRAGON">1</sasangTypes>
//--------------------------------------------------
public class SasangTypeXmlAdapter extends
		BaseXmlAdapterSupporter<SasangTypeXmlAdapter.SasangTypeEntry, SasangType>
{

	public SasangTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class SasangTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public SasangTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public SasangTypeEntry()
		{}
	}

	// --------------------------------------------------
	public SasangType unmarshal(SasangTypeEntry value) throws Exception
	{
		SasangType result = null;
		//
		if (value != null)
		{
			result = SasangType.valueOf(value.key);
		}
		return result;
	}

	public SasangTypeEntry marshal(SasangType value) throws Exception
	{
		SasangTypeEntry result = null;
		//
		if (value != null)
		{
			result = new SasangTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
