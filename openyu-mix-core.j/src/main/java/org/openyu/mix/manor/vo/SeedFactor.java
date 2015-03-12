package org.openyu.mix.manor.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.AppFactor;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 種子因子
 * 
 * 增加產量, Seed.items.Integer
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface SeedFactor extends AppFactor
{
	String KEY = SeedFactor.class.getName();

	/**
	 * 種子id
	 * 
	 * @return
	 */
	String getId();

	void setId(String id);
}
