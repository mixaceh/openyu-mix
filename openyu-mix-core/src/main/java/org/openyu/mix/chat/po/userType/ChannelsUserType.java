package org.openyu.mix.chat.po.userType;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.impl.ChannelImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

public class ChannelsUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -7006927488022876786L;

	public ChannelsUserType() {
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
		return Map.class;
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
		if (!(value instanceof Map)) {
			return result;
		}
		//
		StringBuilder dest = new StringBuilder();
		@SuppressWarnings({ "rawtypes" })
		Map<ChannelType, Channel> src = (Map) value;
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
	public String assembleBy_1(Map<ChannelType, Channel> src) {
		StringBuilder result = new StringBuilder();
		// 頻道size
		result.append(src.size());// 0
		for (Map.Entry<ChannelType, Channel> entry : src.entrySet())// 1
		{
			Channel channel = entry.getValue();
			result.append(SPLITTER);
			// 頻道類別,key
			result.append(toString(entry.getKey()));// e0
			result.append(SPLITTER);
			// 是否開啟頻道
			result.append(toString(channel.isOpened()));// e1
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
		Map<ChannelType, Channel> result = new LinkedHashMap<ChannelType, Channel>();
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
	public Map<ChannelType, Channel> disassembleBy_1(StringBuilder src) {
		Map<ChannelType, Channel> result = new LinkedHashMap<ChannelType, Channel>();
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
			Channel entry = new ChannelImpl();
			// 頻道類別
			entry.setId(toEnumByInt(values, idx++, ChannelType.class));// e0
			// 是否開啟頻道
			entry.setOpened(toObject(values, idx++, boolean.class));// e1
			//
			result.put(entry.getId(), entry);
		}
		return result;
	}
}
