package org.openyu.mix.sasang.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.mix.app.vo.AppInfo;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 四象欄位資訊
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface SasangInfo extends AppInfo
{
	String KEY = SasangInfo.class.getName();

	/**
	 * 最大中獎區道具種類,如:14個道具
	 */
	int MAX_AWARDS_SIZE = 14;

	/**
	 * 玩的時間
	 * 
	 * @return
	 */
	long getPlayTime();

	void setPlayTime(long playTime);

	/**
	 * 每日已玩的次數
	 * 
	 * @return
	 */
	int getDailyTimes();

	void setDailyTimes(int dailyTimes);

	/**
	 * 增減每日已玩的次數
	 * 
	 * @param times
	 * @return
	 */
	boolean addDailyTimes(int times);

	/**
	 * 累計已玩的次數,不需每日重置
	 * 
	 * @return
	 */
	int getAccuTimes();

	void setAccuTimes(int accuTimes);

	/**
	 * 增減累計每日已玩的次數,不需每日重置
	 * 
	 * @param times
	 * @return
	 */
	boolean addAccuTimes(int times);

	/**
	 * 玩的結果
	 * 
	 * @return
	 */
	Outcome getOutcome();

	void setOutcome(Outcome outcome);

	/**
	 * 中獎區,可堆疊道具
	 * 
	 * <道具id,道具數量>
	 * 
	 * @return
	 */
	Map<String, Integer> getAwards();

	void setAwards(Map<String, Integer> awards);

	/**
	 * 中獎區加入道具,並累計道具數量
	 * 
	 * @param itemId
	 * @param amount
	 * @return
	 */
	boolean addAward(String itemId, int amount);

	/**
	 * 中獎區加入道具,並累計道具數量
	 * 
	 * @param awards
	 * @return
	 */
	boolean addAwards(Map<String, Integer> awards);

	/**
	 * 中獎區移除道具
	 * 
	 * @param itemId
	 * @return
	 */
	int removeAward(String itemId);

	/**
	 * 重置
	 * 
	 * @return
	 */
	boolean reset();

}
