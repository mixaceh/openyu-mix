package org.openyu.mix.chat.service.impl;

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
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.chat.service.ChatSetService;
import org.openyu.mix.chat.service.StoreChatService;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.ChatCollector;
import org.openyu.mix.chat.vo.impl.ChatImpl;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.core.vo.StoreType;
import org.openyu.mix.chat.service.ChatService.ErrorType;
import org.openyu.commons.io.FileHelper;
import org.openyu.commons.io.IoHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.commons.nio.NioHelper;
import org.openyu.commons.security.SecurityProcessor;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.commons.thread.supporter.TriggerQueueSupporter;
import org.openyu.commons.util.CompressProcessor;
import org.openyu.commons.util.SerializeProcessor;
import org.openyu.socklet.message.vo.Message;

/**
 * 儲存聊天角色服務
 * 
 * 1.儲存到db
 * 
 * 2.若失敗,則序列化到檔案, 目錄, file:custom/chat/unsave
 */
public class StoreChatServiceImpl extends AppServiceSupporter implements StoreChatService {

	private static final long serialVersionUID = -5119832212938784272L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(StoreChatServiceImpl.class);

	@Autowired
	@Qualifier("chatSetService")
	protected transient ChatSetService chatSetService;

	@Autowired
	@Qualifier("chatService")
	protected transient ChatService chatService;

	/**
	 * 聊天角色數據
	 */
	private static ChatCollector chatCollector = ChatCollector.getInstance();

	private static SerializeProcessor serializeProcessor = chatCollector.getSerializeProcessor();

	private static SecurityProcessor securityProcessor = chatCollector.getSecurityProcessor();

	private static CompressProcessor compressProcessor = chatCollector.getCompressProcessor();

	/**
	 * 監聽儲存chat
	 */
	private transient StoreChatsRunner storeChatsRunner;
	/**
	 * 序列化佇列
	 */
	protected transient SerializeQueue<SerializeChat> serializeQueue;

	public StoreChatServiceImpl() {
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		// 監聽儲存chat
		this.storeChatsRunner = new StoreChatsRunner(threadService);
		this.storeChatsRunner.start();
		// 序列化佇列
		this.serializeQueue = new SerializeQueue<SerializeChat>(threadService);
		this.serializeQueue.start();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.storeChatsRunner.shutdown();
		this.serializeQueue.shutdown();
	}

	/**
	 * 監聽執行者
	 */
	protected class StoreChatsRunner extends BaseRunnableSupporter {

		public StoreChatsRunner(ThreadService threadService) {
			super(threadService);
		}

