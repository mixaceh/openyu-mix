package org.openyu.mix.item.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.IdNamesBeanSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ItemSupporter extends IdNamesBeanSupporter implements Item
{

	private static final long serialVersionUID = -2986578239931324319L;

	/**
	 * 唯一識別碼(玩家)
	 */
	@XmlTransient
	private String uniqueId;

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 道具類別
	 */
	private ItemType itemType;

	/**
	 * 數量(玩家)
	 */
	@XmlTransient
	private int amount;

	/**
	 * 最大堆疊數量,0=無限
	 */
	private int maxAmount;

	/**
	 * 金幣價格
	 */
	private long price;

	/**
	 * 儲值幣價格
	 */
	private int coin;

	/**
	 * 是否綁定,束缚(不可交易)
	 */
	private boolean tied;

	/**
	 * 圖標id
	 */
	private String iconId;

	public ItemSupporter()
	{}

	public String getUniqueId()
	{
		return uniqueId;
	}

	public void setUniqueId(String uid)
	{
		this.uniqueId = uid;
	}

	public String getDescription()
	{
		return descriptions.getName();
	}

	public void setDescription(String description)
	{
		this.descriptions.setName(description);
	}

	public NamesBean getDescriptions()
	{
		return descriptions;
	}

	public void setDescriptions(NamesBean descriptions)
	{
		this.descriptions = descriptions;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getMaxAmount()
	{
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public int getCoin()
	{
		return coin;
	}

	public void setCoin(int coin)
	{
		this.coin = coin;
	}

	public boolean isTied()
	{
		return tied;
	}

	public void setTied(boolean tied)
	{
		this.tied = tied;
	}

	public String getIconId()
	{
		return iconId;
	}

	public void setIconId(String iconName)
	{
		this.iconId = iconName;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("uniqueId", uniqueId);
		append(builder, "descriptions", descriptions);
		builder.append("itemType", itemType);
		builder.append("amount", amount);
		builder.append("maxAmount", maxAmount);
		builder.append("price", price);
		builder.append("coin", coin);
		builder.append("tied", tied);
		builder.append("iconId", iconId);
		return builder.toString();
	}

	public Object clone()
	{
		ItemSupporter copy = null;
		copy = (ItemSupporter) super.clone();
		copy.descriptions = clone(descriptions);
		return copy;
	}

}
