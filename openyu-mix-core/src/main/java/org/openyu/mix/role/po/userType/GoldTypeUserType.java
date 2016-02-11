package org.openyu.mix.role.po.userType;

import java.sql.Types;

import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.userType.supporter.BaseUserTypeSupporter;

/**
 * 角色金幣增減的原因
 */
public class GoldTypeUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	public GoldTypeUserType() {
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
		return GoldType.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof GoldType) {
			GoldType src = (GoldType) value;
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
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		GoldType result = null;
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

	protected GoldType disassembleBy_1(StringBuilder src) {
		GoldType result = null;
		if (src != null) {
			result = EnumHelper.valueOf(GoldType.class,
					toObject(src.toString(), int.class));
		}
		return result;
	}
}
