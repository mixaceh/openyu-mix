package org.openyu.mix.flutter.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Race;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImpl extends BaseBeanSupporter implements Race
{

	private static final long serialVersionUID = 483484784985064240L;

	/**
	 * 職業類型,key
	 */
	private RaceType id;

	/**
	 * 屬性群
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	public RaceImpl()
	{}

	public RaceType getId()
	{
		return id;
	}

	public void setId(RaceType id)
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
		RaceImpl copy = null;
		copy = (RaceImpl) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		return copy;
	}
}
