package org.openyu.mix.activity;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.activity.service.ActivityService;
import org.openyu.mix.activity.service.socklet.ActivitySocklet;
import org.openyu.mix.activity.vo.ActivityCollector;

public class ActivityTestSupporter extends AppTestSupporter {

	protected static ActivityCollector activityCollector = ActivityCollector
			.getInstance();

	protected static ActivityService activityService;

	protected static ActivitySocklet activitySocklet;

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
				"org/openyu/mix/activity/applicationContext-activity.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		activityService = (ActivityService) applicationContext
				.getBean("activityService");
		activitySocklet = (ActivitySocklet) applicationContext
				.getBean("activitySocklet");
	}

	public static class BeanTest extends ActivityTestSupporter {
		@Test
		public void activityService() {
			System.out.println(activityService);
			assertNotNull(activityService);
		}

		@Test
		public void activitySocklet() {
			System.out.println(activitySocklet);
			assertNotNull(activitySocklet);
		}
	}
}
