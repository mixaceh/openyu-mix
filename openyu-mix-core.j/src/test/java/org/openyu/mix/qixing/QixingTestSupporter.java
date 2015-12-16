package org.openyu.mix.qixing;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.role.vo.Role;
//import org.openyu.mix.qixing.dao.QixingLogDao;
//import org.openyu.mix.qixing.service.QixingLogService;
//import org.openyu.mix.qixing.service.QixingService;
//import org.openyu.mix.qixing.service.adapter.QixingChangeAdapter;
//import org.openyu.mix.qixing.service.impl.QixingMachineImpl;
//import org.openyu.mix.qixing.service.socklet.QixingSocklet;
import org.openyu.mix.qixing.vo.Outcome;
import org.openyu.mix.qixing.vo.QixingCollector;
import org.openyu.mix.qixing.vo.QixingPen;
import org.openyu.mix.qixing.vo.impl.QixingPenImpl;

public class QixingTestSupporter extends AppTestSupporter {
	protected static QixingCollector qixingCollector = QixingCollector.getInstance();

	/**
	 * 取實作class,所以不能用aop
	 * 
	 * 改取interface
	 */
	// protected static QixingMachine qixingMachine;
	//
	// protected static QixingService qixingService;
	//
	// protected static QixingSocklet qixingSocklet;
	//
	// //log
	// protected static QixingLogDao qixingLogDao;
	//
	// //
	// protected static QixingLogService qixingLogService;
	//
	// //事件監聽器
	// protected static QixingChangeAdapter qixingChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { "applicationContext-init.xml", //
				"META-INF/applicationContext-commons-core.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-scheduler.xml",//
				"META-INF/applicationContext-sls.xml", //
				"org/openyu/mix/app/applicationContext-app.xml", //
				"org/openyu/mix/item/applicationContext-item.xml", //
				"org/openyu/mix/account/applicationContext-account.xml", //
				"org/openyu/mix/role/applicationContext-role.xml", //
				"org/openyu/mix/qixing/applicationContext-qixing.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		// qixingMachine = (QixingMachine)
		// applicationContext.getBean("qixingMachine");
		// qixingService = (QixingService)
		// applicationContext.getBean("qixingService");
		// qixingSocklet = (QixingSocklet)
		// applicationContext.getBean("qixingSocklet");
		// //
		// qixingLogDao = (QixingLogDao)
		// applicationContext.getBean("qixingLogDao");
		// qixingLogService = (QixingLogService)
		// applicationContext.getBean("qixingLogService");
		// qixingChangeAdapter = (QixingChangeAdapter) applicationContext
		// .getBean("qixingChangeAdapter");
	}

	// /**
	// * 模擬七星欄位
	// *
	// * @param role
	// */
	// public static QixingPen mockQixingPen(Role role)
	// {
	// QixingPen result = new QixingPenImpl(role);
	// //
	// result.setPlayTime(System.currentTimeMillis());
	// result.setDailyTimes(1);
	// Outcome outcome = qixingMachine.createOutcome("13221", "34352", "42243");
	// result.setOutcome(outcome);
	// result.getAwards().put("T_ROLE_EXP_G001", 1);
	// result.getAwards().put("T_ROLE_GOLD_G001", 5);
	// result.getAwards().put("T_ROLE_FAME_G001", 10);
	// return result;
	// }
	//
	// @Test
	// public void qixingMachine()
	// {
	// System.out.println(qixingMachine);
	// assertNotNull(qixingMachine);
	// }
	//
	// @Test
	// public void qixingService()
	// {
	// System.out.println(qixingService);
	// assertNotNull(qixingService);
	// }
	//
	// @Test
	// public void qixingLogDao()
	// {
	// System.out.println(qixingLogDao);
	// assertNotNull(qixingLogDao);
	// }
	//
	// @Test
	// public void qixingLogService()
	// {
	// System.out.println(qixingLogService);
	// assertNotNull(qixingLogService);
	// }
	//
	// @Test
	// public void qixingSocklet()
	// {
	// System.out.println(qixingSocklet);
	// assertNotNull(qixingSocklet);
	// }

}
