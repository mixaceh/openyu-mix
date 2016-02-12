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
import org.openyu.mix.role.vo.EquipmentInfo;

public class EquipmentInfoImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		EquipmentInfo equipmentInfo = new EquipmentInfoImpl();
		//
		String result = CollectorHelper.writeToXml(EquipmentInfoImpl.class, equipmentInfo);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		EquipmentInfo result = CollectorHelper.readFromXml(EquipmentInfoImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

	/**
	 * 模擬包包頁,40個thing
	 * 
	 * @return
	 */
	public static EquipmentInfo mockEquipmentInfo() {
		EquipmentInfo result = new EquipmentInfoImpl();
		return result;
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void addEquipment() {
		EquipmentInfo equipmentInfo = mockEquipmentInfo();
		//
		EquipmentInfo.ErrorType result = EquipmentInfo.ErrorType.NO_ERROR;
		// 防具
		Armor armor = new ArmorImpl();
		armor.setItemType(ItemType.ARMOR);
		armor.setPositionType(PositionType.BODY_HEAD);// 頭部位
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = equipmentInfo.addEquipment(armor);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentInfo.ErrorType.NO_ERROR, result);
		//
		result = equipmentInfo.addEquipment(null);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentInfo.ErrorType.EQUIPMENT_NOT_EXIST, result);

		// 武器
		Weapon weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.LEFT_HAND);// 左手部位
		result = equipmentInfo.addEquipment(weapon);
		System.out.println(result);
		System.out.println(equipmentInfo.getWeapons());
		// 沒有錯誤
		assertEquals(EquipmentInfo.ErrorType.NO_ERROR, result);
		//
		weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.PAIR_HAND);// 雙手部位
		result = equipmentInfo.addEquipment(weapon);
		System.out.println(result);
		System.out.println(equipmentInfo.getWeapons());
		// 沒有錯誤
		assertEquals(EquipmentInfo.ErrorType.NO_ERROR, result);
	}

	@Test
	// 1000000 times: 19 mills.
	// 1000000 times: 17 mills.
	// 1000000 times: 18 mills.
	// verified
	public void removeEquipment() {
		EquipmentInfo equipmentInfo = mockEquipmentInfo();
		//
		EquipmentInfo.ErrorType result = EquipmentInfo.ErrorType.NO_ERROR;
		// 防具
		Armor armor = new ArmorImpl();
		armor.setItemType(ItemType.ARMOR);
		armor.setPositionType(PositionType.BODY_HEAD);// 頭部位
		equipmentInfo.addEquipment(armor);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = equipmentInfo.removeEquipment(armor);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentInfo.ErrorType.NO_ERROR, result);
		//
		result = equipmentInfo.removeEquipment(armor);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentInfo.ErrorType.EQUIPMENT_NOT_EXIST, result);
		//
		result = equipmentInfo.removeEquipment(null);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentInfo.ErrorType.EQUIPMENT_NOT_EXIST, result);

		// 武器
		Weapon weapon = new WeaponImpl();
		weapon.setItemType(ItemType.WEAPON);
		weapon.setPositionType(PositionType.LEFT_HAND);// 左手部位
		equipmentInfo.addEquipment(weapon);

		//
		result = equipmentInfo.removeEquipment(weapon);
		System.out.println(result);
		// 沒有錯誤
		assertEquals(EquipmentInfo.ErrorType.NO_ERROR, result);
		//
		result = equipmentInfo.removeEquipment(weapon);
		System.out.println(result);
		// 道具不存在
		assertEquals(EquipmentInfo.ErrorType.EQUIPMENT_NOT_EXIST, result);

	}
}
