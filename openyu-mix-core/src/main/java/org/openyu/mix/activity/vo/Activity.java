package org.openyu.mix.activity.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.IdNamesBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Activity extends IdNamesBean
{
	String KEY = Activity.class.getName();

	
	/**
	 * 說明
	 * 
	 * @return
	 */
	String getDescription();

	void setDescription(String description);

	/**
	 * 是否已開啟
	 * 
	 * @return
	 */
	boolean isOpened();

	void setOpened(boolean opened);

	/**
	 * 活動類別
	 * 
	 * @return
	 */
	ActivityType getActivityType();

	void setActivityType(ActivityType activityType);

}
