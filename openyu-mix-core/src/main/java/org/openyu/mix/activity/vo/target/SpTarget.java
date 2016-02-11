package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 技能經驗目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface SpTarget extends Target
{

	/**
	 * 技能經驗
	 * 
	 * @return
	 */
	long getSp();

	void setSp(long sp);

}
