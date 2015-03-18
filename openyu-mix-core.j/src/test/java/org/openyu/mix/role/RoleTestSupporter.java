package org.openyu.mix.role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.mix.role.dao.RoleDao;
import org.openyu.mix.role.dao.RoleLogDao;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.StoreRoleService;
import org.openyu.mix.role.service.adapter.RoleChangeAdapter;
import org.openyu.mix.role.service.aop.RoleChangeFameInterceptor;
import org.openyu.mix.role.service.aop.RoleChangeGoldInterceptor;
import org.openyu.mix.role.service.aop.RoleChangeLevelInterceptor;
import org.openyu.mix.role.service.aop.RoleDecreaseGoldInterceptor;
import org.openyu.mix.role.service.aop.RoleIncreaseGoldInterceptor;
import org.openyu.mix.role.service.aop.RoleResetGoldInterceptor;
import org.openyu.mix.role.service.socklet.RoleSocklet;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.BagPenImplTest;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;

public class RoleTestSupporter extends AppTestSupporter {

	protected static RoleDao roleDao;

	/**
	 * 角色服務
	 */
	protected static RoleService roleService;

	/**
	 * 儲存角色服務
	 * 
	 * 20140930
	 */
	protected static StoreRoleService storeRoleService;

	// log
	protected static RoleLogDao roleLogDao;

	protected static RoleLogService roleLogService;

	protected static RoleSocklet roleSocklet;

