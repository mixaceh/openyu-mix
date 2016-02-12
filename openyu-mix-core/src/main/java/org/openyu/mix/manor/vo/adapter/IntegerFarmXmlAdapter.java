package org.openyu.mix.manor.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.manor.vo.ManorInfo.Farm;
import org.openyu.mix.manor.vo.impl.ManorInfoImpl.FarmImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class IntegerFarmXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerFarmXmlAdapter.IntegerFarmList, Map<Integer, Farm>>
{

	public IntegerFarmXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerFarmList
	{
		public List<IntegerFarmEntry> entry = new LinkedList<IntegerFarmEntry>();
	}

	public static class IntegerFarmEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElement(type = FarmImpl.class)
		public Farm value;

		public IntegerFarmEntry(Integer key, Farm value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerFarmEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, Farm> unmarshal(IntegerFarmList value) throws Exception
	{
		Map<Integer, Farm> result = new LinkedHashMap<Integer, Farm>();
		if (value != null)
		{
			for (IntegerFarmEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerFarmList marshal(Map<Integer, Farm> value) throws Exception
	{
		IntegerFarmList result = new IntegerFarmList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, Farm> entry : value.entrySet())
			{
				result.entry.add(new IntegerFarmEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
