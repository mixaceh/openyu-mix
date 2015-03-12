package org.openyu.mix.flutter.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.flutter.vo.Career;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;
import org.openyu.mix.flutter.vo.CareerType;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "career")
@XmlAccessorType(XmlAccessType.FIELD)
public class CareerImpl extends BaseBeanSupporter implements Career
{

	private static final long serialVersionUID = 483484784985064240L;

	/**
	 * 職業類型,key
	 */
	private CareerType id;

	/**
	 * 屬性群
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	public CareerImpl()
	{}

	public CareerType getId()
	{
		return id;
	}

	public void setId(CareerType id)
	{
		this.id = id;
	}

	public AttributeGroup getAttributeGroup()
	{
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup)
	{
		this.attributeGroup = (AttributeGroupImpl) attributeGroup;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("id", id);
		builder.append("attributeGroup", attributeGroup);
		return builder.toString();
	}

	public Object clone()
	{
		CareerImpl copy = null;
		copy = (CareerImpl) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		return copy;
	}
}
