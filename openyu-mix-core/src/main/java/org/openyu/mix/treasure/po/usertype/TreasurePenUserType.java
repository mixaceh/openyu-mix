package org.openyu.mix.treasure.po.usertype;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.impl.TreasurePenImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class TreasurePenUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private static transient TreasureUserType treasureUserType = new TreasureUserType();

	public TreasurePenUserType() {
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
		return TreasurePen.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof TreasurePen)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		TreasurePen src = (TreasurePen) value;
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
	public String assembleBy_1(TreasurePen src) {
		StringBuilder result = new StringBuilder();
		//
		result.append(toString(src.getRefreshTime()));// 0
		result.append(SPLITTER);
		//
		Map<Integer, Treasure> treasures = src.getTreasures();
		// 上架祕寶size
		result.append(treasures.size());// 1
		for (Map.Entry<Integer, Treasure> entry : treasures.entrySet()) {
			Integer index = entry.getKey();
			Treasure treasure = entry.getValue();
			result.append(SPLITTER);
			result.append(toString(index));// 1,index
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
		TreasurePen result = new TreasurePenImpl();
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

	protected TreasurePen disassembleBy_1(StringBuilder src) {
		TreasurePen result = new TreasurePenImpl();
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
		long refreshTime = toObject(values, idx++, long.class);// 0
		result.setRefreshTime(refreshTime);
		//
		int size = toObject(values, idx++, int.class);// 1
		for (int i = 0; i < size; i++) {
			int index = toObject(values, idx++, int.class);// 1,index
			//
			StringBuilder buff = new StringBuilder();
			buff.append(values[idx++]);
			// System.out.println(buff);
			if (buff.length() > 0) {
				Treasure treasure = treasureUserType.disassembleBy_1(buff);
				if (treasure != null) {
					result.getTreasures().put(index, treasure);
				}
			}
		}
		//
		return result;
	}
}
