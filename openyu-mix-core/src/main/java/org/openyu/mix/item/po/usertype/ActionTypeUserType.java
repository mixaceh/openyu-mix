package org.openyu.mix.item.po.usertype;

import java.sql.Types;

import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.util.concurrent.NullValueMap;
import org.openyu.commons.util.concurrent.impl.NullValueMapImpl;

/**
 * 道具操作類別
 * 
 * 實驗性質,加上cache處理,其它的enum先不動,只改這支玩玩
 */
public class ActionTypeUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	/**
	 * <1,Enum>
	 */
	private NullValueMap<String, Enum<?>> valueOfIntCache = new NullValueMapImpl<String, Enum<?>>();

	public ActionTypeUserType() {
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
		return ActionType.class;
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
		if (!(value instanceof ActionType)) {
			return result;
		}
		//
		ActionType src = (ActionType) value;
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
	public String assembleBy_1(ActionType src) {
		StringBuilder result = new StringBuilder();
		//
		result.append(src.getValue());
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
		ActionType result = null;
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

	public ActionType disassembleBy_1(StringBuilder src) {
		ActionType result = null;
		if (src == null) {
			return result;
		}
		//
		// result = EnumHelper.valueOf(ActionType.class,
		// toObject(src.toString(), int.class));
		result = intValueOf(ActionType.class, src.toString());
		return result;
	}

	// String -> int -> Enum
	@SuppressWarnings("unchecked")
	protected <T extends Enum<T>> T intValueOf(Class<T> enumType, String value) {
		T result = null;
		//
		try {
			valueOfIntCache.lockInterruptibly();
			try {
				String key = value;
				if (valueOfIntCache.isNotNullValue(key)) {
					result = (T) valueOfIntCache.get(key);
					if (result == null) {
						result = EnumHelper.valueOf(enumType,
								toObject(value, int.class));
						valueOfIntCache.put(key, result);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				valueOfIntCache.unlock();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
