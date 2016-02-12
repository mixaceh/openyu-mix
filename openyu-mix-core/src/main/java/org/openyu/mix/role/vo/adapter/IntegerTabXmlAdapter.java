package org.openyu.mix.role.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.BagInfo.Tab;
import org.openyu.mix.role.vo.impl.BagInfoImpl.TabImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class IntegerTabXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerTabXmlAdapter.IntegerTabList, Map<Integer, Tab>>
{

	public IntegerTabXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerTabList
	{
		public List<IntegerTabEntry> entry = new LinkedList<IntegerTabEntry>();
	}

	public static class IntegerTabEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElement(type = TabImpl.class)
		public BagInfo.Tab value;

		public IntegerTabEntry(Integer key, BagInfo.Tab value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerTabEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, BagInfo.Tab> unmarshal(IntegerTabList value) throws Exception
	{
		Map<Integer, BagInfo.Tab> result = new LinkedHashMap<Integer, BagInfo.Tab>();
		if (value != null)
		{
			for (IntegerTabEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerTabList marshal(Map<Integer, BagInfo.Tab> value) throws Exception
	{
		IntegerTabList result = new IntegerTabList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, BagInfo.Tab> entry : value.entrySet())
			{
				result.entry.add(new IntegerTabEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
