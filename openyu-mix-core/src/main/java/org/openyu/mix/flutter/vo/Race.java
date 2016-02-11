package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 種族
 * 
 * 1.屬性
 * 
 * point=初值
 * 
 * addPoint=成長值
 * 
 * addRate=成長率
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Race extends BaseBean {
	String KEY = Race.class.getName();

	/**
	 * 種族類別,key值
	 * 
	 * @return
	 */
	RaceType getId();

	void setId(RaceType id);

	/**
	 * 屬性群
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

}
