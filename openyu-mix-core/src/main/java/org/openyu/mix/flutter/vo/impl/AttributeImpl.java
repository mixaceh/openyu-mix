package org.openyu.mix.flutter.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;
import org.openyu.commons.lang.NumberHelper;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeImpl extends BaseBeanSupporter implements Attribute
{
	private static final long serialVersionUID = 7142420910562589028L;

	/**
	 * 屬性類別,key
	 */
	private AttributeType id;

	/**
	 * 屬性值
	 */
	private int point;

	/**
	 * 增加(成長)的屬性值
	 */
	private int addPoint;

	/**
	 * 增加(成長)的比率值,萬分位(2000)
	 */
	private int addRate;

	public AttributeImpl(AttributeType id)
	{
		this.id = id;
	}

	public AttributeImpl()
	{
		this(null);
	}

	public AttributeType getId()
	{
		return id;
	}

	public void setId(AttributeType id)
	{
		this.id = id;
	}

	/**
	 * 屬性值
	 * 
	 * @return
	 */
	public int getPoint()
	{
		return point;
	}

	public void setPoint(int point)
	{
		this.point = point;
	}

	/**
	 * 增減屬性值
	 * 
	 * @param point
	 * @return
	 */
	public boolean changePoint(int point)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(this.point, point))
		{
			this.point += point;
			result = true;
		}
		return result;
	}

	/**
	 * 增加(成長)的屬性值
	 * 
	 * @return
	 */
	public int getAddPoint()
	{
		return addPoint;
	}

	public void setAddPoint(int addPoint)
	{
		this.addPoint = addPoint;
	}

	/**
	 * 增減增加(成長)的屬性值
	 * 
	 * @param addPoint
	 * @return
	 */
	public boolean changeAddPoint(int addPoint)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(this.addPoint, addPoint))
		{
			this.addPoint += addPoint;
			result = true;
		}
		return result;
	}

	/**
	 * 增加(成長)的比率值,萬分位(2000)
	 * 
	 * @return
	 */
	public int getAddRate()
	{
		return addRate;
	}

	public void setAddRate(int addRate)
	{
		this.addRate = addRate;
	}

	/**
	 * 增減增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param addRate
	 * @return
	 */
	public boolean changeAddRate(int addRate)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(this.addRate, addRate))
		{
			this.addRate += addRate;
			result = true;
		}
		return result;
	}

	/**
	 * 最後屬性值=(point + addPoint) * (addRate/10000)
	 * 
	 * @return
	 */
	public int getFinal()
	{
		int result = 0;
		result = (int) ((point + addPoint) * (1 + addRate / 10000d));
		return result;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof AttributeImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		AttributeImpl other = (AttributeImpl) object;
		if (id == null || other.id == null)
		{
			return false;
		}
		return new EqualsBuilder().append(id, other.id).isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("id", id);
		builder.append("point", point);
		builder.append("addPoint", addPoint);
		builder.append("addRate", addRate);
		return builder.toString();
	}

	public Object clone()
	{
		AttributeImpl copy = null;
		copy = (AttributeImpl) super.clone();
		return copy;
	}

}
