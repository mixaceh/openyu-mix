package org.openyu.mix.manor.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.impl.LandImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringLandXmlAdapter extends
		BaseXmlAdapterSupporter<StringLandXmlAdapter.StringLandList, Map<String, Land>>
{

	public StringLandXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringLandList
	{
		public List<StringLandEntry> entry = new LinkedList<StringLandEntry>();
	}

	public static class StringLandEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = LandImpl.class)
		public Land value;

		public StringLandEntry(String key, Land value)
		{
			this.key = key;
			this.value = value;
		}

		public StringLandEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Land> unmarshal(StringLandList value) throws Exception
	{
		Map<String, Land> result = new LinkedHashMap<String, Land>();
		if (value != null)
		{
			for (StringLandEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringLandList marshal(Map<String, Land> value) throws Exception
	{
		StringLandList result = new StringLandList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Land> entry : value.entrySet())
			{
				result.entry.add(new StringLandEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