		@Override
		protected void doRun() throws Exception {
			while (true) {
				try {
					// 自動儲存就不發訊息給client了
					storeChats(false);
					ThreadHelper.sleep(chatCollector.getListenMills());
					// ThreadHelper.sleep(10 * 1000);
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 儲存所有聊天角色
	 * 
	 * @return
	 */
	public int storeChats(boolean sendable) {
		int result = 0;
		//
		for (Chat chat : chatSetService.getChats(false).values()) {
			try {
				// 儲存聊天角色
				boolean stored = storeChat(sendable, chat);
				if (stored) {
					result++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		//
		if (result > 0) {
			LOGGER.info("Every " + chatCollector.getListenMills() + " mills., "
					+ "automatic save chats to DB, total count [" + result + "]");

		}
		return result;
	}

	/**
	 * 儲存聊天角色
	 * 
	 * 1.儲存到db, 失敗會重試
	 * 
	 * 2.若重試也失敗, 則序列化到檔案, 目錄, file:custom/chat/unsave
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param chat
	 * @return
	 */
	public boolean storeChat(boolean sendable, Chat chat) {
		boolean result = false;
		//
		int retryNumber = chatCollector.getRetryNumber();
		long retryPauseMills = chatCollector.getRetryPauseMills();
		//
		int tries = 0;
		int updated = 0;
		String chatId = chat.getId();
		int version = chat.getVersion();
		for (;;) {
			try {
				LOGGER.info("T[" + Thread.currentThread().getId() + "] Save the chat [" + chat.getId() + "]");
				// 使用chatService來存檔
				updated = chatService.update(chat);
				// 存檔成功
				if (updated > 0) {
					result = true;
					//
					String serName = serName(chatId);
					boolean exist = FileHelper.isExist(serName);
					if (exist) {
						// 刪除ser, 2014/10/08
						FileHelper.delete(serName);
						LOGGER.info("Deleted [" + chatId + "] ser file [" + serName + "]");
					}
					// 發訊息
					if (sendable) {
						sendStoreChat(ErrorType.NO_ERROR, chat, StoreType.DATABASE);
					}
					break;
				}
			} catch (Exception ex) {
				// 失敗重試
				++tries;
				// [1/3] time(s) Failed to save the chat
				LOGGER.error("[" + tries + "/" + (retryNumber != 0 ? retryNumber : "N")
						+ "] time(s) Failed to save the chat to DB", ex);
				// 發訊息
				if (sendable) {
					sendStoreChat(ErrorType.RETRYING_STORE_DATABASE, chat, StoreType.DATABASE, tries);
				}

				// 0=無限
				if (retryNumber != 0 && tries >= retryNumber) {
					break;
				}
				// 重試暫停毫秒
				long pauseMills = NioHelper.retryPause(tries, retryPauseMills);
				ThreadHelper.sleep(pauseMills);
				// Retrying save the chat [TEST_ROLE114805N4CEwYzbG]. Already
				// tried [3/3] time(s)
				LOGGER.info("Retrying save the chat [" + chatId + "]. Already tried [" + (tries + 1) + "/"
						+ (retryNumber != 0 ? retryNumber : "N") + "] time(s)");
			}
		}
		// 重試失敗後,丟到queue,再把它序列化輸出
		if (!result) {
			// Trying serialize the chat [TEST_ROLE114805N4CEwYzbG]
			LOGGER.info("Coz save the chat to DB fail. Trying serialize the chat [" + chatId + "]");
			// 因update會導致version+1, 所以再將其還原為原本的version
			chat.setVersion(version);
			SerializeChat serializeChat = new SerializeChatImpl(sendable, chat);
			serializeQueue.offer(serializeChat);
			// 發訊息
			if (sendable) {
				sendStoreChat(ErrorType.STORE_DATABASE_FAIL, chat, StoreType.DATABASE);
			}
		}
		return result;
	}

	/**
	 * 發送儲存聊天角色
	 *
	 * @param errorType
	 * @param chat
	 * @param storeType
	 * @return
	 */
	public Message sendStoreChat(ErrorType errorType, Chat chat, StoreType storeType) {
		return sendStoreChat(errorType, chat, storeType, 0);
	}

	/**
	 * 發送儲存聊天角色
	 *
	 * @param errorType
	 * @param chat
	 * @param storeType
	 * @param tries
	 *            重試次數
	 * @return
	 */
	public Message sendStoreChat(ErrorType errorType, Chat chat, StoreType storeType, int tries) {
		Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.CLIENT,
				CoreMessageType.CHAT_STORE_CHAT_RESPONSE, chat.getId());

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
	 * 序列化聊天角色
	 */
	public static class SerializeChatImpl extends AppResultSupporter implements SerializeChat {

		private static final long serialVersionUID = 7332039566995783929L;

		private boolean sendable;

		private Chat chat;

		public SerializeChatImpl(boolean sendable, Chat chat) {
			this.sendable = sendable;
			this.chat = chat;
		}

		public boolean isSendable() {
			return sendable;
		}

		public void setSendable(boolean sendable) {
			this.sendable = sendable;
		}

		public Chat getChat() {
			return chat;
		}

		public void setChat(Chat chat) {
			this.chat = chat;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("sendable", sendable);
			builder.append("chat", chat.getId());
			return builder.toString();
		}

		public Object clone() {
			SerializeChatImpl copy = null;
			copy = (SerializeChatImpl) super.clone();
			copy.chat = clone(chat);
			return copy;
		}
	}

	/**
	 * 序列化佇列
	 */
	protected class SerializeQueue<E> extends TriggerQueueSupporter<SerializeChat> {

		public SerializeQueue(ThreadService threadService) {
		}

		@Override
		public void doExecute(SerializeChat e) {
			serializeChat(e.isSendable(), e.getChat());
		}
	}

	/**
	 * 序列化聊天角色
	 * 
	 * 序列化到檔案, 目錄, file:custom/chat/unsave
	 * 
	 * custom/chat/unsave/TEST_ROLE4573801Upgckwh9n.ser
	 * 
	 * @param chat
	 * @return 輸出檔名
	 */
	public String serializeChat(boolean sendable, Chat chat) {
		String result = null;
		OutputStream out = null;
		try {
			// custom/chat/unsave/TEST_ROLE4573801Upgckwh9n.ser
			// 會判斷目錄是否存在, 若無則新建目錄
			FileHelper.md(chatCollector.getUnsaveDir());
			String serName = serName(chat.getId());
			out = IoHelper.createOutputStream(serName);

			// 1.序列化
			byte[] buff = serializeProcessor.serialize(chat);

			// 2.加密
			buff = securityProcessor.encrypt(buff);

			// 3.壓縮
			buff = compressProcessor.compress(buff);
			//
			boolean writed = IoHelper.write(out, buff);
			//
			if (writed) {
				result = serName;
				LOGGER.info("Serialized the chat [" + chat.getId() + "] to " + serName);
				// 發訊息
				if (sendable) {
					sendStoreChat(ErrorType.NO_ERROR, chat, StoreType.FILE);
				}
			} else {
				LOGGER.error("Serialized the chat [" + chat.getId() + "] fail");
				// 發訊息
				if (sendable) {
					sendStoreChat(ErrorType.STORE_FILE_FAIL, chat, StoreType.FILE);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IoHelper.close(out);
		}
		//
		return result;
	}

	/**
	 * 反序列化聊天角色, 目錄, file:custom/chat/unsave
	 * 
	 * custom/chat/unsave/TEST_ROLE4573801Upgckwh9n.ser
	 * 
	 * @param roleId
	 *            TEST_ROLE4573801Upgckwh9n
	 * @return
	 */
	public Chat deserializeChat(String chatId) {
		Chat result = null;
		InputStream in = null;
		try {
			if (StringHelper.notBlank(chatId)) {
				String serName = serName(chatId);
				// 判斷檔案是否存在
				boolean exist = FileHelper.isExist(serName);
				if (!exist) {
					LOGGER.error(serName + " Does not exist");
				}
				//
				in = IoHelper.createInputStream(serName);
				byte[] buff = IoHelper.read(in);

				// --------------------------------------------------
				// 搭配serializeChat的演算法,反解回來
				// --------------------------------------------------
				// 3.解壓
				buff = compressProcessor.uncompress(buff);

				// 2.解密
				buff = securityProcessor.decrypt(buff);

				// 1.反序列化
				result = serializeProcessor.deserialize(buff, ChatImpl.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IoHelper.close(in);
		}
		return result;
	}

	/**
	 * ser檔名
	 * 
	 * @param chatId
	 * @return
	 */
	public String serName(String chatId) {
		StringBuilder result = new StringBuilder();
		result.append(chatCollector.getUnsaveDir());
		result.append(File.separator);
		result.append(chatId);
		result.append(".ser");
		return result.toString();
	}

}