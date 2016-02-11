package org.openyu.mix.flutter.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<careerTypes key="WARRIOR">1</careerTypes>
//--------------------------------------------------
public class CareerTypeXmlAdapter extends
		BaseXmlAdapterSupporter<CareerTypeXmlAdapter.CareerTypeEntry, CareerType>
{

	public CareerTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class CareerTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public String value;

		public CareerTypeEntry(String key, int value, int level)
		{
			this.key = key;
			this.value = value + "," + level;
		}

		public CareerTypeEntry()
		{}
	}

	// --------------------------------------------------
	public CareerType unmarshal(CareerTypeEntry value) throws Exception
	{
		CareerType result = null;
		//
		if (value != null)
		{
			result = CareerType.valueOf(value.key);
		}
		return result;
	}

	public CareerTypeEntry marshal(CareerType value) throws Exception
	{
		CareerTypeEntry result = null;
		//
		if (value != null)
		{
			result = new CareerTypeEntry(value.name(), value.getValue(), value.levelValue());
		}
		return result;
	}
}
