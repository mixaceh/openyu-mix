package org.openyu.mix.account.vo.adapter;

import javax.xml.bind.annotation.XmlElement;

import org.openyu.mix.account.vo.Account;
import org.openyu.mix.account.vo.impl.AccountImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<account>
//	<id>TEST_ACCOUNT</id>
//</account>
//--------------------------------------------------
public class AccountXmlAdapter extends
		BaseXmlAdapterSupporter<AccountXmlAdapter.AccountEntry, Account>
{

	public AccountXmlAdapter()
	{}

	// --------------------------------------------------
	public static class AccountEntry
	{
		@XmlElement
		public String id;

		public AccountEntry(String id)
		{
			this.id = id;
		}

		public AccountEntry()
		{}
	}

	// --------------------------------------------------
	public Account unmarshal(AccountEntry value) throws Exception
	{
		Account result = null;
		//
		if (value != null)
		{
			result = new AccountImpl();
			result.setId(value.id);
		}
		return result;
	}

	public AccountEntry marshal(Account value) throws Exception
	{
		AccountEntry result = null;
		//
		if (value != null)
		{
			result = new AccountEntry(value.getId());
		}
		return result;
	}
}
