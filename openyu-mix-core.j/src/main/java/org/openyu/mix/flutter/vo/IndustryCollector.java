package org.openyu.mix.flutter.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.lang.StringHelper;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.adapter.AttributeTypeAttributeFactorXmlAdapter;
import org.openyu.mix.flutter.vo.impl.IndustryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 行業數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "industryCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class IndustryCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 8011622155536265679L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(IndustryCollector.class);

	private static IndustryCollector industryCollector;

	/**
	 * 種族
	 */
	private static RaceCollector raceCollector;

	/**
	 * 職業
	 */
	private static CareerCollector careerCollector;

	/**
	 * 最高等級
	 * 
	 * 裝備最高等級201,Equipment.LevelType.V
	 */
	private int maxLevel = 60;

	/**
	 * 種族因子,用來加乘屬性值,萬分位(2000)
	 * 
	 * <種族類別,萬分位數=1000,2000...n>
	 */
	private Map<RaceType, Integer> raceFactors = new LinkedHashMap<RaceType, Integer>();

	/**
	 * 職業因子,用來加乘屬性值,萬分位(2000)
	 * 
	 * <職業類別,萬分位數=1000,2000...n>
	 */
	private Map<CareerType, Integer> careerFactors = new LinkedHashMap<CareerType, Integer>();

	/**
	 * 屬性因子
	 */
	@XmlJavaTypeAdapter(AttributeTypeAttributeFactorXmlAdapter.class)
	private Map<AttributeType, AttributeFactor> attributeFactors = new LinkedHashMap<AttributeType, AttributeFactor>();

	/**
	 * 行業,來自於種族+職業=(RaceCollector+CareerCollector)
	 * 
	 * 非直接來自於xml
	 * 
	 * 1.屬性
	 * 
	 * point=初值
	 * 
	 * addPoint=成長值
	 * 
	 * addRate=成長率
	 * 
	 */
	private transient Map<String, Industry> industrys = new LinkedHashMap<String, Industry>();

	/**
	 * 所有的經驗表
	 * 
	 * <等級,經驗>
	 */
	private Map<Integer, Long> exps = new LinkedHashMap<Integer, Long>();

	public IndustryCollector() {
		setId(IndustryCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static IndustryCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static IndustryCollector getInstance(boolean initial) {
		if (industryCollector == null) {
			industryCollector = new IndustryCollector();
			if (initial) {
				industryCollector.initialize();
			}
		}
		return industryCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!industryCollector.isInitialized()) {
			raceCollector = RaceCollector.getInstance();
			careerCollector = CareerCollector.getInstance();
			//
			industryCollector = readFromSer(IndustryCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (industryCollector == null) {
				industryCollector = new IndustryCollector();
			}

			// 行業=種族+職業
			industryCollector.industrys = buildIndestrys();
			//
			industryCollector.setInitialized(true);
		}
	}

	public void clear() {
		raceFactors.clear();
		careerFactors.clear();
		attributeFactors.clear();
		industrys.clear();
		exps.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------

	/**
	 * 內部建構行業,主要來自於種族+職業
	 * 
	 * @return
	 */
	protected Map<String, Industry> buildIndestrys() {
		Map<String, Industry> result = new LinkedHashMap<String, Industry>();
		for (RaceType raceType : RaceType.values()) {
			// 種族
			Race race = raceCollector.getRace(raceType);
			if (race == null) {
				// log.warn("[" + raceType + "_*" +
				// "] not exist in XML to be created");
				continue;
			}
			for (CareerType careerType : CareerType.values()) {
				// 職業
				Career career = careerCollector.getCareer(careerType);
				if (career == null) {
					// log.warn("[" + raceType + "_" + careerType +
					// "] not exist in XML to be created");
					continue;
				}
				//
				String industryId = buildIndustryId(raceType, careerType);

				if (StringHelper.isEmpty(industryId)) {
					continue;
				}
				//
				Industry industry = new IndustryImpl();
				industry.setId(industryId);
				industry.setRaceType(raceType);// 種族
				industry.setCareerType(careerType);// 職業
				//
				Map<AttributeType, Attribute> attributes = buildAttrubutes(
						race, career);
				industry.getAttributeGroup().setAttributes(attributes);
				//
				result.put(industry.getId(), industry);
			}
		}
		return result;
	}

	/**
	 * 建構行業id
	 * 
	 * HUMAN_WARRIOR_1
	 * 
	 * @param raceType
	 * @param careerType
	 * @return
	 */
	protected String buildIndustryId(RaceType raceType, CareerType careerType) {
		StringBuilder result = new StringBuilder();
		if (raceType != null && careerType != null) {
			result.append(raceType.name());
			result.append("_");
			result.append(careerType.name());
		}
		//
		return result.toString();
	}

	public Industry getIndustry(RaceType raceType, CareerType careerType) {
		return getIndustry(buildIndustryId(raceType, careerType));
	}

	/**
	 * 取得行業,,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Industry getIndustry(String id) {
		Industry result = null;
		if (id != null) {
			result = industrys.get(id);
		}
		// return (result != null ? (Industry) result.clone() : null);
		return result;
	}

	/**
	 * 建構種族職業屬性
	 * 
	 * 屬性=種族屬性(race)+職業屬性(career)
	 * 
	 * @param raceType
	 * @param career
	 * @return
	 */
	protected Map<AttributeType, Attribute> buildAttrubutes(Race race,
			Career career) {
		Map<AttributeType, Attribute> result = new LinkedHashMap<AttributeType, Attribute>();

		if (race != null && career != null) {
			// 種族因子
			double raceFactor = ratio(industryCollector.getRaceFactor(race
					.getId()));
			// 種族
			for (Map.Entry<AttributeType, Attribute> entry : race
					.getAttributeGroup().getAttributes().entrySet()) {
				AttributeType key = entry.getKey();
				Attribute value = entry.getValue();
				Attribute industryAttrubute = (Attribute) value.clone();
				result.put(key, industryAttrubute);
			}

			// 職業因子
			double careerFactor = ratio(industryCollector
					.getCareerFactor(career.getId()));
			// 職業
			for (Map.Entry<AttributeType, Attribute> entry : career
					.getAttributeGroup().getAttributes().entrySet()) {
				AttributeType key = entry.getKey();
				Attribute value = entry.getValue();
				Attribute careerAttrubute = (Attribute) value.clone();
				//
				Attribute industryAttrubute = result.get(key);
				if (industryAttrubute == null)// 若無屬性,放入屬性
				{
					result.put(key, careerAttrubute);
				} else {
					// 若已有屬性,則種族與職業乘上因子後,累加屬性

					// 初值
					int point = (int) (industryAttrubute.getPoint()
							* raceFactor + careerAttrubute.getPoint()
							* careerFactor);
					industryAttrubute.setPoint(point);
					// 成長值
					int addPoint = (int) (industryAttrubute.getAddPoint()
							* raceFactor + careerAttrubute.getAddPoint()
							* careerFactor);
					industryAttrubute.setAddPoint(addPoint);
					// 成長率
					int addRate = (int) (industryAttrubute.getAddRate()
							* raceFactor + careerAttrubute.getAddRate()
							* careerFactor);
					industryAttrubute.setAddRate(addRate);
				}
			}
		}
		return result;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Map<RaceType, Integer> getRaceFactors() {
		if (raceFactors == null) {
			raceFactors = new LinkedHashMap<RaceType, Integer>();
		}
		return raceFactors;
	}

	public void setRaceFactors(Map<RaceType, Integer> raceFactors) {
		this.raceFactors = raceFactors;
	}

	public int getRaceFactor(RaceType raceType) {
		int result = 0;
		if (raceType != null) {
			result = raceFactors.get(raceType);
		}
		return result;
	}

	public Map<CareerType, Integer> getCareerFactors() {
		if (careerFactors == null) {
			careerFactors = new LinkedHashMap<CareerType, Integer>();
		}
		return careerFactors;
	}

	public void setCareerFactors(Map<CareerType, Integer> careerFactors) {
		this.careerFactors = careerFactors;
	}

	public int getCareerFactor(CareerType careerType) {
		int result = 0;
		if (careerType != null) {
			result = careerFactors.get(careerType);
		}
		return result;
	}

	public Map<AttributeType, AttributeFactor> getAttributeFactors() {
		if (attributeFactors == null) {
			attributeFactors = new LinkedHashMap<AttributeType, AttributeFactor>();
		}
		return attributeFactors;
	}

	public void setAttributeFactors(
			Map<AttributeType, AttributeFactor> attributeFactors) {
		this.attributeFactors = attributeFactors;
	}

	public AttributeFactor getAttributeFactor(AttributeType attributeType) {
		AttributeFactor result = null;
		if (attributeType != null) {
			result = attributeFactors.get(attributeType);
		}
		return result;
	}

	public Map<String, Industry> getIndustrys() {
		if (industrys == null) {
			industrys = new LinkedHashMap<String, Industry>();
		}
		return industrys;
	}

	public void setIndustrys(Map<String, Industry> industrys) {
		this.industrys = industrys;
	}

	// --------------------------------------------------

	public void setExps(Map<Integer, Long> exps) {
		this.exps = exps;
	}

	public Map<Integer, Long> getExps() {
		if (exps == null) {
			exps = new LinkedHashMap<Integer, Long>();
		}
		return exps;
	}

	public long getExp(int level) {
		return safeGet(exps.get(level));
	}

}
