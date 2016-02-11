package org.openyu.mix.manor.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.AppFactor;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 土地因子
 * 
 * 1.減少種植成本, Seed.gold, Seed.coin
 * 
 * 2.減少成長時間, Seed.time
 * 
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface LandFactor extends AppFactor
{
	String KEY = LandFactor.class.getName();

	/**
	 * 土地id
	 * 
	 * @return
	 */
	String getId();

	void setId(String id);
}
