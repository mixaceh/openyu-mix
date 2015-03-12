package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.impl.ArmorImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringArmorXmlAdapter extends
		BaseXmlAdapterSupporter<StringArmorXmlAdapter.StringArmorList, Map<String, Armor>>
{

	public StringArmorXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringArmorList
	{
		public List<StringArmorEntry> entry = new LinkedList<StringArmorEntry>();
	}

	public static class StringArmorEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = ArmorImpl.class)
		public Armor value;

		public StringArmorEntry(String key, Armor value)
		{
			this.key = key;
			this.value = value;
		}

		public StringArmorEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Armor> unmarshal(StringArmorList value) throws Exception
	{
		Map<String, Armor> result = new LinkedHashMap<String, Armor>();
		if (value != null)
		{
			for (StringArmorEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringArmorList marshal(Map<String, Armor> value) throws Exception
	{
		StringArmorList result = new StringArmorList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Armor> entry : value.entrySet())
			{
				result.entry.add(new StringArmorEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
