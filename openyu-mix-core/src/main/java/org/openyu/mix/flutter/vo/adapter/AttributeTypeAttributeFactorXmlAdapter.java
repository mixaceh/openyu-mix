package org.openyu.mix.flutter.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeFactor;
import org.openyu.mix.flutter.vo.impl.AttributeFactorImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<attributeFactors>
//<entry key="PHYSICAL_ATTACK">
//    <value>
//        <id>PHYSICAL_ATTACK</id>
//        <factor>2</factor>
//        <factor2>1</factor2>
//        <factor3>0</factor3>
//    </value>
//</entry>
//<entry key="PHYSICAL_DEFENCE">
//    <value>
//        <id>PHYSICAL_DEFENCE</id>
//        <factor>2</factor>
//        <factor2>0</factor2>
//        <factor3>0</factor3>
//    </value>
//</entry>
//</attributeFactors>
public class AttributeTypeAttributeFactorXmlAdapter
		extends
		BaseXmlAdapterSupporter<AttributeTypeAttributeFactorXmlAdapter.AttributeTypeAttributeFactorList, Map<AttributeType, AttributeFactor>>
{

	public AttributeTypeAttributeFactorXmlAdapter()
	{}

	// --------------------------------------------------
	public static class AttributeTypeAttributeFactorList
	{
		public List<AttributeTypeAttributeFactorEntry> entry = new LinkedList<AttributeTypeAttributeFactorEntry>();
	}

	public static class AttributeTypeAttributeFactorEntry
	{
		@XmlAttribute
		public AttributeType key;

		@XmlElement(type = AttributeFactorImpl.class)
		public AttributeFactor value;

		public AttributeTypeAttributeFactorEntry(AttributeType key, AttributeFactor value)
		{
			this.key = key;
			this.value = value;
		}

		public AttributeTypeAttributeFactorEntry()
		{}

	}

	// --------------------------------------------------
	public Map<AttributeType, AttributeFactor> unmarshal(AttributeTypeAttributeFactorList value) throws Exception
	{
		Map<AttributeType, AttributeFactor> result = new LinkedHashMap<AttributeType, AttributeFactor>();
		if (value != null)
		{
			for (AttributeTypeAttributeFactorEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public AttributeTypeAttributeFactorList marshal(Map<AttributeType, AttributeFactor> value) throws Exception
	{
		AttributeTypeAttributeFactorList result = new AttributeTypeAttributeFactorList();
		//
		if (value != null)
		{
			for (Map.Entry<AttributeType, AttributeFactor> entry : value.entrySet())
			{
				result.entry.add(new AttributeTypeAttributeFactorEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
