package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.item.vo.WeaponType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<weaponTypes key="SWORD">1</weaponTypes>
//--------------------------------------------------
public class WeaponTypeXmlAdapter extends
		BaseXmlAdapterSupporter<WeaponTypeXmlAdapter.WeaponTypeEntry, WeaponType>
{

	public WeaponTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class WeaponTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public WeaponTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public WeaponTypeEntry()
		{}
	}

	// --------------------------------------------------
	public WeaponType unmarshal(WeaponTypeEntry value) throws Exception
	{
		WeaponType result = null;
		//
		if (value != null)
		{
			result = WeaponType.valueOf(value.key);
		}
		return result;
	}

	public WeaponTypeEntry marshal(WeaponType value) throws Exception
	{
		WeaponTypeEntry result = null;
		//
		if (value != null)
		{
			result = new WeaponTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
