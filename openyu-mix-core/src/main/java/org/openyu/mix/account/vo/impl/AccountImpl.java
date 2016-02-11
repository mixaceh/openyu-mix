package org.openyu.mix.account.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.account.vo.Account;
import org.openyu.commons.bean.supporter.SeqIdAuditNamesBeanSupporter;
import org.openyu.commons.util.LocaleHelper;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountImpl extends SeqIdAuditNamesBeanSupporter implements Account
{
	private static final long serialVersionUID = 5077070875489720530L;

	/**
	 * 是否有效
	 */
	private boolean valid;

	/**
	 * 搜尋用名稱
	 */
	@XmlTransient
	private String searchName;

	/**
	 * 儲值總額
	 */
	private int coin;

	/**
	 * 累計儲值總額
	 */
	private int accuCoin;

	/**
	 * 密碼
	 */
	private String password;

	public AccountImpl()
	{}

	public String getName()
	{
		return getName(LocaleHelper.getLocale());
	}

	public void setName(String name)
	{
		setName(LocaleHelper.getLocale(), name);
	}

	public boolean getValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	public String getSearchName()
	{
		return searchName;
	}

	public void setSearchName(String searchName)
	{
		this.searchName = searchName;
	}

	public int getCoin()
	{
		return coin;
	}

	public void setCoin(int coin)
	{
		this.coin = coin;
	}

	public int getAccuCoin()
	{
		return accuCoin;
	}

	public void setAccuCoin(int accuCoin)
	{
		this.accuCoin = accuCoin;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("valid", valid);
		builder.append("searchName", searchName);
		builder.append("coin", coin);
		builder.append("accuCoin", accuCoin);
		builder.append("password", password);
		return builder.toString();
	}

	public Object clone()
	{
		AccountImpl copy = null;
		copy = (AccountImpl) super.clone();
		return copy;
	}

}
