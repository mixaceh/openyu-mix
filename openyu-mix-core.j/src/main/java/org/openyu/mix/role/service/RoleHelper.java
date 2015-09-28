package org.openyu.mix.role.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.helper.supporter.BaseHelperSupporter;
import org.openyu.socklet.message.vo.Message;

/**
 * The Class RoleHelper.
 */
public class RoleHelper extends BaseHelperSupporter {

	/** The Constant LOGGER. */
	private static final transient Logger LOGGER = LoggerFactory
			.getLogger(RoleHelper.class);

	/**
	 * Instantiates a new blank helper.
	 */
	private RoleHelper() {
		super();
		if (InstanceHolder.INSTANCE != null) {
			throw new UnsupportedOperationException("Can not construct.");
		}
	}

	/**
	 * The Class InstanceHolder.
	 */
	private static class InstanceHolder {

		/** The Constant INSTANCE. */
		private static final RoleHelper INSTANCE = new RoleHelper();
	}

	/**
	 * Gets the single instance of BlankHelper.
	 *
	 * @return single instance of BlankHelper
	 */
	public static RoleHelper getInstance() {
		return InstanceHolder.INSTANCE;
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
	public static void fillAttributeGroup(Message message,
			AttributeGroup attributeGroup) {
		Map<AttributeType, Attribute> attributes = attributeGroup
				.getAttributes();
		message.addInt(attributes.size());
		for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
			fillAttribute(message, entry.getValue());
		}
	}
}
