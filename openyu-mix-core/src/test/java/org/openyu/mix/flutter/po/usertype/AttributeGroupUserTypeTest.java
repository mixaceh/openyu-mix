package org.openyu.mix.flutter.po.usertype;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openyu.mix.flutter.po.usertype.AttributeGroupUserType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class AttributeGroupUserTypeTest extends BaseTestSupporter {

	private static AttributeGroupUserType userType = new AttributeGroupUserType();

	@Test
	// 1000000 times: 1962 mills.
	// 1000000 times: 1861 mills.
	// 1000000 times: 2022 mills.
	// verified
	public void marshal() {
		AttributeGroup value = new AttributeGroupImpl();
		Attribute attribute = new AttributeImpl(AttributeType.MAX_HEALTH);
		attribute.setPoint(400);
		attribute.setAddPoint(4);
		attribute.setAddRate(10);
		value.addAttribute(attribute);
		//
		attribute = new AttributeImpl(AttributeType.MAX_MANA);
		attribute.setPoint(200);
		attribute.setAddPoint(2);
		attribute.setAddRate(10);
		value.addAttribute(attribute);
		//
		String result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("♥1♠2♦12♣400♣4♣10♦14♣200♣2♣10", result);
		//
		result = userType.assembleBy_1(value);
		System.out.println(result);
		assertEquals("2♦12♣400♣4♣10♦14♣200♣2♣10", result);
	}

	@Test
	// 1000000 times: 11101 mills.
	// 1000000 times: 11324 mills.
	// 1000000 times: 11771 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠2♦12♣400♣4♣10♦14♣200♣2♣10";
		//
		AttributeGroup result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(2, result.getAttributes().size());
	}
}
