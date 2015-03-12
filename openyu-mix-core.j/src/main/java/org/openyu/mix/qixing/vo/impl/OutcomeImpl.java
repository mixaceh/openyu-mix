package org.openyu.mix.qixing.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.qixing.vo.Outcome;
import org.openyu.commons.bean.supporter.IdBeanSupporter;

/**
 * 開出的結果
 */
//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "outcome")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutcomeImpl extends IdBeanSupporter implements Outcome
{

	private static final long serialVersionUID = 2997772170133056093L;

	/**
	 * banker id
	 */
	private String bankerId;

	/**
	 * player id
	 */
	private String playerId;


	/**
	 * 開出的獎
	 */
	private Prize prize;

	public OutcomeImpl(String id, String bankerId, String playerId)
	{
		setId(id);
		this.bankerId = bankerId;
		this.playerId = playerId;
	}

	public OutcomeImpl()
	{
		this(null, null, null);
	}

	public String getBankerId()
	{
		return bankerId;
	}

	public void setBankerId(String bankerId)
	{
		this.bankerId = bankerId;
	}

	public String getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(String playerId)
	{
		this.playerId = playerId;
	}

	public Prize getPrize()
	{
		return prize;
	}

	public void setPrize(Prize prize)
	{
		this.prize = prize;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("bankerId", bankerId);
		builder.append("playerId", playerId);
		builder.append("prize", prize);
		return builder.toString();
	}

	public Object clone()
	{
		OutcomeImpl copy = null;
		copy = (OutcomeImpl) super.clone();
		copy.prize = clone(prize);
		return copy;
	}

}
