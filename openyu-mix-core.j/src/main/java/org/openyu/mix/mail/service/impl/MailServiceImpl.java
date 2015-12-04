package org.openyu.mix.mail.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.mail.service.MailService;
import org.openyu.mix.mail.vo.Mail;
import org.openyu.mix.mail.vo.MailCollector;
import org.openyu.mix.mail.vo.MailType;
import org.openyu.mix.mail.vo.impl.MailImpl;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.StringHelper;
import org.openyu.commons.util.CalendarHelper;
import org.openyu.socklet.message.vo.Message;

public class MailServiceImpl extends AppServiceSupporter implements MailService {

	private static final long serialVersionUID = -5637721590046550228L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	private transient MailCollector mailCollector = MailCollector.getInstance();

	public MailServiceImpl() {
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
	public Mail createMail(MailType mailType, String senderId, String senderName, String title, String content,
			String receiverId, String receiverName, long gold, List<Item> items) {
		Mail result = new MailImpl(Mail.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		result.setMailType(mailType);
		result.setTitle(title);
		result.setContent(content);
		result.setSenderId(senderId);
		result.setSenderName(senderName);
		result.setSendTime(System.currentTimeMillis());
		// 到期時間
		result.setExpiredTime(result.getSendTime() + mailCollector.getExpiredDay() * CalendarHelper.MILLIS_IN_DAY);
		//
		result.setReceiverId(receiverId);
		result.setReceiverName(receiverName);
		result.setGold(gold);
		result.setItems(items);
		//
		return result;
	}

	// --------------------------------------------------

	/**
	 * 郵件的結果
	 */
	public static class MailResultImpl extends AppResultSupporter implements MailResult {

		private static final long serialVersionUID = -8927307172367893124L;

		public MailResultImpl() {
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			return builder.toString();
		}

		public Object clone() {
			NewMailResultImpl copy = null;
			copy = (NewMailResultImpl) super.clone();
			return copy;
		}
	}

	/**
	 * 取得郵件
	 * 
	 * @param sendable
	 * @param role
	 * @param mailId
	 * @return
	 */
	public MailResult mail(boolean sendable, Role role, String mailId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 發送郵件回應
	 * 
	 * @param role
	 * @param mailId
	 * @return
	 */
	public Message sendMail(Role role, String mailId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 新郵件的結果
	 */
	public static class NewMailResultImpl extends AppResultSupporter implements NewMailResult {

		private static final long serialVersionUID = -8927307172367893124L;

		public NewMailResultImpl() {
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			return builder.toString();
		}

		public Object clone() {
			NewMailResultImpl copy = null;
			copy = (NewMailResultImpl) super.clone();
			return copy;
		}
	}

	/**
	 * 寄發新郵件
	 * 
	 * @param sendable
	 * @param role
	 * @param title
	 * @param content
	 * @param receiverName
	 * @param gold
	 * @return
	 */
	public NewMailResult newMail(boolean sendable, Role role, MailType mailType, String title, String content,
			String receiverName, long gold, String[]... itemUniqueIds) {

		NewMailResult result = null;
		// 檢查條件
		String senderName = role.getName();
		// 發給自己
		if (senderName.equals(receiverName)) {
			return null;
		}

		// TODO
		String receiverId = null;
		// TODO itemUniqueIds
		List<Item> items = new LinkedList<Item>();
		// TODO
		Mail mail = createMail(mailType, role.getId(), senderName, title, content, receiverId, receiverName, gold,
				items);

		// TODO NewMailResult

		return result;
	}

	/**
	 * 發送新郵件回應
	 * 
	 * @param errorType
	 * @param role
	 */
	public Message sendNewMail(ErrorType errorType, Role role) {
		Message message = messageService.createMessage(CoreModuleType.MAIL, CoreModuleType.CLIENT,
				CoreMessageType.MAIL_NEW_RESPONSE, role.getId());

		message.addInt(errorType);// 錯誤碼

		// 沒錯誤才發以下欄位
		if (ErrorType.NO_ERROR.equals(errorType)) {

		}

		return message;
	}

	/**
	 * 填充郵件
	 * 
	 * @param role
	 * @param mail
	 * @return
	 */
	public boolean fillMail(Role role, Mail mail) {
		// TODO Auto-generated method stub
		return false;
	}

}
