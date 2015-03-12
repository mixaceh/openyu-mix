package org.openyu.mix.flutter.po;

import java.util.Map;

import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.entity.SeqIdAuditNamesEntity;

/**
 * 戰鬥基本單位
 * 
 * RaceType+CareerType,如:人類戰士,妖精弓手
 * */
public interface FlutterPo extends SeqIdAuditNamesEntity
{

	String KEY = FlutterPo.class.getName();

	//	/**
	//	 * 種族類型
	//	 * 
	//	 * @return
	//	 */
	//	RaceType getRaceType();
	//
	//	void setRaceType(RaceType raceType);
	//
	//	/**
	//	 * 職業類型
	//	 * 
	//	 * @return
	//	 */
	//	CareerType getCareerType();
	//
	//	void setCareerType(CareerType careerType);

	/**
	 * 行業=種族+職業
	 * 
	 * @return
	 */
	String getIndustryId();

	void setIndustryId(String industryId);

	/**
	 * 經驗
	 * 
	 * @return
	 */
	Long getExp();

	void setExp(Long exp);

	/**
	 * 等級
	 * 
	 * @return
	 */
	Integer getLevel();

	void setLevel(Integer level);

	/**
	 * 技能經驗
	 * 
	 * @return
	 */
	Long getSp();

	void setSp(Long sp);

	/**
	 * 金幣
	 * 
	 * @return
	 */
	Long getGold();

	void setGold(Long gold);

	/**
	 * 聲望
	 * 
	 * @return
	 */
	Integer getFame();

	void setFame(Integer fame);

	/**
	 * 屬性群
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

}
