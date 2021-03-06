package org.openyu.mix.flutter.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Career;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.flutter.vo.impl.CareerImpl;

public class CareerImplTest extends BaseTestSupporter {

	@Test
	public void writeToXml() {
		Career value = new CareerImpl();
		value.setId(CareerType.WARRIOR_1);
		AttributeGroup attributeGroup = value.getAttributeGroup();
		//
		Attribute attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_HEALTH);
		attribute.setPoint(400);
		attribute.setAddPoint(4);
		attribute.setAddRate(10);
		attributeGroup.addAttribute(attribute);
		//
		attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_MANA);
		attribute.setPoint(200);
		attribute.setAddPoint(2);
		attribute.setAddRate(10);
		attributeGroup.addAttribute(attribute);
		//
		String result = CollectorHelper.writeToXml(CareerImpl.class, value);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Career result = CollectorHelper.readFromXml(CareerImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
