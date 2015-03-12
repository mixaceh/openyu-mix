package org.openyu.mix.mail.service;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.mail.vo.Mail;
import org.openyu.mix.mail.vo.MailType;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 郵件服務
 */
public interface MailService extends AppService {

	/**
	 * 錯誤類別
	 */
	enum ErrorType implements IntEnum {
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
		 * 道具不存在
		 */
		ITEM_NOT_EXIST(13),

		/**
		 * 道具不合法
		 */
		ITEM_NOT_VALID(14),

		/**
		 * 數量不合法
		 */
		AMOUNT_NOT_VALID(15),

		/**
		 * 數量不足
		 */
		AMOUNT_NOT_ENOUGH(16),

		/**
		 * 等級不足
		 */
		LEVLE_NOT_ENOUGH(51),

		/**
		 * 金幣不足
		 */
		GOLD_NOT_ENOUGH(52),

		/**
		 * 儲值幣不足
		 */
		COIN_NOT_ENOUGH(53),

		/**
		 * vip不足
		 */
		VIP_NOT_ENOUGH(54),
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
			builder.append("name", "(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 建構郵件
	 * 
	 * @param mailType
	 * @param senderId
	 * @param senderName
	 * @param title
	 * @param content
	 * @param receiverId
	 * @param receiverName
	 * @param gold
	 * @param items
	 * @return
	 */
	Mail createMail(MailType mailType, String senderId, String senderName,
			String title, String content, String receiverId,
			String receiverName, long gold, List<Item> items);

	/**
	 * 郵件的結果
	 */
	interface MailResult extends AppResult {

	}

	/**
	 * 取得郵件
	 * 
	 * @param sendable
	 * @param role
	 * @param mailId
	 * @return
	 */
	MailResult mail(boolean sendable, Role role, String mailId);

	/**
	 * 發送郵件回應
	 * 
	 * @param role
	 * @param mailId
	 * @return
	 */
	Message sendMail(Role role, String mailId);

	/**
	 * 新郵件的結果
	 */
	interface NewMailResult extends AppResult {

	}

	/**
	 * 寄發新郵件
	 * 
	 * @param sendable
	 * @param role
	 * @param mailType
	 * @param title
	 * @param content
	 * @param receiverName
	 * @param gold
	 * @param itemUniqueIds
	 * @return
	 */
	NewMailResult newMail(boolean sendable, Role role, MailType mailType,
			String title, String content, String receiverName, long gold,
			String[]... itemUniqueIds);

	/**
	 * 發送新郵件回應
	 * 
	 * @param errorType
	 * @param role
	 * @return
	 */
	Message sendNewMail(ErrorType errorType, Role role);

	/**
	 * 填充郵件
	 * 
	 * @param role
	 * @param mail
	 * @return
	 */
	boolean fillMail(Role role, Mail mail);
}
