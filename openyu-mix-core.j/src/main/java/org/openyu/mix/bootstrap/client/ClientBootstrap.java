package org.openyu.mix.bootstrap.client;

import org.apache.commons.configuration.XMLConfiguration;
import org.openyu.commons.lang.ArrayHelper;
import org.openyu.commons.mark.Supporter;
import org.openyu.mix.client.service.ConsoleService;
import org.openyu.mix.client.service.impl.ConsoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * client啟動器
 */
public final class ClientBootstrap implements Supporter {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ClientBootstrap.class);

	/**
	 * xml設定
	 */
	private static XMLConfiguration configuration;

	/**
	 * 客戶端服務
	 */
	private static ConsoleService consoleService;

	public final static String ID = "id";

	public final static String IP = "ip";

	public final static String PORT = "port";

	public final static String MODULE_TYPE_NAME = "moduleTypeName";

	public final static String MESSAGE_TYPE_NAME = "messageTypeName";

	/**
	 * client id, sender
	 */
	private static String id;

	/**
	 * 判斷是否啟動
	 */
	private static boolean started;

	public ClientBootstrap() {
	}

	public static boolean isStarted() {
		return started;
	}

	/**
	 * 從main直接啟動
	 * 
	 * file path
	 * 
	 * data/config/appConfig-client1.xml
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		if (!started) {
			long beg = System.currentTimeMillis();
			try {
				// 建構console客戶端服務
				buildConsoleService(args);
			} catch (Exception ex) {
				ex.printStackTrace();
				started = false;
			}
			long end = System.currentTimeMillis();
			//
			if (started) {
				LOGGER.info("[" + id + "] start at " + (end - beg) + " mills. ");
			} else {
				LOGGER.error("[" + id + "] started fail");
			}
		}
	}

	/**
	 * 建構客戶端服務
	 * 
	 * @param args
	 * @throws Exception
	 */
	protected static void buildConsoleService(String args[]) throws Exception {
		// 加入client設定檔 from args
		if (ArrayHelper.isEmpty(args)) {
			LOGGER.error("can't find any appConfig-client.xml");
			return;
		}
		// class path
		// org/openyu/mix/bootstrap/client/appConfig-client1.xml
		configuration = new XMLConfiguration((String) args[0]);
		//
		id = configuration.getString(ID);
		String ip = configuration.getString(IP);
		int port = configuration.getInt(PORT);
		String moduleTypeName = configuration.getString(MODULE_TYPE_NAME);
		String messageTypeName = configuration.getString(MESSAGE_TYPE_NAME);
		//
		consoleService = new ConsoleServiceImpl();
		consoleService.start();
		// 啟動
		started = consoleService.isStarted();
	}
}
