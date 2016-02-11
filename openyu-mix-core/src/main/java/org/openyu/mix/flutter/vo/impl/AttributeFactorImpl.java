package org.openyu.mix.flutter.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeFactor;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "attributeFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeFactorImpl extends BaseBeanSupporter implements AttributeFactor
{

	private static final long serialVersionUID = 5765257878232343827L;

	/**
	 * 屬性類別,key
	 */
	private AttributeType id;

	/**
	 * 因子
	 */
	private int factor;

	/**
	 * 因子2
	 */
	private int factor2;

	/**
	 * 因子3
	 */
	private int factor3;

	public AttributeFactorImpl(AttributeType id)
	{
		this.id = id;
	}

	public AttributeFactorImpl()
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
	 * 因子
	 * 
	 * @return
	 */
	public int getFactor()
	{
		return factor;
	}

	public void setFactor(int factor)
	{
		this.factor = factor;
	}

	/**
	 * 因子2
	 * 
	 * @return
	 */
	public int getFactor2()
	{
		return factor2;
	}

	public void setFactor2(int factor2)
	{
		this.factor2 = factor2;
	}

	/**
	 * 因子3
	 * 
	 * @return
	 */
	public int getFactor3()
	{
		return factor3;
	}

	public void setFactor3(int factor3)
	{
		this.factor3 = factor3;
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
		AttributeFactorImpl copy = null;
		copy = (AttributeFactorImpl) super.clone();
		return copy;
	}

}
