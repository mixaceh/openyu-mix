package org.openyu.mix.item.vo.thing.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.EnhanceThing;

@XmlRootElement(name = "enhanceThing")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EnhanceThingSupporter extends ThingImpl implements EnhanceThing
{

	private static final long serialVersionUID = 8964945671771671569L;

	/**
	 * 強化類型
	 */
	private EnhanceType enhanceType;

	public EnhanceThingSupporter(String id)
	{
		super(id);
	}

	public EnhanceThingSupporter()
	{
		this(null);
	}

	public EnhanceType getEnhanceType()
	{
		return enhanceType;
	}

	public void setEnhanceType(EnhanceType enhanceType)
	{
		this.enhanceType = enhanceType;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("enhanceType", enhanceType);
		return builder.toString();
	}

	public Object clone()
	{
		EnhanceThingSupporter copy = null;
		copy = (EnhanceThingSupporter) super.clone();
		return copy;
	}

}
