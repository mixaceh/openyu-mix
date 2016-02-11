package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.item.vo.EnhanceFactor;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class EnhanceLevelEnhanceFactorXmlAdapter
		extends
		BaseXmlAdapterSupporter<EnhanceLevelEnhanceFactorXmlAdapter.EnhanceLevelEnhanceFactorList, Map<EnhanceLevel, EnhanceFactor>>
{

	public EnhanceLevelEnhanceFactorXmlAdapter()
	{}

	// --------------------------------------------------
	public static class EnhanceLevelEnhanceFactorList
	{
		public List<EnhanceLevelEnhanceFactorEntry> entry = new LinkedList<EnhanceLevelEnhanceFactorEntry>();
	}

	public static class EnhanceLevelEnhanceFactorEntry
	{
		@XmlAttribute
		public EnhanceLevel key;

		@XmlElement(type = EnhanceFactorImpl.class)
		public EnhanceFactor value;

		public EnhanceLevelEnhanceFactorEntry(EnhanceLevel key, EnhanceFactor value)
		{
			this.key = key;
			this.value = value;
		}

		public EnhanceLevelEnhanceFactorEntry()
		{}
	}

	// --------------------------------------------------
	public Map<EnhanceLevel, EnhanceFactor> unmarshal(EnhanceLevelEnhanceFactorList value) throws Exception
	{
		Map<EnhanceLevel, EnhanceFactor> result = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();
		if (value != null)
		{
			for (EnhanceLevelEnhanceFactorEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public EnhanceLevelEnhanceFactorList marshal(Map<EnhanceLevel, EnhanceFactor> value) throws Exception
	{
		EnhanceLevelEnhanceFactorList result = new EnhanceLevelEnhanceFactorList();
		//
		if (value != null)
		{
			for (Map.Entry<EnhanceLevel, EnhanceFactor> entry : value.entrySet())
			{
				result.entry.add(new EnhanceLevelEnhanceFactorEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
