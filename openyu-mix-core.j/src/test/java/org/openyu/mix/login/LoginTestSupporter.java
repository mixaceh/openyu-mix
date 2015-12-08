package org.openyu.mix.login;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.core.CoreTestSupporter;
import org.openyu.mix.login.service.LoginService;
import org.openyu.mix.login.service.socklet.LoginSocklet;

public class LoginTestSupporter extends CoreTestSupporter
{

	protected static LoginService loginService;

	protected static LoginSocklet loginSocklet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",// 
				"META-INF/applicationContext-commons-core.xml",//
				"applicationContext-i18n.xml",//
				"applicationContext-database.xml",// 
				"applicationContext-database-log.xml",// 
				//"applicationContext-schedule.xml",//
				"META-INF/applicationContext-sls.xml",//
				"org/openyu/mix/app/applicationContext-app.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
				"org/openyu/mix/login/applicationContext-login.xml"// 
		});
		//---------------------------------------------------
		initialize();
		//---------------------------------------------------
		loginService = (LoginService) applicationContext.getBean("loginService");
		loginSocklet = (LoginSocklet) applicationContext.getBean("loginSocklet");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void loginService()
	{
		System.out.println(loginService);
		assertNotNull(loginService);
	}

	@Test
	public void loginSocklet()
	{
		System.out.println(loginSocklet);
		assertNotNull(loginSocklet);
	}
}
