package org.openyu.mix.item.vo.thing.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.BackThing;

@XmlRootElement(name = "backThing")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BackThingSupporter extends ThingImpl implements BackThing
{

	private static final long serialVersionUID = -4164776406786490737L;

	public BackThingSupporter(String id)
	{
		super(id);
	}

	public BackThingSupporter()
	{
		this(null);
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone()
	{
		BackThingSupporter copy = null;
		copy = (BackThingSupporter) super.clone();
		return copy;
	}

}
