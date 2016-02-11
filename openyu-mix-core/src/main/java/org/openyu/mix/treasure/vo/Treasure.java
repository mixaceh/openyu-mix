package org.openyu.mix.treasure.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.IdBean;
import org.openyu.commons.bean.WeightBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 祕寶,有權重
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Treasure extends IdBean, WeightBean
{
	/**
	 * 祕寶庫id
	 * 
	 * @return
	 */
	String getStockId();

	void setStockId(String stockId);

	/**
	 * 賣的數量
	 * 
	 * @return
	 */
	int getAmount();

	void setAmount(int amount);

	/**
	 * 是否已購買(玩家)
	 * 
	 * @return
	 */
	boolean isBought();

	void setBought(boolean bought);

	/**
	 * 是否成名
	 */
	boolean isFamous();

	void setFamous(boolean famous);

}
