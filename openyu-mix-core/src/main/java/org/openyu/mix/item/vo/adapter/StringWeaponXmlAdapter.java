package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.impl.WeaponImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringWeaponXmlAdapter extends
		BaseXmlAdapterSupporter<StringWeaponXmlAdapter.StringWeaponList, Map<String, Weapon>>
{

	public StringWeaponXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringWeaponList
	{
		public List<StringWeaponEntry> entry = new LinkedList<StringWeaponEntry>();
	}

	public static class StringWeaponEntry
	{
		@XmlAttribute
		public String key;

		@XmlElement(type = WeaponImpl.class)
		public Weapon value;

		public StringWeaponEntry(String key, Weapon value)
		{
			this.key = key;
			this.value = value;
		}

		public StringWeaponEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Weapon> unmarshal(StringWeaponList value) throws Exception
	{
		Map<String, Weapon> result = new LinkedHashMap<String, Weapon>();
		if (value != null)
		{
			for (StringWeaponEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringWeaponList marshal(Map<String, Weapon> value) throws Exception
	{
		StringWeaponList result = new StringWeaponList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Weapon> entry : value.entrySet())
			{
				result.entry.add(new StringWeaponEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
