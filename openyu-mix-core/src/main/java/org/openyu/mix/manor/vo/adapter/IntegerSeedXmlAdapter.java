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

public class IntegerSeedXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerSeedXmlAdapter.IntegerSeedList, Map<Integer, Seed>>
{

	public IntegerSeedXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerSeedList
	{
		public List<IntegerSeedEntry> entry = new LinkedList<IntegerSeedEntry>();
	}

	public static class IntegerSeedEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElement(type = SeedImpl.class)
		public Seed value;

		public IntegerSeedEntry(Integer key, Seed value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerSeedEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, Seed> unmarshal(IntegerSeedList value) throws Exception
	{
		Map<Integer, Seed> result = new LinkedHashMap<Integer, Seed>();
		if (value != null)
		{
			for (IntegerSeedEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerSeedList marshal(Map<Integer, Seed> value) throws Exception
	{
		IntegerSeedList result = new IntegerSeedList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, Seed> entry : value.entrySet())
			{
				result.entry.add(new IntegerSeedEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
