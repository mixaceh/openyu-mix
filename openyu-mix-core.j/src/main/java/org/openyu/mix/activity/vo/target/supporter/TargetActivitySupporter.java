package org.openyu.mix.activity.vo.target.supporter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.activity.vo.supporter.ActivitySupporter;
import org.openyu.mix.activity.vo.target.Target;
import org.openyu.mix.activity.vo.target.TargetActivity;
import org.openyu.mix.activity.vo.target.adapter.IntegerTargetXmlAdapter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "targetActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class TargetActivitySupporter extends ActivitySupporter implements TargetActivity
{

	private static final long serialVersionUID = -6779044093327618501L;

	/**
	 * 開始日期
	 */
	private String begDate;

	/**
	 * 結束日期
	 */
	private String endDate;

	/**
	 * 是否每日重置
	 */
	private boolean dailyReset;

	/**
	 * 是否依序完成
	 */
	private boolean inOrder;

	/**
	 * 目標
	 */
	@XmlJavaTypeAdapter(IntegerTargetXmlAdapter.class)
	private Map<Integer, Target> targets = new LinkedHashMap<Integer, Target>();

	public TargetActivitySupporter()
	{}

	public String getBegDate()
	{
		return begDate;
	}

	public void setBegDate(String begDate)
	{
		this.begDate = begDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public boolean isDailyReset()
	{
		return dailyReset;
	}

	public void setDailyReset(boolean dailyReset)
	{
		this.dailyReset = dailyReset;
	}

	public boolean isInOrder()
	{
		return inOrder;
	}

	public void setInOrder(boolean inOrder)
	{
		this.inOrder = inOrder;
	}

	public Map<Integer, Target> getTargets()
	{
		return targets;
	}

	public void setTargets(Map<Integer, Target> targets)
	{
		this.targets = targets;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("begDate", begDate);
		builder.append("endDate", endDate);
		builder.append("dailyReset", dailyReset);
		builder.append("inOrder", inOrder);
		builder.append("targets", targets);
		return builder.toString();
	}
}
