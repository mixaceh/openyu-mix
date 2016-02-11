package org.openyu.mix.treasure.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.impl.TreasureImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class IntegerTreasureXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerTreasureXmlAdapter.IntegerTreasureList, Map<Integer, Treasure>>
{

	public IntegerTreasureXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerTreasureList
	{
		public List<IntegerTreasureEntry> entry = new LinkedList<IntegerTreasureEntry>();
	}

	public static class IntegerTreasureEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElement(type = TreasureImpl.class)
		public Treasure value;

		public IntegerTreasureEntry(Integer key, Treasure value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerTreasureEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, Treasure> unmarshal(IntegerTreasureList value) throws Exception
	{
		Map<Integer, Treasure> result = new LinkedHashMap<Integer, Treasure>();
		if (value != null)
		{
			for (IntegerTreasureEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerTreasureList marshal(Map<Integer, Treasure> value) throws Exception
	{
		IntegerTreasureList result = new IntegerTreasureList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, Treasure> entry : value.entrySet())
			{
				result.entry.add(new IntegerTreasureEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
