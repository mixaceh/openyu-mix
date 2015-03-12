package org.openyu.mix.chat.service;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 聊天服務
 */
public interface ChatService extends AppService {

	// --------------------------------------------------
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
		 * 儲存資料庫失敗
		 */
		STORE_DATABASE_FAIL(101),

		/**
		 * 重試儲存資料庫
		 */
		RETRYING_STORE_DATABASE(102),

		/**
		 * 儲存檔案失敗
		 */
		STORE_FILE_FAIL(103),

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

	// --------------------------------------------------
	// db
	// --------------------------------------------------
	/**
	 * 查詢聊天角色
	 * 
	 * @param chatId
	 * @return
	 */
	Chat findChat(String chatId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 建構聊天角色
	 * 
	 * @param roleId
	 * @return
	 */
	Chat createChat(String roleId);

	/**
	 * 聊天
	 * 
	 * @param role
	 * @param channelValue
	 * @param text
	 * @param html
	 * @return
	 * @see ChannelType=3-8
	 */
	void speak(Role role, int channelValue, String text, String html);

	/**
	 * 發送聊天
	 * 
	 * @param errorType
	 * @param receivers
	 * @param channelType
	 * @param role
	 * @param roleName
	 * @param text
	 * @param html
	 * @return
	 */
	Message sendSpeak(ErrorType errorType, List<String> receivers,
			ChannelType channelType, Role role, String roleName, String text,
			String html);
}
