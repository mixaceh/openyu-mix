package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.impl.MaterialImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringMaterialXmlAdapter extends
		BaseXmlAdapterSupporter<StringMaterialXmlAdapter.StringMaterialList, Map<String, Material>>
{

	public StringMaterialXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringMaterialList
	{
		public List<StringMaterialEntry> entry = new LinkedList<StringMaterialEntry>();
	}

	public static class StringMaterialEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = MaterialImpl.class)
		public Material value;

		public StringMaterialEntry(String key, Material value)
		{
			this.key = key;
			this.value = value;
		}

		public StringMaterialEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Material> unmarshal(StringMaterialList value) throws Exception
	{
		Map<String, Material> result = new LinkedHashMap<String, Material>();
		if (value != null)
		{
			for (StringMaterialEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringMaterialList marshal(Map<String, Material> value) throws Exception
	{
		StringMaterialList result = new StringMaterialList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Material> entry : value.entrySet())
			{
				result.entry.add(new StringMaterialEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
