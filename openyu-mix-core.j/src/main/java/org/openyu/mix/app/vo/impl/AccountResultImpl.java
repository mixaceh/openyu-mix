package org.openyu.mix.app.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.vo.AccountResult;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.account.vo.Account;

@XmlRootElement(name = "accountResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountResultImpl extends AppResultSupporter implements AccountResult
{

	private static final long serialVersionUID = 354981373011331823L;

	/**
	 * 帳號
	 */
	private Account account;

	public AccountResultImpl()
	{

	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.appendSuper(super.toString());
		builder.append("account", (account != null ? account.getId() : null));
		return builder.toString();
	}

	public Object clone()
	{
		AccountResultImpl copy = null;
		copy = (AccountResultImpl) super.clone();
		copy.account = clone(account);
		return copy;
	}
}
