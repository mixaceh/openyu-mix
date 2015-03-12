package org.openyu.mix.item.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 物品
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Thing extends Item {
	String KEY = Thing.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "T_";

	/**
	 * 物品類別
	 * 
	 * @return
	 */
	ThingType getThingType();

	void setThingType(ThingType thingType);

}
