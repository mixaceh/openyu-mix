package org.openyu.mix.wuxing.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.wuxing.vo.Wuxing;
import org.openyu.mix.wuxing.vo.WuxingType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

/**
 * 五行元素
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "wuxing")
@XmlAccessorType(XmlAccessType.FIELD)
public class WuxingImpl extends BaseBeanSupporter implements Wuxing
{

	private static final long serialVersionUID = -8761741435335872707L;

	/**
	 * 五行類別,key
	 */
	WuxingType id;

	public WuxingImpl(WuxingType id)
	{
		this.id = id;
	}

	public WuxingImpl()
	{
		this(null);
	}

	public WuxingType getId()
	{
		return id;
	}

	public void setId(WuxingType id)
	{
		this.id = id;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof WuxingImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		WuxingImpl other = (WuxingImpl) object;
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
		return builder.toString();
	}

	public Object clone()
	{
		WuxingImpl copy = null;
		copy = (WuxingImpl) super.clone();
		return copy;
	}
}
