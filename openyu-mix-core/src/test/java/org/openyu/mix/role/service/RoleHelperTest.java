package org.openyu.mix.role.service;

import org.junit.Test;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;

public class RoleHelperTest extends RoleTestSupporter {

	@Test
	// 1000000 times: 1227 mills.
	// 1000000 times: 1228 mills.
	// 1000000 times: 1252 mills.
	public void fillAttribute() {
		Message result = null;
		//
		Role role = mockRole();
		Attribute attribute = role.getAttributeGroup().getAttribute(AttributeType.STRENGTH);
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT, null, role.getId());
			RoleHelper.fillAttribute(result, attribute);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}

	@Test
	// 1000000 times: 4782 mills.
	public void fillAttributeGroup() {
		Message result = null;
		//
		Role role = mockRole();
		AttributeGroup attributeGroup = role.getAttributeGroup();
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT, null, role.getId());
			RoleHelper.fillAttributeGroup(result, attributeGroup);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
	}
}