	// 事件監聽器
	protected static RoleChangeAdapter roleChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",//
				"META-INF/applicationContext-commons-core.xml",//
				"applicationContext-message.xml",//
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-schedule.xml",// 排程
				"META-INF/applicationContext-sls.xml",//
				"org/openyu/mix/app/applicationContext-app.xml",//
				// biz
				"org/openyu/mix/account/applicationContext-account.xml",//
				"org/openyu/mix/item/applicationContext-item.xml",//
				"org/openyu/mix/role/applicationContext-role.xml",//
		});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
		roleDao = (RoleDao) applicationContext.getBean("roleDao");
		roleService = (RoleService) applicationContext.getBean("roleService");
		storeRoleService = (StoreRoleService) applicationContext
				.getBean("storeRoleService");
		//
		roleLogDao = (RoleLogDao) applicationContext.getBean("roleLogDao");
		roleLogService = (RoleLogService) applicationContext
				.getBean("roleLogService");
		roleSocklet = (RoleSocklet) applicationContext.getBean("roleSocklet");
		//
		roleChangeAdapter = (RoleChangeAdapter) applicationContext
				.getBean("roleChangeAdapter");
	}

	public static class BeanTest extends RoleTestSupporter {

		@Test
		public void roleDao() {
			System.out.println(roleDao);
			assertNotNull(roleDao);
		}

		@Test
		public void roleService() {
			System.out.println(roleService);
			assertNotNull(roleService);
		}

		@Test
		public void storeRoleService() {
			System.out.println(storeRoleService);
			assertNotNull(storeRoleService);
		}

		@Test
		public void roleServiceTxPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleServiceTxPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleServiceTxAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleServiceTxAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeLevelAdvice() {
			RoleChangeLevelInterceptor bean = (RoleChangeLevelInterceptor) applicationContext
					.getBean("roleChangeLevelAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeLevelPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleChangeLevelPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeLevelAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleChangeLevelAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleIncreaseGoldAdvice() {
			RoleIncreaseGoldInterceptor bean = (RoleIncreaseGoldInterceptor) applicationContext
					.getBean("roleIncreaseGoldAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleIncreaseGoldPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleIncreaseGoldPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleIncreaseGoldAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleIncreaseGoldAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleDecreaseGoldAdvice() {
			RoleDecreaseGoldInterceptor bean = (RoleDecreaseGoldInterceptor) applicationContext
					.getBean("roleDecreaseGoldAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleDecreaseGoldPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleDecreaseGoldPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleDecreaseGoldAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleDecreaseGoldAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeGoldAdvice() {
			RoleChangeGoldInterceptor bean = (RoleChangeGoldInterceptor) applicationContext
					.getBean("roleChangeGoldAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeGoldPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleChangeGoldPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeGoldAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleChangeGoldAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleResetGoldAdvice() {
			RoleResetGoldInterceptor bean = (RoleResetGoldInterceptor) applicationContext
					.getBean("roleResetGoldAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleResetGoldPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleResetGoldPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleResetGoldAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleResetGoldAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeFameAdvice() {
			RoleChangeFameInterceptor bean = (RoleChangeFameInterceptor) applicationContext
					.getBean("roleChangeFameAdvice");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeFamePointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleChangeFamePointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleChangeFameAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleChangeFameAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleLogDao() {
			System.out.println(roleLogDao);
			assertNotNull(roleLogDao);
		}

		@Test
		public void roleLogService() {
			System.out.println(roleLogService);
			assertNotNull(roleLogService);
		}

		@Test
		public void roleLogServiceTxPointcut() {
			AspectJExpressionPointcut bean = (AspectJExpressionPointcut) applicationContext
					.getBean("roleLogServiceTxPointcut");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleLogServiceTxAdvisor() {
			DefaultBeanFactoryPointcutAdvisor bean = (DefaultBeanFactoryPointcutAdvisor) applicationContext
					.getBean("roleLogServiceTxAdvisor");
			System.out.println(bean);
			assertNotNull(bean);
		}

		@Test
		public void roleSocklet() {
			System.out.println(roleSocklet);
			assertNotNull(roleSocklet);
		}

		@Test
		public void roleChangeAdapter() {
			System.out.println(roleChangeAdapter);
			assertNotNull(roleChangeAdapter);
		}
	}

	/**
	 * 隨機角色
	 * 
	 * @return
	 */
	public static Role randomRole() {
		final String UNIQUE = randomUnique();
		final String ACCOUNT_ID = "TEST_ACCOUNT" + UNIQUE;
		//
		final String ROLE_ID = "TEST_ROLE" + UNIQUE;
		final String ZH_TW_NAME = "測試角色" + UNIQUE;
		final String EN_US_NAME = "Test Role" + UNIQUE;
		//
		Role result = new RoleImpl();
		result.setId(ROLE_ID);
		result.setValid(randomBoolean());
		result.addName(Locale.TRADITIONAL_CHINESE, ZH_TW_NAME);
		result.addName(Locale.US, EN_US_NAME);
		//
		// result.setRaceType(randomType(RaceType.class));
		// result.setCareerType(randomType(CareerType.class));
		//
		result.setExp(randomLong(10000));
		result.setGold(randomLong(100));
		result.setLevel(randomInt(100));
		result.getAttributeGroup().setAttributes(randomAttributes());
		//
		result.setEnterTime(randomDateLong());
		result.setLeaveTime(result.getEnterTime() + randomLong(10000000));
		result.setAcceptor("slave1");
		// 帳號
		result.setAccountId(ACCOUNT_ID);
		result.setBagPen(BagPenImplTest
				.mockBagPenBySameThing("T_POTION_HP_G001"));
		return result;
	}

	/**
	 * 隨機角色屬性
	 * 
	 * @return
	 */
	public static Map<AttributeType, Attribute> randomAttributes() {
		Map<AttributeType, Attribute> result = new LinkedHashMap<AttributeType, Attribute>();
		int size = 3;
		for (int i = 0; i < size; i++) {
			Attribute entry = new AttributeImpl();
			entry.setId(randomType(AttributeType.class));
			entry.setPoint(randomInt(100));
			entry.setAddPoint(randomInt(10));
			entry.setAddRate(randomInt(100));
			result.put(entry.getId(), entry);
		}
		return result;
	}

	public static void printAttribute(Role role) {
		for (Attribute attribute : role.getAttributeGroup().getAttributes()
				.values()) {
			System.out.println(attribute);
		}
	}

	/**
	 * 檢核角色
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertRole(Role expected, Role actual) {
		if (expected == null) {
			assertNull(actual);
		} else {
			assertNotNull(actual);
			//
			assertEquals(expected.getId(), actual.getId());
			assertEquals(expected.getValid(), actual.getValid());
			assertCollectionEquals(expected.getNames(), actual.getNames());
			//
			// assertEquals(expected.getRaceType(), actual.getRaceType());
			// assertEquals(expected.getCareerType(), actual.getCareerType());
			//
			assertEquals(expected.getExp(), actual.getExp());
			assertEquals(expected.getGold(), actual.getGold());
			assertEquals(expected.getLevel(), actual.getLevel());
			// 屬性
			assertAttrubutes(expected.getAttributeGroup().getAttributes(),
					actual.getAttributeGroup().getAttributes());
			//
			assertEquals(expected.getLeaveTime(), actual.getLeaveTime());
			// 帳號
			assertEquals(expected.getAccountId(), actual.getAccountId());
		}
	}

	/**
	 * 檢核屬性
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertAttrubutes(Map<AttributeType, Attribute> expected,
			Map<AttributeType, Attribute> actual) {
		if (expected == null) {
			assertNull(actual);
		} else {
			assertNotNull(actual);
			for (Map.Entry<AttributeType, Attribute> entry : expected
					.entrySet()) {
				AttributeType key = entry.getKey();
				Attribute expecteValue = entry.getValue();
				Attribute actualValue = actual.get(key);
				//
				assertEquals(expecteValue.getId(), actualValue.getId());
				//
				assertEquals(expecteValue.getPoint(), actualValue.getPoint());
				assertEquals(expecteValue.getAddPoint(),
						actualValue.getAddPoint());
				assertEquals(expecteValue.getFinal(), actualValue.getFinal());
			}
		}
	}

	/**
	 * 檢核包包欄
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void assertBagPen(BagPen expected, BagPen actual) {
		if (expected == null) {
			assertNull(actual);
		} else {
			assertNotNull(actual);
			//
			List<Integer> indexs = expected.getTabIndexs();
			for (Integer index : indexs) {
				assertEquals(expected.getTab(index).getItemSize(), actual
						.getTab(index).getItemSize());
			}
		}
	}
}
