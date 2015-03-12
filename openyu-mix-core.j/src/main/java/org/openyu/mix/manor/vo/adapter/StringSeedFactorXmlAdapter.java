package org.openyu.mix.manor.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.manor.vo.SeedFactor;
import org.openyu.mix.manor.vo.impl.SeedFactorImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class StringSeedFactorXmlAdapter
		extends
		BaseXmlAdapterSupporter<StringSeedFactorXmlAdapter.StringSeedFactorList, Map<String, SeedFactor>>
{

	public StringSeedFactorXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringSeedFactorList
	{
		public List<StringSeedFactorEntry> entry = new LinkedList<StringSeedFactorEntry>();
	}

	public static class StringSeedFactorEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = SeedFactorImpl.class)
		public SeedFactor value;

		public StringSeedFactorEntry(String key, SeedFactor value)
		{
			this.key = key;
			this.value = value;
		}

		public StringSeedFactorEntry()
		{}
	}

	// --------------------------------------------------
	public Map<String, SeedFactor> unmarshal(StringSeedFactorList value) throws Exception
	{
		Map<String, SeedFactor> result = new LinkedHashMap<String, SeedFactor>();
		if (value != null)
		{
			for (StringSeedFactorEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringSeedFactorList marshal(Map<String, SeedFactor> value) throws Exception
	{
		StringSeedFactorList result = new StringSeedFactorList();
		//
		if (value != null)
		{
			for (Map.Entry<String, SeedFactor> entry : value.entrySet())
			{
				result.entry.add(new StringSeedFactorEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
