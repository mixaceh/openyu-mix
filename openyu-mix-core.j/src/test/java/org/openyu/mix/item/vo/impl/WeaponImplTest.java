package org.openyu.mix.item.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.SeriesType;
import org.openyu.mix.item.vo.WeaponType;
import org.openyu.mix.item.vo.impl.WeaponImpl;

public class WeaponImplTest extends BaseTestSupporter {
	@Test
	public void writeToXml() {
		// 武器
		Weapon weapon = new WeaponImpl("W_MARS_SWORD_E001");// W_系列_使用_等級
		weapon.setName("E 級單手劍");
		weapon.setItemType(ItemType.WEAPON);
		weapon.setSeriesType(SeriesType.MARS);
		weapon.setWeaponType(WeaponType.SWORD);
		weapon.setLevelType(LevelType.E);
		weapon.setPositionType(PositionType.RIGHT_HAND);
		AttributeGroup attributeGroup = weapon.getAttributeGroup();
		// 物攻
		AttributeType attributeType = AttributeType.PHYSICAL_ATTACK;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)率
		attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 物攻致命(暴擊)傷害率
		attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 物攻命中率
		attributeType = AttributeType.PHYSICAL_HIT_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(8000);
		attributeGroup.addAttribute(attribute);
		System.out.println(weapon.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 物攻速度
		attributeType = AttributeType.PHYSICAL_ATTACK_SPEED;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(50);
		attributeGroup.addAttribute(attribute);
		//
		String result = CollectorHelper.writeToXml(WeaponImpl.class, weapon);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Weapon result = CollectorHelper.readFromXml(WeaponImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
