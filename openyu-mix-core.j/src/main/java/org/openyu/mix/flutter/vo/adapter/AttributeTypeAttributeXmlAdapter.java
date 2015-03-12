package org.openyu.mix.flutter.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<attributes>
//	<entry key="MAX_HEALTH">
//	    <value>
//	        <id>MAX_HEALTH</id>
//	        <point>400.0</point>
//	        <addPoint>4.0</addPoint>
//	        <addRate>0.0</addRate>
//	    </value>
//	</entry>
//	<entry key="MAX_MANA">
//	    <value>
//	        <id>MAX_MANA</id>
//	        <point>200.0</point>
//	        <addPoint>2.0</addPoint>
//	        <addRate>0.0</addRate>
//	    </value>
//	</entry>
//</attributes>
public class AttributeTypeAttributeXmlAdapter
		extends
		BaseXmlAdapterSupporter<AttributeTypeAttributeXmlAdapter.AttributeTypeAttributeList, Map<AttributeType, Attribute>>
{

	public AttributeTypeAttributeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class AttributeTypeAttributeList
	{
		public List<AttributeTypeAttributeEntry> entry = new LinkedList<AttributeTypeAttributeEntry>();
	}

	public static class AttributeTypeAttributeEntry
	{
		@XmlAttribute
		public AttributeType key;

		@XmlElement(type = AttributeImpl.class)
		public Attribute value;

		public AttributeTypeAttributeEntry(AttributeType key, Attribute value)
		{
			this.key = key;
			this.value = value;
		}

		public AttributeTypeAttributeEntry()
		{}

	}

	// --------------------------------------------------
	public Map<AttributeType, Attribute> unmarshal(AttributeTypeAttributeList value) throws Exception
	{
		Map<AttributeType, Attribute> result = new LinkedHashMap<AttributeType, Attribute>();
		if (value != null)
		{
			for (AttributeTypeAttributeEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public AttributeTypeAttributeList marshal(Map<AttributeType, Attribute> value) throws Exception
	{
		AttributeTypeAttributeList result = new AttributeTypeAttributeList();
		//
		if (value != null)
		{
			for (Map.Entry<AttributeType, Attribute> entry : value.entrySet())
			{
				result.entry.add(new AttributeTypeAttributeEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
