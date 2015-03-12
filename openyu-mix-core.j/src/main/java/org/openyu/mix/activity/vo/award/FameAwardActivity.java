package org.openyu.mix.activity.vo.award;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.activity.vo.Activity;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 聲望獎勵活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface FameAwardActivity extends Activity
{
	String KEY = FameAwardActivity.class.getName();

	/**
	 * 增加比率,萬分位(2000)
	 * 
	 * @return
	 */
	int getRate();

	void setRate(int rate);
}
