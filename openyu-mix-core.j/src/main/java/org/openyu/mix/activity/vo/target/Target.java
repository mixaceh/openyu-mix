package org.openyu.mix.activity.vo.target;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Target extends BaseBean
{
	/**
	 * 目標id
	 * 
	 * @return
	 */
	int getId();

	void setId(int id);

	/**
	 * 等級限制
	 * 
	 * @return
	 */
	int getLevelLimit();

	void setLevelLimit(int levelLimit);

	/**
	 * 目標開始後,到期天數
	 * 
	 * @return
	 */
	int getExpiredDay();

	void setExpiredDay(int expiredDay);

	/**
	 * 提示
	 * 
	 * @return
	 */
	String getTip();

	void setTip(String tip);

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
