package org.openyu.mix.train.service;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 訓練服務
 */
public interface TrainService extends AppService {
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		/**
		 * 加入
		 */
		JOIN(1),

		/**
		 * 離開
		 */
		QUIT(2),

		/**
		 * 鼓舞
		 */
		INSPIRE(3),

		//
		;
		private final int value;

		private ActionType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum {
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		// /**
		// * 沒有錯誤,by item
		// */
		// NO_ERROR_BY_ITEM(1),

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(11),

		/**
		 * 已經加入訓練了
		 */
		ALREADY_JOIN(22),

		/**
		 * 已經鼓舞了
		 */
		ALREADY_INSPIRE(23),

		/**
		 * 還沒加入訓練
		 */
		NOT_JOIN(24),

		/**
		 * 超過每天可訓練毫秒
		 */
		OVER_DAILY_MILLS(25),

		/**
		 * 無法加入
		 */
		CAN_NOT_JOIN(26),

		/**
		 * 無法離開
		 */
		CAN_NOT_QUIT(27),

		/**
		 * 超過重置時間
		 */
		OVER_RESET_TIME(28),

		/**
		 * 無法增加道具
		 */
		CAN_NOT_INCREASE_ITEM(31),

		/**
		 * 無法減少道具
		 */
		CAN_NOT_DECREASE_ITEM(32),

		/**
		 * 等級不足
		 */
		LEVLE_NOT_ENOUGH(51),

		/**
		 * 金幣不足
		 */
		GOLD_NOT_ENOUGH(52),

		/**
		 * 儲值幣不足
		 */
		COIN_NOT_ENOUGH(53),

		/**
		 * vip不足
		 */
		VIP_NOT_ENOUGH(54),

		;

		private final int value;

		private ErrorType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this,
					ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	// --------------------------------------------------
	// 流程
	// --------------------------------------------------
	// request -> check -> reponse
	// --------------------------------------------------

	/**
	 * 發送訓練欄位
	 * 
	 * @param role
	 * @param trainPen
	 */
	void sendTrainPen(Role role, TrainPen trainPen);

	/**
	 * 填充訓練欄位
	 * 
	 * @param message
	 * @param trainPen
	 */
	void fillTrainPen(Message message, TrainPen trainPen);

	/**
	 * 加入訓練結果
	 */
	public interface JoinResult extends AppResult {
		/**
		 * 開始時間
		 * 
		 * @return
		 */
		long getJoinTime();

		void setJoinTime(long joinTime);

		/**
		 * 結束時間
		 * 
		 * @return
		 */
		long getQuitTime();

		void setQuitTime(long quitTime);

		/**
		 * 剩餘毫秒
		 * 
		 * @return
		 */
		long getResidualMills();

		void setResidualMills(long residualMills);

	}

	/**
	 * 加入訓練
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	JoinResult join(boolean sendable, Role role);

	/**
	 * 檢查加入訓練
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkJoin(Role role);

	/**
	 * 發送加入訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	void sendJoin(ErrorType errorType, Role role);

	/**
	 * 離開訓練結果
	 */
	public interface QuitResult extends JoinResult {

	}

	/**
	 * 離開訓練
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	QuitResult quit(boolean sendable, Role role);

	/**
	 * 檢查離開訓練
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkQuit(Role role);

	/**
	 * 發送離開訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	void sendQuit(ErrorType errorType, Role role, long quitTime);

	/**
	 * 鼓舞訓練結果
	 */
	public interface InspireResult extends AppResult {
		/**
		 * 鼓舞時間
		 * 
		 * @return
		 */
		long getInspireTime();

		void setInspireTime(long inspireTime);

		/**
		 * 消耗的道具
		 * 
		 * @return
		 */
		List<Item> getSpendItems();

		void setSpendItems(List<Item> spendItems);

		/**
		 * 消耗的儲值幣
		 * 
		 * @return
		 */
		int getSpendCoin();

		void setSpendCoin(int spendCoin);
	}

	/**
	 * 鼓舞訓練
	 * 
	 * @param sendable
	 * @param role
	 */
	InspireResult inspire(boolean sendable, Role role);

	/**
	 * 檢查鼓舞訓練
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkInspire(Role role);

	/**
	 * 發送鼓舞訓練
	 * 
	 * @param errorType
	 * @param role
	 */
	void sendInspire(ErrorType errorType, Role role, long inspireTime);

	/**
	 * 重置每日可訓練毫秒限制
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	boolean reset(boolean sendable, Role role);

	/**
	 * 檢查重置訓練
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkReset(Role role);

	/**
	 * 發送重置訓練
	 * 
	 * @param role
	 * @param trainPen
	 */
	void sendReset(Role role, TrainPen trainPen);

	/**
	 * 重置每日可訓練毫秒限制,所有線上玩家
	 * 
	 * 排程用
	 * 
	 * @param sendable
	 * @return
	 */
	int reset(boolean sendable);

}
