package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.impl.BackInstantTownThingImpl;
import org.openyu.mix.item.vo.thing.impl.BackTownThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceArmorThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceLandThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceWeaponThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionHpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionInstantHpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionInstantMpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionMpThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleExpThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleFameThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleGoldThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleSpThingImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class StringThingXmlAdapter extends
		BaseXmlAdapterSupporter<StringThingXmlAdapter.StringThingList, Map<String, Thing>>
{

	public StringThingXmlAdapter()
	{}

	// --------------------------------------------------
	public static class StringThingList
	{
		public List<StringThingEntry> entry = new LinkedList<StringThingEntry>();
	}

	public static class StringThingEntry
	{
		@XmlAttribute
		public String key;

		@XmlElements({ @XmlElement(name = "thing", type = ThingImpl.class),
				@XmlElement(name = "potionHpThing", type = PotionHpThingImpl.class),
				@XmlElement(name = "potionMpThing", type = PotionMpThingImpl.class),
				@XmlElement(name = "potionInstantHpThing", type = PotionInstantHpThingImpl.class),
				@XmlElement(name = "potionInstantMpThing", type = PotionInstantMpThingImpl.class),
				@XmlElement(name = "backTownThing", type = BackTownThingImpl.class),
				@XmlElement(name = "backInstantTownThing", type = BackInstantTownThingImpl.class),
				@XmlElement(name = "enhanceArmorThing", type = EnhanceArmorThingImpl.class),
				@XmlElement(name = "enhanceWeaponThing", type = EnhanceWeaponThingImpl.class),
				@XmlElement(name = "enhanceLandThing", type = EnhanceLandThingImpl.class),
				@XmlElement(name = "roleExpThing", type = RoleExpThingImpl.class),
				@XmlElement(name = "roleSpThing", type = RoleSpThingImpl.class),
				@XmlElement(name = "roleGoldThing", type = RoleGoldThingImpl.class),
				@XmlElement(name = "roleFameThing", type = RoleFameThingImpl.class),
		//
		})
		/**
		 * @XmlElements 記得要加上新定義的class
		 * 如: @XmlElement(name = "enhanceLandThing", type = EnhanceLandThingImpl.class),
		 * @see org.openyu.mix.item.vo.adapter.StringThingXmlAdapter
		 */
		public Thing value;

		public StringThingEntry(String key, Thing value)
		{
			this.key = key;
			this.value = value;
		}

		public StringThingEntry()
		{}

	}

	// --------------------------------------------------
	public Map<String, Thing> unmarshal(StringThingList value) throws Exception
	{
		Map<String, Thing> result = new LinkedHashMap<String, Thing>();
		if (value != null)
		{
			for (StringThingEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public StringThingList marshal(Map<String, Thing> value) throws Exception
	{
		StringThingList result = new StringThingList();
		//
		if (value != null)
		{
			for (Map.Entry<String, Thing> entry : value.entrySet())
			{
				result.entry.add(new StringThingEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
