package org.openyu.mix.role.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.role.service.RoleService.ErrorType;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.core.vo.StoreType;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.service.StoreRoleService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleCollector;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.commons.io.FileHelper;
import org.openyu.commons.io.IoHelper;
import org.openyu.commons.nio.NioHelper;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.anno.DefaultThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.commons.thread.supporter.TriggerQueueSupporter;
import org.openyu.socklet.message.vo.Message;
import org.openyu.commons.util.SerializeProcessor;
import org.openyu.commons.util.AssertHelper;
import org.openyu.commons.util.CompressProcessor;
import org.openyu.commons.security.SecurityProcessor;

/**
 * 儲存角色服務
 * 
 * 1.儲存到db
 * 
 * 2.若失敗,則序列化到檔案, 目錄, file:custom/role/unsave
 */
public class StoreRoleServiceImpl extends AppServiceSupporter implements StoreRoleService {

	private static final long serialVersionUID = 3975742001750821702L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(StoreRoleServiceImpl.class);

	@DefaultThreadService
	private transient ThreadService threadService;

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	/**
	 * 角色數據
	 */
	private static RoleCollector roleCollector = RoleCollector.getInstance();

	private static SerializeProcessor serializeProcessor = roleCollector.getSerializeProcessor();

	private static SecurityProcessor securityProcessor = roleCollector.getSecurityProcessor();

	private static CompressProcessor compressProcessor = roleCollector.getCompressProcessor();

	/**
	 * 監聽執行者
	 */
	protected transient StoreRolesRunner storeRolesRunner;

	/**
	 * 序列化佇列
	 */
	protected transient SerializeQueue<SerializeRole> serializeQueue;

	public StoreRoleServiceImpl() {
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		//
		storeRolesRunner = new StoreRolesRunner(threadService);
		storeRolesRunner.start();
		//
		serializeQueue = new SerializeQueue<SerializeRole>(threadService);
		serializeQueue.start();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.shutdown();
		//
		storeRolesRunner.shutdown();
		serializeQueue.shutdown();
	}

	/**
	 * 監聽執行者
	 */
	protected class StoreRolesRunner extends BaseRunnableSupporter {

		public StoreRolesRunner(ThreadService threadService) {
			super(threadService);
		}

