package org.openyu.mix.manor.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.ManorPen.Farm;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.socklet.message.vo.Message;

public interface ManorService extends AppService {
	/**
	 * 操作類別
	 */
	public enum ActionType implements IntEnum {
		/**
		 * 土地開墾
		 */
		RECLAIM(1),

		/**
		 * 土地休耕
		 */
		DISUSE(2),

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
	 * 耕種類別
	 */
	public enum CultureType implements IntEnum {
		/**
		 * 種植
		 */
		PLANT(1),

		/**
		 * 澆水,增加成長速度,會感受到土地強化的效果,增加產量
		 */
		WATER(2),

		/**
		 * 祈禱,減少成長毫秒,會感受到土地強化的效果,增加產量
		 */
		PRAY(3),

		/**
		 * 加速,直接成熟,直接收割,無法感受到土地強化的效果
		 */
		SPEED(4),

		/**
		 * 收割,成熟後才能收割,會感受到土地強化的效果,增加產量
		 */
		HARVEST(5),

		/**
		 * 復活,把枯萎的復活,直接收割,無法感受到土地強化的效果
		 */
		REVIVE(6),

		/**
		 * 清除作物,成長中,或已枯萎,都清掉
		 */
		CLEAR(7),

		;
		private final int value;

		private CultureType(int value) {
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
		 * 農場不存在
		 */
		FARM_NOT_EXIST(12),

		/**
		 * 已有農場
		 */
		ALREADY_HAD_FARM(13),

		/**
		 * 土地不存在
		 */
		LAND_NOT_EXIST(14),

		/**
		 * 已有土地
		 */
		ALREADY_HAD_LAND(15),

		/**
		 * 無法扣除土地
		 */
		CAN_NOT_DECREASE_LAND(16),

		/**
		 * 無法增加土地
		 */
		CAN_NOT_INCREASE_LAND(17),

		/**
		 * 種子不存在
		 */
		SEED_NOT_EXIST(18),

		/**
		 * 已有種子
		 */
		ALREADY_HAD_SEED(19),

		/**
		 * 無法扣除種子
		 */
		CAN_NOT_DECREASE_SEED(20),

		/**
		 * 農場滿了
		 */
		FARM_FULL(20),

		/**
		 * 耕種類別不存在
		 */
		CULTURE_TYPE_NOT_EXIST(21),

		/**
		 * 已經澆水了
		 */
		ALREADY_WATER(22),

		/**
		 * 已經祈禱了
		 */
		ALREADY_PRAY(23),

		/**
		 * 沒在成長中
		 */
		NOT_GROWING(24),

		/**
		 * 還沒成熟
		 */
		NOT_MATURE(25),

		/**
		 * 還沒枯萎
		 */
		NOT_WITHER(26),

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
	 * 改變所有的農場頁是否鎖定
	 * 
	 * @param sendable
	 * @param role
	 */
	boolean changeManorPenLocked(boolean sendable, Role role);

	/**
	 * 發送莊園欄位
	 * 
	 * @param role
	 * @param bagPen
	 * @return
	 */
	Message sendManorPen(Role role, ManorPen manorPen);

	/**
	 * 填充莊園欄位
	 * 
	 * @param message
	 * @param manorPen
	 * @return
	 */
	boolean fillManorPen(Message message, ManorPen manorPen);

	/**
	 * 發送農場頁
	 * 
	 * @param role
	 * @param farm
	 * @return
	 */
	Message sendFarm(Role role, Farm farm);

	/**
	 * 填充農場頁
	 * 
	 * @param message
	 * @param farm
	 * @return
	 */
	boolean fillFarm(Message message, Farm farm);

	/**
	 * 開墾結果
	 */
	interface ReclaimResult extends AppResult {
		/**
		 * 農場索引
		 * 
		 * @return
		 */
		int getFarmIndex();

		void setFarmIndex(int farmIndex);

		/**
		 * 土地
		 * 
		 * @return
		 */
		Land getLand();

		void setLand(Land land);

		/**
		 * 花費的金幣
		 * 
		 * @return
		 */
		long getSpendGold();

		void setSpendGold(long spendGold);

	}

	/**
	 * 開墾
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param landUniqueId
	 * @return
	 */
	ReclaimResult reclaim(boolean sendable, Role role, int farmIndex,
			String landUniqueId);

	/**
	 * 檢查開墾
	 * 
	 * @param role
	 * @param farmIndex
	 * @param landUniqueId
	 * @return
	 */
	ErrorType checkReclaim(Role role, int farmIndex, String landUniqueId);

	/**
	 * 發送開墾
	 * 
	 * @param errorType
	 * @param role
	 * @param farmIndex
	 * @param land
	 * @return
	 */
	Message sendReclaim(ErrorType errorType, Role role, int farmIndex, Land land);

	/**
	 * 休耕結果
	 */
	interface DisuseResult extends ReclaimResult {

	}

	/**
	 * 休耕
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 */
	DisuseResult disuse(boolean sendable, Role role, int farmIndex);

	/**
	 * 檢查休耕
	 * 
	 * @param role
	 * @param farmIndex
	 * @return
	 */
	ErrorType checkDisuse(Role role, int farmIndex);

	/**
	 * 發送休耕
	 * 
	 * @param errorType
	 * @param role
	 * @param farmIndex
	 * @return
	 */
	Message sendDisuse(ErrorType errorType, Role role, int farmIndex);

	/**
	 * 耕種結果
	 */
	interface CultureResult extends AppResult {
		/**
		 * 耕種類別
		 * 
		 * @return
		 */
		CultureType getCultureType();

		void setCultureType(CultureType cultureType);

		/**
		 * 農場索引
		 * 
		 * @return
		 */
		int getFarmIndex();

		void setFarmIndex(int farmIndex);

		/**
		 * 格子索引
		 * 
		 * @return
		 */
		int getGridIndex();

		void setGridIndex(int gridIndex);

		/**
		 * 種子
		 * 
		 * @return
		 */
		Seed getSeed();

		void setSeed(Seed seed);

		/**
		 * 剩餘毫秒
		 */
		long getResidualMills();

		void setResidualMills(long residualMills);

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
	 * 耕種
	 * 
	 * @param role
	 * @param cultureValue
	 *            耕種類別 @see CultureType
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 * @return
	 */
	CultureResult culture(boolean sendable, Role role, int cultureValue,
			int farmIndex, int gridIndex, String seedUniqueId);

	/**
	 * 計算種子成長剩餘毫秒
	 * 
	 * @param land
	 * @param seed
	 * @return
	 */
	long calcResidual(Land land, Seed seed);

	/**
	 * 計算種子產出數量
	 * 
	 * @param land
	 * @param seed
	 * @return
	 */
	Map<String, Integer> calcProducts(Land land, Seed seed);

	/**
	 * 種植
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 * @return
	 */
	CultureResult plant(boolean sendable, Role role, int farmIndex,
			int gridIndex, String seedUniqueId);

	/**
	 * 檢查種植
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @param seedUniqueId
	 * @return
	 */
	ErrorType checkPlant(Role role, int farmIndex, int gridIndex,
			String seedUniqueId);

	/**
	 * 澆水
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult water(boolean sendable, Role role, int farmIndex,
			int gridIndex);

	/**
	 * 檢查澆水
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkWater(Role role, int farmIndex, int gridIndex);

	/**
	 * 祈禱
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult pray(boolean sendable, Role role, int farmIndex, int gridIndex);

	/**
	 * 檢查祈禱
	 * 
	 * @param roleId
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkPray(Role role, int farmIndex, int gridIndex);

	/**
	 * 加速,直接成熟,直接收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult speed(boolean sendable, Role role, int farmIndex,
			int gridIndex);

	/**
	 * 檢查加速
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkSpeed(Role role, int farmIndex, int gridIndex);

	/**
	 * 收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult harvest(boolean sendable, Role role, int farmIndex,
			int gridIndex);

	/**
	 * 檢查收割
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkHarvest(Role role, int farmIndex, int gridIndex);

	/**
	 * 復活,把枯萎的復活,直接收割
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult revive(boolean sendable, Role role, int farmIndex,
			int gridIndex);

	/**
	 * 檢查復活
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkRevive(Role role, int farmIndex, int gridIndex);

	/**
	 * 清除
	 * 
	 * @param sendable
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	CultureResult clear(boolean sendable, Role role, int farmIndex,
			int gridIndex);

	/**
	 * 檢查清除
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType checkClear(Role role, int farmIndex, int gridIndex);

	/**
	 * 發送耕種
	 * 
	 * @param errorType
	 * @param role
	 * @param cultureResult
	 */
	Message sendCulture(ErrorType errorType, Role role, CultureResult cultureResult);

	interface SeedResult extends AppResult {
		/**
		 * 農場索引
		 * 
		 * @return
		 */
		int getFarmIndex();

		void setFarmIndex(int farmIndex);

		/**
		 * 格子索引
		 * 
		 * @return
		 */
		int getGridIndex();

		void setGridIndex(int gridIndex);

		/**
		 * 農場
		 * 
		 * @return
		 */
		Farm getFarm();

		void setFarm(Farm farm);

		/**
		 * 土地
		 * 
		 * @return
		 */
		Land getLand();

		void setLand(Land land);

		/**
		 * 種子
		 * 
		 * @return
		 */
		Seed getSeed();

		void setSeed(Seed seed);
	}

	/**
	 * 計算成長中的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcGrowings(ManorPen manorPen);

	/**
	 * 計算可以澆水的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcCanWaters(ManorPen manorPen);

	/**
	 * 計算可以祈禱的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcCanPrays(ManorPen manorPen);

	/**
	 * 計算可以加速的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcCanSpeeds(ManorPen manorPen);

	/**
	 * 計算可以收割的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcCanHarvests(ManorPen manorPen);

	/**
	 * 計算可以復活的種子
	 * 
	 * @param manorPen
	 * @return
	 */
	List<SeedResult> calcCanRevives(ManorPen manorPen);

	/**
	 * 耕種所有結果
	 */
	interface CultureAllResult extends AppResult {
		/**
		 * 耕種類別
		 * 
		 * @return
		 */
		CultureType getCultureType();

		void setCultureType(CultureType cultureType);

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

		/**
		 * 耕種結果
		 * 
		 * @return
		 */
		List<CultureResult> getCultureResults();

		void setCultureResults(List<CultureResult> cultureResults);
	}

	/**
	 * 耕種所有
	 * 
	 * @param sendable
	 * @param role
	 * @param cultureValue
	 *            耕種類別 @see CultureType
	 * @return
	 */
	CultureAllResult cultureAll(boolean sendable, Role role, int cultureValue);

	/**
	 * 澆水所有
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	CultureAllResult waterAll(boolean sendable, Role role);

	/**
	 * 祈禱所有
	 * 
	 * @param sendable
	 * @param role
	 * @return
	 */
	CultureAllResult prayAll(boolean sendable, Role role);

	/**
	 * 發送耕種所有
	 * 
	 * @param errorType
	 * @param role
	 * @param cultureResults
	 * @return
	 */
	Message sendCultureAll(ErrorType errorType, Role role,
			CultureAllResult cultureAllResult);

}
