package org.openyu.mix.flutter.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.flutter.vo.Race;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.RaceImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<races>
//	<entry key="HUMAN">
//	    <value>
//	        <raceType>HUMAN</raceType>
//	        <attributes>
//	            <entry key="STRENGTH">
//	                <value>
//	                    <attributeType>STRENGTH</attributeType>
//	                    <basePoint>40</basePoint>
//	                    <extraPoint>10</extraPoint>
//	                    <percentPoint>10</percentPoint>
//	                </value>
//	            </entry>
//	            <entry key="MAX_HEALTH">
//	                <value>
//	                    <attributeType>MAX_HEALTH</attributeType>
//	                    <basePoint>1000</basePoint>
//	                    <extraPoint>100</extraPoint>
//	                    <percentPoint>10</percentPoint>
//	                </value>
//	            </entry>
//	            <entry key="MAX_MANA">
//	                <value>
//	                    <attributeType>MAX_MANA</attributeType>
//	                    <basePoint>500</basePoint>
//	                    <extraPoint>100</extraPoint>
//	                    <percentPoint>10</percentPoint>
//	                </value>
//	            </entry>
//	        </attributes>
//	    </value>
//	</entry>
//<races>
public class RaceTypeRaceXmlAdapter extends
		BaseXmlAdapterSupporter<RaceTypeRaceXmlAdapter.RaceTypeRaceList, Map<RaceType, Race>>
{

	public RaceTypeRaceXmlAdapter()
	{}

	// --------------------------------------------------
	public static class RaceTypeRaceList
	{
		public List<RaceTypeRaceEntry> entry = new LinkedList<RaceTypeRaceEntry>();
	}

	public static class RaceTypeRaceEntry
	{
		@XmlAttribute
		public RaceType key;

		@XmlElement(type = RaceImpl.class)
		public Race value;

		public RaceTypeRaceEntry(RaceType key, Race value)
		{
			this.key = key;
			this.value = value;
		}

		public RaceTypeRaceEntry()
		{}
	}

	// --------------------------------------------------
	public Map<RaceType, Race> unmarshal(RaceTypeRaceList value) throws Exception
	{
		Map<RaceType, Race> result = new LinkedHashMap<RaceType, Race>();
		if (value != null)
		{
			for (RaceTypeRaceEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public RaceTypeRaceList marshal(Map<RaceType, Race> value) throws Exception
	{
		RaceTypeRaceList result = new RaceTypeRaceList();
		//
		if (value != null)
		{
			for (Map.Entry<RaceType, Race> entry : value.entrySet())
			{
				result.entry.add(new RaceTypeRaceEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
