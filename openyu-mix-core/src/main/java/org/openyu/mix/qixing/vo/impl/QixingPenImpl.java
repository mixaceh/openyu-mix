package org.openyu.mix.qixing.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.app.vo.supporter.AppPenSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.qixing.vo.Outcome;
import org.openyu.mix.qixing.vo.QixingPen;
import org.openyu.commons.lang.NumberHelper;

@XmlRootElement(name = "qixingPen")
@XmlAccessorType(XmlAccessType.FIELD)
public class QixingPenImpl extends AppPenSupporter implements QixingPen
{
	private static final long serialVersionUID = 3394460367607072868L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 玩的時間
	 */
	private long playTime;

	/**
	 * 每日已玩的次數
	 */
	private int dailyTimes;

	/**
	 * 累計已玩的次數,不需每日重置
	 */
	private int accuTimes;

	/**
	 * 玩的結果
	 */
	private Outcome outcome = new OutcomeImpl();

	/**
	 * 中獎區,可堆疊道具
	 * 
	 * <itemId,amount>
	 */
	private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

	public QixingPenImpl(Role role)
	{
		this.role = role;
	}

	public QixingPenImpl()
	{
		this(null);
	}

	public long getPlayTime()
	{
		return playTime;
	}

	public void setPlayTime(long playTime)
	{
		this.playTime = playTime;
	}

	public int getDailyTimes()
	{
		return dailyTimes;
	}

	public void setDailyTimes(int dailyTimes)
	{
		this.dailyTimes = dailyTimes;
	}

	/**
	 * 增減每日已玩的次數
	 * 
	 * @param times
	 * @return
	 */
	public boolean addDailyTimes(int times)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(getDailyTimes(), times))
		{
			//最後玩的時間
			setPlayTime(System.currentTimeMillis());
			//每日已玩的次數
			setDailyTimes(getDailyTimes() + times);
			//累計每日已玩的次數,不需每日重置
			addAccuTimes(times);
			result = true;
		}
		return result;
	}

	public int getAccuTimes()
	{
		return accuTimes;
	}

	public void setAccuTimes(int accuTimes)
	{
		this.accuTimes = accuTimes;
	}

	/**
	 * 增減累計每日已玩的次數,不需每日重置
	 * 
	 * @param times
	 * @return
	 */
	public boolean addAccuTimes(int times)
	{
		boolean result = false;
		if (!NumberHelper.isAddOverflow(getAccuTimes(), times))
		{
			setAccuTimes(getAccuTimes() + times);
			result = true;
		}
		return result;
	}

	public Outcome getOutcome()
	{
		return outcome;
	}

	public void setOutcome(Outcome outcome)
	{
		this.outcome = outcome;
	}

	public Map<String, Integer> getAwards()
	{
		return awards;
	}

	public void setAwards(Map<String, Integer> awards)
	{
		this.awards = awards;
	}

	public boolean addAward(String itemId, int amount)
	{
		boolean result = false;
		if (itemId != null)
		{
			if (awards.size() < MAX_AWARDS_SIZE)
			{
				int origAmount = safeGet(awards.get(itemId));
				origAmount += amount;
				awards.put(itemId, origAmount);
				result = true;
			}
		}
		return result;
	}

	public boolean addAwards(Map<String, Integer> awards)
	{
		boolean result = false;
		//原始獎勵
		Map<String, Integer> origAwards = new LinkedHashMap<String, Integer>(this.awards);
		//
		boolean added = true;
		for (Map.Entry<String, Integer> entry : awards.entrySet())
		{
			added &= addAward(entry.getKey(), entry.getValue());
			//當有一個加入失敗時,則還原
			if (!added)
			{
				this.awards = origAwards;
				break;
			}
			result = added;
		}
		return result;
	}

	public int removeAward(String itemId)
	{
		int result = 0;
		if (itemId != null)
		{
			result = awards.remove(itemId);
		}
		return result;
	}

	/**
	 * 重置每日已玩的次數
	 * 
	 * @return
	 */
	public boolean reset()
	{
		boolean result = false;
		//
		this.playTime = 0L;
		this.dailyTimes = 0;
		result = true;
		//
		return result;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("playTime", playTime);
		builder.append("dailyTimes", dailyTimes);
		builder.append("accuTimes", accuTimes);
		builder.append("outcome", (outcome != null ? outcome.getId() + "," + outcome.getBankerId()
				+ "," + outcome.getPlayerId() : null));
		builder.append("awards", awards);
		return builder.toString();
	}

	public Object clone()
	{
		QixingPenImpl copy = null;
		copy = (QixingPenImpl) super.clone();
		//role不要clone,會造成loop
		copy.outcome = clone(outcome);
		copy.awards = clone(awards);
		return copy;
	}

}
