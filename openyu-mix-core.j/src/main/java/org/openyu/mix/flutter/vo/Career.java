package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 職業
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
public interface Career extends BaseBean {
	String KEY = Career.class.getName();

	/**
	 * 職業類型,key
	 * 
	 * @return
	 */
	CareerType getId();

	void setId(CareerType id);

	/**
	 * 屬性群
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

}
