package org.openyu.mix.treasure.po.usertype;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.treasure.service.impl.TreasureServiceImpl;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

/**
 * 祕寶
 */
public class TreasureUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -6499823897737328840L;

	private static TreasureService treasureService;

	static {
		treasureService = new TreasureServiceImpl();
	}

	public TreasureUserType() {
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
		return Treasure.class;
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
		if (!(value instanceof Treasure)) {
			return result;
		}
		//
		Treasure src = (Treasure) value;
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
	 * @param src
	 * @return
	 */
	public String assembleBy_1(Treasure src) {
		/**
		 * @see org.openyu.mix.treasure.service.impl.TreasureServiceImpl.fillTreasure
		 */
		StringBuilder result = new StringBuilder();
		// 祕寶id
		result.append(toString(src.getId()));// 0
		result.append(OBJECT_SPLITTER);
		// 祕寶庫id
		result.append(toString(src.getStockId()));// 1
		result.append(OBJECT_SPLITTER);
		// 是否已購買
		result.append(toString(src.isBought()));// 2
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
		Treasure result = null;
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

	public Treasure disassembleBy_1(StringBuilder src) {
		Treasure result = null;
		if (src == null) {
			return result;
		}
		//

		String[] values = StringUtils.splitPreserveAllTokens(src.toString(),
				OBJECT_SPLITTER);
		if (ArrayHelper.isEmpty(values)) {
			return result;
		}
		int idx = 0;

		// id
		String id = toObject(values, idx++, String.class);// 0
		// 祕寶庫id
		String stockId = toObject(values, idx++, String.class);// 1
		// 建構祕寶
		result = treasureService.createTreasure(stockId, id);
		// 無法建構祕寶就傳回null
		if (result == null) {
			return result;
		}
		// 是否已購買
		boolean bought = toObject(values, idx++, boolean.class);// 3
		result.setBought(bought);
		//
		return result;
	}
}
