package org.openyu.mix.train.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.AppInfo;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 訓練欄位資訊
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface TrainInfo extends AppInfo
{
	String KEY = TrainInfo.class.getName();

	/**
	 * 加入時間,每次加入訓練都會改變
	 * 
	 * @return
	 */
	long getJoinTime();

	void setJoinTime(long joinTime);

	/**
	 * 結束時間,每次離開訓練都會改變
	 * 
	 * @return
	 */
	long getQuitTime();

	void setQuitTime(long quitTime);

	/**
	 * 每天已訓練毫秒
	 * 
	 * @return
	 */
	long getDailyMills();

	void setDailyMills(long dailyMills);

	/**
	 * 增減每日已訓練毫秒
	 * 
	 * @param mills
	 * @return
	 */
	boolean addDailyMills(long mills);

	/**
	 * 累計每天已訓練毫秒,不需每日重置
	 * 
	 * @return
	 */
	long getAccuMills();

	void setAccuMills(long accuMills);

	/**
	 * 增減累計每天已訓練毫秒,不需每日重置
	 * 
	 * @param mills
	 * @return
	 */
	boolean addAccuMills(long mills);

	/**
	 * 鼓舞時間
	 * 
	 * @return
	 */
	long getInspireTime();

	void setInspireTime(long inspireTime);

	/**
	 * 重置
	 * 
	 * @return
	 */
	boolean reset();

}
