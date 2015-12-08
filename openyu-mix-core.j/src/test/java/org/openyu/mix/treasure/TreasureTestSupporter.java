package org.openyu.mix.treasure;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.aop.TreasureBuyInterceptor;
import org.openyu.mix.treasure.aop.TreasureRefreshInterceptor;
import org.openyu.mix.treasure.dao.TreasureLogDao;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.treasure.service.adapter.TreasureChangeAdapter;
import org.openyu.mix.treasure.socklet.TreasureSocklet;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasureCollector;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.impl.TreasurePenImpl;

public class TreasureTestSupporter extends AppTestSupporter {

	protected static TreasureCollector treasureCollector = TreasureCollector.getInstance();

	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	protected static TreasureService treasureService;

	protected static TreasureSocklet treasureSocklet;

	// log
	protected static TreasureLogDao treasureLogDao;

	protected static TreasureLogService treasureLogService;

	// 事件監聽器
	protected static TreasureChangeAdapter treasureChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-schedule.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
				// biz
				"org/openyu/mix/account/applicationContext-account.xml", //
				"org/openyu/mix/item/applicationContext-item.xml", //
				"org/openyu/mix/role/applicationContext-role.xml", //
				"org/openyu/mix/treasure/applicationContext-treasure.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		//
		treasureService = (TreasureService) applicationContext.getBean("treasureService");
		treasureSocklet = (TreasureSocklet) applicationContext.getBean("treasureSocklet");
		//
		treasureLogDao = (TreasureLogDao) applicationContext.getBean("treasureLogDao");
		treasureLogService = (TreasureLogService) applicationContext.getBean("treasureLogService");
		//
		treasureChangeAdapter = (TreasureChangeAdapter) applicationContext.getBean("treasureChangeAdapter");
	}

	public static class BeanTest extends TreasureTestSupporter {

		@Test
		public void treasureService() {
			System.out.println(treasureService);
			assertNotNull(treasureService);
		}

		@Test
		public void treasureRefreshAdvice() {
			TreasureRefreshInterceptor bean = (TreasureRefreshInterceptor) applicationContext
					.getBean("treasureRefreshAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureRefreshPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("treasureRefreshPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureRefreshAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("treasureRefreshAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureBuyAdvice() {
			TreasureBuyInterceptor bean = (TreasureBuyInterceptor) applicationContext.getBean("treasureBuyAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureBuyPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("treasureBuyPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureBuyAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("treasureBuyAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void treasureLogDao() {
			System.out.println(treasureLogDao);
			assertNotNull(treasureLogDao);
		}

		@Test
		public void treasureLogService() {
			System.out.println(treasureLogService);
			assertNotNull(treasureLogService);
		}

		@Test
		public void treasureSocklet() {
			System.out.println(treasureSocklet);
			assertNotNull(treasureSocklet);
		}

		@Test
		public void treasureChangeAdapter() {
			System.out.println(treasureChangeAdapter);
			assertNotNull(treasureChangeAdapter);
		}
	}

	/**
	 * 模擬祕寶欄位
	 * 
	 * @param role
	 * @return
	 */
	public static TreasurePen mockTreasurePen(Role role) {
		TreasurePen result = new TreasurePenImpl(role);
		//
		result.setRefreshTime(System.currentTimeMillis());
		//
		Treasure treasure = treasureCollector.createTreasure("ROLE_EXP_001", "T_ROLE_EXP_G001");
		result.getTreasures().put(0, treasure);
		//
		treasure = treasureCollector.createTreasure("ROLE_EXP_001", "T_ROLE_EXP_G002");
		result.getTreasures().put(1, treasure);
		//
		treasure = treasureCollector.createTreasure("ROLE_EXP_001", "T_ROLE_EXP_G003");
		result.getTreasures().put(2, treasure);
		return result;
	}

}
