package org.openyu.mix.wuxing.vo.impl;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.wuxing.vo.Outcome;
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
	 * 
	 */
	private List<Prize> prizes = new LinkedList<Prize>();

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

	public List<Prize> getPrizes()
	{
		return prizes;
	}

	public void setPrizes(List<Prize> prizes)
	{
		this.prizes = prizes;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("bankerId", bankerId);
		builder.append("playerId", playerId);
		builder.append("prizes", prizes);
		return builder.toString();
	}

	public Object clone()
	{
		OutcomeImpl copy = null;
		copy = (OutcomeImpl) super.clone();
		copy.prizes = clone(prizes);
		return copy;
	}

}
