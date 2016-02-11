package org.openyu.mix.chat.po.userType;

import java.sql.Types;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.chat.vo.Friend;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.impl.FriendGroupImpl;
import org.openyu.mix.chat.vo.impl.FriendImpl;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.entity.usertype.NamesBeanUserType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class FriendGroupUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -6482018693180507047L;

	private static NamesBeanUserType namesBeanUserType = new NamesBeanUserType();

	public FriendGroupUserType() {
		// --------------------------------------------------
		// 最新版本,目前用1,若將來有新版本
		// 可用其他版號,如:VolType._2
		// --------------------------------------------------
		setVolType(VolType._1);
	}

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public Class<?> returnedClass() {
		return FriendGroup.class;
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
		if (!(value instanceof FriendGroup)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		FriendGroup src = (FriendGroup) value;
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
	public String assembleBy_1(FriendGroup src) {
		StringBuilder result = new StringBuilder();
		// size
		Map<String, Friend> friends = src.getFriends();
		result.append((friends != null ? friends.size() : 0));// 0
		for (Map.Entry<String, Friend> entry : friends.entrySet())// 1
		{
			Friend friend = entry.getValue();
			result.append(SPLITTER);
			// id
			result.append(toString(friend.getId()));// e0
			result.append(SPLITTER);
			// 名稱
			result.append(namesBeanUserType.assembleBy_1(friend.getNames()));// e1
			result.append(SPLITTER);
			// 性別
			result.append(toString(friend.getGenderType()));// e2
			result.append(SPLITTER);
			// 行業
			result.append(toString(friend.getIndustryId()));// e3
			result.append(SPLITTER);
			// 等級
			result.append(toString(friend.getLevel()));// e4
			result.append(SPLITTER);
			// 戰力
			result.append(toString(friend.getPower()));// e5
			result.append(SPLITTER);
			// vip類別
			result.append(toString(friend.getVipType()));// e6
		}
		return result.toString();
	}

	// --------------------------------------------------
	// disassemble
	// --------------------------------------------------
	/**
	 * 由欄位反組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		// 預設傳回空物件,非null
		FriendGroup result = new FriendGroupImpl();
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

	/**
	 * v1 由欄位反組成物件
	 * 
	 * @param src
	 * @return
	 */
	public FriendGroup disassembleBy_1(StringBuilder src) {
		FriendGroup result = new FriendGroupImpl();
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
		int size = toObject(values, idx++, int.class);// 0
		//
		for (int i = 0; i < size; i++)// 1
		{
			Friend entry = new FriendImpl();
			// id
			entry.setId(toObject(values, idx++, String.class));// e0
			// 名稱 2♦zh_TW♣測試角色♦en_US♣Test Role
			Set<LocaleNameBean> names = namesBeanUserType
					.disassembleBy_1(new StringBuilder(values[idx++]));
			entry.setNames(names);
			// 性別
			entry.setGenderType(toEnumByInt(values, idx++, GenderType.class));// e2
			// 行業
			entry.setIndustryId(toObject(values, idx++, String.class));// e3
			// 等級
			entry.setLevel(toObject(values, idx++, int.class));// e4
			// 戰力
			entry.setPower(toObject(values, idx++, int.class));// e5
			// vip類別
			entry.setVipType(toEnumByInt(values, idx++, VipType.class));// e6
			//
			result.getFriends().put(entry.getId(), entry);
		}
		return result;
	}
}
