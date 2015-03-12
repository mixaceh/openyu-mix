package org.openyu.mix.item.vo;

/**
 * 武器
 */
public interface Weapon extends Equipment {
	String KEY = Weapon.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "W_";

	/**
	 * 武器類別
	 * 
	 * @return
	 */
	WeaponType getWeaponType();

	void setWeaponType(WeaponType weaponType);

}
