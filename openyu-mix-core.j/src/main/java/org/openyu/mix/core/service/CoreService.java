package org.openyu.mix.core.service;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AccountResult;
import org.openyu.mix.app.vo.RoleResult;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.system.vo.Context;
import org.openyu.mix.system.vo.Relation;
import org.openyu.commons.enumz.IntEnum;

/**
 * 核心服務
 * 
 * 1.處理角色連線/斷線
 * 
 * 2.處理伺服器關連連線/斷線
 * 
 * 3.處理定時儲存角色
 */
public interface CoreService extends AppService {
	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum {
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
		ROLE_NOT_EXIST(11),

		/**
		 * 帳號不存在
		 */
		ACCOUNT_NOT_EXIST(12),

		/**
		 * 聊天角色不存在
		 */
		CHAT_NOT_EXIST(13),

		/**
		 * 本文不存在
		 */
		CONTEXT_NOT_EXIST(14),

		/**
		 * 伺服器關連不存在
		 */
		RELATION_NOT_EXIST(15),
		//
		;

		private final int value;

		private ErrorType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this,
					ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 檢查角色結果
	 */
	interface CheckRoleResult extends AccountResult, RoleResult {

		/**
		 * 錯誤類別
		 * 
		 * @return
		 */
		ErrorType getErrorType();

		void setErrorType(ErrorType errorType);
	}

	/**
	 * 檢查角色
	 * 
	 * @param roleId
	 * @return
	 */
	CheckRoleResult checkRole(String roleId);

	/**
	 * 檢查聊天
	 * 
	 * @param roleId
	 * @return
	 */
	Chat checkChat(String roleId);

	/**
	 * 排程每日00:00執行
	 */
	void day00_00();

	/**
	 * 排程每日00:03執行
	 */

	void day00_03();

	/**
	 * 排程每日00:07執行
	 */

	void day00_07();

	// --------------------------------------------------
	// 20140919
	// --------------------------------------------------
	/**
	 * 檢查本文結果
	 */
	interface CheckContextResult {

		/**
		 * 本文
		 * 
		 * @return
		 */
		Context getContext();

		void setContext(Context context);

		/**
		 * 錯誤類別
		 * 
		 * @return
		 */
		ErrorType getErrorType();

		void setErrorType(ErrorType errorType);
	}

	/**
	 * 檢查本文
	 * 
	 * @param relationId
	 * @return
	 */
	CheckContextResult checkContext(String contextId);

	/**
	 * 本文連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Context contextConnect(String contextId, T attatch);

	/**
	 * 本文斷線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Context contextDisconnect(String contextId, T attatch);

	// --------------------------------------------------
	// 20140829
	// --------------------------------------------------
	/**
	 * 檢查伺服器關連結果
	 */
	interface CheckRelationResult {

		/**
		 * 伺服器關連
		 * 
		 * @return
		 */
		Relation getRelation();

		void setRelation(Relation relation);

		/**
		 * 錯誤類別
		 * 
		 * @return
		 */
		ErrorType getErrorType();

		void setErrorType(ErrorType errorType);

	}

	/**
	 * 檢查伺服器關連
	 * 
	 * @param relationId
	 * @return
	 */
	CheckRelationResult checkRelation(String relationId);

	/**
	 * 伺服器關連連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationConnect(String relationId, T attatch);

	/**
	 * 伺服器關連斷線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationDisconnect(String relationId, T attatch);

	/**
	 * 伺服器關連無法連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationRefused(String relationId, T attatch);

}
