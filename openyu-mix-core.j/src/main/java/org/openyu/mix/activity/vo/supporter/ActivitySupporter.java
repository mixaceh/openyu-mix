package org.openyu.mix.activity.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.activity.vo.Activity;
import org.openyu.mix.activity.vo.ActivityType;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.IdNamesBeanSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "activity")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivitySupporter extends IdNamesBeanSupporter implements Activity
{

	private static final long serialVersionUID = -3753199638210128445L;

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 是否已開啟
	 */
	@XmlTransient
	private boolean opened;

	/**
	 * 活動類別
	 */
	private ActivityType activityType;

	public ActivitySupporter()
	{}

	public String getDescription()
	{
		return descriptions.getName();
	}

	public void setDescription(String description)
	{
		this.descriptions.setName(description);
	}

	public boolean isOpened()
	{
		return opened;
	}

	public void setOpened(boolean opened)
	{
		this.opened = opened;
	}

	public ActivityType getActivityType()
	{
		return activityType;
	}

	public void setActivityType(ActivityType activityType)
	{
		this.activityType = activityType;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		//
		append(builder, "descriptions", descriptions);
		//
		builder.append("opened", opened);
		builder.append("activityType", activityType);
		return builder.toString();
	}

	public Object clone()
	{
		ActivitySupporter copy = null;
		copy = (ActivitySupporter) super.clone();
		copy.descriptions = clone(descriptions);
		return copy;
	}
}
