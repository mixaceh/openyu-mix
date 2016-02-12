package org.openyu.mix.manor.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.mix.app.vo.AppInfo;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 莊園欄位資訊
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ManorPen extends AppInfo
{
	String KEY = ManorPen.class.getName();

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
		 * 超過農場頁數
		 */
		OVER_FARM_SIZE(11),

		/**
		 * 超過種子格子數量
		 */
		OVER_GRID_SIZE(12),

		/**
		 * 超過種子最大數量
		 */
		OVER_MAX_AMOUNT(13),

		/**
		 * 超過農場頁索引
		 */
		OVER_FARM_INDEX(14),

		/**
		 * 超過格子索引
		 */
		OVER_GRID_INDEX(15),

		/**
		 * 農場頁不存在
		 */
		FARM_NOT_EXIST(21),

		/**
		 * 農場頁被鎖定
		 */
		FARM_LOCKED(22),

		/**
		 * 至少有一個農場頁被鎖定
		 */
		AT_LEAST_ONE_FARM_LOCKED(23),

		/**
		 * 莊園已有農場頁
		 */
		ALREADY_HAS_FARM(24),

		/**
		 * 格子已有種子
		 */
		ALREADY_HAS_SEED(25),

		/**
		 * 莊園滿了
		 */
		MANOR_FULL(26),

		/**
		 * 莊園是空的
		 */
		MANOR_EMPTY(27),

		/**
		 * 農場頁滿了
		 */
		FARM_FULL(28),

		/**
		 * 農場頁是空的
		 */
		FARM_EMPTY(29),

		/**
		 * 種子不存在
		 */
		SEED_NOT_EXIST(30),

		/**
		 * 數量不合法
		 */
		AMOUNT_NOT_VALID(31),

		/**
		 * 數量不足
		 */
		AMOUNT_NOT_ENOUGH(32),

		//
		;

		private final int value;

		private ErrorType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 農場頁類型
	 */
	public enum FarmType implements IntEnum
	{
		/**
		 * 目前只開放0,1,2頁
		 */
		_0(0), _1(1), _2(2), // _3(3), _4(4), _5(5)

		;

		private final int value;

		private FarmType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 農場頁
	 * 
	 * <farmIndex, 農場>
	 * 
	 * @return
	 */
	Map<Integer, Farm> getFarms();

	void setFarms(Map<Integer, Farm> farms);

	/**
	 * 農場頁數,已解鎖農場頁數量,如:6頁
	 */
	int getFarmSize();

	/**
	 * 農場頁數,已解鎖農場頁數量,如:6頁
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	int getFarmSize(boolean ignoreLocked);

	/**
	 * 農場是否鎖定
	 * 
	 * @return
	 */
	boolean isLocked(int index);

	/**
	 * 鎖定農場頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType lock(int index);

	/**
	 * 解鎖農場頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType unLock(int index);

	/**
	 * 農場是否滿了
	 * 
	 * @return
	 */
	boolean isFull();

	/**
	 * 加入農場頁
	 * 
	 * @param index
	 * @param farm
	 * @return
	 */
	ErrorType addFarm(int index, Farm farm);

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @return
	 */
	Farm getFarm(int index);

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	Farm getFarm(int index, boolean ignoreLocked);

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param farmType
	 * @return
	 */
	Farm getFarm(FarmType farmType);

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param farmType
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	Farm getFarm(FarmType farmType, boolean ignoreLocked);

	/**
	 * 移除農場頁
	 * 
	 * @param index
	 * @return
	 */
	ErrorType removeFarm(int index);

	/**
	 * 移除農場頁
	 * 
	 * @param index
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeFarm(int index, boolean ignoreLocked);

	/**
	 * 清除所有農場頁
	 * 
	 * @return
	 */
	ErrorType clearFarm();

	/**
	 * 清除所有農場頁
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearFarm(boolean ignoreLocked);

	/**
	 * 設定農場頁,取代掉原先農場上的farm
	 * 
	 * @param index
	 * @param farm
	 * @return
	 */
	ErrorType setFarm(int index, Farm farm);

	/**
	 * 設定農場頁,取代掉原先農場上的farm
	 * 
	 * @param index
	 * @param farm
	 * @param ignoreLocked
	 * @return
	 */
	ErrorType setFarm(int index, Farm farm, boolean ignoreLocked);

	// --------------------------------------------------
	// 種子
	// --------------------------------------------------
	/**
	 * 增加種子,若格子已有種子,是無法加新種子進去
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 * @return
	 */
	ErrorType addSeed(int farmIndex, int gridIndex, Seed seed);

	/**
	 * 增加種子,若格子已有種子,是無法加新種子進去
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param seed
	 * @param ignoreLocked
	 * @return
	 */
	ErrorType addSeed(int tabIndex, int gridIndex, Seed seed, boolean ignoreLocked);

	/**
	 * 移除種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	ErrorType removeSeed(int farmIndex, int gridIndex);

	/**
	 * 移除種子
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeSeed(int tabIndex, int gridIndex, boolean ignoreLocked);

	/**
	 * 移除種子
	 * 
	 * @param uniqueId
	 * @return
	 */
	ErrorType removeSeed(String uniqueId);

	/**
	 * 移除種子
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType removeSeed(String uniqueId, boolean ignoreLocked);

	/**
	 * 清除指定農場頁內的種子
	 * 
	 * @param farmIndex
	 * @return
	 */
	ErrorType clearSeed(int farmIndex);

	/**
	 * 清除指定包包頁內的種子
	 * 
	 * @param tabIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearSeed(int tabIndex, boolean ignoreLocked);

	/**
	 * 清除所有農場頁內的種子
	 * 
	 * @return
	 */
	ErrorType clearSeed();

	/**
	 * 清除所有包包頁內的種子
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	ErrorType clearSeed(boolean ignoreLocked);

	/**
	 * 取得種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	Seed getSeed(int farmIndex, int gridIndex);

	/**
	 * 取得種子
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	Seed getSeed(int tabIndex, int gridIndex, boolean ignoreLocked);

	/**
	 * 取得種子,by uniqueId
	 * 
	 * @param uniqueId
	 * @return
	 */
	Seed getSeed(String uniqueId);

	/**
	 * 取得種子,by uniqueId
	 * 
	 * @param uniqueId
	 * @param ignoreLocked
	 * @return
	 */
	Seed getSeed(String uniqueId, boolean ignoreLocked);

	/**
	 * 取得種子,by id
	 * 
	 * @param id
	 * @return
	 */
	List<Seed> getSeeds(String id);

	/**
	 * 取得種子,by id
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	List<Seed> getSeeds(String id, boolean ignoreLocked);

	/**
	 * 取得所有種子
	 * 
	 * @return
	 */
	List<Seed> getSeeds();

	/**
	 * 取得所有種子
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	List<Seed> getSeeds(boolean ignoreLocked);

	/**
	 * 設定種子,取代掉原先index上的種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 * @return
	 */
	ErrorType setSeed(int farmIndex, int gridIndex, Seed seed);

	/**
	 * 設定種子,取代掉原先index上的種子
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param seed
	 * @param ignoreLocked
	 * @return
	 */
	ErrorType setSeed(int tabIndex, int gridIndex, Seed seed, boolean ignoreLocked);

	/**
	 * 農場頁是否有此索引
	 * 
	 * @param index
	 * @return
	 */
	boolean containIndex(int index);

	/**
	 * 取得農場索引
	 * 
	 * @return
	 */
	List<Integer> getFarmIndexs();

	/**
	 * 取得農場索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	List<Integer> getFarmIndexs(boolean ignoreLocked);

	/**
	 * 取得種子索引
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @return
	 */
	int[] getIndex(String uniqueId);

	/**
	 * 取得種子索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @param ignoreLocked
	 * @return
	 */
	int[] getIndex(String uniqueId, boolean ignoreLocked);

	/**
	 * 取得種子索引,by id
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @return
	 */
	List<int[]> getIndexs(String id);

	/**
	 * 取得種子索引,by id
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked
	 * @return
	 */
	List<int[]> getIndexs(String id, boolean ignoreLocked);

	/**
	 * 取得有種子的索引
	 * 
	 * @return
	 */
	List<int[]> getIndexs();

	/**
	 * 取得有種子的索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	List<int[]> getIndexs(boolean ignoreLocked);

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
	 * 取得一個空格子的index,無放入任何種子
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @return
	 */
	int[] getEmptyIndex();

	/**
	 * 取得一個空格子的index,無放入任何種子
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	int[] getEmptyIndex(boolean ignoreLocked);

	/**
	 * 農場頁
	 */
	@XmlJavaTypeAdapter(AnyTypeAdapter.class)
	public interface Farm extends BaseBean
	{
		String KEY = Farm.class.getName();

		/**
		 * 農場頁籤最大種子數量,如9=可播種9個種子
		 */
		int MAX_GRID_SIZE = 9;

		/**
		 * 頁籤id
		 * 
		 * @return
		 */
		int getId();

		void setId(int id);

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
		 * <gridIndex,種子>
		 * 
		 * @return
		 */
		Map<Integer, Seed> getSeeds();

		void setSeeds(Map<Integer, Seed> seeds);

		/**
		 * 已放種子的格子數量,如:20格
		 */
		int getSeedSize();

		/**
		 * 頁是否鎖定
		 * 
		 * @return
		 */
		boolean isLocked();

		void setLocked(boolean locked);

		/**
		 * 農場頁是否滿了
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
		 * 增加種子,若格子已有種子,是無法再加入進去
		 * 
		 * @param index
		 * @param seed
		 * @return
		 */
		ErrorType addSeed(int index, Seed seed);

		/**
		 * 增加種子,若格子已有種子,是無法加新種子進去
		 * 
		 * @param index
		 * @param seed
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType addSeed(int index, Seed seed, boolean ignoreLocked);

		/**
		 * 移除種子
		 * 
		 * @param index
		 * @return
		 */
		ErrorType removeSeed(int index);

		/**
		 * 移除種子
		 * 
		 * @param index
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType removeSeed(int index, boolean ignoreLocked);

		/**
		 * 移除種子
		 * 
		 * @param uniqueId
		 * @return
		 */
		ErrorType removeSeed(String uniqueId);

		/**
		 * 移除種子
		 * 
		 * @param uniqueId
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType removeSeed(String uniqueId, boolean ignoreLocked);

		/**
		 * 清除所有農場頁內的種子
		 * 
		 * @return
		 */
		ErrorType clearSeed();

		/**
		 * 清除所有農場頁內的種子
		 * 
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		ErrorType clearSeed(boolean ignoreLocked);

		/**
		 * 取得種子
		 * 
		 * @param index
		 * @return
		 */
		Seed getSeed(int index);

		/**
		 * 取得種子
		 * 
		 * @param index
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		Seed getSeed(int index, boolean ignoreLocked);

		/**
		 * 取得種子,by uniqueId
		 * 
		 * @param uniqueId
		 * @return
		 */
		Seed getSeed(String uniqueId);

		/**
		 * 取得種子,by uniqueId
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		Seed getSeed(String uniqueId, boolean ignoreLocked);

		/**
		 * 取得種子,by id
		 * 
		 * @param id
		 * @return
		 */
		List<Seed> getSeeds(String id);

		/**
		 * 取得種子,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		List<Seed> getSeeds(String id, boolean ignoreLocked);

		/**
		 * 設定種子,取代掉原先index上的種子
		 * 
		 * @param index
		 * @param seed
		 * @return
		 */
		ErrorType setSeed(int index, Seed seed);

		/**
		 * 設定種子,取代掉原先index上的種子
		 * 
		 * @param index
		 * @param seed
		 * @param ignoreLocked
		 * @return
		 */
		ErrorType setSeed(int index, Seed seed, boolean ignoreLocked);

		/**
		 * 格子是否有此索引
		 * 
		 * @param index
		 * @return
		 */
		boolean containIndex(int index);

		/**
		 * 取得種子索引
		 * 
		 * @param uniqueId
		 * @return
		 */
		int getIndex(String uniqueId);

		/**
		 * 取得種子索引
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		int getIndex(String uniqueId, boolean ignoreLocked);

		/**
		 * 取得種子索引,by id
		 * 
		 * @param id
		 * @return
		 */
		List<Integer> getIndexs(String id);

		/**
		 * 取得種子索引,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		List<Integer> getIndexs(String id, boolean ignoreLocked);

		/**
		 * 取得有種子的索引
		 * 
		 * @return
		 */
		List<Integer> getIndexs();

		/**
		 * 取得有種子的索引
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		List<Integer> getIndexs(boolean ignoreLocked);

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
		 * 取得一個空格子的index,無放入任何種子
		 * 
		 * @return
		 */
		int getEmptyIndex();

		/**
		 * 取得一個空格子的index,無放入任何種子
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		int getEmptyIndex(boolean ignoreLocked);

	}

}
