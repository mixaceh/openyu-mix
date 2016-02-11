package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 儲值幣目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface CoinTarget extends Target
{

	/**
	 * 儲值幣
	 * 
	 * @return
	 */
	int getCoin();

	void setCoin(int coin);

}
