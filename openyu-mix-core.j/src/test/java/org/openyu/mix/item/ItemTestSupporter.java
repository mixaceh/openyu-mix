package org.openyu.mix.item;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.item.aop.ItemChangeEnhanceInterceptor;
import org.openyu.mix.item.aop.ItemDecreaseItemInterceptor;
import org.openyu.mix.item.aop.ItemIncreaseItemInterceptor;
import org.openyu.mix.item.aop.ItemUseEnhanceInterceptor;
import org.openyu.mix.item.dao.ItemLogDao;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.socklet.ItemSocklet;

public class ItemTestSupporter extends AppTestSupporter {

	/**
	 * 道具服務
	 */
	protected static ItemService itemService;

	protected static ItemLogDao itemLogDao;
	//
	protected static ItemLogService itemLogService;

	protected static ItemSocklet itemSocklet;

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
				// "org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				// "org/openyu/mix/role/applicationContext-role.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		itemService = (ItemService) applicationContext.getBean("itemService");
		itemLogDao = (ItemLogDao) applicationContext.getBean("itemLogDao");
		itemLogService = (ItemLogService) applicationContext.getBean("itemLogService");
		itemSocklet = (ItemSocklet) applicationContext.getBean("itemSocklet");
	}

	// --------------------------------------------------

	public static class BeanTest extends ItemTestSupporter {

		@Test
		public void itemService() {
			System.out.println(itemService);
			assertNotNull(itemService);
		}

		@Test
		public void itemIncreaseItemAdvice() {
			ItemIncreaseItemInterceptor bean = (ItemIncreaseItemInterceptor) applicationContext
					.getBean("itemIncreaseItemAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemIncreaseItemPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("itemIncreaseItemPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemIncreaseItemAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("itemIncreaseItemAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemDecreaseItemAdvice() {
			ItemDecreaseItemInterceptor bean = (ItemDecreaseItemInterceptor) applicationContext
					.getBean("itemDecreaseItemAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemDecreaseItemPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("itemDecreaseItemPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemDecreaseItemAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("itemDecreaseItemAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemUseEnhanceAdvice() {
			ItemUseEnhanceInterceptor bean = (ItemUseEnhanceInterceptor) applicationContext
					.getBean("itemUseEnhanceAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemUseEnhancePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("itemUseEnhancePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemUseEnhanceAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("itemUseEnhanceAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemChangeEnhanceAdvice() {
			ItemChangeEnhanceInterceptor bean = (ItemChangeEnhanceInterceptor) applicationContext
					.getBean("itemChangeEnhanceAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemChangeEnhancePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("itemChangeEnhancePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemChangeEnhanceAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("itemChangeEnhanceAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemLogDao() {
			System.out.println(itemLogDao);
			assertNotNull(itemLogDao);
		}

		@Test
		public void itemLogService() {
			System.out.println(itemLogService);
			assertNotNull(itemLogService);
		}

		@Test
		public void itemLogServiceTxPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("itemLogServiceTxPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemLogServiceTxAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("itemLogServiceTxAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void itemSocklet() {
			System.out.println(itemSocklet);
			assertNotNull(itemSocklet);
		}
	}
}
