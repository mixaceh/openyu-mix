package org.openyu.mix.flutter.po.userType;

import java.sql.Types;

import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;

public class IndustryUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private static IndustryCollector industryCollector = IndustryCollector
			.getInstance();

	public IndustryUserType() {
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
		return Industry.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof Industry) {
			Industry src = (Industry) value;
			StringBuilder dest = new StringBuilder();
			// vol
			dest.append(assembleVol(getVolType()));
			//
			dest.append(src.getId());
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
		Industry result = null;
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

	protected Industry disassembleBy_1(StringBuilder src) {
		Industry result = null;
		if (src != null) {
			result = industryCollector.getIndustry(src.toString());
		}
		return result;
	}
}
