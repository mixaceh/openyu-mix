package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.account.vo.Account;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 帳號結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AccountResult extends AppResult
{

	String KEY = AccountResult.class.getName();

	/**
	 * 帳號
	 * 
	 * @return
	 */
	Account getAccount();

	void setAccount(Account account);
}
