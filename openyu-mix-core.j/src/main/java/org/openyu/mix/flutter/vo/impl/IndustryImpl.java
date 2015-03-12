package org.openyu.mix.flutter.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "industry")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndustryImpl extends BaseBeanSupporter implements Industry
{

	private static final long serialVersionUID = -8583165554357213387L;

	/**
	 * 行業id,key
	 */
	private String id;

	/**
	 * 種族類別
	 */
	private RaceType raceType;

	/**
	 * 職業類別
	 */
	private CareerType careerType;

	/**
	 * 屬性群
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	public IndustryImpl()
	{}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public RaceType getRaceType()
	{
		return raceType;
	}

	public void setRaceType(RaceType raceType)
	{
		this.raceType = raceType;
	}

	public CareerType getCareerType()
	{
		return careerType;
	}

	public void setCareerType(CareerType careerType)
	{
		this.careerType = careerType;
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
		builder.append("raceType", raceType);
		builder.append("careerType", careerType);
		builder.append("attributeGroup", attributeGroup);
		return builder.toString();
	}

	public Object clone()
	{
		IndustryImpl copy = null;
		copy = (IndustryImpl) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		return copy;
	}
}
