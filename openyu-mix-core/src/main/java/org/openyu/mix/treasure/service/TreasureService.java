package org.openyu.mix.treasure.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 秘寶服務
 */
public interface TreasureService extends AppService {
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		// log已改成兩個table,分開紀錄
		// TreasureBuyLog
		// TreasureRefreshLog

		// /**
		// * 刷新
		// */
		// REFRESH(1),

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
	 * 購買類別
	 */
	public enum BuyType implements IntEnum {
		/**
		 * 金幣購買
		 */
		GOLD(1),

		/**
		 * 儲值幣購買
		 */
		COIN(2),

		//

		;
		private final int value;

		private BuyType(int value) {
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
		 * 祕寶不存在
		 */
		TREASURE_NOT_EXIST(12),

		/**
		 * 道具不存在
		 */
		ITEM_NOT_EXIST(13),

		/**
		 * 道具金幣價格為0
		 */
		ITEM_PRICE_IS_ZERO(14),

		/**
		 * 道具儲值幣價格為0
		 */
		ITEM_COIN_IS_ZERO(15),

		/**
		 * 購買類別不存在
		 */
		BUY_TYPE_NOT_EXIST(21),

		/**
		 * 已經購買了
		 */
		ALREADY_BUY(22),

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
	 * 發送祕寶欄位
	 * 
	 * @param role
	 * @param bagInfo
	 * @return
	 */
	Message sendTreasurePen(Role role, TreasurePen treasurePen);

	/**
	 * 填充祕寶欄位
	 * 
	 * @param message
	 * @param treasurePen
	 * @return
	 */
	boolean fillTreasurePen(Message message, TreasurePen treasurePen);

	/**
	 * 發送上架祕寶
	 * 
	 * @param role
	 * @param treasure
	 * @return
	 */
	Message sendTreasure(Role role, Treasure treasure);

	/**
	 * 填充上架祕寶
	 * 
	 * @param message
	 * @param treasure
	 * @return
	 */
	boolean fillTreasure(Message message, Treasure treasure);

	/**
	 * 建構祕寶
	 * 
	 * @param stockId
	 * @param treasureId
	 * @return
	 */
	Treasure createTreasure(String stockId, String treasureId);

	/**
	 * 隨機上架祕寶
	 * 
	 * @return
	 */
	Map<Integer, Treasure> randomTreasures();

	/**
	 * 刷新秘寶結果
	 */
	public interface RefreshResult extends AppResult {
		/**
		 * 刷新時間
		 * 
		 * @return
		 */
		long getRefreshTime();

		void setRefreshTime(long refreshTime);

		/**
		 * 剩餘毫秒
		 */
		long getResidualMills();

		void setResidualMills(long residualMills);

		/**
		 * 上架祕寶
		 * 
		 * @return
		 */
		Map<Integer, Treasure> getTreasures();

		void setTreasures(Map<Integer, Treasure> treasures);

		/**
		 * 真正成功扣道具及儲值幣的次數
		 * 
		 * @return
		 */
		int getTotalTimes();

		void setTotalTimes(int totalTimes);

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
	 * 刷新秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	RefreshResult refresh(boolean sendable, Role role);

	/**
	 * 檢查刷新秘寶
	 * 
	 * @param role
	 * @return
	 */
	ErrorType checkRefresh(Role role);

	/**
	 * 發送刷新秘寶
	 * 
	 * @param errorType
	 * @param role
	 * @return
	 */
	Message sendRefresh(ErrorType errorType, Role role);

	/**
	 * 購買秘寶結果
	 */
	public interface BuyResult extends AppResult {
		/**
		 * 購買類別
		 * 
		 * @return
		 */
		BuyType getBuyType();

		void setBuyType(BuyType buyType);

		/**
		 * 上架祕寶索引
		 * 
		 * @return
		 */
		int getIndex();

		void setIndex(int index);

		/**
		 * 上架祕寶
		 * 
		 * @return
		 */
		Treasure getTreasure();

		void setTreasure(Treasure treasure);

		/**
		 * 購買的道具
		 * 
		 * @return
		 */
		Item getItem();

		void setItem(Item item);

		/**
		 * 消耗的金幣
		 * 
		 * @return
		 */
		long getSpendGold();

		void setSpendGold(long spendGold);

		/**
		 * 消耗的儲值幣
		 * 
		 * @return
		 */
		int getSpendCoin();

		void setSpendCoin(int spendCoin);
	}

	/**
	 * 購買秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @param buyValue
	 *            購買類別 @see BuyType
	 * @param index
	 * @return
	 */
	BuyResult buy(boolean sendable, Role role, int buyValue, int index);

	/**
	 * 金幣購買秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @param index
	 * @return
	 */
	BuyResult goldBuy(boolean sendable, Role role, int index);

	/**
	 * 檢查金幣購買秘寶
	 * 
	 * @param role
	 * @param index
	 * @return
	 */
	ErrorType checkGoldBuy(Role role, int index);

	/**
	 * 儲值幣購買秘寶
	 * 
	 * @param sendable
	 * @param role
	 * @param index
	 * @return
	 */
	BuyResult coinBuy(boolean sendable, Role role, int index);

	/**
	 * 檢查儲值幣購買秘寶
	 * 
	 * @param role
	 * @param index
	 * @return
	 */
	ErrorType checkCoinBuy(Role role, int index);

	/**
	 * 發送購買秘寶
	 * 
	 * @param errorType
	 * @param role
	 * @param buyResult
	 * @return
	 */
	Message sendBuy(ErrorType errorType, Role role, BuyResult buyResult);

}
