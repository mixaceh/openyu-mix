package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<enhanceLevels key="_1">1</enhanceLevels>
//--------------------------------------------------
public class EnhanceLevelXmlAdapter extends
		BaseXmlAdapterSupporter<EnhanceLevelXmlAdapter.EnhanceLevelEntry, EnhanceLevel>
{

	public EnhanceLevelXmlAdapter()
	{}

	// --------------------------------------------------
	public static class EnhanceLevelEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public EnhanceLevelEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public EnhanceLevelEntry()
		{}
	}

	// --------------------------------------------------
	public EnhanceLevel unmarshal(EnhanceLevelEntry value) throws Exception
	{
		EnhanceLevel result = null;
		//
		if (value != null)
		{
			result = EnhanceLevel.valueOf(value.key);
		}
		return result;
	}

	public EnhanceLevelEntry marshal(EnhanceLevel value) throws Exception
	{
		EnhanceLevelEntry result = null;
		//
		if (value != null)
		{
			result = new EnhanceLevelEntry(value.name(), value.getValue());
		}
		return result;
	}
}
