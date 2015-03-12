package org.openyu.mix.train.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppPenSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.commons.lang.NumberHelper;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "trainPen")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrainPenImpl extends AppPenSupporter implements TrainPen
{

	private static final long serialVersionUID = 5073307472276879735L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 加入時間,每次加入訓練都會改變
	 */
	private long joinTime;

	/**
	 * 離開時間,每次離開訓練都會改變
	 */
	private long quitTime;

	/**
	 * 每天已訓練毫秒
	 */
	private long dailyMills;

	/**
	 * 累計每天已訓練毫秒,不需每日重置
	 */
	private long accuMills;

	/**
	 * 鼓舞時間
	 */
	private long inspireTime;

	public TrainPenImpl(Role role)
	{
		this.role = role;
	}

	public TrainPenImpl()
	{
		this(null);
	}

	public long getJoinTime()
	{
		return joinTime;
	}

	public void setJoinTime(long joinTime)
	{
		this.joinTime = joinTime;
	}

	public long getQuitTime()
	{
		return quitTime;
	}

	public void setQuitTime(long quitTime)
	{
		this.quitTime = quitTime;
	}

	public long getDailyMills()
	{
		return dailyMills;
	}

	public void setDailyMills(long dailyMills)
	{
		this.dailyMills = dailyMills;
	}

	public boolean addDailyMills(long mills)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(getDailyMills(), mills))
		{
			//每天已訓練毫秒
			setDailyMills(getDailyMills() + mills);
			//累計每天已訓練毫秒,不需每日重置
			addAccuMills(mills);
			result = true;
		}
		return result;
	}

	public long getAccuMills()
	{
		return accuMills;
	}

	public void setAccuMills(long accuMills)
	{
		this.accuMills = accuMills;
	}

	public boolean addAccuMills(long mills)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(getAccuMills(), mills))
		{
			setAccuMills(getAccuMills() + mills);
			result = true;
		}
		return result;
	}

	public long getInspireTime()
	{
		return inspireTime;
	}

	public void setInspireTime(long inspireTime)
	{
		this.inspireTime = inspireTime;
	}

	/**
	 * 重置
	 * 
	 * @return
	 */
	public boolean reset()
	{
		boolean result = false;
		//
		this.joinTime = 0L;
		this.quitTime = 0L;
		this.dailyMills = 0L;
		this.inspireTime = 0L;
		//
		result = true;
		return result;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("joinTime", joinTime);
		builder.append("quitTime", quitTime);
		builder.append("dailyMills", dailyMills);
		builder.append("accuMills", accuMills);
		builder.append("inspireTime", inspireTime);
		return builder.toString();
	}

	public Object clone()
	{
		TrainPenImpl copy = null;
		copy = (TrainPenImpl) super.clone();
		//role不要clone,會造成loop
		return copy;
	}

}
