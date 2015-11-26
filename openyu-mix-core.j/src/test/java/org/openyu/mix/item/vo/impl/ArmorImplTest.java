package org.openyu.mix.item.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.SeriesType;
import org.openyu.mix.item.vo.impl.ArmorImpl;

public class ArmorImplTest extends BaseTestSupporter {
	@Test
	public void writeToXml() {
		// 防具
		Armor armor = new ArmorImpl("A_MARS_BODY_HEAD_E001");// A_系列_部位_等級
		armor.setName("E 級頭盔");
		armor.setItemType(ItemType.ARMOR);
		armor.setSeriesType(SeriesType.MARS);
		armor.setPositionType(PositionType.BODY_HEAD);
		armor.setLevelType(LevelType.E);
		AttributeGroup attributeGroup = armor.getAttributeGroup();

		// 物防
		AttributeType attributeType = AttributeType.PHYSICAL_DEFENCE;
		Attribute attribute = new AttributeImpl(attributeType);
		attribute.setPoint(5);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));

		// 物防迴避率
		attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		attribute = new AttributeImpl(attributeType);
		attribute.setPoint(100);
		attributeGroup.addAttribute(attribute);
		System.out.println(armor.getName() + ", " + attributeType + ", "
				+ attributeGroup.getFinal(attributeType));
		//
		String result = CollectorHelper.writeToXml(ArmorImpl.class, armor);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Armor result = CollectorHelper.readFromXml(ArmorImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
