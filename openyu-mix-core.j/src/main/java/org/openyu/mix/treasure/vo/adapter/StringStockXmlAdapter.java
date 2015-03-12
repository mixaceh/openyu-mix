package org.openyu.mix.treasure.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.openyu.mix.treasure.vo.Stock;
import org.openyu.mix.treasure.vo.impl.StockImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class StringStockXmlAdapter
		extends
		BaseXmlAdapterSupporter<StringStockXmlAdapter.StringTreasureStockList, Map<String, Stock>>
{

	public StringStockXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringTreasureStockList
	{
		public List<StringTreasureStockEntry> entry = new LinkedList<StringTreasureStockEntry>();
	}

	public static class StringTreasureStockEntry
	{
		@XmlAttribute
		public String key;

		@XmlElements({ @XmlElement(name = "stock", type = StockImpl.class) })
		public Stock value;

		public StringTreasureStockEntry(String key, Stock value)
		{
			this.key = key;
			this.value = value;
		}

		public StringTreasureStockEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Stock> unmarshal(StringTreasureStockList value) throws Exception
	{
		Map<String, Stock> result = new LinkedHashMap<String, Stock>();
		if (value != null)
		{
			for (StringTreasureStockEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringTreasureStockList marshal(Map<String, Stock> value) throws Exception
	{
		StringTreasureStockList result = new StringTreasureStockList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Stock> entry : value.entrySet())
			{
				result.entry.add(new StringTreasureStockEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
