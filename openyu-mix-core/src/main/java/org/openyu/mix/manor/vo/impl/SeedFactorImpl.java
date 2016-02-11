package org.openyu.mix.manor.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppFactorSupporter;
//--------------------------------------------------
//jaxb
//--------------------------------------------------
import org.openyu.mix.manor.vo.SeedFactor;

@XmlRootElement(name = "seedFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedFactorImpl extends AppFactorSupporter implements SeedFactor
{

	private static final long serialVersionUID = 344793968942870971L;

	/**
	 * 種子id
	 */
	private String id;

	public SeedFactorImpl(String id)
	{
		this.id = id;
	}

	public SeedFactorImpl()
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
		if (!(object instanceof SeedFactorImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		SeedFactorImpl other = (SeedFactorImpl) object;
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
		SeedFactorImpl copy = null;
		copy = (SeedFactorImpl) super.clone();
		return copy;
	}
}
