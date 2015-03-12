package org.openyu.mix.app.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.IdBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 開出的獎
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Prize extends IdBean
{

	String KEY = Prize.class.getName();

	/**
	 * 獎勵等級
	 * 
	 * @return
	 */
	int getLevel();

	void setLevel(int level);

	/**
	 * 是否成名
	 * 
	 * @return
	 */
	boolean isFamous();

	void setFamous(boolean famous);

	/**
	 * 獎勵
	 * 
	 * <道具id,數量>
	 * 
	 * @return
	 */
	Map<String, Integer> getAwards();

	void setAwards(Map<String, Integer> awards);

}
