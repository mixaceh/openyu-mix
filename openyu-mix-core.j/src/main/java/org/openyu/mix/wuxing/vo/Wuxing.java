package org.openyu.mix.wuxing.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 五行元素
 * 
 * 五行相剋：金剋木，木剋土，土剋水，水剋火，火剋金。
 * 
 * 五行相生：金生水，水生木，木生火，火生土，土生金。
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Wuxing extends BaseBean {

	String KEY = Wuxing.class.getName();

	/**
	 * 五行類型,key
	 * 
	 * @return
	 */
	WuxingType getId();

	void setId(WuxingType id);

}