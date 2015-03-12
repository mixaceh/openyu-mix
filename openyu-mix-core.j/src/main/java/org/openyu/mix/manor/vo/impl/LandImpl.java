package org.openyu.mix.manor.vo.impl;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.supporter.ItemSupporter;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.item.vo.ItemType;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "land")
@XmlAccessorType(XmlAccessType.FIELD)
public class LandImpl extends ItemSupporter implements Land {

	private static final long serialVersionUID = 2205494640035324321L;

	/**
	 * 等級限制
	 */
	private int levelLimit;

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
	 * 種子
	 */
	private List<String> seeds = new LinkedList<String>();

	public LandImpl(String id) {
		setId(id);
		setItemType(ItemType.LAND);
	}

	public LandImpl() {
		this(null);
	}

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public EnhanceLevel getEnhanceLevel() {
		return enhanceLevel;
	}

	public void setEnhanceLevel(EnhanceLevel enhanceLevel) {
		this.enhanceLevel = enhanceLevel;
	}

	public AttributeGroup getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup) {
		this.attributeGroup = attributeGroup;
	}

	public List<String> getSeeds() {
		return seeds;
	}

	public void setSeeds(List<String> seeds) {
		this.seeds = seeds;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("levelLimit", levelLimit);
		builder.append("enhanceLevel", enhanceLevel);
		builder.append("attributeGroup", attributeGroup);
		builder.append("seeds", seeds);
		return builder.toString();
	}

	public Object clone() {
		LandImpl copy = null;
		copy = (LandImpl) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		copy.seeds = clone(seeds);
		return copy;
	}
}
