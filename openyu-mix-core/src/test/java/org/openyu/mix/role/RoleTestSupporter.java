package org.openyu.mix.role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.mix.role.aop.RoleAspect;
import org.openyu.mix.role.dao.RoleDao;
import org.openyu.mix.role.dao.RoleLogDao;
import org.openyu.mix.role.service.RoleLogService;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.StoreRoleService;
import org.openyu.mix.role.service.adapter.RoleChangeAdapter;
import org.openyu.mix.role.socklet.RoleSocklet;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.BagPenImplTest;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;

public class RoleTestSupporter extends AppTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();
	
	protected static RoleDao roleDao;

	/**
	 * 角色服務
	 */
	protected static RoleService roleService;

	/**
	 * 儲存角色服務
	 * 
	 * 2014/09/30
	 */
	protected static StoreRoleService storeRoleService;

	// log
	protected static RoleLogDao roleLogDao;

	protected static RoleLogService roleLogService;
	
	protected static RoleAspect roleAspect;

	protected static RoleSocklet roleSocklet;

	// 事件監聽器
	protected static RoleChangeAdapter roleChangeAdapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-i18n.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database.xml", //
				"applicationContext-database-log.xml", //
				// "applicationContext-scheduler.xml",// 排程
				"org/openyu/mix/app/applicationContext-app.xml", //
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
		roleAspect = (RoleAspect) applicationContext.getBean("roleAspect");
		//
		roleChangeAdapter = (RoleChangeAdapter) applicationContext
				.getBean("roleChangeAdapter");
	}

	public static class BeanTest extends RoleTestSupporter {

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleDao() {
			System.out.println(roleDao);
			assertNotNull(roleDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleService() {
			System.out.println(roleService);
			assertNotNull(roleService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void storeRoleService() {
			System.out.println(storeRoleService);
			assertNotNull(storeRoleService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleLogDao() {
			System.out.println(roleLogDao);
			assertNotNull(roleLogDao);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleLogService() {
			System.out.println(roleLogService);
			assertNotNull(roleLogService);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleSocklet() {
			System.out.println(roleSocklet);
			assertNotNull(roleSocklet);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
		public void roleAspect() {
			System.out.println(roleAspect);
			assertNotNull(roleAspect);
		}

		@Test
		@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
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
		result.setAcceptorId("slave1");
		// 帳號
		result.setAccountId(ACCOUNT_ID);
		result.setBagPen(BagPenImplTest
				.mockBagPenWithSameThing("T_POTION_HP_G001"));
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
