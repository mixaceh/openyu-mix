package org.openyu.mix.flutter.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Race;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.RaceImpl;

public class RaceImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		Race race = new RaceImpl();
		race.setId(RaceType.RONG);
		AttributeGroup attributeGroup = race.getAttributeGroup();
		//
		Attribute attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_HEALTH);
		attribute.setPoint(800);
		attribute.setAddPoint(4);
		attribute.setAddRate(10);
		attributeGroup.addAttribute(attribute);
		//
		attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_MANA);
		attribute.setPoint(500);
		attribute.setAddPoint(2);
		attribute.setAddRate(10);
		attributeGroup.addAttribute(attribute);
		//
		String result = CollectorHelper.writeToXml(RaceImpl.class, race);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Race result = CollectorHelper.readFromXml(RaceImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
