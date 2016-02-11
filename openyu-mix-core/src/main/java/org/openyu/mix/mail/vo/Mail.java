package org.openyu.mix.mail.vo;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.Item;
import org.openyu.commons.bean.SeqIdAuditBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 郵件
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Mail extends SeqIdAuditBean {

	String KEY = Mail.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "MAIL_";

	/**
	 * 郵件類別
	 * 
	 * @return
	 */
	MailType getMailType();

	void setMailType(MailType mailType);

	/**
	 * 標題
	 * 
	 * @return
	 */
	String getTitle();

	void setTitle(String title);

	/**
	 * 內容
	 * 
	 * @return
	 */
	String getContent();

	void setContent(String content);

	/**
	 * 寄件者id
	 * 
	 * @return
	 */
	String getSenderId();

	void setSenderId(String senderId);

	/**
	 * 寄件者名稱
	 * 
	 * @return
	 */
	String getSenderName();

	void setSenderName(String senderName);

	/**
	 * 寄件時間
	 * 
	 * @return
	 */
	long getSendTime();

	void setSendTime(long sendTime);

	/**
	 * 收件者id
	 * 
	 * @return
	 */
	String getReceiverId();

	void setReceiverId(String receiverId);

	/**
	 * 收件者名稱
	 * 
	 * @return
	 */
	String getReceiverName();

	void setReceiverName(String receiverName);

	/**
	 * 金幣
	 * 
	 * @return
	 */
	long getGold();

	void setGold(long gold);

	/**
	 * 道具
	 * 
	 * @return
	 */
	List<Item> getItems();

	void setItems(List<Item> items);

	/**
	 * 是否已讀取
	 * 
	 * @return
	 */
	boolean isReaded();

	void setReaded(boolean readed);

	/**
	 * 讀取時間
	 * 
	 * @return
	 */
	long getReadTime();

	void setReadTime(long readTime);

	/**
	 * 到期時間
	 * 
	 * @return
	 */
	long getExpiredTime();

	void setExpiredTime(long expiredTime);
}
