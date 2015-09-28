package org.openyu.mix.flutter.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
public final class CareerCollector extends BaseCollectorSupporter
{

	private static final long serialVersionUID = -366805549782373969L;

	private static CareerCollector careerCollector;

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

	/**
	 * 所有的職業
	 */
	@XmlJavaTypeAdapter(CareerTypeCareerXmlAdapter.class)
	private Map<CareerType, Career> careers = new LinkedHashMap<CareerType, Career>();

	public CareerCollector()
	{
		setId(CareerCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static CareerCollector getInstance()
	{
		return getInstance(true);
	}

	public synchronized static CareerCollector getInstance(boolean initial)
	{
		if (careerCollector == null)
		{
			careerCollector = new CareerCollector();
			if (initial)
			{
				careerCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			careerCollector.careerTypes = EnumHelper.valuesSet(CareerType.class);
			careerCollector.attributeTypes = EnumHelper.valuesSet(AttributeType.class);
		}
		return careerCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize()
	{
		if (!careerCollector.isInitialized())
		{
			careerCollector = readFromSer(CareerCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (careerCollector == null)
			{
				careerCollector = new CareerCollector();
			}
			//
			careerCollector.setInitialized(true);
		}
	}

	public void clear()
	{
		careers.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	public Set<CareerType> getCareerTypes()
	{
		if (careerTypes == null)
		{
			careerTypes = new LinkedHashSet<CareerType>();
		}
		return careerTypes;
	}

	public void setCareerTypes(Set<CareerType> careerTypes)
	{
		this.careerTypes = careerTypes;
	}

	public Set<AttributeType> getAttributeTypes()
	{
		if (attributeTypes == null)
		{
			attributeTypes = new LinkedHashSet<AttributeType>();
		}
		return attributeTypes;
	}

	public void setAttributeTypes(Set<AttributeType> attributeTypes)
	{
		this.attributeTypes = attributeTypes;
	}

	// --------------------------------------------------
	public Map<CareerType, Career> getCareers()
	{
		if (careers == null)
		{
			careers = new LinkedHashMap<CareerType, Career>();
		}
		return careers;
	}

	public void setCareers(Map<CareerType, Career> careers)
	{
		this.careers = careers;
	}

	/**
	 * 取得clone職業
	 * 
	 * @param careerType
	 * @return
	 */
	public Career getCareer(CareerType careerType)
	{
		Career result = null;
		if (careerType != null)
		{
			result = careers.get(careerType);
		}
		return (result != null ? (Career) result.clone() : null);
	}
	// --------------------------------------------------

}
