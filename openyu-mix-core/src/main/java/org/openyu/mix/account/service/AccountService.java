package org.openyu.mix.account.service;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 帳戶服務
 */
public interface AccountService extends AppService {
	// --------------------------------------------------
	// AccountCoinLog用
	// --------------------------------------------------
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		/**
		 * 增加,+
		 */
		INCREASE(1),

		/**
		 * 減少,-
		 */
		DECREASE(2),

		/**
		 * 重置
		 */
		RESET(3),

		;
		private final int value;

		private ActionType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	// --------------------------------------------------
	// AccountCoinLog用
	// --------------------------------------------------
	/**
	 * 儲值增減的原因
	 * 
	 * 第1位數 => 1=系統用, 2=子功能用
	 * 
	 * 第2,3位數模組 => 01-99
	 * 
	 * 第4,5位數原因 => 01-99
	 * 
	 * */
	public enum CoinType implements IntEnum {
		// ---------------------------------------------------
		// reservation 100-109=10
		// ---------------------------------------------------

		// ---------------------------------------------------
		// spec module 110-119=10
		// ---------------------------------------------------
		/**
		 * gm,加項
		 */
		GM_INCREASE(110101),

		/**
		 * gm,減項
		 */
		GM_DECREASE(110501),

		/**
		 * gm重置
		 */
		GM_RESET(110502),

		// ---------------------------------------------------
		// master 120-149=30
		// ---------------------------------------------------

		// ---------------------------------------------------
		// slave 150-198=49
		// ---------------------------------------------------

		/**
		 * 秘技獲得,加項
		 * 
		 * chat=debug
		 */
		DEBUG_INCREASE(152101),

		/**
		 * 秘技減少,減項
		 * 
		 * chat=debug
		 */
		DEBUG_DECREASE(152501),

		/**
		 * 秘技重置
		 * 
		 * chat=debug
		 */
		DEBUG_RESET(152502),

		/**
		 * 增加儲值幣道具,加項
		 */
		ITEM_ACCOUNT_COIN_THING(159101),

		// ---------------------------------------------------
		// 子功能開發
		// ---------------------------------------------------
		// 200-209保留
		// ---------------------------------------------------

		// ---------------------------------------------------
		// 四象
		// ---------------------------------------------------
		/**
		 * 玩四象,減項
		 */
		SASANG_PLAY(210501),

		// ---------------------------------------------------
		// 卜卦
		// ---------------------------------------------------
		/**
		 * 玩卜卦,減項
		 */
		DIVINE_PLAY(211501),

		// ---------------------------------------------------
		// 莊園
		// ---------------------------------------------------
		/**
		 * 莊園開墾,減項
		 */
		MANOR_RECLAIM(213501),

		/**
		 * 莊園休耕,減項
		 */
		MANOR_DISUSE(213502),

		/**
		 * 莊園澆水,減項
		 */
		MANOR_WATER(213512),

		/**
		 * 莊園澆水所有,減項
		 */
		MANOR_WATER_ALL(213612),

		/**
		 * 莊園祈禱,減項
		 */
		MANOR_PRAY(213513),

		/**
		 * 莊園祈禱所有,減項
		 */
		MANOR_PRAY_ALL(213613),

		/**
		 * 莊園加速,減項
		 */
		MANOR_SPEED(213514),

		/**
		 * 莊園加速所有,減項
		 */
		MANOR_SPEED_ALL(2135614),

		/**
		 * 莊園復活,減項
		 */
		MANOR_REVIVE(213516),

		/**
		 * 莊園復活所有,減項
		 */
		MANOR_REVIVE_ALL(213616),

		// ---------------------------------------------------
		// 秘寶
		// ---------------------------------------------------
		/**
		 * 秘寶刷新,減項
		 */
		TREASURE_REFRESH(214501),

		/**
		 * 秘寶購買,減項
		 */
		TREASURE_BUY(214502),

		// ---------------------------------------------------
		// 訓練
		// ---------------------------------------------------
		/**
		 * 訓練鼓舞,減項
		 */
		TRAIN_INSPIRE(215503),

		// ---------------------------------------------------
		// 五行
		// ---------------------------------------------------
		/**
		 * 玩五行,減項
		 */
		WUXING_PLAY(216501),

		//
		;
		private final int value;

