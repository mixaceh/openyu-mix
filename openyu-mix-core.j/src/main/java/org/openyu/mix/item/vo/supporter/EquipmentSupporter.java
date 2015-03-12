package org.openyu.mix.item.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.SeriesType;

@XmlRootElement(name = "equipment")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EquipmentSupporter extends ItemSupporter implements Equipment
{
	private static final long serialVersionUID = 1167706219628780150L;

	/**
	 * 等級類別
	 */
	private LevelType levelType;

	/**
	 * 部位類別,位置類別
	 */
	private PositionType positionType;

	/**
	 * 系列類別
	 */
	private SeriesType seriesType;

	/**
	 * 強化等級(玩家)
	 */
	@XmlTransient
	private EnhanceLevel enhanceLevel = EnhanceLevel._0;

	/**
	 * 屬性群(玩家)
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	/**
	 * 精鍊屬性群(玩家)
	 */
	@XmlTransient
	private AttributeGroup refiningGroup = new AttributeGroupImpl();

	public EquipmentSupporter()
	{}

	public LevelType getLevelType()
	{
		return levelType;
	}

	public void setLevelType(LevelType levelType)
	{
		this.levelType = levelType;
	}

	public PositionType getPositionType()
	{
		return positionType;
	}

	public void setPositionType(PositionType positionType)
	{
		this.positionType = positionType;
	}

	public SeriesType getSeriesType()
	{
		return seriesType;
	}

	public void setSeriesType(SeriesType seriesType)
	{
		this.seriesType = seriesType;
	}

	public EnhanceLevel getEnhanceLevel()
	{
		return enhanceLevel;
	}

	public void setEnhanceLevel(EnhanceLevel enhanceLevel)
	{
		this.enhanceLevel = enhanceLevel;
	}

	public AttributeGroup getAttributeGroup()
	{
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup)
	{
		this.attributeGroup = attributeGroup;
	}

	public AttributeGroup getRefiningGroup()
	{
		return refiningGroup;
	}

	public void setRefiningGroup(AttributeGroup refiningGroup)
	{
		this.refiningGroup = refiningGroup;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("levelType", levelType);
		builder.append("positionType", positionType);
		builder.append("seriesType", seriesType);
		builder.append("enhanceLevel", enhanceLevel);
		builder.append("attributeGroup", attributeGroup);
		builder.append("refiningGroup", refiningGroup);
		return builder.toString();
	}

	public Object clone()
	{
		EquipmentSupporter copy = null;
		copy = (EquipmentSupporter) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		copy.refiningGroup = clone(refiningGroup);
		return copy;
	}

}
