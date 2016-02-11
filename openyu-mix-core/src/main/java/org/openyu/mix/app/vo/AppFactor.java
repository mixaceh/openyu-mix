package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 因子
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AppFactor extends BaseBean
{
	String KEY = AppFactor.class.getName();

	/**
	 * 值
	 * 
	 * 每一等級間的值
	 * 
	 * @return
	 */
	int getPoint();

	void setPoint(int point);

	/**
	 * 比率,萬分位(2000)
	 * 
	 * 每一等級間的值
	 * 
	 * @return
	 */
	int getRate();

	void setRate(int rate);

	/**
	 * 成功機率,萬分位(2000)
	 * 
	 * 每一等級間的值
	 * 
	 * @return
	 */

	int getProbability();

	void setProbability(int probability);

}
