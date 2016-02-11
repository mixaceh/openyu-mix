package org.openyu.mix.item.vo;

/**
 * 防具
 */
public interface Armor extends Equipment
{
	String KEY = Armor.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "A_";
}
