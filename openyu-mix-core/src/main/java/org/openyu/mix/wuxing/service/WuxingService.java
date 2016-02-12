package org.openyu.mix.wuxing.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

public interface WuxingService extends AppService {
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		// /**
		// * 玩
		// */
		// PLAY(1),
		//
		// /**
		// * 處理單擊放入包包請求
		// */
		// PUT(2),

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
	 * 玩的類別,(類型,次數)
	 * 
	 */
	public enum PlayType implements IntEnum {
		/**
		 * 青銅按鈕,可玩1次,消耗金幣,有每日次數限制
		 */
		BRONZE(1, 1),

		/**
		 * 銀按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		 */
		GALACTIC(2, 1),

		/**
		 * 金按鈕,可玩10次,消耗道具或元寶,沒有每日次數限制
		 */
		GOLDEN(3, 10),

		/**
		 * 黑按鈕,可玩100次,消耗道具或元寶,沒有每日次數限制
		 */
		BLACK(4, 100),
		//
		;

		private final int value;

		/**
		 * 玩的次數
		 */
		private final int playTimes;

		private PlayType(int value, int playTimes) {
			this.value = value;
			this.playTimes = playTimes;
		}

		public int getValue() {
			return value;
		}

		/**
		 * 玩的次數
		 * 
		 * @return
		 */
		public int playTimes() {
			return playTimes;
		}
	}

	/**
	 * 放入類別
	 * 
	 */
	public enum PutType implements IntEnum {
		/**
		 * 處理單擊放入包包請求
		 */
		ONE(1),

		/**
		 * 處理所有獎勵區放入包包請求
		 */
		ALL(2),

		//
		;

		private final int value;

		private PutType(int value) {
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

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(11),

		/**
		 * 超過每日次數
		 */
		OVER_PLAY_DAILY_TIMES(12),

		/**
		 * 道具不存在
		 */
		ITEM_NOT_EXIST(13),

		/**
		 * 中獎區滿了
		 */
		AWARDS_FULL(14),

		/**
		 * 獎勵不存在
		 */
		AWARD_NOT_EXIST(15),

		/**
		 * 結果不存在
		 */
		OUTCOME_NOT_EXIST(16),

		/**
		 * 玩的次數為0
		 */
		PLAY_TIMES_IS_ZERO(17),

		/**
		 * 玩的類別不存在
		 */
		PLAY_TYPE_NOT_EXIST(21),

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
		 * 儲值不足
		 */
		COIN_NOT_ENOUGH(53),

		/**
		 * vip不足
		 */
		VIP_NOT_ENOUGH(54),

		//
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
	 * 發送五行欄位
	 * 
	 * @param role
	 * @param bagInfo
	 * @return
	 */
	Message sendWuxingPen(Role role, WuxingPen wuxingPen);

	/**
	 * 填充五行欄位
	 * 
	 * @param message
	 * @param wuxingPen
	 * @return
	 */
	boolean fillWuxingPen(Message message, WuxingPen wuxingPen);

	/**
	 * 填充獎勵
	 * 
	 * @param message
	 * @param awards
	 * @return
	 */
	boolean fillAwards(Message message, Map<String, Integer> awards);

	/**
	 * 建構開出的結果
	 * 
	 * @return
	 */
	Outcome createOutcome();

	/**
	 * 建構開出的結果
	 * 
	 * @param outcomeId
	 *            結果id
	 * @param bankerId
	 * @param playerId
	 * @return
	 */
	Outcome createOutcome(String outcomeId, String bankerId, String playerId);

	/**
	 * 玩的結果
	 */
	interface PlayResult extends AppResult {
		/**
		 * 玩的類別
		 * 
		 * @return
		 */
		PlayType getPlayType();

		void setPlayType(PlayType playType);

		/**
		 * 玩的時間
		 * 
		 * @return
		 */
		long getPlayTime();

		void setPlayTime(long playTime);

		/**
		 * 每日已玩的次數
		 * 
		 * @return
		 */
		int getDailyTimes();

		void setDailyTimes(int dailyTimes);

		/**
		 * 最後玩的結果
		 * 
		 * @return
		 */
		Outcome getOutcome();

		void setOutcome(Outcome outcome);

		/**
		 * 所有結果
		 * 
		 * @return
		 */
		List<Outcome> getOutcomes();

		void setOutcomes(List<Outcome> outcomes);

		/**
		 * 真正成功扣道具及儲值幣的次數
		 * 
		 * @return
		 */
		int getTotalTimes();

		void setTotalTimes(int totalTimes);

		/**
		 * 消耗的金幣
		 * 
		 * @return
		 */
		long getSpendGold();

		void setSpendGold(long spendGold);

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
	 * 玩五行
	 * 
	 * @param sendable
	 * @param role
	 * @param playValue
	 *            玩的類別 @see PlayType
	 * @return
	 */
	PlayResult play(boolean sendable, Role role, int playValue);

	/**
	 * 用金幣玩
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	PlayResult goldPlay(boolean sendable, Role role);

	/**
	 * 檢查用金幣玩
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkGoldPlay(Role role);

	/**
	 * 用道具或儲值幣玩
	 * 
	 * @param sendable
	 * @param role
	 * @param playType
	 * @see PlayType
	 * @return
	 */
	PlayResult itemCoinPlay(boolean sendable, Role role, PlayType playType);

	/**
	 * 檢查用道具或儲值幣玩
	 * 
	 * @param role
	 * @param times
	 *            玩的次數
	 * @return
	 */
	ErrorType checkItemCoinPlay(Role role, int playTimes);

	/**
	 * 發送玩五行
	 * 
	 * @param errorType
	 *            錯誤類別
	 * @param role
	 * @param playResult
	 * @return
	 */
	Message sendPlay(ErrorType errorType, Role role, PlayResult playResult);

	/**
	 * 發送中獎區
	 * 
	 * @param role
	 * @param awards
	 * @return
	 */
	Message sendAwards(Role role, Map<String, Integer> awards);

	/**
	 * 放入包包的結果
	 */
	interface PutResult extends AppResult {
		/**
		 * 已放入包包的獎勵
		 * 
		 * @return
		 */
		Map<String, Integer> getAwards();

		void setAwards(Map<String, Integer> awards);
	}

	/**
	 * 單擊獎勵放入包包
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 */
	PutResult putOne(boolean sendable, Role role, String itemId, int amount);

	/**
	 * 檢查單擊獎勵放入包包
	 * 
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	ErrorType checkPutOne(Role role, String itemId, int amount);

	/**
	 * 發送單擊獎勵放入包包
	 * 
	 * @param errorType
	 * @param role
	 * @param itemId
	 * @param amount
	 * @return
	 */
	Message sendPutOne(ErrorType errorType, Role role, String itemId, int amount);

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * @param role
	 * @return
	 */
	PutResult putAll(boolean sendable, Role role);

	/**
	 * 發送所有中獎區獎勵放入包包
	 * 
	 * @param errorType
	 * @param role
	 * @param awards
	 * @return
	 */
	Message sendPutAll(ErrorType errorType, Role role, PutResult putResult);

	/**
	 * 重置每日已玩的次數
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	boolean reset(boolean sendable, Role role);

	/**
	 * 檢查重置
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkReset(Role role);

	/**
	 * 發送重置
	 * 
	 * @param role
	 * @param trainInfo
	 * @return
	 */
	Message sendReset(Role role, WuxingPen wuxingPen);

	/**
	 * 重置每日已玩的次數,所有線上玩家
	 * 
	 * 排程用
	 * 
	 * @param sendable
	 * @return
	 */
	int reset(boolean sendable);

}
