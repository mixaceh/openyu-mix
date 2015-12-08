package org.openyu.mix.mail;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.mail.service.MailService;
import org.openyu.mix.mail.service.socklet.MailSocklet;

public class MailTestSupporter extends AppTestSupporter {

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	/**
	 * 郵件服務
	 */
	protected static MailService mailService;

	protected static MailSocklet mailSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",//
				"META-INF/applicationContext-commons-core.xml",//
				"applicationContext-i18n.xml",//
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-schedule.xml",// 排程
				"META-INF/applicationContext-sls.xml",//
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/mail/applicationContext-mail.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext
				.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		mailService = (MailService) applicationContext.getBean("mailService");
		mailSocklet = (MailSocklet) applicationContext.getBean("mailSocklet");
	}

	public static class BeanTest extends MailTestSupporter {

		@Test
		public void mailService() {
			System.out.println(mailService);
			assertNotNull(mailService);
		}

		@Test
		public void mailSocklet() {
			System.out.println(mailSocklet);
			assertNotNull(mailSocklet);
		}

	}

}
