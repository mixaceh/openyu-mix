package org.openyu.mix.item.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.impl.ArmorImpl;
import org.openyu.mix.item.vo.impl.MaterialImpl;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.impl.WeaponImpl;
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
import org.openyu.mix.manor.vo.impl.LandImpl;
import org.openyu.mix.manor.vo.impl.SeedImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------

public class IntegerItemXmlAdapter extends
		BaseXmlAdapterSupporter<IntegerItemXmlAdapter.IntegerItemList, Map<Integer, Item>>
{

	public IntegerItemXmlAdapter()
	{}

	// --------------------------------------------------
	public static class IntegerItemList
	{
		public List<IntegerItemEntry> entry = new LinkedList<IntegerItemEntry>();
	}

	public static class IntegerItemEntry
	{
		@XmlAttribute
		public Integer key;

		@XmlElements({
				@XmlElement(name = "thing", type = ThingImpl.class),
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
				@XmlElement(name = "roleGoldThing", type = RoleGoldThingImpl.class),
				@XmlElement(name = "roleFameThing", type = RoleFameThingImpl.class),

				//
				@XmlElement(name = "material", type = MaterialImpl.class),
				@XmlElement(name = "armor", type = ArmorImpl.class),
				@XmlElement(name = "weapon", type = WeaponImpl.class),
				//莊園
				@XmlElement(name = "seed", type = SeedImpl.class),
				@XmlElement(name = "land", type = LandImpl.class),

		//
		})
		/**
		 * @XmlElements 記得要加上新定義的class
		 * 如: @XmlElement(name = "enhanceLandThing", type = EnhanceLandThingImpl.class),
		 * @see org.openyu.mix.item.vo.adapter.StringThingXmlAdapter
		 */
		public Item value;

		public IntegerItemEntry(Integer key, Item value)
		{
			this.key = key;
			this.value = value;
		}

		public IntegerItemEntry()
		{}

	}

	// --------------------------------------------------
	public Map<Integer, Item> unmarshal(IntegerItemList value) throws Exception
	{
		Map<Integer, Item> result = new LinkedHashMap<Integer, Item>();
		if (value != null)
		{
			for (IntegerItemEntry entry : value.entry)
			{
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public IntegerItemList marshal(Map<Integer, Item> value) throws Exception
	{
		IntegerItemList result = new IntegerItemList();
		//
		if (value != null)
		{
			for (Map.Entry<Integer, Item> entry : value.entrySet())
			{
				result.entry.add(new IntegerItemEntry(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
