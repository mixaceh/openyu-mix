package org.openyu.mix.flutter.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.flutter.vo.Career;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.impl.CareerImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<careers>
//	<entry key="WARRIOR">
//	    <value>
//	        <careerType>WARRIOR</careerType>
//	        <attributes>
//	            <entry key="STRENGTH">
//	                <value>
//	                    <attributeType>STRENGTH</attributeType>
//	                    <basePoint>20</basePoint>
//	                    <extraPoint>5</extraPoint>
//	                    <percentPoint>10</percentPoint>
//	                </value>
//	            </entry>
//	            <entry key="MAX_HEALTH">
//	                <value>
//	                    <attributeType>MAX_HEALTH</attributeType>
//	                    <basePoint>500</basePoint>
//	                    <extraPoint>50</extraPoint>
//	                    <percentPoint>5</percentPoint>
//	                </value>
//	            </entry>
//	            <entry key="MAX_MANA">
//	                <value>
//	                    <attributeType>MAX_MANA</attributeType>
//	                    <basePoint>250</basePoint>
//	                    <extraPoint>50</extraPoint>
//	                    <percentPoint>5</percentPoint>
//	                </value>
//	            </entry>
//	        </attributes>
//	    </value>
//	</entry>
//</careers>
public class CareerTypeCareerXmlAdapter
		extends
		BaseXmlAdapterSupporter<CareerTypeCareerXmlAdapter.CareerTypeCareerList, Map<CareerType, Career>>
{

	public CareerTypeCareerXmlAdapter()
	{}

	// --------------------------------------------------
	public static class CareerTypeCareerList
	{
		public List<CareerTypeCareerEntry> entry = new LinkedList<CareerTypeCareerEntry>();
	}

	public static class CareerTypeCareerEntry
	{
		@XmlAttribute
		public CareerType key;

		@XmlElement(type = CareerImpl.class)
		public Career value;

		public CareerTypeCareerEntry(CareerType key, Career value)
		{
			this.key = key;
			this.value = value;
		}

		public CareerTypeCareerEntry()
		{}
	}

	// --------------------------------------------------
	public Map<CareerType, Career> unmarshal(CareerTypeCareerList value)
		throws Exception
	{
		Map<CareerType, Career> result = new LinkedHashMap<CareerType, Career>();
		if (value != null)
		{
			for (CareerTypeCareerEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public CareerTypeCareerList marshal(Map<CareerType, Career> value)
		throws Exception
	{
		CareerTypeCareerList result = new CareerTypeCareerList();
		//
		if (value != null)
		{
			for (Map.Entry<CareerType, Career> entry : value.entrySet())
			{
				result.entry.add(new CareerTypeCareerEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
