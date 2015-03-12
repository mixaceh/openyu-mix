package org.openyu.mix.chat.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.junit.Test;
import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.impl.ChatImpl;
import org.openyu.commons.io.IoHelper;
import org.openyu.commons.thread.ThreadHelper;

public class StoreChatServiceImplTest extends ChatTestSupporter {

	@Test
	public void storeChat() {
		// 隨機
		Chat chat = randomChat();

		// create
		Serializable pk = chatService.insert(chat);
		printInsert(0, pk);
		assertNotNull(pk);
		System.out.println("version: " + chat.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// update
		boolean stored = storeChatService.storeChat(true, chat);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + chat.getVersion());
		System.out.println("可以關DB了,要測試序列化檔案");
		ThreadHelper.sleep(5 * 1000);

		// 在這段等待時間內,把db關了,再儲存資料,就會發生錯誤,然後重試存db
		// 儲存到db, 若失敗, 則序列化到檔案, 目錄, file:custom/chat/unsave
		// update2
		stored = storeChatService.storeChat(true, chat);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + chat.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// update3
		stored = storeChatService.storeChat(true, chat);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + chat.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// delete
		Chat deletedEntity = chatService.delete(ChatImpl.class, chat.getSeq());
		printDelete(0, deletedEntity);
		assertNotNull(deletedEntity);
	}

	@Test
	// JDK serialize
	// 10 times: 37 mills.
	// available: 1963
	public void serializeChat() throws Exception {
		// 隨機
		Chat chat = randomChat();
		//
		String result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeChatService.serializeChat(true, chat);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);

		// 讀取序列化檔案
		InputStream io = new BufferedInputStream(new FileInputStream(result));
		System.out.println("available: " + IoHelper.available(io));
		//
		byte[] bytes = IoHelper.read(io);
		System.out.println("length: " + bytes.length);
	}

	@Test
	public void deserializeChat() throws Exception {
		// 隨機
		Chat chat = randomChat();
		String outName = storeChatService.serializeChat(true, chat);
		System.out.println(outName);
		//
		Chat result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeChatService.deserializeChat(chat.getId());
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(chat.getId(), result.getId());
	}

	@Test
	public void serialize_TEST_ROLE() {
		final String ROLE_ID = "TEST_ROLE";
		Chat chat = chatService.findChat(ROLE_ID);
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeChatService.serializeChat(true, chat);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void deserialize_TEST_ROLE() throws Exception {
		final String ROLE_ID = "TEST_ROLE";
		//
		Chat result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeChatService.deserializeChat(ROLE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ROLE_ID, result.getId());
	}
}
