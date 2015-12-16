package org.openyu.mix.core;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.core.aop.CoreRoleConnectInterceptor;
import org.openyu.mix.core.aop.CoreRoleDisconnectInterceptor;
import org.openyu.mix.core.dao.CoreLogDao;
import org.openyu.mix.core.service.CoreLogService;
import org.openyu.mix.core.service.CoreService;
import org.openyu.mix.core.service.adapter.CoreRelationAdapter;
import org.openyu.mix.core.service.adapter.CoreSessionAdapter;
import org.openyu.mix.core.service.socklet.CoreSocklet;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.sasang.service.SasangMachine;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.sasang.socklet.SasangSocklet;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.wuxing.service.WuxingMachine;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.socklet.WuxingSocklet;

public class CoreTestSupporter extends AppTestSupporter {
	/**
	 * 帳號服務-1
	 */
	protected static AccountService accountService;

	/**
	 * 道具服務-2
	 */
	protected static ItemService itemService;

	/**
	 * 角色服務-3
	 */
	protected static RoleService roleService;

	protected static CoreService coreService;

	protected static CoreSessionAdapter coreSessionAdapter;

	protected static CoreRelationAdapter coreRelationAdapter;

	protected static CoreLogDao coreLogDao;

	protected static CoreLogService coreLogService;

	protected static CoreSocklet coreSocklet;

	/**
	 * 聊天
	 */
	protected static ChatService chatService;

	/**
	 * 四象
	 */
	protected static SasangService sasangService;

	protected static SasangMachine sasangMachine;

	protected static SasangSocklet sasangSocklet;

	/**
	 * 莊園
	 */
	protected static ManorService manorService;

	/**
	 * 祕寶
	 */
	protected static TreasureService treasureService;

	/**
	 * 訓練
	 */
	protected static TrainService trainService;

	/**
	 * 五行
	 */
	protected static WuxingService wuxingService;

	protected static WuxingMachine wuxingMachine;

	protected static WuxingSocklet wuxingSocklet;

	// ---------------------------------------------------
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
				"org/openyu/mix/system/applicationContext-system.xml", //
				//
				"org/openyu/mix/chat/applicationContext-chat.xml", //
				"org/openyu/mix/sasang/applicationContext-sasang.xml", //
				"org/openyu/mix/manor/applicationContext-manor.xml", //
				"org/openyu/mix/treasure/applicationContext-treasure.xml", //
				"org/openyu/mix/train/applicationContext-train.xml", //
				"org/openyu/mix/wuxing/applicationContext-wuxing.xml", //
				// core
				"org/openyu/mix/core/applicationContext-core.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// 帳號
		accountService = (AccountService) applicationContext.getBean("accountService");
		// 道具
		itemService = (ItemService) applicationContext.getBean("itemService");
		// 角色
		roleService = (RoleService) applicationContext.getBean("roleService");
		//
		coreService = (CoreService) applicationContext.getBean("coreService");
		coreSessionAdapter = (CoreSessionAdapter) applicationContext.getBean("coreSessionAdapter");
		coreRelationAdapter = (CoreRelationAdapter) applicationContext.getBean("coreRelationAdapter");
		//
		coreLogDao = (CoreLogDao) applicationContext.getBean("coreLogDao");
		coreLogService = (CoreLogService) applicationContext.getBean("coreLogService");
		coreSocklet = (CoreSocklet) applicationContext.getBean("coreSocklet");

		// 聊天
		chatService = (ChatService) applicationContext.getBean("chatService");
		// 四象
		sasangService = (SasangService) applicationContext.getBean("sasangService");
		sasangMachine = (SasangMachine) applicationContext.getBean("sasangMachine");
		sasangSocklet = (SasangSocklet) applicationContext.getBean("sasangSocklet");
		// 莊園
		manorService = (ManorService) applicationContext.getBean("manorService");
		// 祕寶
		treasureService = (TreasureService) applicationContext.getBean("treasureService");
		// 訓練
		trainService = (TrainService) applicationContext.getBean("trainService");
		// 五行
		wuxingService = (WuxingService) applicationContext.getBean("wuxingService");
		wuxingMachine = (WuxingMachine) applicationContext.getBean("wuxingMachine");
		wuxingSocklet = (WuxingSocklet) applicationContext.getBean("wuxingSocklet");
	}

	// --------------------------------------------------

	public static class BeanTest extends CoreTestSupporter {
		@Test
		public void coreService() {
			System.out.println(coreService);
			assertNotNull(coreService);
		}

		@Test
		public void coreConnectAdvice() {
			CoreRoleConnectInterceptor bean = (CoreRoleConnectInterceptor) applicationContext
					.getBean("coreConnectAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreConnectPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("coreConnectPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreConnectAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("coreConnectAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreDisconnectAdvice() {
			CoreRoleDisconnectInterceptor bean = (CoreRoleDisconnectInterceptor) applicationContext
					.getBean("coreDisconnectAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreDisconnectPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("coreDisconnectPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreDisconnectAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("coreDisconnectAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void coreSessionAdapter() {
			System.out.println(coreSessionAdapter);
			assertNotNull(coreSessionAdapter);
		}

		@Test
		public void coreRelationAdapter() {
			System.out.println(coreRelationAdapter);
			assertNotNull(coreRelationAdapter);
		}

		@Test
		public void coreLogDao() {
			System.out.println(coreLogDao);
			assertNotNull(coreLogDao);
		}

		@Test
		public void coreLogService() {
			System.out.println(coreLogService);
			assertNotNull(coreLogService);
		}

		@Test
		public void coreSocklet() {
			System.out.println(coreSocklet);
			assertNotNull(coreSocklet);
		}
	}

}
