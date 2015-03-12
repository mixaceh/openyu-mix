package org.openyu.mix.item.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.AttributeGroup;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 裝備
 * 
 * +-防具
 * 
 * +-武器
 * 
 * AttributeGroup屬性群(玩家)
 * 
 * point=初值, addPoint=強化值(玩家), addRate=強化率(玩家)
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Equipment extends Item, EnhanceLevelBean {
	String KEY = Equipment.class.getName();

	/**
	 * 裝備等級類別
	 * 
	 * @return
	 */
	LevelType getLevelType();

	void setLevelType(LevelType levelType);

	/**
	 * 位置類別
	 * 
	 * @return
	 */
	PositionType getPositionType();

	void setPositionType(PositionType positionType);

	/**
	 * 系列類別
	 * 
	 * @return
	 */
	SeriesType getSeriesType();

	void setSeriesType(SeriesType seriesType);

	// /**
	// * 強化等級(玩家), 改用EnhanceLevelBean
	// *
	// * @return
	// */
	// EnhanceLevel getEnhanceLevel();
	//
	// void setEnhanceLevel(EnhanceLevel enhanceLevel);

	/**
	 * 屬性群(玩家)
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

	/**
	 * 精鍊屬性群(玩家), 是一種隨機屬性群,且精鍊後不可交易
	 * 
	 * @return
	 */
	AttributeGroup getRefiningGroup();

	void setRefiningGroup(AttributeGroup refiningGroup);
}
