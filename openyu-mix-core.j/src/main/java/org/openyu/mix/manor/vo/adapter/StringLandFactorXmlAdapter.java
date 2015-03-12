package org.openyu.mix.manor.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.manor.vo.LandFactor;
import org.openyu.mix.manor.vo.impl.LandFactorImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class StringLandFactorXmlAdapter
		extends
		BaseXmlAdapterSupporter<StringLandFactorXmlAdapter.StringLandFactorList, Map<String, LandFactor>>
{

	public StringLandFactorXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringLandFactorList
	{
		public List<StringLandFactorEntry> entry = new LinkedList<StringLandFactorEntry>();
	}

	public static class StringLandFactorEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = LandFactorImpl.class)
		public LandFactor value;

		public StringLandFactorEntry(String key, LandFactor value)
		{
			this.key = key;
			this.value = value;
		}

		public StringLandFactorEntry()
		{}
	}

	// --------------------------------------------------
	public Map<String, LandFactor> unmarshal(StringLandFactorList value) throws Exception
	{
		Map<String, LandFactor> result = new LinkedHashMap<String, LandFactor>();
		if (value != null)
		{
			for (StringLandFactorEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringLandFactorList marshal(Map<String, LandFactor> value) throws Exception
	{
		StringLandFactorList result = new StringLandFactorList();
		//
		if (value != null)
		{
			for (Map.Entry<String, LandFactor> entry : value.entrySet())
			{
				result.entry.add(new StringLandFactorEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
