package org.openyu.mix.sasang.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.ArrayHelper;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.adapter.StringPrizeXmlAdapter;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.mix.sasang.vo.adapter.SasangTypeXmlAdapter;
import org.openyu.mix.sasang.vo.impl.SasangImpl;

/**
 * 1.處理靜態資料,放的是vo的資料,並非po資料
 * 
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "sasangCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class SasangCollector extends BaseCollectorSupporter
{

	private static final long serialVersionUID = -366805549782373969L;

	private static SasangCollector sasangCollector;

	//--------------------------------------------------
	//此有系統預設值,只是為了轉出xml,並非給企劃編輯用
	//--------------------------------------------------
	/**
	 * 四象類別
	 */
	@XmlJavaTypeAdapter(SasangTypeXmlAdapter.class)
	private Set<SasangType> sasangTypes = new LinkedHashSet<SasangType>();

	//--------------------------------------------------
	//biz
	//--------------------------------------------------
	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 等級限制
	 */
	private int levelLimit = 20;

	/**
	 * 花費的金幣
	 */
	private long playGold = 250 * 10000L;//250w

	/**
	 * 每日可玩的次數
	 */
	private int dailyTimes = 10;

	/**
	 * 花費的道具,數量為1
	 */
	private String playItem = "T_SASANG_PLAY_G001";//四象石

	/**
	 * 花費的儲值幣
	 */
	private int playCoin = 50;

	/**
	 * 四象元素
	 */
	@XmlElement(type = SasangImpl.class)
	private List<Sasang> sasangs = new LinkedList<Sasang>();

	/**
	 * 開出的獎
	 * 
	 * <prizeId,prize>
	 */
	@XmlJavaTypeAdapter(StringPrizeXmlAdapter.class)
	private Map<String, Prize> prizes = new LinkedHashMap<String, Prize>();

	public SasangCollector()
	{
		setId(SasangCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static SasangCollector getInstance()
	{
		return getInstance(true);
	}

	public synchronized static SasangCollector getInstance(boolean initial)
	{
		if (sasangCollector == null)
		{
			sasangCollector = new SasangCollector();
			if (initial)
			{
				sasangCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			sasangCollector.sasangTypes = EnumHelper.valuesSet(SasangType.class);

		}
		return sasangCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize()
	{
		if (!sasangCollector.isInitialized())
		{
			sasangCollector = readFromSer(SasangCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (sasangCollector == null)
			{
				sasangCollector = new SasangCollector();
			}
			//
			sasangCollector.setInitialized(true);
		}
	}

	public void clear()
	{
		sasangs.clear();
		prizes.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	public Set<SasangType> getSasangTypes()
	{
		if (sasangTypes == null)
		{
			sasangTypes = new LinkedHashSet<SasangType>();
		}
		return sasangTypes;
	}

	public void setSasangTypes(Set<SasangType> sasangTypes)
	{
		this.sasangTypes = sasangTypes;
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

	public List<Sasang> getSasangs()
	{
		if (sasangs == null)
		{
			sasangs = new LinkedList<Sasang>();
		}
		return sasangs;
	}

	public void setSasangs(List<Sasang> sasangs)
	{
		this.sasangs = sasangs;
	}

	/**
	 * 取得四象元素,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Sasang getSasang(SasangType id)
	{
		Sasang result = null;
		if (id != null)
		{
			for (Sasang sasang : sasangs)
			{
				if (sasang.getId().equals(id))
				{
					result = sasang;
					break;
				}
			}
		}
		//return (result != null ? (Sasang) result.clone() : null);
		return result;
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

	/**
	 * 取得獎勵,by 四象類別
	 * 
	 * @param sasangTypes SasangType.BlackDragon... => 111
	 * @return
	 */
	public Prize getPrize(SasangType... sasangTypes)
	{
		StringBuilder buff = new StringBuilder();
		if (ArrayHelper.notEmpty(sasangTypes))
		{
			for (SasangType sasangType : sasangTypes)
			{
				buff.append(sasangType.getValue());
			}
		}
		return getPrize(buff.toString());
	}

}
