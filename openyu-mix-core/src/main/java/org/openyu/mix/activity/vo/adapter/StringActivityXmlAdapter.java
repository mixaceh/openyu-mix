package org.openyu.mix.activity.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.openyu.mix.activity.vo.Activity;
import org.openyu.mix.activity.vo.target.impl.AccuCoinTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.DailyCoinTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.FameTargetActivityImpl;
import org.openyu.mix.activity.vo.target.impl.VipTargetActivityImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringActivityXmlAdapter extends
		BaseXmlAdapterSupporter<StringActivityXmlAdapter.StringActivityList, Map<String, Activity>>
{

	public StringActivityXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringActivityList
	{
		public List<StringActivityEntry> entry = new LinkedList<StringActivityEntry>();
	}

	public static class StringActivityEntry
	{
		@XmlAttribute
		public String key;

		@XmlElements({ @XmlElement(name = "dailyCoinActivity", type = DailyCoinTargetActivityImpl.class),
				@XmlElement(name = "accuCoinActivity", type = AccuCoinTargetActivityImpl.class),
				@XmlElement(name = "fameActivity", type = FameTargetActivityImpl.class),
				@XmlElement(name = "vipActivity", type = VipTargetActivityImpl.class) })
		public Activity value;

		public StringActivityEntry(String key, Activity value)
		{
			this.key = key;
			this.value = value;
		}

		public StringActivityEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Activity> unmarshal(StringActivityList value) throws Exception
	{
		Map<String, Activity> result = new LinkedHashMap<String, Activity>();
		if (value != null)
		{
			for (StringActivityEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringActivityList marshal(Map<String, Activity> value) throws Exception
	{
		StringActivityList result = new StringActivityList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Activity> entry : value.entrySet())
			{
				result.entry.add(new StringActivityEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
