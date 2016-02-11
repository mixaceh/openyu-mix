package org.openyu.mix.sasang.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.commons.bean.supporter.IdBeanSupporter;

/**
 * 開出的結果
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "outcome")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutcomeImpl extends IdBeanSupporter implements Outcome
{

	private static final long serialVersionUID = 2997772170133056093L;

	/**
	 * 開出的獎
	 */
	private Prize prize;

	/**
	 * 結果類別
	 */
	private OutcomeType outcomeType;

	/**
	 * 機率
	 */
	private double probability;

	public OutcomeImpl(String id)
	{
		setId(id);
	}

	public OutcomeImpl()
	{
		this(null);
	}

	public Prize getPrize()
	{
		return prize;
	}

	public void setPrize(Prize prize)
	{
		this.prize = prize;
	}

	public OutcomeType getOutcomeType()
	{
		return outcomeType;
	}

	public void setOutcomeType(OutcomeType outcomeType)
	{
		this.outcomeType = outcomeType;
	}

	public double getProbability()
	{
		return probability;
	}

	public void setProbability(double probability)
	{
		this.probability = probability;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("prize", (prize != null ? prize.getId() : null));
		builder.append("outcomeType", outcomeType);
		builder.append("probability", probability);
		return builder.toString();
	}

	public Object clone()
	{
		OutcomeImpl copy = null;
		copy = (OutcomeImpl) super.clone();
		copy.prize = clone(prize);
		return copy;
	}

}