		@Override
		protected void doRun() throws Exception {
			while (true) {
				try {
					// 自動儲存就不發訊息給client了
					int count = storeRoles(false);
					if (count > 0) {
						LOGGER.info("Automatic save roles to DB, total count [" + count + "]");
					}
					ThreadHelper.sleep(roleCollector.getListenMills());
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 儲存所有角色
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @return
	 */
	public int storeRoles(boolean sendable) {
		int result = 0;
		//
		for (Role role : roleSetService.getRoles(false).values()) {
			try {
				// 儲存角色
				boolean stored = storeRole(sendable, role);
				if (stored) {
					result++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 儲存角色
	 * 
	 * 1.儲存到db, 失敗會重試
	 * 
	 * 2.若重試也失敗, 則序列化到檔案, 目錄, file:custom/role/unsave
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 * @return
	 */
	public boolean storeRole(boolean sendable, Role role) {
		boolean result = false;
		//
		int retryNumber = roleCollector.getRetryNumber();
		long retryPauseMills = roleCollector.getRetryPauseMills();
		//
		int tries = 0;
		int updated = 0;
		String roleId = role.getId();
		int version = role.getVersion();
		for (;;) {
			try {
				LOGGER.info("T[" + Thread.currentThread().getId() + "] Store the role [" + roleId + "]");
				// 使用roleService來存檔
				updated = roleService.update(role);
				// 存檔成功
				if (updated > 0) {
					result = true;
					//
					String serName = serName(roleId);
					boolean exist = FileHelper.isExist(serName);
					if (exist) {
						// 刪除ser, 2014/10/08
						FileHelper.delete(serName);
						LOGGER.info("Deleted [" + roleId + "] ser file [" + serName + "]");
					}
					// 發訊息
					if (sendable) {
						sendStoreRole(ErrorType.NO_ERROR, role, StoreType.DATABASE);
					}
					break;
				}
			} catch (Exception ex) {
				// 失敗重試
				++tries;
				// [1/3] time(s) Failed to save the role
				LOGGER.error("[" + tries + "/" + (retryNumber != 0 ? retryNumber : "N")
						+ "] time(s) Failed to save the role to DB", ex);
				// 發訊息
				if (sendable) {
					sendStoreRole(ErrorType.RETRYING_STORE_DATABASE, role, StoreType.DATABASE, tries);
				}

				// 0=無限
				if (retryNumber != 0 && tries >= retryNumber) {
					break;
				}
				// 重試暫停毫秒
				long pauseMills = NioHelper.retryPause(tries, retryPauseMills);
				ThreadHelper.sleep(pauseMills);
				// Retrying save the role [TEST_ROLE114805N4CEwYzbG]. Already
				// tried [3/3] time(s)
				LOGGER.info("Retrying save the role [" + roleId + "]. Already tried [" + (tries + 1) + "/"
						+ (retryNumber != 0 ? retryNumber : "N") + "] time(s)");
			}
		}
		// 重試失敗後,丟到queue,再把它序列化輸出
		if (!result) {
			// Trying serialize the role [TEST_ROLE114805N4CEwYzbG]
			LOGGER.info("Coz save the role to DB fail. Trying serialize the role [" + roleId + "]");
			// 因update會導致version+1, 所以再將其還原為原本的version
			role.setVersion(version);
			SerializeRole serializeRole = new SerializeRoleImpl(sendable, role);
			serializeQueue.offer(serializeRole);
			// 發訊息
			if (sendable) {
				sendStoreRole(ErrorType.STORE_DATABASE_FAIL, role, StoreType.DATABASE);
			}
		}
		return result;
	}

	/**
	 * 發送儲存角色
	 *
	 * @param errorType
	 * @param role
	 *            角色
	 * 
	 * @param storeType
	 * @return
	 */
	public Message sendStoreRole(ErrorType errorType, Role role, StoreType storeType) {
		return sendStoreRole(errorType, role, storeType, 0);
	}

	/**
	 * 發送儲存角色
	 *
	 * @param errorType
	 * @param role
	 *            角色
	 * 
	 * @param storeType
	 * @param tries
	 *            重試次數
	 * @return
	 */
	public Message sendStoreRole(ErrorType errorType, Role role, StoreType storeType, int tries) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_STORE_ROLE_RESPONSE, role.getId());

		message.addInt(errorType);// 0, int, errorType 錯誤碼
		message.addInt(storeType);// 1, int, 儲存類型, StoreType
		//
		if (ErrorType.RETRYING_STORE_DATABASE == errorType) {
			message.addInt(tries);// 2, int, 重試次數
		}
		//
		messageService.addMessage(message);
		return message;
	}

	/**
	 * 序列化角色
	 */
	public static class SerializeRoleImpl extends AppResultSupporter implements SerializeRole {

		private static final long serialVersionUID = 297156504951956534L;

		private boolean sendable;

		private Role role;

		public SerializeRoleImpl(boolean sendable, Role role) {
			this.sendable = sendable;
			this.role = role;
		}

		public boolean isSendable() {
			return sendable;
		}

		public void setSendable(boolean sendable) {
			this.sendable = sendable;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("sendable", sendable);
			builder.append("role", role.getId());
			return builder.toString();
		}

		public Object clone() {
			SerializeRoleImpl copy = null;
			copy = (SerializeRoleImpl) super.clone();
			copy.role = clone(role);
			return copy;
		}
	}

	/**
	 * 序列化佇列
	 */
	protected class SerializeQueue<E> extends TriggerQueueSupporter<SerializeRole> {

		public SerializeQueue(ThreadService threadService) {
			super(threadService);
		}

		@Override
		public void doExecute(SerializeRole e) throws Exception {
			serializeRole(e.isSendable(), e.getRole());
		}
	}

	/**
	 * 序列化角色
	 * 
	 * 序列化到檔案, 目錄, file:custom/role/unsave
	 * 
	 * custom/role/unsave/TEST_ROLE1074406tRBJjZLZt.ser
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 * @return 輸出檔名
	 */
	public String serializeRole(boolean sendable, Role role) {
		AssertHelper.notNull(role, "The Role must not be null");

		String result = null;
		//
		String roleId = role.getId();
		if (!serializeProcessor.isSerialize()) {
			LOGGER.warn("No serialized the role [" + roleId + "]");
			return result;
		}
		//
		OutputStream out = null;
		try {
			// custom/role/unsave/TEST_ROLE1074406tRBJjZLZt.ser
			// 會判斷目錄是否存在, 若無則新建目錄
			FileHelper.md(roleCollector.getUnsaveDir());
			String serName = serName(roleId);
			out = IoHelper.createOutputStream(serName);

			// 1.序列化
			byte[] buff = serializeProcessor.serialize(role);

			// 2.加密
			buff = securityProcessor.encrypt(buff);

			// 3.壓縮
			buff = compressProcessor.compress(buff);
			//
			boolean writed = IoHelper.write(out, buff);
			//
			if (writed) {
				result = serName;
				LOGGER.info("Serialized the role [" + roleId + "] to " + serName);
				// 發訊息
				if (sendable) {
					sendStoreRole(ErrorType.NO_ERROR, role, StoreType.FILE);
				}
			} else {
				LOGGER.error("Serialized the role [" + roleId + "] fail");
				// 發訊息
				if (sendable) {
					sendStoreRole(ErrorType.STORE_FILE_FAIL, role, StoreType.FILE);
				}
			}
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during serializeRole()").toString(), e);
		} finally {
			IoHelper.close(out);
		}
		//
		return result;
	}

	/**
	 * 反序列化角色, 目錄, file:custom/role/unsave
	 * 
	 * custom/role/unsave/TEST_ROLE1074406tRBJjZLZt.ser
	 * 
	 * @param roleId
	 *            TEST_ROLE1074406tRBJjZLZt
	 * @return
	 */
	public Role deserializeRole(String roleId) {
		AssertHelper.notNull(roleId, "The RoleId must not be null");

		Role result = null;
		//
		if (!serializeProcessor.isSerialize()) {
			LOGGER.warn("No deserialized the role [" + roleId + "]");
			return result;
		}
		//
		InputStream in = null;
		try {
			String serName = serName(roleId);
			// 判斷檔案是否存在
			boolean exist = FileHelper.isExist(serName);
			if (!exist) {
				LOGGER.error(serName + " Does not exist");
				return result;
			}
			//
			in = IoHelper.createInputStream(serName);
			byte[] buff = IoHelper.read(in);

			// --------------------------------------------------
			// 搭配serializeRole的演算法,反解回來
			// --------------------------------------------------
			// 3.解壓
			buff = compressProcessor.uncompress(buff);

			// 2.解密
			buff = securityProcessor.decrypt(buff);

			// 1.反序列化
			result = serializeProcessor.deserialize(buff, RoleImpl.class);
			//
			if (result != null) {
				LOGGER.info("Deserialized the role [" + roleId + "] from " + serName);
			} else {
				LOGGER.error("Deserialized the role [" + roleId + "] fail");
			}
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during deserializeRole()").toString(), e);
		} finally {
			IoHelper.close(in);
		}
		return result;
	}

	/**
	 * ser檔名
	 * 
	 * @param roleId
	 * @return
	 */
	public String serName(String roleId) {
		StringBuilder result = new StringBuilder();
		result.append(roleCollector.getUnsaveDir());
		result.append(File.separator);
		result.append(roleId);
		result.append(".ser");
		return result.toString();
	}

}
