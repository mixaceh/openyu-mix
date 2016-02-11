package org.openyu.mix.mail.vo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.mail.vo.MailType;
import org.openyu.mix.mail.vo.adapter.MailTypeXmlAdapter;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
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

	private static MailCollector instance;

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

	public synchronized static MailCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(MailCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new MailCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.mailTypes = EnumHelper.valuesSet(MailType.class);

		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static MailCollector shutdownInstance() {
		if (instance != null) {
			MailCollector oldInstance = instance;
			instance = null;
			//
			if (oldInstance != null) {
				oldInstance.shutdown();
			}
		}
		return instance;
	}

	/**
	 * 單例重啟
	 * 
	 * @return
	 */
	public synchronized static MailCollector restartInstance() {
		if (instance != null) {
			instance.restart();
		}
		return instance;
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
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
