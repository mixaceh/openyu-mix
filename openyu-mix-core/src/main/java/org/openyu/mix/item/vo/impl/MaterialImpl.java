package org.openyu.mix.item.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.supporter.ItemSupporter;

@XmlRootElement(name = "material")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialImpl extends ItemSupporter implements Material
{

	private static final long serialVersionUID = 1229854337486575699L;

	/**
	 * 材料類別
	 */
	private MaterialType materialType;

	public MaterialImpl(String id)
	{
		setId(id);
		setItemType(ItemType.MATERIAL);
	}
	public MaterialImpl()
	{
		this(null);
	}
	public MaterialType getMaterialType()
	{
		return materialType;
	}

	public void setMaterialType(MaterialType materialType)
	{
		this.materialType = materialType;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("materialType", materialType);
		return builder.toString();
	}

	public Object clone()
	{
		MaterialImpl copy = null;
		copy = (MaterialImpl) super.clone();
		return copy;
	}

}
