package org.openyu.mix.train.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 訓練數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "trainCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class TrainCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -2873453625412211811L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TrainCollector.class);

	private static TrainCollector trainCollector;

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 等級限制
	 */
	private int levelLimit = 10;

	/**
	 * 每日上限毫秒
	 */
	private long dailyMills = 8 * 60 * 60 * 1000L;// 8hr

	/**
	 * 間隔毫秒
	 */
	private long intervalMills = 20 * 1000L;// 20秒

	/**
	 * 活動開啟時,經驗增加比率,萬分位(2000)
	 */
	@XmlTransient
	private int activityRate = 0;

	// --------------------------------------------------
	// 鼓舞,會增加訓練經驗,每日會重置
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 鼓舞的道具,數量為1
	 */
	private String inspireItem = "T_TRAIN_INSPIRE_G001";// 訓練鼓舞石

	/**
	 * 鼓舞的儲值幣
	 */
	private int inspireCoin = 200;

	/**
	 * 鼓舞效果,經驗增加比率,萬分位(2000)
	 */
	private int inspireRate = 3000;

	// --------------------------------------------------
	/**
	 * 每周期獲得的經驗
	 * 
	 * <等級,經驗>
	 */
	private Map<Integer, Long> exps = new LinkedHashMap<Integer, Long>();

	public TrainCollector() {
		setId(TrainCollector.class.getName());
	}

	public synchronized static TrainCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static TrainCollector getInstance(boolean initial) {
		if (trainCollector == null) {
			trainCollector = new TrainCollector();
			if (initial) {
				trainCollector.initialize();
			}
			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
		}
		return trainCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!trainCollector.isInitialized()) {
			trainCollector = readFromSer(TrainCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (trainCollector == null) {
				trainCollector = new TrainCollector();
			}
			//
			trainCollector.setInitialized(true);
		}
	}

	public void clear() {
		exps.clear();
		// 設為可初始化
		setInitialized(false);
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

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public long getDailyMills() {
		return dailyMills;
	}

	public void setDailyMills(long dailyMills) {
		this.dailyMills = dailyMills;
	}

	public long getIntervalMills() {
		return intervalMills;
	}

	public void setIntervalMills(long intervalMills) {
		this.intervalMills = intervalMills;
	}

	public int getActivityRate() {
		return activityRate;
	}

	public void setActivityRate(int activityRate) {
		this.activityRate = activityRate;
	}

	public String getInspireItem() {
		return inspireItem;
	}

	public void setInspireItem(String inspireItem) {
		this.inspireItem = inspireItem;
	}

	public int getInspireCoin() {
		return inspireCoin;
	}

	public void setInspireCoin(int inspireCoin) {
		this.inspireCoin = inspireCoin;
	}

	public int getInspireRate() {
		return inspireRate;
	}

	public void setInspireRate(int inspireRate) {
		this.inspireRate = inspireRate;
	}

	public Map<Integer, Long> getExps() {
		if (exps == null) {
			exps = new LinkedHashMap<Integer, Long>();
		}
		return exps;
	}

	public void setExps(Map<Integer, Long> exps) {
		this.exps = exps;
	}

}
