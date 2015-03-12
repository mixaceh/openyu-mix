package org.openyu.mix.account.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.SeqIdAuditNamesBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 帳號
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Account extends SeqIdAuditNamesBean
{
	String KEY = Account.class.getName();

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	boolean getValid();

	void setValid(boolean valid);

	/**
	 * 搜尋用名稱
	 * 
	 * @return
	 */
	String getSearchName();

	void setSearchName(String searchName);

	/**
	 * 儲值總額
	 * 
	 * @return
	 */
	int getCoin();

	void setCoin(int coin);

	/**
	 * 累計儲值總額
	 * 
	 * @return
	 */
	int getAccuCoin();

	void setAccuCoin(int accuCoin);

	/**
	 * 密碼,md5加密
	 * 
	 * @return
	 */
	String getPassword();

	void setPassword(String password);

}
