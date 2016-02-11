package org.openyu.mix.chat.vo.impl;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.chat.vo.Friend;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.bean.supporter.IdNamesBeanSupporter;

public class FriendImpl extends IdNamesBeanSupporter implements Friend
{

	private static final long serialVersionUID = -8914578497500036373L;

	private GenderType genderType;

	private String industryId;

	private int level;

	private int power;

	private VipType vipType;

	public FriendImpl()
	{}

	public GenderType getGenderType()
	{
		return genderType;
	}

	public void setGenderType(GenderType genderType)
	{
		this.genderType = genderType;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int getPower()
	{
		return power;
	}

	public void setPower(int power)
	{
		this.power = power;
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
		builder.append("genderType", genderType);
		builder.append("industryId", industryId);
		builder.append("level", level);
		builder.append("power", power);
		builder.append("vipType", vipType);
		return builder.toString();
	}

	public Object clone()
	{
		FriendImpl copy = null;
		copy = (FriendImpl) super.clone();
		return copy;
	}
}
