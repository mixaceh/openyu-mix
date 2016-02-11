package org.openyu.mix.flutter.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
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

	private static RaceCollector instance;

	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
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

	// --------------------------------------------------
	// 企劃編輯用
	// --------------------------------------------------
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

	public synchronized static RaceCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(RaceCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new RaceCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.raceTypes = EnumHelper.valuesSet(RaceType.class);
			instance.attributeTypes = EnumHelper.valuesSet(AttributeType.class);
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static RaceCollector shutdownInstance() {
		if (instance != null) {
			RaceCollector oldInstance = instance;
			instance = null;
			//
			if (oldInstance != null) {
				oldInstance.shutdown();
			}
		}
		return instance;
	}

	/**
	 * 單例重啟
	 * 
	 * @return
	 */
	public synchronized static RaceCollector restartInstance() {
		if (instance != null) {
			instance.restart();
		}
		return instance;
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		instance.races.clear();
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
