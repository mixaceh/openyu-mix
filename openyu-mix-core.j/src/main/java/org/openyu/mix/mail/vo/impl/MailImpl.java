package org.openyu.mix.mail.vo.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.mail.vo.Mail;
import org.openyu.mix.mail.vo.MailType;
import org.openyu.commons.bean.supporter.SeqIdAuditBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "mail")
@XmlAccessorType(XmlAccessType.FIELD)
public class MailImpl extends SeqIdAuditBeanSupporter implements Mail
{

	private static final long serialVersionUID = -6453443188491560873L;

	private MailType mailType;

	private String title;

	private String content;

	private String senderId;

	private String senderName;

	private long sendTime;

	private String receiverId;

	private String receiverName;

	private long gold;

	private List<Item> items;

	private boolean readed;

	private long readTime;

	private long expiredTime;

	public MailImpl(String id)
	{
		setId(id);
	}

	public MailImpl()
	{
		this(null);
	}

	public MailType getMailType()
	{
		return mailType;
	}

	public void setMailType(MailType mailType)
	{
		this.mailType = mailType;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getSenderId()
	{
		return senderId;
	}

	public void setSenderId(String senderId)
	{
		this.senderId = senderId;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public void setSenderName(String senderName)
	{
		this.senderName = senderName;
	}

	public long getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(long sendTime)
	{
		this.sendTime = sendTime;
	}

	public String getReceiverId()
	{
		return receiverId;
	}

	public void setReceiverId(String receiverId)
	{
		this.receiverId = receiverId;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public long getGold()
	{
		return gold;
	}

	public void setGold(long gold)
	{
		this.gold = gold;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void setItems(List<Item> items)
	{
		this.items = items;
	}

	public boolean isReaded()
	{
		return readed;
	}

	public void setReaded(boolean readed)
	{
		this.readed = readed;
	}

	public long getReadTime()
	{
		return readTime;
	}

	public void setReadTime(long readTime)
	{
		this.readTime = readTime;
	}

	public long getExpiredTime()
	{
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime)
	{
		this.expiredTime = expiredTime;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("mailType", mailType);
		builder.append("title", title);
		builder.append("content", content);
		builder.append("senderId", senderId);
		builder.append("senderName", senderName);
		builder.append("sendTime", sendTime);
		builder.append("gold", gold);
		builder.append("items", items);
		builder.append("readed", readed);
		builder.append("readTime", readTime);
		builder.append("expiredTime", expiredTime);
		return builder.toString();
	}

	public Object clone()
	{
		MailImpl copy = null;
		copy = (MailImpl) super.clone();
		copy.items = clone(items);
		return copy;
	}
}
