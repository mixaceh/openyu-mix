package org.openyu.mix.item.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.adapter.StringThingXmlAdapter;
import org.openyu.mix.item.vo.adapter.ThingTypeXmlAdapter;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.StringHelper;

/**
 * 道具數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "thingCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ThingCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 1507901194525580302L;

	private static ThingCollector thingCollector;

	/**
	 * 物品類別
	 */
	@XmlJavaTypeAdapter(ThingTypeXmlAdapter.class)
	private Set<ThingType> thingTypes = new LinkedHashSet<ThingType>();

	/**
	 * 所有物品 <id,thing>
	 */
	@XmlJavaTypeAdapter(StringThingXmlAdapter.class)
	private Map<String, Thing> things = new LinkedHashMap<String, Thing>();

	// --------------------------------------------------

	public ThingCollector() {
		setId(ThingCollector.class.getName());
	}

	public synchronized static ThingCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static ThingCollector getInstance(boolean initial) {
		if (thingCollector == null) {
			thingCollector = new ThingCollector();
			if (initial) {
				thingCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			thingCollector.thingTypes = EnumHelper.valuesSet(ThingType.class);
		}
		return thingCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!thingCollector.isInitialized()) {
			thingCollector = readFromSer(ThingCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (thingCollector == null) {
				thingCollector = new ThingCollector();
			}
			//
			thingCollector.setInitialized(true);
		}
	}

	public void clear() {
		things.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------

	public Set<ThingType> getThingTypes() {
		if (thingTypes == null) {
			thingTypes = new LinkedHashSet<ThingType>();
		}
		return thingTypes;
	}

	public void setThingTypes(Set<ThingType> thingTypes) {
		this.thingTypes = thingTypes;
	}

	public Map<String, Thing> getThings() {
		if (things == null) {
			things = new LinkedHashMap<String, Thing>();
		}
		return things;
	}

	public void setThings(Map<String, Thing> things) {
		this.things = things;
	}

	// --------------------------------------------------
	/**
	 * 取得clone物品
	 * 
	 * @param id
	 * @return
	 */
	public Thing getThing(String id) {
		Thing result = null;
		if (id != null) {
			result = things.get(id);
		}
		return (result != null ? (Thing) result.clone() : null);
	}

	public Thing createThing() {
		return createThing(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的thing,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 * @return
	 */
	public Thing createThing(String id) {
		Thing result = null;
		// 來自靜態資料的clone副本
		result = getThing(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new ThingImpl();
			result.setId(id);
		}
		result.setUniqueId(Thing.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		return result;
	}

	/**
	 * 依物品類別取得clone物品集合
	 * 
	 * @param thingType
	 * @return
	 */
	public List<Thing> getThings(ThingType thingType) {
		List<Thing> result = new LinkedList<Thing>();
		if (thingType != null) {
			for (Thing thing : things.values()) {
				if (thingType.equals(thing.getThingType())) {
					result.add(getThing(thing.getId()));
				}
			}
		}
		return result;
	}

	/**
	 * 物品是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean containThing(String id) {
		boolean result = false;
		if (id != null) {
			result = things.containsKey(id);
		}
		return result;
	}

	/**
	 * 物品是否存在
	 * 
	 * @param thing
	 * @return
	 */
	public boolean containThing(Thing thing) {
		boolean result = false;
		if (thing != null) {
			result = things.containsKey(thing.getId());
		}
		return result;
	}

	/**
	 * 取得所有物品id
	 * 
	 * @return
	 */
	public List<String> getThingIds() {
		List<String> result = new LinkedList<String>();
		for (String id : things.keySet()) {
			result.add(id);
		}
		return result;
	}

	/**
	 * 動態加載只是為了測試用,非測試不用
	 * 
	 * @param newThings
	 */
	protected <T extends Thing> void addThings(List<T> newThings) {
		if (newThings != null) {
			for (Thing thing : newThings) {
				if (thing != null) {
					things.put(thing.getId(), thing);
				}
			}
		}
	}
}
