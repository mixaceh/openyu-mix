package org.openyu.mix.train;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.train.aop.TrainInspireInterceptor;
import org.openyu.mix.train.dao.TrainLogDao;
import org.openyu.mix.train.service.TrainLogService;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.train.service.TrainSetService;
import org.openyu.mix.train.service.adapter.TrainChangeAdapter;
import org.openyu.mix.train.socklet.TrainSocklet;
import org.openyu.mix.train.vo.TrainCollector;

public class TrainTestSupporter extends AppTestSupporter {

	protected static TrainCollector trainCollector = TrainCollector.getInstance();

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	protected static TrainSetService trainSetService;

	protected static TrainService trainService;

	protected static TrainSocklet trainSocklet;

	// log
	protected static TrainLogDao trainLogDao;

	protected static TrainLogService trainLogService;

	// 事件監聽器
	protected static TrainChangeAdapter trainChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-scheduler.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/account/applicationContext-account.xml", //
				"org/openyu/mix/item/applicationContext-item.xml", //
				"org/openyu/mix/role/applicationContext-role.xml", //
				"org/openyu/mix/train/applicationContext-train.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		//
		trainSetService = (TrainSetService) applicationContext.getBean("trainSetService");
		trainService = (TrainService) applicationContext.getBean("trainService");
		trainSocklet = (TrainSocklet) applicationContext.getBean("trainSocklet");
		//
		trainLogDao = (TrainLogDao) applicationContext.getBean("trainLogDao");
		trainLogService = (TrainLogService) applicationContext.getBean("trainLogService");
		//
		trainChangeAdapter = (TrainChangeAdapter) applicationContext.getBean("trainChangeAdapter");
	}

	public static class BeanTest extends TrainTestSupporter {

		@Test
		public void trainSetService() {
			System.out.println(trainSetService);
			assertNotNull(trainSetService);
		}

		@Test
		public void trainService() {
			System.out.println(trainService);
			assertNotNull(trainService);
		}

		@Test
		public void trainInspireAdvice() {
			TrainInspireInterceptor bean = (TrainInspireInterceptor) applicationContext.getBean("trainInspireAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void trainInspirePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("trainInspirePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void trainInspireAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("trainInspireAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void trainLogDao() {
			System.out.println(trainLogDao);
			assertNotNull(trainLogDao);
		}

		@Test
		public void trainLogService() {
			System.out.println(trainLogService);
			assertNotNull(trainLogService);
		}

		@Test
		public void trainSocklet() {
			System.out.println(trainSocklet);
			assertNotNull(trainSocklet);
		}

		@Test
		public void trainChangeAdapter() {
			System.out.println(trainChangeAdapter);
			assertNotNull(trainChangeAdapter);
		}
	}

}
