package org.openyu.mix.chat.po.userType;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.impl.ChannelImpl;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class ChannelsUserTypeTest extends BaseTestSupporter {

	private static ChannelsUserType userType = new ChannelsUserType();

	@Test
	// 1000000 times: 924 mills.
	// 1000000 times: 1059 mills.
	// 1000000 times: 1075 mills.
	// verified
	public void marshal() {
		Map<ChannelType, Channel> value = new LinkedHashMap<ChannelType, Channel>();
		Channel channel = new ChannelImpl(ChannelType.SYSTEM);
		channel.setOpened(true);
		value.put(channel.getId(), channel);
		//
		channel = new ChannelImpl(ChannelType.PRIVATE);
		channel.setOpened(false);
		value.put(channel.getId(), channel);
		//
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals("♥1♠2♠1♠1♠2♠0", result);
	}

	@Test
	// 1000000 times: 5646 mills.
	// 1000000 times: 5154 mills.
	// 1000000 times: 6044 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠2♠1♠1♠2♠0";
		//
		Map<ChannelType, Channel> result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(2, result.size());
		//
		Channel channel = result.get(ChannelType.SYSTEM);
		assertEquals(true, channel.isOpened());
		//
		channel = result.get(ChannelType.PRIVATE);
		assertEquals(false, channel.isOpened());
	}
}
