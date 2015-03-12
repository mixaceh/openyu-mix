package org.openyu.mix.beast.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.beast.vo.BeastType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<beastTypes key="FLY">1</beastTypes>
//--------------------------------------------------
public class BeastTypeXmlAdapter extends
		BaseXmlAdapterSupporter<BeastTypeXmlAdapter.BeastTypeEntry, BeastType>
{

	public BeastTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class BeastTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public BeastTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public BeastTypeEntry()
		{}
	}

	// --------------------------------------------------
	public BeastType unmarshal(BeastTypeEntry value) throws Exception
	{
		BeastType result = null;
		//
		if (value != null)
		{
			result = BeastType.valueOf(value.key);
		}
		return result;
	}

	public BeastTypeEntry marshal(BeastType value) throws Exception
	{
		BeastTypeEntry result = null;
		//
		if (value != null)
		{
			result = new BeastTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
