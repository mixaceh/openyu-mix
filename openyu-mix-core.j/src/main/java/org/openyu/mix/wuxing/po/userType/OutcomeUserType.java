package org.openyu.mix.wuxing.po.userType;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.wuxing.service.WuxingMachine;
import org.openyu.mix.wuxing.service.impl.WuxingMachineImpl;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.userType.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

/**
 * 開出的結果
 */
public class OutcomeUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -6499823897737328840L;

	private WuxingMachine wuxingMachine = WuxingMachineImpl.getInstance();

	public OutcomeUserType() {
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
		return Outcome.class;
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
		if (!(value instanceof Outcome)) {
			return result;
		}
		//
		Outcome src = (Outcome) value;
		StringBuilder dest = new StringBuilder();
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
	public String assembleBy_1(Outcome src) {

		StringBuilder result = new StringBuilder();
		// id
		result.append(toString(src.getId()));// 0
		result.append(SPLITTER);
		// banker id
		result.append(toString(src.getBankerId()));// 0
		result.append(SPLITTER);
		// player id
		result.append(toString(src.getPlayerId()));// 0
		//
		return result.toString();
	}

	// --------------------------------------------------
	// disassemble
	// --------------------------------------------------
	/**
	 * 反欄位組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		Outcome result = null;
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

	public Outcome disassembleBy_1(StringBuilder src) {
		Outcome result = null;
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
		// 玩的結果
		String outcomeId = toObject(values, idx++, String.class);
		String bankerId = toObject(values, idx++, String.class);
		String playerId = toObject(values, idx++, String.class);
		// 建構開出的結果
		result = wuxingMachine.createOutcome(outcomeId, bankerId, playerId);
		// 無法建構開出的結果就傳回null
		if (result == null) {
			return result;
		}
		//
		return result;
	}
}
