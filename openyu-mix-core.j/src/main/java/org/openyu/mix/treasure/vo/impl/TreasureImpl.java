package org.openyu.mix.treasure.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.bean.WeightBean;
import org.openyu.commons.bean.adapter.WeightBeanXmlAdapter;
import org.openyu.commons.bean.supporter.IdBeanSupporter;
import org.openyu.commons.bean.supporter.WeightBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "treasure")
@XmlAccessorType(XmlAccessType.FIELD)
public class TreasureImpl extends IdBeanSupporter implements Treasure
{

	private static final long serialVersionUID = 9114917360967600475L;

	/**
	 * 祕寶庫id
	 */
	@XmlTransient
	private String stockId;

	/**
	 * 賣的數量
	 */
	private int amount;

	/**
	 * 是否已購買(玩家)
	 */
	@XmlTransient
	private boolean bought;

	/**
	 * 是否成名
	 */
	private boolean famous;

	/**
	 * 權重
	 */
	@XmlJavaTypeAdapter(WeightBeanXmlAdapter.class)
	private WeightBean weight = new WeightBeanSupporter();

	public TreasureImpl(String id)
	{
		setId(id);
	}

	public TreasureImpl()
	{
		this(null);
	}

	public String getStockId()
	{
		return stockId;
	}

	public void setStockId(String stockId)
	{
		this.stockId = stockId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public boolean isBought()
	{
		return bought;
	}

	public void setBought(boolean bought)
	{
		this.bought = bought;
	}

	public boolean isFamous()
	{
		return famous;
	}

	public void setFamous(boolean famous)
	{
		this.famous = famous;
	}

	public double getProbability()
	{
		return weight.getProbability();
	}

	public void setProbability(double probability)
	{
		weight.setProbability(probability);
	}

	public int getWeight()
	{
		return weight.getWeight();
	}

	public void setWeight(int weight)
	{
		this.weight.setWeight(weight);
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("stockId", stockId);
		builder.append("amount", amount);
		builder.append("bought", bought);
		builder.append("famous", famous);
		//
		append(builder, weight);
		return builder.toString();
	}

	public Object clone()
	{
		TreasureImpl copy = null;
		copy = (TreasureImpl) super.clone();
		copy.weight = clone(weight);
		return copy;
	}

}
