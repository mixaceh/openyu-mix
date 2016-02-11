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
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.adapter.AttributeTypeXmlAdapter;
import org.openyu.mix.flutter.vo.adapter.CareerTypeCareerXmlAdapter;
import org.openyu.mix.flutter.vo.adapter.CareerTypeXmlAdapter;

/**
 * 職業
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "careerCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class CareerCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -366805549782373969L;

	private static CareerCollector instance;

	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
	/**
	 * 所有的職業類別
	 */
	@XmlJavaTypeAdapter(CareerTypeXmlAdapter.class)
	private Set<CareerType> careerTypes = new LinkedHashSet<CareerType>();

	/**
	 * 所有的屬性類別
	 */
	@XmlJavaTypeAdapter(AttributeTypeXmlAdapter.class)
	private Set<AttributeType> attributeTypes = new LinkedHashSet<AttributeType>();

	// --------------------------------------------------
	// 企劃編輯用
	// --------------------------------------------------
	/**
	 * 所有的職業
	 */
	@XmlJavaTypeAdapter(CareerTypeCareerXmlAdapter.class)
	private Map<CareerType, Career> careers = new LinkedHashMap<CareerType, Career>();

	public CareerCollector() {
		setId(CareerCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static CareerCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static CareerCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(CareerCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new CareerCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			instance.careerTypes = EnumHelper.valuesSet(CareerType.class);
			instance.attributeTypes = EnumHelper.valuesSet(AttributeType.class);
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static CareerCollector shutdownInstance() {
		if (instance != null) {
			CareerCollector oldInstance = instance;
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
	public synchronized static CareerCollector restartInstance() {
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
		instance.careers.clear();
	}

	// --------------------------------------------------
	public Set<CareerType> getCareerTypes() {
		if (careerTypes == null) {
			careerTypes = new LinkedHashSet<CareerType>();
		}
		return careerTypes;
	}

	public void setCareerTypes(Set<CareerType> careerTypes) {
		this.careerTypes = careerTypes;
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
	public Map<CareerType, Career> getCareers() {
		if (careers == null) {
			careers = new LinkedHashMap<CareerType, Career>();
		}
		return careers;
	}

	public void setCareers(Map<CareerType, Career> careers) {
		this.careers = careers;
	}

	/**
	 * 取得clone職業
	 * 
	 * @param careerType
	 * @return
	 */
	public Career getCareer(CareerType careerType) {
		Career result = null;
		if (careerType != null) {
			result = careers.get(careerType);
		}
		return (result != null ? (Career) result.clone() : null);
	}
	// --------------------------------------------------

}
