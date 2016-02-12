package org.openyu.mix.manor.po.usertype;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.item.po.usertype.ItemUserType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.manor.vo.ManorInfo.Farm;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.impl.ManorInfoImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class ManorInfoUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private static transient ItemUserType itemUserType = new ItemUserType();

	public ManorInfoUserType() {
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
		return ManorInfo.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof ManorInfo)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		ManorInfo src = (ManorInfo) value;
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
	public String assembleBy_1(ManorInfo src) {
		StringBuilder result = new StringBuilder();
		//
		Map<Integer, Farm> farms = src.getFarms();// 包含被鎖定的包包頁
		result.append(farms.size());// 包包頁數量
		for (Farm farm : farms.values()) {
			result.append(SPLITTER);
			result.append(toString(farm.getId()));// 1,farmIndex
			result.append(SPLITTER);
			result.append(toString(farm.isLocked()));// 2,是否鎖定

			// 土地
			Land land = farm.getLand();
			// 是否有土地,可能沒開墾,就沒土地可用
			boolean hadLand = (land != null);
			//
			result.append(SPLITTER);
			result.append(toString(hadLand));// 3,是否有土地
			if (hadLand) {
				result.append(SPLITTER);
				result.append(itemUserType.assembleBy_1(land));
			}
			//
			result.append(SPLITTER);
			Map<Integer, Seed> seeds = farm.getSeeds();
			result.append(toString(seeds.size()));// 4,種子數量
			for (Map.Entry<Integer, Seed> entry : seeds.entrySet()) {
				// 格子索引
				int gridIndex = entry.getKey();
				// 種子
				Seed seed = entry.getValue();
				//
				result.append(SPLITTER);
				result.append(toString(gridIndex));// gridIndex
				result.append(SPLITTER);
				// 種子,vol=1,itemUserType
				result.append(itemUserType.assembleBy_1(seed));
			}
		}
		//
		return result.toString();
	}

	// --------------------------------------------------

	/**
	 * 由欄位反組成物件
	 * 
	 * @see ItemUserType
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		ManorInfo result = new ManorInfoImpl();
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

	/**
	 * @param src
	 * @return
	 * @see ItemUserType
	 * 
	 */
	protected ManorInfo disassembleBy_1(StringBuilder src) {
		ManorInfo result = new ManorInfoImpl();
		if (src == null) {
			return result;
		}
		// 土地+種子
		// ♥1♠6♠0♠0♠1♠L_TROPICS_G001♦L_00♦1♦0♦7♠1♠0♠S_COTTON_G001♦T_00♦1♦0♦180000♦0♦0♦2♠1♠0♠0♠1♠0♠S_OAK_G001♦T_01♦1♦0♦180000♦0♦0♦2♠2♠0♠0♠0♠3♠0♠0♠0♠4♠0♠0♠0♠5♠0♠0♠0
		String[] values = StringUtils.splitPreserveAllTokens(src.toString(),
				SPLITTER);
		if (ArrayHelper.notEmpty(values)) {
			int idx = 0;
			int tabSize = toObject(values, idx++, int.class);// 0
			for (int i = 0; i < tabSize; i++) {
				int tabIndex = toObject(values, idx++, int.class);// 1,tabIndex
				boolean locked = toObject(values, idx++, boolean.class);// 2,是否鎖定
				//
				boolean hadLand = toObject(values, idx++, boolean.class); // 3,是否有土地
				Land land = null;
				if (hadLand) {
					StringBuilder buff = new StringBuilder();
					buff.append(values[idx++]);
					// System.out.println(buff);
					if (buff.length() > 0) {
						Item item = itemUserType.disassembleBy_1(buff);
						if (item instanceof Land) {
							land = (Land) item;
						}
					}
				}
				//
				int itemSize = toObject(values, idx++, int.class);// 4,種子數量
				//
				Farm farm = result.getFarm(tabIndex, true);// 忽略鎖定
				if (farm != null) {
					farm.setLocked(locked);// 是否鎖定
					farm.setLand(land);// 土地
					for (int j = 0; j < itemSize; j++) {
						int gridIndex = toObject(values, idx++, int.class);// 4
						//
						StringBuilder buff = new StringBuilder();
						buff.append(values[idx++]);
						// System.out.println(buff);
						//
						if (buff.length() > 0) {
							// 種子,vol=1,itemUserType
							Item item = itemUserType.disassembleBy_1(buff);
							if (item instanceof Seed) {
								Seed seed = (Seed) item;
								// #issue farm.addSeed 會改變plantTime,matureType
								// farm.addSeed(gridIndex, seed,
								// true);//忽略鎖定,否則鎖定會放不進去

								// #fix 改用 put
								farm.getSeeds().put(gridIndex, seed);
							}
						}
					}
				}
			}
		}
		return result;
	}
}
