package org.openyu.mix.role.vo;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 包包欄位
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface BagPen extends BaseBean
{

	String KEY = BagPen.class.getName();

	// --------------------------------------------------
	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum
	{
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
		ROLE_NOT_EXIST(1),

		/**
		 * 超過包包頁數
		 */
		OVER_TAB_SIZE(11),

		/**
		 * 超過道具格子數量
		 */
		OVER_GRID_SIZE(12),

		/**
		 * 超過道具最大數量
		 */
		OVER_MAX_AMOUNT(13),

		/**
		 * 超過包包頁索引
		 */
		OVER_TAB_INDEX(14),

		/**
		 * 超過格子索引
		 */
		OVER_GRID_INDEX(15),

		/**
		 * 包包頁不存在
		 */
		TAB_NOT_EXIST(21),

		/**
		 * 包包頁被鎖定
		 */
		TAB_LOCKED(22),

		/**
		 * 至少有一個包包頁被鎖定
		 */
		AT_LEAST_ONE_TAB_LOCKED(23),

		/**
		 * 包包已有包包頁
		 */
		ALREADY_HAS_TAB(24),

		/**
		 * 格子已有道具
		 */
		ALREADY_HAS_ITEM(25),

		/**
		 * 包包滿了
		 */
		BAG_FULL(26),

		/**
		 * 包包是空的
		 */
		BAG_EMPTY(27),

		/**
		 * 包包頁滿了
		 */
		TAB_FULL(28),

		/**
		 * 包包頁是空的
		 */
		TAB_EMPTY(29),

		/**
		 * 道具不存在
		 */
		ITEM_NOT_EXIST(30),

		/**
		 * 數量不合法
		 */
		AMOUNT_NOT_VALID(31),

		/**
		 * 道具數量不足
		 */
		AMOUNT_NOT_ENOUGH(32),

		//
		;

		private final int intValue;

		private ErrorType(int intValue)
		{
			this.intValue = intValue;
		}

		public int getValue()
		{
			return intValue;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + intValue + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 包包頁類型
	 */
	public enum TabType implements IntEnum
	{
		/**
		 * 目前只開放0,1,2頁
		 */
		_0(0), _1(1), _2(2),

		/**
		 * 任務頁
		 */
		QUEST(10),

		;

		private final int intValue;

		private TabType(int intValue)
		{
			this.intValue = intValue;
		}

		public int getValue()
		{
			return intValue;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + intValue + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 包包頁
	 * 
	 * <tabIndex, 頁籤>
	 * 
	 * @return
	 */
	Map<Integer, Tab> getTabs();

	void setTabs(Map<Integer, Tab> tabs);

	/**
	 * 包包頁數,已解鎖包包頁數量,如:3頁
	 * 
	 * @return
	 */
	int getTabSize();

	/**
	 * 包包頁數,已解鎖包包頁數量,如:3頁
	 * 
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	int getTabSize(boolean ignoreLocked);

	/**
	 * 頁是否鎖定
	 * 
	 * @return
	 */
	boolean isLocked(int index);

	/**
	 * 鎖定包包頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType lock(int index);

	/**
	 * 解鎖包包頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType unLock(int index);

	/**
	 * 包包是否滿了
	 * 
	 * @return
	 */
	boolean isFull();

	/**
	 * 任務頁
	 * 
	 * @return
	 */
	Tab getQuestTab();

	void setQuestTab(Tab tab);

	/**
	 * 加入包包頁
	 * 
	 * @param index
	 * @param tab
	 * @return
	 */
	ErrorType addTab(int index, Tab tab);

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @return
	 */
	Tab getTab(int index);

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	Tab getTab(int index, boolean ignoreLocked);

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param tabType
	 * @return
	 */
	Tab getTab(TabType tabType);

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param tabType
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	Tab getTab(TabType tabType, boolean ignoreLocked);

	/**
	 * 移除包包頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType removeTab(int index);

	/**
	 * 移除包包頁
	 * 
	 * @param index
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeTab(int index, boolean ignoreLocked);

	/**
	 * 清除所有包包頁
	 * 
	 * @return
	 */
	ErrorType clearTab();

	/**
	 * 清除所有包包頁
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearTab(boolean ignoreLocked);

	/**
	 * 設定包包頁,取代掉原先包包上的tab
	 * 
	 * @param index
	 * @param tab
	 * @return
	 */
	ErrorType setTab(int index, Tab tab);

	/**
	 * 設定包包頁,取代掉原先包包上的tab
	 * 
	 * @param index
	 * @param tab
	 * @param ignoreLocked
	 * @return
	 */
	ErrorType setTab(int index, Tab tab, boolean ignoreLocked);

	// --------------------------------------------------
	// 道具
	// --------------------------------------------------
	/**
	 * 增加道具,若格子已有道具,是無法加新道具進去
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @return
	 */
	ErrorType addItem(int tabIndex, int gridIndex, Item item);

	/**
	 * 增加道具,若格子已有道具,是無法加新道具進去
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @param ignoreLocked
	 * @return
	 */
	ErrorType addItem(int tabIndex, int gridIndex, Item item, boolean ignoreLocked);

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	ErrorType increaseAmount(int tabIndex, int gridIndex, int amount);

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType increaseAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked);

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	ErrorType increaseAmount(String uniqueId, int amount);

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType increaseAmount(String uniqueId, int amount, boolean ignoreLocked);

	/**
	 * 移除道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType removeItem(int tabIndex, int gridIndex);

	/**
	 * 移除道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeItem(int tabIndex, int gridIndex, boolean ignoreLocked);

	/**
	 * 移除道具
	 * 
	 * @param uniqueId
	 * @return
	 */
	ErrorType removeItem(String uniqueId);

	/**
	 * 移除道具
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeItem(String uniqueId, boolean ignoreLocked);

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	ErrorType decreaseAmount(int tabIndex, int gridIndex, int amount);

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType decreaseAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked);

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	ErrorType decreaseAmount(String uniqueId, int amount);

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType decreaseAmount(String uniqueId, int amount, boolean ignoreLocked);

	/**
	 * 清除指定包包頁內的道具
	 * 
	 * @param tabIndex
	 * @return
	 */
	ErrorType clearItem(int tabIndex);

	/**
	 * 清除指定包包頁內的道具
	 * 
	 * @param tabIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearItem(int tabIndex, boolean ignoreLocked);

	/**
	 * 清除所有包包頁內的道具
	 * 
	 * @return
	 */
	ErrorType clearItem();

	/**
	 * 清除所有包包頁內的道具
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearItem(boolean ignoreLocked);

	/**
	 * 取得道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @return
	 */
	Item getItem(int tabIndex, int gridIndex);

	/**
	 * 取得道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	Item getItem(int tabIndex, int gridIndex, boolean ignoreLocked);

	/**
	 * 取得道具,by uniqueId
	 * 
	 * @param uniqueId
	 * @return
	 */
	Item getItem(String uniqueId);

	/**
	 * 取得道具,by uniqueId
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	Item getItem(String uniqueId, boolean ignoreLocked);

	/**
	 * 取得道具,by id
	 * 
	 * @param id
	 * @return
	 */
	List<Item> getItems(String id);

	/**
	 * 取得道具,by id
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	List<Item> getItems(String id, boolean ignoreLocked);

	/**
	 * 取得道具,by itemType
	 * 
	 * @param itemType
	 * @return
	 */
	List<Item> getItems(ItemType itemType);

	/**
	 * 取得道具,by itemType
	 * 
	 * @param itemType
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	List<Item> getItems(ItemType itemType, boolean ignoreLocked);

	/**
	 * 取得所有道具
	 * 
	 * @return
	 */
	List<Item> getItems();

	/**
	 * 取得所有道具
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	List<Item> getItems(boolean ignoreLocked);

	/**
	 * 設定道具,取代掉原先index上的道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @return
	 */
	ErrorType setItem(int tabIndex, int gridIndex, Item item);

	/**
	 * 設定道具,取代掉原先index上的道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType setItem(int tabIndex, int gridIndex, Item item, boolean ignoreLocked);

	/**
	 * 設定道具,取代掉原先index上的道具數量
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	ErrorType setItemAmount(int tabIndex, int gridIndex, int amount);

	/**
	 * 設定道具,取代掉原先index上的道具數量
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType setItemAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked);

	/**
	 * 包包頁是否有此索引
	 * 
	 * @param index
	 * @return
	 */
	boolean containIndex(int index);

	/**
	 * 取得包包頁索引
	 * 
	 * @return
	 */
	List<Integer> getTabIndexs();

	/**
	 * 取得包包頁索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	List<Integer> getTabIndexs(boolean ignoreLocked);

	/**
	 * 取得道具索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @return
	 */
	int[] getIndex(String uniqueId);

	/**
	 * 取得道具索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	int[] getIndex(String uniqueId, boolean ignoreLocked);

	/**
	 * 取得道具索引,by id
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @return
	 */
	List<int[]> getIndexs(String id);

	/**
	 * 取得道具索引,by id
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	List<int[]> getIndexs(String id, boolean ignoreLocked);

	/**
	 * 取得道具總計數量,by id
	 * 
	 * @param id
	 * @return
	 */
	int getAmount(String id);

	/**
	 * 取得道具總計數量,by id
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	int getAmount(String id, boolean ignoreLocked);

	/**
	 * 取得空格數量
	 * 
	 * @return
	 */
	int getEmptySize();

	/**
	 * 取得空格數量
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	int getEmptySize(boolean ignoreLocked);

	/**
	 * 取得一個空格子的index,無放入任何道具
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @return
	 */
	int[] getEmptyIndex();

	/**
	 * 取得一個空格子的index,無放入任何道具
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	int[] getEmptyIndex(boolean ignoreLocked);

	/**
	 * 取得還能放道具的索引
	 * 
	 * 已有道具,且數量未滿的格子索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @return
	 */
	int[] getPutIndex(String id);

	/**
	 * 取得還能放道具的索引
	 * 
	 * 已有道具,且數量未滿的格子索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * 
	 * @return
	 */
	int[] getPutIndex(String id, boolean ignoreLocked);

	// --------------------------------------------------
	// 包包頁
	// --------------------------------------------------
	@XmlJavaTypeAdapter(AnyTypeAdapter.class)
	public interface Tab extends BaseBean
	{
		/**
		 * 包包頁最大道具格子數量,如40=可放40種道具
		 */
		int MAX_GRID_SIZE = 40;

		/**
		 * 任務道具頁最大道具格子數量,如60=可放60種任務道具
		 */
		int MAX_QUEST_GRID_SIZE = 40;

		/**
		 * 頁籤id
		 * 
		 * @return
		 */
		int getId();

		void setId(int id);

		/**
		 * 道具
		 * 
		 * <index,道具>
		 * 
		 * @return
		 */
		Map<Integer, Item> getItems();

		void setItems(Map<Integer, Item> items);

		/**
		 * 已放道具的格子數量,如:20格
		 */
		int getItemSize();

		// --------------------------------------------------

		/**
		 * 是否鎖定
		 * 
		 * @return
		 */
		boolean isLocked();

		void setLocked(boolean locked);

		/**
		 * 包包頁是否滿了
		 * 
		 * @return
		 */
		boolean isFull();

		/**
		 * 檢查包包頁是否被鎖定
		 * 
		 * @return
		 */
		ErrorType checkLocked();

		/**
		 * 檢查包包頁是否被鎖定
		 * 
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType checkLocked(boolean ignoreLocked);

		/**
		 * 增加道具,若格子已有道具,是無法再加入進去
		 * 
		 * @param index
		 * @param item
		 * @return
		 */
		ErrorType addItem(int index, Item item);

		/**
		 * 增加道具,若格子已有道具,是無法加新道具進去
		 * 
		 * @param index
		 * @param item
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType addItem(int index, Item item, boolean ignoreLocked);

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		ErrorType increaseAmount(int index, int amount);

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType increaseAmount(int index, int amount, boolean ignoreLocked);

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @return
		 */
		ErrorType increaseAmount(String uniqueId, int amount);

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType increaseAmount(String uniqueId, int amount, boolean ignoreLocked);

		/**
		 * 移除道具
		 * 
		 * @param index
		 * @return
		 */
		ErrorType removeItem(int index);

		/**
		 * 移除道具
		 * 
		 * @param index
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType removeItem(int index, boolean ignoreLocked);

		/**
		 * 移除道具
		 * 
		 * @param uniqueId
		 * @return
		 */
		ErrorType removeItem(String uniqueId);

		/**
		 * 移除道具
		 * 
		 * @param uniqueId
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType removeItem(String uniqueId, boolean ignoreLocked);

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		ErrorType decreaseAmount(int index, int amount);

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType decreaseAmount(int index, int amount, boolean ignoreLocked);

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @return
		 */
		ErrorType decreaseAmount(String uniqueId, int amount);

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType decreaseAmount(String uniqueId, int amount, boolean ignoreLocked);

		/**
		 * 清除所有道具
		 * 
		 * @return
		 */
		ErrorType clearItem();

		/**
		 * 清除所有道具
		 * 
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType clearItem(boolean ignoreLocked);

		/**
		 * 取得道具
		 * 
		 * @param index
		 * @return
		 */
		Item getItem(int index);

		/**
		 * 取得道具
		 * 
		 * @param index
		 * @param ignoreLock 是否忽略鎖定
		 * @return
		 */
		Item getItem(int index, boolean ignoreLocked);

		/**
		 * 取得道具,by uniqueId
		 * 
		 * @param uniqueId
		 * @return
		 */
		Item getItem(String uniqueId);

		/**
		 * 取得道具,by uniqueId
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		Item getItem(String uniqueId, boolean ignoreLocked);

		/**
		 * 取得道具,by id
		 * 
		 * @param id
		 * @return
		 */
		List<Item> getItems(String id);

		/**
		 * 取得道具,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		List<Item> getItems(String id, boolean ignoreLocked);

		/**
		 * 取得道具,by itemType
		 * 
		 * @param itemType
		 * @return
		 */
		List<Item> getItems(ItemType itemType);

		/**
		 * 取得道具,by itemType
		 * 
		 * @param itemType
		 * @param ignoreLocked
		 * @return
		 */
		List<Item> getItems(ItemType itemType, boolean ignoreLocked);

		/**
		 * 設定道具,取代掉原先index上的道具
		 * 
		 * @param index
		 * @param item
		 * @return
		 */
		ErrorType setItem(int index, Item item);

		/**
		 * 設定道具,取代掉原先index上的道具
		 * 
		 * @param index
		 * @param item
		 * @param ignoreLocked
		 * @return
		 */
		ErrorType setItem(int index, Item item, boolean ignoreLocked);

		/**
		 * 設定道具,取代掉原先index上的道具數量
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		ErrorType setItemAmount(int index, int amount);

		/**
		 * 設定道具,取代掉原先index上的道具數量
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked
		 * @return
		 */
		ErrorType setItemAmount(int index, int amount, boolean ignoreLocked);

		/**
		 * 格子是否有此索引
		 * 
		 * @param index
		 * @return
		 */
		boolean containIndex(int index);

		/**
		 * 取得道具索引
		 * 
		 * @param uniqueId
		 * @return
		 */
		int getIndex(String uniqueId);

		/**
		 * 取得道具索引
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		int getIndex(String uniqueId, boolean ignoreLocked);

		/**
		 * 取得道具索引,by id
		 * 
		 * @param id
		 * @return
		 */
		List<Integer> getIndexs(String id);

		/**
		 * 取得道具索引,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		List<Integer> getIndexs(String id, boolean ignoreLocked);

		/**
		 * 取得道具總計數量,by id
		 * 
		 * @param id
		 * @return
		 */
		int getAmount(String id);

		/**
		 * 取得道具總計數量,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		int getAmount(String id, boolean ignoreLocked);

		/**
		 * 取得空格數量
		 * 
		 * @return
		 */
		int getEmptySize();

		/**
		 * 取得空格數量
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		int getEmptySize(boolean ignoreLocked);

		/**
		 * 取得一個空格子的index,無放入任何道具
		 * 
		 * @return
		 */
		int getEmptyIndex();

		/**
		 * 取得一個空格子的index,無放入任何道具
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		int getEmptyIndex(boolean ignoreLocked);

		/**
		 * 取得還能放道具的索引
		 * 
		 * 已有道具,且數量未滿的格子索引
		 * 
		 * @param id
		 * @return
		 */
		int getPutIndex(String id);

		/**
		 * 取得還能放道具的索引
		 * 
		 * 已有道具,且數量未滿的格子索引
		 * 
		 * @param id
		 * @param ignoreLocked
		 * 
		 * @return
		 */
		int getPutIndex(String id, boolean ignoreLocked);

	}
}
