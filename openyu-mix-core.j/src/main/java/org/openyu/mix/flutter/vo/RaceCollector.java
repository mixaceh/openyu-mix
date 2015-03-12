package org.openyu.mix.flutter.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.adapter.AttributeTypeXmlAdapter;
import org.openyu.mix.flutter.vo.adapter.RaceTypeXmlAdapter;
import org.openyu.mix.flutter.vo.adapter.RaceTypeRaceXmlAdapter;

/**
 * 種族
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "raceCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class RaceCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -366805549782373969L;

	private static RaceCollector raceCollector;

	/**
	 * 所有的種族類別
	 */
	@XmlJavaTypeAdapter(RaceTypeXmlAdapter.class)
	private Set<RaceType> raceTypes = new LinkedHashSet<RaceType>();

	/**
	 * 所有的屬性類別
	 */
	@XmlJavaTypeAdapter(AttributeTypeXmlAdapter.class)
	private Set<AttributeType> attributeTypes = new LinkedHashSet<AttributeType>();

	/**
	 * 所有的種族
	 */
	@XmlJavaTypeAdapter(RaceTypeRaceXmlAdapter.class)
	private Map<RaceType, Race> races = new LinkedHashMap<RaceType, Race>();

	public RaceCollector() {
		setId(RaceCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static RaceCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static RaceCollector getInstance(boolean initial) {
		if (raceCollector == null) {
			raceCollector = new RaceCollector();
			if (initial) {
				raceCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			raceCollector.raceTypes = EnumHelper.valuesSet(RaceType.class);
			raceCollector.attributeTypes = EnumHelper
					.valuesSet(AttributeType.class);
		}
		return raceCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!raceCollector.isInitialized()) {
			raceCollector = readFromSer(RaceCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (raceCollector == null) {
				raceCollector = new RaceCollector();
			}
			//
			raceCollector.setInitialized(true);
		}
	}

	public void clear() {
		races.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	public Set<RaceType> getRaceTypes() {
		if (raceTypes == null) {
			raceTypes = new LinkedHashSet<RaceType>();
		}
		return raceTypes;
	}

	public void setRaceTypes(Set<RaceType> raceTypes) {
		this.raceTypes = raceTypes;
	}

	public Set<AttributeType> getAttributeTypes() {
		if (attributeTypes == null) {
			attributeTypes = new LinkedHashSet<AttributeType>();
		}
		return attributeTypes;
	}

	public void setAttributeTypes(Set<AttributeType> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}

	// --------------------------------------------------
	public Map<RaceType, Race> getRaces() {
		if (races == null) {
			races = new LinkedHashMap<RaceType, Race>();
		}
		return races;
	}

	public void setRaces(Map<RaceType, Race> races) {
		this.races = races;
	}

	/**
	 * 取得clone種族
	 * 
	 * @param raceType
	 * @return
	 */
	public Race getRace(RaceType raceType) {
		Race result = null;
		if (raceType != null) {
			result = races.get(raceType);
		}
		return (result != null ? (Race) result.clone() : null);
	}

	// --------------------------------------------------

}
