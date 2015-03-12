package org.openyu.mix.app.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<prizes>
//	<entry key="111">
//	    <value>
//	        <id>111</id>
//	        <level>1</level>
//	        <items>
//	            <entry>
//	                <key>POTION_0001</key>
//	                <value>10</value>
//	            </entry>
//	            <entry>
//	                <key>POTION_0002</key>
//	                <value>15</value>
//	            </entry>
//	        </items>
//	        <famous>true</famous>
//	    </value>
//	</entry>
//	<entry key="11">
//	    <value>
//	        <id>11</id>
//	        <level>2</level>
//	        <items>
//	            <entry>
//	                <key>POTION_0003</key>
//	                <value>20</value>
//	            </entry>
//	            <entry>
//	                <key>POTION_0004</key>
//	                <value>25</value>
//	            </entry>
//	        </items>
//	        <famous>true</famous>
//	    </value>
//	</entry>
//</prizes>
public class StringPrizeXmlAdapter extends
		BaseXmlAdapterSupporter<StringPrizeXmlAdapter.StringPrizeList, Map<String, Prize>>
{

	public StringPrizeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringPrizeList
	{
		public List<StringPrizeEntry> entry = new LinkedList<StringPrizeEntry>();
	}

	public static class StringPrizeEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = PrizeImpl.class)
		public Prize value;

		public StringPrizeEntry(String key, Prize value)
		{
			this.key = key;
			this.value = value;
		}

		public StringPrizeEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Prize> unmarshal(StringPrizeList value) throws Exception
	{
		Map<String, Prize> result = new LinkedHashMap<String, Prize>();
		if (value != null)
		{
			for (StringPrizeEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringPrizeList marshal(Map<String, Prize> value) throws Exception
	{
		StringPrizeList result = new StringPrizeList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Prize> entry : value.entrySet())
			{
				result.entry.add(new StringPrizeEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
