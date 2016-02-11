package org.openyu.mix.item.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.WeaponType;
import org.openyu.mix.item.vo.supporter.EquipmentSupporter;
import org.openyu.mix.item.vo.ItemType;

@XmlRootElement(name = "weapon")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeaponImpl extends EquipmentSupporter implements Weapon {

	private static final long serialVersionUID = -8719551220374997732L;

	/**
	 * 武器類別
	 */
	private WeaponType weaponType;

	public WeaponImpl(String id) {
		setId(id);
		setItemType(ItemType.WEAPON);
	}

	public WeaponImpl() {
		this(null);
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("weaponType", weaponType);
		return builder.toString();
	}

	public Object clone() {
		WeaponImpl copy = null;
		copy = (WeaponImpl) super.clone();
		return copy;
	}

}
