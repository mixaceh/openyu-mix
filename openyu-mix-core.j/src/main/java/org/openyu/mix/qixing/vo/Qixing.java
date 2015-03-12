package org.openyu.mix.qixing.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 七星元素
 * 
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Qixing extends BaseBean {

	String KEY = Qixing.class.getName();

	/**
	 * 七星類型,key
	 * 
	 * @return
	 */
	QixingType getId();

	void setId(QixingType id);

}