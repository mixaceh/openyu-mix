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

import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.adapter.EnhanceLevelEnhanceFactorXmlAdapter;
import org.openyu.mix.item.vo.adapter.EnhanceLevelXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentLevelTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentPositionTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentSeriesTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.StringArmorXmlAdapter;
import org.openyu.mix.item.vo.impl.ArmorImpl;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 防具數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "armorCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ArmorCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -5418860208675562994L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ArmorCollector.class);

	private static ArmorCollector instance;
	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
	/**
	 * 等級類別
	 */
	@XmlJavaTypeAdapter(EquipmentLevelTypeXmlAdapter.class)
	private Set<LevelType> levelTypes = new LinkedHashSet<LevelType>();

	/**
	 * 位置類別
	 */
	@XmlJavaTypeAdapter(EquipmentPositionTypeXmlAdapter.class)
	private Set<PositionType> positionTypes = new LinkedHashSet<PositionType>();

	/**
	 * 系列類別
	 */
	@XmlJavaTypeAdapter(EquipmentSeriesTypeXmlAdapter.class)
	private Set<SeriesType> seriesTypes = new LinkedHashSet<SeriesType>();

	/**
	 * 強化等級
	 */
	@XmlJavaTypeAdapter(EnhanceLevelXmlAdapter.class)
	private Set<EnhanceLevel> enhanceLevels = new LinkedHashSet<EnhanceLevel>();

	// --------------------------------------------------
	// 企劃編輯用
	// --------------------------------------------------
	/**
	 * 所有防具
	 */
	@XmlJavaTypeAdapter(StringArmorXmlAdapter.class)
	private Map<String, Armor> armors = new LinkedHashMap<String, Armor>();

	/**
	 * 強化因子,用來加乘屬性值
	 * 
	 * <強化等級,萬分位數=1000,2000...n>
	 */
	@XmlJavaTypeAdapter(EnhanceLevelEnhanceFactorXmlAdapter.class)
	private Map<EnhanceLevel, EnhanceFactor> enhanceFactors = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();

	/**
	 * 累計強化因子
	 * 
	 * (1,10) (2,20) (3,30) 把個別區間累加
	 * 
	 * (1,10) (2,30) (3,60)
	 */
	private transient Map<EnhanceLevel, EnhanceFactor> accuEnhanceFactors = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();

	/**
	 * 最高強化等級
	 */
	private EnhanceLevel maxEnhanceLevel = EnhanceLevel._10;

	/**
	 * 安全強化等級, <=3,不會爆掉, >3以上才有機率爆掉
	 */
	private EnhanceLevel safeEnhanceLevel = EnhanceLevel._3;

	/**
	 * 最高幻想強化等級,使用幻想強化道具,最高強化等級, >+9 無法使用
	 */
	private EnhanceLevel fantasyEnhanceLevel = EnhanceLevel._9;

	// --------------------------------------------------
	public ArmorCollector() {
		setId(ArmorCollector.class.getName());
	}

	public synchronized static ArmorCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static ArmorCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(ArmorCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new ArmorCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.levelTypes = EnumHelper.valuesSet(LevelType.class);
			instance.positionTypes = EnumHelper.valuesSet(PositionType.class);
			instance.seriesTypes = EnumHelper.valuesSet(SeriesType.class);
			instance.enhanceLevels = EnumHelper.valuesSet(EnhanceLevel.class);
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static ArmorCollector shutdownInstance() {
		if (instance != null) {
			ArmorCollector oldInstance = instance;
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
	public synchronized static ArmorCollector restartInstance() {
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
		// 累計防具強化因子
		instance.accuEnhanceFactors = buildAccuEnhanceFactors();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		armors.clear();
		accuEnhanceFactors.clear();
	}
	// --------------------------------------------------

	protected Map<EnhanceLevel, EnhanceFactor> buildAccuEnhanceFactors() {
		Map<EnhanceLevel, EnhanceFactor> result = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();

		int accuPoint = 0;// 累計值
		int accuRate = 0;// 累計比率
		double accuProbability = 1d;// 累計機率
		for (Map.Entry<EnhanceLevel, EnhanceFactor> entry : instance.enhanceFactors.entrySet()) {
			EnhanceLevel key = entry.getKey();
			EnhanceFactor value = entry.getValue();
			// 累計
			accuPoint += value.getPoint();
			accuRate += value.getRate();
			accuProbability *= ratio(value.getProbability());
			//
			EnhanceFactor enhanceFactor = new EnhanceFactorImpl(key);
			enhanceFactor.setPoint(accuPoint);
			enhanceFactor.setRate(accuRate);
			enhanceFactor.setProbability(((int) (accuProbability * 10000d)));
			// 強化機率為0了
			if (enhanceFactor.getProbability() == 0) {
				LOGGER.info("Armor [" + key + "] probability is zero");
			}
			//
			result.put(key, enhanceFactor);
		}
		return result;
	}

	public Map<String, Armor> getArmors() {
		if (armors == null) {
			armors = new LinkedHashMap<String, Armor>();
		}
		return armors;
	}

	public void setArmors(Map<String, Armor> armors) {
		this.armors = armors;
	}

	/**
	 * 取得等級類別
	 * 
	 * @return
	 */
	public Set<LevelType> getLevelTypes() {
		if (levelTypes == null) {
			levelTypes = new LinkedHashSet<LevelType>();
		}
		return levelTypes;
	}

	public void setLevelTypes(Set<LevelType> levelTypes) {
		this.levelTypes = levelTypes;
	}

	/**
	 * 取得位置類別
	 * 
	 * @return
	 */
	public Set<PositionType> getPositionTypes() {
		if (positionTypes == null) {
			positionTypes = new LinkedHashSet<PositionType>();
		}
		return positionTypes;
	}

	public void setPositionTypes(Set<PositionType> positionTypes) {
		this.positionTypes = positionTypes;
	}

	/**
	 * 取得系列類別
	 * 
	 * @return
	 */
	public Set<SeriesType> getSeriesTypes() {
		if (seriesTypes == null) {
			seriesTypes = new LinkedHashSet<SeriesType>();
		}
		return seriesTypes;
	}

	public void setSeriesTypes(Set<SeriesType> seriesTypes) {
		this.seriesTypes = seriesTypes;
	}

	/**
	 * 取得強化等級
	 * 
	 * @return
	 */
	public Set<EnhanceLevel> getEnhanceLevels() {
		if (enhanceLevels == null) {
			enhanceLevels = new LinkedHashSet<EnhanceLevel>();
		}
		return enhanceLevels;
	}

	public void setEnhanceLevels(Set<EnhanceLevel> enhanceLevels) {
		this.enhanceLevels = enhanceLevels;
	}

	/**
	 * 取得強化因子
	 * 
	 * @return
	 */
	public Map<EnhanceLevel, EnhanceFactor> getEnhanceFactors() {
		return enhanceFactors;
	}

	public void setEnhanceFactors(Map<EnhanceLevel, EnhanceFactor> enhanceFactors) {
		if (enhanceFactors == null) {
			enhanceFactors = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();
		}
		this.enhanceFactors = enhanceFactors;
	}

	/**
	 * 取得累計強化因子
	 * 
	 * @return
	 */
	public Map<EnhanceLevel, EnhanceFactor> getAccuEnhanceFactors() {
		if (accuEnhanceFactors == null) {
			accuEnhanceFactors = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();
		}
		return accuEnhanceFactors;
	}

	public void setAccuEnhanceFactors(Map<EnhanceLevel, EnhanceFactor> accuEnhanceFactors) {
		this.accuEnhanceFactors = accuEnhanceFactors;
	}

	/**
	 * 最高強化等級
	 * 
	 * @return
	 */
	public EnhanceLevel getMaxEnhanceLevel() {
		return maxEnhanceLevel;
	}

	public void setMaxEnhanceLevel(EnhanceLevel maxEnhanceLevel) {
		this.maxEnhanceLevel = maxEnhanceLevel;
	}

	/**
	 * 
	 * @return
	 */
	public EnhanceLevel getSafeEnhanceLevel() {
		return safeEnhanceLevel;
	}

	public void setSafeEnhanceLevel(EnhanceLevel safeEnhanceLevel) {
		this.safeEnhanceLevel = safeEnhanceLevel;
	}

	/**
	 * 最高幻想強化等級,使用幻想強化道具,最高強化等級, >+15 無法使用
	 * 
	 * @return
	 */
	public EnhanceLevel getFantasyEnhanceLevel() {
		return fantasyEnhanceLevel;
	}

	public void setFantasyEnhanceLevel(EnhanceLevel fantasyEnhanceLevel) {
		this.fantasyEnhanceLevel = fantasyEnhanceLevel;
	}

	// --------------------------------------------------
	/**
	 * 取得clone防具
	 * 
	 * @param id
	 * @return
	 */
	public Armor getArmor(String id) {
		Armor result = null;
		if (id != null) {
			result = armors.get(id);
		}
		return (result != null ? (Armor) result.clone() : null);
	}

	public Armor createArmor() {
		return createArmor(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的armor,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 * @return
	 */
	public Armor createArmor(String id) {
		Armor result = null;
		// 來自靜態資料的clone副本
		result = getArmor(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new ArmorImpl();
			result.setId(id);
		}
		result.setUniqueId(Armor.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		return result;
	}

	/**
	 * 防具是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean containArmor(String id) {
		boolean result = false;
		if (id != null) {
			result = armors.containsKey(id);
		}
		return result;
	}

	/**
	 * 防具是否存在
	 * 
	 * @param armor
	 * @return
	 */
	public boolean containArmor(Armor armor) {
		boolean result = false;
		if (armor != null) {
			result = armors.containsKey(armor.getId());
		}
		return result;
	}

	/**
	 * 取得所有防具id
	 * 
	 * @return
	 */
	public List<String> getArmorIds() {
		List<String> result = new LinkedList<String>();
		for (String id : armors.keySet()) {
			result.add(id);
		}
		return result;
	}

	/**
	 * 取得強化因子,不clone了,直接拿
	 * 
	 * @param enhanceLevel
	 * @return
	 */
	public EnhanceFactor getEnhanceFactor(EnhanceLevel enhanceLevel) {
		EnhanceFactor result = null;
		if (enhanceLevel != null) {
			result = enhanceFactors.get(enhanceLevel);
		}
		// return (result != null ? (EnhanceFactor) result.clone() : null);
		return result;
	}

	/**
	 * 取得強化因子,by enhanceValue
	 * 
	 * @param enhanceValue
	 * @return
	 */
	public EnhanceFactor getEnhanceFactor(int enhanceValue) {
		EnhanceFactor result = null;
		EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class, enhanceValue);
		if (enhanceLevel != null) {
			result = getEnhanceFactor(enhanceLevel);
		}
		return result;
	}

	/**
	 * 取得累計強化因子,不clone了,直接拿
	 * 
	 * @param enhanceLevel
	 * @return
	 */
	public EnhanceFactor getAccuEnhanceFactor(EnhanceLevel enhanceLevel) {
		EnhanceFactor result = null;
		if (enhanceLevel != null) {
			result = accuEnhanceFactors.get(enhanceLevel);
		}
		// return (result != null ? (EnhanceFactor) result.clone() : null);
		return result;
	}

	/**
	 * 取得累計強化因子,by enhanceValue
	 * 
	 * @param enhanceValue
	 * @return
	 */
	public EnhanceFactor getAccuEnhanceFactor(int enhanceValue) {
		EnhanceFactor result = null;
		EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class, enhanceValue);
		if (enhanceLevel != null) {
			result = getAccuEnhanceFactor(enhanceLevel);
		}
		return result;
	}
}
