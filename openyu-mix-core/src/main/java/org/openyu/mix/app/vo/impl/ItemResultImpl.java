package org.openyu.mix.app.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.vo.ItemResult;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.item.vo.Item;

@XmlRootElement(name = "itemResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemResultImpl extends AppResultSupporter implements ItemResult
{

	private static final long serialVersionUID = 824222800032872759L;

	/**
	 * 道具
	 */
	private Item item;

	public ItemResultImpl()
	{

	}

	public Item getItem()
	{
		return item;
	}

	public void setItem(Item item)
	{
		this.item = item;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.appendSuper(super.toString());
		builder.append("item", (item != null ? item.getId() + ", " + item.getUniqueId() : null));
		return builder.toString();
	}

	public Object clone()
	{
		ItemResultImpl copy = null;
		copy = (ItemResultImpl) super.clone();
		copy.item = clone(item);
		return copy;
	}
}
