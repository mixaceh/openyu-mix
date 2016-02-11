package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 經驗目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ExpTarget extends Target
{

	/**
	 * 經驗
	 * 
	 * @return
	 */
	long getExp();

	void setExp(long exp);

}
