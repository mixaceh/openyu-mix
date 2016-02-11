package org.openyu.mix.chat.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.bean.IdNamesBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 朋友,指對方的朋友資訊
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Friend extends IdNamesBean
{
	String KEY = Friend.class.getName();

	/**
	 * 性別
	 * 
	 * @return
	 */
	GenderType getGenderType();

	void setGenderType(GenderType genderType);

	/**
	 * 行業=種族+職業
	 * 
	 * @return
	 */
	String getIndustryId();

	void setIndustryId(String industryId);

	/**
	 * 等級
	 * 
	 * @return
	 */
	int getLevel();

	void setLevel(int level);

	/**
	 * 戰力
	 * 
	 * @return
	 */
	int getPower();

	void setPower(int power);

	/**
	 * vip類別
	 * 
	 * @return
	 */
	VipType getVipType();

	void setVipType(VipType vipType);

}
