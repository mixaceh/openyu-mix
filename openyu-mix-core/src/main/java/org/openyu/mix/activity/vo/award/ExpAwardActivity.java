package org.openyu.mix.activity.vo.award;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.activity.vo.Activity;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 經驗獎勵活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ExpAwardActivity extends Activity
{
	String KEY = ExpAwardActivity.class.getName();

	/**
	 * 經驗增加比率,萬分位(2000)
	 * 
	 * @return
	 */
	int getRate();

	void setRate(int rate);

	/**
	 * 在線訓練,經驗增加比率,萬分位(2000)
	 * 
	 * @return
	 */
	int getTrainRate();

	void setTrainRate(int trainRate);

}