		private CoinType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	// --------------------------------------------------
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
		 * 帳密錯誤
		 */
		ID_PASSWORD_ERROR(11),

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
	// db
	// --------------------------------------------------
	/**
	 * 查詢帳戶
	 * 
	 * @param accountId
	 * @return
	 */
	Account findAccount(String accountId);

	/**
	 * 查詢帳戶
	 * 
	 * @param locale
	 * @param accountId
	 * @return
	 */
	Account findAccount(Locale locale, String accountId);

	/**
	 * 查詢是否有效帳戶
	 * 
	 * @param valid
	 * @return
	 */
	List<Account> findAccount(boolean valid);

	/**
	 * 查詢是否有效帳戶
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	List<Account> findAccount(Locale locale, boolean valid);

	/**
	 * 查詢帳戶的儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	int findCoin(String accountId);

	/**
	 * 查詢帳號的累計儲值幣
	 * 
	 * @param accountId
	 * @return
	 */
	int findAccuCoin(String accountId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 檢查是否可以增加帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param coin
	 *            正數
	 * @return
	 */
	boolean checkIncreaseCoin(String accountId, int coin);

	/**
	 * 增加帳戶的儲值幣
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            正數
	 * @param accuable
	 *            是否累計
	 * @param coinType
	 *            log用,儲值增加的原因
	 * @return 真正增加的儲值幣,0=無改變, >0有改變
	 */
	int increaseCoin(boolean sendable, String accountId, Role role, int coin,
			boolean accuable, CoinType coinType);

	/**
	 * 檢查是否可以減少帳戶的儲值幣
	 * 
	 * @param accountId
	 * @param coin
	 *            正數
	 * @return
	 */
	boolean checkDecreaseCoin(String accountId, int coin);

	/**
	 * 減少帳戶的儲值幣
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            正數
	 * @param coinType
	 *            log用,儲值增減的原因
	 * @return 真正減少的儲值幣,0=無改變, <0有改變
	 */
	int decreaseCoin(boolean sendable, String accountId, Role role, int coin,
			CoinType coinType);

	/**
	 * 增減帳戶的儲值幣
	 * 
	 * 直接對db處理,並更新角色上的帳戶
	 * 
	 * @param sendable
	 *            是否發訊息
	 * @param accountId
	 *            帳戶id
	 * @param role
	 *            角色
	 * @param coin
	 *            +增加,-減少
	 * @param accuable
	 *            是否累計
	 * @param actionType
	 *            log用,儲值操作類別
	 * @param coinType
	 *            log用,儲值增減的原因
	 * @return 真正增減的儲值幣,0=無改變, !=0有改變
	 */
	int changeCoin(boolean sendable, String accountId, Role role, int coin,
			boolean accuable, ActionType actionType, CoinType coinType);

	/**
	 * 重置帳戶儲值幣,coin=0,accuCoin=0
	 * 
	 * @param sendable
	 * @param accountId
	 * @param role
	 *            角色
	 * @param accuable
	 *            是否累計
	 * @param coinType
	 * @return
	 */
	boolean resetCoin(boolean sendable, String accountId, Role role,
			boolean accuable, CoinType coinType);

	/**
	 * 發送帳戶儲值回應
	 * 
	 * @param role
	 * @param coin
	 * @param diffCoin
	 * @return
	 */
	Message sendCoin(Role role, int coin, int diffCoin);

	/**
	 * 發送帳戶累計儲值回應
	 * 
	 * @param role
	 * @param accuCoin
	 * @return
	 */
	Message sendAccuCoin(Role role, int accuCoin);

	// --------------------------------------------------
	/**
	 * 建立帳戶
	 * 
	 * @param accountId
	 * @param name
	 * @return
	 */
	Account createAccount(String accountId, String name);

	/**
	 * 授權
	 * 
	 * @param accountId
	 * @param password
	 */
	void authorize(String accountId, String password);

	/**
	 * 檢查帳戶id,密碼是否正確
	 * 
	 * 正確=傳回認證碼
	 * 
	 * 錯誤=傳回null
	 * 
	 * @param accountId
	 * @param password
	 * @return
	 */
	String checkAccount(String accountId, String password);

	/**
	 * 授權請求來自於login server
	 * 
	 * @param accountId
	 * @param authKey
	 */
	void authorizeFromLogin(String accountId, String authKey);

	/**
	 * 授權回應
	 * 
	 * @param errorType
	 * @param accountId
	 * @param authKey
	 * @return
	 */
	Message sendAuthorize(ErrorType errorType, String accountId, String authKey);

}
