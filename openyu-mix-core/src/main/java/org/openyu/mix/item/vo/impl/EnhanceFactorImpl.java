package org.openyu.mix.item.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppFactorSupporter;
import org.openyu.mix.item.vo.EnhanceFactor;
import org.openyu.mix.item.vo.EnhanceLevel;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "enhanceFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnhanceFactorImpl extends AppFactorSupporter implements EnhanceFactor
{

	private static final long serialVersionUID = -3127714103588505031L;

	/**
	 * 強化等級,key
	 */
	private EnhanceLevel id;

	public EnhanceFactorImpl(EnhanceLevel id)
	{
		this.id = id;
	}

	public EnhanceFactorImpl()
	{
		this(null);
	}

	public EnhanceLevel getId()
	{
		return id;
	}

	public void setId(EnhanceLevel id)
	{
		this.id = id;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof EnhanceFactorImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		EnhanceFactorImpl other = (EnhanceFactorImpl) object;
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
		EnhanceFactorImpl copy = null;
		copy = (EnhanceFactorImpl) super.clone();
		return copy;
	}
}
