package org.openyu.mix.chat.service;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.commons.enumz.IntEnum;

/**
 * 偵錯服務
 */
public interface DebugService extends AppService
{

	// --------------------------------------------------
	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum
	{
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(1),

		;

		private final int value;

		private ErrorType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("name", "(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 秘技
	 * 
	 * @param roleId
	 * @param text
	 */
	void cheat(String roleId, String text);

//	/**
//	 * 請求
//	 * 
//	 * @param roleId
//	 * @param text
//	 */
//	void request(String roleId, String text);

}
