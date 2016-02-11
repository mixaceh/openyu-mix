package org.openyu.mix.app.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.AppFactor;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "appFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppFactorSupporter extends BaseBeanSupporter implements AppFactor
{

	private static final long serialVersionUID = -3127714103588505031L;

	/**
	 * 值
	 * 
	 * 每一等級間的值
	 */
	private int point;

	/**
	 * 比率,萬分位(2000)
	 * 
	 * 每一等級間的值
	 */
	private int rate;

	/**
	 * 成功機率,萬分位(2000)
	 * 
	 * 每一等級間的值
	 */
	private int probability;

	public AppFactorSupporter()
	{}

	public int getPoint()
	{
		return point;
	}

	public void setPoint(int point)
	{
		this.point = point;
	}

	public int getRate()
	{
		return rate;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

	public int getProbability()
	{
		return probability;
	}

	public void setProbability(int probability)
	{
		this.probability = probability;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("point", point);
		builder.append("rate", rate);
		builder.append("probability", probability);
		return builder.toString();
	}

	public Object clone()
	{
		AppFactorSupporter copy = null;
		copy = (AppFactorSupporter) super.clone();
		return copy;
	}
}
