package org.openyu.mix.activity.vo.target.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.activity.vo.target.CoinTarget;
import org.openyu.mix.activity.vo.target.supporter.TargetSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "coinTarget")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoinTargetImpl extends TargetSupporter implements CoinTarget
{

	private static final long serialVersionUID = 9024786045945577050L;

	/**
	 * 儲值幣
	 */
	private int coin;

	public CoinTargetImpl(int id)
	{
		setId(id);
	}

	public CoinTargetImpl()
	{
		this(0);
	}

	public int getCoin()
	{
		return coin;
	}

	public void setCoin(int coin)
	{
		this.coin = coin;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("coin", coin);
		return builder.toString();
	}
}
