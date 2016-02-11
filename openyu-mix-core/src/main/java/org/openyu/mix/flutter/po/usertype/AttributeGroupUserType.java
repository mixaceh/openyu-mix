package org.openyu.mix.flutter.po.usertype;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class AttributeGroupUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	public AttributeGroupUserType() {
		// --------------------------------------------------
		// 最新版本,目前用1,若將來有新版本
		// 可用其他版號,如:VolType._2
		// --------------------------------------------------
		setVolType(VolType._1);
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public Class<?> returnedClass() {
		return Map.class;
	}

	// --------------------------------------------------
	// assemble
	// --------------------------------------------------
	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof AttributeGroup)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		AttributeGroup src = (AttributeGroup) value;
		// vol
		dest.append(assembleVol(getVolType()));
		// v1
		dest.append(assembleBy_1(src));
		//
		result = (R) dest.toString();
		return result;
	}

	/**
	 * v1 由物件組成欄位
	 * 
	 * @param value
	 * @return
	 */
	public String assembleBy_1(AttributeGroup src) {
		StringBuilder result = new StringBuilder();
		// size
		Map<AttributeType, Attribute> attributes = src.getAttributes();
		result.append((attributes != null ? attributes.size() : 0));// 0
		for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet())// 1
		{
			result.append(OBJECT_SPLITTER);
			// 屬性類別,key
			result.append(toString(entry.getKey()));// e0
			result.append(ENTRY_SPLITTER);
			// 屬性值
			result.append(toString(entry.getValue().getPoint()));// e1
			result.append(ENTRY_SPLITTER);
			// 強化所提升的屬性值
			result.append(toString(entry.getValue().getAddPoint()));// e2
			result.append(ENTRY_SPLITTER);
			// 強化所增加的比率值,萬分位(2000)
			result.append(toString(entry.getValue().getAddRate()));// e3
		}
		return result.toString();
	}

	// --------------------------------------------------
	// disassemble
	// --------------------------------------------------
	/**
	 * 由欄位反組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		// 預設傳回空物件,非null
		AttributeGroup result = new AttributeGroupImpl();
		//
		if (!(value instanceof String)) {
			return (R) result;
		}
		//
		StringBuilder src = new StringBuilder((String) value);
		int vol = disassembleVol(src);
		VolType volType = EnumHelper.valueOf(VolType.class, vol);
		//
		if (volType == null) {
			return (R) result;
		}
		// v1
		if (volType.getValue() >= 1) {
			result = disassembleBy_1(src);
		}

		// v2,有新增的欄位,則繼續塞
		if (volType.getValue() >= 2) {

		}
		return (R) result;
	}

	/**
	 * 由欄位反組成物件 v1
	 * 
	 * @param src
	 * @return
	 */
	public AttributeGroup disassembleBy_1(StringBuilder src) {
		AttributeGroup result = new AttributeGroupImpl();
		if (src == null) {
			return result;
		}
		//
		String[] values = StringUtils.splitPreserveAllTokens(src.toString(),
				OBJECT_SPLITTER);
		if (ArrayHelper.isEmpty(values)) {
			return result;
		}
		//
		int idx = 0;
		int size = toObject(values, idx++, int.class);// 0
		//
		for (int i = 0; i < size; i++)// 1
		{
			String eValue = ArrayHelper.get(values, idx++);
			String[] entryValues = StringUtils.splitPreserveAllTokens(eValue,
					ENTRY_SPLITTER);
			if (ArrayHelper.isEmpty(entryValues)) {
				continue;
			}
			int edx = 0;
			Attribute entry = new AttributeImpl();
			// 屬性類別
			int intValue = toObject(entryValues, edx++, int.class);
			AttributeType attributeType = EnumHelper.valueOf(
					AttributeType.class, intValue);
			entry.setId(attributeType);// e0
			// 屬性值
			entry.setPoint(toObject(entryValues, edx++, int.class));// e1
			// 增加(成長)的屬性值
			entry.setAddPoint(toObject(entryValues, edx++, int.class));// e2
			// 增加(成長)的比率值,萬分位(2000)
			entry.setAddRate(toObject(entryValues, edx++, int.class));// e3
			//
			result.getAttributes().put(entry.getId(), entry);
		}
		return result;
	}
}
