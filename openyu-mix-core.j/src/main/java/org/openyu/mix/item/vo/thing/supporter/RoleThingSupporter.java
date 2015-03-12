package org.openyu.mix.item.vo.thing.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.RoleThing;

@XmlRootElement(name = "roleThing")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RoleThingSupporter extends ThingImpl implements RoleThing
{

	private static final long serialVersionUID = -231263445098158277L;

	/**
	 * 成功機率,萬分位(2000)
	 */
	private int probability;

	public RoleThingSupporter(String id)
	{
		super(id);
	}

	public RoleThingSupporter()
	{
		this(null);
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
		builder.append("probability", probability);
		return builder.toString();
	}

	public Object clone()
	{
		RoleThingSupporter copy = null;
		copy = (RoleThingSupporter) super.clone();
		return copy;
	}

}
