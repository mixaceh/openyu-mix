package org.openyu.mix.core.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Core extends BaseBean
{
	String KEY = Core.class.getName();

	// --------------------------------------------------
	// CoreConnectLog用
	// --------------------------------------------------
	/**
	 * 連線操作類別
	 */
	public enum ConnectAction implements IntEnum
	{
		/**
		 * 登入
		 */
		CONNECT(1),

		/**
		 * 登出
		 */
		DISCONNECT(2),

		/**
		 * 禁止連線
		 */
		BAN(3),

		;
		private final int intValue;

		private ConnectAction(int intValue)
		{
			this.intValue = intValue;
		}

		public int getValue()
		{
			return intValue;
		}
	}
}
