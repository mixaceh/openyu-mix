package org.openyu.mix.role.po.userType;
import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.item.po.userType.ItemUserType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.BagPen.Tab;
import org.openyu.mix.role.vo.impl.BagPenImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.userType.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class BagPenUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -2066924784420555409L;

	private static transient ItemUserType itemUserType = new ItemUserType();

	public BagPenUserType() {
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
		return BagPen.class;
	}

	// --------------------------------------------------

	/**
	 * 由物件組成欄位
	 * 
	 * @see ItemUserType
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (value instanceof BagPen) {
			StringBuilder dest = new StringBuilder();
			BagPen src = (BagPen) value;
			// vol
			dest.append(assembleVol(getVolType()));
			//
			Map<Integer, Tab> tabs = src.getTabs();// 包含被鎖定的包包頁
			dest.append(tabs.size());// 包包頁數量
			for (Tab tab : tabs.values()) {
				dest.append(SPLITTER);
				dest.append(toString(tab.getId()));// 1,tabIndex
				dest.append(SPLITTER);
				dest.append(toString(tab.isLocked()));// 2,是否鎖定
				dest.append(SPLITTER);
				Map<Integer, Item> items = tab.getItems();
				dest.append(toString(items.size()));// 3,道具數量
				for (Map.Entry<Integer, Item> entry : items.entrySet()) {
					// 格子索引
					int gridIndex = entry.getKey();
					// 道具
					Item item = entry.getValue();
					//
					dest.append(SPLITTER);
					dest.append(toString(gridIndex));// gridIndex
					dest.append(SPLITTER);
					// 道具,vol=1,itemUserType
					dest.append(itemUserType.assembleBy_1(item));
				}
			}

			result = (R) dest.toString();
		}
		return result;
	}

	// --------------------------------------------------

	/**
	 * 由欄位反組成物件
	 * 
	 * @see ItemUserType
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		BagPen result = new BagPenImpl();
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
					// do other vol
					break;

				default:
					break;

				}
			}
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
	protected BagPen disassembleBy_1(StringBuilder src) {
		BagPen result = new BagPenImpl();
		if (src != null) {
			// 道具
			// ♥1♠4♠0♠0♠1♠0♠T_POTION_HP_G001♦T_01♦100♦0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_02♦100♦0♠2♠0♠0♠10♠0♠0
			// 道具+武器
			// ♥1♠4♠0♠0♠2♠0♠T_POTION_HP_G001♦T_01♦100♦0♠1♠W_MARS_SWORD_E001♦W_01♦1♦0♦0♦5♦21♣40♣0♣0♦22♣4000♣0♣0♦23♣4000♣0♣0♦24♣9600♣0♣0♦25♣60♣0♣0♠1♠0♠1♠0♠T_POTION_HP_G002♦T_02♦100♦0♠2♠0♠0♠10♠0♠0
			String[] values = StringUtils.splitPreserveAllTokens(
					src.toString(), SPLITTER);
			if (ArrayHelper.notEmpty(values)) {
				int idx = 0;
				int tabSize = toObject(values, idx++, int.class);// 0
				for (int i = 0; i < tabSize; i++) {
					int tabIndex = toObject(values, idx++, int.class);// 1,tabIndex
					boolean locked = toObject(values, idx++, boolean.class);// 2,是否鎖定
					int itemSize = toObject(values, idx++, int.class);// 3,道具數量
					//
					Tab tab = result.getTab(tabIndex);
					if (tab != null) {
						tab.setLocked(locked);// 是否鎖定
						for (int j = 0; j < itemSize; j++) {
							int gridIndex = toObject(values, idx++, int.class);// 4
							//
							StringBuilder buff = new StringBuilder();
							buff.append(values[idx++]);
							// System.out.println(buff);
							//
							if (buff.length() > 0) {
								// 道具,vol=1,itemUserType
								Item item = itemUserType.disassembleBy_1(buff);
								if (item != null) {
									// #issue tab.tab 會改變某些欄位
									// tab.addItem(gridIndex, item,
									// true);//忽略鎖定,否則鎖定會放不進去

									// #fix 改用 put
									tab.getItems().put(gridIndex, item);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
}
