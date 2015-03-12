package org.openyu.mix.chat.vo.impl;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

/**
 * 頻道設定
 */
public class ChannelImpl extends BaseBeanSupporter implements Channel
{

	private static final long serialVersionUID = 2135957386421261544L;

	/**
	 * 頻道類別,key
	 */
	private ChannelType id;

	/**
	 * 是否開啟頻道
	 */
	private boolean opened;

	public ChannelImpl(ChannelType id)
	{
		this.id = id;
	}

	public ChannelImpl()
	{
		this(null);
	}

	public ChannelType getId()
	{
		return id;
	}

	public void setId(ChannelType id)
	{
		this.id = id;
	}

	public boolean isOpened()
	{
		return opened;
	}

	public void setOpened(boolean opened)
	{
		this.opened = opened;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof ChannelImpl))
		{
			return false;
		}
		if (this == object)
		{
			return true;
		}
		ChannelImpl other = (ChannelImpl) object;
		if (getId() == null || other.getId() == null)
		{
			return false;
		}
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("id", id);
		builder.append("opened", opened);
		return builder.toString();
	}

	public Object clone()
	{
		ChannelImpl copy = null;
		copy = (ChannelImpl) super.clone();
		return copy;
	}

}
