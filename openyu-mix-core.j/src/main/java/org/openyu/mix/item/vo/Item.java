package org.openyu.mix.item.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.IdNamesBean;
import org.openyu.commons.bean.NamesBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 道具
 * 
 * Item
 * 
 * +-Thing(ThingCollector) 物品
 * 
 * +-Seed(ManorCollector) 種子
 * 
 * +-Land(ManorCollector) 土地
 * 
 * +-Material(MaterialCollector) 材料
 * 
 * +-Equipment
 * 
 * +-+--Armor (ArmorCollector) 防具
 * 
 * +-+--Weapon (WeaponCollector) 武器
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Item extends IdNamesBean {
	String KEY = Item.class.getName();

	/**
	 * 唯一識別碼(玩家)
	 * 
	 * @return
	 */
	String getUniqueId();

	void setUniqueId(String uniqueId);

	/**
	 * 說明
	 * 
	 * @return
	 */
	String getDescription();

	void setDescription(String description);

	NamesBean getDescriptions();

	void setDescriptions(NamesBean descriptions);

	/**
	 * 道具類別
	 * 
	 * @return
	 */
	ItemType getItemType();

	void setItemType(ItemType itemType);

	/**
	 * 數量(玩家)
	 * 
	 * @return
	 */
	int getAmount();

	void setAmount(int amount);

	/**
	 * 最大堆疊數量,0=無限
	 * 
	 * @return
	 */
	int getMaxAmount();

	void setMaxAmount(int maxAmount);

	/**
	 * 金幣價格
	 * 
	 * @return
	 */
	long getPrice();

	void setPrice(long price);

	/**
	 * 儲值幣價格
	 * 
	 * @return
	 */
	int getCoin();

	void setCoin(int coin);

	/**
	 * 是否綁定(束缚)(不可交易)
	 * 
	 * @return
	 */
	boolean isTied();

	void setTied(boolean tied);

	/**
	 * 圖標id
	 * 
	 * @return
	 */
	String getIconId();

	void setIconId(String iconId);

}
