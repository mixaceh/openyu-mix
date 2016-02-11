package org.openyu.mix.role.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.role.vo.EquipmentPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.adapter.PositionTypeArmorXmlAdapter;
import org.openyu.mix.role.vo.adapter.PositionTypeWeaponXmlAdapter;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "equipmentPen")
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipmentPenImpl extends BaseBeanSupporter implements EquipmentPen {

	private static final long serialVersionUID = 6755055591625269928L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 防具格子
	 */
	@XmlJavaTypeAdapter(PositionTypeArmorXmlAdapter.class)
	private Map<PositionType, Armor> armors = new LinkedHashMap<PositionType, Armor>();

	/**
	 * 武器格子
	 */
	@XmlJavaTypeAdapter(PositionTypeWeaponXmlAdapter.class)
	private Map<PositionType, Weapon> weapons = new LinkedHashMap<PositionType, Weapon>();

	public EquipmentPenImpl(Role role) {
		this.role = role;
	}

	public EquipmentPenImpl() {
		this(null);
	}

	public Map<PositionType, Armor> getArmors() {
		return armors;
	}

	public void setArmors(Map<PositionType, Armor> armors) {
		this.armors = armors;
	}

	public Map<PositionType, Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(Map<PositionType, Weapon> weapons) {
		this.weapons = weapons;
	}

	// --------------------------------------------------

	/**
	 * 增加裝備,若已有此道具,會置換掉
	 * 
	 * @param equipment
	 * @return
	 */
	public ErrorType addEquipment(Equipment equipment) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		if (equipment == null) {
			errorType = ErrorType.EQUIPMENT_NOT_EXIST;
		} else {
			ItemType itemType = equipment.getItemType();
			switch (itemType) {
			// 防具
			case ARMOR: {
				Armor armor = (Armor) equipment;
				PositionType positionType = armor.getPositionType();
				// 直接取代裝備
				armors.put(positionType, armor);
				break;
			}
			// 武器
			case WEAPON: {
				Weapon weapon = (Weapon) equipment;
				PositionType positionType = weapon.getPositionType();
				// 當換成雙手武器時,卸下左右手武器
				if (PositionType.PAIR_HAND.equals(positionType)) {
					weapons.clear();
				}
				weapons.put(positionType, weapon);
				break;
			}
			default: {
				break;
			}
			}
		}
		return errorType;
	}

	/**
	 * 移除裝備
	 * 
	 * @param equipment
	 * @return
	 */
	public ErrorType removeEquipment(Equipment equipment) {
		ErrorType errorType = ErrorType.NO_ERROR;
		//
		if (equipment == null || !containEquipment(equipment)) {
			errorType = ErrorType.EQUIPMENT_NOT_EXIST;
		} else {
			ItemType itemType = equipment.getItemType();
			switch (itemType) {
			// 防具
			case ARMOR: {
				Armor armor = (Armor) equipment;
				PositionType positionType = armor.getPositionType();
				armors.remove(positionType);
				break;
			}
			// 武器
			case WEAPON: {
				Weapon weapon = (Weapon) equipment;
				PositionType positionType = weapon.getPositionType();
				weapons.remove(positionType);
				break;
			}
			default: {
				break;
			}
			}
		}
		return errorType;
	}

	public Boolean containEquipment(Equipment equipment) {
		Boolean result = false;
		if (equipment != null) {
			ItemType itemType = equipment.getItemType();
			switch (itemType) {
			// 防具
			case ARMOR: {
				Armor armor = (Armor) equipment;
				PositionType positionType = armor.getPositionType();
				result = armors.containsKey(positionType);
				break;
			}
			// 武器
			case WEAPON: {
				Weapon weapon = (Weapon) equipment;
				PositionType positionType = weapon.getPositionType();
				result = weapons.containsKey(positionType);
				break;
			}
			default: {
				break;
			}
			}
		}
		return result;
	}

}
