package org.openyu.mix.qixing.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.qixing.vo.Qixing;
import org.openyu.mix.qixing.vo.QixingType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

/**
 * 七星元素
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "qixing")
@XmlAccessorType(XmlAccessType.FIELD)
public class QixingImpl extends BaseBeanSupporter implements Qixing
{

	private static final long serialVersionUID = -8761741435335872707L;

	/**
	 * 七星類別,key
	 */
	QixingType id;

	public QixingImpl(QixingType id)
	{
		this.id = id;
	}

	public QixingImpl()
	{
		this(null);
	}

	public QixingType getId()
	{
		return id;
	}

	public void setId(QixingType id)
	{
		this.id = id;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof QixingImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		QixingImpl other = (QixingImpl) object;
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
		QixingImpl copy = null;
		copy = (QixingImpl) super.clone();
		return copy;
	}
}
