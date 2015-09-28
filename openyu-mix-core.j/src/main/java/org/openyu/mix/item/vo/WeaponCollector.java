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
import org.openyu.mix.item.vo.WeaponType;
import org.openyu.mix.item.vo.adapter.EnhanceLevelEnhanceFactorXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentLevelTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentPositionTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentSeriesTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.StringWeaponXmlAdapter;
import org.openyu.mix.item.vo.adapter.WeaponTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EnhanceLevelXmlAdapter;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.mix.item.vo.impl.WeaponImpl;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 武器數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "weaponCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class WeaponCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 3300149883340061142L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WeaponCollector.class);

	private static WeaponCollector weaponCollector;

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

	/**
	 * 武器類別
	 */
	@XmlJavaTypeAdapter(WeaponTypeXmlAdapter.class)
	private Set<WeaponType> weaponTypes = new LinkedHashSet<WeaponType>();

	/**
	 * 所有武器
	 */
	@XmlJavaTypeAdapter(StringWeaponXmlAdapter.class)
	private Map<String, Weapon> weapons = new LinkedHashMap<String, Weapon>();

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
	private EnhanceLevel maxEnhanceLevel = EnhanceLevel._20;

	/**
	 * 安全強化等級, +1-3,不會爆掉, > +3才有機率爆掉
	 */
	private EnhanceLevel safeEnhanceLevel = EnhanceLevel._3;

	/**
	 * 最高幻想強化等級,使用幻想強化道具,最高強化等級, >+15 無法使用
	 */
	private EnhanceLevel fantasyEnhanceLevel = EnhanceLevel._15;

	// --------------------------------------------------
	public WeaponCollector() {
		setId(WeaponCollector.class.getName());
	}

	public synchronized static WeaponCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static WeaponCollector getInstance(boolean initial) {
		if (weaponCollector == null) {
			weaponCollector = new WeaponCollector();
			if (initial) {
				weaponCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			weaponCollector.levelTypes = EnumHelper.valuesSet(LevelType.class);
			weaponCollector.positionTypes = EnumHelper
					.valuesSet(PositionType.class);
			weaponCollector.seriesTypes = EnumHelper
					.valuesSet(SeriesType.class);
			weaponCollector.enhanceLevels = EnumHelper
					.valuesSet(EnhanceLevel.class);
			weaponCollector.weaponTypes = EnumHelper
					.valuesSet(WeaponType.class);
		}
		return weaponCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!weaponCollector.isInitialized()) {
			weaponCollector = readFromSer(WeaponCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (weaponCollector == null) {
				weaponCollector = new WeaponCollector();
			}

			// 累計土地強化因子
			weaponCollector.accuEnhanceFactors = buildAccuEnhanceFactors();
			weaponCollector.setInitialized(true);
		}
	}

	public void clear() {
		weapons.clear();
		accuEnhanceFactors.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------

	protected Map<EnhanceLevel, EnhanceFactor> buildAccuEnhanceFactors() {
		Map<EnhanceLevel, EnhanceFactor> result = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();

		int accuPoint = 0;// 累計值
		int accuRate = 0;// 累計比率
		double accuProbability = 1d;// 累計機率
		for (Map.Entry<EnhanceLevel, EnhanceFactor> entry : weaponCollector.enhanceFactors
				.entrySet()) {
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
				LOGGER.info("Weapon [" + key + "] probability is zero");
			}
			//
			result.put(key, enhanceFactor);
		}
		return result;
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
	 * 取得武器類別
	 * 
	 * @return
	 */
	public Set<WeaponType> getWeaponTypes() {
		if (weaponTypes == null) {
			weaponTypes = new LinkedHashSet<WeaponType>();
		}
		return weaponTypes;
	}

	public void setWeaponTypes(Set<WeaponType> weaponTypes) {
		this.weaponTypes = weaponTypes;
	}

	/**
	 * 取得所有武器
	 * 
	 * @return
	 */
	public Map<String, Weapon> getWeapons() {
		if (weapons == null) {
			weapons = new LinkedHashMap<String, Weapon>();
		}
		return weapons;
	}

	public void setWeapons(Map<String, Weapon> weapons) {
		this.weapons = weapons;
	}

	/**
	 * 取得強化因子
	 * 
	 * @return
	 */
	public Map<EnhanceLevel, EnhanceFactor> getEnhanceFactors() {
		return enhanceFactors;
	}

	public void setEnhanceFactors(
			Map<EnhanceLevel, EnhanceFactor> enhanceFactors) {
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

	public void setAccuEnhanceFactors(
			Map<EnhanceLevel, EnhanceFactor> accuEnhanceFactors) {
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
	 * 安全強化等級, +1-3,不會爆掉, > +3才有機率爆掉
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
	 * 取得clone武器
	 * 
	 * @param id
	 * @return
	 */
	public Weapon getWeapon(String id) {
		Weapon result = null;
		if (id != null) {
			result = weapons.get(id);
		}
		return (result != null ? (Weapon) result.clone() : null);
	}

	public Weapon createWeapon() {
		return createWeapon(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的weapon,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 * @return
	 */
	public Weapon createWeapon(String id) {
		Weapon result = null;
		// 來自靜態資料的clone副本
		result = getWeapon(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new WeaponImpl(id);
		}
		result.setUniqueId(Weapon.UNIQUE_ID_PREFIX
				+ StringHelper.randomUnique());
		return result;
	}

	/**
	 * 依使用類別取得clone武器集合
	 * 
	 * @param weaponType
	 * @return
	 */
	public List<Weapon> getWeapons(WeaponType weaponType) {
		List<Weapon> result = new LinkedList<Weapon>();
		if (weaponType != null) {
			for (Weapon weapon : weapons.values()) {
				if (weaponType.equals(weapon.getWeaponType())) {
					result.add(getWeapon(weapon.getId()));
				}
			}
		}
		return result;
	}

	/**
	 * 武器是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean containWeapon(String id) {
		boolean result = false;
		if (id != null) {
			result = weapons.containsKey(id);
		}
		return result;
	}

	/**
	 * 武器是否存在
	 * 
	 * @param weapon
	 * @return
	 */
	public boolean containWeapon(Weapon weapon) {
		boolean result = false;
		if (weapon != null) {
			result = weapons.containsKey(weapon.getId());
		}
		return result;
	}

	/**
	 * 取得所有武器id
	 * 
	 * @return
	 */
	public List<String> getWeaponIds() {
		List<String> result = new LinkedList<String>();
		for (String id : weapons.keySet()) {
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
	public EnhanceFactor getEnhanceFactor(EnhanceLevel id) {
		EnhanceFactor result = null;
		if (id != null) {
			result = enhanceFactors.get(id);
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
		EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class,
				enhanceValue);
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
		EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class,
				enhanceValue);
		if (enhanceLevel != null) {
			result = getAccuEnhanceFactor(enhanceLevel);
		}
		return result;
	}
}
