package org.openyu.mix.activity.vo.target;

import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.mix.activity.vo.Activity;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface TargetActivity extends Activity
{
	String KEY = TargetActivity.class.getName();

	/**
	 * 開始日期
	 * 
	 * @return
	 */
	String getBegDate();

	void setBegDate(String begDate);

	/**
	 * 結束日期
	 * 
	 * @return
	 */
	String getEndDate();

	void setEndDate(String endDate);

	/**
	 * 是否每日重置
	 * 
	 * @return
	 */
	boolean isDailyReset();

	void setDailyReset(boolean dailyReset);

	/**
	 * 是否依序完成
	 * 
	 * @return
	 */
	boolean isInOrder();

	void setInOrder(boolean inOrder);

	/**
	 * 目標
	 * 
	 * @return
	 */
	Map<Integer, Target> getTargets();

	void setTargets(Map<Integer, Target> targets);

}
