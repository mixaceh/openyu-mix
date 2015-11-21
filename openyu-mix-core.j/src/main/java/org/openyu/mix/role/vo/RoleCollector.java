package org.openyu.mix.role.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.nio.NioHelper;
import org.openyu.commons.util.SerializeProcessor;
import org.openyu.commons.util.CompressProcessor;
import org.openyu.commons.security.SecurityProcessor;
import org.openyu.commons.security.impl.SecurityProcessorImpl;
import org.openyu.commons.util.impl.CompressProcessorImpl;
import org.openyu.commons.util.impl.SerializeProcessorImpl;

/**
 * 角色數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "roleCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class RoleCollector extends BaseCollectorSupporter {
	// implements Serialable, Securityable, Compressable {

	private static final long serialVersionUID = 832639015982391363L;

	private static RoleCollector instance;

	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------

	// --------------------------------------------------
	// 企劃編輯用
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
	private String unsaveDir = "custom/role/unsave";

	/**
	 * 監聽毫秒
	 */
	private long listenMills = 3 * 60 * 1000L;// 3分鐘

	public RoleCollector() {
		setId(RoleCollector.class.getName());
	}

	public synchronized static RoleCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static RoleCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(RoleCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new RoleCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用

		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static RoleCollector shutdownInstance() {
		if (instance != null) {
			RoleCollector oldInstance = instance;
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
	public synchronized static RoleCollector restartInstance() {
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

	public SerializeProcessor getSerializeProcessor() {
		return serializeProcessor;
	}

	public SecurityProcessor getSecurityProcessor() {
		return securityProcessor;
	}

	public CompressProcessor getCompressProcessor() {
		return compressProcessor;
	}

	// --------------------------------------------------
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
