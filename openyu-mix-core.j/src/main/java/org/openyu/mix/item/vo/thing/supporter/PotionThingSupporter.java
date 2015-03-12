package org.openyu.mix.item.vo.thing.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.PotionThing;

@XmlRootElement(name = "potionThing")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class PotionThingSupporter extends ThingImpl implements PotionThing
{

	private static final long serialVersionUID = 3840565099881631794L;

	/**
	 * 每秒恢復比率,萬分位(2000)
	 */
	private int rate;

	public PotionThingSupporter(String id)
	{
		super(id);
	}

	public PotionThingSupporter()
	{
		this(null);
	}

	public int getRate()
	{
		return rate;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("rate", rate);
		return builder.toString();
	}

	public Object clone()
	{
		PotionThingSupporter copy = null;
		copy = (PotionThingSupporter) super.clone();
		return copy;
	}

}
