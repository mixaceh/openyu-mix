package org.openyu.mix.chat.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openyu.commons.bean.supporter.BaseCollectorSupporter;
import org.openyu.commons.nio.NioHelper;
import org.openyu.commons.security.SecurityProcessor;
import org.openyu.commons.security.impl.SecurityProcessorImpl;
import org.openyu.commons.util.CompressProcessor;
import org.openyu.commons.util.SerializeProcessor;
import org.openyu.commons.util.impl.CompressProcessorImpl;
import org.openyu.commons.util.impl.SerializeProcessorImpl;

/**
 * 聊天角色數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "chatCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ChatCollector extends BaseCollectorSupporter {
	// implements SerializeProcessor, SecurityProcessor, CompressProcessor {

	private static final long serialVersionUID = 3530463458196103043L;

	private static ChatCollector chatCollector;

	// --------------------------------------------------
	// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/** 序列化 */
	@XmlElement(type = SerializeProcessorImpl.class)
	private SerializeProcessor serializeProcessor = new SerializeProcessorImpl();

	/** 安全性 */
	@XmlElement(type = SecurityProcessorImpl.class)
	private SecurityProcessor securityProcessor = new SecurityProcessorImpl();

	/** 壓縮 */
	@XmlElement(type = CompressProcessorImpl.class)
	private CompressProcessor compressProcessor = new CompressProcessorImpl();

	/**
	 * 重試次數, 0=無限
	 */
	private int retryNumber = NioHelper.DEFAULT_RETRY_NUMBER;

	/**
	 * 重試暫停毫秒
	 */
	private long retryPauseMills = NioHelper.DEFAULT_RETRY_PAUSE_MILLS;

	/**
	 * 尚未儲存角色的序列化檔案目錄
	 */
	private String unsaveDir = "custom/chat/unsave";

	/**
	 * 監聽毫秒
	 */
	private long listenMills = 3 * 60 * 1000L + 10000L;// 3分鐘又10秒

	public ChatCollector() {
		setId(ChatCollector.class.getName());
	}

	// --------------------------------------------------
	public synchronized static ChatCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static ChatCollector getInstance(boolean initial) {
		if (chatCollector == null) {
			chatCollector = new ChatCollector();
			if (initial) {
				chatCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用

		}
		return chatCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!chatCollector.isInitialized()) {
			chatCollector = readFromSer(ChatCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (chatCollector == null) {
				chatCollector = new ChatCollector();
			}
			//
			chatCollector.setInitialized(true);
		}
	}

	public void clear() {
		// 設為可初始化
		setInitialized(false);
	}

	public SerializeProcessor getSerializeProcessor() {
		return serializeProcessor;
	}

	public SecurityProcessor getSecurityProcessor() {
		return securityProcessor;
	}

	public CompressProcessor getCompressProcessor() {
		return compressProcessor;
	}

	public int getRetryNumber() {
		return retryNumber;
	}

	public void setRetryNumber(int retryNumber) {
		this.retryNumber = retryNumber;
	}

	public long getRetryPauseMills() {
		return retryPauseMills;
	}

	public void setRetryPauseMills(long retryPauseMills) {
		this.retryPauseMills = retryPauseMills;
	}

	public String getUnsaveDir() {
		return unsaveDir;
	}

	public void setUnsaveDir(String unsaveDir) {
		this.unsaveDir = unsaveDir;
	}

	public long getListenMills() {
		return listenMills;
	}

	public void setListenMills(long listenMills) {
		this.listenMills = listenMills;
	}

}
