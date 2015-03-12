package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.activity.vo.target.VipTarget;
import org.openyu.mix.activity.vo.target.supporter.TargetSupporter;
import org.openyu.mix.vip.vo.VipType;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "vipTarget")
@XmlAccessorType(XmlAccessType.FIELD)
public class VipTargetImpl extends TargetSupporter implements VipTarget
{

	private static final long serialVersionUID = -6099475577988765186L;

	/**
	 * vip類別
	 */
	private VipType vipType;

	public VipTargetImpl(int id)
	{
		setId(id);
	}

	public VipTargetImpl()
	{
		this(0);
	}

	public VipType getVipType()
	{
		return vipType;
	}

	public void setVipType(VipType vipType)
	{
		this.vipType = vipType;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("vipType", vipType);
		return builder.toString();
	}
}
