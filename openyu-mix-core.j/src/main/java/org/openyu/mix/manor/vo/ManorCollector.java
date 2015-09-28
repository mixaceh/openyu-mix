package org.openyu.mix.manor.vo;

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

import org.openyu.mix.item.vo.EnhanceFactor;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.adapter.EnhanceLevelEnhanceFactorXmlAdapter;
import org.openyu.mix.item.vo.adapter.EnhanceLevelXmlAdapter;
import org.openyu.mix.item.vo.impl.EnhanceFactorImpl;
import org.openyu.mix.manor.vo.adapter.StringLandXmlAdapter;
import org.openyu.mix.manor.vo.adapter.StringSeedXmlAdapter;
import org.openyu.mix.manor.vo.impl.LandImpl;
import org.openyu.mix.manor.vo.impl.SeedImpl;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 莊園數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "manorCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ManorCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 4981643884128613616L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorCollector.class);

	private static ManorCollector manorCollector;

	/**
	 * 強化等級
	 */
	@XmlJavaTypeAdapter(EnhanceLevelXmlAdapter.class)
	private Set<EnhanceLevel> enhanceLevels = new LinkedHashSet<EnhanceLevel>();

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 最高強化等級
	 */
	private EnhanceLevel maxEnhanceLevel = EnhanceLevel._20;

	/**
	 * 安全強化等級, +1-3,不會爆掉, > +3才有機率爆掉
	 */
	private EnhanceLevel safeEnhanceLevel = EnhanceLevel._3;

	/**
	 * 幻想強化等級,使用幻想強化道具,最高強化等級, >+15 無法使用
	 */
	private EnhanceLevel fantasyEnhanceLevel = EnhanceLevel._15;

	// --------------------------------------------------
	// 開墾
	// --------------------------------------------------
	/**
	 * 開墾的金幣
	 */
	private long reclaimGold = 250 * 10000L;// 250w

	// --------------------------------------------------
	// 休耕
	// --------------------------------------------------
	/**
	 * 休耕的金幣
	 */
	private long disuseGold = 50 * 10000L;// 50w

	// --------------------------------------------------
	// 澆水
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 澆水的道具,數量為1
	 */
	private String waterItem = "T_MANOR_WATER_G001";// 莊園澆水石

	/**
	 * 澆水的儲值幣
	 */
	private int waterCoin = 2;

	/**
	 * 澆水效果,增加成長速度,萬分位(2000)
	 */
	private int waterRate = 1000;

	// --------------------------------------------------
	// 祈禱
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 祈禱的道具,數量為1
	 */
	private String prayItem = "T_MANOR_PRAY_G001";// 莊園祈禱石

	/**
	 * 祈禱的儲值幣
	 */
	private int prayCoin = 5;

	/**
	 * 祈禱效果,減少成長毫秒,萬分位(2000)
	 */
	private int prayRate = 2000;

	// --------------------------------------------------
	// 加速
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 加速的道具,數量為1
	 */
	private String speedItem = "T_MANOR_SPEED_G001";// 莊園加速石

	/**
	 * 加速的儲值幣
	 */
	private int speedCoin = 15;

	// --------------------------------------------------
	// 復活
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 復活的道具,數量為1
	 */
	private String reviveItem = "T_MANOR_REVIVE_G001";// 莊園復活石

	/**
	 * 復活的儲值幣
	 */
	private int reviveCoin = 15;

	// --------------------------------------------------

	/**
	 * 種子
	 * 
	 * <種子id,種子>
	 */
	@XmlJavaTypeAdapter(StringSeedXmlAdapter.class)
	private Map<String, Seed> seeds = new LinkedHashMap<String, Seed>();

	// /**
	// * 種子因子,用來加乘屬性值
	// *
	// * <種子id,萬分位數=1000,2000...n>
	// */
	// @XmlJavaTypeAdapter(StringSeedFactorXmlAdapter.class)
	// private Map<String, SeedFactor> seedFactors = new LinkedHashMap<String,
	// SeedFactor>();

	/**
	 * 土地
	 * 
	 * <土地id,土地>
	 */
	@XmlJavaTypeAdapter(StringLandXmlAdapter.class)
	private Map<String, Land> lands = new LinkedHashMap<String, Land>();

	// /**
	// * 土地因子,用來加乘屬性值
	// *
	// * <土地id,萬分位數=1000,2000...n>
	// */
	// @XmlJavaTypeAdapter(StringLandFactorXmlAdapter.class)
	// private Map<String, LandFactor> landFactors = new LinkedHashMap<String,
	// LandFactor>();

	/**
	 * 強化因子,用來增加土地屬性
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

	// --------------------------------------------------
	public ManorCollector() {
		setId(ManorCollector.class.getName());
	}

	public synchronized static ManorCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static ManorCollector getInstance(boolean initial) {
		if (manorCollector == null) {
			manorCollector = new ManorCollector();
			if (initial) {
				manorCollector.initialize();
			}
			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			manorCollector.enhanceLevels = EnumHelper
					.valuesSet(EnhanceLevel.class);
			//
		}
		return manorCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!manorCollector.isInitialized()) {
			manorCollector = readFromSer(ManorCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (manorCollector == null) {
				manorCollector = new ManorCollector();
			}

			// 累計土地強化因子
			manorCollector.accuEnhanceFactors = buildAccuEnhanceFactors();
			manorCollector.setInitialized(true);
		}
	}

	public void clear() {
		seeds.clear();
		// seedFactors.clear();
		lands.clear();
		// landFactors.clear();
		enhanceFactors.clear();
		accuEnhanceFactors.clear();
		// 設為可初始化
		setInitialized(false);
	}

	protected Map<EnhanceLevel, EnhanceFactor> buildAccuEnhanceFactors() {
		Map<EnhanceLevel, EnhanceFactor> result = new LinkedHashMap<EnhanceLevel, EnhanceFactor>();

		int accuPoint = 0;// 累計值
		int accuRate = 0;// 累計比率
		double accuProbability = 1d;// 累計機率
		for (Map.Entry<EnhanceLevel, EnhanceFactor> entry : manorCollector.enhanceFactors
				.entrySet()) {
			EnhanceLevel enhanceLevel = entry.getKey();
			EnhanceFactor enhanceFactor = entry.getValue();
			// 累計
			accuPoint += enhanceFactor.getPoint();
			accuRate += enhanceFactor.getRate();
			accuProbability *= ratio(enhanceFactor.getProbability());
			//
			EnhanceFactor accufactor = new EnhanceFactorImpl(enhanceLevel);
			accufactor.setPoint(accuPoint);
			accufactor.setRate(accuRate);
			accufactor.setProbability(((int) (accuProbability * 10000d)));
			//
			if (accufactor.getProbability() == 0) {
				LOGGER.info("Manor [" + enhanceLevel
						+ "] probability is zero");
			}
			//
			result.put(enhanceLevel, accufactor);
		}
		return result;
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
	 * 說明
	 * 
	 * @return
	 */
	public String getDescription() {
		return descriptions.getName();
	}

	public void setDescription(String description) {
		this.descriptions.setName(description);
	}

	public NamesBean getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(NamesBean descriptions) {
		this.descriptions = descriptions;
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

	public long getReclaimGold() {
		return reclaimGold;
	}

	public void setReclaimGold(long reclaimGold) {
		this.reclaimGold = reclaimGold;
	}

	public long getDisuseGold() {
		return disuseGold;
	}

	public void setDisuseGold(long disuseGold) {
		this.disuseGold = disuseGold;
	}

	public String getWaterItem() {
		return waterItem;
	}

	public void setWaterItem(String waterItem) {
		this.waterItem = waterItem;
	}

	public int getWaterCoin() {
		return waterCoin;
	}

	public void setWaterCoin(int waterCoin) {
		this.waterCoin = waterCoin;
	}

	public int getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(int waterRate) {
		this.waterRate = waterRate;
	}

	public String getPrayItem() {
		return prayItem;
	}

	public void setPrayItem(String prayItem) {
		this.prayItem = prayItem;
	}

	public int getPrayCoin() {
		return prayCoin;
	}

	public void setPrayCoin(int prayCoin) {
		this.prayCoin = prayCoin;
	}

	public int getPrayRate() {
		return prayRate;
	}

	public void setPrayRate(int prayRate) {
		this.prayRate = prayRate;
	}

	public String getSpeedItem() {
		return speedItem;
	}

	public void setSpeedItem(String speedItem) {
		this.speedItem = speedItem;
	}

	public int getSpeedCoin() {
		return speedCoin;
	}

	public void setSpeedCoin(int speedCoin) {
		this.speedCoin = speedCoin;
	}

	public String getReviveItem() {
		return reviveItem;
	}

	public void setReviveItem(String reviveItem) {
		this.reviveItem = reviveItem;
	}

	public int getReviveCoin() {
		return reviveCoin;
	}

	public void setReviveCoin(int reviveCoin) {
		this.reviveCoin = reviveCoin;
	}

	/**
	 * 取得所有種子
	 * 
	 * @return
	 */
	public Map<String, Seed> getSeeds() {
		if (seeds == null) {
			seeds = new LinkedHashMap<String, Seed>();
		}
		return seeds;
	}

	public void setSeeds(Map<String, Seed> seeds) {
		this.seeds = seeds;
	}

	// /**
	// * 取得種子因子
	// *
	// * @return
	// */
	// public Map<String, SeedFactor> getSeedFactors()
	// {
	// return seedFactors;
	// }
	//
	// public void setSeedFactors(Map<String, SeedFactor> seedFactors)
	// {
	// if (seedFactors == null)
	// {
	// seedFactors = new LinkedHashMap<String, SeedFactor>();
	// }
	// this.seedFactors = seedFactors;
	// }

	/**
	 * 取得所有土地
	 * 
	 * @return
	 */
	public Map<String, Land> getLands() {
		if (lands == null) {
			lands = new LinkedHashMap<String, Land>();
		}
		return lands;
	}

	public void setLands(Map<String, Land> lands) {
		this.lands = lands;
	}

	// /**
	// * 取得土地因子
	// *
	// * @return
	// */
	// public Map<String, LandFactor> getLandFactors()
	// {
	// return landFactors;
	// }
	//
	// public void setLandFactors(Map<String, LandFactor> landFactors)
	// {
	// if (landFactors == null)
	// {
	// landFactors = new LinkedHashMap<String, LandFactor>();
	// }
	// this.landFactors = landFactors;
	// }

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

	// --------------------------------------------------
	/**
	 * 取得clone種子
	 * 
	 * @param id
	 *            種子id
	 * @return
	 */
	public Seed getSeed(String id) {
		Seed result = null;
		if (id != null) {
			result = seeds.get(id);
		}
		return (result != null ? (Seed) result.clone() : null);
	}

	public Seed createSeed() {
		return createSeed(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的seed,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 *            種子id
	 * @return
	 */
	public Seed createSeed(String id) {
		Seed result = null;
		// 來自靜態資料的clone副本
		result = getSeed(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new SeedImpl(id);
		}
		result.setUniqueId(Seed.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		return result;
	}

	/**
	 * 種子是否存在
	 * 
	 * @param id
	 *            種子id
	 * @return
	 */
	public boolean containSeed(String id) {
		boolean result = false;
		if (id != null) {
			result = seeds.containsKey(id);
		}
		return result;
	}

	/**
	 * 種子是否存在
	 * 
	 * @param seed
	 * @return
	 */
	public boolean containSeed(Seed seed) {
		boolean result = false;
		if (seed != null) {
			result = seeds.containsKey(seed.getId());
		}
		return result;
	}

	/**
	 * 取得所有種子id
	 * 
	 * @return
	 */
	public List<String> getSeedIds() {
		List<String> result = new LinkedList<String>();
		for (String id : seeds.keySet()) {
			result.add(id);
		}
		return result;
	}

	// /**
	// * 取得種子因子,不clone了,直接拿
	// *
	// * @param id
	// * @return
	// */
	// public SeedFactor getSeedFactor(String id)
	// {
	// SeedFactor result = null;
	// if (id != null)
	// {
	// result = seedFactors.get(id);
	// }
	// //return (result != null ? (SeedFactor) result.clone() : null);
	// return result;
	// }

	// --------------------------------------------------
	/**
	 * 取得clone土地
	 * 
	 * @param id
	 *            土地id
	 * @return
	 */
	public Land getLand(String id) {
		Land result = null;
		if (id != null) {
			result = lands.get(id);
		}
		return (result != null ? (Land) result.clone() : null);
	}

	public Land createLand() {
		return createLand(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的land,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 *            土地id
	 * @return
	 */
	public Land createLand(String id) {
		Land result = null;
		// 來自靜態資料的clone副本
		result = getLand(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new LandImpl(id);
		}
		result.setUniqueId(Land.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		return result;
	}

	/**
	 * 土地是否存在
	 * 
	 * @param id
	 *            土地id
	 * @return
	 */
	public boolean containLand(String id) {
		boolean result = false;
		if (id != null) {
			result = lands.containsKey(id);
		}
		return result;
	}

	/**
	 * 土地是否存在
	 * 
	 * @param land
	 * @return
	 */
	public boolean containLand(Land land) {
		boolean result = false;
		if (land != null) {
			result = lands.containsKey(land.getId());
		}
		return result;
	}

	/**
	 * 取得所有土地id
	 * 
	 * @return
	 */
	public List<String> getLandIds() {
		List<String> result = new LinkedList<String>();
		for (String id : lands.keySet()) {
			result.add(id);
		}
		return result;
	}

	// /**
	// * 取得土地因子,不clone了,直接拿
	// *
	// * @param id 土地id
	// * @return
	// */
	// public LandFactor getLandFactor(String id)
	// {
	// LandFactor result = null;
	// if (id != null)
	// {
	// result = landFactors.get(id);
	// }
	// //return (result != null ? (LandFactor) result.clone() : null);
	// return result;
	// }

	/**
	 * 取得土地強化因子,不clone了,直接拿
	 * 
	 * @param id
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
	 * 取得土地強化因子,by enhanceValue
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
