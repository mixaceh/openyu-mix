package org.openyu.mix.role.vo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.impl.ArmorImpl;
import org.openyu.mix.item.vo.impl.WeaponImpl;
import org.openyu.mix.role.vo.EquipmentPen;

public class EquipmentPenImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		EquipmentPen equipmentPen = new EquipmentPenImpl();
		//
		String result = CollectorHelper.writeToXml(EquipmentPenImpl.class, equipmentPen);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		EquipmentPen result = CollectorHelper.readFromXml(EquipmentPenImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬包包頁,40個thing
	 * 
	 * @return
	 */
	public static EquipmentPen mockEquipmentPen() {
		EquipmentPen result = new EquipmentPenImpl();
		return result;
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addEquipment() {
		EquipmentPen equipmentPen = mockEquipmentPen();
		//
		EquipmentPen.ErrorType result = EquipmentPen.ErrorType.NO_ERROR;
		// 防具
		Armor armor = new ArmorImpl();
		armor.setItemType(ItemType.ARMOR);
		armor.setPositionType(PositionType.BODY_HEAD);// 頭部位
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = equipmentPen.addEquipment(armor);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentPen.ErrorType.NO_ERROR, result);
		//
		result = equipmentPen.addEquipment(null);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentPen.ErrorType.EQUIPMENT_NOT_EXIST, result);

		// 武器
		Weapon weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.LEFT_HAND);// 左手部位
		result = equipmentPen.addEquipment(weapon);
		System.out.println(result);
		System.out.println(equipmentPen.getWeapons());
		// 沒有錯誤
		assertEquals(EquipmentPen.ErrorType.NO_ERROR, result);
		//
		weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.PAIR_HAND);// 雙手部位
		result = equipmentPen.addEquipment(weapon);
		System.out.println(result);
		System.out.println(equipmentPen.getWeapons());
		// 沒有錯誤
		assertEquals(EquipmentPen.ErrorType.NO_ERROR, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeEquipment() {
		EquipmentPen equipmentPen = mockEquipmentPen();
		//
		EquipmentPen.ErrorType result = EquipmentPen.ErrorType.NO_ERROR;
		// 防具
		Armor armor = new ArmorImpl();
		armor.setItemType(ItemType.ARMOR);
		armor.setPositionType(PositionType.BODY_HEAD);// 頭部位
		equipmentPen.addEquipment(armor);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = equipmentPen.removeEquipment(armor);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentPen.ErrorType.NO_ERROR, result);
		//
		result = equipmentPen.removeEquipment(armor);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentPen.ErrorType.EQUIPMENT_NOT_EXIST, result);
		//
		result = equipmentPen.removeEquipment(null);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentPen.ErrorType.EQUIPMENT_NOT_EXIST, result);

		// 武器
		Weapon weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.LEFT_HAND);// 左手部位
		equipmentPen.addEquipment(weapon);

		//
		result = equipmentPen.removeEquipment(weapon);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentPen.ErrorType.NO_ERROR, result);
		//
		result = equipmentPen.removeEquipment(weapon);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentPen.ErrorType.EQUIPMENT_NOT_EXIST, result);

	}
}
