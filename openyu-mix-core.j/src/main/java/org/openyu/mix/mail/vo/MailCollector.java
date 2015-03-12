package org.openyu.mix.mail.vo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.mail.vo.MailType;
import org.openyu.mix.mail.vo.adapter.MailTypeXmlAdapter;
import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;

/**
 * 郵件數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "mailCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class MailCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -366805549782373969L;

	private static MailCollector mailCollector;

	/**
	 * 郵件類別
	 */
	@XmlJavaTypeAdapter(MailTypeXmlAdapter.class)
	private Set<MailType> mailTypes = new LinkedHashSet<MailType>();

	/**
	 * 郵件未讀,到期天數
	 */
	private int expiredDay = 14;

	/**
	 * 寄郵件所花費的金幣,1000
	 */
	private long spendGold = 1000L;

	public MailCollector() {
		setId(MailCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static MailCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static MailCollector getInstance(boolean initial) {
		if (mailCollector == null) {
			mailCollector = new MailCollector();
			if (initial) {
				mailCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
			mailCollector.mailTypes = EnumHelper.valuesSet(MailType.class);

		}
		return mailCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!mailCollector.isInitialized()) {
			mailCollector = readFromSer(MailCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (mailCollector == null) {
				mailCollector = new MailCollector();
			}
			//
			mailCollector.setInitialized(true);
		}
	}

	public void clear() {
		// 設為可初始化
		setInitialized(false);
	}

	public int getExpiredDay() {
		return expiredDay;
	}

	public void setExpiredDay(int expiredDay) {
		this.expiredDay = expiredDay;
	}

	public long getSpendGold() {
		return spendGold;
	}

	public void setSpendGold(long spendGold) {
		this.spendGold = spendGold;
	}

}
