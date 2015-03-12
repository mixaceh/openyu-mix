package org.openyu.mix.sasang.po.userType;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.sasang.service.SasangMachine;
import org.openyu.mix.sasang.service.impl.SasangMachineImpl;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.sasang.vo.impl.SasangPenImpl;
import org.openyu.commons.entity.userType.StringIntegerUserType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.userType.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class SasangPenUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private SasangMachine sasangMachine = new SasangMachineImpl();

	private StringIntegerUserType stringIntegerUserType = new StringIntegerUserType();

	public SasangPenUserType() {
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
		return SasangPen.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof SasangPen) {
			StringBuilder dest = new StringBuilder();
			SasangPen src = (SasangPen) value;
			// vol
			dest.append(assembleVol(getVolType()));
			// v1
			dest.append(assembleBy_1(src));
			//
			result = (R) dest.toString();
		}
		return result;
	}

	/**
	 * v1 由物件組成欄位
	 */
	public String assembleBy_1(SasangPen src) {
		StringBuilder result = new StringBuilder();
		//
		// 玩的時間
		result.append(toString(src.getPlayTime()));// 0
		result.append(SPLITTER);
		// 每日已玩的次數
		result.append(toString(src.getDailyTimes()));// 1
		result.append(SPLITTER);
		// 累計已玩的次數
		result.append(toString(src.getAccuTimes()));// 2
		result.append(SPLITTER);

		// 結果
		Outcome outcome = src.getOutcome();
		// 最後玩的結果
		result.append(toString((outcome != null ? outcome.getId() : "")));// 3
		result.append(SPLITTER);

		// 中獎區
		result.append(stringIntegerUserType.assembleBy_1(src.getAwards()));
		//
		return result.toString();
	}

	// --------------------------------------------------

	/**
	 * 由欄位反組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		SasangPen result = new SasangPenImpl();
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

	protected SasangPen disassembleBy_1(StringBuilder src) {
		SasangPen result = new SasangPenImpl();
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
		// 玩的時間
		long playTime = toObject(values, idx++, long.class);
		result.setPlayTime(playTime);
		// 每日已玩的次數
		int dailyTimes = toObject(values, idx++, int.class);
		result.setDailyTimes(dailyTimes);
		// 累計每日已玩的次數
		int accuTimes = toObject(values, idx++, int.class);
		result.setAccuTimes(accuTimes);
		// 玩的結果
		String outcomeId = toObject(values, idx++, String.class);
		Outcome outcome = sasangMachine.createOutcome(outcomeId);
		result.setOutcome(outcome);
		// 中獎區
		StringBuilder buff = new StringBuilder();
		buff.append(values[idx++]);
		Map<String, Integer> awards = stringIntegerUserType
				.disassembleBy_1(buff);
		result.setAwards(awards);
		return result;
	}
}
