package org.openyu.mix.manor;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.manor.dao.ManorLogDao;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.manor.service.adapter.ManorChangeAdapter;
import org.openyu.mix.manor.service.socklet.ManorSocklet;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.ManorPen.Farm;
import org.openyu.mix.manor.vo.impl.ManorPenImpl.FarmImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.service.aop.SasangPlayInterceptor;
import org.openyu.mix.manor.service.aop.ManorReclaimInterceptor;
import org.openyu.mix.manor.service.aop.ManorDisuseInterceptor;
import org.openyu.mix.manor.service.aop.ManorCultureInterceptor;

public class ManorTestSupporter extends AppTestSupporter {
	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	protected static ManorCollector manorCollector = ManorCollector
			.getInstance();

	protected static ManorService manorService;

	protected static ManorSocklet manorSocklet;

	// log
	protected static ManorLogDao manorLogDao;

	protected static ManorLogService manorLogService;

	// 事件監聽器
	protected static ManorChangeAdapter manorChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",//
				"META-INF/applicationContext-sfc.xml",//
				"applicationContext-message.xml",//
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-schedule.xml",// 排程
				"META-INF/applicationContext-sls.xml",//
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/manor/applicationContext-manor.xml",//
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
		manorService = (ManorService) applicationContext
				.getBean("manorService");
		manorSocklet = (ManorSocklet) applicationContext
				.getBean("manorSocklet");
		//
		manorLogDao = (ManorLogDao) applicationContext.getBean("manorLogDao");
		manorLogService = (ManorLogService) applicationContext
				.getBean("manorLogService");
		//
		manorChangeAdapter = (ManorChangeAdapter) applicationContext
				.getBean("manorChangeAdapter");
	}

	public static class BeanTest extends ManorTestSupporter {

		@Test
		public void manorService() {
			System.out.println(manorService);
			assertNotNull(manorService);
		}

		@Test
		public void manorReclaimAdvice() {
			ManorReclaimInterceptor bean = (ManorReclaimInterceptor) applicationContext
					.getBean("manorReclaimAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorReclaimPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("manorReclaimPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorReclaimAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("manorReclaimAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorDisuseAdvice() {
			ManorDisuseInterceptor bean = (ManorDisuseInterceptor) applicationContext
					.getBean("manorDisuseAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorDisusePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("manorDisusePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorDisuseAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("manorDisuseAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorCultureAdvice() {
			ManorCultureInterceptor bean = (ManorCultureInterceptor) applicationContext
					.getBean("manorCultureAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorCulturePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("manorCulturePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorCultureAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("manorCultureAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void manorLogDao() {
			System.out.println(manorLogDao);
			assertNotNull(manorLogDao);
		}

		@Test
		public void manorLogService() {
			System.out.println(manorLogService);
			assertNotNull(manorLogService);
		}

		@Test
		public void manorSocklet() {
			System.out.println(manorSocklet);
			assertNotNull(manorSocklet);
		}

		@Test
		public void manorChangeAdapter() {
			System.out.println(manorChangeAdapter);
			assertNotNull(manorChangeAdapter);
		}
	}

	/**
	 * 模擬開墾
	 * 
	 * @param role
	 */
	public static void mockReclaim(Role role) {
		ManorPen manorPen = role.getManorPen();
		Farm farm = new FarmImpl(0);
		//
		Land land = manorCollector.createLand("L_TROPICS_G001");
		land.setAmount(1);
		land.setEnhanceLevel(EnhanceLevel._0);
		//
		farm.setLand(land);
		//
		manorPen.setFarm(farm.getId(), farm);
	}

	/**
	 * 模擬種植
	 * 
	 * @param role
	 */
	public static void mockPlant(Role role) {
		ManorPen manorPen = role.getManorPen();
		Farm farm = manorPen.getFarm(0);

		// 種植1個種子
		Seed seed = mockSeed("S_COTTON_G001");
		seed.setAmount(1);
		seed.setGrowMills(30 * 1000L);// 30秒後成熟
		// farm.addSeed會設定
		// seed.setPlantTime(System.currentTimeMillis());
		// seed.setMatureType(MatureType.GROWING);
		seed.setReapMills(30 * 1000L);// 成熟後不收割,30秒後枯萎
		farm.addSeed(0, seed);
	}

	/*
	 * 模擬種子
	 */
	public static Seed mockSeed(String seeId) {
		// 種植1個種子
		Seed result = manorCollector.createSeed("S_COTTON_G001");
		result.setAmount(1);
		result.setGrowMills(30 * 1000L);// 30秒後成熟
		// farm.addSeed會設定
		// seed.setPlantTime(System.currentTimeMillis());
		// seed.setMatureType(MatureType.GROWING);
		result.setReapMills(30 * 1000L);// 成熟後不收割,30秒後枯萎
		//
		return result;
	}

}
