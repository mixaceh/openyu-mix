import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.thread.ThreadHelper;

public class ApplicationContextScheduleTest extends BaseTestSupporter {

	private static ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				"applicationContext-schedule.xml", // 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/system/applicationContext-system.xml", //
				"org/openyu/mix/account/applicationContext-account.xml", //
				"org/openyu/mix/item/applicationContext-item.xml", //
				"org/openyu/mix/role/applicationContext-role.xml", //
				//
				"org/openyu/mix/core/applicationContext-core.xml", //
				"org/openyu/mix/chat/applicationContext-chat.xml", //
				"org/openyu/mix/sasang/applicationContext-sasang.xml", //
				"org/openyu/mix/manor/applicationContext-manor.xml", //
				"org/openyu/mix/treasure/applicationContext-treasure.xml", //
				"org/openyu/mix/train/applicationContext-train.xml", //
				"org/openyu/mix/wuxing/applicationContext-wuxing.xml",//
		});
	}

	@Test
	public void coreDay00_00Job() {
		JobDetail bean = (JobDetail) applicationContext.getBean("coreDay00_00Job");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void coreDay00_00Trigger() {
		CronTrigger bean = (CronTrigger) applicationContext.getBean("coreDay00_00Trigger");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void coreDay00_03Job() {
		JobDetail bean = (JobDetail) applicationContext.getBean("coreDay00_03Job");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void coreDay00_03Trigger() {
		CronTrigger bean = (CronTrigger) applicationContext.getBean("coreDay00_03Trigger");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void coreDay00_07Job() {
		JobDetail bean = (JobDetail) applicationContext.getBean("coreDay00_07Job");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void coreDay00_07Trigger() {
		CronTrigger bean = (CronTrigger) applicationContext.getBean("coreDay00_07Trigger");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void scheduler() {
		Scheduler bean = (Scheduler) applicationContext.getBean("scheduler");
		System.out.println(bean);
		assertNotNull(bean);
		//
		ThreadHelper.sleep(3 * 1000);
	}
}
