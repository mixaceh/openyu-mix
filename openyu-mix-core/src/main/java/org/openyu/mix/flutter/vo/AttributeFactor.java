package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 屬性因子
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AttributeFactor extends BaseBean
{
	String KEY = AttributeFactor.class.getName();

	/**
	 * 屬性類別,key
	 * 
	 * @return
	 */
	AttributeType getId();

	void setId(AttributeType id);

	/**
	 * 因子,萬分位(2000)
	 * 
	 * @return
	 */
	int getFactor();

	void setFactor(int factor);

	/**
	 * 因子2,萬分位(2000)
	 * 
	 * @return
	 */
	int getFactor2();

	void setFactor2(int factor2);

	/**
	 * 因子3,萬分位(2000)
	 * 
	 * @return
	 */
	int getFactor3();

	void setFactor3(int facto3);
}
