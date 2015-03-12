package org.openyu.mix.manor.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.Item;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 種子
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Seed extends Item {
	String KEY = Seed.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "S_";

	/**
	 * 等級限制
	 * 
	 * @return
	 */
	int getLevelLimit();

	void setLevelLimit(int levelLimit);

	/**
	 * 成長毫秒,未足時間無法收割
	 * 
	 * @return
	 */
	long getGrowMills();

	void setGrowMills(long growMills);

	/**
	 * 種植時間(玩家)
	 * 
	 * @return
	 */
	long getPlantTime();

	void setPlantTime(long plantTime);

	/**
	 * 澆水時間(玩家)
	 * 
	 * @return
	 */
	long getWaterTime();

	void setWaterTime(long waterTime);

	/**
	 * 祈禱時間(玩家)
	 * 
	 * @return
	 */
	long getPrayTime();

	void setPrayTime(long prayTime);

	/**
	 * 收割毫秒,超過時間會枯萎
	 * 
	 * @return
	 */
	long getReapMills();

	void setReapMills(long reapMills);

	/**
	 * 成熟時間(玩家)
	 * 
	 * @return
	 */
	long getMatureTime();

	void setMatureTime(long matureTime);

	/**
	 * 成熟類別(玩家)
	 * 
	 * @return
	 */
	MatureType getMatureType();

	void setMatureType(MatureType matureType);

	/**
	 * 產量,產出
	 * 
	 * <道具id,數量>
	 */
	Map<String, Integer> getProducts();

	void setProducts(Map<String, Integer> products);
}
