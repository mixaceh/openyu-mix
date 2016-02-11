package org.openyu.mix.activity.vo.target.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.openyu.mix.activity.vo.target.Target;
import org.openyu.mix.activity.vo.target.impl.CoinTargetImpl;
import org.openyu.mix.activity.vo.target.impl.FameTargetImpl;
import org.openyu.mix.activity.vo.target.impl.VipTargetImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class IntegerTargetXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerTargetXmlAdapter.IntegerTargetList, Map<Integer, Target>>
{

	public IntegerTargetXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerTargetList
	{
		public List<IntegerTargetEntry> entry = new LinkedList<IntegerTargetEntry>();
	}

	public static class IntegerTargetEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElements({ @XmlElement(name = "coinTarget", type = CoinTargetImpl.class),
				@XmlElement(name = "fameTarget", type = FameTargetImpl.class),
				@XmlElement(name = "vipTarget", type = VipTargetImpl.class) })
		public Target value;

		public IntegerTargetEntry(Integer key, Target value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerTargetEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, Target> unmarshal(IntegerTargetList value) throws Exception
	{
		Map<Integer, Target> result = new LinkedHashMap<Integer, Target>();
		if (value != null)
		{
			for (IntegerTargetEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerTargetList marshal(Map<Integer, Target> value) throws Exception
	{
		IntegerTargetList result = new IntegerTargetList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, Target> entry : value.entrySet())
			{
				result.entry.add(new IntegerTargetEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
