package org.openyu.mix.flutter.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.adapter.AttributeTypeAttributeXmlAdapter;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;
//--------------------------------------------------
//jaxb
//--------------------------------------------------

@XmlRootElement(name = "attributeGroup")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeGroupImpl extends BaseBeanSupporter implements AttributeGroup
{

	private static final long serialVersionUID = 5938246729553715646L;

	/**
	 * 屬性
	 */
	@XmlJavaTypeAdapter(AttributeTypeAttributeXmlAdapter.class)
	private Map<AttributeType, Attribute> attributes = new LinkedHashMap<AttributeType, Attribute>();

	public AttributeGroupImpl()
	{}

	/**
	 * 屬性
	 */
	public Map<AttributeType, Attribute> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Map<AttributeType, Attribute> attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * 增加屬性
	 * 
	 * @param attribute
	 */
	public Attribute addAttribute(Attribute attribute)
	{
		Attribute result = null;
		if (attribute != null)
		{
			result = attributes.put(attribute.getId(), attribute);
		}
		return result;
	}

	/**
	 * 取得屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	public Attribute getAttribute(AttributeType attributeType)
	{
		Attribute result = null;
		if (attributeType != null)
		{
			result = attributes.get(attributeType);
		}
		return result;
	}

	/**
	 * 移除屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	public Attribute removeAttribute(AttributeType attributeType)
	{
		Attribute result = null;
		if (attributeType != null)
		{
			result = attributes.remove(attributeType);
		}
		return result;
	}

	/**
	 * 移除屬性
	 * 
	 * @param attribute
	 * @return
	 */
	public Attribute removeAttribute(Attribute attribute)
	{
		Attribute result = null;
		if (attribute != null)
		{
			result = removeAttribute(attribute.getId());
		}
		return result;
	}

	/**
	 * 屬性是否存在
	 * 
	 * @param attributeType
	 * @return
	 */
	public boolean containAttribute(AttributeType attributeType)
	{
		boolean result = false;
		if (attributeType != null)
		{
			result = attributes.containsKey(attributeType);
		}
		return result;
	}

	/**
	 * 屬性是否存在
	 * 
	 * @param attribute
	 * @return
	 */
	public boolean containAttribute(Attribute attribute)
	{
		boolean result = false;
		if (attribute != null)
		{
			result = containAttribute(attribute.getId());
		}
		return result;
	}

	/**
	 * 清除屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	public boolean clearAttribute(AttributeType attributeType)
	{
		boolean result = false;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			attribute.setPoint(0);
			attribute.setAddPoint(0);
			attribute.setAddRate(0);
			result = true;
		}
		return result;
	}

	/**
	 * 清除所有屬性
	 * 
	 * @return
	 */
	public boolean clearAttributes()
	{
		boolean result = false;
		for (Attribute attribute : attributes.values())
		{
			attribute.setPoint(0);
			attribute.setAddPoint(0);
			attribute.setAddRate(0);
			result = true;
		}
		return result;
	}

	/**
	 * 清除增加的屬性
	 * 
	 * @param attributeType
	 * @return
	 */
	public boolean clearAddAttribute(AttributeType attributeType)
	{
		boolean result = false;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			attribute.setAddPoint(0);
			attribute.setAddRate(0);
			result = true;
		}
		return result;
	}

	/**
	 * 清除所有增加的屬性
	 * 
	 * @return
	 */
	public boolean clearAddAttributes()
	{
		boolean result = false;
		for (Attribute attribute : attributes.values())
		{
			attribute.setAddPoint(0);
			attribute.setAddRate(0);
			result = true;
		}
		return result;
	}

	/**
	 * 設定屬性
	 * 
	 * @param attributeType
	 * @param attribute
	 */
	public Attribute setAttribute(AttributeType attributeType, Attribute attribute)
	{
		Attribute result = null;
		if (attributeType != null)
		{
			result = attributes.put(attributeType, attribute);
		}
		return result;
	}

	//---------------------------------------------------
	// 屬性相關
	//---------------------------------------------------
	/**
	 * 屬性值
	 * 
	 * @param attributeType
	 * @return
	 */
	public int getPoint(AttributeType attributeType)
	{
		int result = 0;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.getPoint();
		}
		return result;
	}

	public void setPoint(AttributeType attributeType, int point)
	{
		Attribute attribute = getAttribute(attributeType);
		if (attribute == null)
		{
			attribute = new AttributeImpl(attributeType);
			addAttribute(attribute);
		}
		attribute.setPoint(point);
	}

	/**
	 * 增減屬性值
	 * 
	 * @param attributeType
	 * @param point
	 * @return
	 */
	public boolean changePoint(AttributeType attributeType, int point)
	{
		boolean result = false;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.changePoint(point);
		}
		return result;
	}

	/**
	 * 增加(成長)的屬性值
	 * 
	 * @param attributeType
	 * @return
	 */
	public int getAddPoint(AttributeType attributeType)
	{
		int result = 0;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.getAddPoint();
		}
		return result;
	}

	public void setAddPoint(AttributeType attributeType, int addPoint)
	{
		Attribute attribute = getAttribute(attributeType);
		if (attribute == null)
		{
			attribute = new AttributeImpl(attributeType);
			addAttribute(attribute);
		}
		attribute.setAddPoint(addPoint);
	}

	/**
	 * 增減增加(成長)的屬性值
	 * 
	 * @param attributeType
	 * @param enhancePoint
	 * @return
	 */
	public boolean changeAddPoint(AttributeType attributeType, int point)
	{
		boolean result = false;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.changeAddPoint(point);
		}
		return result;
	}

	/**
	 * 增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param attributeType
	 * @return
	 */
	public int getAddRate(AttributeType attributeType)
	{
		int result = 0;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.getAddRate();
		}
		return result;
	}

	public void setAddRate(AttributeType attributeType, int addRate)
	{
		Attribute attribute = getAttribute(attributeType);
		if (attribute == null)
		{
			attribute = new AttributeImpl(attributeType);
			addAttribute(attribute);
		}
		attribute.setAddPoint(addRate);
	}

	/**
	 * 增減增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param attributeType
	 * @param enhanceRate
	 * @return
	 */
	public boolean changeAddRate(AttributeType attributeType, int rate)
	{
		boolean result = false;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.changeAddRate(rate);
		}
		return result;
	}

	/**
	 * 最後屬性值=(point + addPoint) * (addRate/10000)
	 * 
	 * @param attributeType
	 * @return
	 */
	public int getFinal(AttributeType attributeType)
	{
		int result = 0;
		Attribute attribute = getAttribute(attributeType);
		if (attribute != null)
		{
			result = attribute.getFinal();
			return result;
		}
		return result;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		//builder.append("attributes", attributes);
		append(builder, "attributes", attributes);
		return builder.toString();
	}

	/**
	 * for toString
	 * 
	 * @param builder
	 * @param fieldName
	 * @param attributes
	 */
	protected void append(ToStringBuilder builder, String fieldName,
							Map<AttributeType, Attribute> attributes)
	{
		if (attributes != null)
		{
			Map<AttributeType, String> buff = new LinkedHashMap<AttributeType, String>();
			for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet())
			{
				Attribute attribute = entry.getValue();
				StringBuilder sb = new StringBuilder();
				sb.append(attribute.getPoint());
				sb.append(",");
				sb.append(attribute.getAddPoint());
				sb.append(",");
				sb.append(attribute.getAddRate());
				buff.put(entry.getKey(), sb.toString());
			}
			builder.append(fieldName, buff);
		}
		else
		{
			builder.append(fieldName, (Object) null);
		}
	}

	public Object clone()
	{
		AttributeGroupImpl copy = null;
		copy = (AttributeGroupImpl) super.clone();
		copy.attributes = clone(attributes);
		return copy;
	}
}
