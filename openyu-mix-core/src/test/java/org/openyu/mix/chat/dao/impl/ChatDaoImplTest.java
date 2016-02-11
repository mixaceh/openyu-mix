package org.openyu.mix.chat.dao.impl;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.po.ChatPo;
import org.openyu.mix.chat.po.impl.ChatPoImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

public class ChatDaoImplTest extends ChatTestSupporter {

	/**
	 * 隨機角色
	 * 
	 * @return
	 */
	public static ChatPo randomChatPo() {
		final String UNIQUE = randomUnique();
		//
		final String ROLE_ID = "TEST_ROLE" + UNIQUE;
		//
		ChatPo result = new ChatPoImpl();
		result.setId(ROLE_ID);
		result.setOpenFriend(randomBoolean());
		//
		return result;
	}

	/**
	 * 檢核角色
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertChatPo(ChatPo expected, ChatPo actual) {
		if (expected == null) {
			assertNull(actual);
		} else {
			assertNotNull(actual);
			//
			assertEquals(expected.getId(), actual.getId());
			assertEquals(expected.isOpenFriend(), actual.isOpenFriend());
		}
	}

	@Test
	// insert -> find -> delete
	//
	// 10 times: 7237 mills.
	// 10 times: 6825 mills.
	// 10 times: 6693 mills.
	//
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void crud() {
		int count = 10;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 隨機
			ChatPo chatPo = randomChatPo();

			// create
			Serializable pk = chatDao.insert(chatPo);
			printInsert(i, pk);
			assertNotNull(pk);

			// retrieve
			ChatPo foundEntity = chatDao.find(ChatPoImpl.class, chatPo.getSeq());
			printFind(i, foundEntity);
			assertChatPo(chatPo, foundEntity);

			// update
			chatPo.setOpenFriend(true);
			int updated = chatDao.update(chatPo);
			printUpdate(i, updated);
			assertTrue(updated > 0);

			// delete
			ChatPo deletedEntity = chatDao.delete(ChatPoImpl.class, chatPo.getSeq());
			printDelete(i, deletedEntity);
			assertNotNull(deletedEntity);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void insert() {
		// 隨機
		ChatPo chatPo = randomChatPo();
		//
		Serializable pk = chatDao.insert(chatPo);
		printInsert(pk);
		assertNotNull(pk);

		ChatPo foundEntity = chatDao.find(ChatPoImpl.class, chatPo.getSeq());
		assertChatPo(chatPo, foundEntity);

		System.out.println(chatPo);
	}

	@Test
	// 1 times: 272 mills.
	// 1 times: 276 mills.
	// 1 times: 276 mills.
	// verified: ok
	public void findChat() {
		final String ROLE_ID = "TEST_ROLE";
		ChatPo result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = chatDao.findChat(ROLE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

	@Test
	public void update() {
		final String ROLE_ID = "TEST_ROLE";

		ChatPo value = new ChatPoImpl();
		value.setSeq(11L);
		//
		value.setId(ROLE_ID);
		value.setOpenFriend(true);
		value.setVersion(0);
		//
		int result = 0;

		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = chatService.update(value);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result > 0);
	}

	@Test
	public void reindex() {
		chatDao.reindex(ChatPoImpl.class);
	}

}
