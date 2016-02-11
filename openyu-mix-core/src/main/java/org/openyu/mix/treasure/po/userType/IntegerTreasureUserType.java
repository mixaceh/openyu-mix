package org.openyu.mix.treasure.po.userType;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.userType.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class IntegerTreasureUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private static transient TreasureUserType treasureUserType = new TreasureUserType();

	public IntegerTreasureUserType() {
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

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof Map)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		Map<Integer, Treasure> src = (Map<Integer, Treasure>) value;
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
	 */
	public String assembleBy_1(Map<Integer, Treasure> src) {
		StringBuilder result = new StringBuilder();
		//
		result.append(src.size());// 數量
		for (Map.Entry<Integer, Treasure> entry : src.entrySet()) {
			Integer index = entry.getKey();
			//
			Treasure treasure = entry.getValue();
			//
			result.append(SPLITTER);
			result.append(toString(index));// index
			result.append(SPLITTER);
			result.append(treasureUserType.assembleBy_1(treasure));
		}
		//
		return result.toString();
	}

	// --------------------------------------------------

	/**
	 * 由欄位反組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		Map<Integer, Treasure> result = new LinkedHashMap<Integer, Treasure>();
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

	// --------------------------------------------------

	protected Map<Integer, Treasure> disassembleBy_1(StringBuilder src) {
		Map<Integer, Treasure> result = new LinkedHashMap<Integer, Treasure>();
		if (src == null) {
			return result;
		}
		//
		String[] values = StringUtils.splitPreserveAllTokens(src.toString(),
				SPLITTER);
		if (ArrayHelper.isEmpty(values)) {
			return result;
		}
		//
		int idx = 0;
		int size = toObject(values, idx++, int.class);
		for (int i = 0; i < size; i++) {
			int index = toObject(values, idx++, int.class);// index
			//
			StringBuilder buff = new StringBuilder();
			buff.append(values[idx++]);
			// System.out.println(buff);
			if (buff.length() > 0) {
				Treasure treasure = treasureUserType.disassembleBy_1(buff);
				if (treasure != null) {
					result.put(index, treasure);
				}
			}
		}
		return result;
	}
}
