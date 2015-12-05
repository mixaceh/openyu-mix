package org.openyu.mix.system;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.system.service.SystemService;
import org.openyu.mix.system.service.socklet.SystemSocklet;

public class SystemTestSupporter extends AppTestSupporter {

	/**
	 * 系統服務
	 */
	protected static SystemService systemService;

	protected static SystemSocklet systemSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-message.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-schedule.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/system/applicationContext-system.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		systemService = (SystemService) applicationContext.getBean("systemService");
		systemSocklet = (SystemSocklet) applicationContext.getBean("systemSocklet");
	}

	// --------------------------------------------------

	public static class BeanTest extends SystemTestSupporter {

		@Test
		public void systemService() {
			System.out.println(systemService);
			assertNotNull(systemService);
		}

		@Test
		public void systemSocklet() {
			System.out.println(systemSocklet);
			assertNotNull(systemSocklet);
		}
	}
}
