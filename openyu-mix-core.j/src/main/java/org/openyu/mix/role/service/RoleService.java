package org.openyu.mix.role.service;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

/**
 * 角色服務
 */
public interface RoleService extends AppService {
	// --------------------------------------------------
	// RoleGoldLog用
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
	// RoleGoldLog用
	// --------------------------------------------------
	/**
	 * 金幣增減的原因
	 * 
	 * 第1位數 => 1=系統用, 2=子功能用
	 * 
	 * 第2,3位數模組 => 01-99
	 * 
	 * 第4,5位數原因 => 01-99
	 * 
	 * */
	public enum GoldType implements IntEnum {
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
		ITEM_ROLE_GOLD_THING(159101),

		/**
		 * 商店賣出,加項
		 */
		SHOP_SELL(160101),

		/**
		 * 商店購買,減項
		 */
		SHOP_BUY(160501),

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
		 * 莊園祈禱,減項
		 */
		MANOR_PRAY(213513),

		/**
		 * 莊園加速,減項
		 */
		MANOR_SPEED(213514),

		/**
		 * 莊園復活,減項
		 */
		MANOR_REVIVE(213516),

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

		private GoldType(int value) {
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
		 * 沒有錯誤,by item
		 */
		NO_ERROR_BY_ITEM(1),

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(11),

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

		/**
		 * 儲存資料庫失敗
		 */
		STORE_DATABASE_FAIL(101),

		/**
		 * 重試儲存資料庫
		 */
		RETRYING_STORE_DATABASE(102),

		/**
		 * 儲存檔案失敗
		 */
		STORE_FILE_FAIL(103),

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
	 * 查詢角色
	 * 
	 * @param id
	 * @return
	 */
	Role findRole(String id);

	/**
	 * 查詢角色
	 * 
	 * @param locale
	 * @param id
	 * @return
	 */
	Role findRole(Locale locale, String id);

	/**
	 * 查詢是否有效角色
	 * 
	 * @param valid
	 * @return
	 */
	List<Role> findRole(boolean valid);

	/**
	 * 查詢是否有效角色
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	List<Role> findRole(Locale locale, boolean valid);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 建構角色
	 * 
	 * @param roleId
	 * @param name
	 *            角色名稱
	 * @param raceType
	 *            種族類別
	 * @param careerType
	 *            職業類別
	 * @param genderType
	 *            性別
	 * @param hairType
	 *            髮型
	 * @param faceType
	 *            臉型
	 * @return
	 */
	Role createRole(String roleId, String name, RaceType raceType,
			CareerType careerType, GenderType genderType, HairType hairType,
			FaceType faceType);

	/**
	 * 重新計算所有屬性,等級,行業會改變屬性
	 * 
	 * 屬性=行業屬性初值+(level-1)*成長值
	 * 
	 * point=industry.ponit+(role.level-1)*industry.addoint
	 * 
	 * @param role
	 * @return
	 */
	boolean calcAttrubutes(Role role);

	// --------------------------------------------------
	// 同步角色
	// --------------------------------------------------
	/**
	 * 發送同步角色連線
	 * 
	 * @param role
	 * @return
	 */
	Message sendSyncRoleConnect(Role role);

	/**
	 * 同步角色連線
	 * 
	 * @param syncRole
	 * @return
	 */
	Role syncRoleConnect(Role syncRole);

	/**
	 * 發送同步角色斷線
	 * 
	 * @param syncRole
	 * @return
	 */
	Message sendSyncRoleDisconnect(Role syncRole);

	/**
	 * 同步角色斷線
	 * 
	 * @param syncRoleId
	 * @return
	 */
	Role syncRoleDisconnect(String syncRoleId);

	/**
	 * 發送同步角色欄位
	 * 
	 * @param syncRoleId
	 * @param fieldName
	 * @param value
	 * @return
	 */
	Message sendSyncRoleField(String syncRoleId, String fieldName,
			Serializable value);

	/**
	 * 同步, 同步角色的欄位
	 * 
	 * @param syncRoleId
	 * @param fieldName
	 * @param value
	 * @return
	 */
	Object syncRoleField(String syncRoleId, String fieldName, Object value);

	// --------------------------------------------------
	// 增減經驗
	// --------------------------------------------------
	/**
	 * 增減經驗,exp->level->attribute
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param exp
	 *            增減的經驗
	 * @return
	 */
	long changeExp(boolean sendable, Role role, long exp);

	/**
	 * 發送經驗
	 * 
	 * @param role
	 *            角色
	 * @param exp
	 *            經驗
	 * @param diffExp
	 *            增減的經驗
	 * @return
	 */
	Message sendExp(Role role, long exp, long diffExp);

	// --------------------------------------------------
	// 增減技魂(sp)
	// --------------------------------------------------
	/**
	 * 增減技魂(sp)
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param sp
	 *            增減的技魂(sp)
	 * @return
	 */
	long changeSp(boolean sendable, Role role, long sp);

	/**
	 * 發送技魂(sp)
	 * 
	 * @param role
	 *            角色
	 * @param sp
	 *            技魂(sp)
	 * @param diffSp
	 *            增減的技魂(sp)
	 * @return
	 */
	Message sendSp(Role role, long sp, long diffSp);

	// --------------------------------------------------
	// 增減等級
	// --------------------------------------------------

	/**
	 * 增減等級,會重新計算屬性,增加技能
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 *            角色
	 * @param level
	 *            要增減的等級
	 * @return
	 */
	int changeLevel(boolean sendable, Role role, int level);

	/**
	 * 發送等級
	 * 
	 * @param role
	 *            角色
	 * @param level
	 *            等級
	 * @return
	 */
	Message sendLevel(Role role, int level);

	/**
	 * 成名等級
	 * 
	 * @param role
	 *            角色
	 * @param diffLevel
	 * @return
	 */
	Message sendFamousLevel(Role role, int diffLevel);

	// --------------------------------------------------
	// 增減金幣
	// --------------------------------------------------
	/**
	 * 檢查增加金幣
	 * 
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @return
	 */
	boolean checkIncreaseGold(Role role, long gold);

	/**
	 * 增加金幣
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @param goldReason
	 *            log用,金幣增加的原因
	 * @return
	 */
	long increaseGold(boolean sendable, Role role, long gold,
			GoldType goldReason);

	/**
	 * 檢查減少金幣
	 * 
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @return
	 */
	boolean checkDecreaseGold(Role role, long gold);

	/**
	 * 減少金幣
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @param goldReason
	 *            log用,金幣減少的原因
	 * @return
	 */
	long decreaseGold(boolean sendable, Role role, long gold,
			GoldType goldReason);

	/**
	 * 增減金幣
	 * 
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 *            角色
	 * @param gold
	 *            增減的金幣
	 * @param goldAction
	 *            log用,金幣操作類別
	 * @param goldReason
	 *            log用,金幣增減的原因
	 * @return
	 */
	long changeGold(boolean sendable, Role role, long gold,
			ActionType goldAction, GoldType goldReason);

	/**
	 * 重置金幣,gold=0
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param goldReason
	 *            log用,金幣重置的原因
	 * @return
	 */
	boolean resetGold(boolean sendable, Role role, GoldType goldReason);

	/**
	 * 發送金幣
	 * 
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @param diffGold
	 *            增減的金幣
	 * @return
	 */
	Message sendGold(Role role, long gold, long diffGold);

	/**
	 * 增減聲望
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param fame
	 *            增減的聲望
	 * @return
	 */
	int changeFame(boolean sendable, Role role, int fame);

	/**
	 * 發送聲望
	 * 
	 * @param role
	 *            角色
	 * @param fame
	 *            聲望
	 * @param diffFame
	 *            增減的聲望
	 * @return
	 */
	Message sendFame(Role role, int fame, int diffFame);

	/**
	 * 增減屬性
	 * 
	 * @param sendable
	 * @param role
	 * @param attributeValue
	 *            屬性類型
	 * @param point
	 *            增減屬性值
	 * @param addPoint
	 *            增減增加的屬性值
	 * @param addRate
	 *            增減增加的比率值
	 * @return
	 */
	Attribute changeAttribute(boolean sendable, Role role, int attributeValue,
			int point, int addPoint, int addRate);

	/**
	 * 發送單一屬性回應
	 * 
	 * @param role
	 * @param attribute
	 * @return
	 */
	Message sendAttribute(Role role, Attribute attribute);

	// /**
	// * 填充屬性
	// *
	// * remove to RoleHelper.fillAttribute
	// *
	// * @param message
	// * @param attribute
	// */
	// void fillAttribute(Message message, Attribute attribute);

	/**
	 * 發送屬性群
	 * 
	 * @param role
	 * @param attributeGroup
	 * @return
	 */
	Message sendAttributeGroup(Role role, AttributeGroup attributeGroup);

	// /**
	// * 填充屬性群
	// *
	// * remove to RoleHelper.fillAttributeGroup
	// *
	// * @param message
	// * @param attributeGroup
	// */
	// void fillAttributeGroup(Message message, AttributeGroup attributeGroup);

	/**
	 * 填充有改變的屬性
	 * 
	 * @param message
	 * @param attributes
	 */
	// void fillChangedAttribute(Message message, Role role, List<AttributeType>
	// changedAttributes);

	/**
	 * 增減vip等級
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param vip
	 *            vip等級
	 * @return
	 */
	int changeVip(boolean sendable, Role role, int vip);

	/**
	 * 發送vip等級
	 * 
	 * @param role
	 *            角色
	 * @param vipType
	 *            vip等級
	 * @param diffVip
	 *            增減的vip等級
	 * @return
	 */
	Message sendVip(Role role, VipType vipType, int diffVip);

	public interface SpendResult extends AppResult {

		/**
		 * 錯誤類別
		 * 
		 * @return
		 */
		ErrorType getErrorType();

		void setErrorType(ErrorType errorType);

		/**
		 * 真正成功扣道具及儲值幣的次數
		 * 
		 * @return
		 */
		int getTotalTimes();

		void setTotalTimes(int totalTimes);

		/**
		 * 消耗道具的次數
		 * 
		 * @return
		 */
		int getItemTimes();

		void setItemTimes(int itemTimes);

		/**
		 * 消耗道具的數量
		 * 
		 * @return
		 */
		int getItemAmount();

		void setItemAmount(int itemAmount);

		/**
		 * 消耗的道具
		 * 
		 * @return
		 */
		List<Item> getItems();

		void setItems(List<Item> items);

		/**
		 * 消耗儲值幣的次數
		 * 
		 * @return
		 */
		int getCoinTimes();

		void setCoinTimes(int coinTimes);

		/**
		 * 消耗的儲值幣
		 * 
		 * @return
		 */
		int getCoin();

		void setCoin(int coin);

	}

	/**
	 * 消耗道具或儲值幣
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param itemId
	 *            消耗道具d
	 * @param everyAmount
	 *            每次扣的道具數量
	 * @param everyCoin
	 *            每次扣的儲值幣
	 * @param coinType
	 *            coinType log用,儲值增減的原因
	 * @param vipLimit
	 *            vip限制
	 * @return
	 */
	SpendResult spendByItemCoin(boolean sendable, Role role, String itemId,
			int everyAmount, int everyCoin, CoinType coinType, VipType vipLimit);

	/**
	 * 消耗道具或儲值幣
	 * 
	 * @param sendable
	 * @param role
	 *            角色
	 * @param spendTimes
	 *            消耗的次數
	 * @param itemId
	 *            消耗道具d
	 * @param everyAmount
	 *            每次扣的道具數量
	 * @param everyCoin
	 *            每次扣的儲值幣
	 * @param coinType
	 *            coinType log用,儲值增減的原因
	 * @param vipLimit
	 *            vip限制
	 * @return
	 */
	SpendResult spendByItemCoin(boolean sendable, Role role, int spendTimes,
			String itemId, int everyAmount, int everyCoin, CoinType coinType,
			VipType vipLimit);
}
