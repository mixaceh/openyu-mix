package org.openyu.mix.wuxing.vo;

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
import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.adapter.StringPrizeXmlAdapter;
import org.openyu.mix.wuxing.vo.WuxingType;
import org.openyu.mix.wuxing.vo.adapter.WuxingTypeXmlAdapter;

/**
 * 1.處理靜態資料,放的是vo的資料,並非po資料
 * 
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "wuxingCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class WuxingCollector extends BaseCollectorSupporter
{

	private static final long serialVersionUID = -366805549782373969L;

	private static WuxingCollector wuxingCollector;

	/**
	 * 五行類別
	 */
	@XmlJavaTypeAdapter(WuxingTypeXmlAdapter.class)
	private Set<WuxingType> wuxingTypes = new LinkedHashSet<WuxingType>();

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
	private long playGold = 350 * 10000L;//350w

	/**
	 * 每日可玩的次數
	 */
	private int dailyTimes = 10;

	/**
	 * 花費的道具,數量為1
	 */
	private String playItem = "T_WUXING_PLAY_G001";//五行石

	/**
	 * 花費的儲值幣
	 */
	private int playCoin = 70;

	/**
	 * 勝負獎
	 * 
	 * <prizeId,prize>
	 */
	@XmlJavaTypeAdapter(StringPrizeXmlAdapter.class)
	private Map<String, Prize> finalPrizes = new LinkedHashMap<String, Prize>();

	/**
	 * 生獎
	 * 
	 * <prizeId,prize>
	 */
	@XmlJavaTypeAdapter(StringPrizeXmlAdapter.class)
	private Map<String, Prize> birthPrizes = new LinkedHashMap<String, Prize>();

	/**
	 * 和獎
	 * 
	 * <prizeId,prize>
	 */
	@XmlJavaTypeAdapter(StringPrizeXmlAdapter.class)
	private Map<String, Prize> tiePrizes = new LinkedHashMap<String, Prize>();

	public WuxingCollector()
	{
		setId(WuxingCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static WuxingCollector getInstance()
	{
		return getInstance(true);
	}

	public synchronized static WuxingCollector getInstance(boolean initial)
	{
		if (wuxingCollector == null)
		{
			wuxingCollector = new WuxingCollector();
			if (initial)
			{
				wuxingCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			wuxingCollector.wuxingTypes = EnumHelper.valuesSet(WuxingType.class);

		}
		return wuxingCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize()
	{
		if (!wuxingCollector.isInitialized())
		{
			wuxingCollector = readFromSer(WuxingCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (wuxingCollector == null)
			{
				wuxingCollector = new WuxingCollector();
			}
			//
			wuxingCollector.setInitialized(true);
		}
	}

	public void clear()
	{
		finalPrizes.clear();
		birthPrizes.clear();
		tiePrizes.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	public Set<WuxingType> getWuxingTypes()
	{
		if (wuxingTypes == null)
		{
			wuxingTypes = new LinkedHashSet<WuxingType>();
		}
		return wuxingTypes;
	}

	public void setWuxingTypes(Set<WuxingType> wuxingTypes)
	{
		this.wuxingTypes = wuxingTypes;
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

	public Map<String, Prize> getFinalPrizes()
	{
		if (finalPrizes == null)
		{
			finalPrizes = new LinkedHashMap<String, Prize>();
		}
		return finalPrizes;
	}

	public void setFinalPrizes(Map<String, Prize> finalPrizes)
	{
		this.finalPrizes = finalPrizes;
	}

	/**
	 * 取得勝負獎勵,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Prize getFinalPrize(String id)
	{
		Prize result = null;
		if (id != null)
		{
			result = finalPrizes.get(id);
		}
		//return (result != null ? (Prize) result.clone() : null);
		return result;
	}

	public Map<String, Prize> getBirthPrizes()
	{
		if (birthPrizes == null)
		{
			birthPrizes = new LinkedHashMap<String, Prize>();
		}
		return birthPrizes;
	}

	public void setBirthPrizes(Map<String, Prize> birthPrizes)
	{
		this.birthPrizes = birthPrizes;
	}

	/**
	 * 取得生的獎勵,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Prize getBirthPrize(String id)
	{
		Prize result = null;
		if (id != null)
		{
			result = birthPrizes.get(id);
		}
		//return (result != null ? (Prize) result.clone() : null);
		return result;
	}

	public Map<String, Prize> getTiePrizes()
	{
		if (tiePrizes == null)
		{
			tiePrizes = new LinkedHashMap<String, Prize>();
		}
		return tiePrizes;
	}

	public void setTiePrizes(Map<String, Prize> tiePrizes)
	{
		this.tiePrizes = tiePrizes;
	}

	/**
	 * 取得和的獎勵,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Prize getTiePrize(String id)
	{
		Prize result = null;
		if (id != null)
		{
			result = tiePrizes.get(id);
		}
		//return (result != null ? (Prize) result.clone() : null);
		return result;
	}
}
