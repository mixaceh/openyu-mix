package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.vip.vo.VipType;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * vip目標
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface VipTarget extends Target
{

	/**
	 * vip類別
	 * 
	 * @return
	 */
	VipType getVipType();

	void setVipType(VipType vipType);

}
