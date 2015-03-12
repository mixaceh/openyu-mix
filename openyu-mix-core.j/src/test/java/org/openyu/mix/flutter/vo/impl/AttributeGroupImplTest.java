package org.openyu.mix.flutter.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.commons.bean.BeanCollector;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class AttributeGroupImplTest extends BeanTestSupporter {

	@Test
	public void writeToXml() {
		AttributeGroup value = new AttributeGroupImpl();
		//
		Attribute attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_HEALTH);
		attribute.setPoint(410);
		attribute.setAddPoint(4);
		attribute.setAddRate(10);
		value.addAttribute(attribute);
		//
		attribute = new AttributeImpl();
		attribute.setId(AttributeType.MAX_MANA);
		attribute.setPoint(210);
		attribute.setAddPoint(2);
		attribute.setAddRate(5);
		value.addAttribute(attribute);
		//
		String result = beanCollector.writeToXml(AttributeGroupImpl.class,
				value);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		AttributeGroup result = beanCollector
				.readFromXml(AttributeGroupImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
