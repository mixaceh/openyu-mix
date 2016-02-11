package org.openyu.mix.manor.vo;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.item.vo.EnhanceLevelBean;
import org.openyu.mix.item.vo.Item;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 土地
 * 
 * AttributeGroup屬性群(玩家)
 * 
 * point=初值, addPoint=強化值(玩家), addRate=強化率(玩家)
 * 
 * org.openyu.mix.flutter.vo.Attribute.AttributeType.MANOR_*
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Land extends Item, EnhanceLevelBean
{

	String KEY = Land.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "L_";

	/**
	 * 等級限制
	 * 
	 * @return
	 */
	int getLevelLimit();

	void setLevelLimit(int levelLimit);

	/**
	 * 屬性群(玩家)
	 * 
	 * @return
	 */
	AttributeGroup getAttributeGroup();

	void setAttributeGroup(AttributeGroup attributeGroup);

	/**
	 * 種子
	 * 
	 * <種子id>
	 * 
	 * @return
	 */
	List<String> getSeeds();

	void setSeeds(List<String> seeds);
}
