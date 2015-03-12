package org.openyu.mix.app.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.Prize;
import org.openyu.commons.bean.supporter.IdBeanSupporter;

/**
 * 開出的獎
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "prize")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrizeImpl extends IdBeanSupporter implements Prize
{

	private static final long serialVersionUID = -5247165138207495619L;

	/**
	 * 獎勵等級
	 */
	private int level;

	/**
	 * 是否成名
	 */
	private boolean famous;

	/**
	 * 獎勵
	 * 
	 * <道具id,數量>
	 */
	private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

	public PrizeImpl(String id)
	{
		setId(id);
	}

	public PrizeImpl()
	{
		this(null);
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public boolean isFamous()
	{
		return famous;
	}

	public void setFamous(boolean famous)
	{
		this.famous = famous;
	}

	public Map<String, Integer> getAwards()
	{
		return awards;
	}

	public void setAwards(Map<String, Integer> awards)
	{
		this.awards = awards;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("level", level);
		builder.append("famous", famous);
		builder.append("awards", awards);
		return builder.toString();
	}

	public Object clone()
	{
		PrizeImpl copy = null;
		copy = (PrizeImpl) super.clone();
		copy.awards = clone(awards);
		return copy;
	}

}
