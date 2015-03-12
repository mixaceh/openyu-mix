package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 金幣目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface GoldTarget extends Target
{

	/**
	 * 金幣
	 * 
	 * @return
	 */
	long getGold();

	void setGold(long gold);

}
