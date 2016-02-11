package org.openyu.mix.item.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.AppFactor;
import org.openyu.mix.item.vo.EnhanceLevel;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 強化因子
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface EnhanceFactor extends AppFactor
{
	String KEY = EnhanceFactor.class.getName();

	/**
	 * 強化等級,key
	 * 
	 * @return
	 */
	EnhanceLevel getId();

	void setId(EnhanceLevel id);
}
