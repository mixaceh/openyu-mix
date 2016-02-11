package org.openyu.mix.manor.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppFactorSupporter;
import org.openyu.mix.manor.vo.LandFactor;

@XmlRootElement(name = "landFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class LandFactorImpl extends AppFactorSupporter implements LandFactor
{
	private static final long serialVersionUID = 814910294240282249L;

	/**
	 * 土地id
	 */
	private String id;

	public LandFactorImpl(String id)
	{
		this.id = id;
	}

	public LandFactorImpl()
	{
		this(null);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof LandFactorImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		LandFactorImpl other = (LandFactorImpl) object;
		return new EqualsBuilder().append(id, other.id).isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone()
	{
		LandFactorImpl copy = null;
		copy = (LandFactorImpl) super.clone();
		return copy;
	}
}
