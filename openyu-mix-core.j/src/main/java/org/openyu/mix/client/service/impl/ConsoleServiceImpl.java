package org.openyu.mix.client.service.impl;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.openyu.commons.lang.StringHelper;
import org.openyu.socklet.connector.frame.ClientFrame;
import org.openyu.socklet.connector.frame.impl.ClientFrameImpl;
import org.openyu.socklet.connector.service.supporter.ClientServiceSupporter;
import org.openyu.mix.client.service.ConsoleService;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * console客戶端服務
 */
public class ConsoleServiceImpl extends ClientServiceSupporter implements
		ConsoleService {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ConsoleServiceImpl.class);

	/**
	 * 客戶端ui
	 */
	private ClientFrame clientFrame;

	public ConsoleServiceImpl() {
	}

	public ClientFrame getClientFrame() {
		return clientFrame;
	}

	public void setClientFrame(ClientFrame clientFrame) {
		this.clientFrame = clientFrame;
	}

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				clientFrame = new ClientFrameImpl(id);
				clientFrame.getCommandTextField().addKeyListener(
						new CommandTextFieldKeyAdapter(clientFrame));
				//
				clientFrame.setVisible(true);// 顯示frame
			}
		});
		//
		super.start();
	}

	/**
	 * 命令區,輸入處理
	 */
	protected class CommandTextFieldKeyAdapter extends KeyAdapter {
		private ClientFrame clientFrame;

		public CommandTextFieldKeyAdapter(ClientFrame clientFrame) {
			this.clientFrame = clientFrame;
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String text = clientFrame.getCommandTextField().getText();
				if (StringHelper.isBlank(text)) {
					return;
				}
				// 聊天
				Message message = messageService.createClient(id,
						CoreMessageType.CHAT_SPEAK_REQUEST);
				message.addString(id);// 角色id
				message.addInt(7);// 7=世界聊天,Channel.ChannelType
				message.addString(text);// 聊天內容
				message.addString("");// html
				//
				addMessage(message);
				//
				LOGGER.info(text);
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				clientFrame.getCommandTextField().setText("");
			}
		}
	}

	/**
	 * 服務,收到來自於server的訊息
	 * 
	 * @param message
	 */
	public void service(final Message message) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				clientFrame.getMessageTextArea().append(
						message + StringHelper.LF);
				clientFrame.getMessageTextArea().setCaretPosition(
						clientFrame.getMessageTextArea().getText().length());
			}
		});
		// TODO debug
		// System.out.println(message);
	}
}
