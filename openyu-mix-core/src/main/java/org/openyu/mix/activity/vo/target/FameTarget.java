package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 聲望目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface FameTarget extends Target
{

	/**
	 * 聲望
	 * 
	 * @return
	 */
	int getFame();

	void setFame(int fame);

}
