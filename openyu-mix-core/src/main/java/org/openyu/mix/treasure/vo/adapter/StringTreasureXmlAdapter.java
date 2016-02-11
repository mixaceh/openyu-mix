package org.openyu.mix.treasure.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.impl.TreasureImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class StringTreasureXmlAdapter extends
		BaseXmlAdapterSupporter<StringTreasureXmlAdapter.StringTreasureList, Map<String, Treasure>>
{

	public StringTreasureXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringTreasureList
	{
		public List<StringTreasureEntry> entry = new LinkedList<StringTreasureEntry>();
	}

	public static class StringTreasureEntry
	{
		@XmlAttribute
		public String key;

		@XmlElements({ @XmlElement(name = "treasure", type = TreasureImpl.class) })
		public Treasure value;

		public StringTreasureEntry(String key, Treasure value)
		{
			this.key = key;
			this.value = value;
		}

		public StringTreasureEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Treasure> unmarshal(StringTreasureList value) throws Exception
	{
		Map<String, Treasure> result = new LinkedHashMap<String, Treasure>();
		if (value != null)
		{
			for (StringTreasureEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringTreasureList marshal(Map<String, Treasure> value) throws Exception
	{
		StringTreasureList result = new StringTreasureList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Treasure> entry : value.entrySet())
			{
				result.entry.add(new StringTreasureEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
