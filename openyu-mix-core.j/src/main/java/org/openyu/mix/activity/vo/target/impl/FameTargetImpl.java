package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.activity.vo.target.FameTarget;
import org.openyu.mix.activity.vo.target.supporter.TargetSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "fameTarget")
@XmlAccessorType(XmlAccessType.FIELD)
public class FameTargetImpl extends TargetSupporter implements FameTarget
{

	private static final long serialVersionUID = -2601726347181620342L;

	/**
	 * 聲望
	 */
	private int fame;

	public FameTargetImpl(int id)
	{
		setId(id);
	}

	public FameTargetImpl()
	{
		this(0);
	}

	public int getFame()
	{
		return fame;
	}

	public void setFame(int fame)
	{
		this.fame = fame;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("fame", fame);
		return builder.toString();
	}
}
