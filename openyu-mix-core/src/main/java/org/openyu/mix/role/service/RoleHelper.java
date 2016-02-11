package org.openyu.mix.role.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.helper.ex.HelperException;
import org.openyu.commons.helper.supporter.BaseHelperSupporter;
import org.openyu.socklet.message.vo.Message;

public final class RoleHelper extends BaseHelperSupporter {

	private static final transient Logger LOGGER = LoggerFactory.getLogger(RoleHelper.class);

	private RoleHelper() {
		throw new HelperException(
				new StringBuilder().append(RoleHelper.class.getName()).append(" can not construct").toString());
	}

	/**
	 * 填充屬性
	 *
	 * @param message
	 * @param attribute
	 */
	public static void fillAttribute(Message message, Attribute attribute) {
		message.addInt(attribute.getId());
		message.addInt(attribute.getPoint());
		message.addInt(attribute.getFinal());
	}

	/**
	 * 填充屬性群
	 *
	 * @param message
	 * @param attributeGroup
	 */
	public static void fillAttributeGroup(Message message, AttributeGroup attributeGroup) {
		Map<AttributeType, Attribute> attributes = attributeGroup.getAttributes();
		message.addInt(attributes.size());
		for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
			fillAttribute(message, entry.getValue());
		}
	}
}
