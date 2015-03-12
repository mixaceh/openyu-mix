package org.openyu.mix.flutter.vo.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class AttributeImplTest extends BeanTestSupporter {

	@Test
	public void writeToXml() {
		Attribute value = new AttributeImpl();
		value.setId(AttributeType.MAX_HEALTH);
		value.setPoint(400);
		value.setAddPoint(4);
		value.setAddRate(10);

		String result = beanCollector.writeToXml(AttributeImpl.class, value);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void readFromXml() {
		Attribute result = beanCollector.readFromXml(AttributeImpl.class);
		System.out.println(result);
		assertNotNull(result);
	}

}
