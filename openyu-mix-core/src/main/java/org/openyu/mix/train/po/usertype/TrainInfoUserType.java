package org.openyu.mix.train.po.usertype;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.train.vo.impl.TrainInfoImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class TrainInfoUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	public TrainInfoUserType() {
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
		return TrainInfo.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof TrainInfo)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		TrainInfo src = (TrainInfo) value;
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
	public String assembleBy_1(TrainInfo src) {
		StringBuilder result = new StringBuilder();
		//
		result.append(toString(src.getJoinTime()));
		result.append(SPLITTER);
		result.append(toString(src.getQuitTime()));
		result.append(SPLITTER);
		result.append(toString(src.getDailyMills()));
		result.append(SPLITTER);
		result.append(toString(src.getAccuMills()));
		result.append(SPLITTER);
		result.append(toString(src.getInspireTime()));
		//
		return result.toString();
	}

	// --------------------------------------------------

	/**
	 * 由欄位反組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		TrainInfo result = new TrainInfoImpl();
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

	protected TrainInfo disassembleBy_1(StringBuilder src) {
		TrainInfo result = new TrainInfoImpl();
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
		// 加入時間
		long joinTime = toObject(values, idx++, long.class);// 0
		result.setJoinTime(joinTime);
		// 離開時間
		long quitTime = toObject(values, idx++, long.class);// 1
		result.setQuitTime(quitTime);
		// 每天已訓練毫秒
		long dailyMills = toObject(values, idx++, long.class);// 2
		result.setDailyMills(dailyMills);
		// 累計每天已訓練毫秒
		long accuMills = toObject(values, idx++, long.class);// 3
		result.setAccuMills(accuMills);
		// 鼓舞時間
		long inspireTime = toObject(values, idx++, long.class);// 4
		result.setInspireTime(inspireTime);
		//
		return result;
	}
}
