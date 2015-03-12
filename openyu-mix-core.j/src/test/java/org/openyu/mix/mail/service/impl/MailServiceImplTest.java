package org.openyu.mix.mail.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.mail.MailTestSupporter;
import org.openyu.mix.mail.vo.Mail;
import org.openyu.mix.mail.vo.MailType;

public class MailServiceImplTest extends MailTestSupporter {

	@Test
	public void createMail() {
		List<Item> items = new LinkedList<Item>();
		Item item = mockItem("T_POTION_HP_G001", 1);
		items.add(item);
		item = mockItem("M_COTTON_G001", 10);
		items.add(item);
		//
		Mail result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			// 物品
			result = mailService.createMail(MailType.GENERAL, "TEST_ROLE",
					"測試角色", "123", "內容123", "TEST_ROLE_2", "測試角色_2", 999L,
					items);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(MailType.GENERAL, result.getMailType());
	}

	@Test
	public void newMail() {
		fail("Not yet implemented");
	}

	@Test
	public void sendNewMail() {
		fail("Not yet implemented");
	}

	@Test
	public void sendMail() {
		fail("Not yet implemented");
	}

	@Test
	public void fillMail() {
		fail("Not yet implemented");
	}

}
