package org.openyu.mix.role.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

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

	private static RoleCollector roleCollector;

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

	public synchronized static RoleCollector getInstance(boolean initial) {
		if (roleCollector == null) {
			roleCollector = new RoleCollector();
			if (initial) {
				roleCollector.initialize();
			}

			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
		}
		return roleCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!roleCollector.isInitialized()) {
			roleCollector = readFromSer(RoleCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (roleCollector == null) {
				roleCollector = new RoleCollector();
			}
			//
			roleCollector.setInitialized(true);
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

	// --------------------------------------------------

	// /**
	// * 是否序列化
	// *
	// * @return
	// */
	// public boolean isSerialize() {
	// return serialable.isSerialize();
	// }
	//
	// public void setSerialize(boolean serialize) {
	// serialable.setSerialize(serialize);
	// }
	//
	// /**
	// * 序列化類別
	// *
	// * @return
	// */
	// public SerializeType getSerializeType() {
	// return serialable.getSerializeType();
	// }
	//
	// public void setSerializeType(SerializeType serializeType) {
	// serialable.setSerializeType(serializeType);
	// }
	//
	// /**
	// * 序列化類別列舉
	// *
	// * @return
	// */
	// public Set<SerializeType> getSerializeTypes() {
	// return serialable.getSerializeTypes();
	// }
	//
	// public void setSerializeTypes(Set<SerializeType> serializeTypes) {
	// serialable.setSerializeTypes(serializeTypes);
	// }
	//
	// /**
	// * 序列化
	// *
	// * @param serializeTypeValue
	// * 序列化類別
	// * @see SerializeType
	// * @param value
	// * @return
	// */
	// public byte[] serialize(String serializeTypeValue, Serializable value) {
	// return serialable.serialize(serializeTypeValue, value);
	// }
	//
	// /**
	// * 序列化
	// *
	// * @param serializeTypeValue
	// * 序列化類別
	// * @see SerializeType
	// * @param value
	// * @return
	// */
	// public byte[] serialize(int serializeTypeValue, Serializable value) {
	// return serialable.serialize(serializeTypeValue, value);
	// }
	//
	// /**
	// * 序列化
	// *
	// * @param serializeType
	// * 序列化類別
	// * @see SerializeType
	// * @param value
	// * @return
	// */
	// public byte[] serialize(SerializeType serializeType, Serializable value)
	// {
	// return serialable.serialize(serializeType, value);
	// }
	//
	// /**
	// * 反序列化執行
	// *
	// * @param serializeTypeValue
	// * 序列化類別
	// * @see SerializeType
	// * @param values
	// * @return
	// */
	// public <T> T deserialize(String serializeTypeValue, byte[] values) {
	// return serialable.deserialize(serializeTypeValue, values);
	// }
	//
	// /**
	// * 反序列化執行
	// *
	// * @param serializeTypeValue
	// * 序列化類別
	// * @see SerializeType
	// * @param values
	// * @return
	// */
	// public <T> T deserialize(int serializeTypeValue, byte[] values) {
	// return serialable.deserialize(serializeTypeValue, values);
	// }
	//
	// /**
	// * 反序列化執行
	// *
	// * @param serializeType
	// * 序列化類別
	// * @see SerializeType
	// * @param values
	// * @return
	// */
	// public <T> T deserialize(SerializeType serializeType, byte[] values) {
	// return serialable.deserialize(serializeType, values);
	// }
	//
	// /**
	// * 是否加密
	// *
	// * @return
	// */
	// public boolean isSecurity() {
	// return securityable.isSecurity();
	// }
	//
	// public void setSecurity(boolean security) {
	// securityable.setSecurity(security);
	// }
	//
	// /**
	// * 加密類別
	// *
	// * @return
	// */
	// public SecurityType getSecurityType() {
	// return securityable.getSecurityType();
	// }
	//
	// public void setSecurityType(SecurityType securityType) {
	// securityable.setSecurityType(securityType);
	// }
	//
	// /**
	// * 加密key
	// *
	// * @return
	// */
	// public String getSecurityKey() {
	// return securityable.getSecurityKey();
	// }
	//
	// public void setSecurityKey(String securityKey) {
	// securityable.setSecurityKey(securityKey);
	// }
	//
	// /**
	// * 安全性類別列舉
	// *
	// * @return
	// */
	// public Set<SecurityType> getSecurityTypes() {
	// return securityable.getSecurityTypes();
	// }
	//
	// public void setSecurityTypes(Set<SecurityType> securityTypes) {
	// securityable.setSecurityTypes(securityTypes);
	// }
	//
	// /**
	// * 加密
	// *
	// * @param securityTypeValue
	// * 安全性類別
	// * @see SecurityType
	// * @param values
	// * @param assignKey
	// * @return
	// */
	// public byte[] encrypt(String securityTypeValue, byte[] values,
	// String assignKey) {
	// return securityable.encrypt(securityTypeValue, values, assignKey);
	// }
	//
	// /**
	// * 加密
	// *
	// * @param securityType
	// * 安全性類別
	// * @see SecurityType
	// * @param values
	// * @param assignKey
	// * @return
	// */
	// public byte[] encrypt(SecurityType securityType, byte[] values,
	// String assignKey) {
	// return securityable.encrypt(securityType, values, assignKey);
	// }
	//
	// /**
	// * 解密
	// *
	// * @param securityTypeValue
	// * 安全性類別
	// * @see SecurityType
	// * @param values
	// * @param assignKey
	// * @return
	// */
	// public byte[] decrypt(String securityTypeValue, byte[] values,
	// String assignKey) {
	// return securityable.decrypt(securityTypeValue, values, assignKey);
	// }
	//
	// /**
	// * 解密
	// *
	// * @param securityType
	// * 安全性類別
	// * @see SecurityType
	// * @param values
	// * @param assignKey
	// * @return
	// */
	// public byte[] decrypt(SecurityType securityType, byte[] values,
	// String assignKey) {
	// return securityable.decrypt(securityType, values, assignKey);
	// }
	//
	// /**
	// * 是否壓縮
	// *
	// * @return
	// */
	// public boolean isCompress() {
	// return compressable.isCompress();
	// }
	//
	// public void setCompress(boolean compress) {
	// compressable.setCompress(compress);
	// }
	//
	// /**
	// * 壓縮類別
	// *
	// * @return
	// */
	// public CompressType getCompressType() {
	// return compressable.getCompressType();
	// }
	//
	// public void setCompressType(CompressType compressType) {
	// compressable.setCompressType(compressType);
	// }
	//
	// /**
	// * 壓縮類別列舉
	// *
	// * @return
	// */
	// public Set<CompressType> getCompressTypes() {
	// return compressable.getCompressTypes();
	// }
	//
	// public void setCompressTypes(Set<CompressType> compressTypes) {
	// compressable.setCompressTypes(compressTypes);
	// }
	//
	// /**
	// * 壓縮
	// *
	// * @param compressTypeValue
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] compress(String compressTypeValue, byte[] values) {
	// return compressable.compress(compressTypeValue, values);
	// }
	//
	// /**
	// * 壓縮
	// *
	// * @param compressTypeValue
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] compress(int compressTypeValue, byte[] values) {
	// return compressable.compress(compressTypeValue, values);
	// }
	//
	// /**
	// * 壓縮
	// *
	// * @param compressType
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] compress(CompressType compressType, byte[] values) {
	// return compressable.compress(compressType, values);
	// }
	//
	// /**
	// * 解壓
	// *
	// * @param compressTypeValue
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] uncompress(String compressTypeValue, byte[] values) {
	// return compressable.uncompress(compressTypeValue, values);
	// }
	//
	// /**
	// * 解壓
	// *
	// * @param compressTypeValue
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] uncompress(int compressTypeValue, byte[] values) {
	// return compressable.uncompress(compressTypeValue, values);
	// }
	//
	// /**
	// * 解壓
	// *
	// * @param compressTypeValue
	// * 壓縮類別
	// * @see CompressType
	// * @param values
	// * @return
	// */
	// public byte[] uncompress(CompressType compressType, byte[] values) {
	// return compressable.uncompress(compressType, values);
	// }

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
