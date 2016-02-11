package org.openyu.mix.flutter.po.useraype;

import java.sql.Types;

import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;

public class RaceTypeUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	public RaceTypeUserType() {
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
		return RaceType.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof RaceType) {
			RaceType src = (RaceType) value;
			StringBuilder dest = new StringBuilder();
			// vol
			dest.append(assembleVol(getVolType()));
			//
			dest.append(src.getValue());
			//
			result = (R) dest.toString();
		}
		return result;
	}

	/**
	 * 反欄位組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		RaceType result = null;
		//
		if (value instanceof String) {
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
		}
		return (R) result;
	}

	protected RaceType disassembleBy_1(StringBuilder src) {
		RaceType result = null;
		if (src != null) {
			result = EnumHelper.valueOf(RaceType.class,
					toObject(src.toString(), int.class));
		}
		return result;
	}
}
