package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.SeqIdAuditNamesBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 戰鬥單位
 * 
 * Flutter
 * 
 * +-Role
 * 
 * +-Beast
 * 
 * +-Monster
 * 
 * 1.行業, RaceType+CareerType,如:人類戰士,妖精弓手
 * 
 * 2.屬性 AttributeGroup
 * 
 * point=行業屬性初值+(level-1)*成長值
 * 
 * addPoint=???
 * 
 * addRate=???
 * 
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Flutter extends SeqIdAuditNamesBean {

	String KEY = Flutter.class.getName();

	// /**
	// * 種族類型
	// *
	// * @return
	// */
	// RaceType getRaceType();
	//
	// void setRaceType(RaceType raceType);
	//
	// /**
	// * 職業類型
	// *
	// * @return
	// */
	// CareerType getCareerType();
	//
	// void setCareerType(CareerType careerType);
	// ---------------------------------------------------
	// 外觀
	// ---------------------------------------------------

	/**
	 * 性別
	 * 
	 * @return
	 */
	GenderType getGenderType();

	void setGenderType(GenderType genderType);

	/**
	 * 髮型
	 * 
	 * @return
	 */
	HairType getHairType();

	void setHairType(HairType hairType);

	/**
	 * 臉型
	 * 
	 * @return
	 */
	FaceType getFaceType();

	void setFaceType(FaceType faceType);

	// ---------------------------------------------------
	/**
	 * 戰力
	 * 
	 * @return
	 */
	int getPower();

	void setPower(int power);

	// ---------------------------------------------------
	/**
	 * 地圖
	 * 
	 * @return
	 */
	String getMap();

	void setMap(String map);

	/**
	 * 座標
	 * 
	 * @return
	 */
	int[] getCoordinate();

	void setCoordinate(int pixelX, int pixelY);

	// ---------------------------------------------------
	/**
	 * 行業=種族+職業
	 * 
	 * @return
	 */
	String getIndustryId();

	void setIndustryId(String industryId);

	// ---------------------------------------------------
	// 經驗
	// ---------------------------------------------------
	/**
	 * 經驗
	 * 
	 * @return
	 */
	long getExp();

	void setExp(long exp);

	/**
	 * 等級
	 * 
	 * @return
	 */
	int getLevel();

	/**
	 * 有事件觸發
	 * 
	 * @param level
	 */
	void setLevel(int level);

	/**
	 * 技魂(技能經驗)
	 * 
	 * @return
	 */
	long getSp();

	void setSp(long sp);

	/**
	 * 金幣
	 * 
	 * @return
	 */
	long getGold();

	void setGold(long gold);

	/**
	 * 聲望
	 * 
	 * @return
	 */
	int getFame();

	/**
	 * 有事件觸發
	 * 
	 * @param fame
	 */
	void setFame(int fame);

	// ---------------------------------------------------
	// 屬性
	// ---------------------------------------------------
	/**
	 * 屬性群
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

	// /**
	// * 取得已改變的屬性type
	// *
	// * @return
	// */
	// List<AttributeType> getChangedAttributes();
	//
	// /**
	// * 重置上次已改變的屬性
	// */
	// void resetChangedAttributes();

}
