package org.openyu.mix.train.service.socklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.train.service.TrainSetService;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class TrainSocklet extends AppSockletServiceSupporter {
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TrainSocklet.class);

	@Autowired
	@Qualifier("trainSetService")
	protected transient TrainSetService trainSetService;

	@Autowired
	@Qualifier("trainService")
	protected transient TrainService trainService;

	public TrainSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case TRAIN_JOIN_RESPONSE: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			trainService.join(true, role);
			break;
		}
		case TRAIN_QUIT_RESPONSE: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			trainService.quit(true, role);
			break;
		}
		case TRAIN_INSPIRE_RESPONSE: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			trainService.inspire(true, role);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技訓練加入
		case TRAIN_DEBUG_JOIN_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_join(role);
			break;
		}
		// 祕技訓練離開
		case TRAIN_DEBUG_QUIT_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_quit(role);
			break;
		}
		// 祕技訓練鼓舞
		case TRAIN_DEBUG_INSPIRE_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_inspire(role);
			break;
		}
		// 祕技訓練重置
		case TRAIN_DEBUG_RESET_REQUEST: {
			String roleId = message.getString(0);
			//
			Role role = checkRole(roleId);
			DEBUG_reset(role);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	/**
	 * 祕技訓練加入
	 * 
	 * @param roleId
	 */
	protected void DEBUG_join(Role role) {
		trainService.join(true, role);
	}

	/**
	 * 祕技訓練離開
	 * 
	 * @param roleId
	 */
	protected void DEBUG_quit(Role role) {
		trainService.quit(true, role);
	}

	/**
	 * 祕技訓練鼓舞
	 * 
	 * @param roleId
	 */
	protected void DEBUG_inspire(Role role) {
		trainService.inspire(true, role);
	}

	/**
	 * 祕技訓練重置
	 * 
	 * @param roleId
	 */
	protected void DEBUG_reset(Role role) {
		TrainPen trainPen = role.getTrainPen();
		// 若在訓練中,則離開訓練
		boolean contains = trainSetService.containRole(role);
		if (contains) {
			trainService.quit(true, role);
		}
		//
		boolean result = trainPen.reset();
		// 發訊息
		if (result) {
			trainService.sendReset(role, trainPen);
		}
	}
}
