package org.openyu.mix.activity.vo.target.supporter;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "target")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class TargetSupporter extends BaseBeanSupporter
{

	private static final long serialVersionUID = 2292682062573915951L;

	/**
	 * 目標id
	 */
	private int id;

	/**
	 * 等級限制
	 */
	private int levelLimit;

	/**
	 * 目標開始後,到期天數
	 */
	private int expiredDay;

	/**
	 * 提示
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean tips = new NamesBeanSupporter();

	/**
	 * 獎勵道具
	 * 
	 * <道具id,道具數量>
	 */
	private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

	public TargetSupporter()
	{}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getLevelLimit()
	{
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit)
	{
		this.levelLimit = levelLimit;
	}

	public int getExpiredDay()
	{
		return expiredDay;
	}

	public void setExpiredDay(int expiredDay)
	{
		this.expiredDay = expiredDay;
	}

	public String getTip()
	{
		return tips.getName();
	}

	public void setTip(String tip)
	{
		this.tips.setName(tip);
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
		builder.append("id", id);
		builder.append("levelLimit", levelLimit);
		builder.append("expiredDay", expiredDay);
		append(builder, "tips", tips);
		builder.append("awards", awards);
		return builder.toString();
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof TargetSupporter))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		TargetSupporter other = (TargetSupporter) object;
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public Object clone()
	{
		TargetSupporter copy = null;
		copy = (TargetSupporter) super.clone();
		copy.tips = clone(tips);
		copy.awards = clone(awards);
		return copy;
	}
}
