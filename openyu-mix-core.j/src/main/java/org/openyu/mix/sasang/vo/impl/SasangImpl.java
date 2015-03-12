package org.openyu.mix.sasang.vo.impl;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.sasang.vo.Sasang;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

/**
 * 四象元素
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "sasang")
@XmlAccessorType(XmlAccessType.FIELD)
public class SasangImpl extends BaseBeanSupporter implements Sasang
{

	private static final long serialVersionUID = -8761741435335872707L;

	/**
	 * 四象類型,key
	 */
	SasangType id;

	/**
	 * 權重
	 */
	private List<Integer> weights = new LinkedList<Integer>();

	public SasangImpl(SasangType id)
	{
		this.id = id;
	}

	public SasangImpl()
	{
		this(null);
	}

	public SasangType getId()
	{
		return id;
	}

	public void setId(SasangType id)
	{
		this.id = id;
	}

	public List<Integer> getWeights()
	{
		return weights;
	}

	public void setWeights(List<Integer> weights)
	{
		this.weights = weights;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof SasangImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		SasangImpl other = (SasangImpl) object;
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
		builder.append("weights", weights);
		return builder.toString();
	}

	public Object clone()
	{
		SasangImpl copy = null;
		copy = (SasangImpl) super.clone();
		copy.weights = clone(weights);
		return copy;
	}
}
