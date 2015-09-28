package org.openyu.mix.vip.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.openyu.mix.manor.vo.ManorPen.FarmType;
import org.openyu.mix.role.vo.BagPen.TabType;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.util.ConfigHelper;

/**
 * VIP數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "vipCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class VipCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 3351247036913668230L;

	private static VipCollector vipCollector;

	public final static String VIP_TYPE = "vipCollector.vipType";

	private static Map<String, String> appVipTypes = ConfigHelper
			.getMap(VIP_TYPE);

	/**
	 * vip等級,來自於appConfig-op.xml
	 * 
	 * 非直接來自於xml,故不ser
	 * 
	 * <vipType,accuCoin>
	 */
	// 1=10, 2=100, 3=500, 4=1000, 5=2000, 6=5000, 7=10000, 8=20000, 9=50000,
	// 10=100000
	// 塞個0級的,0=0
	private transient Map<VipType, Integer> vipTypes = new LinkedHashMap<VipType, Integer>();

	/**
	 * 最高vip等級
	 */
	private transient VipType maxVipType;

	// --------------------------------------------------
	/**
	 * 包包頁應對的vip等級
	 */
	private Map<TabType, VipType> bagTabTypes = new LinkedHashMap<TabType, VipType>();

	/**
	 * 莊園農場頁應對的vip等級
	 */
	private Map<FarmType, VipType> manorFarmTypes = new LinkedHashMap<FarmType, VipType>();

	/**
	 * 在四象中,可以使用儲值幣vip等級
	 */
	// @XmlJavaTypeAdapter(VipTypeXmlAdapter.class)
	private VipType sasangCoinVipType = VipType._2;

	/**
	 * 在莊園中,可以使用儲值幣vip等級
	 */
	// @XmlJavaTypeAdapter(VipTypeXmlAdapter.class)
	private VipType manorCoinVipType = VipType._2;

	/**
	 * 在祕寶中,可以使用儲值幣vip等級
	 */
	// @XmlJavaTypeAdapter(VipTypeXmlAdapter.class)
	private VipType treasureCoinVipType = VipType._2;

	/**
	 * 在訓練中,可以使用儲值幣vip等級
	 */
	// @XmlJavaTypeAdapter(VipTypeXmlAdapter.class)
	private VipType trainCoinVipType = VipType._2;

	public VipCollector() {
		setId(VipCollector.class.getName());
	}

	/**
	 * 在五行中,可以使用儲值幣vip等級
	 */
	// @XmlJavaTypeAdapter(VipTypeXmlAdapter.class)
	private VipType wuxingCoinVipType = VipType._2;

	// --------------------------------------------------
	public synchronized static VipCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static VipCollector getInstance(boolean initial) {
		if (vipCollector == null) {
			vipCollector = new VipCollector();
			if (initial) {
				vipCollector.initialize();
			}
		}
		return vipCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!vipCollector.isInitialized()) {
			vipCollector = readFromSer(VipCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (vipCollector == null) {
				vipCollector = new VipCollector();
			}

			// vip等級
			vipCollector.vipTypes = buildVipTypes();
			vipCollector.maxVipType = buildMaxVipType();
			vipCollector.setInitialized(true);
		}
	}

	public void clear() {
		vipTypes.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------

	/**
	 * 內部建構用vip等級,主要來自於appConfig-op.xml
	 * 
	 * @return
	 */
	protected Map<VipType, Integer> buildVipTypes() {
		Map<VipType, Integer> result = new LinkedHashMap<VipType, Integer>();
		// 塞個0級的,0=0
		result.put(VipType._0, 0);
		//
		for (Map.Entry<String, String> entry : appVipTypes.entrySet()) {
			int intValue = NumberHelper.toInt(entry.getKey());
			VipType vipType = EnumHelper.valueOf(VipType.class, intValue);
			//
			int accuCoin = NumberHelper.toInt(entry.getValue());
			result.put(vipType, accuCoin);
		}
		return result;
	}

	/**
	 * 內部建構用,最高vip等級
	 * 
	 * @return
	 */
	protected VipType buildMaxVipType() {
		VipType result = null;
		for (VipType vipType : vipCollector.vipTypes.keySet()) {
			if (result == null || vipType.getValue() > result.getValue()) {
				result = vipType;
			}
		}
		return result;
	}

	public Map<VipType, Integer> getVipTypes() {
		if (vipTypes == null) {
			vipTypes = new LinkedHashMap<VipType, Integer>();
		}
		return vipTypes;
	}

	public void setVipTypes(Map<VipType, Integer> vipTypes) {
		this.vipTypes = vipTypes;
	}

	/**
	 * 取得最高vip等級
	 * 
	 * @return
	 */
	public VipType getMaxVipType() {
		return maxVipType;
	}

	/**
	 * 包包頁應對的vip類型
	 * 
	 * @return
	 */
	public Map<TabType, VipType> getBagTabTypes() {
		if (bagTabTypes == null) {
			bagTabTypes = new LinkedHashMap<TabType, VipType>();
		}
		return bagTabTypes;
	}

	public void setBagTabTypes(Map<TabType, VipType> bagTabTypes) {
		this.bagTabTypes = bagTabTypes;
	}

	/**
	 * 由包包頁類型,取得vip類型
	 * 
	 * @param tabType
	 * @return
	 */
	public VipType getVipType(TabType tabType) {
		return getBagTabTypes().get(tabType);
	}

	/**
	 * 莊園農場頁應對的vip類型
	 * 
	 * @return
	 */
	public Map<FarmType, VipType> getManorFarmTypes() {
		if (manorFarmTypes == null) {
			manorFarmTypes = new LinkedHashMap<FarmType, VipType>();
		}
		return manorFarmTypes;
	}

	public void setManorFarmTypes(Map<FarmType, VipType> manorFarmTypes) {
		this.manorFarmTypes = manorFarmTypes;
	}

	/**
	 * 由農場頁類型,取得vip類型
	 * 
	 * @param tabType
	 * @return
	 */
	public VipType getVipType(FarmType farmType) {
		return getManorFarmTypes().get(farmType);
	}

	// --------------------------------------------------

	/**
	 * 由vip類型,取得累計儲值
	 * 
	 * @param vipType
	 * @return
	 */
	public int getAccuCoin(VipType vipType) {
		int result = 0;
		if (vipType != null) {
			result = safeGet(vipTypes.get(vipType));
		}
		return result;
	}

	public boolean containVipType(VipType vipType) {
		boolean result = false;
		if (vipType != null) {
			result = vipTypes.containsKey(vipType);
		}
		return result;
	}

	/**
	 * 由累計儲值,取得vip類型
	 * 
	 * @param accuCoin
	 * @return
	 */
	public VipType getVipType(int accuCoin) {
		VipType result = null;
		// 由大到小
		for (int i = vipTypes.size(); i >= 0; i--) {
			VipType vipType = EnumHelper.valueOf(VipType.class, i);
			if (vipType != null) {
				int existAccuCoin = safeGet(vipTypes.get(vipType));// Integer=null
				if (accuCoin >= existAccuCoin) {
					result = vipType;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 在四象中,可以使用儲值幣vip類型
	 * 
	 * @return
	 */
	public VipType getSasangCoinVipType() {
		return sasangCoinVipType;
	}

	public void setSasangCoinVipType(VipType sasangCoinVipType) {
		this.sasangCoinVipType = sasangCoinVipType;
	}

	/**
	 * 在莊園中,可以使用儲值幣vip類型
	 * 
	 * @return
	 */
	public VipType getManorCoinVipType() {
		return manorCoinVipType;
	}

	public void setManorCoinVipType(VipType manorCoinVipType) {
		this.manorCoinVipType = manorCoinVipType;
	}

	/**
	 * 在祕寶中,可以使用儲值幣vip等級
	 * 
	 * @return
	 */
	public VipType getTreasureCoinVipType() {
		return treasureCoinVipType;
	}

	public void setTreasureCoinVipType(VipType treasureCoinVipType) {
		this.treasureCoinVipType = treasureCoinVipType;
	}

	/**
	 * 在訓練中,可以使用儲值幣vip等級
	 * 
	 * @return
	 */
	public VipType getTrainCoinVipType() {
		return trainCoinVipType;
	}

	public void setTrainCoinVipType(VipType trainCoinVipType) {
		this.trainCoinVipType = trainCoinVipType;
	}

	/**
	 * 在五行中,可以使用儲值幣vip等級
	 * 
	 * @return
	 */
	public VipType getWuxingCoinVipType() {
		return wuxingCoinVipType;
	}

	public void setWuxingCoinVipType(VipType wuxingCoinVipType) {
		this.wuxingCoinVipType = wuxingCoinVipType;
	}

}
