package org.openyu.mix.manor.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.SeedImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringSeedXmlAdapter extends
		BaseXmlAdapterSupporter<StringSeedXmlAdapter.StringSeedList, Map<String, Seed>>
{

	public StringSeedXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringSeedList
	{
		public List<StringSeedEntry> entry = new LinkedList<StringSeedEntry>();
	}

	public static class StringSeedEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = SeedImpl.class)
		public Seed value;

		public StringSeedEntry(String key, Seed value)
		{
			this.key = key;
			this.value = value;
		}

		public StringSeedEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Seed> unmarshal(StringSeedList value) throws Exception
	{
		Map<String, Seed> result = new LinkedHashMap<String, Seed>();
		if (value != null)
		{
			for (StringSeedEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringSeedList marshal(Map<String, Seed> value) throws Exception
	{
		StringSeedList result = new StringSeedList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Seed> entry : value.entrySet())
			{
				result.entry.add(new StringSeedEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
