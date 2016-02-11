package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 行業=種族(race=4)*職業(carrer=30)=120
 * 
 * @see Race
 * @see Career
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Industry extends BaseBean
{

	/**
	 * 行業id
	 * 
	 * @return
	 */
	String getId();

	void setId(String id);

	/**
	 * 種族類型
	 * 
	 * @return
	 */
	RaceType getRaceType();

	void setRaceType(RaceType raceType);

	/**
	 * 職業類型
	 * 
	 * @return
	 */
	CareerType getCareerType();

	void setCareerType(CareerType careerType);

	/**
	 * 屬性群
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

}
