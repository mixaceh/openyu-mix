package org.openyu.mix.manor.po.usertype;

import java.sql.Types;

import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;

public class CultureTypeUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	public CultureTypeUserType() {
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
		return CultureType.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof CultureType) {
			CultureType src = (CultureType) value;
			StringBuilder dest = new StringBuilder();
			// vol
			dest.append(VOL_SPLITTER);
			dest.append(getVolType().getValue());
			dest.append(SPLITTER);

			//
			dest.append(src.getValue());
			//
			result = (R) dest.toString();
		}
		return result;
	}

	// --------------------------------------------------

	/**
	 * 反欄位組成物件
	 */
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		CultureType result = null;
		//
		if (value instanceof String) {
			StringBuilder src = new StringBuilder((String) value);
			int vol = disassembleVol(src);
			VolType volType = EnumHelper.valueOf(VolType.class, vol);
			//
			if (volType != null) {
				switch (volType) {
				case _1:
					result = disassembleBy_1(src);
					break;

				case _2:
					// do by other vol
					break;

				default:
					break;

				}
			}
		}
		return (R) result;
	}

	protected CultureType disassembleBy_1(StringBuilder src) {
		CultureType result = null;
		if (src != null) {
			result = EnumHelper.valueOf(CultureType.class,
					toObject(src.toString(), int.class));
		}
		return result;
	}
}
