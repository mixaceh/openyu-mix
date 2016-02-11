package org.openyu.mix.manor.vo.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppInfoSupporter;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.manor.vo.adapter.IntegerFarmXmlAdapter;
import org.openyu.mix.manor.vo.adapter.IntegerSeedXmlAdapter;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "manorPen")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManorPenImpl extends AppInfoSupporter implements ManorPen
{

	private static final long serialVersionUID = 1413819560547091412L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 農場
	 */
	@XmlJavaTypeAdapter(IntegerFarmXmlAdapter.class)
	private Map<Integer, Farm> farms = new LinkedHashMap<Integer, Farm>();

	public ManorPenImpl(Role role)
	{
		this.role = role;
		//建構農場頁
		FarmType[] farmTypes = ManorPen.FarmType.values();
		for (FarmType farmType : farmTypes)
		{
			//id=0,1,2
			int farmIndex = farmType.getValue();
			Farm farm = new FarmImpl(farmIndex);
			//只開放第1頁,其他頁鎖定
			if (farmType == FarmType._0)
			{
				farm.setLocked(false);
			}
			else
			{
				farm.setLocked(true);
			}
			addFarm(farmIndex, farm);
		}
	}

	public ManorPenImpl()
	{
		this(null);
	}

	public Map<Integer, Farm> getFarms()
	{
		return farms;
	}

	public void setFarms(Map<Integer, Farm> farms)
	{
		this.farms = farms;
	}

	/**
	 * 農場頁數,已解鎖農場頁數量,如:6頁
	 * 
	 * @return
	 */
	public int getFarmSize()
	{
		return getFarmSize(false);
	}

	/**
	 * 農場頁數,已解鎖農場頁數量,如:6頁
	 * 
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	public int getFarmSize(boolean ignoreLocked)
	{
		int result = 0;
		FarmType[] farmTypes = FarmType.values();
		for (FarmType farmType : farmTypes)
		{
			Farm farm = getFarm(farmType, ignoreLocked);
			if (farm != null)
			{
				result += 1;
			}
		}
		return result;
	}

	/**
	 * 頁是否鎖定
	 * 
	 * @return
	 */
	public boolean isLocked(int index)
	{
		boolean result = true;
		Farm farm = getFarm(index);
		if (farm != null)
		{
			result = farm.isLocked();
		}
		return result;
	}

	/**
	 * 鎖定農場頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType lock(int index)
	{
		//檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(index);
			if (farm == null)
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
			else
			{
				farm.setLocked(true);
			}
		}
		return errorType;
	}

	/**
	 * 解鎖農場頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType unLock(int index)
	{
		//檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(index);
			if (farm == null)
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
			else
			{
				farm.setLocked(false);
			}
		}
		return errorType;
	}

	/**
	 * 農場是否滿了
	 * 
	 * @return
	 */
	public boolean isFull()
	{
		boolean result = true;
		//
		FarmType[] farmTypes = FarmType.values();
		for (FarmType farmType : farmTypes)
		{
			Farm farm = farms.get(farmType.getValue());
			if (farm == null || !farm.isFull())
			{
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * 檢查是否超過農場頁索引
	 * 
	 * @param index
	 * @return
	 */
	protected ErrorType checkFarmIndex(int index)
	{
		ErrorType errorType = ErrorType.NO_ERROR;
		if (!containFarmType(index) || index < 0)
		{
			errorType = ErrorType.OVER_FARM_INDEX;
		}
		return errorType;
	}

	protected boolean containFarmType(int index)
	{
		boolean result = false;
		FarmType[] farmTypes = FarmType.values();
		for (FarmType entry : farmTypes)
		{
			if (entry.getValue() == index)
			{
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 加入農場頁
	 * 
	 * @param index
	 * @param farm
	 * @return
	 */
	public ErrorType addFarm(int index, Farm farm)
	{
		//檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			// 農場頁不存在
			if (farm == null)
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
			// 農場已有農場頁
			else if (containIndex(index))
			{
				errorType = ErrorType.ALREADY_HAS_FARM;
			}
			else
			{
				farms.put(index, farm);
			}
		}
		return errorType;
	}

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @return
	 */
	public Farm getFarm(int index)
	{
		return getFarm(index, false);
	}

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	public Farm getFarm(int index, boolean ignoreLocked)
	{
		Farm result = null;
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		//
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(index);
			if (farm != null)
			{
				//檢查農場頁是否被鎖定
				errorType = farm.checkLocked(ignoreLocked);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					result = farm;
				}
			}
			else
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
		}
		return result;
	}

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param farmType
	 * @return
	 */
	public Farm getFarm(FarmType farmType)
	{
		return getFarm(farmType.getValue());
	}

	/**
	 * 取得農場頁
	 * 
	 * 當農場頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param farmType
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	public Farm getFarm(FarmType farmType, boolean ignoreLocked)
	{
		return getFarm(farmType.getValue(), ignoreLocked);
	}

	/**
	 * 移除農場頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType removeFarm(int index)
	{
		return removeFarm(index, false);
	}

	/**
	 * 移除農場頁
	 * 
	 * @param index
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType removeFarm(int index, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(index);
			if (farm != null)
			{
				farms.remove(index);
			}
			else
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 清除所有農場頁
	 * 
	 * @return
	 */
	public ErrorType clearFarm()
	{
		return clearFarm(false);
	}

	/**
	 * 清除所有農場頁
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType clearFarm(boolean ignoreLocked)
	{
		ErrorType result = ErrorType.NO_ERROR;
		//欲清除的農場頁索引
		List<Integer> removeIndexs = new LinkedList<Integer>();
		for (Map.Entry<Integer, Farm> entry : farms.entrySet())
		{
			Integer index = entry.getKey();
			Farm farm = entry.getValue();
			ErrorType errorType = farm.checkLocked(ignoreLocked);
			//沒有被鎖定的包包頁
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				removeIndexs.add(index);
			}
			else
			{
				if (!ErrorType.AT_LEAST_ONE_FARM_LOCKED.equals(errorType))
				{
					result = ErrorType.AT_LEAST_ONE_FARM_LOCKED;
				}
			}
		}
		//
		for (Integer index : removeIndexs)
		{
			farms.remove(index);
		}
		return result;
	}

	/**
	 * 設定農場頁,取代掉原先農場上的farm
	 * 
	 * @param index
	 * @param farm
	 * @return
	 */
	public ErrorType setFarm(int index, Farm farm)
	{
		return setFarm(index, farm, false);
	}

	/**
	 * 設定農場頁,取代掉原先農場上的farm
	 * 
	 * @param index
	 * @param farm
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType setFarm(int index, Farm farm, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm existFarm = farms.get(index);
			if (existFarm != null)
			{
				//檢查包包頁是否被鎖定
				errorType = existFarm.checkLocked(ignoreLocked);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					farms.put(index, farm);
				}
			}
			else
			{
				farms.put(index, farm);
			}
		}
		return errorType;
	}

	/**
	 * 增加種子,若格子已有種子,是無法加新種子進去
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 * @return
	 */
	public ErrorType addSeed(int farmIndex, int gridIndex, Seed seed)
	{
		return addSeed(farmIndex, gridIndex, seed, false);
	}

	/**
	 * 增加種子,若格子已有種子,是無法加新種子進去
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType addSeed(int farmIndex, int gridIndex, Seed seed, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(farmIndex);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(farmIndex);
			if (farm != null)
			{
				errorType = farm.addSeed(gridIndex, seed, ignoreLocked);
			}
			else
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 移除種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType removeSeed(int farmIndex, int gridIndex)
	{
		return removeSeed(farmIndex, gridIndex, false);
	}

	/**
	 * 移除種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType removeSeed(int farmIndex, int gridIndex, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(farmIndex);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(farmIndex);
			if (farm != null)
			{
				errorType = farm.removeSeed(gridIndex, ignoreLocked);
			}
			else
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 移除種子
	 * 
	 * @param uniqueId
	 * @return
	 */
	public ErrorType removeSeed(String uniqueId)
	{
		return removeSeed(uniqueId, false);
	}

	/**
	 * 移除種子
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType removeSeed(String uniqueId, boolean ignoreLocked)
	{
		ErrorType errorType = ErrorType.NO_ERROR;
		int[] indexs = getIndex(uniqueId, true);//先不管farm是否被鎖定
		if (indexs != null)
		{
			errorType = removeSeed(indexs[0], indexs[1], ignoreLocked);
		}
		else
		{
			// 種子不存在
			errorType = ErrorType.SEED_NOT_EXIST;
		}
		return errorType;
	}

	/**
	 * 清除指定農場頁內的種子
	 * 
	 * @param farmIndex
	 * @return
	 */
	public ErrorType clearSeed(int farmIndex)
	{
		return clearSeed(farmIndex, false);
	}

	/**
	 * 清除指定農場頁內的種子
	 * 
	 * @param farmIndex
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType clearSeed(int farmIndex, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(farmIndex);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(farmIndex);
			if (farm != null)
			{
				errorType = farm.clearSeed(ignoreLocked);
			}
			else
			{
				errorType = ErrorType.FARM_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 清除所有農場頁內的種子
	 * 
	 * @return
	 */
	public ErrorType clearSeed()
	{
		return clearSeed(false);
	}

	/**
	 * 清除所有農場頁內的種子
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType clearSeed(boolean ignoreLocked)
	{
		ErrorType errorType = ErrorType.NO_ERROR;

		//農場頁
		for (Integer farmIndex : farms.keySet())
		{
			errorType = clearSeed(farmIndex, ignoreLocked);
			//
			if (!ErrorType.NO_ERROR.equals(errorType))
			{
				if (!ErrorType.FARM_EMPTY.equals(errorType))
				{
					return errorType;
				}
				else
				{
					errorType = ErrorType.NO_ERROR;
				}
			}
		}
		return errorType;
	}

	/**
	 * 取得種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @return
	 */
	public Seed getSeed(int farmIndex, int gridIndex)
	{
		return getSeed(farmIndex, gridIndex, false);
	}

	/**
	 * 取得種子
	 * 
	 * @param farmIndex
	 * @param gridIndex
	 * @param ignoreLock 是否忽略鎖定
	 * @return
	 */
	public Seed getSeed(int farmIndex, int gridIndex, boolean ignoreLocked)
	{
		Seed result = null;
		Farm farm = getFarm(farmIndex, ignoreLocked);
		if (farm != null)
		{
			result = farm.getSeed(gridIndex, ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得種子,by uniqueId
	 * 
	 * @param uniqueId
	 * @return
	 */
	public Seed getSeed(String uniqueId)
	{
		return getSeed(uniqueId, false);
	}

	/**
	 * 取得種子,by uniqueId
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public Seed getSeed(String uniqueId, boolean ignoreLocked)
	{
		Seed result = null;
		int[] indexs = getIndex(uniqueId, ignoreLocked);
		if (indexs != null)
		{
			result = getSeed(indexs[0], indexs[1], ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得種子,by id
	 * 
	 * @param id
	 * @return
	 */
	public List<Seed> getSeeds(String id)
	{
		return getSeeds(id, false);
	}

	/**
	 * 取得種子,by id
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public List<Seed> getSeeds(String id, boolean ignoreLocked)
	{
		List<Seed> result = new LinkedList<Seed>();
		for (Farm farm : farms.values())
		{
			List<Seed> seeds = farm.getSeeds(id, ignoreLocked);
			if (seeds.size() > 0)
			{
				result.addAll(seeds);
			}
		}
		return result;
	}

	/**
	 * 取得所有種子
	 * 
	 * @return
	 */
	public List<Seed> getSeeds()
	{
		return getSeeds(false);
	}

	/**
	 * 取得所有種子
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public List<Seed> getSeeds(boolean ignoreLocked)
	{
		List<Seed> result = new LinkedList<Seed>();
		for (Farm farm : farms.values())
		{
			if (ignoreLocked)
			{
				result.addAll(farm.getSeeds().values());
			}
			else
			{
				if (!farm.isLocked())
				{
					result.addAll(farm.getSeeds().values());
				}
			}
		}
		return result;
	}

	/**
	 * 設定種子,取代掉原先index上的種子
	 * 
	 * @param index
	 * @param seed
	 * @return
	 */
	public ErrorType setSeed(int farmIndex, int gridIndex, Seed seed)
	{
		return setSeed(farmIndex, gridIndex, seed, false);
	}

	/**
	 * 設定種子,取代掉原先index上的種子
	 * 
	 * @param index
	 * @param seed
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public ErrorType setSeed(int farmIndex, int gridIndex, Seed seed, boolean ignoreLocked)
	{
		// 檢查是否超過農場頁索引
		ErrorType errorType = checkFarmIndex(farmIndex);
		if (ErrorType.NO_ERROR.equals(errorType))
		{
			Farm farm = farms.get(farmIndex);
			if (farm != null)
			{
				errorType = farm.setSeed(gridIndex, seed, ignoreLocked);
			}
		}
		return errorType;
	}

	/**
	 * 農場頁是否有此索引
	 * 
	 * @param index
	 * @return
	 */
	public boolean containIndex(int index)
	{
		return farms.containsKey(index);
	}

	/**
	 * 取得農場索引
	 * 
	 * @return
	 */
	public List<Integer> getFarmIndexs()
	{
		return getFarmIndexs(false);
	}

	/**
	 * 取得農場索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	public List<Integer> getFarmIndexs(boolean ignoreLocked)
	{
		List<Integer> result = new LinkedList<Integer>();
		for (Farm farm : farms.values())
		{
			if (ignoreLocked || !farm.isLocked())
			{
				result.add(farm.getId());
			}
		}
		return result;
	}

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
	public int[] getIndex(String uniqueId)
	{
		return getIndex(uniqueId, false);
	}

	/**
	 * 取得種子索引
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public int[] getIndex(String uniqueId, boolean ignoreLocked)
	{
		int[] result = null;
		for (Farm farm : farms.values())
		{
			int gridIndex = farm.getIndex(uniqueId, ignoreLocked);
			if (gridIndex != -1)
			{
				result = new int[] { farm.getId(), gridIndex };
				break;
			}
		}
		return result;
	}

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
	public List<int[]> getIndexs(String id)
	{
		return getIndexs(id, false);
	}

	/**
	 * 取得種子索引,by id
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public List<int[]> getIndexs(String id, boolean ignoreLocked)
	{
		List<int[]> result = new LinkedList<int[]>();
		for (Farm farm : farms.values())
		{
			List<Integer> gridIndexs = farm.getIndexs(id, ignoreLocked);
			if (gridIndexs.size() > 0)
			{
				for (Integer gridIndex : gridIndexs)
				{
					int[] indexs = new int[] { farm.getId(), gridIndex };
					result.add(indexs);
				}
			}
		}
		return result;
	}

	/**
	 * 取得有種子的索引
	 * 
	 * @return
	 */
	public List<int[]> getIndexs()
	{
		return getIndexs(false);
	}

	/**
	 * 取得有種子的索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	public List<int[]> getIndexs(boolean ignoreLocked)
	{
		List<int[]> result = new LinkedList<int[]>();
		for (Farm farm : farms.values())
		{
			List<Integer> gridIndexs = farm.getIndexs(ignoreLocked);
			if (gridIndexs.size() > 0)
			{
				for (Integer gridIndex : gridIndexs)
				{
					int[] indexs = new int[] { farm.getId(), gridIndex };
					result.add(indexs);
				}
			}
		}
		return result;
	}

	/**
	 * 取得空格數量
	 * 
	 * @return
	 */
	public int getEmptySize()
	{
		return getEmptySize(false);
	}

	/**
	 * 取得空格數量
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public int getEmptySize(boolean ignoreLocked)
	{
		int result = 0;
		for (Farm farm : farms.values())
		{
			result += farm.getEmptySize(ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得一個空格子的index,無放入任何種子
	 * 
	 * @return
	 */
	public int[] getEmptyIndex()
	{
		return getEmptyIndex(false);
	}

	/**
	 * 取得一個空格子的index,無放入任何種子
	 * 
	 * [0]=farmIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param ignoreLocked 是否忽略鎖定
	 * @return
	 */
	public int[] getEmptyIndex(boolean ignoreLocked)
	{
		int[] result = null;
		for (Farm farm : farms.values())
		{
			int gridIndex = farm.getEmptyIndex(ignoreLocked);
			if (gridIndex != -1)
			{
				result = new int[] { farm.getId(), gridIndex };
				break;
			}
		}
		return result;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("farms", farms);
		return builder.toString();
	}

	public Object clone()
	{
		ManorPenImpl copy = null;
		copy = (ManorPenImpl) super.clone();
		copy.farms = clone(farms);
		//role不要clone,會造成loop
		return copy;
	}

	//--------------------------------------------------
	//jaxb
	//--------------------------------------------------
	@XmlRootElement(name = "farm")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class FarmImpl extends BaseBeanSupporter implements Farm
	{

		private static final long serialVersionUID = 8995262649934546499L;

		/**
		 * 頁籤id
		 */
		private int id;

		/**
		 * 是否鎖定
		 */
		private boolean locked = false;

		/**
		 * 土地
		 */
		@XmlElement(type = LandImpl.class)
		private Land land;

		/**
		 * 種子
		 * 
		 * <gridIndex,種子>
		 */
		@XmlJavaTypeAdapter(IntegerSeedXmlAdapter.class)
		private Map<Integer, Seed> seeds = new LinkedHashMap<Integer, Seed>();

		public FarmImpl(int id)
		{
			this.id = id;
		}

		public FarmImpl(FarmType farmType)
		{
			this(farmType.getValue());
		}

		public FarmImpl()
		{
			this(0);
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public Land getLand()
		{
			return land;
		}

		public void setLand(Land land)
		{
			this.land = land;
		}

		public Map<Integer, Seed> getSeeds()
		{
			return seeds;
		}

		public void setSeeds(Map<Integer, Seed> seeds)
		{
			this.seeds = seeds;
		}

		/**
		 * 已放種子的格子數量,如:20格
		 */
		public int getSeedSize()
		{
			int result = 0;
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked();
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (int i = 0; i < MAX_GRID_SIZE; i++)
				{
					Seed seed = seeds.get(i);
					if (seed != null)
					{
						result += 1;
					}
				}
			}
			return result;
		}

		/**
		 * 頁是否鎖定
		 * 
		 * @return
		 */
		public boolean isLocked()
		{
			return locked;
		}

		public void setLocked(boolean locked)
		{
			this.locked = locked;
		}

		/**
		 * 農場頁是否滿了
		 * 
		 * @return
		 */
		public boolean isFull()
		{
			boolean result = true;
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked();
			if (errorType == ErrorType.FARM_LOCKED)
			{
				result = true;
			}
			else
			{
				for (int i = 0; i < MAX_GRID_SIZE; i++)
				{
					Seed seed = seeds.get(i);
					if (seed == null)
					{
						result = false;
						break;
					}
				}
			}
			return result;
		}

		/**
		 * 檢查農場頁是否被鎖定
		 * 
		 * @return
		 */
		public ErrorType checkLocked()
		{
			return checkLocked(false);
		}

		/**
		 * 檢查農場頁是否被鎖定
		 * 
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		public ErrorType checkLocked(boolean ignoreLocked)
		{
			ErrorType errorType = ErrorType.NO_ERROR;
			if (locked && !ignoreLocked)
			{
				errorType = ErrorType.FARM_LOCKED;
			}
			return errorType;
		}

		/**
		 * 檢查是否超過格子索引
		 * 
		 * @param index
		 * @return
		 */
		protected ErrorType checkGridIndex(int index)
		{
			ErrorType errorType = ErrorType.NO_ERROR;
			if (index >= MAX_GRID_SIZE || index < 0)
			{
				errorType = ErrorType.OVER_GRID_INDEX;
			}
			return errorType;
		}

		/**
		 * 增加種子,若格子已有種子,是無法再加入進去
		 * 
		 * @param index
		 * @param seed
		 * @return
		 */
		public ErrorType addSeed(int index, Seed seed)
		{
			return addSeed(index, seed, false);
		}

		/**
		 * 增加種子,若格子已有種子,是無法加新種子進去
		 * 
		 * @param index
		 * @param seed
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		public ErrorType addSeed(int index, Seed seed, boolean ignoreLocked)
		{
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					// 種子不存在
					if (seed == null)
					{
						errorType = ErrorType.SEED_NOT_EXIST;
					}
					// 農場頁滿了
					else if (seeds.size() >= MAX_GRID_SIZE)
					{
						errorType = ErrorType.FARM_FULL;
					}
					// 格子已有種子
					else if (containIndex(index))
					{
						errorType = ErrorType.ALREADY_HAS_SEED;
					}
					else
					{
						//種植時間
						seed.setPlantTime(System.currentTimeMillis());
						//成熟類別
						seed.setMatureType(MatureType.GROWING);
						seeds.put(index, seed);
					}
				}
			}
			return errorType;
		}

		/**
		 * 移除種子
		 * 
		 * @param index
		 * @return
		 */
		public ErrorType removeSeed(int index)
		{
			return removeSeed(index, false);
		}

		/**
		 * 移除種子
		 * 
		 * @param index
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		public ErrorType removeSeed(int index, boolean ignoreLocked)
		{
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					Seed seed = seeds.get(index);
					// 種子不存在
					if (seed == null)
					{
						errorType = ErrorType.SEED_NOT_EXIST;
					}
					else
					{
						seeds.remove(index);
					}
				}
			}
			return errorType;
		}

		/**
		 * 移除種子
		 * 
		 * @param uniqueId
		 * @return
		 */
		public ErrorType removeSeed(String uniqueId)
		{
			return removeSeed(uniqueId, false);
		}

		/**
		 * 移除種子
		 * 
		 * @param uniqueId
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		public ErrorType removeSeed(String uniqueId, boolean ignoreLocked)
		{
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				int index = getIndex(uniqueId);
				if (index == -1)
				{
					errorType = ErrorType.SEED_NOT_EXIST;
				}
				else
				{
					errorType = removeSeed(index);
				}
			}
			return errorType;
		}

		/**
		 * 清除所有種子
		 * 
		 * @return
		 */
		public ErrorType clearSeed()
		{
			return clearSeed(false);
		}

		/**
		 * 清除所有種子
		 * 
		 * @param ignoreLocked 是否忽略鎖定
		 * @return
		 */
		public ErrorType clearSeed(boolean ignoreLocked)
		{
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				if (seeds.size() == 0)
				{
					errorType = ErrorType.FARM_EMPTY;
				}
				else
				{
					seeds.clear();
				}
			}
			return errorType;
		}

		/**
		 * 取得種子
		 * 
		 * @param index
		 * @return
		 */
		public Seed getSeed(int index)
		{
			return getSeed(index, false);
		}

		/**
		 * 取得種子
		 * 
		 * @param index
		 * @param ignoreLock 是否忽略鎖定
		 * @return
		 */
		public Seed getSeed(int index, boolean ignoreLocked)
		{
			Seed result = null;
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					result = seeds.get(index);
				}
			}
			return result;
		}

		/**
		 * 取得種子,by uniqueId
		 * 
		 * @param uniqueId
		 * @return
		 */
		public Seed getSeed(String uniqueId)
		{
			return getSeed(uniqueId, false);
		}

		/**
		 * 取得種子,by uniqueId
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		public Seed getSeed(String uniqueId, boolean ignoreLocked)
		{
			Seed result = null;
			int index = getIndex(uniqueId, ignoreLocked);
			if (index != -1)
			{
				result = getSeed(index, ignoreLocked);
			}
			return result;
		}

		/**
		 * 取得種子,by id
		 * 
		 * @param id
		 * @return
		 */
		public List<Seed> getSeeds(String id)
		{
			return getSeeds(id, false);
		}

		/**
		 * 取得種子,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public List<Seed> getSeeds(String id, boolean ignoreLocked)
		{
			List<Seed> result = new LinkedList<Seed>();
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (Seed seed : seeds.values())
				{
					if (seed.getId().equals(id))
					{
						result.add(seed);
					}
				}
			}
			return result;
		}

		/**
		 * 設定種子,取代掉原先index上的種子
		 * 
		 * @param index
		 * @param seed
		 * @return
		 */
		public ErrorType setSeed(int index, Seed seed)
		{
			return setSeed(index, seed, false);
		}

		/**
		 * 設定種子,取代掉原先index上的種子
		 * 
		 * @param index
		 * @param seed
		 * @param ignoreLocked
		 * @return
		 */
		public ErrorType setSeed(int index, Seed seed, boolean ignoreLocked)
		{
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType))
				{
					seeds.put(index, seed);
				}
			}
			return errorType;
		}

		/**
		 * 格子是否有此索引
		 * 
		 * @param index
		 * @return
		 */
		public boolean containIndex(int index)
		{
			return seeds.containsKey(index);
		}

		/**
		 * 取得種子索引
		 * 
		 * @param uniqueId
		 * @return
		 */
		public int getIndex(String uniqueId)
		{
			return getIndex(uniqueId, false);
		}

		/**
		 * 取得種子索引
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		public int getIndex(String uniqueId, boolean ignoreLocked)
		{
			int result = -1;//表示找不到此uniqueId的索引 = -1
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (Map.Entry<Integer, Seed> entry : seeds.entrySet())
				{
					Integer index = entry.getKey();
					Seed seed = entry.getValue();
					if (seed != null && seed.getUniqueId().equals(uniqueId))
					{
						result = index;
						break;
					}
				}
			}
			return result;
		}

		/**
		 * 取得種子索引,by id
		 * 
		 * @param id
		 * @return
		 */
		public List<Integer> getIndexs(String id)
		{
			return getIndexs(id, false);
		}

		/**
		 * 取得種子索引,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public List<Integer> getIndexs(String id, boolean ignoreLocked)
		{
			List<Integer> result = new LinkedList<Integer>();
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (Map.Entry<Integer, Seed> entry : seeds.entrySet())
				{
					Integer index = entry.getKey();
					Seed seed = entry.getValue();
					if (seed != null && seed.getId().equals(id))
					{
						result.add(index);
					}
				}
			}
			return result;
		}

		/**
		 * 取得有種子的索引
		 * 
		 * @return
		 */
		public List<Integer> getIndexs()
		{
			return getIndexs(false);
		}

		/**
		 * 取得有種子的索引
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		public List<Integer> getIndexs(boolean ignoreLocked)
		{
			List<Integer> result = new LinkedList<Integer>();
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (Map.Entry<Integer, Seed> entry : seeds.entrySet())
				{
					Integer index = entry.getKey();
					Seed seed = entry.getValue();
					if (seed != null)
					{
						result.add(index);
					}
				}
			}
			return result;
		}

		/**
		 * 取得空格數量
		 * 
		 * @return
		 */
		public int getEmptySize()
		{
			return getEmptySize(false);
		}

		/**
		 * 取得空格數量
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		public int getEmptySize(boolean ignoreLocked)
		{
			int result = 0;
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (int i = 0; i < MAX_GRID_SIZE; i++)
				{
					Seed seed = seeds.get(i);
					if (seed == null)
					{
						result += 1;
					}
				}
			}
			return result;
		}

		/**
		 * 取得一個空格子的index,無放入任何種子
		 * 
		 * @return
		 */
		public int getEmptyIndex()
		{
			return getEmptyIndex(false);
		}

		/**
		 * 取得一個空格子的index,無放入任何種子
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		public int getEmptyIndex(boolean ignoreLocked)
		{
			int result = -1;
			// 檢查農場頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType))
			{
				for (int i = 0; i < MAX_GRID_SIZE; i++)
				{
					Seed seed = seeds.get(i);
					if (seed == null)
					{
						result = i;
						break;
					}
				}
			}
			return result;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this);
			builder.appendSuper(super.toString());
			builder.append("id", id);
			builder.append("locked", locked);
			builder.append("land", (land != null ? land.getId() : null));
			builder.append("seeds", seeds);
			return builder.toString();
		}

		public boolean equals(Object object)
		{
			if (!(object instanceof FarmImpl))
			{
				return false;
			}
			if (this == object)
			{
				return true;
			}
			FarmImpl other = (FarmImpl) object;
			return new EqualsBuilder().append(id, other.getId()).isEquals();
		}

		public int hashCode()
		{
			return new HashCodeBuilder().append(id).toHashCode();
		}

		public Object clone()
		{
			FarmImpl copy = null;
			copy = (FarmImpl) super.clone();
			copy.land = clone(land);
			copy.seeds = clone(seeds);
			return copy;
		}
	}
}
