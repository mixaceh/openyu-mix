package org.openyu.mix.sasang.vo;

import java.util.List;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 四象元素
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Sasang extends BaseBean
{

	String KEY = Sasang.class.getName();

	/**
	 * 四象類型,key
	 * 
	 * @return
	 */
	SasangType getId();

	void setId(SasangType id);

	/**
	 * 權重
	 * 
	 * 0=100(第1個), 1=200(第2個),3=300(第3個)
	 * 
	 * @return
	 */
	List<Integer> getWeights();

	void setWeights(List<Integer> weights);

}