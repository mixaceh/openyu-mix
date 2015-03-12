package org.openyu.mix.flutter.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 屬性群,因有角色,裝備,行業,土地使用
 * 
 * 故拉出來獨立集合處理
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AttributeGroup extends BaseBean
{
	/**
	 * 屬性
	 */
	Map<AttributeType, Attribute> getAttributes();

	void setAttributes(Map<AttributeType, Attribute> attributes);

	/**
	 * 增加屬性
	 * 
	 * @param attribute
	 */
	Attribute addAttribute(Attribute attribute);

	/**
	 * 取得屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	Attribute getAttribute(AttributeType attributeType);

	/**
	 * 移除屬性
	 * 
	 * @param attributeType
	 */
	Attribute removeAttribute(AttributeType attributeType);

	/**
	 * 移除屬性
	 * 
	 * @param attribute
	 * @return
	 */
	Attribute removeAttribute(Attribute attribute);

	/**
	 * 屬性是否存在
	 * 
	 * @param attributeType
	 * @return
	 */
	boolean containAttribute(AttributeType attributeType);

	/**
	 * 屬性是否存在
	 * 
	 * @param attribute
	 * @return
	 */
	boolean containAttribute(Attribute attribute);

	/**
	 * 清除屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	boolean clearAttribute(AttributeType attributeType);

	/**
	 * 清除所有屬性
	 * 
	 * @return
	 */
	boolean clearAttributes();

	/**
	 * 清除增加的屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	boolean clearAddAttribute(AttributeType attributeType);

	/**
	 * 清除所有增加的屬性
	 * 
	 * @return
	 */
	boolean clearAddAttributes();

	/**
	 * 設定屬性
	 * 
	 * @param attributeType
	 * @param attribute
	 */
	Attribute setAttribute(AttributeType attributeType, Attribute attribute);

	//---------------------------------------------------
	// 屬性相關
	//---------------------------------------------------
	/**
	 * 屬性值
	 * 
	 * @param attributeType
	 * @return
	 */
	int getPoint(AttributeType attributeType);

	void setPoint(AttributeType attributeType, int point);

	/**
	 * 增減屬性值
	 * 
	 * @param attributeType
	 * @param point
	 * @return
	 */
	boolean changePoint(AttributeType attributeType, int point);

	/**
	 * 增加(成長)的屬性值
	 * 
	 * @param attributeType
	 * @return
	 */
	int getAddPoint(AttributeType attributeType);

	void setAddPoint(AttributeType attributeType, int addPoint);

	/**
	 * 增減增加(成長)的屬性值
	 * 
	 * @param attributeType
	 * @param enhancePoint
	 * @return
	 */
	boolean changeAddPoint(AttributeType attributeType, int point);

	/**
	 * 增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param attributeType
	 * @return
	 */
	int getAddRate(AttributeType attributeType);

	void setAddRate(AttributeType attributeType, int addRate);

	/**
	 * 增減增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param attributeType
	 * @param enhanceRate
	 * @return
	 */
	boolean changeAddRate(AttributeType attributeType, int rate);

	/**
	 * 最後屬性值=(point + addPoint) * (addRate/10000)
	 * 
	 * @param attributeType
	 * @return
	 */
	int getFinal(AttributeType attributeType);
}
