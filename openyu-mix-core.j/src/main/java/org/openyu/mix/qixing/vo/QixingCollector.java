package org.openyu.mix.qixing.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.adapter.StringPrizeXmlAdapter;
import org.openyu.mix.qixing.vo.QixingType;
import org.openyu.mix.qixing.vo.adapter.QixingTypeXmlAdapter;

/**
 * 1.處理靜態資料,放的是vo的資料,並非po資料
 * 
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "qixingCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class QixingCollector extends BaseCollectorSupporter
{

	private static final long serialVersionUID = -366805549782373969L;

	private static QixingCollector qixingCollector;

	/**
	 * 七星類別
	 */
	@XmlJavaTypeAdapter(QixingTypeXmlAdapter.class)
	private Set<QixingType> qixingTypes = new LinkedHashSet<QixingType>();

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 等級限制
	 */
	private int levelLimit = 25;

	/**
	 * 花費的金幣
	 */
	private long playGold = 200 * 10000L;//200w

	/**
	 * 每日可玩的次數
	 */
	private int dailyTimes = 10;

	/**
	 * 花費的道具,數量為1
	 */
	private String playItem = "T_QIXING_PLAY_G001";//七星石

	/**
	 * 花費的儲值幣
	 */
	private int playCoin = 40;

	/**
	 * 開出的獎
	 * 
	 * <prizeId,prize>
	 */
	@XmlJavaTypeAdapter(StringPrizeXmlAdapter.class)
	private Map<String, Prize> prizes = new LinkedHashMap<String, Prize>();

	public QixingCollector()
	{
		setId(QixingCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static QixingCollector getInstance()
	{
		return getInstance(true);
	}

	public synchronized static QixingCollector getInstance(boolean initial)
	{
		if (qixingCollector == null)
		{
			qixingCollector = new QixingCollector();
			if (initial)
			{
				qixingCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			qixingCollector.qixingTypes = EnumHelper.valuesSet(QixingType.class);

		}
		return qixingCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize()
	{
		if (!qixingCollector.isInitialized())
		{
			qixingCollector = readFromSer(QixingCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (qixingCollector == null)
			{
				qixingCollector = new QixingCollector();
			}
			//
			qixingCollector.setInitialized(true);
		}
	}

	public void clear()
	{
		prizes.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	public Set<QixingType> getQixingTypes()
	{
		if (qixingTypes == null)
		{
			qixingTypes = new LinkedHashSet<QixingType>();
		}
		return qixingTypes;
	}

	public void setQixingTypes(Set<QixingType> qixingTypes)
	{
		this.qixingTypes = qixingTypes;
	}

	/**
	 * 說明
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return descriptions.getName();
	}

	public void setDescription(String description)
	{
		this.descriptions.setName(description);
	}

	public NamesBean getDescriptions()
	{
		return descriptions;
	}

	public void setDescriptions(NamesBean descriptions)
	{
		this.descriptions = descriptions;
	}

	public int getLevelLimit()
	{
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit)
	{
		this.levelLimit = levelLimit;
	}

	public long getPlayGold()
	{
		return playGold;
	}

	public void setPlayGold(long playGold)
	{
		this.playGold = playGold;
	}

	public int getDailyTimes()
	{
		return dailyTimes;
	}

	public void setDailyTimes(int dailyTimes)
	{
		this.dailyTimes = dailyTimes;
	}

	public String getPlayItem()
	{
		return playItem;
	}

	public void setPlayItem(String playItem)
	{
		this.playItem = playItem;
	}

	public int getPlayCoin()
	{
		return playCoin;
	}

	public void setPlayCoin(int playCoin)
	{
		this.playCoin = playCoin;
	}

	public Map<String, Prize> getPrizes()
	{
		if (prizes == null)
		{
			prizes = new LinkedHashMap<String, Prize>();
		}
		return prizes;
	}

	public void setPrizes(Map<String, Prize> prizes)
	{
		this.prizes = prizes;
	}

	/**
	 * 取得獎勵,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Prize getPrize(String id)
	{
		Prize result = null;
		if (id != null)
		{
			result = prizes.get(id);
		}
		//return (result != null ? (Prize) result.clone() : null);
		return result;
	}

}
